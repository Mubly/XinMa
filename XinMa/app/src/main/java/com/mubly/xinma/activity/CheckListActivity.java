package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.MyPageAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityCheckListBinding;
import com.mubly.xinma.iview.ICheckView;
import com.mubly.xinma.presenter.CheckCreatePresenter;
import com.mubly.xinma.presenter.CheckPresenter;

import java.util.List;

public class CheckListActivity extends BaseActivity<CheckPresenter, ICheckView> implements ICheckView {
    ActivityCheckListBinding binding = null;

    @Override
    public void initView() {
        setTitle(R.string.check_name);
        setRightAddBtnEnable(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.init();
    }

    @Override
    public void onRightAddEvent(ImageView rightAddBtn) {
        super.onRightAddEvent(rightAddBtn);
        startActivity(CheckCreateActivity.class);
    }

    @Override
    protected CheckPresenter createPresenter() {
        return new CheckPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_list);
    }

    @Override
    public FragmentManager getFM() {
        return getSupportFragmentManager();
    }

    @Override
    public void showView(MyPageAdapter pageAdapter, List<String> tabStrList) {
        binding.checkVp.setOffscreenPageLimit(2);
        binding.checkVp.setAdapter(pageAdapter);
        binding.checkTabLayout.removeAllTabs();
        for (int i = 0; i < tabStrList.size(); i++) {
            TabLayout.Tab tab = binding.checkTabLayout.newTab();
            tab.setCustomView(R.layout.tab_check_layout);
            TextView tvTab = (TextView) tab.getCustomView();
            tvTab.setText(tabStrList.get(i));
            binding.checkTabLayout.addTab(tab);
        }
        binding.checkTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.checkVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        binding.checkVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                binding.checkTabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        binding.checkVp.setCurrentItem(0);
    }
}
