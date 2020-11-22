package com.maritesallen.almanac2020.core.dialogs;

import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-08-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public interface DialogListener {

    /**
     * Default response method of a dialog
     * @param tag class name from which the response is getting
     * @param params string array with relative CalendarData
     */
    void onSuccessDialogResponse(String tag, String... params);

    interface DialogListenerModelList{
        /**
         *
         * Default response method of a dialog Model list
         * @param tag class name from which the response is getting
         * @param modelList DialogModel list with relative CalendarData
         */
        void onSuccessDialogResponse(String tag, List<DialogModel> modelList);
    }

    interface DialogtListenerModel{
        /**
         *
         * Default response method of a dialog Model list
         * @param tag class name from which the response is getting
         * @param model DialogModel with relative CalendarData
         */
        void onSuccessDialogResponse(String tag, DialogModel model);
    }
}
