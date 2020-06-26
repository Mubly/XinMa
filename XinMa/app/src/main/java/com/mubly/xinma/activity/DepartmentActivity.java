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
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.databinding.ActivityDepartmentBinding;
import com.mubly.xinma.iview.IDepartmentView;
import com.mubly.xinma.presenter.DepartMentPresenter;
import com.mubly.xinma.utils.CommUtil;

/**
 * 部门详情
 */
public class DepartmentActivity extends BaseActivity<DepartMentPresenter, IDepartmentView> implements IDepartmentView {
    ActivityDepartmentBinding binding = null;
    private String departId;
    private String departName;


    @Override
    public void initView() {
        setTitle(departName);
        setRightAddBtnEnable(true);
        binding.setPersenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init(departId);
    }

    @Override
    public void onRightAddEvent(ImageView rightAddBtn) {
        super.onRightAddEvent(rightAddBtn);
        Intent intent = new Intent(this, StaffCreateActivity.class);
        intent.putExtra("departName", departName);
        startActivity(intent);
    }

    @Override
    protected DepartMentPresenter createPresenter() {
        return new DepartMentPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_department);
        departId = getIntent().getStringExtra("departId");
        departName = getIntent().getStringExtra("departName");
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.departmentRv.setLayoutManager(new LinearLayoutManager(this));
        binding.departmentRv.setAdapter(adapter);
    }
}
