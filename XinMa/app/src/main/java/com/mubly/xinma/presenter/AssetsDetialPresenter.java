package com.mubly.xinma.presenter;

import androidx.lifecycle.MutableLiveData;

import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.IAssetsDetialView;

public class AssetsDetialPresenter extends BasePresenter<IAssetsDetialView> {
    private MutableLiveData<String> currentStatus = new MutableLiveData<>();

    public MutableLiveData<String> getCurrentStatus() {
        return currentStatus;
    }
}
