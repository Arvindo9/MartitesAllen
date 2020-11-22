package com.maritesallen.almanac2020.ui.access.registrationFacebook;

import android.app.TimePickerDialog;
import android.location.Location;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.country.CountryDialog;
import com.maritesallen.almanac2020.core.dialogs.date.DateDialog;
import com.maritesallen.almanac2020.databinding.FragmentRegistrationFacebookBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.bundles.Bundles;
import com.maritesallen.almanac2020.utils.util.DeviceId;
import com.maritesallen.almanac2020.utils.util.General;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/**
 * Author       : Arvindo Mondal
 * Created on   : 11-11-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class RegistrationFacebookFragment extends BaseFragment<FragmentRegistrationFacebookBinding,
        RegistrationFacebookViewModel> implements RegistrationFacebookNavigation,
        DateDialog.DateInterface, DialogListener {

    @Inject
    ViewModelProviderFactory factory;
    private RegistrationFacebookViewModel viewModel;
    private FragmentRegistrationFacebookBinding binding;
    private String birthDate = "";
    private String countryId = "";
    private Location location;
    private String deviceId;
    private String email = "";

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentRegistrationFacebookBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_registration_facebook;
    }

    /**
     * Override for set view model
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public RegistrationFacebookViewModel getViewModel() {
        email = Bundles.getFacebookEmail(getArguments());
        return viewModel = ViewModelProviders.of(this,factory).get(RegistrationFacebookViewModel.class);
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
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        setUpLocation();
        setUp();
    }

    private void setUp() {
        if(email != null && !email.isEmpty()){
            binding.email.setText(email);
            binding.email.setFocusable(false);
        }

        countryId = "175";
        binding.country.setText(getText(R.string.philippines));
    }

    private void setUpLocation() {
        try {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getBaseActivity());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getBaseActivity(), location -> {
                        // Got last known location. In some rare situations this can be null.
                        this.location = location;
                    });
        }
        catch (Exception e){
            e.printStackTrace();
        }

        deviceId = DeviceId.GetDeviceId(new WeakReference<>(getBaseActivity()));
    }

    //-------------------------------------

    @Override
    public void onDateClick(View view) {
        getBaseActivity().hideKeyboard();

        DateDialog dateFragment = DateDialog.newInstance(this);
        dateFragment.show(getBaseActivity().getSupportFragmentManager(), DateDialog.TAG);
    }

    @Override
    public void onTimeClick(View view) {
        getBaseActivity().hideKeyboard();
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getBaseActivity(),  R.style.TimeDialog,
                (timePicker, selectedHour, selectedMinute) -> {
                    String data = selectedHour + ":" + selectedMinute;
                    binding.birthTime.setText(data);
                },
                hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(getString(R.string.select_time));
        mTimePicker.show();
    }

    @Override
    public void onCountryClick(View view) {

        getBaseActivity().hideKeyboard();
        if(isNetworkConnected()) {
            //Start Dialog
            CountryDialog dialog = new CountryDialog();

            dialog.setListener(this);
            getBaseActivity().startDialog(dialog, CountryDialog.TAG);
        }
        else{
            showMessage(R.string.network_error);
        }
    }

    @Override
    public void onLoginUpClick(View view) {
        getBaseActivity().hideKeyboard();
        submitForm();
    }

    @Override
    public void openDashboard() {
        if(getBaseActivity() != null) {
            getBaseActivity().startActivity(DashboardActivity.class);
            getBaseActivity().finishAffinity();
        }
    }

    @Override
    public void onLoginError(String message) {
        if(message != null){
            getBaseActivity().showToast(message);
        }
        else{
            getBaseActivity().showToast(R.string.error_default);
        }
    }

    @Override
    public void showMessage(int message) {
        if(getBaseActivity() != null){
            getBaseActivity().showToast(message);
        }
    }

    /**
     * Default response method of a dialog
     *
     * @param tag    class name from which the response is getting
     * @param params string array with relative Data
     */
    @Override
    public void onSuccessDialogResponse(String tag, String... params) {
        if(tag.equals(CountryDialog.TAG)){
            if(params.length > 0){
                binding.country.setText(params[1]);
                countryId = params[0];
            }
        }
    }

    /**
     * @param date   date will return in string format
     * @param params date will return in int format
     */
    @Override
    public void date(String date, String... params) {
        getBaseActivity().hideKeyboard();


        if(!isValidLastDate(params)){
            getBaseActivity().showToast(R.string.last_date_error);
        }
        else if(isValidDate(params)){
            binding.birthDate.setText(General.getDateReadable(date));
            birthDate = params[0];
        }
        else{
            getBaseActivity().showToast(R.string.future_date_error);
        }
    }


    //Submit task------------------------------

    private void submitForm() {
        if (!validateEmail(binding.email)) {
            return;
        }

        if (birthDate.isEmpty()) {
            getBaseActivity().showToast(R.string.enter_birth_date);
            return;
        }

        if (!validateCountry(binding.country) && countryId.isEmpty()) {
            return;
        }

        String latitude = "0.0";
        String longitude = "0.0";

        if(location != null){
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
        }

        String email = binding.email.getText() != null ? binding.email.getText().toString() : "";
        String time = binding.birthTime.getText() != null ? binding.birthTime.getText().toString() : "";
//        String county = binding.name.getText() != null ? binding.name.getText().toString() : "";
        String county = countryId;

        Logger.e("birthDate:" + birthDate);
        Logger.e("time:" + time);
        Logger.e("country:" + county);
        Logger.e("deviceId:" + deviceId);
        Logger.e("latitude:" + latitude);
        Logger.e("longitude:" + longitude);

        viewModel.doLoginWithFacebook(email, birthDate, time, county);
    }

    //Additional-------------------------------

    private boolean validateEmail(EditText editText) {
        String string = editText.getText().toString();
        if (string.matches("[*a-zA-Z]") ||
                !string.contains("@") ||
                !string.contains(".") ||
                string.contains("@.")||
                string.startsWith("@")||
                string.endsWith(".")||
                (string.lastIndexOf(".") < string.indexOf("@"))) {
            editText.setError(getString(R.string.invalid_email));
            getBaseActivity().requestFocus(editText);
            return false;
        }
        else {
            editText.setError(null);
        }

        return true;
    }

    private boolean isValidDate(String[] params) {
        int currentDate = General.getDateInt(new Date());
        int enterDate = Integer.parseInt(params[1]);
        if(enterDate <= currentDate){
            return true;
        }

        return false;
    }

    private boolean isValidLastDate(String[] params) {
        int enterDate = Integer.parseInt(params[4]);
        if(enterDate >= 1932){
            return true;
        }

        return false;
    }

    private boolean validateCountry(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(getString(R.string.invalid_country));
            getBaseActivity().requestFocus(editText);
            return false;
        } else {
            editText.setError(null);
        }

        return true;
    }
}
