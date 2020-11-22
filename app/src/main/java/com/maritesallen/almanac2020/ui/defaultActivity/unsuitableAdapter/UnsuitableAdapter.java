package com.maritesallen.almanac2020.ui.defaultActivity.unsuitableAdapter;

import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.UnSuitableActivityList;
import com.maritesallen.almanac2020.databinding.AdapterForecastUnsuitableBinding;

import java.util.ArrayList;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class UnsuitableAdapter extends BaseAdapter<AdapterForecastUnsuitableBinding, UnSuitableActivityList> {

    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public UnsuitableAdapter(ArrayList<UnSuitableActivityList> adapterList) {
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
        return R.layout.adapter_forecast_unsuitable;
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder<B, D>(binding);
     */
    @Override
    public ViewHolder getViewHolder(AdapterForecastUnsuitableBinding binding) {
        return new ViewHolder<AdapterForecastUnsuitableBinding, UnSuitableActivityList>(binding){
            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding  layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(AdapterForecastUnsuitableBinding binding, UnSuitableActivityList data, int position) {
                binding.forecastLayout.setVisibility(View.GONE);
                binding.defaultLayout.setVisibility(View.VISIBLE);
            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
             */
            @Override
            protected void bindData(UnSuitableActivityList data) {
                binding.setDataDefault(new UnsuitableViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, AdapterForecastUnsuitableBinding binding,
                                          UnSuitableActivityList data) {

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
            protected ViewHolderClickListener viewHolderReference(AdapterForecastUnsuitableBinding binding,
                                                                  UnSuitableActivityList data, int position) {
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
