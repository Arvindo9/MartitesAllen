package com.maritesallen.almanac2020.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.maritesallen.almanac2020.data.local.db.dao.books.BookPremiumDao;
import com.maritesallen.almanac2020.data.local.db.dao.CalendarDao;
import com.maritesallen.almanac2020.data.local.db.dao.FlagDao;
import com.maritesallen.almanac2020.data.local.db.dao.ForecastCalendarDao;
import com.maritesallen.almanac2020.data.local.db.dao.ProfileDao;
import com.maritesallen.almanac2020.data.local.db.dao.TermsDao;
import com.maritesallen.almanac2020.data.local.db.dao.books.BooksDao;
import com.maritesallen.almanac2020.data.local.db.dao.forecast.ForecastActivityDao;
import com.maritesallen.almanac2020.data.local.db.dao.forecast.ForecastFlyStarDao;
import com.maritesallen.almanac2020.data.local.db.dao.forecast.ForecastMonthDao;
import com.maritesallen.almanac2020.data.local.db.utils.DataConverter;
import com.maritesallen.almanac2020.data.local.db.utils.DateConverter;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;
import com.maritesallen.almanac2020.data.model.apis.profile.Forecast;
import com.maritesallen.almanac2020.data.model.apis.slider.Slider;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.data.model.db.flag.Flag;
import com.maritesallen.almanac2020.data.model.db.terms.Terms;

/**
 * Author       : Arvindo Mondal
 * Created on   : 09-05-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
@Database(entities = {Terms.class, BookPremium.class, CalendarModel.class, Forecast.class, Flag.class,
        Slider.class, Books.class,
        com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Data.class,
        com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data.class,
        com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Data.class
},
        version = 17, exportSchema = false)
@TypeConverters({DateConverter.class, DataConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TermsDao termsDao();

    public abstract BookPremiumDao bookPremiumDao();

    public abstract CalendarDao calendarDao();

    public abstract ProfileDao profileDao();

    public abstract FlagDao flagDao();

    public abstract ForecastActivityDao forecastActivityDao();

    public abstract ForecastMonthDao forecastMonthDao();

    public abstract ForecastFlyStarDao forecastFlyStarDao();

    public abstract ForecastCalendarDao forecastCalendarDao();

    public abstract BooksDao booksDao();
}
