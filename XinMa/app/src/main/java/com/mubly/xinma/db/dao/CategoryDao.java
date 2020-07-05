package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.AssetInfoBean;
import com.mubly.xinma.model.CategoryBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import retrofit2.http.DELETE;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categoryBean")
    List<CategoryBean> getAll();

    @Query("SELECT * FROM categoryBean WHERE CategoryID LIKE (:ids) LIMIT 1")
    CategoryBean getCategoryById(String ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CategoryBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CategoryBean> categoryBeanList);

    @Query("DELETE FROM categoryBean WHERE CategoryID LIKE :categoryId")
    void deleteById(String categoryId);

    @Delete
    void delete(CategoryBean entity);

    @Update
    void update(CategoryBean entity);
}
