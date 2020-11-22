package com.maritesallen.almanac2020.core.dialogs.alert;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.BR;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.databinding.DialogAlertBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;

import javax.inject.Inject;

/**
 * Author       : Arvindo Mondal
 * Created on   : 26-08-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class AlertDialog extends BaseDialog<DialogAlertBinding, AlertViewModel> implements AlertNavigator{

    public static final String TAG = AlertDialog.class.getSimpleName();
    private static final String KEY_DIALOG_TITLE = "DIALOG_TITLE";
    private static final String KEY_DIALOG_HEADING = "KEY_DIALOG_HEADING";
    private static final String KEY_DIALOG_BODY = "KEY_DIALOG_BODY";

    @Inject
    ViewModelProviderFactory factory;
    private AlertViewModel viewModel;
    private DialogAlertBinding binding;
    private DialogListener callBack;
    private String title;
    private String heading;
    private String body;

    public static AlertDialog newInstance(String title, String heading, String body) {
        AlertDialog fragment = new AlertDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DIALOG_TITLE, title);
        bundle.putString(KEY_DIALOG_HEADING, heading);
        bundle.putString(KEY_DIALOG_BODY, body);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setCallBack(DialogListener callBack){
        this.callBack = callBack;
    }

    public AlertDialog() {
        super();
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_alert;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public AlertViewModel getViewModel() {
        title = getArguments() != null ? getArguments().getString(KEY_DIALOG_TITLE) : "";
        heading = getArguments() != null ? getArguments().getString(KEY_DIALOG_HEADING) : "";
        body = getArguments() != null ? getArguments().getString(KEY_DIALOG_BODY) : "";
        return viewModel = ViewModelProviders.of(this,factory).get(AlertViewModel.class);
    }

    /**
     * Override for set binding variable
     *
     * @return BR.CalendarData;
     */
    @Override
    public int getBindingVariable() {
        return BR.data;
    }

    /**
     * @param binding activity class CalendarData binding
     */
    @Override
    public void getActivityBinding(DialogAlertBinding binding) {
        this.binding = binding;
    }

    /**
     * Do anything on onCreateView after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        binding.title.setText(title);
        binding.heading.setText(heading);
        binding.body.setText(body);
    }

    //Navigator---------------------------

    @Override
    public void onCancelClick() {

    }

    @Override
    public void onYesClick() {
        callBack.onSuccessDialogResponse(TAG, String.valueOf(AppConstants.YES_TRUE));
        Logger.e(TAG + " :  " + AppConstants.YES_TRUE);
    }

    @Override
    public void onNoClick() {

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void handleMessage(String message) {

    }

    @Override
    public void handleMessage(int index) {

    }
}
