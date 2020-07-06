package com.mubly.xinma.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.view.View;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivitySettingBinding;
import com.mubly.xinma.iview.ISettingView;
import com.mubly.xinma.presenter.SettingPresenter;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity<SettingPresenter, ISettingView> implements ISettingView {
    ActivitySettingBinding binding = null;

    @Override
    public void initView() {
        setTitle("设置");
        binding.setPersenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.userAgreementContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserAgreeActivity.class);
            }
        });
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
    }

    public void toJumpAct(View view) {
        Intent intent = new Intent(this, PrintModelActivity.class);
        startActivity(intent);
    }
}
