package com.mubly.xinma.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BaseOperateActivity;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.GroupSelectCallBack;
import com.mubly.xinma.databinding.ActivityBrrorowBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IBrrorowView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetParam;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.OperateBean;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.model.resbean.OperateDataRes;
import com.mubly.xinma.presenter.BrrorowPresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.EditViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mubly.xinma.activity.AssetSelectActivity.BRROROW_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.GET_USE_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RESULT_OK_CODE;

public class BrrorowActivity extends BaseOperateActivity<BrrorowPresenter, IBrrorowView> implements IBrrorowView {
    ActivityBrrorowBinding binding = null;
    SelectAssetsBean selectAssetsBean = null;

    private String ProcessCate = "借用";
    private String Depart;

    private String Staff;
    private String Seat;

    private String Remark;
    private JSONArray AssetIDList = new JSONArray();
    private AssetBean operaAsset = null;
    private String operateID;

    @Override
    public void initView() {
        setTitle(R.string.brrorow_name);
        setRightTv("保存");
        binding.setPresenter(mPresenter);
        binding.setLifecycleOwner(this);
        if (null != operateID) {
            mPresenter.setHideDelect(true);
        }
        mPresenter.init();
        if (null != operaAsset) {
            initSelectAssetsBean();
            selectAssetsBean.getSelectBean().add(operaAsset);
            mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
        }
        if (null != operateID) {//日志页面进入的
            setRightTvEnable(false);
            hideArrowView();
            binding.bottomLayout.setVisibility(View.GONE);
            binding.brrorowTimeLayou.setEnabled(false);
            binding.brrorowDepartLayout.setEnabled(false);
            binding.brrorowAddressTv.setEnabled(false);
            binding.brrorowReasonTv.setEnabled(false);
            mPresenter.gainOperateData(operateID);
        }
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        if (TextUtils.isEmpty(Depart)) {
            CommUtil.ToastU.showToast("请完善借用信息");
            return;
        }
        if (null == selectAssetsBean || selectAssetsBean.getSelectBean() == null || selectAssetsBean.getSelectBean().size() < 1) {
            CommUtil.ToastU.showToast("请先选择资产");
            return;
        } else {
            AssetIDList = new JSONArray();
            for (AssetBean bean : selectAssetsBean.getSelectBean()) {
                JSONObject object = new JSONObject();
                try {
                    object.put("AssetID", bean.getAssetID());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AssetIDList.put(object);
            }
        }
        mPresenter.operate(ProcessCate, mPresenter.getCreatDate().getValue(), Depart, Staff, Seat, Remark, AssetIDList.toString(), null, new CallBack<OperateDataRes>() {
            @Override
            public void callBack(OperateDataRes obj) {
                CommUtil.ToastU.showToast("借用成功");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (AssetBean bean : selectAssetsBean.getSelectBean()) {
                            bean.setStatusName("借用");
                            bean.setStatus("5");
                            bean.setDepart(Depart);
                            bean.setStaff(Staff);
                            bean.setSeat(Seat);
                            bean.setRemark(Remark);
                            bean.setLastProcessTime(mPresenter.getCreatDate().getValue());
                            XinMaDatabase.getInstance().assetBeanDao().update(bean);
                        }
                        closeCurrentAct();
                    }
                }).start();
            }
        });
    }

    @Override
    protected BrrorowPresenter createPresenter() {
        return new BrrorowPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_brrorow);
        operaAsset = (AssetBean) getIntent().getSerializableExtra("assetBean");
        operateID = getIntent().getStringExtra("operateId");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK_CODE) {
            selectAssetsBean = (SelectAssetsBean) data.getSerializableExtra("selectData");
            mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.brrorowTimeLayou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommUtil.hideKeyboard( binding.brrorowTimeLayou);
                showTimeSelectDialog(new CallBack<String>() {
                    @Override
                    public void callBack(String obj) {
                        mPresenter.getCreatDate().setValue(obj);
                    }
                });
            }
        });
        binding.brrorowDepartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommUtil.hideKeyboard( binding.brrorowDepartLayout);
                showGroupStaffDialog(new GroupSelectCallBack() {
                    @Override
                    public void callback(GroupBean groupBean, StaffBean staffBean) {
                        Depart = groupBean.getDepart();
                        Staff = staffBean.getStaff();
                        binding.brrorowDepartTv.setText(groupBean.getDepart() + "-" + staffBean.getStaff());
//                        binding.brrorowStaffTv.setText(staffBean.getStaff());
                    }
                });
            }
        });
        EditViewUtil.EditDatachangeLister(binding.brrorowAddressTv, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                Seat = obj;
            }
        });
        EditViewUtil.EditDatachangeLister(binding.brrorowReasonTv, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                Remark = obj;
            }
        });
    }

    @Override
    public void toSelectAssetsAct() {
        Intent intent = new Intent(this, AssetSelectActivity.class);
        intent.putExtra("from", BRROROW_REQUEST_CODE);
        if (null != selectAssetsBean) {
            intent.putExtra("selectedData", selectAssetsBean);
        }
        startActivityForResult(intent, BRROROW_REQUEST_CODE);
    }

    @Override
    public void showRv(AssetsListCallBackAdapter adapter) {
        binding.brrorowRv.setLayoutManager(new LinearLayoutManager(this));
        binding.brrorowRv.setAdapter(adapter);
        adapter.setOnDelectListener(new AssetsListCallBackAdapter.OnItemDelectistener() {
            @Override
            public void itemClick(AssetBean data, int index) {
                selectAssetsBean.getSelectBean().remove(data);
                mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
            }
        });
    }

    @Override
    public void showOperateLogInfo(OperateBean operateBean) {
        mPresenter.getCreatDate().setValue(operateBean.getProcessTime());
        binding.brrorowDepartTv.setText(operateBean.getDepart() + "-" + operateBean.getStaff());
        binding.brrorowAddressTv.setText(operateBean.getSeat());
        binding.brrorowReasonTv.setText(operateBean.getRemark());
    }

    @Override
    public void forScanResult(String code) {
        super.forScanResult(code);
        Observable.create(new ObservableOnSubscribe<AssetBean>() {
            @Override
            public void subscribe(ObservableEmitter<AssetBean> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getAssetBeanByNo(code));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AssetBean>() {
                    @Override
                    public void accept(AssetBean assetBean) throws Exception {
                        if (null != assetBean) {
                            initSelectAssetsBean();
                            selectAssetsBean.getSelectBean().add(assetBean);
                            mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
                        } else {
                            CommUtil.ToastU.showToast("查无信息");
                        }

                    }
                });
    }

    private void initSelectAssetsBean() {
        if (null == selectAssetsBean) {
            selectAssetsBean = new SelectAssetsBean();
            List<AssetBean> assetBeans = new ArrayList<>();
            selectAssetsBean.setSelectBean(assetBeans);
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
        return false;
    }

    @Override
    public boolean isGroupSelectInit() {
        return true;
    }

    private void hideArrowView() {
        binding.brrorowArrow1.setVisibility(View.GONE);
        binding.brrorowArrow2.setVisibility(View.GONE);
        binding.brrorowArrow3.setVisibility(View.GONE);
        binding.brrorowArrow4.setVisibility(View.GONE);
        binding.assetListTitle.setText("资产信息");
    }
}
