package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;

public interface ICategoryCreateView extends BaseMvpView {
    void showRv(SmartAdapter adapter);
    void openSelect();
}
