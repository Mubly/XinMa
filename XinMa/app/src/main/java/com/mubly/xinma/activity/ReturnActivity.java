package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityReturnBinding;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.presenter.ReturnPresenter;

public class ReturnActivity extends BaseActivity<ReturnPresenter, IReturnView> implements IReturnView {

    ActivityReturnBinding binding = null;

    @Override
    protected ReturnPresenter createPresenter() {
        return new ReturnPresenter();
    }

    @Override
    public void initView() {
        setTitle(R.string.return_name);
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_return);
    }
}
