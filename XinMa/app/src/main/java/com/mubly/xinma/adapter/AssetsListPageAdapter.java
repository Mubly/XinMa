package com.mubly.xinma.adapter;

import com.mubly.xinma.fragment.AssetsListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AssetsListPageAdapter extends FragmentPagerAdapter {
    private List<String> titleList;
    private List<AssetsListFragment> listFragments = new ArrayList<>();

    public AssetsListPageAdapter(FragmentManager fm, List<String> titleList) {
        super(fm);
        this.titleList = titleList;
        getFragments();
    }

    private void getFragments() {
        listFragments.clear();
        listFragments.add(AssetsListFragment.getInstance(0));
        listFragments.add(AssetsListFragment.getInstance(1));
        listFragments.add(AssetsListFragment.getInstance(2));
        listFragments.add(AssetsListFragment.getInstance(3));
        listFragments.add(AssetsListFragment.getInstance(4));
        listFragments.add(AssetsListFragment.getInstance(5));
        listFragments.add(AssetsListFragment.getInstance(6));
    }

    @Override
    public Fragment getItem(int position) {

        return listFragments.get(position);
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
