package com.mubly.xinma.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.databinding.ActivityPrintBinding;
import com.mubly.xinma.model.BlueToothDeviceBean;
import com.mubly.xinma.utils.CommUtil;
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
    private BluetoothClient mBluetoothClient;
    public static final int REQUEST_CODE_BLUETOOTH = 99;
    List<String> deviceAddressRecordList = new ArrayList<>();

    List<String> deviceNameRecordList = new ArrayList<>();
    SmartAdapter<String> adapter = null;

    List<BlueToothDeviceBean> deviceList = new ArrayList<>();
    /**
     * 该值只有在已连接打印机，并且再次进入打印机连接页面有效
     */
    private String curConnectMacAddress;

    @Override
    public void initView() {
        setTitle("蓝牙打印");
        adapter = new SmartAdapter<String>(deviceNameRecordList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_staff_layout;
            }

            @Override
            public void dealView(VH holder, String data, int position) {
                holder.setText(R.id.item_staff_name, data);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };
        binding.printMationRv.setLayoutManager(new LinearLayoutManager(this));
        binding.printMationRv.setAdapter(adapter);
        mBluetoothClient = PrintCenterManager.getInstance().getBluetoothClient();
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
                searchBluetoothDevices();
            }
        });
    }

    private void initBlueTooth() {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            CommUtil.ToastU.showToast("当前设备不支持蓝牙功能！");
            return;
        }

        if (!mBluetoothClient.isBluetoothOpened()) {
            openBlueTooth();
        } else {
            searchBluetoothDevices();
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

    private void searchBluetoothDevices() {
        deviceNameRecordList.clear();
        deviceAddressRecordList.clear();
        deviceList.clear();

        // 先扫BLE设备3次，每次3s
        // 再扫经典蓝牙5s
        // 再扫BLE设备2s

        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(1000, 3)
                .searchBluetoothClassicDevice(3000)
                .searchBluetoothLeDevice(1000)
                .build();

        mBluetoothClient.search(request, new SearchResponse() {

            @Override
            public void onSearchStarted() {
                Log.i("bluesearch", "onSearchStarted");
//                showLoadingDialogWithMsg("正在搜索蓝牙设备...");
            }

            @Override
            public void onDeviceFounded(SearchResult wrapperDevice) {

                long currentThreadId = Thread.currentThread().getId();

                String deviceName = wrapperDevice.getName();

                String deviceAddress = wrapperDevice.getAddress();
                Log.i("bluesearch", "currentThreadId:"+currentThreadId+"   deviceAddress:"+deviceAddress);

//                LogUtils.debugInfo("当前线程id为:" + currentThreadId + "设备名称为：" + wrapperDevice.getName() + "设备地址为：" + wrapperDevice.getAddress() + "是否连接：" + wrapperDevice.device.getBondState());

                if (
                        !TextUtils.isEmpty(deviceName)
                        && !TextUtils.isEmpty(deviceAddress)
                        && !"NULL".equals(deviceName)
                                && !deviceNameRecordList.contains(deviceName)) {

                    if (deviceNameRecordList.size() >= 12) {
                        mBluetoothClient.stopSearch();
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    deviceNameRecordList.add(deviceName);
                    deviceAddressRecordList.add(deviceAddress);
                    deviceList.add(new BlueToothDeviceBean(deviceName,deviceAddress));
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onSearchStopped() {
                Log.i("bluesearch", "onSearchStopped");
            }

            @Override
            public void onSearchCanceled() {
                Log.i("bluesearch", "onSearchCanceled");
            }

        });
    }

}
