package com.maritesallen.almanac2020.base;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-05-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public interface BaseNavigator {

    void handleError(Throwable throwable);

    void handleMessage(String message);

    void handleMessage(int index);
}
