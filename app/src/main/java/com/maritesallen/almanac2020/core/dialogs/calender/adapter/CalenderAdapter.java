package com.maritesallen.almanac2020.core.dialogs.calender.adapter;

import android.view.View;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.db.calendar.months.Months;
import com.maritesallen.almanac2020.databinding.AdapterCalenderDialogBinding;

import java.util.ArrayList;

public class CalenderAdapter extends BaseAdapter<AdapterCalenderDialogBinding, Months> {

    private Listener listener;

    public interface Listener {
        void onRetryClick();

        void onMonthClick(int id, String month);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public CalenderAdapter(ArrayList<Months> adapterList) {
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
        return R.layout.adapter_calender_dialog;
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder<B, D>(binding);
     */
    @Override
    public ViewHolder getViewHolder(AdapterCalenderDialogBinding binding) {
        return new ViewHolder<AdapterCalenderDialogBinding, Months>(binding){
            /**
             * If there is anything to do then do here otherwise leave it blank
             *
             * @param binding  layout reference for single view
             * @param data     for single view
             * @param position position of ArrayList
             */
            @Override
            protected void doSomeWorkHere(AdapterCalenderDialogBinding binding, Months data, int position) {

            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
             */
            @Override
            protected void bindData(Months data) {
                binding.setData(new CalenderAdapterViewModel(data, position == list.size()-1));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext,
                                          AdapterCalenderDialogBinding binding, Months data) {
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
            protected ViewHolderClickListener viewHolderReference(AdapterCalenderDialogBinding binding,
                                                                  Months data, int position) {
                return new ViewHolderClickListener<AdapterCalenderDialogBinding, Months>(binding, data, position){
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
                            listener.onMonthClick(data.getId(), data.getMonth());
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
