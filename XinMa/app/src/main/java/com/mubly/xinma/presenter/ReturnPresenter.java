package com.mubly.xinma.presenter;

import com.mubly.xinma.activity.ScannerActivity;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class ReturnPresenter extends BaseOperatPresenter<IReturnView> {
    List<AssetBean> selectDataList = new ArrayList<>();
    AssetsListCallBackAdapter adapter = null;
    MutableLiveData<String> creatDate = new MutableLiveData<>();

    public MutableLiveData<String> getCreatDate() {
        return creatDate;
    }

    @Override
    public void init() {
        creatDate.setValue(CommUtil.getCurrentTimeYMD());
        adapter = new AssetsListCallBackAdapter(selectDataList);
        getMvpView().showRv(adapter);
    }

    @Override
    public void scanAdd() {
        getMvpView().startActivityForResult(ScannerActivity.class,666);
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
