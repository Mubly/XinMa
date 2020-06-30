package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.OperateBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface OperateBeanDao {
    @Query("SELECT * FROM operateBean")
    List<OperateBean> getAll();

//    @Query("SELECT * FROM operateBean WHERE id IN (:ids)")
//    List<OperateBean> getGroupBeanByIds(long[] ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OperateBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OperateBean> groupBeanList);

    @Delete
    void delete(OperateBean entity);

    @Update
    void update(OperateBean entity);
}
