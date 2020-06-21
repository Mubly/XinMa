package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.ICreateView;
import com.mubly.xinma.presenter.CreatePresenter;

public class CreateActivity extends BaseActivity<CreatePresenter, ICreateView>implements ICreateView {


    @Override
    public void initView() {
        setTitle(R.string.create_name);
    }

    @Override
    protected CreatePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this,R.layout.activity_create);
    }
}
