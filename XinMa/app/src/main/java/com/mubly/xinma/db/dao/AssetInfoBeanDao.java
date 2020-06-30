package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetInfoBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AssetInfoBeanDao {
    @Query("SELECT * FROM assetInfo")
    List<AssetInfoBean> getAll();

//    @Query("SELECT * FROM assetInfo WHERE id IN (:ids)")
//    List<AssetInfoBean> getAllByIds(long[] ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssetInfoBean... entities);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssetInfoBean> assetInfoBeanList);
    @Delete
    void delete(AssetInfoBean entity);

    @Update
    void update(AssetInfoBean entity);
}
