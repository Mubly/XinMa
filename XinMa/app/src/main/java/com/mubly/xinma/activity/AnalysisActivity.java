package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.MyPageAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityAnalysisBinding;
import com.mubly.xinma.iview.IAnaysisView;
import com.mubly.xinma.presenter.AnalysisPresenter;

/**
 * 分析报表
 */
public class AnalysisActivity extends BaseActivity<AnalysisPresenter, IAnaysisView> implements IAnaysisView {

    ActivityAnalysisBinding binding = null;

    @Override
    public void initView() {
        setTitle(R.string.analysis_name);
        mPresenter.init();
    }

    @Override
    protected AnalysisPresenter createPresenter() {
        return new AnalysisPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_analysis);
    }

    @Override
    public FragmentManager getFgMer() {
        return getSupportFragmentManager();
    }

    @Override
    public void showView(MyPageAdapter adapter) {
        binding.analysisVp.setAdapter(adapter);
        binding.analysisTabLayout.setupWithViewPager(binding.analysisVp);
    }
}
