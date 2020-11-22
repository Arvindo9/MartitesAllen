package com.maritesallen.almanac2020.core.dialogs.country.countryAdapter;

import android.content.Context;
import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.db.flag.Flag;
import com.maritesallen.almanac2020.databinding.AdapterCountryBinding;

import java.util.ArrayList;

/**
 * Author : Arvindo Mondal
 * Created on 29-11-2019
 */
public class CountryAdapter extends BaseAdapter<AdapterCountryBinding, Flag> {

    private Listener listener;

    public interface Listener {
        void onRetryClick();
        void onCountrySelected(String id, String country);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public CountryAdapter(ArrayList<Flag> adapterList) {
        super(adapterList);
    }

    @Override
    protected int getItemViewTypeAdapter(int position) {
        return 0;
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_country;
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder<B, D>(binding);
     */
    @Override
    public ViewHolder getViewHolder(AdapterCountryBinding binding) {
        return new ViewHolder<AdapterCountryBinding, Flag>(binding){

            @Override
            protected void doSomeWorkHere(AdapterCountryBinding binding, Flag data, int position) {

            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
             */
            @Override
            protected void bindData(Flag data) {
                binding.setData(new CountryAdapterViewModel(data, position == list.size()-1));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, AdapterCountryBinding binding, Flag data) {
                binding.layout.setOnClickListener(thisContext);
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
            protected ViewHolderClickListener viewHolderReference(AdapterCountryBinding binding, Flag data, int position) {
                return new ViewHolderClickListener<AdapterCountryBinding, Flag>(binding, data, position){
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
                        if(view.getId() == R.id.layout){
                            listener.onCountrySelected(data.getCountryCode(), data.getCountryName());

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
        return new FilterClass() {
            /**
             * @return Context, to initialise and use filter class pass activity or application context
             */
            @Override
            public Context getContext() {
                return null;
            }

            /**
             * @param constraint
             * @param list              only one time, use in for loop
             * @param filteredArrayList JobsResponse
             *                          for (L CalendarData : list) {
             *                          if (CalendarData.getRefNo().toLowerCase().contains(constraint) ||
             *                          CalendarData.getZone().toLowerCase().contains(constraint))
             *                          filteredArrayList.add(CalendarData);
             *                          }
             */
            @Override
            public ArrayList<Flag> filterData(CharSequence constraint, ArrayList<Flag> list, ArrayList<Flag> filteredArrayList) {
                for (Flag model : list) {
                    if (model.getCountryName().toLowerCase().contains(constraint))
                    filteredArrayList.add(model);
                }
                return filteredArrayList;
            }
        };
    }

}
