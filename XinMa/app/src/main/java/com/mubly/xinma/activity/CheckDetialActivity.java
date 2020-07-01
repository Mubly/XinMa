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
import com.mubly.xinma.databinding.ActivityCheckDetialBinding;
import com.mubly.xinma.iview.ICheckDetialView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.presenter.CheckDetialPresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.LiveDataBus;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

public class CheckDetialActivity extends BaseActivity<CheckDetialPresenter, ICheckDetialView> implements ICheckDetialView {
    ActivityCheckDetialBinding binding = null;
    private String checkId;
    private String checkTime;


    @Override
    public void initView() {
        setTitle("资产盘点");
        setBackBtnEnable(true);
        setRightBtnResId(R.mipmap.helper_icon);
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.currentTime.setValue(checkTime);
        mPresenter.init();
        mPresenter.initdata(checkId, "0");
        tabSelect(0);
        mPresenter.initTab(checkId);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.waitCheckTv.setOnClickListener(this);
        binding.goodCheckTv.setOnClickListener(this);
        binding.diffCheckTv.setOnClickListener(this);
        binding.lessCheckTv.setOnClickListener(this);
        LiveDataBus.get().with("chekRefresh", Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mPresenter.initdata(checkId, "0");
                tabSelect(0);
                mPresenter.initTab(checkId);
            }
        });
    }

    @Override
    public void onRightAddEvent(ImageView rightAddBtn) {
        super.onRightAddEvent(rightAddBtn);
        NiceDialog.init().setLayoutId(R.layout.dialog_text_promapt)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        holder.getView(R.id.dialog_promapt_ack).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setMargin(60).show(getSupportFragmentManager());
    }

    @Override
    protected CheckDetialPresenter createPresenter() {
        return new CheckDetialPresenter();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.wait_check_tv:
                tabSelect(0);
                mPresenter.initdata(checkId, "0");
                break;
            case R.id.good_check_tv:
                tabSelect(1);
                mPresenter.initdata(checkId, "1");
                break;
            case R.id.diff_check_tv:
                tabSelect(2);
                mPresenter.initdata(checkId, "2");
                break;
            case R.id.less_check_tv:
                tabSelect(3);
                mPresenter.initdata(checkId, "3");
                break;
        }
    }

    private void tabSelect(int i) {
        binding.waitCheckTv.setSelected(false);
        binding.goodCheckTv.setSelected(false);
        binding.diffCheckTv.setSelected(false);
        binding.lessCheckTv.setSelected(false);
        switch (i) {
            case 0:
                binding.waitCheckTv.setSelected(true);
                break;
            case 1:
                binding.goodCheckTv.setSelected(true);
                break;
            case 2:
                binding.diffCheckTv.setSelected(true);
                break;
            case 3:
                binding.lessCheckTv.setSelected(true);
                break;
        }
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_detial);
        checkId = getIntent().getStringExtra("checkId");
        checkTime = getIntent().getStringExtra("checkTime");
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.inventoryRv.setLayoutManager(new LinearLayoutManager(this));
        binding.inventoryRv.setAdapter(adapter);
    }

    @Override
    public void delectSuc() {
        CommUtil.ToastU.showToast("删除成功");
        finish();
    }

    @Override
    public void showDelectpromapt() {
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
                                mPresenter.delectAck();
                            }
                        });
                    }
                }).setMargin(60)
                .show(getSupportFragmentManager());
    }

    @Override
    public void toAssetDesAct(AssetBean assetBean) {
        Intent intent = new Intent(this, AssetsDetialActivity.class);
        intent.putExtra("assetBean", assetBean);
        intent.putExtra("from", "check");
        intent.putExtra("checkId", checkId);
        startActivity(intent);
    }
}
