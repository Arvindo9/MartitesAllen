package com.maritesallen.almanac2020.utils.calenderTmp.calenderAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.base.views.BaseLinearTextHorizontal;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;
import com.maritesallen.almanac2020.databinding.AdapterCalenderBinding;
import com.maritesallen.almanac2020.databinding.CalenderTopCardBinding;
import com.maritesallen.almanac2020.databinding.LayoutAdsBinding;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class CalenderAdapter extends BaseAdapter<AdapterCalenderBinding, CalendarModel> implements
        BaseLinearTextHorizontal.Listener {

    private Listener listener;
    private int viewBigCard = -11;
    private int viewAds = -13;
    private Context context;
    private int currentMonth;
    private int currentDay;


    public interface Listener {
        void onRetryClick();

        void onMonthReach(int monthId);

        void onCalendarLaunch(int monthId, int day, String month);
    }

    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public CalenderAdapter(ArrayList<CalendarModel> adapterList) {
        super(adapterList);
    }

    public void setCurrentDate(int currentMonth, int currentDay) {
        this.currentMonth = currentMonth;
        this.currentDay = currentDay;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected int getItemViewTypeAdapter(int position) {
        if(list.get(position) == null){
            return viewAds;
        }
        else if(list.get(position).isMonthCalendar()){
            return viewBigCard;
        }
        return 0;
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_calender;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == viewAds){
//            LayoutAdsBinding binding = (LayoutAdsBinding)
//                    createBinding(parent, viewType, R.layout.layout_ads);
////            new CalenderAdapterViewModel();
//            return adsViewHolder(binding);
            return new AdviewHolder(googleAdsBanner());
        }
        else if(viewType == viewBigCard){
            CalenderTopCardBinding binding = (CalenderTopCardBinding)
                    createBinding(parent, viewType, R.layout.calender_top_card);
//            new CalenderAdapterViewModel();
            return getCardViewHolder(binding);
        }
        else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    private AdView googleAdsBanner(){
        AdView adview = new AdView(context);
        adview.setAdSize(AdSize.LARGE_BANNER);

        // this is the good adview
        adview.setAdUnitId(AppConstants.ADS_UNIT_ID);

        float density = context.getResources().getDisplayMetrics().density;
        int height = Math.round(AdSize.LARGE_BANNER.getHeight() * density);
        AbsListView.LayoutParams params = new
                AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height);
        adview.setLayoutParams(params);

        // dont use below if testing on a device
        // follow https://developers.google.com/admob/android/quick-start?hl=en to setup testing device
        AdRequest request = new AdRequest.Builder().build();
        adview.loadAd(request);
        return adview;
    }

    @Override
    protected void doJobInOnCreate(ViewGroup viewGroup, int viewType, AdapterCalenderBinding binding) {
        super.doJobInOnCreate(viewGroup, viewType, binding);
        if(list.get(viewType).getActivity() != null) {
            binding.activityBaseLayout.addItems(list.get(viewType).getActivity(), list.get(viewType).getDate());
            binding.activityBaseLayout.setListener(CalenderAdapter.this);
        }
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder<B, D>(binding);
     */
    @Override
    public ViewHolder getViewHolder(AdapterCalenderBinding binding) {
        return new ViewHolder<AdapterCalenderBinding, CalendarModel>(binding) {
            @Override
            protected void doSomeWorkHere(AdapterCalenderBinding binding, CalendarModel data, int position) {
                if(data.getDateInt() < 28){
                    listener.onMonthReach(data.getMonthId());
                }

                if(data.getMonthId() == currentMonth && data.getDateInt() == currentDay){
                    binding.calenderDate.setBackgroundColor(itemView.getResources().getColor(R.color.colorPrimary));
                }
/*
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                adapterSuitable = new CalenderSymbolAdapter(new ArrayList<>());
                adapterUnSuitable = new CalenderSymbolAdapter(new ArrayList<>());

                binding.listViewSuitable.setLayoutManager(linearLayoutManager);
                binding.listViewSuitable.hasFixedSize();
                binding.listViewSuitable.setAdapter(adapterSuitable);
                adapterSuitable.addItems(list);
                adapterSuitable.setListener(CalenderAdapter.this);

                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(itemView.getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                adapterUnSuitable = new CalenderSymbolAdapter(new ArrayList<>());
                binding.listViewUnSuitable.setLayoutManager(linearLayoutManager1);
                binding.listViewUnSuitable.hasFixedSize();
                binding.listViewUnSuitable.setAdapter(adapterUnSuitable);
                adapterUnSuitable.addItems(list);
                adapterUnSuitable.setListener(CalenderAdapter.this);
*/

//                binding.suitableBaseLayout.addItems(suitableList);
//                binding.unSuitableBaseLayout.addItems(suitableList);
            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
             */
            @Override
            protected void bindData(CalendarModel data) {
//                binding.setData(new CalenderAdapterViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, AdapterCalenderBinding binding, CalendarModel data) {

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
            protected ViewHolderClickListener viewHolderReference(AdapterCalenderBinding binding, CalendarModel data, int position) {
                return null;
            }
        };
    }

    private ViewHolder getCardViewHolder(CalenderTopCardBinding binding) {
        return new ViewHolder<CalenderTopCardBinding, CalendarModel>(binding){
            @Override
            protected void doSomeWorkHere(CalenderTopCardBinding binding, CalendarModel data, int position) {
                listener.onMonthReach(data.getMonthId());
            }

            @Override
            protected void bindData(CalendarModel data) {
//                binding.setData(new CalenderAdapterViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        CalendarData
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, CalenderTopCardBinding binding,
                                          CalendarModel data) {

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
            protected ViewHolderClickListener viewHolderReference(CalenderTopCardBinding binding,
                                                                  CalendarModel data, int position) {
                return null;
            }
        };
    }


    private class AdviewHolder extends ViewHolder<ViewDataBinding, CalendarModel> {

        public AdviewHolder(AdView adview) {
            super(adview);
        }


        /**
         * If there is anything to do then do here otherwise leave it blank
         *
         * @param binding  layout reference for single view
         * @param data     for single view
         * @param position position of ArrayList
         */
        @Override
        protected void doSomeWorkHere(ViewDataBinding binding, CalendarModel data, int position) {

        }

        /**
         * @param data binding.setCalendarData(data);
         */
        @Override
        protected void bindData(CalendarModel data) {
        }

        /**
         * Method to set click listeners on view holder or groups
         *
         * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
         * @param binding     DataBinding
         * @param data        data
         */
        @Override
        public void setClickListeners(ViewHolderClickListener thisContext, ViewDataBinding binding, CalendarModel data) {

        }

        /**
         * Initialised holder by new operator
         *
         * @param binding  DataBinding
         * @param data     dataList
         * @param position of adapter
         * @return new ViewHolderClickListener(binding, data, position)
         */
        @Override
        protected ViewHolderClickListener viewHolderReference(ViewDataBinding binding, CalendarModel data,
                                                              int position) {
            return null;
        }
    }



    private ViewHolder adsViewHolder1(LayoutAdsBinding binding) {
    return new ViewHolder<LayoutAdsBinding, CalendarModel>(binding){
        /**
         * If there is anything to do then do here otherwise leave it blank
         *
         * @param binding  layout reference for single view
         * @param data     for single view
         * @param position position of ArrayList
         */
        @Override
        protected void doSomeWorkHere(LayoutAdsBinding binding, CalendarModel data, int position) {

        }

        /**
         * @param data binding.setCalendarData(new AdapterViewModel(CalendarData));
         */
        @Override
        protected void bindData(CalendarModel data) {

        }

        /**
         * Method to set click listeners on view holder or groups
         *
         * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
         * @param binding     DataBinding
         * @param data        CalendarData
         */
        @Override
        public void setClickListeners(ViewHolderClickListener thisContext, LayoutAdsBinding binding, CalendarModel data) {

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
        protected ViewHolderClickListener viewHolderReference(LayoutAdsBinding binding, CalendarModel data, int position) {
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

    @Override
    public void onBaseCalendarIconClick(View view, String date) {
        //TODO Base Icon click
        if(context != null && context instanceof DashboardActivity){
            ((DashboardActivity)context).calendarToCofecast(date);
        }
    }
}