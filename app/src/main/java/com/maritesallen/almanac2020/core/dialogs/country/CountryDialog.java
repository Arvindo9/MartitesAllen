package com.maritesallen.almanac2020.core.dialogs.country;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.country.countryAdapter.CountryAdapter;
import com.maritesallen.almanac2020.databinding.DialogCountryBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;

import javax.inject.Inject;

import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_FULL_MARGIN;

/**
 * Author : Arvindo Mondal
 * Created on 29-11-2019
 */
public class CountryDialog extends BaseDialog<DialogCountryBinding, CountryViewModel>
        implements CountryNavigator, CountryAdapter.Listener{
    public static final String TAG = CountryDialog.class.getSimpleName();

    @Inject
    CountryAdapter adapter;
    @Inject
    ViewModelProviderFactory factory;
    private CountryViewModel viewModel;
    private DialogCountryBinding binding;

    public CountryDialog(){
        super(DIALOG_SIZE_FULL_MARGIN);
    }

    private DialogListener listener;

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_country;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public CountryViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(CountryViewModel.class);
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
    public void getActivityBinding(DialogCountryBinding binding) {
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
        viewModel.getFlagsDb();
        setFilter();
    }

    private void setUpAdapter() {
        adapter.setListener(this);
        binding.listView.setAdapter(adapter);
    }

    private void subscribeToLiveData() {
        viewModel.getLiveData().observe(this, model ->{

                    viewModel.addDataToList(model);

                    if(binding.listView.getLayoutManager() != null) {
                        binding.listView.getLayoutManager().scrollToPosition(viewModel.getListPosition());
                    }
                });
    }

    private void setFilter(){
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
/*
                if (s.length() > 0) {
                    if (binding.clearBtn.getVisibility() == View.GONE) {
                        binding.clearBtn.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (binding.clearBtn.getVisibility() == View.VISIBLE) {
                        binding.clearBtn.setVisibility(View.GONE);
                    }
                }
                */
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onFlagLoadFailed(String message) {
        if(message != null) {
            getBaseActivity().showToast(message);
        }else{
            getBaseActivity().showToast(R.string.country_load_fail);
        }
        viewModel.cancel();
    }

    @Override
    public void onCancelClick() {
        dismissDialog();
        viewModel.cancel();
    }


    @Override
    public void onRetryClick() {

    }

    @Override
    public void onCountrySelected(String id, String country) {
        listener.onSuccessDialogResponse(TAG, id, country);
        dismissDialog();
        viewModel.cancel();
    }
}
