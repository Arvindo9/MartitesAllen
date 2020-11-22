package com.maritesallen.almanac2020.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 28-12-2019
 */
@Dao
public interface CalendarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CalendarModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CalendarModel> modelList);

    @Query("SELECT * FROM calendar")
    List<CalendarModel> getCalender();

    @Query("SELECT * FROM calendar where monthId = :monthId")
    List<CalendarModel> getCalender(int monthId);

    @Query("Delete from calendar")
    void delete();
}
