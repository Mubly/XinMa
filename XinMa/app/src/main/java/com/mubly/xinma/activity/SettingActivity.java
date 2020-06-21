package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.ISettingView;
import com.mubly.xinma.presenter.SettingPresenter;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity<SettingPresenter, ISettingView>implements ISettingView {


    @Override
    public void initView() {
        setTitle("设置");
    }

    @Override
    protected SettingPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this,R.layout.activity_setting);
    }
}
