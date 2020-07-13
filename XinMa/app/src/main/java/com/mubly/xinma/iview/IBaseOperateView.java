package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.model.OperateBean;

public interface IBaseOperateView extends BaseMvpView {
    void toSelectAssetsAct();
    void showRv(AssetsListCallBackAdapter adapter);
    void showOperateLogInfo(OperateBean operateBean);
}
