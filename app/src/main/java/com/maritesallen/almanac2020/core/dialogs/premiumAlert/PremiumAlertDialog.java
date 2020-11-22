package com.maritesallen.almanac2020.core.dialogs.premiumAlert;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.databinding.DialogPremiumAlertBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.profile.ProfileFragment;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.bundles.Bundles;

import javax.inject.Inject;

public class PremiumAlertDialog extends BaseDialog<DialogPremiumAlertBinding, PremiumAlertViewModel>
        implements PremiumAlertNavigator{

    public static final String TAG = PremiumAlertDialog.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private PremiumAlertViewModel viewModel;
    private DialogPremiumAlertBinding binding;
    private DialogListener listener;
    private String tag = "";

    public PremiumAlertDialog(DialogListener listener){
        this.listener = listener;
    }


    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_premium_alert;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public PremiumAlertViewModel getViewModel() {
        tag = Bundles.getPremiumAlertDialog(getArguments());
        return viewModel = ViewModelProviders.of(this,factory).get(PremiumAlertViewModel.class);
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
    public void getActivityBinding(DialogPremiumAlertBinding binding) {

        this.binding = binding;
    }

    /**
     * Do anything on onCreateView after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        if(tag.equals(ProfileFragment.TAG)){
            binding.title.setText(getString(R.string.premiumAlertTitleGuest));
            binding.body.setText(getString(R.string.premiumAlertBodyGuest));
        }
    }

    @Override
    public void onProceedClick() {
/*
        if(tag.equals("Default") || tag.equals(CalenderDialog.TAG)){
            viewModel.checkLoginMode();
        }
        else if(getBaseActivity() != null){
            getBaseActivity().startActivity(SplashActivity.class);
            getBaseActivity().finishAffinity();
        }
        dismissDialog();
*/

        viewModel.cancel();
        dismissDialog();
        listener.onSuccessDialogResponse(TAG, AppConstants.YES);
    }

    @Override
    public void onCancelClick() {
        viewModel.cancel();
        dismissDialog();
        listener.onSuccessDialogResponse(TAG, AppConstants.NO);
    }

    @Override
    public void openPremiumAlertDialogProfile() {
/*
        if(getBaseActivity() != null){
            getBaseActivity().startDialog(new PremiumAlertDialog(), PremiumAlertDialog.TAG,
                    Bundles.setPremiumAlertDialog(ProfileFragment.TAG));
        }
        dismissDialog();
        */
    }
}
