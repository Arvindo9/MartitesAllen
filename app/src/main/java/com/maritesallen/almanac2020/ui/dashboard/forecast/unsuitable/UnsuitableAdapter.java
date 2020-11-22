package com.maritesallen.almanac2020.ui.dashboard.forecast.unsuitable;

import android.os.Bundle;
import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Unsuitable;
import com.maritesallen.almanac2020.databinding.AdapterForecastUnsuitableBinding;
import com.maritesallen.almanac2020.ui.dashboard.forecast.suitable.SuitableAdapter;
import com.maritesallen.almanac2020.ui.defaultActivity.DefaultActivity;
import com.maritesallen.almanac2020.utils.bundles.Bundles;

import java.util.ArrayList;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class UnsuitableAdapter extends BaseAdapter<AdapterForecastUnsuitableBinding, Unsuitable> {
    public static final String TAG = UnsuitableAdapter.class.getSimpleName();
    private SuitableAdapter.Listener listener;

    public interface Listener {
        void onRetryClick();

        void onSuitableIconCLick(Bundle bundle);
    }

    public void setListeners(SuitableAdapter.Listener listener) {
        this.listener = listener;
    }


    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public UnsuitableAdapter(ArrayList<Unsuitable> adapterList) {
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
        return new ViewHolder<AdapterForecastUnsuitableBinding, Unsuitable>(binding){
            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding  layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(AdapterForecastUnsuitableBinding binding, Unsuitable data, int position) {
                binding.forecastLayout.setVisibility(View.VISIBLE);
                binding.defaultLayout.setVisibility(View.GONE);
            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
             */
            @Override
            protected void bindData(Unsuitable data) {
                binding.setData(new UnsuitableViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, AdapterForecastUnsuitableBinding binding, Unsuitable data) {
                binding.forecastLayout.setOnClickListener(thisContext);
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
            protected ViewHolderClickListener viewHolderReference(AdapterForecastUnsuitableBinding binding, Unsuitable data, int position) {
                return new ViewHolderClickListener<AdapterForecastUnsuitableBinding, Unsuitable>(binding, data, position){
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
                        switch (view.getId()){
                            case R.id.forecastLayout:
                   /*
                                Intent i = new Intent(itemView.getContext(), DefaultActivity.class);
                                i.putExtra(AppConstants.KEY_DEFAULT_ACTIVITY_BUNDLE,
                                        Bundles.setDefaultActivityForecastCalendar(TAG,
                                                DefaultActivity.TYPE_FORECAST_CALENDAR, data.getId(), data.getYear(), "-1"));
                                itemView.getContext().startActivity(i);
*/

                                listener.onSuitableIconCLick(Bundles.setDefaultActivityForecastCalendar(TAG,
                                        DefaultActivity.TYPE_FORECAST_CALENDAR, data.getId(), data.getYear(), "-1"));
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
