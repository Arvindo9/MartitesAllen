package com.maritesallen.almanac2020.utils.util;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.maritesallen.almanac2020.data.model.apis.calender.CalendarData;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.maritesallen.almanac2020.utils.AppConstants.DATE_INT_FORMAT;
import static com.maritesallen.almanac2020.utils.AppConstants.DATE_SEND_FORMAT;
import static com.maritesallen.almanac2020.utils.AppConstants.DATE_TIME_NAME_FORMAT;
import static com.maritesallen.almanac2020.utils.AppConstants.GENERAL_DATE_FORMAT;

/**
 * Author       : Arvindo Mondal
 * Created on   : 15-01-2019
 * Email        : arvindomondal@gmail.com
 */
public class General {

    private General(){}

    public static int CURRENT_MONTH = 0;

    static {
        getCurrentMonth();
    }

    public static int getCurrentDateInt(){
        SimpleDateFormat df = new SimpleDateFormat(DATE_INT_FORMAT, Locale.ENGLISH);
        return Integer.parseInt(df.format(new Date()));
    }

    public static Date getCurrentDate(){
        return new Date();
    }

    public static String getCurrentDateTime(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_NAME_FORMAT, Locale.ENGLISH);
        return  df.format(date);
    }

    public static String getDateTime(Date date){
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_NAME_FORMAT, Locale.ENGLISH);
        return  df.format(date);
    }

    public static String getDateSend(Date date){
        SimpleDateFormat df = new SimpleDateFormat(DATE_SEND_FORMAT, Locale.ENGLISH);
        return  df.format(date);
    }

    public static String getDateSend(){
        SimpleDateFormat df = new SimpleDateFormat(DATE_SEND_FORMAT, Locale.ENGLISH);
        return  df.format(new Date());
    }

    public static int getDateInt(Date date){
        try {
            SimpleDateFormat df = new SimpleDateFormat(DATE_INT_FORMAT, Locale.ENGLISH);
            int d = Integer.parseInt(df.format(date));
            return d;
        }
        catch (Exception e){
            return 0;
        }
    }

    public static String getDateReadable(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(GENERAL_DATE_FORMAT, Locale.ENGLISH);
        return df.format(date);
    }

    public static String getDateReadable(String date){
        SimpleDateFormat df = new SimpleDateFormat(GENERAL_DATE_FORMAT, Locale.ENGLISH);
        String dateStr = "";
        try {
            Date dateObj = df.parse(date);
            dateStr = df.format(dateObj);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static String getDateReadable1(String date){
        String dateStr = "";
        SimpleDateFormat input = new SimpleDateFormat(DATE_SEND_FORMAT, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(GENERAL_DATE_FORMAT, Locale.ENGLISH);
        try {
            Date oneWayTripDate = input.parse(date);                 // parse input
            dateStr = output.format(oneWayTripDate);    // format output
        }
        catch (NullPointerException | ParseException e) {
            e.printStackTrace();
        }

        return dateStr;
    }

    public static int getIntDate(String date){
        SimpleDateFormat input = new SimpleDateFormat(DATE_SEND_FORMAT, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(DATE_INT_FORMAT, Locale.ENGLISH);
        int dateStr = 0;
        try {
            Date dateObj = input.parse(date);
            String d = output.format(dateObj);
            dateStr = Integer.parseInt(d);
        }
        catch (NullPointerException | ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static int getIntDate(Date date){
        SimpleDateFormat output = new SimpleDateFormat(DATE_INT_FORMAT, Locale.ENGLISH);
        int dateStr = 0;
        try {
            String d = output.format(date);
            dateStr = Integer.parseInt(d);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static int getIntDateDay(String date){
        SimpleDateFormat input = new SimpleDateFormat(DATE_INT_FORMAT, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(DATE_INT_FORMAT, Locale.ENGLISH);
        int dateStr = 0;
        try {
            Date dateObj = input.parse(date);
            String d = output.format(dateObj);
            dateStr = Integer.parseInt(d.substring(6));
        }
        catch (NullPointerException | ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static String getDateReadable(Date date){
        SimpleDateFormat df = new SimpleDateFormat(GENERAL_DATE_FORMAT, Locale.ENGLISH);
        return df.format(date);
    }

    public static int getCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        return CURRENT_MONTH = calendar.get(Calendar.MONTH);
    }

    public static int getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    public static int getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    // Clear cache------------


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if(children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static boolean isCurrentDate(int[] calendarDate) {
        int currentDate = getCurrentDateInt();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, calendarDate[2]);         //comment this
        cal.set(Calendar.MONTH, calendarDate[1]);
        cal.set(Calendar.DAY_OF_MONTH, calendarDate[0]);

        int selectDate = General.getIntDate(cal.getTime());
        return currentDate == selectDate;
    }

    //-----------------------

    /**
     * If keypad is showing it can be hide immediately
     */
    public static void hideKeyboard(Context context) {
        View view = ((Activity)context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}
