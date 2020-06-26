package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityDepartMentCreateBinding;
import com.mubly.xinma.iview.IDepartAndStaffCreateView;
import com.mubly.xinma.presenter.DepartAndStaffCreatePresenter;
import com.mubly.xinma.utils.CommUtil;

public class DepartMentCreateActivity extends BaseActivity<DepartAndStaffCreatePresenter, IDepartAndStaffCreateView> implements IDepartAndStaffCreateView {
    ActivityDepartMentCreateBinding binding = null;


    @Override
    public void initView() {
        setRightTv("保存");
        binding.setPresenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.initDeportMentCreate();
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_depart_ment_create);
    }
}
