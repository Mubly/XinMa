package com.mubly.xinma.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assetInfo")
public class AssetInfoBean {
    @PrimaryKey(autoGenerate = true)
    private long id;
    public String AssetInfoID;
    public String AssetID;
    public String CategoryInfoID;
    public String InfoName;
    public String InfoValue;
    public String InfoType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
