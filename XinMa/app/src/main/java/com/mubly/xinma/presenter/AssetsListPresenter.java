package com.mubly.xinma.presenter;

import com.lzy.okgo.db.DBUtils;
import com.mubly.xinma.adapter.AssetsListPageAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IAssetListView;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetBean;

import java.util.ArrayList;
import java.util.List;

public class AssetsListPresenter extends BasePresenter<IAssetListView> {
    List<AssetBean> allAssetBeanList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    AssetsListPageAdapter pageAdapter = null;

    public void init() {
        titleList.add("全部");
        titleList.add("闲置");
        titleList.add("在用");
        titleList.add("借用");
        titleList.add("维修");
        titleList.add("废弃");
        pageAdapter = new AssetsListPageAdapter(getMvpView().getFgManager(), titleList);
        getMvpView().showPageView(pageAdapter);
    }
}
