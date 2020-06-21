package com.mubly.xinma.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "groupBean")
public class GroupBean {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String DepartID;//部门id
    private String Depart;//部门名称
    private int ShowIndex;//排序	暂时首次使用
    private int Status;//状态	1/有效 0/关闭

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
