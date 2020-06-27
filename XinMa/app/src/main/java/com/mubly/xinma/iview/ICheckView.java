package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.MyPageAdapter;
import com.mubly.xinma.base.BaseMvpView;

import java.util.List;

import androidx.fragment.app.FragmentManager;

public interface ICheckView extends BaseMvpView {
    FragmentManager getFM();
    void showView(MyPageAdapter pageAdapter, List<String> tabStrList);
}
