package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.IAssetListView;
import com.mubly.xinma.presenter.AssetsListPresenter;

/**
 * 资产列表页
 */
public class AssetsListActivity extends BaseActivity<AssetsListPresenter, IAssetListView>implements IAssetListView {

    @Override
    public void initView() {
        setTitle(R.string.assets_name);
    }

    @Override
    protected AssetsListPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this,R.layout.activity_assets_list);
    }
}
