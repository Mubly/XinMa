package com.mubly.xinma.model.resbean;

import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.model.CheckBean;

import java.util.List;

public class CheckOperateResData extends BaseModel {
    private List<CheckBean> Check;

    public List<CheckBean> getCheck() {
        return Check;
    }

    public void setCheck(List<CheckBean> check) {
        Check = check;
    }
}
