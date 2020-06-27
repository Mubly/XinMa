package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.MyPageAdapter;
import com.mubly.xinma.base.BaseMvpView;

import androidx.fragment.app.FragmentManager;

public interface IAnaysisView extends BaseMvpView {
    FragmentManager getFgMer();

    void showView(MyPageAdapter adapter);
}
