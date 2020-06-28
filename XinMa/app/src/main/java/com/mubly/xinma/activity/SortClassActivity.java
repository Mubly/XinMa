package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivitySortClassBinding;
import com.mubly.xinma.iview.ISortClassView;
import com.mubly.xinma.presenter.SortClassPresenter;

public class SortClassActivity extends BaseActivity<SortClassPresenter, ISortClassView> implements ISortClassView {
    ActivitySortClassBinding binding = null;

    @Override
    public void initView() {
        setTitle(R.string.sort_class_name);
        setRightAddBtnEnable(true);
        mPresenter.init();
    }

    @Override
    public void onRightAddEvent(ImageView rightAddBtn) {
        super.onRightAddEvent(rightAddBtn);
        startActivity(CategoryCreateActivity.class);
    }

    @Override
    protected SortClassPresenter createPresenter() {
        return new SortClassPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sort_class);
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.sortClassRv.setLayoutManager(new LinearLayoutManager(this));
        binding.sortClassRv.setAdapter(adapter);
    }

    @Override
    public void toCreate(String categoryID, String categoryName) {
        Intent intent = new Intent(this, CategoryCreateActivity.class);
        intent.putExtra("categoryId", categoryID);
        if (!TextUtils.isEmpty(categoryName)) {
            intent.putExtra("categoryName", categoryName);
        }

        startActivity(intent);
    }

}
