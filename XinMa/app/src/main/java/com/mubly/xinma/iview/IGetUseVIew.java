package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.AssetsListAdapter;
import com.mubly.xinma.base.BaseMvpView;

public interface IGetUseVIew extends BaseMvpView {
    void toSelectAssetsAct();
    void showRv(AssetsListAdapter adapter);
}
