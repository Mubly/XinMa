package com.mubly.xinma.login.presenter;

import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.login.IView.IRegisterView;

import androidx.lifecycle.MutableLiveData;

public class RegisterPresenter extends BasePresenter<IRegisterView> {
    private MutableLiveData<Boolean>phoneCheck=new MutableLiveData<>();
    private MutableLiveData<Boolean>passwordCheck=new MutableLiveData<>();
    private MutableLiveData<Boolean>password2Check=new MutableLiveData<>();
    private MutableLiveData<Boolean>compNameCheck=new MutableLiveData<>();

    public MutableLiveData<Boolean> getPhoneCheck() {
        return phoneCheck;
    }

    public MutableLiveData<Boolean> getPasswordCheck() {
        return passwordCheck;
    }

    public MutableLiveData<Boolean> getPassword2Check() {
        return password2Check;
    }

    public MutableLiveData<Boolean> getCompNameCheck() {
        return compNameCheck;
    }

    public void register() {
    }
}
