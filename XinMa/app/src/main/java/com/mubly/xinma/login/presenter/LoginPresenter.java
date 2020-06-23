package com.mubly.xinma.login.presenter;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.base.ResponseData;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.home.MainActivity;
import com.mubly.xinma.login.IView.ILoginView;
import com.mubly.xinma.login.view.LostPassWordActivity;
import com.mubly.xinma.login.view.RegisterActivity;
import com.mubly.xinma.model.AssetDataBean;
import com.mubly.xinma.model.UserInfoData;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class LoginPresenter extends BasePresenter<ILoginView> {
    private MutableLiveData<Boolean> phoneCheck = new MutableLiveData<>();
    private MutableLiveData<Boolean> passwordCheck = new MutableLiveData<>();

    public MutableLiveData<Boolean> getPhoneCheck() {
        return phoneCheck;
    }

    public MutableLiveData<Boolean> getPasswordCheck() {
        return passwordCheck;
    }

    //立即登录
    public void login() {
        getMvpView().startActivity(MainActivity.class);
    }

    //体验登录
    public void experienceLogin() {
        OkGo.<ResponseData>post(URLConstant.EXPERIENCE_LOGIN_URL)
                .execute(new JsonCallback<ResponseData>() {

                    @Override
                    public void onSuccess(Response<ResponseData> response) {
                        if (response.body().getCode() == 1) {
                            AppConfig.token.put(response.body().getToken());
                            AssetDataBean.pullAssetData(new CallBack() {
                                @Override
                                public void callBack(Object obj) {
                                    UserInfoData.getUserInfo(new CallBack<UserInfoData>() {
                                        @Override
                                        public void callBack(UserInfoData obj) {
                                            getMvpView().startActivity(MainActivity.class);
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
    }

    //注册
    public void register() {
        getMvpView().startActivity(RegisterActivity.class);
    }

    //    忘记密码
    public void lostPassWord() {
        getMvpView().startActivity(LostPassWordActivity.class);
    }
}
