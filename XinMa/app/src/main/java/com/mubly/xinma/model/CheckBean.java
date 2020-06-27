package com.mubly.xinma.model;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "checkBean")
public class CheckBean {
    @NotNull
    @PrimaryKey
    private String CheckID;
    private String CheckSN;
    private String Items;
    private String Status;
    private String StatusName;
    private String Stamp;
    private String LastTime;
    private String CreateTime;

    public String getCheckSN() {
        return CheckSN;
    }

    public void setCheckSN(String checkSN) {
        CheckSN = checkSN;
    }

    public String getStamp() {
        return Stamp;
    }

    public void setStamp(String stamp) {
        Stamp = stamp;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getCheckID() {
        return CheckID;
    }

    public void setCheckID(String checkID) {
        CheckID = checkID;
    }

    public String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLastTime() {
        return LastTime;
    }

    public void setLastTime(String lastTime) {
        LastTime = lastTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
