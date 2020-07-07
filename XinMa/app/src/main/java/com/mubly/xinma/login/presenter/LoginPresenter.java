package com.mubly.xinma.login.presenter;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.base.CrossApp;
import com.mubly.xinma.base.ResponseData;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.home.MainActivity;
import com.mubly.xinma.login.IView.ILoginView;
import com.mubly.xinma.login.view.LostPassWordActivity;
import com.mubly.xinma.login.view.RegisterActivity;
import com.mubly.xinma.model.AssetDataBean;
import com.mubly.xinma.model.CategoryDataBean;
import com.mubly.xinma.model.GroupData;
import com.mubly.xinma.model.ParamBeanData;
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
    public void login(String phone, String pass) {
        CommUtil.ToastU.showToast(CrossApp.get(), "开始登录…");
        OkGo.<ResponseData>post(URLConstant.LOGIN_URL)
                .params("Phone", phone)
                .params("Pass", pass)
                .execute(new JsonCallback<ResponseData>() {
                    @Override
                    public void onSuccess(Response<ResponseData> response) {
                        if (response.body().getCode() == 1) {//登录成功
                            AppConfig.token.put(response.body().getToken());
                            CommUtil.ToastU.showToast(CrossApp.get(), "获取用户信息…");
                            UserInfoData.getUserInfo(new CallBack<UserInfoData>() {
                                @Override
                                public void callBack(UserInfoData obj) {//获取用户信息信息
                                    if (obj.getCode() == 1) {
                                        CommUtil.ToastU.showToast(CrossApp.get(), "获取分类信息…");
                                        AssetDataBean.pullAssetData(new CallBack<AssetDataBean>() {
                                            @Override
                                            public void callBack(AssetDataBean obj) {
                                                ParamBeanData.synData(new CallBack<ParamBeanData>() {
                                                    @Override
                                                    public void callBack(ParamBeanData obj) {
                                                        getMvpView().startActivity(MainActivity.class, true);//跳转首页
                                                    }
                                                });
                                            }
                                        });

                                    } else
                                        CommUtil.ToastU.showToast(obj.getMsg());
                                }
                            });
                        } else {
                            CommUtil.ToastU.showToast(response.body().getMsg());
                        }
                    }
                });
    }

    //体验登录
    public void experienceLogin() {
        CommUtil.ToastU.showToast(CrossApp.get(), "登录中…");
        OkGo.<ResponseData>post(URLConstant.EXPERIENCE_LOGIN_URL)
                .execute(new JsonCallback<ResponseData>() {

                    @Override
                    public void onSuccess(Response<ResponseData> response) {
                        if (response.body().getCode() == 1) {//登录成功
                            AppConfig.token.put(response.body().getToken());//保存Token
                            CommUtil.ToastU.showToast(CrossApp.get(), "获取用户信息…");
                            UserInfoData.getUserInfo(new CallBack<UserInfoData>() {
                                @Override
                                public void callBack(UserInfoData obj) {//获取用户信息
                                    CommUtil.ToastU.showToast(CrossApp.get(), "同步分类…");
                                    CategoryDataBean.getCateGoryData(new CallBack<CategoryDataBean>() {
                                        @Override
                                        public void callBack(CategoryDataBean obj) {//获取分类信息
                                            CommUtil.ToastU.showToast(CrossApp.get(), "同步组织…");
                                            GroupData.getGroupData(new CallBack<GroupData>() {
                                                @Override
                                                public void callBack(GroupData obj) {//获取组织信息
                                                    CommUtil.ToastU.showToast(CrossApp.get(), "同步资产…");
                                                    AssetDataBean.pullAssetData(new CallBack() {
                                                        @Override
                                                        public void callBack(Object obj) {//获取资产
                                                            getMvpView().startActivity(MainActivity.class, true);//跳转首页
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
//
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
