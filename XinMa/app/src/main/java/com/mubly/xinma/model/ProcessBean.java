package com.mubly.xinma.model;

import com.mubly.xinma.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "processBean")
public class ProcessBean {
    @NotNull
    @PrimaryKey
    private String ProcessID;
    private String AssetID;
    private String AssetNo;
    private String OperateID;
    private String ProcessCate;
    private String ProcessTime;
    private String Depart;
    private String Staff;
    private String Seat;
    private String Fee;
    private String Remark;
    private String CreateTime;

    public String getOperateID() {
        return OperateID;
    }

    public void setOperateID(String operateID) {
        OperateID = operateID;
    }

    public String getProcessCate() {
        return StringUtils.notNull(ProcessCate);
    }

    public void setProcessCate(String processCate) {
        ProcessCate = processCate;
    }

    public String getProcessTime() {
        return ProcessTime;
    }

    public void setProcessTime(String processTime) {
        ProcessTime = processTime;
    }

    public String getDepart() {
        return Depart;
    }

    public void setDepart(String depart) {
        Depart = depart;
    }

    public String getStaff() {
        return Staff;
    }

    public void setStaff(String staff) {
        Staff = staff;
    }

    public String getSeat() {
        return Seat;
    }

    public void setSeat(String seat) {
        Seat = seat;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getProcessID() {
        return ProcessID;
    }

    public void setProcessID(String processID) {
        ProcessID = processID;
    }

    public String getAssetID() {
        return AssetID;
    }

    public void setAssetID(String assetID) {
        AssetID = assetID;
    }

    public String getAssetNo() {
        return AssetNo;
    }

    public void setAssetNo(String assetNo) {
        AssetNo = assetNo;
    }
}
