package com.mubly.xinma.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.formatter.IFillFormatter;
import com.lzy.okgo.OkGo;
import com.mubly.xinma.R;
import com.mubly.xinma.activity.UserAgreeActivity;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.databinding.ActivityRegisterBinding;
import com.mubly.xinma.login.IView.IRegisterView;
import com.mubly.xinma.login.presenter.RegisterPresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.EditViewUtil;
import com.mubly.xinma.utils.TimeUtils;

public class RegisterActivity extends BaseActivity<RegisterPresenter, IRegisterView> implements IRegisterView {
    ActivityRegisterBinding binding = null;
    TimeUtils timeUtils = null;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
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
        binding.registerPhoneCodeGainBtn.setOnClickListener(this);
        binding.registerAck.setOnClickListener(this);
        binding.userAgreement.setOnClickListener(this);
        EditViewUtil.EditDatachangeLister(binding.registerPhoneInputEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj) && obj.length() == 11) {
                    mPresenter.registerPhoneCheck(obj, new CallBack<Boolean>() {
                        @Override
                        public void callBack(Boolean obj) {
                            if (obj) {
                                mPresenter.getPhoneCheck().setValue(true);
                            } else {
                                mPresenter.getPhoneCheck().setValue(false);
                            }
                        }
                    });

                } else {
                    mPresenter.getPhoneCheck().setValue(false);
                }
            }
        });
        EditViewUtil.EditDatachangeLister(binding.registerPasswordInputEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj) && obj.length() > 5) {
                    mPresenter.getPasswordCheck().setValue(true);
                } else {
                    mPresenter.getPasswordCheck().setValue(false);
                }
            }
        });
        EditViewUtil.EditDatachangeLister(binding.registerPassword2InputEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj) && binding.registerPasswordInputEt.getText().toString().equals(obj)) {
                    mPresenter.getPassword2Check().setValue(true);

                } else {
                    mPresenter.getPassword2Check().setValue(false);
                }
            }
        });
        EditViewUtil.EditDatachangeLister(binding.registerCompNameEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj)) {
                    mPresenter.getCompNameCheck().setValue(true);
                } else {
                    mPresenter.getCompNameCheck().setValue(false);
                }
            }
        });
        EditViewUtil.EditDatachangeLister(binding.registerPhoneCodeEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj) && obj.length() > 3) {
                    mPresenter.getPhoneCodeCheck().setValue(true);
                } else {
                    mPresenter.getPhoneCodeCheck().setValue(false);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.register_phone_code_gain_btn:
                if (!mPresenter.getPhoneCheck().getValue()) {
                    CommUtil.ToastU.showToast("请输入手机号码");
                    return;
                }
                String phone = binding.registerPhoneInputEt.getText().toString();
                mPresenter.gainPhoneCode(phone);
                timeUtils.runTimer();
                break;
            case R.id.register_ack:
                String phoneStr = binding.registerPhoneInputEt.getText().toString();
                String phoneCodeStr = binding.registerPhoneCodeEt.getText().toString();
                String passStr = binding.registerPasswordInputEt.getText().toString();
                String companyStr = binding.registerCompNameEt.getText().toString();
                if (mPresenter.registerEnable()) {
                    mPresenter.register(companyStr, phoneStr, phoneCodeStr, passStr);
                } else {
                    CommUtil.ToastU.showToast("请填写完整的注册信息");
                }

                break;
            case R.id.user_agreement:
                startActivity(UserAgreeActivity.class);
                break;
        }
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
    }

    @Override
    public void closeAct() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != timeUtils)
            timeUtils.cancelTimer();
    }
}
