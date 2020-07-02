package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.IPrintOperateView;
import com.mubly.xinma.presenter.PrintOperatePresenter;

public class PrintOperateActivity extends BaseActivity<PrintOperatePresenter, IPrintOperateView> implements IPrintOperateView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_operate);
    }

    @Override
    public void initView() {

    }

    @Override
    protected PrintOperatePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {

    }
}
