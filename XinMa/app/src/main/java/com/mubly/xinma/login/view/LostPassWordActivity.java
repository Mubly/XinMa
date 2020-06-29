package com.mubly.xinma.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.databinding.ActivityLostPassWordBinding;
import com.mubly.xinma.login.IView.ILostPassWordView;
import com.mubly.xinma.login.presenter.LostPassWordPresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.EditViewUtil;
import com.mubly.xinma.utils.TimeUtils;

public class LostPassWordActivity extends BaseActivity<LostPassWordPresenter, ILostPassWordView> implements ILostPassWordView {
    ActivityLostPassWordBinding binding = null;
    TimeUtils timeUtils = null;

    @Override
    protected LostPassWordPresenter createPresenter() {
        return new LostPassWordPresenter();
    }

    @Override
    public void initView() {
        setTitle(R.string.app_name_title);
        binding.setPersenter(mPresenter);
        binding.setLifecycleOwner(this);
        timeUtils = new TimeUtils(binding.registerPhoneCodeGainBtn, "重新发送");
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.forgetPassAck.setOnClickListener(this);
        binding.registerPhoneCodeGainBtn.setOnClickListener(this);
        EditViewUtil.EditDatachangeLister(binding.registerPhoneInputEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj) && obj.length() == 11)
                    mPresenter.getPhoneCheck().setValue(true);
                else
                    mPresenter.getPhoneCheck().setValue(false);
            }
        });
        EditViewUtil.EditDatachangeLister(binding.registerPhoneCodeEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj) && obj.length() > 3)
                    mPresenter.getPhoneCodeCheck().setValue(true);
                else
                    mPresenter.getPhoneCodeCheck().setValue(false);
            }
        });
        EditViewUtil.EditDatachangeLister(binding.registerPasswordInputEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj) && obj.length() > 6)
                    mPresenter.getPasswordCheck().setValue(true);
                else
                    mPresenter.getPasswordCheck().setValue(false);
            }
        });
        EditViewUtil.EditDatachangeLister(binding.registerPassword2InputEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj) && obj.equals(binding.registerPasswordInputEt.getText().toString()))
                    mPresenter.getPassword2Check().setValue(true);
                else
                    mPresenter.getPassword2Check().setValue(false);
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.forget_pass_ack:
                String passStr = binding.registerPasswordInputEt.getText().toString();
                String phoneStr = binding.registerPhoneInputEt.getText().toString();
                String phoneCode = binding.registerPhoneCodeEt.getText().toString();
                if (mPresenter.ackEnable())
                    mPresenter.resetPassWord(phoneStr, phoneCode, passStr);
                break;
            case R.id.register_phone_code_gain_btn:
                String phoneStr1 = binding.registerPhoneInputEt.getText().toString();
                if (!TextUtils.isEmpty(phoneStr1) && phoneStr1.length() == 11) {
                    timeUtils.runTimer();
                    mPresenter.gainPhoneCode(phoneStr1);
                } else {
                    CommUtil.ToastU.showToast("请输入正确的手机号");
                }
                break;
        }
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lost_pass_word);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != timeUtils)
            timeUtils.cancelTimer();
    }

    @Override
    public void closeAct() {
        finish();
    }
}
