package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.IDeposeView;
import com.mubly.xinma.presenter.DeposePresenter;

/**
 * 处置
 */
public class DisposeActivity extends BaseActivity<DeposePresenter, IDeposeView> implements IDeposeView {


    @Override
    public void initView() {
        setTitle(R.string.dispose_name);
    }

    @Override
    protected DeposePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this, R.layout.activity_dispose);
    }
}
