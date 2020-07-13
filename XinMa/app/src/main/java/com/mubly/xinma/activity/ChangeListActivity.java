package com.mubly.xinma.activity;

import android.widget.ImageView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityChangeListBinding;
import com.mubly.xinma.databinding.ActivityOperateLogListBinding;
import com.mubly.xinma.iview.IOperateLogListView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.presenter.ChangeListPresenter;
import com.mubly.xinma.presenter.OperateLogListPresenter;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ChangeListActivity extends BaseActivity<ChangeListPresenter, IOperateLogListView> implements IOperateLogListView {
    ActivityChangeListBinding binding = null;
    private AssetBean assetBean = null;

    @Override
    public void initView() {
        setTitle("价值变更");
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init(assetBean.getAssetID());
        mPresenter.getCurrentPrice().setValue(assetBean.getPrice());
        mPresenter.getOriginalPrice().setValue(assetBean.getOriginal());
    }

    @Override
    protected ChangeListPresenter createPresenter() {
        return new ChangeListPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_list);
        assetBean = (AssetBean) getIntent().getSerializableExtra("assetBean");
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.changeLogRv.setLayoutManager(new LinearLayoutManager(this));
        binding.changeLogRv.setAdapter(adapter);
    }

    @Override
    public void toDesPage(String OperateID, String type) {

    }
}
