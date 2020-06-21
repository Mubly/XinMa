package com.mubly.xinma.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityLostPassWordBinding;
import com.mubly.xinma.login.IView.ILostPassWordView;
import com.mubly.xinma.login.presenter.LostPassWordPresenter;

public class LostPassWordActivity extends BaseActivity<LostPassWordPresenter, ILostPassWordView> implements ILostPassWordView{
    ActivityLostPassWordBinding binding = null;

    @Override
    protected LostPassWordPresenter createPresenter() {
        return new LostPassWordPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        setTitle(R.string.app_name_title);
        binding.setPersenter(mPresenter);
        binding.setLifecycleOwner(this);
    }
    @Override
    protected void getLayoutId() {
//        View view= LayoutInflater.from(this).inflate(R.layout.activity_lost_pass_word,rootView);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_lost_pass_word);
    }
}
