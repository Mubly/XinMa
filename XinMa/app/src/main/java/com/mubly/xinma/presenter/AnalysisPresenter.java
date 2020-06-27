package com.mubly.xinma.presenter;

import com.mubly.xinma.adapter.MyPageAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.fragment.AnalysisTableFragment;
import com.mubly.xinma.iview.IAnaysisView;
import com.mubly.xinma.iview.IReturnView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class AnalysisPresenter extends BasePresenter<IAnaysisView> {
    MyPageAdapter adapter = null;
    private List<String> tabList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    public void init() {
        tabList.add("资产总数(条)");
        tabList.add("资产总价(元)");
        fragmentList.add(AnalysisTableFragment.getInstance(0));
        fragmentList.add(AnalysisTableFragment.getInstance(1));
        adapter = new MyPageAdapter(getMvpView().getFgMer(), tabList, fragmentList);
        getMvpView().showView(adapter);
    }
}
