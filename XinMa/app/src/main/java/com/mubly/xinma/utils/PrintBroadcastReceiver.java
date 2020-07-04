package com.mubly.xinma.utils;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



/**
 *
 * @Description: 打印广播接收器
 * @author 周洪民
 * @date 2018/8/22 下午2:00
 *
 */
public class PrintBroadcastReceiver extends BroadcastReceiver {


    public final static int id = 0;

    /**
     * 打印完成
     */
    public static final String ACTION_QUERY_PRINTER_STATE = "action_query_printer_state";

    /**
     * 连接断开
     */
    public static final String BLE_CONN_STATE_DISCONN = "ble_conn_state_disconn";

    /**
     * 连接失败
     */
    public static final String BLE_CONN_STATE_FAILED = "ble_conn_stata_failed";

    /**
     * 连接状态:未连接
     */
    public static final String BLE_CONN_STATE_DISCONNECT = "ble_conn_state_disconnect";

    /**
     * 连接中
     */
    public static final String BLE_CONN_STATE_CONNECTING = "ble_conn_state_connecting";

    /**
     * 连接成功
     */
    public static final String BLE_CONN_STATE_CONNECTED = "ble_conn_state_connected";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        switch (action) {

            //蓝牙连接断开广播
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
//                EventBus.getDefault().post(BLE_CONN_STATE_DISCONN, "PrintConnection");
                break;

//            case DeviceConnFactoryManager.ACTION_CONN_STATE:
//
//                int state = intent.getIntExtra(DeviceConnFactoryManager.STATE, -1);
//                int deviceId = intent.getIntExtra(DeviceConnFactoryManager.DEVICE_ID, -1);
//
//                switch (state) {
//
//                    //断开连接
//                    case DeviceConnFactoryManager.CONN_STATE_DISCONNECT:
//
//                        if (id == deviceId) {
//                            EventBus.getDefault().post(BLE_CONN_STATE_DISCONNECT, "PrintConnection");
//                        }
//                        break;
//
//                    //连接中
//                    case DeviceConnFactoryManager.CONN_STATE_CONNECTING:
//                        EventBus.getDefault().post(BLE_CONN_STATE_CONNECTING, "PrintConnection");
//                        break;
//
//                    //连接成功
//                    case DeviceConnFactoryManager.CONN_STATE_CONNECTED:
//                        EventBus.getDefault().post(BLE_CONN_STATE_CONNECTED, "PrintConnection");
//                        break;
//
//                    //连接失败
//                    case DeviceConnFactoryManager.CONN_STATE_FAILED:
//                        EventBus.getDefault().post(BLE_CONN_STATE_FAILED, "PrintConnection");
//                        break;
//
//                    default:
//                        break;
//                }
//
//                break;

            //打印完成
//            case DeviceConnFactoryManager.ACTION_QUERY_PRINTER_STATE:
//                EventBus.getDefault().post(ACTION_QUERY_PRINTER_STATE, "PrintConnection");
//                break;

            default:
                break;
        }
    }
}
