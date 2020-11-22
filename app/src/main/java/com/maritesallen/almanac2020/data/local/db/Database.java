package com.maritesallen.almanac2020.data.local.db;

import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Data;
import com.maritesallen.almanac2020.data.model.apis.profile.Forecast;
import com.maritesallen.almanac2020.data.model.apis.slider.Slider;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.data.model.db.flag.Flag;
import com.maritesallen.almanac2020.data.model.db.terms.Terms;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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
@Singleton
public class Database implements DatabaseService {

    private final AppDatabase appDatabase;

    @Inject
    public Database(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    @Override
    public Flowable<Boolean> saveTerms(List<Terms> list) {
        return Flowable.fromCallable(() -> {
            appDatabase.termsDao().insertAll(list);
            return true;
        });
    }

    @Override
    public Flowable<List<Terms>> getTerms() {
        return Flowable.fromCallable(() -> appDatabase.termsDao().getTermsList());
    }

    @Override
    public Flowable<Boolean> saveBookPremium(List<BookPremium> list) {
        return Flowable.fromCallable(() -> {
            appDatabase.bookPremiumDao().insertAll(list);
            return true;
        });
    }

    @Override
    public Flowable<List<BookPremium>> getBookPremium() {
        return Flowable.fromCallable(() -> appDatabase.bookPremiumDao().getBooksPremium());
    }

    @Override
    public Flowable<Boolean> saveBooks(List<Books> list) {
        return Flowable.fromCallable(() -> {
            appDatabase.booksDao().insertAll(list);
            return true;
        });
    }

    @Override
    public Flowable<List<Books>> getBooks() {
        return Flowable.fromCallable(() -> appDatabase.booksDao().getBooks());
    }

    @Override
    public Flowable<Boolean> isThisBooksPurchase(int bookId) {
        return Flowable.fromCallable(() -> appDatabase.bookPremiumDao().getThisBookPurchase(bookId) == bookId);
    }

    @Override
    public Flowable<Boolean> deleteBookPremium() {
        return Flowable.fromCallable(() -> {
            appDatabase.bookPremiumDao().deleteBookPremium();
            return true;
        });
    }

    @Override
    public Flowable<Boolean> deleteCalendar() {
        return Flowable.fromCallable(() -> {
            appDatabase.calendarDao().delete();
            return true;
        });
    }

    @Override
    public Flowable<Boolean> saveCalendar(List<CalendarModel> list) {
        return Flowable.fromCallable(() -> {
            appDatabase.calendarDao().insertAll(list);
            return true;
        });
    }

    @Override
    public Flowable<List<CalendarModel>> getCalendar() {
        return Flowable.fromCallable(() -> appDatabase.calendarDao().getCalender());
    }

    @Override
    public Flowable<List<CalendarModel>> getCalendar(int monthId) {
        return Flowable.fromCallable(() -> appDatabase.calendarDao().getCalender(monthId));
    }

    @Override
    public Flowable<Boolean> saveProfile(Forecast data) {
        return Flowable.fromCallable(() -> {
            appDatabase.profileDao().insert(data);
            return true;
        });
    }

    @Override
    public Flowable<Forecast> getProfile() {
        return Flowable.fromCallable(() -> appDatabase.profileDao().getProfile());
    }

    @Override
    public Flowable<Boolean> deleteProfile() {
        return Flowable.fromCallable(() -> {
            appDatabase.profileDao().delete();
            return true;
        });
    }

    @Override
    public Flowable<Boolean> saveFlag(List<Flag> data) {
        return Flowable.fromCallable(() -> {
            appDatabase.flagDao().insertAll(data);
            return true;
        });
    }

    @Override
    public Flowable<List<Flag>> getFlag() {
        return Flowable.fromCallable(() -> appDatabase.flagDao().getFlag());
    }

    @Override
    public Flowable<Boolean> saveForecastActivity(Data data) {
        return Flowable.fromCallable(() -> {
            appDatabase.forecastActivityDao().insert(data);
            return true;
        });
    }

    @Override
    public Flowable<Boolean> saveForecastMonth(com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data data) {
        return Flowable.fromCallable(() -> {
            appDatabase.forecastMonthDao().insert(data);
            return true;
        });
    }

    @Override
    public Flowable<Boolean> saveFlyStar(List<Slider> data) {
        return Flowable.fromCallable(() -> {
            appDatabase.forecastFlyStarDao().insertAll(data);
            return true;
        });
    }

    @Override
    public Flowable<Data> getForecastActivity(String date) {
        return Flowable.fromCallable(() -> appDatabase.forecastActivityDao().getForecastActivity(date));
    }

    @Override
    public Flowable<com.maritesallen.almanac2020.data.model.apis.forecast.forecastMonth.Data> getForecastMonth(String date) {
        return Flowable.fromCallable(() -> appDatabase.forecastMonthDao().getForecastMonth(date));
    }

    @Override
    public Flowable<List<Slider>> getFlyStar() {
        return Flowable.fromCallable(() -> appDatabase.forecastFlyStarDao().getForecastFlyStar());
    }

    @Override
    public Flowable<Boolean> saveForecastCalendar(com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Data data) {
        return Flowable.fromCallable(() -> {
            appDatabase.forecastCalendarDao().insert(data);
            return true;
        });
    }

    @Override
    public Flowable<com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Data> getForecastCalendar(
            String iconId, String dataType) {
        return Flowable.fromCallable(() -> appDatabase.forecastCalendarDao().getForecastCalendar(iconId, dataType));
    }

    @Override
    public Flowable<Boolean> clearAllDb() {
        return Flowable.fromCallable(() -> {
            appDatabase.clearAllTables();
            return true;
        });
    }
}
