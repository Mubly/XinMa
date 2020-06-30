package com.mubly.xinma.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.GroupBean;

import java.util.List;

@Dao
public interface GroupBeanDao {
    @Query("SELECT * FROM groupBean")
    List<GroupBean> getAll();

//    @Query("SELECT * FROM groupBean WHERE id IN (:ids)")
//    List<GroupBean> getGroupBeanByIds(long[] ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GroupBean... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GroupBean> groupBeanList);

    @Delete
    void delete(GroupBean entity);

    @Update
    void update(GroupBean entity);
}
