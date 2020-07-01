package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;

public interface ICheckDetialView extends BaseMvpView {
    void showRv(SmartAdapter adapter);
    void delectSuc();
    void showDelectpromapt();
}
