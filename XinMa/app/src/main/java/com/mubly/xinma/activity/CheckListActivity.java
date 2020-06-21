package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.iview.ICheckView;
import com.mubly.xinma.presenter.CheckPresenter;

public class CheckListActivity extends BaseActivity<CheckPresenter, ICheckView> implements ICheckView {

    @Override
    public void initView() {
        setTitle(R.string.check_name);
    }

    @Override
    protected CheckPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        DataBindingUtil.setContentView(this, R.layout.activity_check_list);
    }
}
