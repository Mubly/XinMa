package com.mubly.xinma.model;

import com.mubly.xinma.utils.StringUtils;

public class ChangeBean {
    private String Tag;
    private String ChangeTime;
    private String FirstPrice;
    private String FirstRemainder;
    private String Price;
    private int Remainder;
    private int Decrease;
    private String Remark;
    private int Status;

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getChangeTime() {
        return ChangeTime;
    }

    public void setChangeTime(String changeTime) {
        ChangeTime = changeTime;
    }

    public String getFirstPrice() {
        return FirstPrice;
    }

    public void setFirstPrice(String firstPrice) {
        FirstPrice = firstPrice;
    }

    public String getFirstRemainder() {
        return FirstRemainder;
    }

    public void setFirstRemainder(String firstRemainder) {
        FirstRemainder = firstRemainder;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getRemainder() {
        return Remainder;
    }

    public void setRemainder(int remainder) {
        Remainder = remainder;
    }

    public int getDecrease() {
        return Decrease;
    }

    public void setDecrease(int decrease) {
        Decrease = decrease;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
