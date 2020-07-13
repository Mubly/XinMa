package com.mubly.xinma.iview;

import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.OperateBean;

public interface IChangeView extends BaseMvpView {
    void showOperateLogInfo(OperateBean operateBean);
    void showOperateInfo(AssetBean assetBean);
}
