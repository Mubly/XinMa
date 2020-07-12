package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.databinding.ActivityChangePhoneBinding;
import com.mubly.xinma.login.presenter.LostPassWordPresenter;
import com.mubly.xinma.model.CompanyDataBean;
import com.mubly.xinma.model.UserInfoBean;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.EditViewUtil;
import com.mubly.xinma.utils.LiveDataBus;
import com.mubly.xinma.utils.TimeUtils;

public class ChangePhoneActivity extends BaseActivity {
    ActivityChangePhoneBinding binding = null;
    TimeUtils timeUtils = null;

    @Override
    public void initView() {
        setTitle("修改手机号码");
        timeUtils = new TimeUtils(binding.registerPhoneCodeGainBtn, "重新发送");
    }


    @Override
    public void initEvent() {
        super.initEvent();
        EditViewUtil.EditDatachangeLister(binding.registerPhoneInputEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (!TextUtils.isEmpty(obj) && obj.length() == 11)
                    binding.registerPhoneCheckIv.setVisibility(View.VISIBLE);
                else
                    binding.registerPhoneCheckIv.setVisibility(View.GONE);
            }
        });
        binding.registerPhoneCodeGainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = binding.registerPhoneInputEt.getText().toString();
                if (phone.length() == 11) {
                    timeUtils.runTimer();
                    getPhoneCode(phone);
                }

            }
        });
        binding.forgetPassAck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.registerPhoneCodeEt.getText().toString();
                String phone = binding.registerPhoneInputEt.getText().toString();
                if (code.length() == 4 && phone.length() == 11) {
                    changePhone(phone, code);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != timeUtils) {
            timeUtils.cancelTimer();
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_phone);
    }

    private void changePhone(String phone, String code) {
        CompanyDataBean.changePhoneNo(phone, code, new CallBack<BaseModel>() {
            @Override
            public void callBack(BaseModel obj) {
                if (obj.getCode() == 1) {
                    UserInfoBean userInfoBean = (UserInfoBean) JSON.parseObject(AppConfig.userInfo.get(), UserInfoBean.class);
                    userInfoBean.setPhone(phone);
                    AppConfig.userInfo.put(JSON.toJSONString(userInfoBean));
                    LiveDataBus.get().with("refreshPhone").setValue(phone);
                    ChangePhoneActivity.this.finish();
                } else {
                    checkNetCode(obj.getCode(), obj.getMsg());
                }
            }
        });
    }

    private void getPhoneCode(String phone) {

        new LostPassWordPresenter().gainPhoneCode(phone);
    }
}
