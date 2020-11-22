package com.maritesallen.almanac2020.core.dialogs.book;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.databinding.ObservableField;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

/**
 * Author : Arvindo Mondal
 * Created on 17-12-2019
 */
public class BookPurchaseViewModel extends BaseViewModel<BookPurchaseNavigator> {

    public final ObservableField<String> purchaseAmount;
    public final ObservableField<String> bookCover;
    public final ObservableField<String> discription;
    public final ObservableField<String> title;

    public BookPurchaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        purchaseAmount = new ObservableField<>();
        bookCover = new ObservableField<>();
        discription = new ObservableField<>();
        title = new ObservableField<>();
    }

    void loadData(Books model) {

        Spanned currencyCode;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currencyCode = Html.fromHtml(model.getCurrencyCode(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            currencyCode = Html.fromHtml(model.getCurrencyCode());
        }

        purchaseAmount.set("   " + "Buy for " + currencyCode + "" + model.getPrice() + "   ");
        bookCover.set(model.getCoverImageFlat());
        discription.set(model.getDescription());
        title.set(model.getTitle());
    }

    //resource---------

    public void onCancelClick(){
        getNavigator().onCancelClick();
    }
    public void onPurchaseClick(){
        getNavigator().onPurchaseClick();
    }

    public void cancel() {
        setIsLoading(false);
    }
}
