package com.maritesallen.almanac2020.ui.dashboard;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.maritesallen.almanac2020.BuildConfig;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseActivity;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.calender.CalenderDialog;
import com.maritesallen.almanac2020.core.dialogs.date.DateDialog;
import com.maritesallen.almanac2020.core.dialogs.guest.GuestDialog;
import com.maritesallen.almanac2020.core.dialogs.premiumAlert.PremiumAlertDialog;
import com.maritesallen.almanac2020.core.dialogs.upgrade.UpgradeDialog;
import com.maritesallen.almanac2020.core.util.BookCallback;
import com.maritesallen.almanac2020.core.util.ReadBook;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.databinding.DashboardBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.access.AccessActivity;
import com.maritesallen.almanac2020.ui.dashboard.books.BooksFragment;
import com.maritesallen.almanac2020.ui.dashboard.books.BooksViewModel;
import com.maritesallen.almanac2020.ui.dashboard.calender.CalenderFragment;
import com.maritesallen.almanac2020.ui.dashboard.forecast.ForecastFragment;
import com.maritesallen.almanac2020.ui.dashboard.profile.ProfileFragment;
import com.maritesallen.almanac2020.ui.pdfViewer.PdfViewerActivity;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.billing.core.IabResult;
import com.maritesallen.almanac2020.utils.billing.core.Purchase;
import com.maritesallen.almanac2020.utils.billing.util.BillingConfig;
import com.maritesallen.almanac2020.utils.billing.util.BillingConfigPremium;
import com.maritesallen.almanac2020.utils.bundles.Bundles;
import com.maritesallen.almanac2020.utils.util.General;
import com.yalantis.ucrop.UCrop;

import org.androidannotations.annotations.App;

import java.io.File;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

import static com.maritesallen.almanac2020.ui.dashboard.profile.ProfileFragment.PICK_IMAGE;
import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_FULL;

/**
 * Author       : Arvindo Mondal
 * Created on   : 12-11-2019
 * Email        : arvindo@aiprog.in

 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 *
 */
