package com.mubly.xinma.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityReturnBinding;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.presenter.ReturnPresenter;
import com.mubly.xinma.utils.CommUtil;

import static com.mubly.xinma.activity.AssetSelectActivity.BRROROW_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RESULT_OK_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RETURN_REQUEST_CODE;

public class ReturnActivity extends BaseActivity<ReturnPresenter, IReturnView> implements IReturnView {

    ActivityReturnBinding binding = null;
    SelectAssetsBean selectAssetsBean = null;

    @Override
    protected ReturnPresenter createPresenter() {
        return new ReturnPresenter();
    }

    @Override
    public void initView() {
        setTitle(R.string.return_name);
        setRightTv("保存");
        binding.setPresenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        finish();
        CommUtil.ToastU.showToast("保存成功");
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_return);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK_CODE) {
            selectAssetsBean = (SelectAssetsBean) data.getSerializableExtra("selectData");
            mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
        }
    }

    @Override
    public void toSelectAssetsAct() {
        Intent intent = new Intent(this, AssetSelectActivity.class);
        intent.putExtra("status", "1");
        if (null != selectAssetsBean) {
            intent.putExtra("selectedData", selectAssetsBean);
        }
        startActivityForResult(intent, RETURN_REQUEST_CODE);
    }

    @Override
    public void showRv(AssetsListCallBackAdapter adapter) {
        binding.returnRv.setLayoutManager(new LinearLayoutManager(this));
        binding.returnRv.setAdapter(adapter);
    }
}
