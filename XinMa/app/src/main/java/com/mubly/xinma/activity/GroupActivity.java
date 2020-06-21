package com.mubly.xinma.activity;

import android.widget.ImageView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.databinding.ActivityGroupBinding;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.iview.IGroupView;
import com.mubly.xinma.presenter.GroupPresenter;
import com.mubly.xinma.utils.CommUtil;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class GroupActivity extends BaseActivity<GroupPresenter, IGroupView> implements IGroupView {
    ActivityGroupBinding binding = null;

    @Override
    protected GroupPresenter createPresenter() {
        return new GroupPresenter();
    }

    @Override
    public void initView() {
        setTitle(R.string.group_name);
        setRightAddBtnEnable(true);
        mPresenter.init();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group);
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.groupRv.setLayoutManager(new LinearLayoutManager(this));
        binding.groupRv.setAdapter(adapter);
    }

    @Override
    public void onRightAddEvent(ImageView rightAddBtn) {
        super.onRightAddEvent(rightAddBtn);
        CommUtil.ToastU.showToast("添加成功");
    }
}
