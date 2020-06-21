package com.mubly.xinma.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.databinding.ActivityRegisterBinding;
import com.mubly.xinma.login.IView.IRegisterView;
import com.mubly.xinma.login.presenter.RegisterPresenter;

public class RegisterActivity extends BaseActivity<RegisterPresenter, IRegisterView> implements IRegisterView{
    ActivityRegisterBinding binding = null;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void initView() {
        setTitle(R.string.app_name_title);
        binding.setPersenter(mPresenter);
        binding.setLifecycleOwner(this);
    }
    @Override
    protected void getLayoutId() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_register);
    }

}
