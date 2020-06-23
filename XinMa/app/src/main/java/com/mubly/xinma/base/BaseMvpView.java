package com.mubly.xinma.base;

import android.app.ProgressDialog;

public interface BaseMvpView {
    void showProgress(String msg);
    ProgressDialog getProgressDialog();
    void hideProgress();
    void checkNetCode(int code, String msg);

    void startActivity(Class<?>act);
    void startActivity(Class<?>act,boolean closeAct);
    void closeAllAct();
}
