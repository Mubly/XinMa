package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.IAnaysisView;
import com.mubly.xinma.presenter.AnalysisPresenter;

public class AnalysisActivity extends BaseActivity<AnalysisPresenter, IAnaysisView> implements IAnaysisView {


    @Override
    public void initView() {
        setTitle(R.string.analysis_name);
    }

    @Override
    protected AnalysisPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this, R.layout.activity_analysis);
    }
}
