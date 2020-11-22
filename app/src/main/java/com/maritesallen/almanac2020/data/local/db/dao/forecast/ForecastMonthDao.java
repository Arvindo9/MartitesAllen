package com.maritesallen.almanac2020.data.local.db.dao.forecast;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 16-01-2020
 */
@Dao
public interface ForecastMonthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Data model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Data> modelList);

    @Query("SELECT * FROM forecastMonth where currentDate = :date")
    Data getForecastMonth(String date);
}
