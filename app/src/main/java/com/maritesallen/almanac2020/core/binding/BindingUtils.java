/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.maritesallen.almanac2020.core.binding;

import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.views.BaseLinearTextHorizontal;
import com.maritesallen.almanac2020.core.dialogs.country.countryAdapter.CountryAdapter;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.apis.calender.activity.Activity;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastSlider.Slider;
import com.maritesallen.almanac2020.data.model.db.flag.Flag;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ActivityList;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Forecast;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Suitable;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.UnSuitableActivityList;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Unsuitable;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.ForecastCalender;
import com.maritesallen.almanac2020.data.model.db.calendar.months.Months;
import com.maritesallen.almanac2020.ui.dashboard.books.adapter.BooksAdapter;
import com.maritesallen.almanac2020.ui.dashboard.calender.calenderAdapter.CalenderAdapter;
import com.maritesallen.almanac2020.ui.dashboard.forecast.adapter.ForecastAdapter;
import com.maritesallen.almanac2020.ui.dashboard.forecast.flystar.FlyStarPager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.ghostMonth.GhostMonthPager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager.imagePager.ForecastImagePager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.suitable.SuitableAdapter;
import com.maritesallen.almanac2020.ui.dashboard.forecast.unsuitable.UnsuitableAdapter;
import com.maritesallen.almanac2020.ui.defaultActivity.forecastCalendarAdapter.ForecastCalendarAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 09-05-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public final class BindingUtils {
    //ObservableField must not have final
    public static ObservableField<Boolean> progressBarVisibility = new ObservableField<>();

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"ImageViewUrl"})
    public static void setImageUrl(ImageView imageView, String url) {
        if(url != null && !url.isEmpty()) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.blank_image)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .fit()
                    .centerCrop()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            //Try again online if cache failed
                            Picasso.get()
                                    .load(url)
                                    .placeholder(R.drawable.blank_image)
                                    .error(R.drawable.blank_image)
                                    .fit()
                                    .centerCrop()
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
//                                        e.printStackTrace();
//                                        Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });
        }
        else{
            Picasso.get()
                    .load(R.drawable.blank_image)
                    .placeholder(R.drawable.blank_image)
//                    .error(R.drawable.blank_image)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }
    }

    @BindingAdapter({"ImageViewUrlXy"})
    public static void setImageUrlFitXy(ImageView imageView, String url) {
        if(url != null && !url.isEmpty()) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.blank_image)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .fit()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            //Try again online if cache failed
                            Picasso.get()
                                    .load(url)
                                    .placeholder(R.drawable.blank_image)
                                    .error(R.drawable.blank_image)
                                    .fit()
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
//                                        e.printStackTrace();
//                                        Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });
        }
        else{
            Picasso.get()
                    .load(R.drawable.blank_image)
                    .placeholder(R.drawable.blank_image)
                    .fit()
//                    .error(R.drawable.blank_image)
                    .into(imageView);
        }
    }

    @BindingAdapter({"ImageViewUrlXyFit"})
    public static void setImageUrlFitXyFit(ImageView imageView, String url) {
        if(url != null && !url.isEmpty()) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.card_holder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .fit()
                    .centerInside()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            //Try again online if cache failed
                            Picasso.get()
                                    .load(url)
                                    .placeholder(R.drawable.card_holder)
                                    .error(R.drawable.card_holder)
                                    .fit()
                                    .centerInside()
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
//                                        e.printStackTrace();
//                                        Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });
        }
        else{
            Picasso.get()
                    .load(R.drawable.card_holder)
                    .placeholder(R.drawable.card_holder)
//                    .error(R.drawable.blank_image)
                    .fit()
                    .centerInside()
                    .into(imageView);
        }
    }


    @BindingAdapter({"ImageViewUrlProfile"})
    public static void setImageUrlProfile(ImageView imageView, String url) {
        if (Build.VERSION.SDK_INT < 21) {
            if (url != null && !url.isEmpty()) {
                Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.user_profile_img)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .fit()
//                    .centerCrop()
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                //Try again online if cache failed
                                Picasso.get()
                                        .load(url)
                                        .placeholder(R.drawable.user_profile_img)
                                        .error(R.drawable.user_profile_img)
                                        .fit()
//                                    .centerCrop()
                                        .into(imageView, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {
//                                        e.printStackTrace();
//                                        Log.v("Picasso","Could not fetch image");
                                            }
                                        });
                            }
                        });
            } else {
                Picasso.get()
                        .load(R.drawable.user_profile_img)
                        .placeholder(R.drawable.user_profile_img)
//                    .error(R.drawable.blank_image)
                        .fit()
//                    .centerCrop()
                        .into(imageView);
            }
        }
        else{
            if (url != null && !url.isEmpty()) {
                Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.ic_user_profile_default)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .fit()
//                    .centerCrop()
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                //Try again online if cache failed
                                Picasso.get()
                                        .load(url)
                                        .placeholder(R.drawable.ic_user_profile_default)
                                        .error(R.drawable.ic_user_profile_default)
                                        .fit()
//                                    .centerCrop()
                                        .into(imageView, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {
//                                        e.printStackTrace();
//                                        Log.v("Picasso","Could not fetch image");
                                            }
                                        });
                            }
                        });
            } else {
                Picasso.get()
                        .load(R.drawable.ic_user_profile_default)
                        .placeholder(R.drawable.ic_user_profile_default)
//                    .error(R.drawable.blank_image)
                        .fit()
//                    .centerCrop()
                        .into(imageView);
            }
        }

    }

    @BindingAdapter({"ImageView"})
    public static void setImageView(ImageView imageView, File file) {
        Picasso.get()
                .load(file)
                .placeholder(R.drawable.blank_image)
                .error(R.drawable.blank_image)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @BindingAdapter({"ImageViewUri"})
    public static void setImageViewUri(ImageView imageView, Uri uri) {
        Picasso.get()
                .load(uri)
                .placeholder(R.drawable.blank_image)
                .error(R.drawable.blank_image)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @BindingAdapter({"ImageViewDrawable"})
    public static void setImageViewDrawable(ImageView imageView,@DrawableRes int index) {
        Picasso.get()
                .load(index)
                .placeholder(R.drawable.background)
                .error(R.drawable.blank_image)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    //List view-----------------------------------

    @BindingAdapter({"ForecastAdapter"})
    public static void addForecastAdapter(RecyclerView recyclerView, List<Forecast> list) {
        ForecastAdapter adapter = (ForecastAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"CalenderAdapter"})
    public static void addCalenderAdapter(RecyclerView recyclerView, List<CalendarModel> list) {
        CalenderAdapter adapter = (CalenderAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"BooksAdapter"})
    public static void addBooksAdapter(RecyclerView recyclerView, List<Books> list) {
        BooksAdapter adapter = (BooksAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"CountryAdapter"})
    public static void addCountryAdapter(RecyclerView recyclerView, List<Flag> list) {
        CountryAdapter adapter = (CountryAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"CalenderMonthAdapter"})
    public static void addCalenderMonthAdapter(RecyclerView recyclerView, List<Months> list) {
        com.maritesallen.almanac2020.core.dialogs.calender.adapter.CalenderAdapter adapter =
                (com.maritesallen.almanac2020.core.dialogs.calender.adapter.CalenderAdapter)
                        recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"BaseCalenderIcon"})
    public static void addBaseCalenderIcon(BaseLinearTextHorizontal layout, List<Activity> list) {
        if (layout != null) {
            layout.clearItems();
//            layout.addItems(list, list.get(viewType).getDate());
        }
    }

    @BindingAdapter({"SuitableAdapter"})
    public static void addSuitableAdapter(RecyclerView recyclerView, List<Suitable> list) {
        SuitableAdapter adapter = (SuitableAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"UnsuitableAdapter"})
    public static void addUnsuitableAdapter(RecyclerView recyclerView, List<Unsuitable> list) {
        UnsuitableAdapter adapter = (UnsuitableAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"DefaultSuitableAdapter"})
    public static void addDefaultSuitableAdapter(RecyclerView recyclerView, List<ActivityList> list) {
        com.maritesallen.almanac2020.ui.defaultActivity.suitableAdapter.SuitableAdapter adapter =
                (com.maritesallen.almanac2020.ui.defaultActivity.suitableAdapter.SuitableAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"DefaultUnsuitableAdapter"})
    public static void addDefaultUnsuitableAdapter(RecyclerView recyclerView, List<UnSuitableActivityList> list) {
        com.maritesallen.almanac2020.ui.defaultActivity.unsuitableAdapter.UnsuitableAdapter adapter =
                (com.maritesallen.almanac2020.ui.defaultActivity.unsuitableAdapter.UnsuitableAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }


    @BindingAdapter({"DefaultForecastCalendarAdapter"})
    public static void addDefaultForecastCalendarAdapter(RecyclerView recyclerView, List<ForecastCalender> list) {
        ForecastCalendarAdapter adapter = (ForecastCalendarAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"ForecastImagePager"})
    public static void addForecastImagePager(ViewPager recyclerView, List<Slider> list) {
        ForecastImagePager adapter = (ForecastImagePager) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }


    @BindingAdapter({"GhostMonthPager"})
    public static void addGhostMonthPager(ViewPager recyclerView, List<com.maritesallen.almanac2020.data.model.apis.slider.Slider> list) {
        GhostMonthPager adapter = (GhostMonthPager) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

    @BindingAdapter({"FlyStarPager"})
    public static void addFlyStarPager(ViewPager recyclerView, List<com.maritesallen.almanac2020.data.model.apis.slider.Slider> list) {
        FlyStarPager adapter = (FlyStarPager) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(list);
        }
    }

}
