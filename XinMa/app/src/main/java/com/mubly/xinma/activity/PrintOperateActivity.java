package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.work.Data;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityPrintOperateBinding;
import com.mubly.xinma.iview.IPrintOperateView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.presenter.PrintOperatePresenter;

public class PrintOperateActivity extends BaseActivity<PrintOperatePresenter, IPrintOperateView> implements IPrintOperateView {
    private AssetBean assetsBean = null;
    ActivityPrintOperateBinding binding = null;

    @Override
    public void initView() {
        setTitle("模板");
    }

    @Override
    protected PrintOperatePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_print_operate);
        assetsBean = (AssetBean) getIntent().getSerializableExtra("assetsBean");
    }
}
