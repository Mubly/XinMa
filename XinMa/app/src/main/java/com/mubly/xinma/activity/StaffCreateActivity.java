package com.mubly.xinma.activity;


import android.os.Bundle;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.databinding.ActivityStaffCreateBinding;
import com.mubly.xinma.iview.IDepartAndStaffCreateView;
import com.mubly.xinma.presenter.DepartAndStaffCreatePresenter;
import com.mubly.xinma.utils.CommUtil;

import androidx.databinding.DataBindingUtil;

public class StaffCreateActivity extends BaseActivity<DepartAndStaffCreatePresenter, IDepartAndStaffCreateView> implements IDepartAndStaffCreateView {
    ActivityStaffCreateBinding binding = null;
    private String departName;

    @Override
    public void initView() {
        setRightTv("保存");
        binding.setPresenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.getDepartName().setValue(departName);
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        CommUtil.ToastU.ToastMsg(this, "保存成功");
    }

    @Override
    protected DepartAndStaffCreatePresenter createPresenter() {
        return new DepartAndStaffCreatePresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_staff_create);
        departName = getIntent().getStringExtra("departName");
    }
}
