package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.databinding.ActivityCategoryCreateBinding;
import com.mubly.xinma.iview.ICategoryCreateView;
import com.mubly.xinma.presenter.CategoryCreatePresenter;
import com.mubly.xinma.utils.CommUtil;

/**
 * 创建类别
 */
public class CategoryCreateActivity extends BaseActivity<CategoryCreatePresenter, ICategoryCreateView> implements ICategoryCreateView {
    ActivityCategoryCreateBinding binding = null;
    String categoryId;
    String categoryName;

    @Override
    public void initView() {
        setTitle("创建分类");
        setRightTv("保存");
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init(categoryId, categoryName);
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        CommUtil.ToastU.ToastMsg(this, "保存成功");
    }

    @Override
    protected CategoryCreatePresenter createPresenter() {
        return new CategoryCreatePresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_create);
        categoryId = getIntent().getStringExtra("categoryId");
        categoryName = getIntent().getStringExtra("categoryName");
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.paramRv.setLayoutManager(new LinearLayoutManager(this));
        binding.paramRv.setAdapter(adapter);
    }

    @Override
    public void openSelect() {
        startActivity(CategoryInfoSelectActivity.class);
    }
}
