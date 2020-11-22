package com.maritesallen.almanac2020.ui.defaultActivity.suitableAdapter;

import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ActivityList;
import com.maritesallen.almanac2020.databinding.AdapterForecastSuitableBinding;

import java.util.ArrayList;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class SuitableAdapter extends BaseAdapter<AdapterForecastSuitableBinding, ActivityList> {

    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public SuitableAdapter(ArrayList<ActivityList> adapterList) {
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
        return R.layout.adapter_forecast_suitable;
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder<B, D>(binding);
     */
    @Override
    public ViewHolder getViewHolder(AdapterForecastSuitableBinding binding) {
        return new ViewHolder<AdapterForecastSuitableBinding, ActivityList>(binding){
            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding  layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(AdapterForecastSuitableBinding binding, ActivityList data, int position) {
                binding.forecastLayout.setVisibility(View.GONE);
                binding.defaultLayout.setVisibility(View.VISIBLE);
            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
             */
            @Override
            protected void bindData(ActivityList data) {
                binding.setDataDefault(new SuitableViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, AdapterForecastSuitableBinding binding, ActivityList data) {

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
            protected ViewHolderClickListener viewHolderReference(AdapterForecastSuitableBinding binding, ActivityList data, int position) {
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
