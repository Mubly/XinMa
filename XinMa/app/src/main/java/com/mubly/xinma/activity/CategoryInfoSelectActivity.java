package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityCategoryInfoSelectBinding;
import com.mubly.xinma.iview.ICategoryInfoSelectView;
import com.mubly.xinma.presenter.CategoryInfoSelectPresenter;

public class CategoryInfoSelectActivity extends BaseActivity<CategoryInfoSelectPresenter, ICategoryInfoSelectView> implements ICategoryInfoSelectView {

    ActivityCategoryInfoSelectBinding binding = null;

    @Override
    public void initView() {
        setTitle("编辑选择项");
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
    }

    @Override
    protected CategoryInfoSelectPresenter createPresenter() {
        return new CategoryInfoSelectPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_info_select);
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.selectRv.setLayoutManager(new LinearLayoutManager(this));
        binding.selectRv.setAdapter(adapter);
    }
}
