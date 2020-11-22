package com.maritesallen.almanac2020.data.model.apis.books;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 16-12-2019
 */
public class Data {

    @SerializedName("book")
    @Expose
    private List<Books> books = null;


    @SerializedName("books")
    @Expose
    private List<BookPremium> bookPremium = null;

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public List<BookPremium> getBookPremium() {
        return bookPremium;
    }

    public void setBookPremium(List<BookPremium> bookPremium) {
        this.bookPremium = bookPremium;
    }
}
