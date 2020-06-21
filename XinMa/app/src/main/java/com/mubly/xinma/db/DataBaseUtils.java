package com.mubly.xinma.db;

import com.mubly.xinma.base.CrossApp;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetInfoBean;

import java.util.List;

import androidx.room.Room;

public class DataBaseUtils {
    private static DataBaseUtils instance = null;
    private XinMaDatabase database = null;

    private DataBaseUtils() {
        database = Room.databaseBuilder(CrossApp.get(), XinMaDatabase.class, "xinma_db").build();
    }

    public static DataBaseUtils getInstance() {
        if (null == instance) {
            synchronized (DataBaseUtils.class) {
                if (null == instance) {
                    instance = new DataBaseUtils();
                }
            }
        }
        return instance;
    }

    public void setAssetBeanList(List<AssetBean> assetBeanList) {
        database.assetBeanDao().insertAll(assetBeanList);
    }

    public List<AssetBean> getAssetBeanList() {
        return database.assetBeanDao().getAll();
    }

    public void setAssetInfoBeanList(List<AssetInfoBean> assetInfoBeanList) {
        database.assetInfoBeanDao().insertAll(assetInfoBeanList);
    }

    public List<AssetInfoBean> getAssetInfoBeanList() {
        return database.assetInfoBeanDao().getAll();
    }
}
