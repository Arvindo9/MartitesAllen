package com.maritesallen.almanac2020.core.dialogs.sessionExpire;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.databinding.DialogSessionExpireBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.splash.SplashActivity;

import javax.inject.Inject;

public class SessionExpireDialog extends BaseDialog<DialogSessionExpireBinding, SessionExpireViewModel>
        implements SessionExpireNavigator{

    @Inject
    ViewModelProviderFactory factory;
    private SessionExpireViewModel viewModel;
    private DialogSessionExpireBinding binding;

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_session_expire;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public SessionExpireViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(SessionExpireViewModel.class);
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
    public void getActivityBinding(DialogSessionExpireBinding binding) {
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
    public void onProceedClick() {
        viewModel.cancel();
        if(getBaseActivity() != null){
            getBaseActivity().startActivity(SplashActivity.class);
            getBaseActivity().finishAffinity();
        }
        dismissDialog();
    }

    @Override
    public void onCancelClick() {
        viewModel.cancel();
        if(getBaseActivity() != null){
            getBaseActivity().startActivity(SplashActivity.class);
            getBaseActivity().finishAffinity();
        }
        dismissDialog();
    }
}
