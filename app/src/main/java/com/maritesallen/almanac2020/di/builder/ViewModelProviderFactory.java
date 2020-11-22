package com.maritesallen.almanac2020.di.builder;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.maritesallen.almanac2020.core.dialogs.book.BookPurchaseViewModel;
import com.maritesallen.almanac2020.core.dialogs.calender.CalenderDialogViewModel;
import com.maritesallen.almanac2020.core.dialogs.country.CountryViewModel;
import com.maritesallen.almanac2020.core.dialogs.dutyOfficer.DutyOfficerViewModel;
import com.maritesallen.almanac2020.core.dialogs.guest.GuestViewModel;
import com.maritesallen.almanac2020.core.dialogs.premiumAlert.PremiumAlertViewModel;
import com.maritesallen.almanac2020.core.dialogs.sessionExpire.SessionExpireViewModel;
import com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionViewModel;
import com.maritesallen.almanac2020.core.dialogs.upgrade.UpgradeViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.ui.access.AccessViewModel;
import com.maritesallen.almanac2020.ui.access.accessDefault.AccessDefaultViewModel;
import com.maritesallen.almanac2020.ui.access.forgetPassword.ForgetPasswordViewModel;
import com.maritesallen.almanac2020.ui.access.forgetPasswordUpdate.ForgetPasswordUpdateViewModel;
import com.maritesallen.almanac2020.ui.access.login.LoginViewModel;
import com.maritesallen.almanac2020.ui.access.registration.RegistrationViewModel;
import com.maritesallen.almanac2020.ui.access.registrationFacebook.RegistrationFacebookViewModel;
import com.maritesallen.almanac2020.ui.dashboard.DashboardViewModel;
import com.maritesallen.almanac2020.ui.dashboard.books.BooksViewModel;
import com.maritesallen.almanac2020.ui.dashboard.calender.CalenderViewModel;
import com.maritesallen.almanac2020.ui.dashboard.forecast.ForecastViewModel;
import com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager.ForecastAdapterViewModel;
import com.maritesallen.almanac2020.ui.dashboard.profile.ProfileViewModel;
import com.maritesallen.almanac2020.ui.defaultActivity.DefaultViewModel;
import com.maritesallen.almanac2020.ui.pdfViewer.PdfViewerViewModel;
import com.maritesallen.almanac2020.ui.splash.SplashViewModel;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;
import com.maritesallen.almanac2020.utils.tasks.Task;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Author       : Arvindo Mondal
 * Created on   : 10-05-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory  {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;
    private final Task task;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager,
                                    SchedulerProvider schedulerProvider, Task task) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.task = task;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(AccessViewModel.class)) {
            //noinspection unchecked
            return (T) new AccessViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(AccessDefaultViewModel.class)) {
            //noinspection unchecked
            return (T) new AccessDefaultViewModel(dataManager,schedulerProvider, task);
        }
        else if (modelClass.isAssignableFrom(ForgetPasswordViewModel.class)) {
            //noinspection unchecked
            return (T) new ForgetPasswordViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginViewModel(dataManager,schedulerProvider,task);
        }
        else if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            //noinspection unchecked
            return (T) new RegistrationViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(RegistrationFacebookViewModel.class)) {
            //noinspection unchecked
            return (T) new RegistrationFacebookViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(DashboardViewModel.class)) {
            //noinspection unchecked
            return (T) new DashboardViewModel(dataManager,schedulerProvider, task);
        }
        else if (modelClass.isAssignableFrom(ForecastViewModel.class)) {
            //noinspection unchecked
            return (T) new ForecastViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(CalenderViewModel.class)) {
            //noinspection unchecked
            return (T) new CalenderViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            //noinspection unchecked
            return (T) new ProfileViewModel(dataManager,schedulerProvider, task);
        }
        else if (modelClass.isAssignableFrom(TermsConditionViewModel.class)) {
            //noinspection unchecked
            return (T) new TermsConditionViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(CalenderDialogViewModel.class)) {
            //noinspection unchecked
            return (T) new CalenderDialogViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(UpgradeViewModel.class)) {
            //noinspection unchecked
            return (T) new UpgradeViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(SessionExpireViewModel.class)) {
            //noinspection unchecked
            return (T) new SessionExpireViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(PremiumAlertViewModel.class)) {
            //noinspection unchecked
            return (T) new PremiumAlertViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(CountryViewModel.class)) {
            //noinspection unchecked
            return (T) new CountryViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(ForecastAdapterViewModel.class)) {
            //noinspection unchecked
            return (T) new ForecastAdapterViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(DefaultViewModel.class)) {
            //noinspection unchecked
            return (T) new DefaultViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(DutyOfficerViewModel.class)) {
            //noinspection unchecked
            return (T) new DutyOfficerViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(GuestViewModel.class)) {
            //noinspection unchecked
            return (T) new GuestViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(BookPurchaseViewModel.class)) {
            //noinspection unchecked
            return (T) new BookPurchaseViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(ForgetPasswordUpdateViewModel.class)) {
            //noinspection unchecked
            return (T) new ForgetPasswordUpdateViewModel(dataManager,schedulerProvider);
        }
        else if (modelClass.isAssignableFrom(BooksViewModel.class)) {
            //noinspection unchecked
            return (T) new BooksViewModel(dataManager,schedulerProvider, task);
        }
        else if (modelClass.isAssignableFrom(PdfViewerViewModel.class)) {
            //noinspection unchecked
            return (T) new PdfViewerViewModel(dataManager,schedulerProvider);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
