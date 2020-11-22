package com.maritesallen.almanac2020.ui;

import androidx.databinding.ViewDataBinding;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.base.BaseViewModel;

/**
 * Author : Arvindo Mondal
 * Created on 05-02-2020
 */
public class TmpFrag extends BaseFragment {
    private ViewDataBinding binding;

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(ViewDataBinding binding) {

        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_forecast;
    }

    /**
     * Override for set view model
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public BaseViewModel getViewModel() {
        return null;
    }

    /**
     * Override for set binding variable
     *
     * @return BR.CalendarData;
     */
    @Override
    public int getBindingVariable() {
        return 0;
    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {

    }
}
