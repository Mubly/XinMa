package com.mubly.xinma.iview;

import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.model.AssetInfoBean;

import java.util.List;

public interface IAssetsDetialView extends BaseMvpView {
    void showCustomParam(List<AssetInfoBean> infoBeans);
}
