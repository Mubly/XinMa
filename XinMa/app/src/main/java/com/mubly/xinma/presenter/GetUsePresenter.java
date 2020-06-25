package com.mubly.xinma.presenter;

import com.mubly.xinma.adapter.AssetsListAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.IGetUseVIew;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.SelectAssetsBean;

import java.util.ArrayList;
import java.util.List;

public class GetUsePresenter extends BasePresenter<IGetUseVIew> {
    SelectAssetsBean selectAssetsBean = null;
    List<AssetBean> selectDataList = new ArrayList<>();
    AssetsListAdapter adapter = null;

    public void init() {
        adapter = new AssetsListAdapter(selectDataList);
        getMvpView().showRv(adapter);
    }

    public void scanAdd() {
    }

    public void manualAdd() {
        getMvpView().toSelectAssetsAct();
    }

    public void notifyDataChange(List<AssetBean> newDataList) {
        selectDataList.clear();
        selectDataList.addAll(newDataList);
        adapter.notifyDataSetChanged();
    }
}
