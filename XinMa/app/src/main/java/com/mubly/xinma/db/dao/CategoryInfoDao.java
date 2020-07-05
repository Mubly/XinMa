package com.mubly.xinma.db.dao;


import com.mubly.xinma.model.CategoryInfoBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CategoryInfoDao {
    @Query("SELECT * FROM categroyInfo")
    List<CategoryInfoBean> getAll();

    @Query("SELECT * FROM categroyInfo WHERE CategoryID like :ids")
    List<CategoryInfoBean> getAllById(String ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CategoryInfoBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CategoryInfoBean> categoryInfoBeanList);

    @Query("DELETE FROM categroyInfo WHERE CategoryID LIKE :categoryId")
    void deleteById(String categoryId);

    @Query("SELECT * FROM categroyInfo WHERE CategoryID LIKE :categoryId LIMIT 1")
    CategoryInfoBean getCategoryInfoById(String categoryId);

    @Delete
    void delete(CategoryInfoBean entity);

    @Update
    void update(CategoryInfoBean entity);
}
