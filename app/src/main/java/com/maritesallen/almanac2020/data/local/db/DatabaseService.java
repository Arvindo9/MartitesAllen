package com.maritesallen.almanac2020.data.local.db;


import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;
import com.maritesallen.almanac2020.data.model.apis.profile.Forecast;
import com.maritesallen.almanac2020.data.model.apis.slider.Slider;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.data.model.db.flag.Flag;
import com.maritesallen.almanac2020.data.model.db.terms.Terms;

import java.util.List;

import io.reactivex.Flowable;

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
public interface DatabaseService {

    Flowable<Boolean> saveTerms(List<Terms> list);

    Flowable<List<Terms>> getTerms();

    Flowable<Boolean> saveBookPremium(List<BookPremium> list);

    Flowable<List<BookPremium>> getBookPremium();

    Flowable<Boolean> saveBooks(List<Books> list);

    Flowable<List<Books>> getBooks();

    Flowable<Boolean> isThisBooksPurchase(int bookId);

    Flowable<Boolean> deleteBookPremium();


    Flowable<Boolean> deleteCalendar();

    Flowable<Boolean> saveCalendar(List<CalendarModel> data);

    Flowable<List<CalendarModel>> getCalendar();

    Flowable<List<CalendarModel>> getCalendar(int monthId);


    Flowable<Boolean> saveProfile(Forecast data);

    Flowable<Forecast> getProfile();

    Flowable<Boolean> deleteProfile();


    Flowable<Boolean> saveFlag(List<Flag> data);

    Flowable<List<Flag>> getFlag();

    //Forecast---------------------

    Flowable<Boolean> saveForecastActivity(
            com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Data data
    );

    Flowable<Boolean> saveForecastMonth(
            com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data data
    );

    Flowable<Boolean> saveFlyStar(
            List<Slider> data
    );

    Flowable<com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Data> getForecastActivity(String date);

    Flowable<com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data> getForecastMonth(String date);

    Flowable<List<Slider>> getFlyStar();

    //Forecast calendar--------------


    Flowable<Boolean> saveForecastCalendar(
            com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Data data
    );

    Flowable<com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Data> getForecastCalendar(
            String iconId,
            String dataType
    );

    //logout
    Flowable<Boolean> clearAllDb();

}
