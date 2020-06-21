package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.IRepairView;
import com.mubly.xinma.presenter.RepairPresenter;

public class RepairActivity extends BaseActivity<RepairPresenter, IRepairView> implements IRepairView {


    @Override
    public void initView() {
        setTitle(R.string.repair_name);
    }

    @Override
    protected RepairPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this, R.layout.activity_repair);
    }
}
