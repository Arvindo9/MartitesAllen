package com.maritesallen.almanac2020.data.local.db.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maritesallen.almanac2020.data.model.apis.calender.activity.Activity;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ActivityList;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Suitable;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.UnSuitableActivityList;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Unsuitable;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Day;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Month;
import com.maritesallen.almanac2020.data.model.apis.profile.Animal;
import com.maritesallen.almanac2020.data.model.apis.profile.Career;
import com.maritesallen.almanac2020.data.model.apis.profile.Health;
import com.maritesallen.almanac2020.data.model.apis.profile.Romance;
import com.maritesallen.almanac2020.data.model.apis.profile.Wealth;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 28-12-2019
 */
public class DataConverter {

    private static Gson gson = new Gson();

    public static Gson getGson(){
        return gson;
    }

    @TypeConverter
    public static List<CalendarModel> profileMediaToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<CalendarModel>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String profileMediaToString(List<CalendarModel> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Activity> ActivityToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Activity>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String ActivityToString(List<Activity> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Suitable> SuitableToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Suitable>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String SuitableToString(List<Suitable> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Unsuitable> UnsuitableToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Unsuitable>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String UnsuitableToString(List<Unsuitable> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<ActivityList> ActivityListToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<ActivityList>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String ActivityListToString(List<ActivityList> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<UnSuitableActivityList> UnSuitableActivityListListToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<UnSuitableActivityList>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String UnSuitableActivityListListToString(List<UnSuitableActivityList> list) {
        return gson.toJson(list);
    }

    //Profile------------------------------

    @TypeConverter
    public static Romance romanceList(String data) {
        if (data == null) {
            return new Romance();
        }
        Type listType = new TypeToken<Romance>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String ActivityToString(Romance list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static Wealth WealthList(String data) {
        if (data == null) {
            return new Wealth();
        }
        Type listType = new TypeToken<Wealth>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String WealthToString(Wealth list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static Health HealthList(String data) {
        if (data == null) {
            return new Health();
        }
        Type listType = new TypeToken<Health>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String HealthToString(Health list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static Career CareerList(String data) {
        if (data == null) {
            return new Career();
        }
        Type listType = new TypeToken<Career>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String CareerString(Career list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static Animal AnimalList(String data) {
        if (data == null) {
            return new Animal();
        }
        Type listType = new TypeToken<Animal>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String AnimalToString(Animal list) {
        return gson.toJson(list);
    }







    @TypeConverter
    public static List<Month> MonthList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Month>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String MonthToString(List<Month> list) {
        return gson.toJson(list);
    }


    @TypeConverter
    public static List<Day> DayList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Day>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String DayToString(List<Day> list) {
        return gson.toJson(list);
    }

}
