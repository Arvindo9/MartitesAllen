/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.maritesallen.almanac2020.utils;

import android.os.Build;

import com.maritesallen.almanac2020.BuildConfig;

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

public final class AppConstants {

//    INSTANCE;

    public static final String FIREBASE_NOTIFICATION_TOPIC = "NOTIFICATION_TOPIC_GENERAL";
    public static final String ADS_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
    public static final int DEFAULT_YEAR = 2020;

    public static final int PAYMENT_TYPE_PREMIUM = 1;
    public static final int PAYMENT_TYPE_BOOK = 2;


    public static final int PUSH_NOTIFICATION_ID = 1201;
    public static final String CHANNEL_NAME = BuildConfig.APPLICATION_ID + " Service";
    public static final String CHANNEL_ID = BuildConfig.APPLICATION_ID;

    public static final String NOTIFICATION_TOPIC_GENERAL = "NOTIFICATION_TOPIC_GENERAL";
    public static final String NOTIFICATION_TOPIC_LOGOUT = "NOTIFICATION_TOPIC_LOGOUT";

    public static final String LOGOUT_CODE = "100";
    public static final String BLOCK_CODE = "200";
    public static final String KEY_FROM_SERVICE = "KEY_FROM_SERVICE";
    public static final String INTENT_SERVER_RECEIVE = "LogoutSession";

    public static byte[] pdfByte;

    AppConstants() {
        // This utility class is not publicly instantiable
    }

    public static final String DB_NAME = "arvindo.db";

    public static final String PASS_PHRASE_FIELD = "PASS_PHRASE_FIELD.db";

    public static final String PREF_NAME = "arvindo_pref";

    public static final String GENERAL_DATE_FORMAT = "dd MMMM yyyy";

    public static final String DATE_TIME_NAME_FORMAT = "yyyyMMdd_HHmmss";

    public static final String DATE_SEND_FORMAT = "yyyy-MM-dd";

    public static final String DATE_INT_FORMAT = "yyyyMMdd";

    public static final int REQUEST_PERMISSIONS = 20;

    public static final String KEY_DEFAULT_ACTIVITY_BUNDLE = "KEY_DEFAULT_ACTIVITY_BUNDLE";

    public static final String URL_SHOP_NOW = "https://frigga.co.uk";

    //Default initialisation-----------

    public static final boolean YES_TRUE = true;

    //Dialog size----------------------

    public static final int DIALOG_SIZE_MEDIAM = 1;
    public static final int DIALOG_SIZE_FULL = 2;
    public static final int DIALOG_SIZE_FULL_SCREEN = 3;
    public static final int DIALOG_SIZE_FULL_MARGIN = 4;

    //Terms----------------------------

    public static final String PRIVACY = "privacy";
    public static final String TERMS = "terms";
    public static final String DISCLAIMER = "disclaimer";
    public static final String ABOUT = "about";
    public static final String APP_INFO = "appinfo";

    //-----------

    public static final String YES = "YES";
    public static final String NO = "NO";

    //Folder----------

    public static final String APP_DIRECTORY = "MaritesAllen";


}