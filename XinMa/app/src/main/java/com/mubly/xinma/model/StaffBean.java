package com.mubly.xinma.model;

import com.mubly.xinma.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "staffBean")
public class StaffBean {
    //    @PrimaryKey(autoGenerate = true)
//    private long id;
    @NotNull
    @PrimaryKey
    private String StaffID;//员工ID
    private String DepartID;//部门ID
    private String Depart;//部门名称
    private String Staff;//员工姓名
    private String Position;//职位
    private String Phone;
    private String AssetQty;//资产数量
    private String Status;//状态

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }

    public String getDepartID() {
        return DepartID;
    }

    public void setDepartID(String departID) {
        DepartID = departID;
    }

    public String getDepart() {
        return StringUtils.notNull(Depart);
    }

    public void setDepart(String depart) {
        Depart = depart;
    }

    public String getStaff() {
        return StringUtils.notNull2(Staff);
    }

    public void setStaff(String staff) {
        Staff = staff;
    }

    public String getPosition() {
        return StringUtils.notNull(Position);
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getPhone() {
        return StringUtils.notNull(Phone);
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAssetQty() {
        return AssetQty;
    }

    public void setAssetQty(String assetQty) {
        AssetQty = assetQty;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
}
