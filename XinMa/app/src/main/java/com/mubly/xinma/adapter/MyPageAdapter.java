package com.mubly.xinma.adapter;

import com.mubly.xinma.fragment.AssetsListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
    private List<String> titleList;
    private List<Fragment> fragmentList = new ArrayList<>();

    public MyPageAdapter(FragmentManager fm, List<String> titleList,List<Fragment>fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList=fragmentList;

    }



    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