public class DashboardActivity extends BaseActivity<DashboardBinding, DashboardViewModel>
        implements DashboardNavigation, HasAndroidInjector, DateDialog.DateInterface, DialogListener,
        BillingConfig.BillingConfigListener, BillingConfigPremium.BillingConfigListener, BookCallback {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    @Inject
    DispatchingAndroidInjector<Object> androidInjector;
    @Inject
    ViewModelProviderFactory factory;
    private DashboardBinding binding;
    private DashboardViewModel viewModel;
    private NavController navController;
    private String fragmentTag = "";
    private CalenderFragment calenderFragment;
    private ProfileFragment profileFragment;
    private BooksFragment booksFragment;
    private int[] calendarDate;

    private BillingConfig billing;
    private BillingConfigPremium billingPremium;
    private String subscriptionYear = "";
    private AlertDialog alertDialog;
    private ProgressDialog dialog;
    private AlertDialog downloadDialog;
    private ProgressBar progressBar;
    private ProgressDialog premiumSuccessDialog;
    private boolean isPremiumEnable = true;


    /**
     * Returns an {@link AndroidInjector}.
     */
    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    /**
     * @param binding activity class Data binding
     */
    @Override
    public void getActivityBinding(DashboardBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dashboard;
    }

    /**
     * Override for set binding variable
     *
     * @return BR.Data;
     */
    @Override
    public int getBindingVariable() {
        return com.maritesallen.almanac2020.BR.data;
    }

    /**
     * Override for get the instance of viewModel
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public DashboardViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(DashboardViewModel.class);
    }

    /**
     * Do anything on onCreate after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        viewModel.cancel();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        setUp();
        setUpExtras();
        setUpPaymentPremium();

        viewModel.checkForPaymentSync();
        firebaseToken();
        setupBroadcast();
        viewModel.verifyPremium();
    }

    private void setUp() {
        binding.toolbarLayout.calender.setOnClickListener(v -> {
            todayForecast(true);
        });

        binding.toolbarLayout.logout.setOnClickListener(v -> {
            if(viewModel.isGuestUser()) {
                logout();
            }
            else{
                logoutDialog();
            }
        });

        binding.toolbarLayout.title.setOnClickListener(v -> {
            if(fragmentTag.equals(CalenderFragment.TAG)){
                calenderDateDialog();
            }
        });

        setUpCalendarDate();
        viewModel.getVersion();
    }

    public void logoutDialog(){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_messsage))

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Continue with delete operation
                    logout();
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(logoutReceiver);
        }catch (Exception ignore){}
    }

    void checkForGuest(){
        if(viewModel.getLogginMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType()){
            binding.toolbarLayout.calender.setVisibility(View.GONE);
            binding.toolbarLayout.logout.setVisibility(View.VISIBLE);
        }
    }

    private void setUpExtras(){
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            try {
                if(destination.getLabel().equals(ForecastFragment.TAG)){
                    fragmentTag = ForecastFragment.TAG;
                }
                else if(destination.getLabel().equals(CalenderFragment.TAG)){
                    fragmentTag = CalenderFragment.TAG;
                }
                else if(destination.getLabel().equals(BooksFragment.TAG)){
                    fragmentTag = BooksFragment.TAG;
                }
                else if(destination.getLabel().equals(ProfileFragment.TAG)){
                    fragmentTag = ProfileFragment.TAG;
                }
                Logger.e("onDestinationChanged: "+destination.getLabel());
            }catch (NullPointerException ignore){}
        });
    }

    public void setForecast(){
        binding.toolbarLayout.title.setText(getText(R.string.daily_forecast));
        binding.toolbarLayout.imageArrowDown.setVisibility(View.GONE);
        binding.toolbarLayout.calender.setVisibility(View.VISIBLE);
        binding.toolbarLayout.logout.setVisibility(View.GONE);
//        binding.toolbarLayout.layoutLast.setVisibility(View.VISIBLE);

//        binding.toolbarDashboard.forecastActivity.setSupportBackgroundTintList(
//                ColorStateList.valueOf(getResources().getColor(R.color.colorView)));
        binding.toolbarDashboard.forecast.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_forecast_color, null));
        binding.toolbarDashboard.calender.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_calender, null));
        binding.toolbarDashboard.books.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_book, null));
        binding.toolbarDashboard.profile.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_profile, null));
//        binding.toolbarDashboard.profile.setSupportBackgroundTintList(
//                ColorStateList.valueOf(getResources().getColor(R.color.dashboardIconColorDefault)));

        binding.toolbarDashboard.forecastText.setTextColor(getResources().getColor(R.color.colorView));
        binding.toolbarDashboard.calenderText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
        binding.toolbarDashboard.booksText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
        binding.toolbarDashboard.profileText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));

        checkForGuest();
    }

    public void setCalender(String title){
        binding.toolbarLayout.title.setText(title);
        binding.toolbarLayout.imageArrowDown.setVisibility(View.VISIBLE);
        binding.toolbarLayout.calender.setVisibility(View.GONE);
        binding.toolbarLayout.logout.setVisibility(View.GONE);
//        binding.toolbarLayout.layoutLast.setVisibility(View.VISIBLE);
        binding.toolbarDashboard.calender.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_calender_color, null));
        binding.toolbarDashboard.books.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_book, null));
        binding.toolbarDashboard.forecast.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_forecast, null));
        binding.toolbarDashboard.profile.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_profile, null));


//        binding.toolbarDashboard.forecastActivity.setSupportBackgroundTintList(
//                ColorStateList.valueOf(getResources().getColor(R.color.dashboardIconColorDefault)));
//        binding.toolbarDashboard.profile.setSupportBackgroundTintList(
//                ColorStateList.valueOf(getResources().getColor(R.color.dashboardIconColorDefault)));

        binding.toolbarDashboard.calenderText.setTextColor(getResources().getColor(R.color.colorView));
        binding.toolbarDashboard.forecastText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
        binding.toolbarDashboard.booksText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
        binding.toolbarDashboard.profileText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
    }

    public void setBooks(){
        binding.toolbarLayout.title.setText(getText(R.string.books));
        binding.toolbarLayout.calender.setVisibility(View.GONE);
        binding.toolbarLayout.imageArrowDown.setVisibility(View.GONE);
        binding.toolbarLayout.logout.setVisibility(View.GONE);
//        binding.toolbarLayout.layoutLast.setVisibility(View.VISIBLE);

        binding.toolbarDashboard.calender.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_calender, null));
        binding.toolbarDashboard.books.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_book_color, null));
        binding.toolbarDashboard.forecast.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_forecast, null));
        binding.toolbarDashboard.profile.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_profile, null));

//        binding.toolbarDashboard.forecastActivity.setSupportBackgroundTintList(
//                ColorStateList.valueOf(getResources().getColor(R.color.dashboardIconColorDefault)));
//        binding.toolbarDashboard.profile.setSupportBackgroundTintList(
//                ColorStateList.valueOf(getResources().getColor(R.color.dashboardIconColorDefault)));

        binding.toolbarDashboard.booksText.setTextColor(getResources().getColor(R.color.colorView));
        binding.toolbarDashboard.calenderText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
        binding.toolbarDashboard.forecastText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
        binding.toolbarDashboard.profileText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
        checkForGuest();
    }

    public void setProfile(){
        binding.toolbarLayout.title.setText(getText(R.string.profile));
        binding.toolbarLayout.imageArrowDown.setVisibility(View.GONE);
        binding.toolbarLayout.calender.setVisibility(View.GONE);
        binding.toolbarLayout.logout.setVisibility(View.VISIBLE);
//        binding.toolbarLayout.layoutLast.setVisibility(View.GONE);

        binding.toolbarDashboard.calender.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_calender, null));
        binding.toolbarDashboard.books.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_book, null));
        binding.toolbarDashboard.forecast.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_forecast, null));
        binding.toolbarDashboard.profile.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.ic_profile_color, null));

//        binding.toolbarDashboard.profile.setSupportBackgroundTintList(
//                ColorStateList.valueOf(getResources().getColor(R.color.colorView)));
//        binding.toolbarDashboard.forecastActivity.setSupportBackgroundTintList(
//                ColorStateList.valueOf(getResources().getColor(R.color.dashboardIconColorDefault)));

        binding.toolbarDashboard.profileText.setTextColor(getResources().getColor(R.color.colorView));
        binding.toolbarDashboard.calenderText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
        binding.toolbarDashboard.booksText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
        binding.toolbarDashboard.forecastText.setTextColor(getResources().getColor(R.color.dashboardDefaultIconColor));
    }

    private void setUpCalendarDate(){
        calendarDate = new int[3];
        calendarDate[2] = General.getCurrentYear();
//        calendarDate[0] = AppConstants.DEFAULT_YEAR;
        calendarDate[1] = General.getCurrentMonth();
        calendarDate[0] = General.getCurrentDay();
    }

    //Navigation----------------------------

    public void todayForecast(boolean status) {
        if(status) {
            if (General.isCurrentDate(calendarDate)) {
//            showToast(R.string.your_are_on_current_date);

                new AlertDialog.Builder(this)
//                    .setTitle(getString(R.string.today_forecast))
                        .setMessage(R.string.your_are_on_current_date)
                        .setCancelable(true)
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            } else {
                setUpCalendarDate();

                if (fragmentTag.equals(ForecastFragment.TAG)) {
                    navController.navigate(R.id.action_forecastFragment_self);
                }
            }
        }
        else{
            setUpCalendarDate();

            if (fragmentTag.equals(ForecastFragment.TAG)) {
                navController.navigate(R.id.action_forecastFragment_self);
            }
        }
    }

    public void dateForecast(String date) {
        if(date != null && !date.isEmpty() && fragmentTag.equals(ForecastFragment.TAG)) {
            Bundle bundle = Bundles.setForecastDate(date);
            navController.navigate(R.id.action_forecastFragment_self, bundle);
        }
    }

    public void calendarToCofecast(String date) {
        if(date != null && !date.isEmpty() && fragmentTag.equals(CalenderFragment.TAG)) {
            Bundle bundle = Bundles.setForecastDate(date);
            navController.navigate(R.id.action_calenderFragment_to_forecastFragment, bundle);
        }
    }

    public void booksFragmentReload(){
        if(fragmentTag.equals(BooksFragment.TAG)){
            navController.navigate(R.id.action_booksFragment_self);
        }
    }

    @Override
    public void onForecastClick(View view) {
        viewModel.cancel();
        setUpCalendarDate();

        if(fragmentTag.equals(CalenderFragment.TAG)){
            navController.navigate(R.id.action_calenderFragment_to_forecastFragment);
        }else if(fragmentTag.equals(BooksFragment.TAG)){
            navController.navigate(R.id.action_booksFragment_to_forecastFragment);
        }else if(fragmentTag.equals(ProfileFragment.TAG)){
            navController.navigate(R.id.action_profileFragment_to_forecastFragment);
        }
/*
        if(!isNetworkAvailable()){
            networkErrorMessage();
        }
        */
    }

    @Override
    public void onCalenderClick(View view, boolean isAccessible, int mode) {
        viewModel.cancel();
        if(isAccessible && ( mode == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType() ||
                mode == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_FACEBOOK.getType())) {
            if (fragmentTag.equals(ForecastFragment.TAG)) {
                navController.navigate(R.id.action_forecastFragment_to_calenderFragment);
            } else if (fragmentTag.equals(BooksFragment.TAG)) {
                navController.navigate(R.id.action_booksFragment_to_calenderFragment);
            } else if (fragmentTag.equals(ProfileFragment.TAG)) {
                navController.navigate(R.id.action_profileFragment_to_calenderFragment);
            }
        }
        else if((mode == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType()  ||
                mode == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_FACEBOOK.getType())){
            startDialog(new PremiumAlertDialog(this), PremiumAlertDialog.TAG);
        }
        else{
            startDialog(new GuestDialog(this), GuestDialog.TAG);
        }

/*        if(!isNetworkAvailable()){
            networkErrorMessage();
        }
        */
    }

    @Override
    public void onBooksClick(View view) {
        viewModel.cancel();
        if(fragmentTag.equals(ForecastFragment.TAG)){
            navController.navigate(R.id.action_forecastFragment_to_booksFragment);
        }else if(fragmentTag.equals(CalenderFragment.TAG)){
            navController.navigate(R.id.action_calenderFragment_to_booksFragment);
        }else if(fragmentTag.equals(ProfileFragment.TAG)){
            navController.navigate(R.id.action_profileFragment_to_booksFragment);
        }
/*
        if(!isNetworkAvailable()){
            networkErrorMessage();
        }
        */
    }

    @Override
    public void onProfileClick(View view, boolean isAccessible) {
        viewModel.cancel();
        if(isAccessible) {
            if (fragmentTag.equals(ForecastFragment.TAG)) {
                navController.navigate(R.id.action_forecastFragment_to_profileFragment);
            } else if (fragmentTag.equals(CalenderFragment.TAG)) {
                navController.navigate(R.id.action_calenderFragment_to_profileFragment);
            } else if (fragmentTag.equals(BooksFragment.TAG)) {
                navController.navigate(R.id.action_booksFragment_to_profileFragment);
            }
        }
        else{
            startDialog(new GuestDialog(this), GuestDialog.TAG);
        }
/*
        if(!isNetworkAvailable()){
            networkErrorMessage();
        }
        */
    }


    /**
     * @param date   date will return in string format
     * @param params date will return in int format
     */
    @Override
    public void date(String date, String... params) {
        if(params != null && params.length > 0){
            calendarDate[0] = Integer.parseInt(params[2]);
            calendarDate[1] = Integer.parseInt(params[3]);
            calendarDate[2] = Integer.parseInt(params[4]);
        }

        //TODO on calendar month select
        if(fragmentTag.equals(CalenderFragment.TAG)){
            if(calenderFragment != null && params != null && params.length > 0){
//                calenderFragment.onCalenderClick(calendarDate[1], calendarDate[0], date);   //used earlier
                navController.navigate(R.id.action_calenderFragment_self,
                        Bundles.setCalendarData(calendarDate[1], calendarDate[0], date));
            }
        }
        else{
            onForecastClick(null);
        }
    }

    //Extras------------------------------------------

    //TODO open custom calendar for calendar
    private void calenderDateDialog() {
//        startDialog(new CalenderDialog(this), CalenderDialog.TAG);
        DateDialog dateFragment = DateDialog.newInstance(this);
        dateFragment.setSingle(true);
        dateFragment.setShowDateSingle(true, calendarDate);
        dateFragment.show(getSupportFragmentManager(), DateDialog.TAG);
    }

    /**
     * Default response method of a dialog
     *
     * @param tag    class name from which the response is getting
     * @param params string array with relative Data
     */
    @Override
    public void onSuccessDialogResponse(String tag, String... params) {
        if(tag.equals(CalenderDialog.TAG)){
            if(calenderFragment != null && params.length > 0){
                calenderFragment.onCalenderClick(params[0], params[1]);
            }
        }
        else if(tag.equals(GuestDialog.TAG)){
            if(params.length > 0 && params[0].equals(AppConstants.YES)){
                logout();
            }
        }
        else if(tag.equals(PremiumAlertDialog.TAG)){
            if(params.length > 0 && params[0].equals(AppConstants.YES)){
                startDialog(new UpgradeDialog(DIALOG_SIZE_FULL, this), UpgradeDialog.TAG);
            }
        }
        else if(tag.equals(UpgradeDialog.TAG)){
            if(params != null && params.length > 0){
                if(params[0].equals(AppConstants.YES)){
                    doPremiumPurchase();
                }
            }
        }
    }

    @Override
    public void onFragmentAttached(Fragment fragment) {
        super.onFragmentAttached(fragment);
        if(fragment instanceof CalenderFragment) {
            this.calenderFragment = (CalenderFragment) fragment;
        }
        else if(fragment instanceof ProfileFragment) {
            this.profileFragment = (ProfileFragment) fragment;
        }
        else if(fragment instanceof BooksFragment) {
            this.booksFragment = (BooksFragment) fragment;
        }
    }

    public void logout() {
        if(isNetworkAvailable()) {
            viewModel.doLogout();
        }
        else{
            showToast(R.string.network_error);
        }
    }

    @Override
    public void doLogout(boolean isLogout, String message) {
        if(isLogout){
            startActivity(AccessActivity.class);
            finishAffinity();
        }
    }

    @Override
    public void showMessage(int message) {
        showToast(message);
    }

    @Override
    public void onPemiumPurchaseSuccess(boolean status) {
        if(premiumSuccessDialog != null && premiumSuccessDialog.isShowing()){
            premiumSuccessDialog.dismiss();
        }
        if(status) {
            isPremiumEnable = false;
            showToast(R.string.success_premium_user);
        }
        else{
            isPremiumEnable = true;
        }
        if(fragmentTag.equals(ForecastFragment.TAG)){
            todayForecast(false);
        }
        else{
            onForecastClick(null);
        }
    }
