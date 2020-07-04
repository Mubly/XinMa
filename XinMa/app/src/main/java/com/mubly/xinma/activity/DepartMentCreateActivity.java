package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityDepartMentCreateBinding;
import com.mubly.xinma.iview.IDepartAndStaffCreateView;
import com.mubly.xinma.presenter.DepartAndStaffCreatePresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.LiveDataBus;

public class DepartMentCreateActivity extends BaseActivity<DepartAndStaffCreatePresenter, IDepartAndStaffCreateView> implements IDepartAndStaffCreateView {
    ActivityDepartMentCreateBinding binding = null;
    private String departId, departName;

    @Override
    public void initView() {
        setRightTv("保存");
        binding.setPresenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.initDeportMentCreate();
        if (!TextUtils.isEmpty(departName)) {
            binding.departmentEditName.setText(departName);
        }
        if (TextUtils.isEmpty(departId)){
            setTitle("创建部门");
        }else {
            setTitle("编辑部门");
        }
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        departName = binding.departmentEditName.getText().toString();
        if (TextUtils.isEmpty(departName)){
            CommUtil.ToastU.showToast("请输入部门名称");
            return;
        }
        mPresenter.ackDepart(departId, departName);
    }

    @Override
    protected DepartAndStaffCreatePresenter createPresenter() {
        return new DepartAndStaffCreatePresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_depart_ment_create);
        departId = getIntent().getStringExtra("departId");
        departName = getIntent().getStringExtra("departName");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LiveDataBus.get().with("refreshGroup").setValue(true);
        LiveDataBus.get().with("DepartMentTitle").setValue(departName);
    }
}
