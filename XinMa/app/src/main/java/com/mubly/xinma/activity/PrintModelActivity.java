package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityPrintModelBinding;
import com.mubly.xinma.iview.IprintModelView;
import com.mubly.xinma.presenter.printModelPresenter;

/**
 * 打印模板选择列表
 */
public class PrintModelActivity extends BaseActivity<printModelPresenter, IprintModelView> implements IprintModelView {
    ActivityPrintModelBinding binding = null;

    @Override
    public void initView() {
        setTitle("模板列表");
        setRightTv("选定");
        mPresenter.init(this);
    }

    @Override
    protected printModelPresenter createPresenter() {
        return new printModelPresenter();
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        Intent intent = new Intent();
        intent.putExtra("prnitMode", mPresenter.getSelectBean());
        setResult(10019, intent);
        finish();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_print_model);
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.printModelRv.setLayoutManager(new LinearLayoutManager(this));
        binding.printModelRv.setAdapter(adapter);
    }
}
