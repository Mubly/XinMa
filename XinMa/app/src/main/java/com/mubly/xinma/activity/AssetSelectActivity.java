package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityAssetSelectBinding;
import com.mubly.xinma.iview.IAssetSelectView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.presenter.AssetSelectPresenter;

import java.util.ArrayList;
import java.util.List;

public class AssetSelectActivity extends BaseActivity<AssetSelectPresenter, IAssetSelectView> implements IAssetSelectView {
    ActivityAssetSelectBinding binding = null;
    public static final int GET_USE_REQUEST_CODE = 1001;//领用
    //    返回码
    public static final int RESULT_OK_CODE = 10010;//数据返回成功
    public static final int RESULT_NULL_CODE = 10011;//无数据返回成功
    ConstraintLayout filterLayout;
    LinearLayout filterContentLayout;
    //记录之前选中的
    private SelectAssetsBean selectBean;
    //资产的状态 默认全部
    private String status = null;

    @Override
    public void initView() {
        setTitle(R.string.assets_name);
        setRightTv("确认");
        mPresenter.init(selectBean, status);
    }

    @Override
    protected AssetSelectPresenter createPresenter() {
        return new AssetSelectPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_asset_select);
        selectBean = (SelectAssetsBean) getIntent().getSerializableExtra("selectedData");
        status = getIntent().getStringExtra("status");
        filterLayout = findViewById(R.id.filter_layout);
        filterContentLayout = findViewById(R.id.filter_content_layout);
        filterLayout.setOnClickListener(this);
        filterContentLayout.setOnClickListener(this);
        binding.sortAssetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.filter_layout:
                filterLayout.setVisibility(View.GONE);
                break;
            case R.id.filter_content_layout:
                break;
            case R.id.sort_asset_btn:
                filterLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        Intent intent = new Intent();
        if (null != mPresenter.getLocalSelectBean()) {
            intent.putExtra("selectData", mPresenter.getLocalSelectBean());
            setResult(RESULT_OK_CODE, intent);
        } else {
            setResult(RESULT_NULL_CODE);
        }
        finish();
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.assetSelectRv.setLayoutManager(new LinearLayoutManager(this));
        binding.assetSelectRv.setAdapter(adapter);
    }
}
