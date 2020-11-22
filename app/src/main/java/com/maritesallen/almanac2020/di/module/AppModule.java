package com.maritesallen.almanac2020.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.maritesallen.almanac2020.data.AppDataManager;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.local.db.AppDatabase;
import com.maritesallen.almanac2020.data.local.db.Database;
import com.maritesallen.almanac2020.data.local.db.DatabaseService;
import com.maritesallen.almanac2020.data.local.prefs.AppPreferences;
import com.maritesallen.almanac2020.data.local.prefs.PreferencesService;
import com.maritesallen.almanac2020.data.remote.APIService;
import com.maritesallen.almanac2020.data.remote.APIs;
import com.maritesallen.almanac2020.di.annotation.ApiService;
import com.maritesallen.almanac2020.di.annotation.DatabaseInfo;
import com.maritesallen.almanac2020.di.annotation.PassPhraseField;
import com.maritesallen.almanac2020.di.annotation.PreferenceInfo;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.rx.AppSchedulerProvider;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;
import com.maritesallen.almanac2020.utils.tasks.Task;
import com.maritesallen.almanac2020.utils.tasks.Tasks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.maritesallen.almanac2020.BuildConfig.BASE_URL;

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
@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    //Preferences------------------------------------------------

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesService providePreferencesService(AppPreferences appPreferences) {
        return appPreferences;
    }
    //Database----------------------------------------------------

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @PassPhraseField
    String providePassPhraseField() {
        return AppConstants.PASS_PHRASE_FIELD;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context, @PassPhraseField String passPhraseField) {
//        SafeHelperFactory factory = SafeHelperFactory.fromUser(passphraseField.getText());
//        SafeHelperFactory factory = SafeHelperFactory.fromUser(new SpannableStringBuilder(passPhraseField),
//                SafeHelperFactory.POST_KEY_SQL_V3);

        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
//                .openHelperFactory(factory)
                .build();
    }

    @Provides
    @Singleton
    DatabaseService provideDbHelper(Database database) {
        return database;
    }

    //Network module----------------------------------------------------

    @Provides
    @Singleton
    APIService provideAPIService(APIs APIs) {
        return APIs;
    }

    @Provides
    @Singleton
    @ApiService
    APIs provideAPIHeader(@Named("APP") Retrofit retrofit, @Named("RetrofitUtil") Retrofit retrofitUtil) {
        return new APIs(retrofit ,retrofitUtil);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
//        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), 10485760);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
//    OkHttpClient provideOkhttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA)
                .build();
        return new OkHttpClient
                .Builder()
                .cache(cache)
                .addInterceptor(interceptor)
//                .addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
//                .connectionSpecs(Collections.singletonList(ConnectionSpec.COMPATIBLE_TLS))
                .build();
    }

    @Provides
    @Singleton
    @Named("APP")
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @Named("RetrofitUtil")
    Retrofit provideRetrofitUtil(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    //Additional task----------------------------------------------

    @Provides
    @Singleton
    Task provideTaskViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new Tasks(dataManager, schedulerProvider);
    }

    //------------------------end-----------------------------------
}
