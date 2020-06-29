package com.mubly.xinma.presenter;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.base.ResponseData;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.iview.ISettingView;
import com.mubly.xinma.login.view.LoginActivity;
import com.mubly.xinma.model.UserInfoBean;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.StringUtils;

import androidx.lifecycle.MutableLiveData;

public class SettingPresenter extends BasePresenter<ISettingView> {
    private MutableLiveData<UserInfoBean> userInfo = new MutableLiveData<>();

    public void loginOut() {
        AppConfig.clearAll();
        DataBaseUtils.getInstance().clearAll();
        getMvpView().startActivity(LoginActivity.class);
        getMvpView().closeAllAct();
//        OkGo.<ResponseData>post(URLConstant.EXPERIENCE_LOGIN_OUT_URL)
//                .execute(new JsonCallback<ResponseData>() {
//                    @Override
//                    public void onSuccess(Response<ResponseData> response) {
//                        if (response.body().getCode() == 1) {
//                            AppConfig.clearAll();
//                            DataBaseUtils.getInstance().clearAll();
//                            getMvpView().startActivity(LoginActivity.class);
//                            getMvpView().closeAllAct();
//                        } else
//                            CommUtil.ToastU.showToast(response.body().getMsg());
//                    }
//                });

    }

    public MutableLiveData<UserInfoBean> getUserInfo() {
        return userInfo;
    }

    public void init() {
        UserInfoBean userInfoBean = (UserInfoBean) JSON.parseObject(AppConfig.userInfo.get(), UserInfoBean.class);
        userInfo.setValue(userInfoBean);
    }

    public String getVersionCode() {
        return CommUtil.getPackageName();
    }
}
