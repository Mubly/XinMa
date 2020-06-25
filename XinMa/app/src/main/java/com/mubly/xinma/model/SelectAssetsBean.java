package com.mubly.xinma.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectAssetsBean implements Serializable {
    private List<AssetBean> selectBean ;

    public SelectAssetsBean() {
    }

    public List<AssetBean> getSelectBean() {
        return selectBean;
    }

    public void setSelectBean(List<AssetBean> selectBean) {
        this.selectBean = selectBean;
    }
}
