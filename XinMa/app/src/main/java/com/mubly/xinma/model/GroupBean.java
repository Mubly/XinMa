package com.mubly.xinma.model;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "groupBean")
public class GroupBean {
    @NotNull
    @PrimaryKey
    private String DepartID;//部门id
    private String Depart;//部门名称
    private int ShowIndex;//排序	暂时首次使用
    private int Status;//状态	1/有效 0/关闭
    @Ignore
    private int staffCount;//下属员工数量

    public int getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(int staffCount) {
        this.staffCount = staffCount;
    }

    public String getDepartID() {
        return DepartID;
    }

    public void setDepartID(String departID) {
        DepartID = departID;
    }

    public String getDepart() {
        return Depart;
    }

    public void setDepart(String depart) {
        Depart = depart;
    }

    public int getShowIndex() {
        return ShowIndex;
    }

    public void setShowIndex(int showIndex) {
        ShowIndex = showIndex;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return TextUtils.isEmpty(Depart)?"--":Depart;
    }
}
