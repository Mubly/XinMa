package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.model.AssetBean;

public interface ICheckDetialView extends BaseMvpView {
    void showRv(SmartAdapter adapter);
    void delectSuc();
    void showDelectpromapt();
    void toAssetDesAct(AssetBean assetBean);
}
