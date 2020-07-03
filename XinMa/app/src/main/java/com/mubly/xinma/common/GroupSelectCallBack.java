package com.mubly.xinma.common;

import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.StaffBean;

public interface GroupSelectCallBack {
    void callback(GroupBean groupBean, StaffBean staffBean);
}
