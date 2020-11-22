package com.maritesallen.almanac2020.data;


import com.maritesallen.almanac2020.data.local.db.DatabaseService;
import com.maritesallen.almanac2020.data.local.prefs.PreferencesService;
import com.maritesallen.almanac2020.data.remote.APIService;

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
public interface DataManager extends DatabaseService, PreferencesService, APIService {

    enum LoggedInMode {
        LOGGED_IN_MODE_FIRST_TIME(0),
        LOGGED_IN_MODE_LOGGED_OUT(1),                       //login screen
        LOGGED_IN_MODE_LOGGED_IN(2),
        LOGGED_IN_MODE_LOGGED_IN_FACEBOOK(3),
        LOGGED_IN_MODE_LOGGED_IN_GUEST(4);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
