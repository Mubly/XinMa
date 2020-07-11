package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseMvpView;

public interface IPrintOperateView extends BaseMvpView {
    void showRv(AssetsListCallBackAdapter adapter);

    void toSelectAssetsAct();

}
