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

    @Query("SELECT * FROM assetInfo WHERE AssetID LIKE (:ids)")
    List<AssetInfoBean> getAllByAssetsId(String ids);

    @Query("SELECT * FROM assetInfo WHERE AssetID LIKE (:ids) AND InfoName LIKE (:key) LIMIT 1")
    AssetInfoBean getLocalAssetInfo(String ids, String key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssetInfoBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssetInfoBean> assetInfoBeanList);

    @Query("DELETE FROM assetInfo WHERE AssetID LIKE :assetId")
    void deleteByAssetId(String assetId);

    @Delete
    void delete(AssetInfoBean entity);

    @Update
    void update(AssetInfoBean entity);
}
