package com.mubly.xinma.presenter;

import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.model.AssetBean;

import java.util.List;

public abstract class BaseOperatPresenter<V extends BaseMvpView> extends BasePresenter<V> {
    public abstract void init();

    public abstract void scanAdd();

    public abstract void manualAdd();

    public abstract void notifyDataChange(List<AssetBean> newDataList);
}
