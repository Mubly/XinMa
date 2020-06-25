package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListPageAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityAssetsListBinding;
import com.mubly.xinma.iview.IAssetListView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.presenter.AssetsListPresenter;

import java.util.ArrayList;
import java.util.List;

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
        binding.mViewPager.setOffscreenPageLimit(5);
        binding.mViewPager.setAdapter(pageAdapter);
        binding.mTabLayout.setupWithViewPager(binding.mViewPager);
    }

    public List<AssetBean> getAllAssetBeanList() {
        return mPresenter.getAllAssetBeanList();
    }

    public void refreshTab(int index, String count) {
        binding.mTabLayout.getTabAt(index).setText(count);
    }

    public void clickEvent(View view) {
        refreshTab(2, "闲置(33 )");
    }
}