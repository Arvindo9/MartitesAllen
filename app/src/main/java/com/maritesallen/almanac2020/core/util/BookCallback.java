package com.maritesallen.almanac2020.core.util;

import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;

import java.io.File;

/**
 * Author : Arvindo Mondal
 * Created on 17-12-2019
 */
public interface BookCallback {

    void onFileDecryptionSuccess(File file);

    void onBookDecrypt(byte[] bytes, int requestCode);

    void onResponse(int statusCode, Books model);

    void onError(int statusCode, Throwable throwable);

    interface BookPrrmium{
        void onResponse(int statusCode, BookPremium model);
        void onError(int statusCode, Throwable throwable);
    }
}
