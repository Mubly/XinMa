package com.mubly.xinma.activity;

import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityDisposeBinding;
import com.mubly.xinma.iview.IDisposeView;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.presenter.DisposePresenter;
import com.mubly.xinma.utils.CommUtil;

import static com.mubly.xinma.activity.AssetSelectActivity.BRROROW_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RESULT_OK_CODE;

/**
 * 处置
 */
public class DisposeActivity extends BaseActivity<DisposePresenter, IDisposeView> implements IDisposeView {
    ActivityDisposeBinding binding = null;
    SelectAssetsBean selectAssetsBean = null;
    @Override
    public void initView() {
        setTitle(R.string.dispose_name);
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
    protected DisposePresenter createPresenter() {
        return new DisposePresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dispose);
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
        startActivityForResult(intent, BRROROW_REQUEST_CODE);
    }

    @Override
    public void showRv(AssetsListCallBackAdapter adapter) {
        binding.disposeRv.setLayoutManager(new LinearLayoutManager(this));
        binding.disposeRv.setAdapter(adapter);
    }
}
