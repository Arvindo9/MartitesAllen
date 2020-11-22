package com.maritesallen.almanac2020.ui.dashboard.forecast.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Forecast;
import com.maritesallen.almanac2020.databinding.AdapterForecastBinding;

import java.util.ArrayList;

import static com.maritesallen.almanac2020.utils.AppConstants.URL_SHOP_NOW;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class ForecastAdapter extends BaseAdapter<AdapterForecastBinding, Forecast> {
/*
    private Listener listener;

    public interface Listener {
        void onRetryClick();
    }

    public void addItems(List<Forecast> model) {
        list.addAll(model);
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
        mainList.clear();
        notifyDataSetChanged();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
*/


    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public ForecastAdapter(ArrayList<Forecast> adapterList) {
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
        return R.layout.adapter_forecast;
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder<B, D>(binding);
     */
    @Override
    public ViewHolder getViewHolder(AdapterForecastBinding binding) {
        return new ViewHolder<AdapterForecastBinding, Forecast>(binding){
            @Override
            protected void doSomeWorkHere(AdapterForecastBinding binding, Forecast data, int position) {

            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
             */
            @Override
            protected void bindData(Forecast data) {
//                binding.setCalendarData(new ForecastAdapterViewModel(CalendarData));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, AdapterForecastBinding binding, Forecast data) {
                binding.upgrade.setOnClickListener(thisContext);
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
            protected ViewHolderClickListener viewHolderReference(AdapterForecastBinding binding,
                                                                  Forecast data, int position) {
                return new ViewHolderClickListener<AdapterForecastBinding, Forecast>(binding, data, position){
                    /**
                     * Called when a view has been clicked.
                     *
                     * @param view The view that was clicked.
                     *             switch (view.getId()){
                     *             case R.id.id:
                     *             // itemView.getContext().startActivity();
                     *             break;
                     *             }
                     */
                    @Override
                    public void onClick(View view) {
                        switch ((view.getId())){
                            case R.id.upgrade:
                                Uri uri = Uri.parse(URL_SHOP_NOW); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                itemView.getContext().startActivity(intent);
                                break;
                        }
                    }
                };
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
