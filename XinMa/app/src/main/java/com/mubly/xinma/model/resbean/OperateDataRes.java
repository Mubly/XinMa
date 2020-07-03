package com.mubly.xinma.model.resbean;

import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.model.AssetBean;

import java.util.List;

public class OperateDataRes extends BaseModel {
    private String OperateID;
    private String Stamp;
    private List<AssetBean>Asset;

    public String getOperateID() {
        return OperateID;
    }

    public void setOperateID(String operateID) {
        OperateID = operateID;
    }

    public String getStamp() {
        return Stamp;
    }

    public void setStamp(String stamp) {
        Stamp = stamp;
    }

    public List<AssetBean> getAsset() {
        return Asset;
    }

    public void setAsset(List<AssetBean> asset) {
        Asset = asset;
    }
}
