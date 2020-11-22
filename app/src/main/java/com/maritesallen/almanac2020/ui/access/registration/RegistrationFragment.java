package com.maritesallen.almanac2020.ui.access.registration;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.location.Location;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.country.CountryDialog;
import com.maritesallen.almanac2020.core.dialogs.date.DateDialog;
import com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog;
import com.maritesallen.almanac2020.databinding.FragmentRegistrationBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.util.DeviceId;
import com.maritesallen.almanac2020.utils.util.Dimensions;
import com.maritesallen.almanac2020.utils.util.General;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_DISCLAMIER;
import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_TERMS;
import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_FULL;

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
public class RegistrationFragment extends BaseFragment<FragmentRegistrationBinding, RegistrationViewModel>
        implements RegistrationNavigator, DateDialog.DateInterface, DialogListener {
    @Inject
    ViewModelProviderFactory factory;
    private FragmentRegistrationBinding binding;
    private RegistrationViewModel viewModel;
    private boolean showPassword = false;
    private boolean showConfirmPassword = false;
    private Location location;
    private String deviceId = "123";
    private String birthDate = "";
    private boolean idRegistrationComplete = false;
    private boolean isTermsAccepted = false;
    private String countryId = "";

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentRegistrationBinding binding) {

        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_registration;
    }

    /**
     * Override for set view model
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public RegistrationViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(RegistrationViewModel.class);
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
//        setUpUI();
//        setUpUIText();
    }

    private void setUp() {
        binding.termCheckBok.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked && !isTermsAccepted){
                binding.termCheckBok.setChecked(false);
            }

//            if(isTermsAccepted){
//                binding.termCheckBok.setChecked(true);
//            }
        });

        if (getBaseActivity() != null) {
            getBaseActivity().hideKeyboard();
            if (getBaseActivity().isNetworkAvailable()) {
                getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_TERMS),
                        TermsConditionDialog.TAG);
            } else {
                getBaseActivity().showToast(R.string.network_error);
            }
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

    private void setUpUIText() {
//        SpannableStringBuilder longDescription = new SpannableStringBuilder();
        SpannableStringBuilder ss = new SpannableStringBuilder(getString(R.string.terms));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View textView) {
                //Start Dialog
                if(isNetworkConnected()) {
                    getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, RegistrationFragment.this, TYPE_TERMS),
                            TermsConditionDialog.TAG);
                }
                else{
                    showMessage(R.string.network_error);
                }
            }
            @Override
            public void updateDrawState(@NotNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        ss.setSpan(clickableSpan, 28, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan, 54, 68, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        binding.terms.setText(ss);
        binding.terms.setMovementMethod(LinkMovementMethod.getInstance());
        binding.terms.setHighlightColor(Color.TRANSPARENT);
    }

    private void setUpUI() {
        LinearLayoutCompat.LayoutParams layoutParams = new
                LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Dimensions.getScreenHeight(getBaseActivity()) /3);
//        binding.logoLayout.setLayoutParams(layoutParams);
    }

    //Navigation------------------------------

    @Override
    public void onShowPasswordClick() {
        //to show
        if(!showPassword) {
            binding.password.setTransformationMethod(new PasswordTransformationMethod());
            showPassword = true;
            binding.showPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_eye));
        }
        else{
            //to hide
            binding.password.setTransformationMethod(null);
            showPassword = false;
            binding.showPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_eye_close));
        }
    }

    @Override
    public void onShowConfirmPasswordClick() {
        //to show
        if(!showConfirmPassword) {
            binding.confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            showConfirmPassword = true;
            binding.showConfirmPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_eye));
        }
        else{
            //to hide
            binding.confirmPassword.setTransformationMethod(null);
            showConfirmPassword = false;
            binding.showConfirmPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_eye_close));
        }
    }

    @Override
    public void onSignUpClick() {
        getBaseActivity().hideKeyboard();
        submitForm();
    }

    @Override
    public void onDateClick() {
        getBaseActivity().hideKeyboard();

        DateDialog dateFragment = DateDialog.newInstance(this);
        dateFragment.show(getBaseActivity().getSupportFragmentManager(), DateDialog.TAG);
    }

    @Override
    public void onTimeClick() {
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
    public void onTermsClick() {

        if (getBaseActivity() != null) {
            getBaseActivity().hideKeyboard();
            if (getBaseActivity().isNetworkAvailable()) {
                getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_TERMS),
                        TermsConditionDialog.TAG);
            } else {
                getBaseActivity().showToast(R.string.network_error);
            }
        }
    }

    @Override
    public void onCountryClick() {
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
    public void onRegistrationSuccess() {
        idRegistrationComplete = true;
        if(isNetworkConnected()) {
            getBaseActivity().hideKeyboard();
            //Start Dialog
            getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_DISCLAMIER),
                    TermsConditionDialog.TAG);
        }
        else{
            showMessage(R.string.network_error);
        }
    }

    private void openDashboard(){
        if(getBaseActivity() != null) {
            getBaseActivity().startActivity(DashboardActivity.class);
            getBaseActivity().finishAffinity();
        }
    }

    @Override
    public void onRegistrationError(String message) {
        if(message != null){
            getBaseActivity().showToast(message);
        }
        else{
            getBaseActivity().showToast(R.string.error_default);
        }
    }

    @Override
    public void showMessage(int message) {
        if(getBaseActivity() != null) {
            getBaseActivity().showToast(message);
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

    private boolean isValidLastDate(String[] params) {
        int enterDate = Integer.parseInt(params[4]);
        if(enterDate >= 1932){
            return true;
        }

        return false;
    }

    private boolean isValidDate(String[] params) {
        int currentDate = General.getDateInt(new Date());
        int enterDate = Integer.parseInt(params[1]);
        if(enterDate <= currentDate){
            return true;
        }

        return false;
    }


    //Submit task------------------------------

    private void submitForm() {
        if (!validateFirstName(binding.name)) {
            return;
        }

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

        if (!validatePassword(binding.password)) {
            return;
        }

        if (!validatePasswordConfirm(binding.password, binding.confirmPassword)) {
            return;
        }

        if(!binding.termCheckBok.isChecked()){
            getBaseActivity().showToast(R.string.accept_terms);
            return;
        }

        String latitude = "0.0";
        String longitude = "0.0";

        if(location != null){
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
        }

        String email = binding.email.getText() != null ? binding.email.getText().toString() : "";
        String name = binding.name.getText() != null ? binding.name.getText().toString() : "";
        String time = binding.birthTime.getText() != null ? binding.birthTime.getText().toString() : "";
//        String county = binding.name.getText() != null ? binding.name.getText().toString() : "";
        String county = countryId;
        String password = binding.password.getText() != null ? binding.password.getText().toString() : "";
        String cPassword = binding.confirmPassword.getText() != null ? binding.confirmPassword.getText().toString() : "";

        Logger.e("firstName:" + name);
        Logger.e("email:" + email);
        Logger.e("birthDate:" + birthDate);
        Logger.e("time:" + time);
        Logger.e("country:" + county);
        Logger.e("password:" + password);
        Logger.e("deviceId:" + deviceId);
        Logger.e("latitude:" + latitude);
        Logger.e("longitude:" + longitude);

        viewModel.doRegistration(name, email, birthDate, time, county, password, latitude + "," + longitude, deviceId);
    }

    //Additional-------------------------------

    private boolean validateMobile(EditText editText) {
        String string = editText.getText().toString();
        String regex = "[0-9]+";
        if (string.trim().isEmpty()) {
            editText.setError(getString(R.string.mobile_number_empty));
            getBaseActivity().requestFocus(editText);
            return false;
        }
        else if (!string.matches(regex) ||
                string.length() != 10)  {
            editText.setError(getString(R.string.errorMobile));
            getBaseActivity().requestFocus(editText);
            return false;
        } else {
            editText.setError(null);
        }

        return true;
    }

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

    private boolean validatePassword(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(getString(R.string.invalid_password));
            getBaseActivity().requestFocus(editText);
            return false;
        } else if (editText.getText().toString().trim().length()< 6) {
            editText.setError(getString(R.string.invalid_password_length));
            getBaseActivity().requestFocus(editText);
            return false;
        }
        else {
            editText.setError(null);
        }

        return true;
    }

    private boolean validatePasswordConfirm(EditText passwordText, EditText confirmText) {
        if (!passwordText.getText().toString().equals(confirmText.getText().toString())) {
            confirmText.setError(getString(R.string.invalid_confirm_password));
            getBaseActivity().requestFocus(confirmText);
            return false;
        } else {
            confirmText.setError(null);
        }

        return true;
    }

    private boolean validateFirstName(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(getString(R.string.invalid_first_name));
            getBaseActivity().requestFocus(editText);
            return false;
        } else {
            editText.setError(null);
        }

        return true;
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

    /**
     * Default response method of a dialog
     *
     * @param tag    class name from which the response is getting
     * @param params string array with relative Data
     */
    @Override
    public void onSuccessDialogResponse(String tag, String... params) {
        if(tag.equals(TermsConditionDialog.TAG)){
            if(params.length > 0 && params[0].equals(AppConstants.YES) &&
                    params[1].equals(String.valueOf(TYPE_TERMS))){
                isTermsAccepted = true;
                binding.termCheckBok.setChecked(true);
            }
            else if(params.length > 0 && params[0].equals(AppConstants.NO) &&
                    params[1].equals(String.valueOf(TYPE_TERMS))){
                isTermsAccepted = false;
                binding.termCheckBok.setChecked(false);
            }
            else if(params.length > 0 && params[0].equals(AppConstants.YES) &&
                    params[1].equals(String.valueOf(TYPE_DISCLAMIER))){
                if(idRegistrationComplete){
                    openDashboard();
                }
            }
        }
        else if(tag.equals(CountryDialog.TAG)){
            if(params.length > 0){
                binding.country.setText(params[1]);
                countryId = params[0];
            }
        }
    }
}
