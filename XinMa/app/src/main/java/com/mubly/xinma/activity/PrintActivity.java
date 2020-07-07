package com.mubly.xinma.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dothantech.printer.IDzPrinter;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.databinding.ActivityPrintBinding;
import com.mubly.xinma.model.BlueToothDeviceBean;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.LiveDataBus;
import com.mubly.xinma.utils.PrintCenterManager;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PrintActivity extends BaseActivity {
    ActivityPrintBinding binding = null;
    public static final int REQUEST_CODE_BLUETOOTH = 99;
    SmartAdapter<IDzPrinter.PrinterAddress> adapter = null;

    private List<IDzPrinter.PrinterAddress> pairedPrinters = new ArrayList<IDzPrinter.PrinterAddress>();

    @Override
    public void initView() {
        setTitle("蓝牙打印");
        adapter = new SmartAdapter<IDzPrinter.PrinterAddress>(pairedPrinters) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_staff_layout;
            }

            @Override
            public void dealView(VH holder, IDzPrinter.PrinterAddress data, int position) {
                holder.setText(R.id.item_staff_name, data.shownName);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PrintCenterManager.getInstance().connectingPrint(data, new CallBack() {
                            @Override
                            public void callBack(Object obj) {
                                binding.currentPrint.setText(data.shownName + " " + "连接中…");
                                showProgress("打印机连接中");
                            }
                        });
                    }
                });
            }
        };
        binding.printMationRv.setLayoutManager(new LinearLayoutManager(this));
        binding.printMationRv.setAdapter(adapter);
        if (PrintCenterManager.getInstance().isPrinterConnected()) {
            binding.currentPrint.setText(PrintCenterManager.getInstance().getCurrentPrint().shownName);
        } else {
            binding.currentPrint.setText("无");
        }
        initBlueTooth();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_print);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                initBlueTooth();
            }
        });
        LiveDataBus.get().with("printConnect", Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                hideProgress();
                if (aBoolean) {
                    binding.currentPrint.setText(PrintCenterManager.getInstance().getCurrentPrint().shownName);
                    CommUtil.ToastU.showToast("打印机连接成功");
                } else {
                    CommUtil.ToastU.showToast("打印机连接失败,请确认设备是否打开");
                }
            }
        });
    }

    private void initBlueTooth() {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            CommUtil.ToastU.showToast("当前设备不支持蓝牙功能！");
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            openBlueTooth();
        } else {
            pairedPrinters.clear();
            pairedPrinters.addAll(PrintCenterManager.getInstance().getAllPrint());
            adapter.notifyDataSetChanged();
            if (pairedPrinters.size() < 1) {
                binding.printEmpty.setVisibility(View.VISIBLE);
            } else {
                binding.printEmpty.setVisibility(View.GONE);
            }
        }

    }

    private void openBlueTooth() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, REQUEST_CODE_BLUETOOTH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            initBlueTooth();
        } else {
            NiceDialog.init().setLayoutId(R.layout.dialog_text_chose_promapt)
                    .setConvertListener(new ViewConvertListener() {
                        @Override
                        protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                            holder.setText(R.id.dialog_tittle_tv, "温馨提示");
                            holder.setText(R.id.dialog_content_tv, "要连接蓝牙打印机，请打开蓝牙并保持蓝牙处于连接状态。关闭蓝牙将无法使用打印功能");
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
                                    openBlueTooth();
                                }
                            });
                        }
                    }).setMargin(60)
                    .setOutCancel(false)
                    .show(getSupportFragmentManager());
        }
    }

}
