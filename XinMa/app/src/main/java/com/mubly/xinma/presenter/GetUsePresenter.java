package com.mubly.xinma.presenter;

import com.mubly.xinma.adapter.AssetsListAdapter;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.IGetUseVIew;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.SelectAssetsBean;

import java.util.ArrayList;
import java.util.List;

public class GetUsePresenter extends BaseOperatPresenter<IGetUseVIew> {
    SelectAssetsBean selectAssetsBean = null;
    List<AssetBean> selectDataList = new ArrayList<>();
    AssetsListCallBackAdapter adapter = null;

    @Override
    public void init() {
        adapter = new AssetsListCallBackAdapter(selectDataList);
        getMvpView().showRv(adapter);
    }

    @Override
    public void scanAdd() {
    }

    @Override
    public void manualAdd() {
        getMvpView().toSelectAssetsAct();
    }

    @Override
    public void notifyDataChange(List<AssetBean> newDataList) {
        selectDataList.clear();
        selectDataList.addAll(newDataList);
        adapter.notifyDataSetChanged();
    }
}
