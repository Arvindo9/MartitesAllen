package com.maritesallen.almanac2020.ui.splash;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.gms.common.util.JsonUtils;
import com.maritesallen.almanac2020.BR;
import com.maritesallen.almanac2020.BuildConfig;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseActivity;
import com.maritesallen.almanac2020.core.binding.BindingUtils;
import com.maritesallen.almanac2020.databinding.ActivitySplashBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.access.AccessActivity;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Author       : Arvindo Mondal
 * Created on   : 11-11-2019
 * Email        : arvindo@aiprog.in

 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 *
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel>
        implements SplashNavigator{

    @Inject
    ViewModelProviderFactory factory;
    private SplashViewModel viewModel;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ActivitySplashBinding binding;

    @Override
    public void getActivityBinding(ActivitySplashBinding binding) {
        this.binding = binding;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public int getBindingVariable() {
        return BR.data;
    }

    @Override
    public SplashViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(SplashViewModel.class);
    }

    @Override
    protected void init() {
        //android O fix bug orientation
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        viewModel.setNavigator(this);
        REQUEST_PERMISSION_FOR_ACTIVITY = false;
        setup();
        checkFacebook();

    }

    private void setup() {
        BindingUtils.setImageViewDrawable(binding.logo, R.drawable.logo);
    }

    private void checkFacebook() {
//        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //Navigator----------------------

    @Override
    public void openWelcomeScreen() {

    }

    @Override
    public void openLoginActivity() {
        startActivity(AccessActivity.class);
        finish();
    }

    @Override
    public void openDashboard() {
        startActivity(DashboardActivity.class);
        finish();
    }

    @Override
    public void openDashboardFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn) {
            startActivity(DashboardActivity.class);
            finish();
        }else{
            openLoginActivity();
        }
    }

    @Override
    public void openDashboardGuest() {
        startActivity(DashboardActivity.class);
        finish();
    }

    // make the application close if splash activity is closed in Android
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
/*

    @Override
    public void setAdsId(String id){
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            applicationInfo.metaData.putString(
                    "com.google.android.gms.ads.APPLICATION_ID",
                    id);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
*/

    @Override
    public void openPlayStore() {
        appUpdate();
    }

    private void appUpdate() {
        new BottomDialog.Builder(this)
                .setTitle(getResources().getString(R.string.update_available))
                .setContent("App version outdated, some features might not be available." + "\n\n" +
                        "Please upgrade to version" + " " + BuildConfig.VERSION_NAME + " " +
                        "for an enhanced experience.")
                .setNegativeText(getResources().getString(R.string.not_now))
                .setPositiveText(getResources().getString(R.string.update_app))
                .onPositive(bottomDialog -> {update(); viewModel.updateVersionDate();})
                .onNegative(bottomDialog -> {viewModel.updateVersionDate();
                        viewModel.decideNextScreen();})
                .show();
    }

    public void update() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.maritesallen.almanac2020&hl=en")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.maritesallen.almanac2020&hl=en")));
        }
    }
}
