package com.maritesallen.almanac2020.core.dialogs.alert;

import com.maritesallen.almanac2020.base.BaseNavigator;

/**
 * Author       : Arvindo Mondal
 * Created on   : 26-08-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
interface AlertNavigator extends BaseNavigator {
    void onCancelClick();

    void onYesClick();

    void onNoClick();
}
