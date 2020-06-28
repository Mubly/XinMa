package com.mubly.xinma.db.dao;


import com.mubly.xinma.model.CategoryInfoBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CategoryInfoDao {
    @Query("SELECT * FROM categroyInfo")
    List<CategoryInfoBean> getAll();

    @Query("SELECT * FROM categroyInfo WHERE CategoryID like :ids")
    List<CategoryInfoBean> getAllById(String ids);

    @Insert
    void insert(CategoryInfoBean... entities);

    @Insert
    void insertAll(List<CategoryInfoBean> categoryInfoBeanList);

    @Delete
    void delete(CategoryInfoBean entity);

    @Update
    void update(CategoryInfoBean entity);
}
