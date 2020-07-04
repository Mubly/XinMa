package com.mubly.xinma.model.resbean;

import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.model.AssetBean;

import java.util.List;

public class AssetsCreateRes extends BaseModel {
    private String AssetID;
    private double Stamp;
    private List<AssetBean>Asset;

    public List<AssetBean> getAsset() {
        return Asset;
    }

    public void setAsset(List<AssetBean> asset) {
        Asset = asset;
    }

    public String getAssetID() {
        return AssetID;
    }

    public void setAssetID(String assetID) {
        AssetID = assetID;
    }

    public double getStamp() {
        return Stamp;
    }

    public void setStamp(double stamp) {
        Stamp = stamp;
    }
}
