package com.mubly.xinma.model.resbean;

import com.mubly.xinma.base.BaseModel;

public class AssetsCreateRes extends BaseModel {
    private String AssetID;
    private String Stamp;

    public String getAssetID() {
        return AssetID;
    }

    public void setAssetID(String assetID) {
        AssetID = assetID;
    }

    public String getStamp() {
        return Stamp;
    }

    public void setStamp(String stamp) {
        Stamp = stamp;
    }
}
