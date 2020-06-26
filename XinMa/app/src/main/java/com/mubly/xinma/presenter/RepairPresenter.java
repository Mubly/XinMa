package com.mubly.xinma.presenter;

import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.IRepairView;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetBean;

import java.util.ArrayList;
import java.util.List;

public class RepairPresenter extends BaseOperatPresenter<IRepairView> {
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
