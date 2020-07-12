package com.mubly.xinma.model;

import android.text.TextUtils;

import com.mubly.xinma.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assetInfo")
public class AssetInfoBean {
    @NotNull
    @PrimaryKey
    private String AssetInfoID;
    private String AssetID;
    private String CategoryInfoID;
    private String InfoName;
    private String InfoValue;
    private String InfoType;

    public String getAssetInfoID() {
        return AssetInfoID;
    }

    public void setAssetInfoID(String assetInfoID) {
        AssetInfoID = assetInfoID;
    }

    public String getAssetID() {
        return AssetID;
    }

    public void setAssetID(String assetID) {
        AssetID = assetID;
    }

    public String getCategoryInfoID() {
        return TextUtils.isEmpty(CategoryInfoID) ? "xm" + System.currentTimeMillis() : CategoryInfoID;
    }

    public void setCategoryInfoID(String categoryInfoID) {
        CategoryInfoID = categoryInfoID;
    }

    public String getInfoName() {
        return InfoName;
    }

    public void setInfoName(String infoName) {
        InfoName = infoName;
    }

    public String getInfoValue() {
        return InfoValue;
    }

    public void setInfoValue(String infoValue) {
        InfoValue = infoValue;
    }

    public String getInfoType() {
        return TextUtils.isEmpty(InfoType) ? "Text" : InfoType;
    }

    public void setInfoType(String infoType) {
        InfoType = infoType;
    }
}
