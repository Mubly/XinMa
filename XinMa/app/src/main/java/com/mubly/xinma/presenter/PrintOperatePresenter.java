package com.mubly.xinma.presenter;

import com.mubly.xinma.activity.PrintActivity;
import com.mubly.xinma.activity.ScannerActivity;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.IPrintOperateView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class PrintOperatePresenter extends BasePresenter<IPrintOperateView> {
    List<AssetBean> selectDataList = new ArrayList<>();
    AssetsListCallBackAdapter adapter = null;
    private MutableLiveData<String> printStatus = new MutableLiveData<>();

    public MutableLiveData<String> getPrnitStatus() {
        return printStatus;
    }

    public void init() {
        adapter = new AssetsListCallBackAdapter(selectDataList,false);
        getMvpView().showRv(adapter);
    }

    public void scanAdd() {
        getMvpView().startActivityForResult(ScannerActivity.class, 666);
    }

    public void manualAdd() {
        getMvpView().toSelectAssetsAct();
    }

    public void notifyDataChange(List<AssetBean> newDataList) {
        selectDataList.clear();
        selectDataList.addAll(newDataList);
        adapter.notifyDataSetChanged();
    }

    public void toConnectPrnit() {
        getMvpView().startActivity(PrintActivity.class);
    }
}
