package com.mubly.xinma.model;

import com.mubly.xinma.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "processBean")
public class ProcessBean implements Serializable {
    @NotNull
    @PrimaryKey
    private String ProcessID;
    private String AssetID;
    private String AssetNo;
    private String AssetModel;
    private String AssetName;
    private String OperateID;
    private String ProcessCate;
    private String ProcessTime;
    private String Depart;
    private String Staff;
    private String Seat;
    private String Fee;
    private String Remark;
    private String CreateTime;
    private String Headimg;
    private String StatusName;

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getAssetModel() {
        return AssetModel;
    }

    public void setAssetModel(String assetModel) {
        AssetModel = assetModel;
    }

    public String getAssetName() {
        return AssetName;
    }

    public void setAssetName(String assetName) {
        AssetName = assetName;
    }

    public String getHeadimg() {
        return Headimg;
    }

    public void setHeadimg(String headimg) {
        Headimg = headimg;
    }

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
