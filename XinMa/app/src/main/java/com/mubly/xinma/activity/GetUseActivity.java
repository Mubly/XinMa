package com.mubly.xinma.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListAdapter;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BaseOperateActivity;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.GroupSelectCallBack;
import com.mubly.xinma.databinding.ActivityGetUseBinding;
import com.mubly.xinma.iview.IGetUseVIew;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.presenter.GetUsePresenter;
import com.mubly.xinma.utils.CommUtil;

import static com.mubly.xinma.activity.AssetSelectActivity.GET_USE_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RESULT_OK_CODE;

public class GetUseActivity extends BaseOperateActivity<GetUsePresenter, IGetUseVIew> implements IGetUseVIew {
    ActivityGetUseBinding binding = null;
    SelectAssetsBean selectAssetsBean = null;

    @Override
    public void initView() {
        setTitle(R.string.getuse_name);
        setRightTv("保存");
        binding.setPersent(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
    }

    @Override
    protected GetUsePresenter createPresenter() {
        return new GetUsePresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_use);
    }

    @Override
    public void toSelectAssetsAct() {
        Intent intent = new Intent(this, AssetSelectActivity.class);
        intent.putExtra("status", "1");
        if (null != selectAssetsBean) {
            intent.putExtra("selectedData", selectAssetsBean);
        }
        startActivityForResult(intent, GET_USE_REQUEST_CODE);
    }

    @Override
    public void showRv(AssetsListCallBackAdapter adapter) {
        binding.getUseRv.setLayoutManager(new LinearLayoutManager(this));
        binding.getUseRv.setAdapter(adapter);
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        finish();
        CommUtil.ToastU.showToast("保存成功");
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
    public void initEvent() {
        super.initEvent();
        binding.getUseTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeSelectDialog(new CallBack<String>() {
                    @Override
                    public void callBack(String obj) {
                        binding.getUseTimeTv.setText(obj);
                    }
                });
            }
        });
        binding.getUseDepartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroupStaffDialog(new GroupSelectCallBack() {
                    @Override
                    public void callback(GroupBean groupBean, StaffBean staffBean) {
                        binding.getUseDepartTv.setText(groupBean.getDepart());
                        binding.getUseStaffTv.setText(staffBean.getStaff());
                    }
                });
            }
        });
    }

    @Override
    public boolean isTimeSelectInit() {
        return true;
    }

    @Override
    public boolean isCategorySelectInit() {
        return false;
    }

    @Override
    public boolean isGroupSelectInit() {
        return true;
    }
}
