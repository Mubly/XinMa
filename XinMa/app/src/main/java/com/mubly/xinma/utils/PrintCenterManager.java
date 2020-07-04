package com.mubly.xinma.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.inuker.bluetooth.library.BluetoothClient;
import com.mubly.xinma.base.CrossApp;


import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;

/**
 * 打印中心管理类
 *
 * @author zhouhongmin
 * @date 2019-06-18
 */
public class PrintCenterManager {

    public static final String KEY_PRINT_DATA = "printData";

    private static volatile PrintCenterManager mInstance;

    PrintBroadcastReceiver printBroadcastReceiver;

    BluetoothClient mBluetoothClient;

    Context mApplicationContext;
    private boolean isRegister;


    private PrintCenterManager() {
        mApplicationContext = CrossApp.get();
        mBluetoothClient = new BluetoothClient(mApplicationContext);
    }

    public static PrintCenterManager getInstance() {
        if (mInstance == null) {
            synchronized (PrintCenterManager.class) {
                if (mInstance == null) {
                    mInstance = new PrintCenterManager();
                }
            }
        }
        return mInstance;
    }

    public BluetoothClient getBluetoothClient() {
        return mBluetoothClient;
    }

    public void registerPrintBroadcastReceiver() {
        printBroadcastReceiver = new PrintBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_DEVICE_DETACHED);
//        filter.addAction(DeviceConnFactoryManager.ACTION_QUERY_PRINTER_STATE);
//        filter.addAction(DeviceConnFactoryManager.ACTION_CONN_STATE);
        mApplicationContext.registerReceiver(printBroadcastReceiver, filter);
        isRegister = true;
    }

    /**
     * 是否已连接蓝牙打印机 true 连接 false 未连接
     *
     * @return
     */
    public boolean isConnectBluetoothPrinter() {

//        if (!mBluetoothClient.isBluetoothOpened() || DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null ||
//                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].getConnState()) {
//            return false;
//        }

        return true;
    }

    /**
     * 获取当前已连接的打印机地址
     *
     * @return
     */
    public String getConnectMacAddress() {

        if (!isConnectBluetoothPrinter()) {
            return "";
        }
        return "";
//        return DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].getMacAddress();
    }

    public void toConnectBluetooth(Activity activity, int requestCode) {
//        Intent intent = new Intent(mApplicationContext, PrinterConnectActivity.class);
//        activity.startActivityForResult(intent, requestCode);
    }


//    public void printData(YhPrintData printData, Context context) {
//
//        if (printData == null || printData.getDatas() == null || printData.getDatas().size() == 0) {
//            ToastUtils.showShort("打印数据为空");
//            return;
//        }
//
//        if (!mBluetoothClient.isBluetoothOpened() || DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0] == null ||
//                !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].getConnState()) {
//            return;
//        }
//
//
//        ThreadPool threadPool = ThreadPool.getInstantiation();
//
//        threadPool.addTask(() -> {
//
//            if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[0].getCurrentPrinterCommand() == PrinterCommand.ESC) {
//
//                YhPrintUtil.print(printData.datas);
//
//            } else {
//
//                EventBus.getDefault().post("使用打印机指令错误");
//
//            }
//        });
//
//    }


    public void unregisterPrintBroadcastReceiver() {
        if (printBroadcastReceiver != null) {
            mApplicationContext.unregisterReceiver(printBroadcastReceiver);
            isRegister = false;
        }
    }

    public boolean isRegister() {
        return isRegister;
    }
}
