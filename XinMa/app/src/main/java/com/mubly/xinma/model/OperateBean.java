package com.mubly.xinma.model;

import com.mubly.xinma.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "operateBean")
public class OperateBean {
    @NotNull
    @PrimaryKey
    private String OperateID;
    private String ProcessCate;
    private String ProcessTime;
    private String Depart;
    private String Staff;
    private String Seat;
    private String Fee;
    private String Remark;
    private String CreateTime;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
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
        return StringUtils.notNull(Depart);
    }

    public void setDepart(String depart) {
        Depart = depart;
    }

    public String getStaff() {
        return StringUtils.notNull(Staff);
    }

    public void setStaff(String staff) {
        Staff = staff;
    }

    public String getSeat() {
        return StringUtils.notNull2(Seat);
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
        return StringUtils.notNull2(Remark);
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
