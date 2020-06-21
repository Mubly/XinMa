package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;

public class EditStaffActivity extends BaseActivity {



    @Override
    public void initView() {
        setTitle("员工编辑");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this,R.layout.activity_edit_staff);
    }
}
