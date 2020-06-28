package com.mubly.xinma.presenter;

import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.ICreateView;
import com.mubly.xinma.iview.IReturnView;

public class CreatePresenter extends BasePresenter<ICreateView> {
    public void choosePhoto() {
        getMvpView().choosePhoto();
    }

    public void init() {
    }

    public void customeParam() {
        getMvpView().customeParam();
    }
}
