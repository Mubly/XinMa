package com.mubly.xinma.db.dao;

import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.ProcessBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProcessBeanDao {
    @Query("SELECT * FROM processBean")
    List<ProcessBean> getAll();

//    @Query("SELECT * FROM processBean WHERE id = :ids")
//    List<ProcessBean> getGroupBeanByIds(long ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProcessBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProcessBean> groupBeanList);

    @Delete
    void delete(ProcessBean entity);

    @Update
    void update(ProcessBean entity);
}
