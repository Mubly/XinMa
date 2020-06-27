package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.CheckBean;
import com.mubly.xinma.model.InventoryBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface InventoryBeanDao {

    @Query("SELECT * FROM inventoryBean")
    List<InventoryBean> getAll();

    @Query("SELECT COUNT(*) FROM inventoryBean WHERE Status like :status")
    int getCountByStatus(String status);

    @Query("SELECT * FROM inventoryBean WHERE Status LIKE :status")
    List<InventoryBean> getAllByStatus(String status);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InventoryBean... entities);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<InventoryBean> inventoryBeanList);
    @Delete
    void delete(InventoryBean entity);

    @Update
    void update(InventoryBean entity);
}
