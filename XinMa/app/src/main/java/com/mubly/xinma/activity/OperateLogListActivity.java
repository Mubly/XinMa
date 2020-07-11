package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityOperateLogListBinding;
import com.mubly.xinma.iview.IOperateLogListView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.presenter.OperateLogListPresenter;

public class OperateLogListActivity extends BaseActivity<OperateLogListPresenter, IOperateLogListView> implements IOperateLogListView {
    ActivityOperateLogListBinding binding = null;
    private AssetBean assetBean = null;

    @Override
    public void initView() {
        setTitle("日志");
        mPresenter.init(assetBean.getAssetID());
        setRightBtnResId(R.drawable.wallet_white_icon);
    }

    @Override
    public void onRightAddEvent(ImageView rightAddBtn) {
        super.onRightAddEvent(rightAddBtn);
        Intent intent = new Intent(this, ChangeListActivity.class);
        intent.putExtra("assetBean", assetBean);
        startActivity(intent);
        startPage();
    }

    @Override
    protected OperateLogListPresenter createPresenter() {
        return new OperateLogListPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_operate_log_list);
        assetBean = (AssetBean) getIntent().getSerializableExtra("assetBean");
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.operateLogRv.setLayoutManager(new LinearLayoutManager(this));
        binding.operateLogRv.setAdapter(adapter);
    }
}
