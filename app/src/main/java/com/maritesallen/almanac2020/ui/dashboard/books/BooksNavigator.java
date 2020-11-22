package com.maritesallen.almanac2020.ui.dashboard.books;

import com.maritesallen.almanac2020.data.model.apis.books.Books;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
interface BooksNavigator {
    void downloadFileStatus(boolean status);

    void showMessage(int message);

    void downloadStatus(boolean status);

    void downloadFile(Books model);
}
