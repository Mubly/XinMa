package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.IBrrorowView;
import com.mubly.xinma.presenter.BrrorowPresenter;

public class BrrorowActivity extends BaseActivity<BrrorowPresenter, IBrrorowView>implements IBrrorowView {



    @Override
    public void initView() {
        setTitle(R.string.brrorow_name);
    }

    @Override
    protected BrrorowPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this,R.layout.activity_brrorow);
    }
}
