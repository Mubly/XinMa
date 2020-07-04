package com.mubly.xinma.presenter;

import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetParam;
import com.mubly.xinma.model.OperateData;
import com.mubly.xinma.model.resbean.OperateDataRes;

import java.util.List;

public abstract class BaseOperatPresenter<V extends BaseMvpView> extends BasePresenter<V> {
    public abstract void init();

    public abstract void scanAdd();

    public abstract void manualAdd();

    public abstract void notifyDataChange(List<AssetBean> newDataList);

    //领用操作
    public void operate(String ProcessCate, String ProcessTime, String Depart, String Staff, String Seat, String Remark
            , String AssetID, String Fee, CallBack<OperateDataRes> callBack) {
        OperateData.operate(ProcessCate, ProcessTime, Depart, Staff, Seat, Remark, AssetID, Fee, callBack);
    }
}
