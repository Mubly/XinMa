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
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.databinding.ActivityLoginBinding;
import com.mubly.xinma.login.IView.ILoginView;
import com.mubly.xinma.login.presenter.LoginPresenter;
import com.mubly.xinma.utils.EditViewUtil;

public class LoginActivity extends BaseActivity<LoginPresenter, ILoginView> implements ILoginView {
    ActivityLoginBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    @Override
    public void initView() {
        binding.setPersenter(mPresenter);
        binding.setLifecycleOwner(this);
        setBackBtnEnable(false);
        setTitle(R.string.app_name_title);
        setRightTv("注册");
    }

    @Override
    public void initEvent() {
        super.initEvent();
        EditViewUtil.EditDatachangeLister(binding.passwordInputEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (obj.length() > 6)
                    mPresenter.getPasswordCheck().setValue(true);
                else
                    mPresenter.getPasswordCheck().setValue(false);
            }
        });
        EditViewUtil.EditDatachangeLister(binding.phoneInputEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (obj.length() == 11)
                    mPresenter.getPhoneCheck().setValue(true);
                else
                    mPresenter.getPhoneCheck().setValue(false);
            }
        });
        binding.loginAck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneStr = binding.phoneInputEt.getText().toString();
                String passStr = binding.passwordInputEt.getText().toString();
                if (mPresenter.getPhoneCheck().getValue() && mPresenter.getPasswordCheck().getValue()) {
                    mPresenter.login(phoneStr,passStr);
                }
            }
        });
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        startActivity(RegisterActivity.class);
    }
}
