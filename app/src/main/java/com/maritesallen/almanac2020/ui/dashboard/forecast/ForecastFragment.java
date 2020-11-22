package com.maritesallen.almanac2020.ui.dashboard.forecast;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.date.DateDialog;
import com.maritesallen.almanac2020.core.dialogs.guest.GuestDialog;
import com.maritesallen.almanac2020.core.dialogs.premiumAlert.PremiumAlertDialog;
import com.maritesallen.almanac2020.core.dialogs.upgrade.UpgradeDialog;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.Data;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ForecastCard;
import com.maritesallen.almanac2020.databinding.FragmentForecastBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.ui.dashboard.forecast.adapter.ForecastAdapter;
import com.maritesallen.almanac2020.ui.dashboard.forecast.flystar.FlyStarPager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.ghostMonth.GhostMonthPager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager.ForecastPager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager.imagePager.ForecastImagePager;
import com.maritesallen.almanac2020.ui.dashboard.forecast.suitable.SuitableAdapter;
import com.maritesallen.almanac2020.ui.dashboard.forecast.unsuitable.UnsuitableAdapter;
import com.maritesallen.almanac2020.ui.defaultActivity.DefaultActivity;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.bundles.Bundles;
import com.maritesallen.almanac2020.utils.util.Dimensions;
import com.maritesallen.almanac2020.utils.util.General;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import static com.maritesallen.almanac2020.ui.defaultActivity.DefaultActivity.TYPE_SUITABLE;
import static com.maritesallen.almanac2020.ui.defaultActivity.DefaultActivity.TYPE_UNSUITABLE;
import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_FULL;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Email        : arvindo@aiprog.in
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class ForecastFragment  extends BaseFragment<FragmentForecastBinding, ForecastViewModel>
        implements ForecastNavigator, ForecastAdapter.Listener, DateDialog.DateInterface, DialogListener,
                    SuitableAdapter.Listener, UnsuitableAdapter.Listener{
    public static final String TAG = ForecastFragment.class.getSimpleName();

    private static final int SLIDE_TIME = 9000;
    private static final int SLIDE_TIME_FLY = 7000;
    private static final long PAGER_TRANSITION_DURATION_MS = 3000;
    private static final long PAGER_TRANSITION_DURATION_MS_fly = 2500;

    private Runnable runnableGhost;
    private Handler handlerGhost;

    @Inject
    GhostMonthPager ghostPager;
    @Inject
    FlyStarPager flyStarPager;
    @Inject
    ViewModelProviderFactory factory;
    @Inject
    ForecastAdapter adapter;
    @Inject
    SuitableAdapter adapterSuitable;
    @Inject
    UnsuitableAdapter adapterUnsuitable;
    private int pagerCurrentPage = 1;

//    @Inject
//    ForecastPager adapterPager;
//    @Inject
    private ForecastPager adapterPager;
    private FragmentForecastBinding binding;
    private ForecastViewModel viewModel;
    private String newdate = "";
    private ProgressDialog dialog;
    private Handler handlerFly;
    private Runnable runnableFly;
    private int pageGhost = 0;
    private int pageFly = 0;

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentForecastBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_forecast;
    }

    /**
     * Override for set view model
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public ForecastViewModel getViewModel() {
        newdate = Bundles.getForecastDate(getArguments());
//        return viewModel = new ViewModelProvider(this,factory).get(ForecastViewModel.class);
        return viewModel = ViewModelProviders.of(this,factory).get(ForecastViewModel.class);
    }

    @Override
    public int setTitle() {
        return R.string.send_email;
    }

    /**
     * Override for set binding variable
     *
     * @return BR.CalendarData;
     */
    @Override
    public int getBindingVariable() {
        return com.maritesallen.almanac2020.BR.data;
    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        subscribeToLiveData();
        setUpDate();
        setUp();
        setUpAdapter();
        setUpPager();
        setUpAds();
        setUpPagers();

        tmp();
        viewModel.forcastActivity(newdate);
    }

    private void setUpAds() {
//        if(viewModel.isPremium()) {
//            binding.layoutAds.layoutAds.setVisibility(View.GONE);
//        }
//        else{
/*
            binding.layoutAds.layoutAds.setVisibility(View.VISIBLE);
            MobileAds.initialize(getBaseActivity(), initializationStatus -> {

            });
            AdRequest adRequest = new AdRequest.Builder().build();
            binding.layoutAds.adView.loadAd(adRequest);
            */
            AdView adView = new AdView(getBaseActivity());
            adView.setAdSize(AdSize.LARGE_BANNER);
            adView.setAdUnitId(viewModel.getAdsUnitId());

            MobileAds.initialize(getBaseActivity(), initializationStatus -> {});
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            binding.layoutAds.layoutAds.addView(adView, params);
//        }
    }

    private void setUpDate() {
        if(newdate != null && !newdate.isEmpty()) {
            String date = String.valueOf(General.getIntDate(newdate));
            String readDate = General.getDateReadable1(newdate);
            if (isValidCurentDate(date)) {
                binding.layoutDay.todayText.setVisibility(View.VISIBLE);
                binding.layoutDay.dateText.setText(readDate);
            } else {
                binding.layoutDay.todayText.setVisibility(View.GONE);
                binding.layoutDay.dateText.setText(readDate);

                //set up date
                int[] dateT = ((DashboardActivity) getBaseActivity()).getCalendarDate();
                dateT[0] = General.getIntDateDay(date);
                ((DashboardActivity) getBaseActivity()).setCalendarDate(dateT);
            }
        }
        else{
        binding.layoutDay.todayText.setVisibility(View.VISIBLE);
        binding.layoutDay.dateText.setText(General.getDateReadable());
        }
    }

    private void loadNewData(String data){
        ((DashboardActivity)getBaseActivity()).dateForecast(data);
    }

    private void setUpPager() {
/*

        ArrayList<ForecastCard> forecastCardList = new ArrayList<>();
        forecastCardList.add(new ForecastCard());
        forecastCardList.add(new ForecastCard());
        forecastCardList.add(new ForecastCard());
        forecastCardList.add(new ForecastCard());
        forecastCardList.add(new ForecastCard());
        forecastCardList.add(new ForecastCard());
*/

//        adapterPager = new ForecastPager(getBaseActivity(), forecastCardList);
        adapterPager = new ForecastPager(getChildFragmentManager());
        binding.layoutAdapter.pager.setAdapter(adapterPager);
        pagerCurrentPage = 1;
//        adapterPager.setValues(forecastCardList);

//        adapterPager.getPagerLiveData().observe(this, CalendarData ->
//                adapterPager.scroll(CalendarData));
//        autoScroll();


        ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                Logger.e("onPageScrollStateChanged:" + arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                Logger.e("onPageScrolled:" + arg0 + "," + arg1 + "," + arg2);
            }

            @Override
            public void onPageSelected(int pos) {
                Logger.e("onPageSelected:" + pos);
                if(pagerCurrentPage > pos){
                    setPreviousDate();
                }
                else if(pagerCurrentPage < pos){
                    setNextDate();
                }
                else{
//                    currentDatePager();
                }
//                if(binding.layoutAdapter.pager.getCurrentItem() == pos)
            }
        };
        binding.layoutAdapter.pager.addOnPageChangeListener(mPageChangeListener);
    }

    private void setNextDate() {
        int[] DateObject = ((DashboardActivity)getBaseActivity()).getCalendarDate();
        int year = DateObject[2];
        int month = DateObject[1];
        int day = DateObject[0];

//        ((DashboardActivity) getBaseActivity()).setCalendarDate(DateObject);

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);         //comment this
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        cal.add(Calendar.DAY_OF_MONTH, 1);

        year = cal.get((Calendar.YEAR));
        month = cal.get((Calendar.MONTH));
        day = cal.get((Calendar.DATE));

        DateObject = new int[3];
        DateObject[2] = year;
        DateObject[1] = month;
        DateObject[0] = day;

        String dateReadable = General.getDateReadable(cal.getTime());
        String sendDate = General.getDateSend(cal.getTime());
        String dateInt = String.valueOf(General.getDateInt(cal.getTime()));

        onDateChange(DateObject, dateReadable, sendDate, dateInt);
    }

    private void setPreviousDate() {
        int[] DateObject = ((DashboardActivity)getBaseActivity()).getCalendarDate();
        int year = DateObject[2];
        int month = DateObject[1];
        int day = DateObject[0];

//        ((DashboardActivity) getBaseActivity()).setCalendarDate(DateObject);

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);         //comment this
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        cal.add(Calendar.DATE, -1);

        year = cal.get((Calendar.YEAR));
        month = cal.get((Calendar.MONTH));
        day = cal.get((Calendar.DATE));

        DateObject = new int[3];
        DateObject[2] = year;
        DateObject[1] = month;
        DateObject[0] = day;

        String dateReadable = General.getDateReadable(cal.getTime());
        String sendDate = General.getDateSend(cal.getTime());
        String dateInt = String.valueOf(General.getDateInt(cal.getTime()));

        onDateChange(DateObject, dateReadable, sendDate, dateInt);
    }

    private void setUpAdapter() {
//        adapter.setListener(this);
//        binding.layoutAdapter.listView.setAdapter(adapter);

        adapterSuitable.setListeners(this);
        binding.layoutSuitableActivities.listView.setAdapter(adapterSuitable);

        adapterUnsuitable.setListeners(this);
        binding.layoutUnsuitableActivities.listView.setAdapter(adapterUnsuitable);
    }

    private void setUp() {
        ((DashboardActivity)getBaseActivity()).setForecast();

//        LinearLayoutCompat.LayoutParams layoutParams = new
//                LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                (int)(Dimensions.getScreenHeight(getBaseActivity()) /2.8));
//        binding.layoutAdapter.layout.setLayoutParams(layoutParams);

        binding.profileUpgradeCard.upgrade.setOnClickListener(v -> {
            if(viewModel.isGuestLogin()){
                getBaseActivity().startDialog(new GuestDialog(this), GuestDialog.TAG);
            }
            else{
                getBaseActivity().startDialog(new UpgradeDialog(DIALOG_SIZE_FULL, this), UpgradeDialog.TAG);
            }
        });


//        binding.layoutDay.todayText.setVisibility(View.VISIBLE);
//        binding.layoutDay.dateText.setText(General.getDateReadable());

        if(viewModel.isGuestLogin()){
//            binding.layoutDay.layoutLast.setVisibility(View.GONE);
            binding.layoutDay.dutyOfficerImage.setVisibility(View.GONE);
            viewModel.setGuestUserTitle(getString(R.string.guest_user));
        }

        dialog = new ProgressDialog(getBaseActivity());
        dialog.setMessage(getString(R.string.loading_wait));
        dialog.setCancelable(false);
    }

    private void tmp(){
        Data data = new Data();
        data.setDutyOfficer(" ");
        data.setDutyOfficerDescription(" ");
        data.setMonthAnimal("");
        data.setShowNow("");
        data.setMoonStatus("");
        setUpPagerData(data);
    }

    private void subscribeToLiveData() {
        viewModel.getModelSuitableLiveData().observe(this, data ->
                viewModel.addSuitableData(data));
        viewModel.getModelUnsuitableLiveData().observe(this, data ->
                viewModel.addUnsuitableData(data));
        viewModel.getModelLiveData().observe(this, data ->{
                viewModel.addModelData(data);
                setUpPagerData(data);
        });
        viewModel.getMonthModelLiveData().observe(this, data ->{
                viewModel.addMonthModelData(data);
        });

        viewModel.getModelGhostLiveData().observe(this, data ->{
                viewModel.addGhostData(data);
        });

        viewModel.getModelFlyLiveData().observe(this, data ->{
                viewModel.addFlyStarData(data);
        });
    }

    private void setUpPagerData(Data data){
        ArrayList<ForecastCard> forecastCardList = new ArrayList<>();
        forecastCardList.add(new ForecastCard(data.getDutyOfficer(), data.getDutyOfficerDescription(), data.getMonthAnimal(),
                data.getMoonStatus(), data.getShowNow(), data.getShowNowPh()));
        forecastCardList.add(new ForecastCard(data.getDutyOfficer(), data.getDutyOfficerDescription(), data.getMonthAnimal(),
                data.getMoonStatus(), data.getShowNow(), data.getShowNowPh()));
        forecastCardList.add(new ForecastCard(data.getDutyOfficer(), data.getDutyOfficerDescription(), data.getMonthAnimal(),
                data.getMoonStatus(), data.getShowNow(), data.getShowNowPh()));

        adapterPager.clearData();
        adapterPager.setValues(forecastCardList);
        binding.layoutAdapter.pager.setCurrentItem(pagerCurrentPage);

    }

    @Override
    public void ghostMonthStatus(@NotNull Boolean status) {
        if(status){
            binding.layoutGhostMonth.layout.setVisibility(View.VISIBLE);
        }
        else{
            binding.layoutGhostMonth.layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void offlineMode(boolean status) {
        if(status){
            binding.layoutMonth.layout.setVisibility(View.GONE);
            binding.offlineCard.layout.setVisibility(View.VISIBLE);
        }else {
            binding.layoutMonth.layout.setVisibility(View.VISIBLE);
            binding.offlineCard.layout.setVisibility(View.GONE);
        }
    }

    private void setUpPagers() {
//        viewModel.loadSlider();
        if(viewModel.isPremium()) {
            binding.layoutSliderCard.layout.setVisibility(View.VISIBLE);

            viewModel.loadGhostMonth();
//            viewModel.loadFlyStar();
            viewModel.getFlyStarDb();

            binding.layoutGhostMonth.ghostMonthPager.setAdapter(ghostPager);
            binding.layoutSliderCard.ghostMonthPager.setAdapter(flyStarPager);

            autoScroll();
            autoScrollFlyStar();
        }
        else{
            binding.layoutSliderCard.layout.setVisibility(View.GONE);
            binding.layoutGhostMonth.layout.setVisibility(View.GONE);
        }
    }

    private void autoScroll() {
        /*After setting the ghostPager use the timer */
        handlerGhost = new Handler();
        runnableGhost = () -> {
            if (ghostPager.getCount() <= pageGhost) {
                pageGhost = 0;
                binding.layoutGhostMonth.ghostMonthPager.setCurrentItem(pageGhost, true);
            } else {
                pageGhost++;
                animatePagerTransition(true);
            }

                handlerGhost.postDelayed(runnableGhost, SLIDE_TIME);
        };

        handlerGhost.postDelayed(runnableGhost, SLIDE_TIME);

    }

    private void autoScrollFlyStar() {
        /*After setting the ghostPager use the timer */
        handlerFly = new Handler();
        runnableFly = () -> {
            if (flyStarPager.getCount() <= pageFly) {
                pageFly = 0;
                binding.layoutSliderCard.ghostMonthPager.setCurrentItem(pageFly, true);
            } else {
                pageFly++;
                animatePagerTransitionFlyStar(true);
            }

//                binding.pager.setCurrentItem(page, true);

                handlerFly.postDelayed(runnableFly, SLIDE_TIME_FLY);
        };

        handlerFly.postDelayed(runnableFly, SLIDE_TIME_FLY);
    }

    private void animatePagerTransition(final boolean forward) {
        ValueAnimator animator = ValueAnimator.ofInt(0, binding.layoutGhostMonth.ghostMonthPager.getWidth() -
                ( forward ? binding.layoutGhostMonth.ghostMonthPager.getPaddingLeft() : binding.layoutGhostMonth.ghostMonthPager.getPaddingRight() ));
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    binding.layoutGhostMonth.ghostMonthPager.endFakeDrag();
                }catch (Exception ignore){}
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                try {
                    binding.layoutGhostMonth.ghostMonthPager.endFakeDrag();
                }catch (Exception ignore){}
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private int oldDragPosition = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int dragPosition = (Integer) animation.getAnimatedValue();
                int dragOffset = dragPosition - oldDragPosition;
                oldDragPosition = dragPosition;
                try {
                    binding.layoutGhostMonth.ghostMonthPager.fakeDragBy(dragOffset * (forward ? -1 : 1));
                }catch (Exception ignore){}

//                binding.layoutGhostMonth.ghostMonthPager.fakeDragBy(dragOffset * page);
            }
        });

        animator.setDuration(PAGER_TRANSITION_DURATION_MS);
        try {
            binding.layoutGhostMonth.ghostMonthPager.beginFakeDrag();
        }catch (Exception ignore){}
        animator.start();
    }

    private void animatePagerTransitionFlyStar(final boolean forward) {
        ValueAnimator animator = ValueAnimator.ofInt(0, binding.layoutSliderCard.ghostMonthPager.getWidth() -
                ( forward ? binding.layoutSliderCard.ghostMonthPager.getPaddingLeft() : binding.layoutSliderCard.ghostMonthPager.getPaddingRight() ));
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    binding.layoutSliderCard.ghostMonthPager.endFakeDrag();
                }catch (Exception ignore){}
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                try {
                    binding.layoutSliderCard.ghostMonthPager.endFakeDrag();
                }catch (Exception ignore){}
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private int oldDragPosition = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int dragPosition = (Integer) animation.getAnimatedValue();
                int dragOffset = dragPosition - oldDragPosition;
                oldDragPosition = dragPosition;
                try {
                    binding.layoutSliderCard.ghostMonthPager.fakeDragBy(dragOffset * (forward ? -1 : 1));
                }catch (Exception ignore){}

