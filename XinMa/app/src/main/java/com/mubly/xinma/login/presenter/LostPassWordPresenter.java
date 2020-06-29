package com.mubly.xinma.login.presenter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.base.ResponseData;
import com.mubly.xinma.login.IView.ILostPassWordView;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;

import androidx.lifecycle.MutableLiveData;

public class LostPassWordPresenter extends BasePresenter<ILostPassWordView> {
    private MutableLiveData<Boolean> phoneCheck = new MutableLiveData<>();
    private MutableLiveData<Boolean> passwordCheck = new MutableLiveData<>();
    private MutableLiveData<Boolean> password2Check = new MutableLiveData<>();
    private MutableLiveData<Boolean> phoneCodeCheck = new MutableLiveData<>();


    public LostPassWordPresenter() {
        phoneCheck.setValue(false);
        passwordCheck.setValue(false);
        password2Check.setValue(false);
        phoneCodeCheck.setValue(false);
    }

    public MutableLiveData<Boolean> getPhoneCheck() {
        return phoneCheck;
    }

    public MutableLiveData<Boolean> getPasswordCheck() {
        return passwordCheck;
    }

    public MutableLiveData<Boolean> getPassword2Check() {
        return password2Check;
    }

    public MutableLiveData<Boolean> getPhoneCodeCheck() {
        return phoneCodeCheck;
    }

    public void resetPassWord(String phone, String code, String pass) {
        OkGo.<ResponseData>post(URLConstant.FORGET_PASS_URL)
                .params("Phone", phone)
                .params("Code", code)
                .params("Pass", pass)
                .execute(new JsonCallback<ResponseData>() {
                    @Override
                    public void onSuccess(Response<ResponseData> response) {
                        if (response.body().getCode() == 1)
                            getMvpView().closeAct();
                        else
                            CommUtil.ToastU.showToast(response.body().getMsg());
                    }
                });

    }

    public void gainPhoneCode(String phone) {
        OkGo.<ResponseData>post(URLConstant.FORGET_GAIN_PHONE_CODE_URL)
                .params("Phone", phone)
                .params("LogID", AppConfig.LogID.get())
                .execute(new JsonCallback<ResponseData>() {
                    @Override
                    public void onSuccess(Response<ResponseData> response) {
                        if (response.body().getCode() != 1)
                            CommUtil.ToastU.showToast(response.body().getMsg());
                    }
                });
    }

    public boolean ackEnable() {
        return (password2Check.getValue() && passwordCheck.getValue() && phoneCheck.getValue() && phoneCodeCheck.getValue());
    }
}
