package com.maritesallen.almanac2020.ui.defaultActivity.forecastCalendarAdapter;

import android.graphics.Color;
import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastCalendar.ForecastCalender;
import com.maritesallen.almanac2020.databinding.AdapterForecastActivityCalendarBinding;

import java.util.ArrayList;

/**
 * Author : Arvindo Mondal
 * Created on 09-12-2019
 */
public class ForecastCalendarAdapter extends BaseAdapter<AdapterForecastActivityCalendarBinding, ForecastCalender> {

    public static final int TYPE_MONTH = 1;
    public static final int TYPE_DAY = 2;

    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public ForecastCalendarAdapter(ArrayList<ForecastCalender> adapterList) {
        super(adapterList);
    }

    /**
     * @param position current index of ArrayList
     * @return return 0 if single layout xml else override this method for multiple xml or elements
     */
    @Override
    protected int getItemViewTypeAdapter(int position) {
        return 0;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.adapter_forecast_activity_calendar;
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder<B, D>(binding);
     */
    @Override
    public ViewHolder getViewHolder(AdapterForecastActivityCalendarBinding binding) {
        return new ViewHolder<AdapterForecastActivityCalendarBinding, ForecastCalender>(binding){
            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding  layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(AdapterForecastActivityCalendarBinding binding, ForecastCalender data, int position) {
                if(data.getType() == TYPE_MONTH){
                    binding.layoutMonth.setVisibility(View.VISIBLE);
                    binding.layoutDay.setVisibility(View.GONE);
                    binding.layoutMonth.setBackgroundColor(Color.parseColor(data.getMonthColor()));
                }
                else{
                    binding.layoutDay.setVisibility(View.VISIBLE);
                    binding.layoutMonth.setVisibility(View.GONE);
                }
            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
             */
            @Override
            protected void bindData(ForecastCalender data) {
                binding.setData(new ForecastCalendarViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, AdapterForecastActivityCalendarBinding binding, ForecastCalender data) {

            }

            /**
             * Initialised holder by new operator
             *
             * @param binding  DataBinding
             * @param data     dataList
             * @param position of adapter
             * @return new ViewHolderClickListener<B, D>(binding, CalendarData, position)
             */
            @Override
            protected ViewHolderClickListener viewHolderReference(AdapterForecastActivityCalendarBinding binding, ForecastCalender data, int position) {
                return null;
            }
        };
    }

    /**
     * @return new FilterClass();
     */
    @Override
    protected FilterClass initialisedFilterClass() {
        return null;
    }
}
