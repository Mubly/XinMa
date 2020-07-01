package com.mubly.xinma.model;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventoryBean")
public class InventoryBean {
    @NotNull
    @PrimaryKey
    private String InventoryID;
    private String CheckID;
    private String AssetID;
    private String Status;
    private String StatusName;
    private String InventoryTime;
    private String CreateTime;
    @Ignore
    private List<AssetBean>assetBeanList;

    public List<AssetBean> getAssetBeanList() {
        return assetBeanList;
    }

    public void setAssetBeanList(List<AssetBean> assetBeanList) {
        this.assetBeanList = assetBeanList;
    }

    public String getInventoryID() {
        return InventoryID;
    }

    public void setInventoryID(String inventoryID) {
        InventoryID = inventoryID;
    }

    public String getCheckID() {
        return CheckID;
    }

    public void setCheckID(String checkID) {
        CheckID = checkID;
    }

    public String getAssetID() {
        return AssetID;
    }

    public void setAssetID(String assetID) {
        AssetID = assetID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getInventoryTime() {
        return InventoryTime;
    }

    public void setInventoryTime(String inventoryTime) {
        InventoryTime = inventoryTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
