package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BaseOperateActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.GroupSelectCallBack;
import com.mubly.xinma.databinding.ActivityChangeBinding;
import com.mubly.xinma.iview.IChangeView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.presenter.ChangePresenter;
import com.mubly.xinma.presenter.CreatePresenter;
import com.mubly.xinma.presenter.ImageUrlPersenter;
import com.mubly.xinma.utils.EditViewUtil;

public class ChangeActivity extends BaseOperateActivity<ChangePresenter, IChangeView> implements IChangeView {
    ActivityChangeBinding binding = null;
    private AssetBean changeAsset;

    @Override
    public void initView() {
        setBackBtnEnable(true);
        setTitle("变更");
        setRightTv("保存");
        mPresenter.init();
        mPresenter.getDepartStaff().setValue(changeAsset.getDepart()+"-"+changeAsset.getStaff());
        binding.setBean(changeAsset);
        binding.setImagePresenter(new ImageUrlPersenter());
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        mPresenter.saveChange(changeAsset);

    }

    @Override
    protected ChangePresenter createPresenter() {
        return new ChangePresenter();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.changeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeSelectDialog(new CallBack<String>() {
                    @Override
                    public void callBack(String obj) {
                        mPresenter.getChangeTime().setValue(obj);
                    }
                });
            }
        });
        binding.departChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroupStaffDialog(new GroupSelectCallBack() {
                    @Override
                    public void callback(GroupBean groupBean, StaffBean staffBean) {
                        mPresenter.getDepartStaff().setValue(groupBean.getDepart() + "-" + staffBean.getStaff());
                        changeAsset.setStaff(staffBean.getStaff());
                        changeAsset.setDepart(groupBean.getDepart());
                    }
                });
            }
        });
        EditViewUtil.EditDatachangeLister(binding.changeNewSeat, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                changeAsset.setSeat(obj);
            }
        });
        EditViewUtil.EditDatachangeLister(binding.changePrice, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                changeAsset.setPrice(obj);
            }
        });
        EditViewUtil.EditDatachangeLister(binding.changetRemainder, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                changeAsset.setRemainder(obj);
            }
        });
        EditViewUtil.EditDatachangeLister(binding.changeRemark, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                changeAsset.setRemark(obj);
            }
        });
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change);
        changeAsset = (AssetBean) getIntent().getSerializableExtra("assetBean");
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
