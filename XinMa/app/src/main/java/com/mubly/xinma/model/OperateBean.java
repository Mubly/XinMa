package com.mubly.xinma.model;

import com.mubly.xinma.utils.StringUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "operateBean")
public class OperateBean {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String OperateID;
    private String ProcessCate;
    private String ProcessTime;
    private String Depart;
    private String Staff;
    private String Seat;
    private String Fee;
    private String Remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperateID() {
        return OperateID;
    }

    public void setOperateID(String operateID) {
        OperateID = operateID;
    }

    public String getProcessCate() {
        return ProcessCate;
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
}
