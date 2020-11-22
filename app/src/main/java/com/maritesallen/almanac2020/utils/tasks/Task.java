package com.maritesallen.almanac2020.utils.tasks;

import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;

import java.io.InputStream;

import okhttp3.RequestBody;

/**
 * Author       : Arvindo Mondal
 * Created on   : 22-05-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public interface Task {

    interface TaskCallBack{
        void onResponse(String responseCode, Object object);

        void onError(Throwable throwable);
    }

    interface TaskCallBackDownload{
        void onResponse(String responseCode, Object body, Books model);

        void onError(Throwable throwable);
    }

    void downloadFile(TaskCallBackDownload callBack, Books model);

    void downloadFile(TaskCallBack callBack, BookPremium model);

    void downloadCalendar();

    void loadTerms();
}
