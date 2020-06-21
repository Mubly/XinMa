package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.IAssetsDetialView;
import com.mubly.xinma.presenter.AssetsDetialPresenter;

/**
 * 资产详情
 */
public class AssetsDetialActivity extends BaseActivity<AssetsDetialPresenter, IAssetsDetialView> implements IAssetsDetialView {

    @Override
    public void initView() {
        setTitle("资产详情");
    }

    @Override
    protected AssetsDetialPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this, R.layout.activity_assets_detial);
    }
}
