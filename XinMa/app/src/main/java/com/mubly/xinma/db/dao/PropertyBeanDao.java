package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.PropertyBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PropertyBeanDao {
    @Query("SELECT * FROM propertyBean")
    List<PropertyBean> getAll();

    @Query("SELECT * FROM propertyBean WHERE PropertyID like :ids")
    List<PropertyBean> getGroupBeanByIds(String ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PropertyBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PropertyBean> groupBeanList);

    @Delete
    void delete(PropertyBean entity);

    @Update
    void update(PropertyBean entity);
}
