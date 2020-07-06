package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.databinding.ActivityUserAgreeBinding;
import com.mubly.xinma.utils.CommUtil;

import java.io.IOException;

//用户协议页面
public class UserAgreeActivity extends BaseActivity {
    ActivityUserAgreeBinding binding = null;


    @Override
    public void initView() {
        setBackBtnEnable(true);
        setTitle("用户协议");
        binding.userAgreementContent.setText(CommUtil.readAssetsTxt(this, "provision"));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_agree);
    }
}
