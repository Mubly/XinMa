package com.mubly.xinma.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivitySettingBinding;
import com.mubly.xinma.iview.ISettingView;
import com.mubly.xinma.presenter.SettingPresenter;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.PrintCenterManager;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

import java.util.logging.Handler;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity<SettingPresenter, ISettingView> implements ISettingView {
    ActivitySettingBinding binding = null;
    private int editIndex;

    @Override
    public void initView() {
        setTitle("设置");
        mPresenter.init();
        binding.setPersenter(mPresenter);
        binding.setLifecycleOwner(this);

    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.autoCreateNoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mPresenter.getUserInfo().getValue().setIsAutoNo(1);
                    AppConfig.isAutoNo.put("1");
                } else {
                    mPresenter.getUserInfo().getValue().setIsAutoNo(0);
                    AppConfig.isAutoNo.put("0");
                }

                AppConfig.userInfo.put(JSON.toJSONString(mPresenter.getUserInfo().getValue()));
            }
        });
        binding.userAgreementContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserAgreeActivity.class);
            }
        });
        binding.changeCompanyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editIndex = 1;
                showPromat();
            }
        });
        binding.changeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editIndex = 2;
                showPromat();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getPrnitStatus().setValue(PrintCenterManager.getInstance().isPrinterConnected() ? PrintCenterManager.getInstance().getCurrentPrint().shownName : "未连接");
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
    }


    private void showPromat() {
        NiceDialog.init().setLayoutId(R.layout.dialog_text_chose_promapt)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        holder.setText(R.id.dialog_tittle_tv, "提示");
                        if (editIndex == 1)
                            holder.setText(R.id.dialog_content_tv, "确认修改主题名称？");
                        if (editIndex == 2)
                            holder.setText(R.id.dialog_content_tv, "确认修改姓名？");
                        if (editIndex == 3)
                            holder.setText(R.id.dialog_content_tv, "确认电话号码？");
                        holder.getView(R.id.dialog_promapt_cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        holder.getView(R.id.dialog_promapt_ack).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                getEditContentDialog();
                            }
                        });
                    }
                }).setMargin(60)
                .show(getSupportFragmentManager());
    }

    private void getEditContentDialog() {
        NiceDialog.init().setLayoutId(R.layout.dialog_one_input_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        holder.setText(R.id.input_one_title, "确认");

                        EditText inputEdt = holder.getView(R.id.input_one_et);
                        if (editIndex == 1)
                            inputEdt.setHint("请输入主题名称");
                        if (editIndex == 2)
                            inputEdt.setHint("请输入姓名");
                        if (editIndex == 3)
                            inputEdt.setHint("请输入电话号码");
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    Thread.sleep(300);
                                    SettingActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            CommUtil.showKeyboard(inputEdt);
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        holder.getView(R.id.input_one_cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        holder.getView(R.id.input_one_ack).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String data = inputEdt.getText().toString();
                                if (TextUtils.isEmpty(data)) {
                                    CommUtil.ToastU.showToast("请输入要修改的信息");
                                    return;
                                }
                                dialog.dismiss();
                                if (editIndex == 1) {
                                    mPresenter.changeCompanyName(data);
                                } else if (editIndex == 2) {
                                    mPresenter.changeUserName(data);
                                }
                            }
                        });
                    }
                }).setMargin(60)
                .show(getSupportFragmentManager());
    }
}
