package com.mubly.xinma.model;

import java.util.List;

public class GroupSelectBean {
    private List<List<StaffBean>> staffDataList;
    private List<GroupBean> groupDataList;

    public List<List<StaffBean>> getStaffDataList() {
        return staffDataList;
    }

    public void setStaffDataList(List<List<StaffBean>> staffDataList) {
        this.staffDataList = staffDataList;
    }

    public List<GroupBean> getGroupDataList() {
        return groupDataList;
    }

    public void setGroupDataList(List<GroupBean> groupDataList) {
        this.groupDataList = groupDataList;
    }
}
