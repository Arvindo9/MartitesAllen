package com.maritesallen.almanac2020.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maritesallen.almanac2020.data.model.db.flag.Flag;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 02-01-2020
 */
@Dao
public interface FlagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Flag model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Flag> modelList);

    @Query("SELECT * FROM flag")
    List<Flag> getFlag();
}
