package com.maritesallen.almanac2020.data.model.apis.purchase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 28-12-2019
 */
public class Data {

    @SerializedName("books")
    @Expose
    private List<BookPremium> bookPremium = null;

    public List<BookPremium> getBookPremium() {
        return bookPremium;
    }

    public void setBookPremium(List<BookPremium> bookPremium) {
        this.bookPremium = bookPremium;
    }
}
