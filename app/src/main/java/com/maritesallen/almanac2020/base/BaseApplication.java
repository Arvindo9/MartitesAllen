package com.maritesallen.almanac2020.base;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.di.component.DaggerAppComponent;
import com.maritesallen.almanac2020.utils.Logger;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

import static com.maritesallen.almanac2020.utils.AppConstants.FIREBASE_NOTIFICATION_TOPIC;
//import static com.maritesallen.almanac2020.utils.AppConstants.FIREBASE_NOTIFICATION_TOPIC;


/**
 * Author       : Arvindo Mondal
 * Created on   : 09-05-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class BaseApplication extends Application  implements HasAndroidInjector{

    private static final String TAG = BaseApplication.class.getSimpleName();

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    /**
     * Returns an {@link AndroidInjector}.
     */
    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    public void onCreate() {
/*
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
//                    .penaltyDeath()
//                    .detectAll()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
//                    .penaltyDeath()
//                    .detectAll()
                    .build());
        }
*/

        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        Logger.init();

//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        setUpStetho();

        setUpPicasso();

        setUpFacebook();

        subscribToTopic();

//        setUpAds();

//        setUpFileProvider();

//        subscribToTopic();
    }

    private void setUpAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });
    }

    private void setUpFileProvider() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void setUpFacebook() {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        setUpMultiDex();
    }

    private void setUpMultiDex() {
        MultiDex.install(this);
    }

    private void setUpPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);
    }

    private void setUpStetho(){
        Stetho.initializeWithDefaults(this);
    }

    private void subscribToTopic(){
        Log.d(TAG, "Subscribing to weather topic");
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic(FIREBASE_NOTIFICATION_TOPIC)
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d(TAG, msg);
                });
        // [END subscribe_topics]
    }

    static {
//        System.loadLibrary("algorithm-lib");
    }
}
