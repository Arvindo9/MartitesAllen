package com.maritesallen.almanac2020.di.component;


import android.app.Application;

import com.maritesallen.almanac2020.base.BaseApplication;
import com.maritesallen.almanac2020.di.builder.ActivityBuilder;
import com.maritesallen.almanac2020.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

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
@Singleton
@Component(
        modules = {
            AndroidInjectionModule.class,
            AppModule.class,
            ActivityBuilder.class
    }
)
public interface AppComponent {

    void inject(BaseApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
