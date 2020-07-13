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
import com.mubly.xinma.model.AssetInfoBean;
import com.mubly.xinma.model.CategoryBean;
import com.mubly.xinma.model.CategoryInfoBean;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.PropertyBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.model.resbean.AssetsCreateRes;
import com.mubly.xinma.presenter.CreatePresenter;
import com.mubly.xinma.presenter.ImageUrlPersenter;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.DialogUtils;
import com.mubly.xinma.utils.EditViewUtil;
import com.mubly.xinma.utils.GlideEngine;
import com.mubly.xinma.utils.ImageUtils;
import com.mubly.xinma.utils.StringUtils;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String isAutoNo;

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
        } else if (type == 2) {//复制
            selectTime = CommUtil.getCurrentTimeYMD();
            binding.createAssetTimeTv.setText(CommUtil.getCurrentTimeYMD());
            initCopyView();
        } else {
            selectTime = CommUtil.getCurrentTimeYMD();
            binding.createAssetTimeTv.setText(CommUtil.getCurrentTimeYMD());
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
        isAutoNo = AppConfig.isAutoNo.get();
        if (isAutoNo.equals("0"))//显示-不自动生成
            binding.autoCreateNoLayout.setVisibility(View.VISIBLE);
        else
            binding.autoCreateNoLayout.setVisibility(View.GONE);
    }

    private void initCopyView() {
        headimg = selectAssetBean.getHeadimg();
        Glide.with(CreateActivity.this).load(new ImageUrlPersenter().getAssetListUrl(selectAssetBean.getHeadimg()))
                .apply(RequestOptions.centerCropTransform().placeholder(R.mipmap.img_defaut).error(R.mipmap.img_defaut)).into(binding.createAssetImg);
        binding.assetCreateAssetName.setText(selectAssetBean.getAssetName());
        if (isAutoNo.equals("0") && type == 1) {//编辑，自动填充
            binding.assetCreateAssetNo.setText(selectAssetBean.getAssetNo());
        }
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
        initCustomParam();
    }

    private void initCustomParam() {
        Observable.create(new ObservableOnSubscribe<List<AssetInfoBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AssetInfoBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().assetInfoBeanDao().getAllByAssetsId(selectAssetBean.getAssetID()));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetInfoBean>>() {
                    @Override
                    public void accept(List<AssetInfoBean> infoBeans) throws Exception {
                        for (AssetInfoBean categoryInfoBean : infoBeans) {
                            categoryArrList.add(categoryInfoBean.getCategoryInfoID());//暂时以这个作为标识，其实只是判别是否为null
                            createCustomParam(categoryInfoBean.getInfoName(), categoryInfoBean.getInfoValue(), categoryInfoBean.getInfoType());
                        }
                    }
                });
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
        String status = "1";
        String statusName = "闲置";
        if (type == 1 && null != selectAssetBean) {
            status = selectAssetBean.getStatus();
            statusName = selectAssetBean.getStatusName();
        }

        if (TextUtils.isEmpty(assetName)) {
            CommUtil.ToastU.showToast("请输入资产名称");
            return;
        }

        if (null != AppConfig.isAutoNo.get() && AppConfig.isAutoNo.get().equals("0") && TextUtils.isEmpty(assetNo)) {
            CommUtil.ToastU.showToast("请输入资产编码");
            return;
        }
        gainCustomParam();
        mPresenter.createAssets(assetsId, headimg, assetNo, assetName, assetModel, assetUnit, assetSupply, PurchaseDate, original, depreciated, guaranteed, Depart
                , Staff, seat, Category, CategoryId, paramArray.toString(), status, statusName, new CallBack<AssetsCreateRes>() {
                    @Override
                    public void callBack(AssetsCreateRes obj) {
                        saveCategoryInfoData(obj.getAssetID());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (type == 1) {
                                    CommUtil.ToastU.showToast("编辑成功");
                                } else {
                                    CommUtil.ToastU.showToast("创建成功");
                                }
                                finish();
                            }
                        });

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
                .freeStyleCropEnabled(false)
                .isDragFrame(false)
                .withAspectRatio(1, 1)
                .minimumCompressSize(300)
                .cropImageWideHigh(640, 640)
                .compressQuality(90)
                .isCompress(true)
                .imageEngine(GlideEngine.createGlideEngine())
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
                CommUtil.hideKeyboard(binding.createAssetTimeLabel);
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
                CommUtil.hideKeyboard(binding.createAssetTimeLabel);
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
                CommUtil.hideKeyboard(binding.createAssetTimeLabel);
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
                CommUtil.hideKeyboard(binding.createAssetTimeLabel);
                unitSelectDialog.show();
            }
        });
        binding.assetCreateGuaranteed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String eGuaranteed = binding.assetCreateGuaranteed.getText().toString();
                double dGuaranteed = Double.valueOf(TextUtils.isEmpty(eGuaranteed) ? "0.00" : eGuaranteed);
                if (b) {
                    if (dGuaranteed == 0) {
                        binding.assetCreateGuaranteed.setText("");
                    }
                } else {
                    if (TextUtils.isEmpty(eGuaranteed)) {
                        binding.assetCreateGuaranteed.setText("0.00");
                    }
                }
            }
        });
        binding.assetCreateDepreciated.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String eGuaranteed = binding.assetCreateDepreciated.getText().toString();
                double dGuaranteed = Double.valueOf(TextUtils.isEmpty(eGuaranteed) ? "0.00" : eGuaranteed);
                if (b) {
                    if (dGuaranteed == 0) {
                        binding.assetCreateDepreciated.setText("");
                    }
                } else {
                    if (TextUtils.isEmpty(eGuaranteed)) {
                        binding.assetCreateDepreciated.setText("0.00");
                    }
                }
            }
        });
        binding.assetCreateOriginal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String eGuaranteed = binding.assetCreateOriginal.getText().toString();
                double dGuaranteed = Double.valueOf(TextUtils.isEmpty(eGuaranteed) ? "0.00" : eGuaranteed);
                if (b) {
                    if (dGuaranteed == 0) {
                        binding.assetCreateOriginal.setText("");
                    }
                } else {
                    if (TextUtils.isEmpty(eGuaranteed)) {
                        binding.assetCreateOriginal.setText("0.00");
                    }
                }
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
                                categoryArrList.add("null");
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
        if (categoryArrList.size() > 0) {
            for (int i = (categoryArrList.size() - 1); i >= 0; i--) {//清除之前的
                if (!categoryArrList.get(i).equals("null")) {
                    categoryArrList.remove(i);
                    valueArrList.remove(i);
                    typeArrList.remove(i);
                    binding.dryParamLayout.removeViewAt(i);
                }
            }
        }

        for (CategoryInfoBean categoryInfoBean : categoryInfoBeans) {
            categoryArrList.add(categoryInfoBean.getCategoryID());
            createCustomParam(categoryInfoBean.getInfoName(), categoryInfoBean.getInfoValues(), categoryInfoBean.getInfoType());
        }
    }

    private void createCustomParam(String key, String value, String type) {
        View itemView = View.inflate(CreateActivity.this, R.layout.custom_param_layout, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommUtil.dip2px(50));
        TextView paramKeyTv = itemView.findViewById(R.id.custom_param_key);
        EditText paramValueTv = itemView.findViewById(R.id.custom_param_value);
        paramKeyTv.setText(key);
        if (value.contains("#")) {
            String[] valueSpl = value.split("#");
            paramValueTv.setText(valueSpl[0]);
        } else {
            paramValueTv.setText(value);
        }
        if (type.equals("Select") || type.equals("Date")) {
            paramValueTv.setFocusableInTouchMode(false);
            paramValueTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type.equals("Date")) {
                        showTimeSelectDialog(new CallBack<String>() {
                            @Override
                            public void callBack(String obj) {
                                paramValueTv.setText(obj);
                            }
                        });
                    } else if (type.equals("Select")) {
                        setSelectCategoryParam(paramValueTv, value);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type.equals("Date")) {
                        showTimeSelectDialog(new CallBack<String>() {
                            @Override
                            public void callBack(String obj) {
                                paramValueTv.setText(obj);
                            }
                        });
                    } else if (type.equals("Select")) {
                        setSelectCategoryParam(paramValueTv, value);
                    }
                }
            });
        }
        itemView.setLayoutParams(layoutParams);
        binding.dryParamLayout.addView(itemView);
        valueArrList.add(itemView);
        typeArrList.add(type);
    }

    private void setSelectCategoryParam(EditText edtView, String valueStr) {
        if (TextUtils.isEmpty(valueStr)) return;
        List<String> paramList = new ArrayList<>();

        if (valueStr.contains("#")) {
            String[] paramArr = valueStr.split("#");
            for (int i = 0; i < paramArr.length; i++) {
                paramList.add(paramArr[i]);
            }
        } else {
            paramList.add(valueStr);
        }

        OptionsPickerView categoryParamDialog = DialogUtils.showSelectDialog(CreateActivity.this, new DialogUtils.SelectListener() {
            @Override
            public void selected(int index1, int index2, int index3, View v) {
                edtView.setText(paramList.get(index1));
            }
        });
        categoryParamDialog.setPicker(paramList);

        categoryParamDialog.show();
    }

    private void gainCustomParam() {
        for (int i = 0; i < valueArrList.size(); i++) {
            View view = valueArrList.get(i);
            TextView paramKeyTv = view.findViewById(R.id.custom_param_key);
            EditText paramValueTv = view.findViewById(R.id.custom_param_value);
            if (!TextUtils.isEmpty(paramValueTv.getText().toString())) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("InfoName", paramKeyTv.getText().toString());
                    jsonObject.put("InfoValue", paramValueTv.getText().toString());
                    jsonObject.put("InfoType", typeArrList.get(i));
                    paramArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void saveCategoryInfoData(String assetsId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < valueArrList.size(); i++) {
                    View view = valueArrList.get(i);
                    TextView paramKeyTv = view.findViewById(R.id.custom_param_key);
                    EditText paramValueTv = view.findViewById(R.id.custom_param_value);
                    if (!TextUtils.isEmpty(paramValueTv.getText().toString())) {
                        AssetInfoBean localInfoBean = XinMaDatabase.getInstance().assetInfoBeanDao().getLocalAssetInfo(assetsId, paramKeyTv.getText().toString());
                        AssetInfoBean categoryInfoBean = new AssetInfoBean();
                        categoryInfoBean.setInfoName(paramKeyTv.getText().toString());
                        categoryInfoBean.setAssetID(assetsId);
                        categoryInfoBean.setInfoType(typeArrList.get(i));
                        categoryInfoBean.setInfoValue(paramValueTv.getText().toString());
                        if (null != localInfoBean) {
                            categoryInfoBean.setAssetInfoID(localInfoBean.getAssetInfoID());
                        } else {
                            categoryInfoBean.setAssetInfoID("xm" + String.valueOf(System.currentTimeMillis()));
                        }
                        XinMaDatabase.getInstance().assetInfoBeanDao().insert(categoryInfoBean);
                    }
                }
            }
        }).start();
    }

    private List<View> valueArrList = new ArrayList<>();
    private List<String> typeArrList = new ArrayList<>();
    private List<String> categoryArrList = new ArrayList<>();

    @Override
    public boolean isTimeSelectInit() {
        return true;
    }

    @Override
    public boolean showSecond() {
        return false;
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
