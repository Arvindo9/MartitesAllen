package com.maritesallen.almanac2020.utils.calenderTmp.calenderAdapter.calenderSymbolAdapter;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.apis.calender.Calenders;
import com.maritesallen.almanac2020.databinding.AdapterCalenderSymbolBinding;

import java.util.ArrayList;

/**
 * Author : Arvindo Mondal
 * Created on 02-12-2019
 */
public class CalenderSymbolAdapter extends BaseAdapter<AdapterCalenderSymbolBinding, Calenders> {


    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public CalenderSymbolAdapter(ArrayList<Calenders> adapterList) {
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
        return R.layout.adapter_calender_symbol;
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder<B, D>(binding);
     */
    @Override
    public ViewHolder getViewHolder(AdapterCalenderSymbolBinding binding) {
        return new ViewHolder<AdapterCalenderSymbolBinding, Calenders>(binding){
            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding  layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(AdapterCalenderSymbolBinding binding, Calenders data, int position) {
//                binding.icon.setImageDrawable(VectorDrawableCompat.create(itemView.getResources(), R.drawable.almanac_icons_01, null));
                binding.icon.setImageResource(R.drawable.almanac_icons_01);
            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
             */
            @Override
            protected void bindData(Calenders data) {
//                binding.setData(new CalenderSymbolAdapterViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, AdapterCalenderSymbolBinding binding, Calenders data) {

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
            protected ViewHolderClickListener viewHolderReference(AdapterCalenderSymbolBinding binding, Calenders data, int position) {
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
