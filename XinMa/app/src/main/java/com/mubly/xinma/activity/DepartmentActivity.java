package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.databinding.ActivityDepartmentBinding;
import com.mubly.xinma.iview.IDepartmentView;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.presenter.DepartMentPresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.LiveDataBus;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

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
        mPresenter.init(departId, departName);
    }

    @Override
    public void onRightAddEvent(ImageView rightAddBtn) {
        super.onRightAddEvent(rightAddBtn);
        Intent intent = new Intent(this, StaffCreateActivity.class);
        intent.putExtra("departName", departName);
        intent.putExtra("departId", departId);
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

    @Override
    public void editDepartMent() {
        Intent intent = new Intent(this, DepartMentCreateActivity.class);
        intent.putExtra("departName", departName);
        intent.putExtra("departId", departId);
        startActivity(intent);
    }

    @Override
    public void deletDepartMent() {
        closeCurrentAct();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.departmentDeletTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forDelect();
            }
        });
        LiveDataBus.get().with("refreshDepartMent", Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mPresenter.initData(departId);
            }
        });
        LiveDataBus.get().with("DepartMentTitle", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                setTitle(s);
            }
        });
    }

    @Override
    public void toStaffInfo(StaffBean staffBean) {
        Intent intent = new Intent(this, StaffCreateActivity.class);
        intent.putExtra("staffBean", staffBean);
        startActivity(intent);
    }


    private void forDelect() {
        NiceDialog.init().setLayoutId(R.layout.dialog_text_chose_promapt)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        holder.setText(R.id.dialog_tittle_tv, "提示");
                        holder.setText(R.id.dialog_content_tv, "确定删除？");
                        holder.getView(R.id.dialog_promapt_cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        holder.getView(R.id.dialog_promapt_ack).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                mPresenter.delectDepartMent();
                            }
                        });
                    }
                }).setMargin(60)
                .show(getSupportFragmentManager());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LiveDataBus.get().with("refreshGroup").postValue(true);
    }
}
