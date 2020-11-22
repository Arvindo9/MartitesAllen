package com.maritesallen.almanac2020.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maritesallen.almanac2020.data.model.apis.profile.Forecast;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 02-01-2020
 */
@Dao
public interface ProfileDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Forecast model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Forecast> modelList);

    @Query("SELECT * FROM profileData order by _id desc limit 1")
    Forecast getProfile();

    @Query("DELETE FROM profileData")
    void delete();
}
