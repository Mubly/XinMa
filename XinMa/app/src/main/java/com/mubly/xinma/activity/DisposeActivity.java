package com.mubly.xinma.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSON;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BaseOperateActivity;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.GroupSelectCallBack;
import com.mubly.xinma.databinding.ActivityDisposeBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IDisposeView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetParam;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.model.resbean.OperateDataRes;
import com.mubly.xinma.presenter.DisposePresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.EditViewUtil;

import java.util.ArrayList;
import java.util.List;

import static com.mubly.xinma.activity.AssetSelectActivity.BRROROW_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RESULT_OK_CODE;

/**
 * 处置
 */
public class DisposeActivity extends BaseOperateActivity<DisposePresenter, IDisposeView> implements IDisposeView {
    ActivityDisposeBinding binding = null;
    SelectAssetsBean selectAssetsBean = null;
    private String ProcessCate = "处置";
    private String Depart;

    private String Staff;
    private String Seat;
    private String fee;
    private String Remark;
    private List<AssetParam> AssetIDList = new ArrayList<>();

    @Override
    public void initView() {
        setTitle(R.string.dispose_name);
        setRightTv("保存");
        binding.setPresenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        if (TextUtils.isEmpty(Depart)) {
            CommUtil.ToastU.showToast("请添加领用部门");
            return;
        }
        if (null == selectAssetsBean || selectAssetsBean.getSelectBean() == null || selectAssetsBean.getSelectBean().size() < 1) {
            CommUtil.ToastU.showToast("请添加要领用的资产");
            return;
        } else {
            for (AssetBean bean : selectAssetsBean.getSelectBean()) {
                AssetIDList.add(new AssetParam(bean.getAssetID()));
            }
        }
        mPresenter.operate(ProcessCate, mPresenter.getCreatDate().getValue(), Depart, Staff, Seat, Remark, AssetIDList, fee, new CallBack<OperateDataRes>() {
            @Override
            public void callBack(OperateDataRes obj) {
                CommUtil.ToastU.showToast("领用成功");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (AssetBean bean : selectAssetsBean.getSelectBean()) {
                            bean.setStatusName("维修");
                            bean.setStatus("6");
                            bean.setDepart(Depart);
                            bean.setStaff(Staff);
                            bean.setSeat(Seat);
                            bean.setRemark(Remark);
                            bean.setLastProcessTime(mPresenter.getCreatDate().getValue());
                            XinMaDatabase.getInstance().assetBeanDao().update(bean);
                        }
                        closeAllAct();
                    }
                }).start();
            }
        });
    }

    @Override
    protected DisposePresenter createPresenter() {
        return new DisposePresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dispose);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.disposeTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeSelectDialog(new CallBack<String>() {
                    @Override
                    public void callBack(String obj) {
                        mPresenter.getCreatDate().setValue(obj);
                    }
                });
            }
        });
        binding.disposeDepartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroupStaffDialog(new GroupSelectCallBack() {
                    @Override
                    public void callback(GroupBean groupBean, StaffBean staffBean) {
                        Depart = groupBean.getDepart();
                        Staff = staffBean.getStaff();
                        binding.disposeDepartTv.setText(groupBean.getDepart());
                        binding.disposeStaffTv.setText(staffBean.getStaff());
                    }
                });
            }
        });
        EditViewUtil.EditDatachangeLister(binding.disposeFeeTv, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                fee = obj;
            }
        });
        EditViewUtil.EditDatachangeLister(binding.disposeReasonEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                Remark = obj;
            }
        });
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
    public void toSelectAssetsAct() {
        Intent intent = new Intent(this, AssetSelectActivity.class);
        intent.putExtra("status", "1");
        if (null != selectAssetsBean) {
            intent.putExtra("selectedData", selectAssetsBean);
        }
        startActivityForResult(intent, BRROROW_REQUEST_CODE);
    }

    @Override
    public void showRv(AssetsListCallBackAdapter adapter) {
        binding.disposeRv.setLayoutManager(new LinearLayoutManager(this));
        binding.disposeRv.setAdapter(adapter);
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
}
