package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;

public interface ISortClassView extends BaseMvpView {
    void showRv(SmartAdapter adapter);
    void toCreate(String categoryID);
}
