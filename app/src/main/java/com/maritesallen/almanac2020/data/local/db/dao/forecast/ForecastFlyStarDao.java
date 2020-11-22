package com.maritesallen.almanac2020.data.local.db.dao.forecast;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.maritesallen.almanac2020.data.model.apis.slider.Slider;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 16-01-2020
 */
@Dao
public interface ForecastFlyStarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Slider model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Slider> modelList);

    @Query("SELECT * FROM flyStarSlider")
    List<Slider> getForecastFlyStar();
}
