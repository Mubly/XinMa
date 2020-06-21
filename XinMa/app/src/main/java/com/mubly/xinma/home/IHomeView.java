package com.mubly.xinma.home;

import com.mubly.xinma.adapter.HomeMenuAdapter;
import com.mubly.xinma.base.BaseMvpView;

public interface IHomeView extends BaseMvpView {
    void showMenu(HomeMenuAdapter adapter);
}
