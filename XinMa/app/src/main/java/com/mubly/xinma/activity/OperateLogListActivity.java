package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityOperateLogListBinding;
import com.mubly.xinma.iview.IOperateLogListView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.ProcessBean;
import com.mubly.xinma.presenter.OperateLogListPresenter;
import com.mubly.xinma.utils.CommUtil;

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

    @Override
    public void toDesPage(String OperateID, String type) {
        if (type.equals("维修")) {
            toDes(RepairActivity.class, OperateID);
        } else if (type.equals("领用")) {
            toDes(GetUseActivity.class, OperateID);
        } else if (type.equals("盘点")) {
            toDes(CheckLogDesActivity.class, OperateID);
        } else if (type.equals("变更")) {
            toDes(ChangeActivity.class, OperateID);
        } else if (type.equals("借用")) {
            toDes(BrrorowActivity.class, OperateID);
        } else if (type.equals("归还")) {
            toDes(ReturnActivity.class, OperateID);
        } else if (type.equals("处置")) {
            toDes(DisposeActivity.class, OperateID);
        }
    }

    @Override
    public void toChangeView(ProcessBean processBean) {
        Intent intent = new Intent(this, ChangeActivity.class);
        intent.putExtra("processBean", processBean);
        startActivity(intent);
        startPage();
    }

    private void toDes(Class<?> className, String operateId) {
        if (TextUtils.isEmpty(operateId)) {
            CommUtil.ToastU.showToast("operateId为null");
            return;
        }
        Intent intent = new Intent(this, className);
        intent.putExtra("operateId", operateId);
        startActivity(intent);
        startPage();
    }
}
