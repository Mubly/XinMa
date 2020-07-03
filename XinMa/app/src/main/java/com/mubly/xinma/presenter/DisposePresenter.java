package com.mubly.xinma.presenter;

import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.iview.IDisposeView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class DisposePresenter extends BaseOperatPresenter<IDisposeView> {
    MutableLiveData<String> creatDate = new MutableLiveData<>();
    List<AssetBean> selectDataList = new ArrayList<>();
    AssetsListCallBackAdapter adapter = null;

    public MutableLiveData<String> getCreatDate() {
        return creatDate;
    }

    @Override
    public void init() {
        creatDate.setValue(CommUtil.getCurrentTime());
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
