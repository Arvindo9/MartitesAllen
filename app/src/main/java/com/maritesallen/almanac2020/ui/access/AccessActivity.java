package com.maritesallen.almanac2020.ui.access;

import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.maritesallen.almanac2020.BR;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseActivity;
import com.maritesallen.almanac2020.core.binding.BindingUtils;
import com.maritesallen.almanac2020.databinding.ActivityAccessBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.Logger;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

/**
 * Author       : Arvindo Mondal
 * Created on   : 11-11-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class AccessActivity extends BaseActivity<ActivityAccessBinding, AccessViewModel> implements
        HasAndroidInjector{

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    DispatchingAndroidInjector<Object> androidInjector;
    private AccessViewModel viewModel;
    private ActivityAccessBinding binding;

    /**
     * Returns an {@link AndroidInjector}.
     */
    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    /**
     * @param binding activity class Data binding
     */
    @Override
    public void getActivityBinding(ActivityAccessBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_access;
    }

    /**
     * Override for set binding variable
     *
     * @return BR.Data;
     */
    @Override
    public int getBindingVariable() {
        return BR.data;
    }

    /**
     * Override for get the instance of viewModel
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public AccessViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(AccessViewModel.class);
    }

    /**
     * Do anything on onCreate after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.cancel();
        setUp();
//        setUpLocation();
        setUpExtras();

//        setUpFacebook();

//        testCrash();
    }

    private void testCrash() {

        throw new RuntimeException("Test crash Arvindo");
    }

    private void setUpFacebook() {
//        callbackManager = CallbackManager.Factory.create();
    }

    private void setUpLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                    }
                });
    }

    private void setUp() {
        setSupportActionBar(binding.toolbarLayout.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        binding.toolbarLayout.toolbar.setVisibility(View.GONE);

        BindingUtils.setImageViewDrawable(binding.logoLayout.logo, R.drawable.logo);
    }

    private void setUpExtras(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            try {
                if(destination.getLabel().equals("AccessDefaultFragment")){
                    binding.toolbarLayout.toolbar.setVisibility(View.GONE);
                }
                else{
                    binding.toolbarLayout.toolbar.setVisibility(View.VISIBLE);
                }
                Logger.e("onDestinationChanged: "+destination.getLabel());
            }catch (NullPointerException ignore){}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.cancel();
    }
}
