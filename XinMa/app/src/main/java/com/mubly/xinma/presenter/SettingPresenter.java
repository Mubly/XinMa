package com.mubly.xinma.presenter;

import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.iview.ISettingView;
import com.mubly.xinma.login.view.LoginActivity;
import com.mubly.xinma.utils.AppConfig;

public class SettingPresenter extends BasePresenter<ISettingView> {
    public void loginOut() {
        AppConfig.clearAll();
        DataBaseUtils.getInstance().clearAll();
        getMvpView().startActivity(LoginActivity.class);
        getMvpView().closeAllAct();
    }
}
