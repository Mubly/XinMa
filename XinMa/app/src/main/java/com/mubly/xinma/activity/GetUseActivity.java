package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.IGetUseVIew;
import com.mubly.xinma.presenter.GetUsePresenter;

public class GetUseActivity extends BaseActivity<GetUsePresenter, IGetUseVIew>implements IGetUseVIew {


    @Override
    public void initView() {
        setTitle(R.string.getuse_name);
    }

    @Override
    protected GetUsePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this,R.layout.activity_get_use);
    }
}
