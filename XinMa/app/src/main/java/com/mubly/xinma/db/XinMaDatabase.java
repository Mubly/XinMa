package com.mubly.xinma.db;

import com.mubly.xinma.base.CrossApp;
import com.mubly.xinma.db.dao.AssetBeanDao;
import com.mubly.xinma.db.dao.AssetInfoBeanDao;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetInfoBean;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {AssetBean.class, AssetInfoBean.class}, version = 1)
public abstract class XinMaDatabase extends RoomDatabase {
    private static XinMaDatabase instance = null;

    public abstract AssetBeanDao assetBeanDao();

    public abstract AssetInfoBeanDao assetInfoBeanDao();

    public static XinMaDatabase getInstance() {
        if (null == instance) {
            synchronized (XinMaDatabase.class) {
                if (null == instance) {
                    instance = Room.databaseBuilder(CrossApp.get(), XinMaDatabase.class, "xinma_db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