//                binding.layoutSliderCard.ghostMonthPager.fakeDragBy(dragOffset * page);
            }
        });

        animator.setDuration(PAGER_TRANSITION_DURATION_MS_fly);
        try {
            binding.layoutSliderCard.ghostMonthPager.beginFakeDrag();
        }catch (Exception ignore){}
        animator.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(handlerGhost != null && runnableGhost != null) {
            handlerGhost.removeCallbacks(runnableGhost);
        }

        if(handlerFly != null && runnableFly != null) {
            handlerFly.removeCallbacks(runnableFly);
        }
    }

    //Navigator------------------------

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onSuitableIconCLick(Bundle bundle) {
        if(viewModel.isPremium()) {
            Intent i = new Intent(getBaseActivity(), DefaultActivity.class);
            i.putExtra(AppConstants.KEY_DEFAULT_ACTIVITY_BUNDLE, bundle);
            startActivity(i);
        }
        else{
            if(!viewModel.isGuestLogin()){
                getBaseActivity().startDialog(new PremiumAlertDialog(this), PremiumAlertDialog.TAG);
            }
            else{
                getBaseActivity().startDialog(new GuestDialog(this), GuestDialog.TAG);
            }
        }
    }

    @Override
    public void onDateChangeClick() {
        DateDialog dateFragment = DateDialog.newInstance(this);
        dateFragment.setSingle(true);
        dateFragment.setShowDateSingle(true, ((DashboardActivity) getBaseActivity()).getCalendarDate());
        dateFragment.show(getBaseActivity().getSupportFragmentManager(), DateDialog.TAG);
    }

    @Override
    public void onUnSuitableInfoClick(View view) {
        if(viewModel.getModel().get() != null &&
                viewModel.getModel().get().getUnSuitableActivityLists() != null) {
            if (getBaseActivity() != null) {
                getBaseActivity().startActivity(DefaultActivity.class, Bundles.setDefaultActivityUnSuitable(TAG,
                        TYPE_UNSUITABLE, (ArrayList) viewModel.getModel().get().getUnSuitableActivityLists()));
            }
        }
    }

    @Override
    public void onSuitableInfoClick(View view) {
        if(viewModel.getModel().get() != null &&
                viewModel.getModel().get().getActivityLists() != null) {
            if (getBaseActivity() != null) {
                getBaseActivity().startActivity(DefaultActivity.class, Bundles.setDefaultActivitySuitable(TAG,
                        TYPE_SUITABLE, (ArrayList) viewModel.getModel().get().getActivityLists()));
            }
        }
    }

    @Override
    public void isPremiumAccount(boolean premium) {
        if(premium){
            binding.profileUpgradeCard.layout.setVisibility(View.GONE);
        }
        else{
            binding.layoutMonth.layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void removeSlotForecastStar() {
//        binding.layoutMonth.luckForecast.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    @Override
    public void showMessage(String message) {
        getBaseActivity().showToast(message);
    }

    @Override
    public void reset(boolean value) {
/*
        if(value){
            binding.layoutAdapter.layout.setVisibility(View.VISIBLE);
            binding.layoutSuitableActivities.layout.setVisibility(View.VISIBLE);
            binding.layoutUnsuitableActivities.layout.setVisibility(View.VISIBLE);
            binding.layoutMonth.layout.setVisibility(View.VISIBLE);
            if(viewModel.isPremium()){
                binding.layoutMonth.layout.setVisibility(View.VISIBLE);
            }
        }
        else{
            binding.layoutAdapter.layout.setVisibility(View.GONE);
            binding.layoutSuitableActivities.layout.setVisibility(View.GONE);
            binding.layoutUnsuitableActivities.layout.setVisibility(View.GONE);
            binding.layoutMonth.layout.setVisibility(View.GONE);
            binding.layoutMonth.layout.setVisibility(View.GONE);
        }
        */
    }

    @Override
    public void showMessage(int messageId) {
        if(getBaseActivity() != null) {
            getBaseActivity().showToast(messageId);
        }
    }

    /**
     * @param date   date will return in string format
     * @param params date will return in int format
     */
    @Override
    public void date(String date, String... params) {
        if(params != null && params.length > 0){
            int[] calendarDate = new int[3];
            calendarDate[0] = Integer.parseInt(params[2]);  //day
            calendarDate[1] = Integer.parseInt(params[3]);  //moth
            calendarDate[2] = Integer.parseInt(params[4]);  //year

            onDateChange(calendarDate, date, params[0], params[1]);
        }
    }

    private void onDateChange(int[] calendarDate, String dateReadable, String sendDate, String intDate){
        if(isValidYear(calendarDate[2])) {
            if (!viewModel.isGuestLogin() && viewModel.isPremium()) {
                if (isValidCurentDate(intDate)) {
                    //hit new api
                    binding.layoutDay.todayText.setVisibility(View.VISIBLE);
                    binding.layoutDay.dateText.setText(dateReadable);
                    ((DashboardActivity) getBaseActivity()).setCalendarDate(calendarDate);
                    loadNewData(sendDate);
                } else {
                    //hit new api
                    binding.layoutDay.todayText.setVisibility(View.GONE);
                    binding.layoutDay.dateText.setText(dateReadable);
                    ((DashboardActivity) getBaseActivity()).setCalendarDate(calendarDate);
                    loadNewData(sendDate);
                }

            } else {
                if (isValidDate(intDate)) {
                    //hit new api
                    binding.layoutDay.todayText.setVisibility(View.GONE);
                    binding.layoutDay.dateText.setText(dateReadable);
                    ((DashboardActivity) getBaseActivity()).setCalendarDate(calendarDate);
                    loadNewData(sendDate);
                } else {
                    //premium dialog
                    binding.layoutAdapter.pager.setCurrentItem(pagerCurrentPage);
                    getBaseActivity().startDialog(new PremiumAlertDialog(this), PremiumAlertDialog.TAG);
                }
            }
        }
        else{
            binding.layoutAdapter.pager.setCurrentItem(pagerCurrentPage);
        }
    }

    /**
     * Default response method of a dialog
     *
     * @param tag    class name from which the response is getting
     * @param params string array with relative CalendarData
     */
    @Override
    public void onSuccessDialogResponse(String tag, String... params) {
        if(tag.equals(GuestDialog.TAG)){
            if(params.length > 0 && params[0].equals(AppConstants.YES)){
                ((DashboardActivity)getBaseActivity()).logout();
            }
        }
        else if(tag.equals(PremiumAlertDialog.TAG)){
            if(params.length > 0 && params[0].equals(AppConstants.YES)){
                getBaseActivity().startDialog(new UpgradeDialog(DIALOG_SIZE_FULL, this), UpgradeDialog.TAG);
            }
        }
        else if(tag.equals(UpgradeDialog.TAG)){
            if(params != null && params.length > 0){
                if(params[0].equals(AppConstants.YES)){
                    ((DashboardActivity)getBaseActivity()).doPremiumPurchase();
                }
            }
        }
    }

    private boolean isValidCurentDate(String date) {
        int currentDate = General.getDateInt(new Date());
        int enterDate = Integer.parseInt(date);
        if(enterDate == currentDate){
            return true;
        }

        return false;
    }

    private boolean isValidDate(String date) {
        int currentDate = General.getDateInt(new Date());
        int enterDate = Integer.parseInt(date);
        if(enterDate <= (currentDate+1) && enterDate > (currentDate-1)){
            return true;
        }
        else if(enterDate == (currentDate-1)){
            return true;
        }

        return false;
    }

    private boolean isValidYear(int year) {
        int yearCurrent = 2020;
        if(year == 2020){
            return true;
        }

        return false;
    }

    //pager animation------------------


    //Activity-------------------------

    //Loader---------------------------

    @Override
    public void forecastDialog(boolean status) {
        if(status){
            dialog.show();
        }
        else if(dialog != null && dialog.isShowing()){
            new Handler().postDelayed(() -> {
                if(dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }, 300);
        }
    }



}
