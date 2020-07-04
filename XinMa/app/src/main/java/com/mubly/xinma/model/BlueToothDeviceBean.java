package com.mubly.xinma.model;

/**
 * 蓝牙设备模型类
 * @author zhouhongmin
 * @date 2019-06-15
 */
public class BlueToothDeviceBean {

    String deviceName;

    String deviceAddress;

    boolean isMatch;

    public BlueToothDeviceBean() {

    }

    public BlueToothDeviceBean(String deviceName, String deviceAddress) {
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
    }

    public BlueToothDeviceBean(String deviceName, String deviceAddress, boolean isMatch) {
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
        this.isMatch = isMatch;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public void setMatch(boolean match) {
        isMatch = match;
    }
}
