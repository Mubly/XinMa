package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.AssetsListPageAdapter;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;

import androidx.fragment.app.FragmentManager;

public interface IAssetListView extends BaseMvpView {
    FragmentManager getFgManager();
    void showPageView(AssetsListPageAdapter pageAdapter);
}
