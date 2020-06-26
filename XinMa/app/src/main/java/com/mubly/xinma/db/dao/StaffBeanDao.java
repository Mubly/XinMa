package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.StaffBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StaffBeanDao {
    @Query("SELECT * FROM staffBean")
    List<StaffBean> getAll();

    @Query("SELECT COUNT(*) FROM staffBean WHERE DepartID like :departId")
    int getCountByDepartId(String departId);

    @Query("SELECT * FROM staffBean WHERE DepartID LIKE :departId")
    List<StaffBean> getAllByDepartId(String departId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StaffBean... entities);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StaffBean> staffBeanList);
    @Delete
    void delete(StaffBean entity);

    @Update
    void update(StaffBean entity);
}
