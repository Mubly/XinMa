package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.UCropOptions;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BaseOperateActivity;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.GroupSelectCallBack;
import com.mubly.xinma.databinding.ActivityCreateBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.ICreateView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.CategoryBean;
import com.mubly.xinma.model.CategoryInfoBean;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.PropertyBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.presenter.CreatePresenter;
import com.mubly.xinma.presenter.ImageUrlPersenter;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.DialogUtils;
import com.mubly.xinma.utils.GlideEngine;
import com.mubly.xinma.utils.ImageUtils;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 资产创建
 */
public class CreateActivity extends BaseOperateActivity<CreatePresenter, ICreateView> implements ICreateView {
    ActivityCreateBinding binding = null;
    private OptionsPickerView unitSelectDialog;
    private AssetBean selectAssetBean;
    private int type;//;1编辑2复制
    private String headimg;
    private JSONArray paramArray = new JSONArray();
    private String selectDepart;
    private String selectStaff;
    private String selectCategory;
    private String selectCategoryId;
    private String selectTime;
    private String assetsId;

    @Override
    public void initView() {
        setTitle(R.string.create_name);
        setRightTv("保存");
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
        initCreatNoView();
        if (type == 1) {//编辑
            setTitle("编辑");
            selectTime = selectAssetBean.getPurchaseDate();
            assetsId = selectAssetBean.getAssetID();
            initCopyView();
        } else if (type == 2) {

            selectTime = CommUtil.getCurrentTimeHM();
            binding.createAssetTimeTv.setText(CommUtil.getCurrentTimeHM());
            initCopyView();
        } else {
            selectTime = CommUtil.getCurrentTimeHM();
            binding.createAssetTimeTv.setText(CommUtil.getCurrentTimeHM());
        }
        initUnitSelectData();
    }

