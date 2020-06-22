package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListPageAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityAssetsListBinding;
import com.mubly.xinma.iview.IAssetListView;
import com.mubly.xinma.presenter.AssetsListPresenter;

/**
 * 资产列表页
 */
public class AssetsListActivity extends BaseActivity<AssetsListPresenter, IAssetListView> implements IAssetListView {
    ActivityAssetsListBinding binding = null;

    @Override
    public void initView() {
        setTitle(R.string.assets_name);
        mPresenter.init();
    }

    @Override
    protected AssetsListPresenter createPresenter() {
        return new AssetsListPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_assets_list);
    }


    @Override
    public FragmentManager getFgManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void showPageView(AssetsListPageAdapter pageAdapter) {
        binding.mViewPager.setAdapter(pageAdapter);
        binding.mTabLayout.setupWithViewPager(binding.mViewPager);
    }

}