/*

    @Override
    public void downloadFileStatus(boolean status) {
        if(status){
            showToast(R.string.file_downloaded_successfully);
        }
        else{
            showToast(R.string.file_downloaded_not_success);
        }
    }
*/

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.cancel();
    }

    public int[] getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(int[] calendarDate) {
        this.calendarDate = calendarDate;
    }

    private void networkErrorMessage(){
        showToast(R.string.network_error);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        if(requestCode == 10001) {

            if (billing.getmHelper() == null) return;

            // Pass on the activity result to the helper for handling
            if (!billing.getmHelper().handleActivityResult(requestCode, resultCode, data)) {
                // not handled, so handle it ourselves (here's where you'd
                // perform any handling of activity results not related to in-app
                // billing2...
                super.onActivityResult(requestCode, resultCode, data);
            } else {
                Log.e(TAG, "onActivityResult handled by IABUtil.");
            }
        }
        else if(requestCode == 10002) {
            if (billingPremium.getmHelper() == null) return;

            // Pass on the activity result to the helper for handling
            if (!billingPremium.getmHelper().handleActivityResult(requestCode, resultCode, data)) {
                // not handled, so handle it ourselves (here's where you'd
                // perform any handling of activity results not related to in-app
                // billing2...
                super.onActivityResult(requestCode, resultCode, data);
            } else {
                Log.e(TAG, "onActivityResult handled by IABUtil.");
            }
        }
        else if(requestCode == PICK_IMAGE) {
            if(fragmentTag.equals(ProfileFragment.TAG)){
                profileFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        else if(requestCode == UCrop.REQUEST_CROP) {
            if(fragmentTag.equals(ProfileFragment.TAG)){
                profileFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void selectPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_pic)), PICK_IMAGE);
    }

    public void cropImage(Uri sourceUri, Uri destinationUri, UCrop.Options options){
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);
    }

    //Billing Premium------------------------------------

    private void setUpPaymentPremium() {
        billingPremium = BillingConfigPremium.getInstance();
        billingPremium.setCallback(this);
        billingPremium.setPurchaseItemId(viewModel.getSubscriptionProductId());
        billingPremium.initialise(this, viewModel.getBase64EncodedPublicKey());
    }

    public BillingConfigPremium getBillingPremium(){
        return billingPremium;
    }

    public void doPremiumPurchase(){
        if(isPremiumEnable) {
            if(viewModel.isPaymentSync()) {
                billingPremium.doPurchase(viewModel.getPayload());
            }
            else{
                viewModel.syncPrevoiusPayment(subscriptionYear);
                showToast(R.string.previousPayment);
            }
        }
        else{
            showToast(R.string.already_premium_user);
        }
    }

    @Override
    public void onConsumeFinishedPremium(Purchase purchase, IabResult result) {
        Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

        // if we were disposed of in the meantime, quit.
        if (billingPremium.getmHelper() == null) return;

        // We know this is the "gas" sku because it's the only one we consume,
        // so we don't check which sku was consumed. If you have more than one
        // sku, you probably should check...
        if (result.isSuccess()) {
            // successfully consumed, so we apply the effects of the item in our
            // game world's logic, which in our case means filling the gas tank a bit
            Log.d(TAG, "Consumption successful. Provisioning.");

            //TODO reopen it
            premiumSuccessDialog = new ProgressDialog(this);
            premiumSuccessDialog.setMessage(getString(R.string.please_wait));
            premiumSuccessDialog.setCancelable(false);
            premiumSuccessDialog.show();

            viewModel.onSuccessfulPurchasePremium(purchase);
        }
    }

    //Billing---------------------------------------------

    public BillingConfig getBilling(){
        return billing;
    }

    @Override
    public void loading(boolean status) {
//        viewModel.loading(status);
    }

    private void setUpPayment() {
        billing = BillingConfig.getInstance();
        billing.setCallback(this);
        billing.initialise(this, viewModel.getBase64EncodedPublicKey());

//        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk3/SlCJeUB8BfILLYAlaA3B4gg9ZR2qffMHFVvEC9+JMELa00WKmYu+pMg4inPy7nmQZLBUe9pnot7cfk8911Rd3ZgLjmcCCgbhV/2r+l1uZwLYIdhaKGaw7ArsP2Cegk4cXZx55xpArbXiZ1mnPfz2NLLYLR8NisDRXNWfZ770UVt4hic4mrPFIm3E4G9uuXHG3K4brctX4HBca/vnGSPXnMOeOSD6vXd6fV7XVDyKsKJ5vuFqg0Ni1ZqRsy94k/Kt5CkAt5Oj7blSAtwbqzau02SCEtqPjffA1BXrRHQzKnySkFHjSXwASEAzISVMbUJyc5y8Jh/ybrEnjk0AuDwIDAQAB";
//        billing.initialise(this, base64EncodedPublicKey);
    }

    public void setBilling(String productId){
        billing.setUp(productId);
//        billing.setUp("purchase_book");
    }

    public void doBookPurchase(String payload){
        if(viewModel.isPaymentSync()) {
            getBilling().doPurchase(payload);
        }
        else{
            viewModel.syncPrevoiusPayment(subscriptionYear);
            showToast(R.string.previousPayment);
        }
    }

    public void resetBilling() {
        billing = null;
        setUpPayment();
    }

    public void setSubscriptionYear(String year){
        subscriptionYear = year;
    }

    @Override
    public void onConsumeFinished(Purchase purchase, IabResult result) {
        Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

        // if we were disposed of in the meantime, quit.
        if (billing.getmHelper() == null) return;

        // We know this is the "gas" sku because it's the only one we consume,
        // so we don't check which sku was consumed. If you have more than one
        // sku, you probably should check...
        if (result.isSuccess()) {
            // successfully consumed, so we apply the effects of the item in our
            // game world's logic, which in our case means filling the gas tank a bit
            Log.d(TAG, "Consumption successful. Provisioning.");

            viewModel.onSuccessfulPurchase(purchase, subscriptionYear);
        }

        resetBilling();
    }

    @Override
    public void puchPurchaseLoader(boolean status) {
        if(status){

            String titleMessage = getString(R.string.purchase_success_message);
            dialog = new ProgressDialog(this);
            dialog.setMessage(titleMessage);
            dialog.setCancelable(false);
            dialog.show();
        }
        else if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void refreshBooks(int bookId) {
//        booksFragmentReload();
        if(fragmentTag.equals(BooksFragment.TAG)){
            booksFragment.loadBookData(bookId);
        }
    }

    private void logDebug(String msg) {
        Log.e(TAG, msg);
    }

    //------------------------------restoreSuccessful

    public void restore() {
        restoreMessage();

//        showToast(R.string.restoring_in_progress);
        viewModel.restore();
    }


    private void restoreMessage() {
        String titleMessage = getString(R.string.restore_message);

        new AlertDialog.Builder(this)
        .setTitle(getString(R.string.restore))
        .setMessage(titleMessage)
        .setCancelable(false)
        .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
        .create()
        .show();
    }

    @Override
    public void restoreSuccessful(boolean status) {
        if(alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        if(status) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.restore))
                    .setMessage(getString(R.string.restore_successful))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.cancel())
                    .show();
        }
        else{
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.restore))
                    .setMessage(getString(R.string.restore_unsuccessful))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.cancel())
                    .show();
        }
    }

    //Download---------------------------------------

    public void downloadStatus(boolean status){
        if(status) {
            puchPurchaseLoader(false);

            LayoutInflater inflater = getLayoutInflater();
            final View alertLayout = inflater.inflate(R.layout.dialog_download_start, null);
            Button ok = alertLayout.findViewById(R.id.ok);
            progressBar = alertLayout.findViewById(R.id.progressBar);

            downloadDialog = new AlertDialog.Builder(this)
                    .setView(alertLayout)
//                    .setTitle(getString(R.string.downloading))
//                    .setMessage(getString(R.string.downloading_file_message))
                    .setCancelable(false)
                    .create();
//                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())

            downloadDialog.show();

            ok.setOnClickListener(view -> downloadDialog.dismiss());
        }
        else{
            if(downloadDialog != null && downloadDialog.isShowing()){
                downloadDialog.dismiss();
                downloadDialog = null;
            }
            else{
                downloadDialog = null;
            }
        }
    }

    @Override
    public void downloadProgress(int currentProgress) {
        if(progressBar != null){
            Logger.e("progressBar : " + currentProgress);
            progressBar.setProgress(currentProgress);
        }
    }

    // Successful download of the book----------------

    @Override
    public void downloadFileStatus(boolean status) {
        downloadStatus(false);
        if(status){
            LayoutInflater inflater = getLayoutInflater();
            final View alertLayout = inflater.inflate(R.layout.dialog_download_complete, null);
            Button ok = alertLayout.findViewById(R.id.ok);
            Button read = alertLayout.findViewById(R.id.read);

            AlertDialog dialogRead = new AlertDialog.Builder(this)
                    .setView(alertLayout)
//                    .setTitle(getString(R.string.book_downloaded))
//                    .setMessage(getString(R.string.on_download_book_positive_message))
                    .setCancelable(false)
//                    .setPositiveButton(R.string.continue_, (dialog, which) -> {
//                        readBook();
//                        dialog.dismiss();
//                    })
//                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                    .create();
            dialogRead.show();
            ok.setOnClickListener(view -> dialogRead.dismiss());
            read.setOnClickListener(view -> {
                readBook();
                dialogRead.dismiss();
            });
        }
        else{

            LayoutInflater inflater = getLayoutInflater();
            final View alertLayout = inflater.inflate(R.layout.dialog_download_fail, null);
            Button ok = alertLayout.findViewById(R.id.ok);

            AlertDialog dialogFail = new AlertDialog.Builder(this)
                    .setView(alertLayout)
//                    .setTitle(getString(R.string.book_downloaded_failure))
//                    .setMessage(getString(R.string.on_download_book_negative_message))
                    .setCancelable(false)
                    .create();
//                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
            dialogFail.show();
            ok.setOnClickListener(view -> dialogFail.dismiss());
        }
    }

    //Download the book-------------------
    public void downloadFile(Books model) {
        if(downloadDialog == null) {
            downloadStatus(true);
            viewModel.downloadFile(model);
        }
        else{
            downloadDialog.show();
        }
    }

    // Read book----------------------
    public void readBook() {
            //TODO open do encryption and read

        downloadStatus(false);
        readBookDialog(true);
        BooksViewModel viewModelBook = ViewModelProviders.of(this, factory).get(BooksViewModel.class);
        Books model = viewModelBook.getBookModelCurrent();

        ReadBook readBook = ReadBook.getInstance();
        Logger.e("readFile");

        //TODO setUp Read book drm keys
        readBook.setUp(viewModelBook.getDrmToken(), viewModelBook.getCompanyId(), viewModelBook.getApplication(),
                viewModelBook.getPrivateKey(), viewModelBook.getPublicKey(), viewModelBook.getProjectId(),
                viewModelBook.getUserId(),
                viewModelBook.getProjectName());
        Logger.e("DrmId:" + model.getDrmId());
        readBook.setUp(model.getDrmId());
        readBook.setCallback(this);
        readBook.readFile(this, model);
    }

    //Read book dialog---------------------------

    public void readBookDialog(boolean status) {
        if(status){
            dialog = new ProgressDialog(this);
            dialog.setMessage(getString(R.string.loading_wait));
            dialog.setCancelable(false);
            dialog.show();
        }
        else if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    //Books call back-----------------------------

    @Override
    public void onBookDecrypt(byte[] bytes, int requestCode) {
        if(bytes != null && bytes.length > 0){
            readBookDialog(false);
            AppConstants.pdfByte = bytes;
            startActivity(PdfViewerActivity.class);
        }
    }

    @Override
    public void onError(int statusCode, Throwable throwable) {
        readBookDialog(false);
    }

    @Override
    public void onFileDecryptionSuccess(File file) {

    }

    @Override
    public void onResponse(int statusCode, Books model) {

    }

    // update----

    @Override
    public void openPlayStore() {
        appUpdate();
    }

    @Override
    public void unauthorosizePayment(boolean status) {
        showToast(R.string.unauthorisePayment);
    }

    private void appUpdate() {
        new BottomDialog.Builder(this)
                .setTitle(getResources().getString(R.string.update_available))
                .setContent("App version outdated, some features might not be available." + "\n\n" +
                        "Please upgrade to version" + " " + BuildConfig.VERSION_NAME + " " +
                        "for an enhanced experience.")
                .setNegativeText(getResources().getString(R.string.not_now))
                .setPositiveText(getResources().getString(R.string.update_app))
                .onPositive(bottomDialog -> {update(); viewModel.updateVersionDate();})
                .onNegative(bottomDialog -> {viewModel.updateVersionDate();})
                .show();
    }

    public void update() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.maritesallen.almanac2020&hl=en")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.maritesallen.almanac2020&hl=en")));
        }
    }


    //Firebase---------------

    private void firebaseToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    if(task.getResult() != null) {
                        String token = task.getResult().getToken();

                        viewModel.pushFirebaseToken(token);

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e(TAG, msg);
                    }
                });
    }

    //Logout event------------

    private void setupBroadcast() {
        LocalBroadcastManager.getInstance(this).registerReceiver(logoutReceiver,
                new IntentFilter(AppConstants.INTENT_SERVER_RECEIVE));
    }

    private BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra(AppConstants.KEY_FROM_SERVICE);
            Log.d("receiver", "Got message: " + message);
            if (message != null) {
                if (message.equals(AppConstants.LOGOUT_CODE)) {
//                    viewModel.doLogout();
                    viewModel.blockUser(AppConstants.LOGOUT_CODE);
                } else if (message.equals(AppConstants.BLOCK_CODE)) {
                    viewModel.blockUser(AppConstants.BLOCK_CODE);
                }
            }
        }
    };

    //Block user-------------


    @Override
    public void blockUser(String statusCode) {
        if(statusCode.equals(AppConstants.BLOCK_CODE)) {
            try {
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.dialog_block_user, null);
                Button ok = alertLayout.findViewById(R.id.ok);

                AlertDialog dialogFail = new AlertDialog.Builder(this)
                        .setView(alertLayout)
//                    .setTitle(getString(R.string.book_downloaded_failure))
//                    .setMessage(getString(R.string.on_download_book_negative_message))
                        .setCancelable(false)
                        .create();
//                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                dialogFail.show();
                ok.setOnClickListener(view -> {
                    dialogFail.dismiss();
                    doLogout(true, "");
                });
            }
            catch (Exception e){
                e.printStackTrace();
//                doLogout(true, "");
            }
        }
    }


}