    private void initUnitSelectData() {
        Observable.create(new ObservableOnSubscribe<List<PropertyBean>>() {

            @Override
            public void subscribe(ObservableEmitter<List<PropertyBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().propertyBeanDao().getGetAllByCode("Unit"));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PropertyBean>>() {
                    @Override
                    public void accept(List<PropertyBean> propertyBeans) throws Exception {
                        initUnitSelectDialog(propertyBeans);
                    }
                });
    }

    private void initUnitSelectDialog(List<PropertyBean> propertyBeans) {
        unitSelectDialog = DialogUtils.showSelectDialog(this, new DialogUtils.SelectListener() {
            @Override
            public void selected(int index1, int index2, int index3, View v) {
                binding.assetCreateUnit.setText(propertyBeans.get(index1).toString());
            }
        });
        unitSelectDialog.setPicker(propertyBeans);
    }

    private void initCreatNoView() {
        String isAutoNo = AppConfig.isAutoNo.get();
        if (isAutoNo.equals("1"))
            binding.autoCreateNoLayout.setVisibility(View.VISIBLE);
        else
            binding.autoCreateNoLayout.setVisibility(View.GONE);
    }

    private void initCopyView() {
        headimg = selectAssetBean.getHeadimg();
        Glide.with(CreateActivity.this).load(new ImageUrlPersenter().getAssetListUrl(selectAssetBean.getHeadimg()))
                .apply(RequestOptions.centerCropTransform().placeholder(R.mipmap.img_defaut).error(R.mipmap.img_defaut)).into(binding.createAssetImg);
        binding.assetCreateAssetName.setText(selectAssetBean.getAssetName());
        binding.assetCreateAssetNo.setText(selectAssetBean.getAssetNo());
        binding.assetCreateAssetModel.setText(selectAssetBean.getAssetModel());
        binding.assetCreateUnit.setText(selectAssetBean.getUnit());
        binding.assetCreateSupply.setText(selectAssetBean.getSupply());
        binding.assetCreateOriginal.setText(selectAssetBean.getOriginal());
        binding.assetCreateDepreciated.setText(selectAssetBean.getDepreciated());
        binding.assetCreateGuaranteed.setText(selectAssetBean.getGuaranteed());
        binding.assetCreateSeat.setText(selectAssetBean.getSeat());
        binding.createAssetTimeTv.setText(selectAssetBean.getPurchaseDate());
        binding.createAssetCategoryTv.setText(selectAssetBean.getCategory());
        binding.createDepartSelectTv.setText(selectAssetBean.getDepart() + "-" + selectAssetBean.getStaff());
        selectCategory = selectAssetBean.getCategory();
        selectDepart = selectAssetBean.getDepart();
        selectStaff = selectAssetBean.getStaff();
    }

    @Override
    protected CreatePresenter createPresenter() {
        return new CreatePresenter();
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        String assetNo = binding.assetCreateAssetNo.getText().toString();
        String assetName = binding.assetCreateAssetName.getText().toString();
        String assetModel = binding.assetCreateAssetModel.getText().toString();
        String assetUnit = binding.assetCreateUnit.getText().toString();
        String assetSupply = binding.assetCreateSupply.getText().toString();
        String PurchaseDate = selectTime;
        String original = binding.assetCreateOriginal.getText().toString();
        String depreciated = binding.assetCreateDepreciated.getText().toString();
        String guaranteed = binding.assetCreateGuaranteed.getText().toString();
        String Depart = selectDepart;
        String Staff = selectStaff;
        String seat = binding.assetCreateSeat.getText().toString();
        String Category = selectCategory;//资产分类
        String CategoryId = selectCategoryId;//资产分类Id

        if (TextUtils.isEmpty(assetName)) {
            CommUtil.ToastU.showToast("请输入资产名称");
            return;
        }
        mPresenter.createAssets(assetsId, headimg, assetNo, assetName, assetModel, assetUnit, assetSupply, PurchaseDate, original, depreciated, guaranteed, Depart
                , Staff, seat, Category, CategoryId, paramArray.toString(), new CallBack<Boolean>() {
                    @Override
                    public void callBack(Boolean obj) {
                        if (type == 1) {
                            CommUtil.ToastU.showToast("编辑成功");
                        } else {
                            CommUtil.ToastU.showToast("创建成功");
                        }

                        finish();
                    }
                });
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create);
        selectAssetBean = (AssetBean) getIntent().getSerializableExtra("assetsBean");
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void choosePhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .isEnableCrop(true)
                .freeStyleCropEnabled(true)
                .compressQuality(60)
                .isCompress(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .cropWH(100, 100)
                .cropImageWideHigh(100, 100)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        if (null != result && result.size() > 0) {
                            Glide.with(CreateActivity.this).load(result.get(0).getCutPath())
                                    .apply(RequestOptions.centerCropTransform()).into(binding.createAssetImg);
                            mPresenter.imageUpload(ImageUtils.imageToBase64(result.get(0).isCompressed() ? result.get(0).getCompressPath() : result.get(0).getCutPath()), new CallBack<String>() {
                                @Override
                                public void callBack(String obj) {
                                    headimg = obj;
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.assetCreateNoScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CreateActivity.this, ScannerActivity.class), 666);
            }
        });
        binding.createAssetTimeLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeSelectDialog(new CallBack<String>() {
                    @Override
                    public void callBack(String obj) {
                        selectTime = obj;
                        binding.createAssetTimeTv.setText(obj);
                    }
                });
            }
        });
        binding.createAssetCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategorySelectDialog(new CallBack<CategoryBean>() {
                    @Override
                    public void callBack(CategoryBean obj) {
                        selectCategory = obj.getCategory();
                        selectCategoryId = obj.getCategoryID();
                        binding.createAssetCategoryTv.setText(obj.getCategory());
                        mPresenter.getCategoryInfo(selectCategoryId);
                    }
                });
            }
        });
        binding.createDepartSelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroupStaffDialog(new GroupSelectCallBack() {
                    @Override
                    public void callback(GroupBean groupBean, StaffBean staffBean) {
                        selectStaff = staffBean.getStaff();
                        selectDepart = groupBean.getDepart();
                        binding.createDepartSelectTv.setText(groupBean.getDepart() + "-" + staffBean.getStaff());
                    }
                });
            }
        });
        binding.assetCreateUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unitSelectDialog.show();
            }
        });
    }

    @Override
    public void forScanResult(String code) {
        super.forScanResult(code);
        mPresenter.searchCode(code);
        binding.assetCreateAssetNo.setText(code);
    }

    @Override
    public void customeParam() {
        NiceDialog.init().setLayoutId(R.layout.dialog_custom_param_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        EditText paramKey = holder.getView(R.id.dialog_custom_param_key);
                        EditText paramValue = holder.getView(R.id.dialog_custom_param_value);
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    Thread.sleep(300);
                                    CreateActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            CommUtil.showKeyboard(paramKey);
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        holder.getView(R.id.dialog_custom_param_cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        holder.getView(R.id.dialog_custom_param_ack).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String paramKeyStr = paramKey.getText().toString();
                                String paramValueStr = paramValue.getText().toString();
                                if (TextUtils.isEmpty(paramKeyStr)) {
                                    CommUtil.ToastU.showToast("请输入参数名称");
                                    return;
                                }
                                if (TextUtils.isEmpty(paramValueStr)) {
                                    CommUtil.ToastU.showToast("请输入参数内容");
                                    return;
                                }
                                createCustomParam(paramKeyStr, paramValueStr, "Text");
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setMargin(50)
                .setOutCancel(false)
                .show(getSupportFragmentManager());

    }

    @Override
    public void createCustomerParam(List<CategoryInfoBean> categoryInfoBeans) {
        for (CategoryInfoBean categoryInfoBean : categoryInfoBeans) {
            createCustomParam(categoryInfoBean.getInfoName(), categoryInfoBean.getInfoValues(), categoryInfoBean.getInfoType());
        }
    }

    private void createCustomParam(String key, String value, String type) {
        View itemView = View.inflate(CreateActivity.this, R.layout.custom_param_layout, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommUtil.dip2px(50));
        TextView paramKeyTv = itemView.findViewById(R.id.custom_param_key);
        TextView paramValueTv = itemView.findViewById(R.id.custom_param_value);
        paramKeyTv.setText(key);
        paramValueTv.setText(value);
        itemView.setLayoutParams(layoutParams);
        binding.dryParamLayout.addView(itemView);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("InfoName", key);
            jsonObject.put("InfoValue", value);
            jsonObject.put("InfoType", type);
            paramArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isTimeSelectInit() {
        return true;
    }

    @Override
    public boolean showSecond() {
        return true;
    }

    @Override
    public boolean isCategorySelectInit() {
        return true;
    }

    @Override
    public boolean isGroupSelectInit() {
        return true;
    }
}
