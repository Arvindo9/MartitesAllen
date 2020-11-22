package com.maritesallen.almanac2020.core.dialogs.guest;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.databinding.DialogGuestBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.AppConstants;

import javax.inject.Inject;

/**
 * Author : Arvindo Mondal
 * Created on 10-12-2019
 */
public class GuestDialog extends BaseDialog<DialogGuestBinding, GuestViewModel>
        implements GuestNavigator{
    public static final String TAG = GuestDialog.class.getSimpleName();
    private DialogListener listener;
    private GuestViewModel viewModel;
    @Inject
    ViewModelProviderFactory factory;
    private DialogGuestBinding binding;

    public GuestDialog(DialogListener listener){
        this.listener = listener;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_guest;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public GuestViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(GuestViewModel.class);
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
     * @param binding activity class CalendarData binding
     */
    @Override
    public void getActivityBinding(DialogGuestBinding binding) {
        this.binding = binding;
    }

    /**
     * Do anything on onCreateView after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
    }

    @Override
    public void onCancelClick() {
        viewModel.cancel();
        dismissDialog();
        listener.onSuccessDialogResponse(TAG, AppConstants.NO);
    }

    @Override
    public void onProceedClick() {
        viewModel.cancel();
        dismissDialog();
        listener.onSuccessDialogResponse(TAG, AppConstants.YES);
    }
}
