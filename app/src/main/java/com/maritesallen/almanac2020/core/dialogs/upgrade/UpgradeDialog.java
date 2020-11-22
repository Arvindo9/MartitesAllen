package com.maritesallen.almanac2020.core.dialogs.upgrade;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.databinding.DialogUpgradeBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.AppConstants;

import javax.inject.Inject;

public class UpgradeDialog extends BaseDialog<DialogUpgradeBinding, UpgradeViewModel>
        implements UpgradeNavigator{
    public static final String TAG = UpgradeDialog.class.getSimpleName();
    @Inject
    ViewModelProviderFactory factory;
    private UpgradeViewModel viewModel;
    private DialogUpgradeBinding binding;

    private DialogListener listener;

    public UpgradeDialog(int size, DialogListener listener){
        super(size);
        this.listener = listener;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_upgrade;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public UpgradeViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(UpgradeViewModel.class);
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
    public void getActivityBinding(DialogUpgradeBinding binding) {
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
    public void onPurchaseClick() {
        listener.onSuccessDialogResponse(TAG, AppConstants.YES);
        viewModel.cancel();
        dismissDialog();
    }

    @Override
    public void onCancelClick() {
        listener.onSuccessDialogResponse(TAG, AppConstants.NO);
        viewModel.cancel();
        dismissDialog();
    }
}
