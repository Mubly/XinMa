package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.UCropOptions;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityCreateBinding;
import com.mubly.xinma.iview.ICreateView;
import com.mubly.xinma.presenter.CreatePresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.GlideEngine;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

import java.util.List;

/**
 * 资产创建
 */
public class CreateActivity extends BaseActivity<CreatePresenter, ICreateView> implements ICreateView {
    ActivityCreateBinding binding = null;

    @Override
    public void initView() {
        setTitle(R.string.create_name);
        setRightTv("保存");
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected CreatePresenter createPresenter() {
        return new CreatePresenter();
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        CommUtil.ToastU.showToast("保存成功");
        finish();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create);
    }

    @Override
    public void choosePhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .isEnableCrop(true)
                .freeStyleCropEnabled(true)
                .imageEngine(GlideEngine.createGlideEngine())
                .cropWH(100, 100)
                .cropImageWideHigh(100, 100)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        if (null != result && result.size() > 0) {
                            Glide.with(CreateActivity.this).load(result.get(0).getCutPath())
                                    .apply(RequestOptions.centerCropTransform()).into(binding.createAssetImg);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    @Override
    public void customeParam() {
        NiceDialog.init().setLayoutId(R.layout.dialog_custom_param_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        EditText paramKey = holder.getView(R.id.dialog_custom_param_key);
                        EditText paramValue = holder.getView(R.id.dialog_custom_param_value);
                        holder.getView(R.id.dialog_custom_param_cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        holder.getView(R.id.dialog_custom_param_ack).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String paramKeyStr = paramKey.getText().toString();
                                String paramValueStr = paramValue.getText().toString();
                                if (TextUtils.isEmpty(paramKeyStr)) {
                                    CommUtil.ToastU.showToast("请输入参数名称");
                                    return;
                                }
                                if (TextUtils.isEmpty(paramValueStr)) {
                                    CommUtil.ToastU.showToast("请输入参数内容");
                                    return;
                                }
                                View itemView = View.inflate(CreateActivity.this, R.layout.custom_param_layout, null);
                                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommUtil.dip2px(40));
                                TextView paramKeyTv = itemView.findViewById(R.id.custom_param_key);
                                TextView paramValueTv = itemView.findViewById(R.id.custom_param_value);
                                paramKeyTv.setText(paramKeyStr);
                                paramValueTv.setText(paramValueStr);
                                itemView.setLayoutParams(layoutParams);
                                binding.dryParamLayout.addView(itemView);
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setMargin(50)
                .setOutCancel(false)
                .show(getSupportFragmentManager());

    }
}
