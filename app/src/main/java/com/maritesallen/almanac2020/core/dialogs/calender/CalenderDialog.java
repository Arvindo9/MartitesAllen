package com.maritesallen.almanac2020.core.dialogs.calender;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.calender.adapter.CalenderAdapter;
import com.maritesallen.almanac2020.databinding.DialogCalenderBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;

import javax.inject.Inject;

public class CalenderDialog extends BaseDialog<DialogCalenderBinding, CalenderDialogViewModel>
        implements CalenderNavigator, CalenderAdapter.Listener {
    public static final String TAG = CalenderDialog.class.getSimpleName();
    @Inject
    ViewModelProviderFactory factory;
    @Inject
    CalenderAdapter adapter;
    private CalenderDialogViewModel viewModel;
    private DialogCalenderBinding binding;
    private DialogListener listener;

    public CalenderDialog(DialogListener listener){
        this.listener = listener;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_calender;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public CalenderDialogViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(CalenderDialogViewModel.class);
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
    public void getActivityBinding(DialogCalenderBinding binding) {
        this.binding = binding;
    }


    /**
     * Do anything on onCreateView after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        setUpAdapter();
        subscribeToLiveData();
        viewModel.loadAdapterData(getResources().getStringArray(R.array.monthsFull));
    }

    private void subscribeToLiveData() {
        viewModel.getLiveData().observe(this, data ->
                viewModel.addDataToList(data));
    }


    private void setUpAdapter() {
        adapter.setListener(this);
        binding.listView.setAdapter(adapter);
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onMonthClick(int id, String month) {
        listener.onSuccessDialogResponse(TAG, String.valueOf(id), month);
        dismissDialog();
        viewModel.cancel();
    }

    @Override
    public void onCancelClick() {
        dismissDialog();
        viewModel.cancel();
    }
}
