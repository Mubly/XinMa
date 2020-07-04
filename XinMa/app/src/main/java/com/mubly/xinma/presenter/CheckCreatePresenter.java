package com.mubly.xinma.presenter;

import com.mubly.xinma.activity.ScannerActivity;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.ICheckCreateView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class CheckCreatePresenter extends BaseOperatPresenter<ICheckCreateView> {
    public MutableLiveData<String> currentTime = new MutableLiveData<>();
    List<AssetBean> selectDataList = new ArrayList<>();
    AssetsListCallBackAdapter adapter = null;

    public MutableLiveData<String> getCurrentTime() {
        return currentTime;
    }

    @Override
    public void init() {
        adapter = new AssetsListCallBackAdapter(selectDataList);
        getMvpView().showRv(adapter);
    }

    public CheckCreatePresenter() {
        currentTime.setValue(CommUtil.getCurrentTime());
    }

    @Override
    public void scanAdd() {
        getMvpView().startActivityForResult(ScannerActivity.class, 666);
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
