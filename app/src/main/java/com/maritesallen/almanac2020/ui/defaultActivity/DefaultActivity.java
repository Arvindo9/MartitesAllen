package com.maritesallen.almanac2020.ui.defaultActivity;

import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseActivity;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ActivityList;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.UnSuitableActivityList;
import com.maritesallen.almanac2020.databinding.ActivityDefaultBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.defaultActivity.forecastCalendarAdapter.ForecastCalendarAdapter;
import com.maritesallen.almanac2020.ui.defaultActivity.suitableAdapter.SuitableAdapter;
import com.maritesallen.almanac2020.ui.defaultActivity.unsuitableAdapter.UnsuitableAdapter;
import com.maritesallen.almanac2020.utils.bundles.Bundles;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.maritesallen.almanac2020.utils.AppConstants.KEY_DEFAULT_ACTIVITY_BUNDLE;

/**
 * Author : Arvindo Mondal
 * Created on 07-12-2019
 */
public class DefaultActivity extends BaseActivity<ActivityDefaultBinding, DefaultViewModel>
        implements DefaultNavigator, BaseAdapter.Listener {

    public static final int TYPE_SUITABLE = 1;
    public static final int TYPE_UNSUITABLE = 2;
    public static final int TYPE_FORECAST_CALENDAR = 3;

    @Inject
    ViewModelProviderFactory factory;

    @Inject
    SuitableAdapter adapterSuitable;
    @Inject
    UnsuitableAdapter adapterUnsuitable;
    @Inject
    ForecastCalendarAdapter adapterForecastCalendar;

    private ActivityDefaultBinding binding;
    private DefaultViewModel viewModel;

    private String tag = "";
    private int type = 0;

    /**
     * @param binding activity class CalendarData binding
     */
    @Override
    public void getActivityBinding(ActivityDefaultBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_default;
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
     * Override for get the instance of viewModel
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public DefaultViewModel getViewModel() {
        type = Bundles.getDefaultActivityType(getIntent().getBundleExtra(KEY_DEFAULT_ACTIVITY_BUNDLE));
        tag = Bundles.getDefaultActivityTag(getIntent().getBundleExtra(KEY_DEFAULT_ACTIVITY_BUNDLE));
        return viewModel = ViewModelProviders.of(this,factory).get(DefaultViewModel.class);
    }

    /**
     * Do anything on onCreate after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);

        setUpTool();
        subscribeToLiveData();
        setUp();
        setUpAdapter();
    }


    private void subscribeToLiveData() {
        viewModel.getModelForecastCalendarLiveData().observe(this, data ->
                viewModel.setForecastCalendar(data));
    }

    private void setUpTool() {
        setSupportActionBar(binding.toolbarLayout.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setTitle();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpAdapter() {
        adapterSuitable.setListener(this);
        binding.suitableList.setAdapter(adapterSuitable);

        adapterUnsuitable.setListener(this);
        binding.unsuitableList.setAdapter(adapterUnsuitable);

        adapterForecastCalendar.setListener(this);
        binding.forecastActivityCalendar.calendarList.setAdapter(adapterForecastCalendar);
    }

    private void setUp() {
        switch (type) {
            case TYPE_SUITABLE:
                binding.toolbarLayout.title.setText(R.string.suitable_icons_legend);
                binding.suitableList.setVisibility(View.VISIBLE);
                binding.forecastActivityCalendar.layout.setVisibility(View.GONE);
                ArrayList<ActivityList> _data = Bundles.getDefaultActivityDataSitable(getIntent()
                        .getBundleExtra(KEY_DEFAULT_ACTIVITY_BUNDLE));
                if (_data != null) {
                    viewModel.setSutableData(_data);
                }
                break;

            case TYPE_UNSUITABLE:
                binding.toolbarLayout.title.setText(R.string.unsuitable_icons_legend);
                binding.unsuitableList.setVisibility(View.VISIBLE);
                binding.forecastActivityCalendar.layout.setVisibility(View.GONE);
                ArrayList<UnSuitableActivityList> _data2 = Bundles.getDefaultActivityDataUnSitable(getIntent()
                        .getBundleExtra(KEY_DEFAULT_ACTIVITY_BUNDLE));
                if (_data2 != null) {
                    viewModel.setUnsutableData(_data2);
                }
                break;

            case TYPE_FORECAST_CALENDAR:
                if (tag.equals(com.maritesallen.almanac2020.ui.dashboard.forecast.unsuitable.UnsuitableAdapter.TAG)){
                    binding.toolbarLayout.title.setText(R.string.list_unsutable_dates);
                }
                else{
                    binding.toolbarLayout.title.setText(R.string.list_sutable_dates);
                }
                binding.forecastActivityCalendar.layout.setVisibility(View.VISIBLE);
                binding.layoutActivity.setVisibility(View.GONE);
                String iconId = Bundles.getDefaultActivityDataForecastCalendarIconId(getIntent()
                                .getBundleExtra(KEY_DEFAULT_ACTIVITY_BUNDLE));
                String yearId = Bundles.getDefaultActivityDataForecastCalendarYearId(getIntent()
                                .getBundleExtra(KEY_DEFAULT_ACTIVITY_BUNDLE));
                String dataType = Bundles.getDefaultActivityDataForecastCalendarDataType(getIntent()
                            .getBundleExtra(KEY_DEFAULT_ACTIVITY_BUNDLE));
                viewModel.forecastCalendarDb(iconId, yearId, dataType);
/*
                ArrayList<Month> _data3 = Bundles.getDefaultActivityDataForecastCalendar(getIntent()
                        .getBundleExtra(KEY_DEFAULT_ACTIVITY_BUNDLE));
                if(_data3 != null) {
                    viewModel.setForecastCalendar(_data3);
                }
                String title = Bundles.getDefaultActivityDataForecastCalendarTitle(getIntent()
                        .getBundleExtra(KEY_DEFAULT_ACTIVITY_BUNDLE));
                if(title != null){
                    viewModel.setTitle(title);
                }
                */
                break;
        }
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.cancel();
    }
}
