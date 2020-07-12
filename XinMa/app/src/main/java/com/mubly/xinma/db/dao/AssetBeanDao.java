package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.FilterBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AssetBeanDao {
    @Query("SELECT * FROM asset ORDER BY Stamp DESC")
    List<AssetBean> getAll();

    @Query("SELECT COUNT(*) FROM asset WHERE Status like :status")
    int getCountByStatus(String status);

    @Query("SELECT COUNT(*) FROM asset")
    int getAllCount();

    @Query("SELECT * FROM asset WHERE Status LIKE :status")
    List<AssetBean> getAllByStatus(String status);

    @Query("SELECT * FROM asset WHERE Status IN (:status)")
    List<AssetBean> getAllByInStatus(String[] status);

    @Query("SELECT * FROM asset WHERE AssetNo LIKE :assetNo LIMIT 1")
    AssetBean getAssetBeanByNo(String assetNo);

    @Query("SELECT * FROM asset WHERE AssetID LIKE :assetNo LIMIT 1")
    AssetBean getAssetBeanByAssetId(String assetNo);

    @Query("SELECT * FROM asset WHERE AssetID LIKE :assetId")
    List<AssetBean> getAllById(String assetId);

    @Query("SELECT * FROM asset ORDER BY Stamp DESC LIMIT :pageIndex, :pageSize")
    List<AssetBean> getAllByPage(int pageIndex, int pageSize);

    @Query("SELECT * FROM asset WHERE Status LIKE :status ORDER BY Stamp DESC LIMIT :pageIndex, :pageSize")
    List<AssetBean> getAllByStatusPage(int pageIndex, int pageSize, String status);


    @Query("SELECT * FROM asset WHERE AssetName LIKE '%' || :searchKey || '%' ORDER BY Stamp DESC")
    List<AssetBean> getAssetAllBySeachKey(String searchKey);

    @Query("SELECT * FROM asset WHERE AssetName LIKE '%' || :searchKey || '%' AND Status LIKE :status ORDER BY Stamp DESC ")
    List<AssetBean> getStatusAssetBySeachKey(String status, String searchKey);

    @Query("SELECT * FROM asset WHERE AssetName LIKE '%' || :searchKey || '%' AND Status IN (:status) ORDER BY Stamp DESC ")
    List<AssetBean> getStaAssetBySeachKey(String[] status, String searchKey);

    @Query("select *from asset where (:cartgroyId is null or CategoryID like :cartgroyId)and (:departmentId is null or Depart like :department)" +
            "and (:staffID is null or Staff like:staff)and(:status is null or Status like :status)and(:startTime is null or PurchaseDate>=:startTime)" +
            "and (:endTime is null or ExpireDate<=:endTime)and(:peroidTime is null or Remainder>=:peroidTime)")
    List<AssetBean> getFilterAssets(String status, String cartgroyId, String departmentId, String department, String staffID, String staff, String startTime, String endTime, String peroidTime);

    @Query("select *from asset where (:cartgroyId is null or CategoryID like :cartgroyId)and (:departmentId is null or Depart like :department)" +
            "and (:staffID is null or Staff like:staff)and( Status in (:status))and(:startTime is null or PurchaseDate>=:startTime)" +
            "and (:endTime is null or ExpireDate<=:endTime)and(:peroidTime is null or Remainder>=:peroidTime)")
    List<AssetBean> getSelectFilterAssets(String[] status, String cartgroyId, String departmentId, String department, String staffID, String staff, String startTime, String endTime, String peroidTime);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssetBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssetBean> assetBeanList);

    @Delete
    void delete(AssetBean entity);

    @Update
    void update(AssetBean entity);


}

