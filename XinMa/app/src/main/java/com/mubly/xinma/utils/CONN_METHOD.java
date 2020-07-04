package com.mubly.xinma.utils;

public enum CONN_METHOD {
    //蓝牙连接
    BLUETOOTH("BLUETOOTH"),
    //USB连接
    USB("USB"),
    //wifi连接
    WIFI("WIFI"),
    //串口连接
    SERIAL_PORT("SERIAL_PORT");

    private String name;

    private CONN_METHOD(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
