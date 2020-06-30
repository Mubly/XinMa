package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.ICheckDetialView;
import com.mubly.xinma.presenter.CheckDetialPresenter;

public class CheckDetialActivity extends BaseActivity<CheckDetialPresenter,ICheckDetialView>implements ICheckDetialView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_detial);
    }

    @Override
    public void initView() {

    }

    @Override
    protected CheckDetialPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {

    }
}
