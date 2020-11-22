package com.maritesallen.almanac2020.ui.dashboard.calender;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.databinding.FragmentCalenderBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.ui.dashboard.calender.calenderAdapter.CalenderAdapter;
import com.maritesallen.almanac2020.utils.bundles.Bundles;
import com.maritesallen.almanac2020.utils.util.General;

import java.util.Date;

import javax.inject.Inject;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class CalenderFragment extends BaseFragment<FragmentCalenderBinding, CalenderViewModel>
        implements CalenderNavigator, CalenderAdapter.Listener {
    public static final String TAG = CalenderFragment.class.getSimpleName();

    @Inject
    CalenderAdapter adapter;
    @Inject
    ViewModelProviderFactory factory;
    private FragmentCalenderBinding binding;
    private CalenderViewModel viewModel;
    private LinearLayoutManager layoutManager;
    private ProgressDialog dialog;
    private int monthId;
    private int day;
    private String month;

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentCalenderBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_calender;
    }

    /**
     * Override for set view model
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public CalenderViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this, factory).get(CalenderViewModel.class);
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

    @Override
    protected void onCreateFragment(Bundle savedInstanceState, Bundle args) {
        monthId = Bundles.getCalendarMonthId(args);
        day = Bundles.getCalendarDay(args);
        month = Bundles.getCalendarMonth(args);
    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        subscribeToLiveData();
        setUp();
        setUpAdapter();

        if(monthId < 0 || monthId == General.getCurrentMonth()){
            loadData();
        }
        else{
            onCalenderClick(monthId, day, month);
        }

//        loadData();
    }

    private void setUp() {
        String[] months = getResources().getStringArray(R.array.monthsFull);
//        String month = months[General.CURRENT_MONTH];
        String month = months[General.getCurrentMonth()];
        ((DashboardActivity) getBaseActivity()).setCalender(month + " 2020");

        //TODO set current data
    }

    private void setUpAdapter() {
        adapter.setListener(this);
        adapter.setAdsId(viewModel.getAdsUnitId());
        adapter.setContext(getBaseActivity());
//        binding.listView.hasFixedSize();
        binding.listView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getBaseActivity());
        binding.listView.setLayoutManager(layoutManager);
    }

    private void subscribeToLiveData() {
        viewModel.getLiveData().observe(this, data ->{
                    viewModel.addDataToList(data);
                    onCalendarLaunch(General.getCurrentMonth(), General.getCurrentDay(), General.getDateReadable());
                });
    }

    private void loadData() {
        adapter.setCurrentDate(General.getCurrentMonth()+1, General.getCurrentDay());
        viewModel.getCalendarDb();
    }

    //Navigator------------------------

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onMonthReach(int monthId) {
        //Hide the for single month
/*
        String[] months = getResources().getStringArray(R.array.monthsFull);
        try {
            String month = months[monthId-1];
            ((DashboardActivity) getBaseActivity()).setCalender(month + " 2020");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        */
    }

    /*
        use the when calendar first loaded, to set current data
     */
    @Override
    public void onCalendarLaunch(int monthId, int day, String month) {
        if(binding.listView.getLayoutManager() != null) {
            int i = viewModel.getListPosition(monthId + 1, day);
            if(i != 0){
                layoutManager.scrollToPositionWithOffset(i, 5);
            }
        }
    }

    //TODO Scroll calendar to current date
    public void onCalenderClick(int monthId, int day, String month){
        String s = month.substring(month.indexOf(" "));
        ((DashboardActivity) getBaseActivity()).setCalender(s);

        if(binding.listView.getLayoutManager() != null) {
//            binding.listView.getLayoutManager().scrollToPosition(viewModel.getListPosition(monthId+1, day));
            int i = viewModel.getListPosition(monthId + 1, day);
            if(i != 0){
                layoutManager.scrollToPositionWithOffset(i, 5);
            }
            else{
                viewModel.getCalendarApi(monthId + 1, true);
            }
        }
    }

    /*
        Currently not in use
     */
    public void onCalenderClick(String id, String month){
        ((DashboardActivity) getBaseActivity()).setCalender(month);

        if(binding.listView.getLayoutManager() != null) {
            binding.listView.getLayoutManager().scrollToPosition(viewModel.getListPosition(Integer.parseInt(id), 0));
//            layoutManager.scrollToPositionWithOffset(viewModel.getListPosition(Integer.parseInt(id), 0), 20);
        }
    }

    @Override
    public void onDataLoad(int currentMonth, Date currentDate) {
        if(binding.listView.getLayoutManager() != null) {
            adapter.setCurrentDate(currentMonth+1, 0);
            binding.listView.getLayoutManager().scrollToPosition(viewModel.getListPosition(
                    currentMonth+1, currentMonth));
        }
    }

    @Override
    public void showMessage(int message) {
        if(getBaseActivity() != null){
            getBaseActivity().showToast(message);
        }
    }

    @Override
    public void showDialog(boolean status, boolean shouldShow) {
        if(shouldShow) {
            if (status) {
//            dialog = ProgressDialog.show(getBaseActivity(), getString(R.string.calenderTmp),
//                    getString(R.string.downloading), true);

                dialog = new ProgressDialog(getBaseActivity());
                dialog.setMessage(getString(R.string.loading));
                dialog.setCancelable(false);
                dialog.show();
            } else if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
