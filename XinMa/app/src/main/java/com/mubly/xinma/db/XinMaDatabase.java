package com.mubly.xinma.db;

import com.mubly.xinma.db.dao.AssetBeanDao;
import com.mubly.xinma.db.dao.AssetInfoBeanDao;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetInfoBean;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {AssetBean.class, AssetInfoBean.class},version = 1)
public abstract class XinMaDatabase extends RoomDatabase {
    public abstract AssetBeanDao assetBeanDao();
    public abstract AssetInfoBeanDao assetInfoBeanDao();
}
