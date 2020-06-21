package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.presenter.ReturnPresenter;

public class AssetsReturnActivity extends BaseActivity<ReturnPresenter, IReturnView>implements IReturnView {

    @Override
    public void initView() {
        setTitle(R.string.return_name);
    }

    @Override
    protected ReturnPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this,R.layout.activity_assets_return);
    }
}
