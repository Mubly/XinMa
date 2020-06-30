package com.mubly.xinma.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.databinding.ActivityCheckCreateBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.ICheckCreateView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.CheckBean;
import com.mubly.xinma.model.CheckData;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.presenter.CheckCreatePresenter;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

import static com.mubly.xinma.activity.AssetSelectActivity.BRROROW_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.CHECK_CREATE_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RESULT_OK_CODE;

public class CheckCreateActivity extends BaseActivity<CheckCreatePresenter, ICheckCreateView> implements ICheckCreateView {
    ActivityCheckCreateBinding binding = null;
    SelectAssetsBean selectAssetsBean = null;

    @Override
    public void initView() {
        setTitle("创建盘点");
        setBackBtnEnable(true);
        setRightTv("保存");
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
    }

    @Override
    protected CheckCreatePresenter createPresenter() {
        return new CheckCreatePresenter();
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        if (null != selectAssetsBean && selectAssetsBean.getSelectBean().size() > 0) {
            CheckData.checkCreate(getParam(), new CallBack<String>() {
                @Override
                public void callBack(String obj) {
                    if (!TextUtils.isEmpty(obj)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CheckBean checkBean = new CheckBean();
                                checkBean.setCheckID(obj);
                                checkBean.setItems(String.valueOf(getParam().size()));
                                checkBean.setCreateTime(mPresenter.currentTime.getValue());
                                checkBean.setStatusName("待盘点");
                                checkBean.setStatus("0");
                                XinMaDatabase.getInstance().checkBeanDao().insert(checkBean);
                                finish();
                            }
                        }).start();
                    }
                }
            });
        } else {
            CommUtil.ToastU.showToast("请选择要盘点的资产");
        }

    }

    private List<String> getParam() {
        List<String> paramList = new ArrayList<>();
        for (AssetBean assetBean : selectAssetsBean.getSelectBean()) {
            paramList.add(assetBean.getAssetID());
        }
        return paramList;
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_create);
    }

    @Override
    public void toSelectAssetsAct() {
        Intent intent = new Intent(this, AssetSelectActivity.class);
        intent.putExtra("status", "1");
        if (null != selectAssetsBean) {
            intent.putExtra("selectedData", selectAssetsBean);
        }
        startActivityForResult(intent, CHECK_CREATE_REQUEST_CODE);
    }

    @Override
    public void showRv(AssetsListCallBackAdapter adapter) {
        binding.checkOrderRv.setLayoutManager(new LinearLayoutManager(this));
        binding.checkOrderRv.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK_CODE) {
            selectAssetsBean = (SelectAssetsBean) data.getSerializableExtra("selectData");
            mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
        }
    }
}
