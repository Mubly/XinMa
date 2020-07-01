package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.CheckBean;
import com.mubly.xinma.model.StaffBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CheckBeanDao {
    @Query("SELECT * FROM checkBean")
    List<CheckBean> getAll();

    @Query("SELECT COUNT(*) FROM checkBean WHERE Status like :status")
    int getCountByStatus(String status);

    @Query("SELECT COUNT(*) FROM checkBean")
    int getAllCount();

    @Query("SELECT * FROM checkBean WHERE Status LIKE :status")
    List<CheckBean> getAllByStatus(String status);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CheckBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CheckBean> checkBeanList);

    @Query("DELETE FROM CHECKBEAN WHERE CheckID = (:id)")
    void deleteById(String id);

    @Delete
    void delete(CheckBean entity);

    @Update
    void update(CheckBean entity);
}
