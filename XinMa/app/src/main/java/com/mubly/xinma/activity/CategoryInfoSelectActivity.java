package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityCategoryInfoSelectBinding;
import com.mubly.xinma.iview.ICategoryInfoSelectView;
import com.mubly.xinma.presenter.CategoryInfoSelectPresenter;
import com.mubly.xinma.utils.LiveDataBus;

public class CategoryInfoSelectActivity extends BaseActivity<CategoryInfoSelectPresenter, ICategoryInfoSelectView> implements ICategoryInfoSelectView {

    ActivityCategoryInfoSelectBinding binding = null;
    private String selectParam;

    @Override
    public void initView() {
        setTitle("编辑选择项");
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
        if (!TextUtils.isEmpty(selectParam))
            mPresenter.refreshData(selectParam);
    }

    @Override
    protected CategoryInfoSelectPresenter createPresenter() {
        return new CategoryInfoSelectPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_info_select);
        selectParam = getIntent().getStringExtra("selectParam");
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.selectRv.setLayoutManager(new LinearLayoutManager(this));
        binding.selectRv.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LiveDataBus.get().with("selectParam").setValue(mPresenter.getSelectParam());
    }
}
