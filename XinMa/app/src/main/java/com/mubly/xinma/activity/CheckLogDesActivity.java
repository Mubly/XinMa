package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.databinding.ActivityCheckLogDesBinding;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.OperateData;
import com.mubly.xinma.model.ProcessBean;
import com.mubly.xinma.presenter.ImageUrlPersenter;

public class CheckLogDesActivity extends BaseActivity {
    ActivityCheckLogDesBinding binding = null;
    private ProcessBean processBean = null;
    AssetBean assetBean = null;

    @Override
    public void initView() {
        binding.setBean(assetBean);
        binding.setImagePresenter(new ImageUrlPersenter());
        binding.setLifecycleOwner(this);
        setTitle("盘点详情");
        binding.checkDesRemark.setText(processBean.getRemark());
        binding.checkDesStatus.setText(processBean.getStatusName());
        binding.checkDesTime.setText(processBean.getProcessTime());
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_log_des);
        processBean = (ProcessBean) getIntent().getSerializableExtra("processBean");
        assetBean = (AssetBean) getIntent().getSerializableExtra("assetBean");
    }
}
