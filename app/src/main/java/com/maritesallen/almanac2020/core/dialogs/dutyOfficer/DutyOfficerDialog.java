package com.maritesallen.almanac2020.core.dialogs.dutyOfficer;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.databinding.DialogDutyOfficerBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.bundles.Bundles;

import javax.inject.Inject;

/**
 * Author : Arvindo Mondal
 * Created on 07-12-2019
 */
public class DutyOfficerDialog extends BaseDialog<DialogDutyOfficerBinding, DutyOfficerViewModel> implements
        DutyOfficerNavigator{
    public static final String TAG = DutyOfficerDialog.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private DutyOfficerViewModel viewModel;
    private DialogDutyOfficerBinding binding;
    private String title;
    private String body;

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_duty_officer;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public DutyOfficerViewModel getViewModel() {
        title = Bundles.getDutyOfficerTitle(getArguments());
        body = Bundles.getDutyOfficerBody(getArguments());
        return viewModel = ViewModelProviders.of(this,factory).get(DutyOfficerViewModel.class);
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
    public void getActivityBinding(DialogDutyOfficerBinding binding) {
        this.binding = binding;
    }

    /**
     * Do anything on onCreateView after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        viewModel.setData(title, body);
    }

    @Override
    public void onCancelClick() {
        dismissDialog();
        viewModel.cancel();
    }
}
