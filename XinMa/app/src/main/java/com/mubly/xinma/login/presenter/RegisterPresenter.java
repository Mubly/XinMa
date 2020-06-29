package com.mubly.xinma.login.presenter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.base.ResponseData;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.login.IView.IRegisterView;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;

import androidx.lifecycle.MutableLiveData;

import javax.security.auth.callback.Callback;

public class RegisterPresenter extends BasePresenter<IRegisterView> {
    private MutableLiveData<Boolean> phoneCheck = new MutableLiveData<>();
    private MutableLiveData<Boolean> passwordCheck = new MutableLiveData<>();
    private MutableLiveData<Boolean> password2Check = new MutableLiveData<>();
    private MutableLiveData<Boolean> compNameCheck = new MutableLiveData<>();
    private MutableLiveData<Boolean> phoneCodeCheck = new MutableLiveData<>();

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

    public MutableLiveData<Boolean> getPhoneCodeCheck() {
        return phoneCodeCheck;
    }

    public RegisterPresenter() {
        phoneCheck.setValue(false);
        passwordCheck.setValue(false);
        password2Check.setValue(false);
        compNameCheck.setValue(false);
        phoneCodeCheck.setValue(false);
    }

    public void gainPhoneCode(String phone) {
        OkGo.<ResponseData>post(URLConstant.REGISTER_GAIN_PHONE_CODE_URL)
                .params("Phone", phone)
                .params("LogID", AppConfig.LogID.get())
                .execute(new JsonCallback<ResponseData>() {
                    @Override
                    public void onSuccess(Response<ResponseData> response) {

                    }
                });
    }

    public void register(String company, String phone, String code, String pass) {
        OkGo.<ResponseData>post(URLConstant.REGISTER_URL)
                .params("Company", company)
                .params("Phone", phone)
                .params("Code", code)
                .params("Pass", pass)
                .execute(new JsonCallback<ResponseData>() {
                    @Override
                    public void onSuccess(Response<ResponseData> response) {
                        if (response.body().getCode()==1){
                            AppConfig.token.put(response.body().getToken());
                            CommUtil.ToastU.showToast("注册成功");
                            getMvpView().closeAct();
                        }else {
                            CommUtil.ToastU.showToast(response.body().getMsg());
                        }

                    }
                });
    }

    public void registerPhoneCheck(String phone, CallBack<Boolean> callback) {
        OkGo.<ResponseData>post(URLConstant.CHECK_REPEAT_URL)

                .params("Phone", phone)

                .execute(new JsonCallback<ResponseData>() {
                    @Override
                    public void onSuccess(Response<ResponseData> response) {
                        if (response.body().getCode() == 1) {
                            callback.callBack(true);
                        } else {
                            callback.callBack(false);
                            CommUtil.ToastU.showToast(response.body().getMsg() + "");
                        }
                    }
                });
    }

    public boolean registerEnable() {
        return (phoneCheck.getValue() && password2Check.getValue() && passwordCheck.getValue() && compNameCheck.getValue() && phoneCodeCheck.getValue());
    }
}
