package com.mubly.xinma.model;

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
    private String AssetName;
    private String AssetModel;
    private String Headimg;

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

    public String getAssetName() {
        return AssetName;
    }

    public void setAssetName(String assetName) {
        AssetName = assetName;
    }

    public String getAssetModel() {
        return AssetModel;
    }

    public void setAssetModel(String assetModel) {
        AssetModel = assetModel;
    }

    public String getHeadimg() {
        return Headimg;
    }

    public void setHeadimg(String headimg) {
        Headimg = headimg;
    }
}
