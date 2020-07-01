package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.AssetBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AssetBeanDao {
    @Query("SELECT * FROM asset")
    List<AssetBean> getAll();

    @Query("SELECT COUNT(*) FROM asset WHERE Status like :status")
    int getCountByStatus(String status);

    @Query("SELECT * FROM asset WHERE Status LIKE :status")
    List<AssetBean> getAllByStatus(String status);

    @Query("SELECT * FROM asset WHERE AssetID LIKE :assetId")
    List<AssetBean> getAllById(String assetId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssetBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssetBean> assetBeanList);

    @Delete
    void delete(AssetBean entity);

    @Update
    void update(AssetBean entity);
}

