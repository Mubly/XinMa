package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseMvpView;

public interface IBaseOperateView extends BaseMvpView {
    void toSelectAssetsAct();
    void showRv(AssetsListCallBackAdapter adapter);
}
