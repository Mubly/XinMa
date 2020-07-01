package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityAssetsDetialBinding;
import com.mubly.xinma.iview.IAssetsDetialView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetInfoBean;
import com.mubly.xinma.presenter.AssetsDetialPresenter;
import com.mubly.xinma.utils.CommUtil;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

import java.util.List;

/**
 * 资产详情
 */
public class AssetsDetialActivity extends BaseActivity<AssetsDetialPresenter, IAssetsDetialView> implements IAssetsDetialView {
    private AssetBean selectAssetsBean = null;
    private String from, checkId;
    ActivityAssetsDetialBinding binding = null;
    private View checkBottomLayout;
    private TextView normalTv, diffTv, lessTV;

    @Override
    public void initView() {
        setTitle("资产详情");
        if (from.equals("check")) {
            binding.checkDetialPromptLayout.getViewStub().inflate();
            checkBottomLayout = binding.checkDetialBottom.getViewStub().inflate();
            normalTv = checkBottomLayout.findViewById(R.id.check_normal_tv);
            diffTv = checkBottomLayout.findViewById(R.id.check_diff_tv);
            lessTV = checkBottomLayout.findViewById(R.id.check_less_tv);
        }
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init(selectAssetsBean);

    }

    @Override
    public void initEvent() {
        super.initEvent();
     if (from.equals("check")){
         normalTv.setOnClickListener(this);
         diffTv.setOnClickListener(this);
         lessTV.setOnClickListener(this);
     }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.check_normal_tv:
                mPresenter.checkOperate(checkId, "1", null);
                break;
            case R.id.check_diff_tv:
                checkOperate("2");
                break;
            case R.id.check_less_tv:
                checkOperate("3");
                break;
        }
    }

    private void checkOperate(String s) {
        NiceDialog.init().setLayoutId(R.layout.dialog_input_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        EditText inputEt = holder.getView(R.id.dialog_content_tv);
                        holder.setText(R.id.dialog_tittle_tv, "请输入描述");
                        holder.getView(R.id.dialog_promapt_cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        holder.getView(R.id.dialog_promapt_ack).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String remark = inputEt.getText().toString();
                                if (TextUtils.isEmpty(remark)) {
                                    CommUtil.ToastU.showToast("请输入描述");
                                    return;
                                }
                                mPresenter.checkOperate(checkId, s, remark);
                                dialog.dismiss();
                            }
                        });
                    }
                }).setMargin(60)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    @Override
    protected AssetsDetialPresenter createPresenter() {
        return new AssetsDetialPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_assets_detial);
        selectAssetsBean = (AssetBean) getIntent().getSerializableExtra("assetBean");
        from = getIntent().getStringExtra("from");
        checkId = getIntent().getStringExtra("checkId");
    }


    @Override
    public void showCustomParam(List<AssetInfoBean> infoBeans) {
        if (null != infoBeans && infoBeans.size() > 0) {
            for (AssetInfoBean assetInfoBean : infoBeans) {
                View view = View.inflate(this, R.layout.custom_param_layout, null);
                TextView leftTv = view.findViewById(R.id.custom_param_key);
                leftTv.setText(assetInfoBean.getInfoName());
                binding.dryParamLayout.addView(view);
            }
        } else {
            View view = View.inflate(this, R.layout.custom_param_layout, null);
            TextView leftTv = view.findViewById(R.id.custom_param_key);
            leftTv.setText("无");
            binding.dryParamLayout.addView(view);
        }

    }
}
