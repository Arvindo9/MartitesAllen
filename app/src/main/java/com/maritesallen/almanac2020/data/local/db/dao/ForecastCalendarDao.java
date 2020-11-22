package com.maritesallen.almanac2020.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Data;

/**
 * Author : Arvindo Mondal
 * Created on 24-01-2020
 */
@Dao
public interface ForecastCalendarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Data model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Data> modelList);

    @Query("SELECT * FROM forecastCalendar where iconId = :iconId and dataType = :dataType")
    Data getForecastCalendar(String iconId, String dataType );

    @Query("DELETE FROM forecastCalendar")
    void delete();
}
