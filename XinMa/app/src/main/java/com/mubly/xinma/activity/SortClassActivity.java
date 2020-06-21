package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.ISortClassView;
import com.mubly.xinma.presenter.SortClassPresenter;

public class SortClassActivity extends BaseActivity<SortClassPresenter, ISortClassView>implements ISortClassView {


    @Override
    public void initView() {
        setTitle(R.string.sort_class_name);
    }

    @Override
    protected SortClassPresenter createPresenter() {
        return new SortClassPresenter();
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this,R.layout.activity_sort_class);
    }
}
