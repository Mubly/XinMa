package com.mubly.xinma.presenter;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.activity.PrintActivity;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.base.ResponseData;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.iview.ISettingView;
import com.mubly.xinma.login.view.LoginActivity;
import com.mubly.xinma.model.CompanyDataBean;
import com.mubly.xinma.model.UserInfoBean;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.StringUtils;

import androidx.lifecycle.MutableLiveData;

public class SettingPresenter extends BasePresenter<ISettingView> {
    private MutableLiveData<UserInfoBean> userInfo = new MutableLiveData<>();
    private MutableLiveData<String> prnitStatus = new MutableLiveData<>();

    public MutableLiveData<String> getPrnitStatus() {
        return prnitStatus;
    }

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


    public void changeCompanyName(String companyName) {
        CompanyDataBean.changeCompanyName(companyName, new CallBack<BaseModel>() {
            @Override
            public void callBack(BaseModel obj) {
                userInfo.getValue().setCompany(companyName);
                AppConfig.companyInfo.put(JSON.toJSONString(userInfo.getValue()));
            }
        });
    }

    public void changeUserName(String userName) {
        CompanyDataBean.changeUserName(userName, new CallBack<BaseModel>() {
            @Override
            public void callBack(BaseModel obj) {
                userInfo.getValue().setFullName(userName);
                AppConfig.companyInfo.put(JSON.toJSONString(userInfo.getValue()));
            }
        });
    }

    public void changePhoneName(String phoneName) {
    }

    public void toPrintPage() {
        getMvpView().startActivity(PrintActivity.class);
    }
}
