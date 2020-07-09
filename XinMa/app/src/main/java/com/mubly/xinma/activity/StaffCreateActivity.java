package com.mubly.xinma.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.databinding.ActivityStaffCreateBinding;
import com.mubly.xinma.iview.IDepartAndStaffCreateView;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.presenter.DepartAndStaffCreatePresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.LiveDataBus;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

import androidx.databinding.DataBindingUtil;

public class StaffCreateActivity extends BaseActivity<DepartAndStaffCreatePresenter, IDepartAndStaffCreateView> implements IDepartAndStaffCreateView {
    ActivityStaffCreateBinding binding = null;
    private String departName;
    private String staff;
    private String staffID;
    private String departId;
    private String postion;
    private String phone;
    private String status = "1";//0离职1在职
    private StaffBean staffBean;

    @Override
    public void initView() {
        setRightTv("保存");
        binding.setPresenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.getDepartName().setValue(departName);
        if (null != staffBean) {
            setTitle("编辑员工");
            binding.staffDeletLayout.setVisibility(View.VISIBLE);
            departName = staffBean.getDepart();
            departId = staffBean.getDepartID();
            staffID = staffBean.getStaffID();
            mPresenter.getDepartName().setValue(staffBean.getDepart());
            binding.staffInfoName.setText(staffBean.getStaff());
            binding.staffInfoPosition.setText(staffBean.getPosition());
            binding.staffInfoPhone.setText(staffBean.getPhone());
            if (staffBean.getStatus().equals("1"))
                binding.staffInfoStatus.setChecked(true);
        }else {
            setTitle("添加员工");
        }
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        staff = binding.staffInfoName.getText().toString();
        postion = binding.staffInfoPosition.getText().toString();
        phone = binding.staffInfoPhone.getText().toString();
        if (TextUtils.isEmpty(staff)) {
            CommUtil.ToastU.showToast("请输入员工姓名");
            return;
        }
        mPresenter.initStaffCreate(departName, departId, staffID, staff, postion, phone, status);
        LiveDataBus.get().with("refreshDepartMent").setValue(true);
    }

    @Override
    protected DepartAndStaffCreatePresenter createPresenter() {
        return new DepartAndStaffCreatePresenter();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.staffInfoStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    status = "1";
                } else {
                    status = "0";
                }
            }
        });
        binding.staffDeletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forDelect();
            }
        });
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_staff_create);
        departName = getIntent().getStringExtra("departName");
        departId = getIntent().getStringExtra("departId");
        staffBean = (StaffBean) getIntent().getSerializableExtra("staffBean");
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
                                LiveDataBus.get().with("refreshDepartMent").setValue(true);
                                mPresenter.delectStaff(staffID);
                            }
                        });
                    }
                }).setMargin(60)
                .show(getSupportFragmentManager());
    }
}
