package com.maritesallen.almanac2020.ui.dashboard.books.adapter;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.data.model.apis.books.Books;

public class BooksAdapterViewModel {

    public final ObservableField<String> prise;
    public final ObservableField<String> book;

    public BooksAdapterViewModel(Books data) {
        book = new ObservableField<>(data.getCoverImage());
        Spanned currencyCode;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currencyCode = Html.fromHtml(data.getCurrencyCode(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            currencyCode = Html.fromHtml(data.getCurrencyCode());
        }
        prise = new ObservableField<>(currencyCode + "" + data.getPrice());
    }
}
