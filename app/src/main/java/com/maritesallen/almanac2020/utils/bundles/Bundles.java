package com.maritesallen.almanac2020.utils.bundles;

import android.os.Bundle;

import com.maritesallen.almanac2020.data.model.apis.access.FacebookModel;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ActivityList;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.UnSuitableActivityList;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.Month;

import java.util.ArrayList;

/**
 * Author       : Arvindo Mondal
 * Created on   : 09-10-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class Bundles {
    private static final String KEY_USER_DATA = "KEY_USER_DATA";
    private static final String KEY_DEFAULT_TAG = "KEY_DEFAULT_TAG";
    private static final String KEY_DEFAULT_TYPE = "KEY_DEFAULT_TYPE";
    private static final String KEY_DEFAULT_DATA = "KEY_DEFAULT_DATA";
    private static final String KEY_DEFAULT_TITLE = "KEY_DEFAULT_TITLE";
    private static final String KEY_DEFAULT_ID1 = "KEY_DEFAULT_ID1";
    private static final String KEY_DEFAULT_ID2 = "KEY_DEFAULT_ID2";
    private static final String KEY_DEFAULT_ID3 = "KEY_DEFAULT_ID3";

    private static Bundles bundles;

    private Bundles(){
        //Bundle
    }

    public static Bundle setPremiumAlertDialog(String tag){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USER_DATA, tag);
        return bundle;
    }

    public static String getPremiumAlertDialog(Bundle bundle){
        if(bundle == null){
            return "Default";
        }
        return bundle.getString(KEY_USER_DATA);
    }

    public static String getDefaultActivityTag(Bundle bundle){
        if(bundle == null){
            return "Default";
        }
        return bundle.getString(KEY_DEFAULT_TAG);
    }

    public static Bundle setDefaultActivitySuitable(String tag, int type, ArrayList<ActivityList> list){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DEFAULT_TAG, tag);
        bundle.putInt(KEY_DEFAULT_TYPE, type);
        bundle.putParcelableArrayList(KEY_DEFAULT_DATA, list);
        return bundle;
    }

    public static Bundle setDefaultActivityUnSuitable(String tag, int type, ArrayList<UnSuitableActivityList> list){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DEFAULT_TAG, tag);
        bundle.putInt(KEY_DEFAULT_TYPE, type);
        bundle.putParcelableArrayList(KEY_DEFAULT_DATA, list);
        return bundle;
    }

    public static Bundle setDefaultActivityForecastCalendar(String tag, int type, String iconId, String yearId, String dataType){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DEFAULT_TAG, tag);
        bundle.putInt(KEY_DEFAULT_TYPE, type);
        bundle.putString(KEY_DEFAULT_ID1, iconId);
        bundle.putString(KEY_DEFAULT_ID2, yearId);
        bundle.putString(KEY_DEFAULT_ID3, dataType);
        return bundle;
    }

    public static int getDefaultActivityType(Bundle bundle){
        if(bundle == null){
            return 0;
        }
        return bundle.getInt(KEY_DEFAULT_TYPE);
    }

    public static ArrayList<ActivityList> getDefaultActivityDataSitable(Bundle bundle){
        if(bundle == null){
            return null;
        }
        return bundle.getParcelableArrayList(KEY_DEFAULT_DATA);
    }

    public static ArrayList<UnSuitableActivityList> getDefaultActivityDataUnSitable(Bundle bundle){
        if(bundle == null){
            return null;
        }
        return bundle.getParcelableArrayList(KEY_DEFAULT_DATA);
    }

    public static ArrayList<Month> getDefaultActivityDataForecastCalendar(Bundle bundle){
        if(bundle == null){
            return null;
        }
        return bundle.getParcelableArrayList(KEY_DEFAULT_DATA);
    }


    public static String getDefaultActivityDataForecastCalendarTitle(Bundle bundle){
        if(bundle == null){
            return "Default";
        }
        return bundle.getString(KEY_DEFAULT_TITLE);
    }

    public static String getDefaultActivityDataForecastCalendarIconId(Bundle bundle){
        if(bundle == null){
            return "1";
        }
        return bundle.getString(KEY_DEFAULT_ID1);
    }

    public static String getDefaultActivityDataForecastCalendarYearId(Bundle bundle){
        if(bundle == null){
            return "1";
        }
        return bundle.getString(KEY_DEFAULT_ID2);
    }

    public static String getDefaultActivityDataForecastCalendarDataType(Bundle bundle){
        if(bundle == null){
            return "1";
        }
        return bundle.getString(KEY_DEFAULT_ID3);
    }

    public static Bundle setDutyOfficer(String title, String body){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USER_DATA, title);
        bundle.putString(KEY_DEFAULT_TAG, body);
        return bundle;
    }

    public static String getDutyOfficerTitle(Bundle bundle){
        if(bundle == null){
            return "Default";
        }
        return bundle.getString(KEY_USER_DATA);
    }

    public static String getDutyOfficerBody(Bundle bundle){
        if(bundle == null){
            return "Default";
        }
        return bundle.getString(KEY_DEFAULT_TAG);
    }

    public static Bundle setForecastDate(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USER_DATA, date);
        return bundle;
    }

    public static Bundle setForecastDateDefault() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USER_DATA, "");
        return bundle;
    }

    public static String getForecastDate(Bundle bundle){
        if(bundle == null){
            return "Default";
        }
        return bundle.getString(KEY_USER_DATA);
    }

    public static Bundle setFacebookData(FacebookModel model) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_USER_DATA, model);
        return bundle;
    }

    public static Bundle setFacebookData(String  model) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USER_DATA + "FbEmail", model);
        return bundle;
    }

    public static String getFacebookEmail(Bundle bundle){
        if(bundle == null){
            return "";
        }
        return bundle.getString(KEY_USER_DATA + "FbEmail");
    }

    public static FacebookModel getFacebookData(Bundle bundle){
        if(bundle == null){
            return null;
        }
        return bundle.getParcelable(KEY_USER_DATA);
    }

    public static Bundle setBook(Books data, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_USER_DATA, data);
        bundle.putInt(KEY_DEFAULT_ID1, position);
        return bundle;
    }

    public static Books getBook(Bundle bundle){
        if(bundle == null){
            return null;
        }
        return bundle.getParcelable(KEY_USER_DATA);
    }

    public static int getBookId(Bundle bundle){
        if(bundle == null){
            return -1;
        }
        return bundle.getInt(KEY_DEFAULT_ID1);
    }


    public static Bundle setBookPdf(byte[] data) {
        Bundle bundle = new Bundle();
        bundle.putByteArray(KEY_USER_DATA + "Byte", data);
        return bundle;
    }


    public static byte[] getBookPdf(Bundle bundle){
        if(bundle == null){
            return new byte[1];
        }
        return bundle.getByteArray(KEY_USER_DATA + "Byte");
    }

    //calendar-----------------------

    public static Bundle setCalendarData(int monthId, int day, String month) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_USER_DATA + "monthId", monthId);
        bundle.putInt(KEY_USER_DATA + "day", day);
        bundle.putString(KEY_USER_DATA + "month", month);

        return bundle;
    }

    public static int getCalendarMonthId(Bundle bundle){
        if(bundle == null){
            return -1;
        }
        return bundle.getInt(KEY_USER_DATA + "monthId");
    }

    public static int getCalendarDay(Bundle bundle){
        if(bundle == null){
            return 0;
        }
        return bundle.getInt(KEY_USER_DATA + "day");
    }

    public static String getCalendarMonth(Bundle bundle){
        if(bundle == null){
            return "";
        }
        return bundle.getString(KEY_USER_DATA + "month");
    }
}
