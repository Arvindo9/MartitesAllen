package com.maritesallen.almanac2020.utils.tasks;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.core.util.Download;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.apis.calender.calendar.CalendarModel;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.data.model.db.terms.Terms;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-05-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
@Singleton
public class Tasks extends BaseViewModel<Task> implements Task{

    @Inject
    public Tasks(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    @Override
    public void downloadFile(TaskCallBackDownload callBack, Books model) {
        Call<ResponseBody> call = getDataManager().downloadFileRetrofit(model.getFile());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //you can now get your file in the InputStream
                    callBack.onResponse("downloadFileNow", response.body(), model);

                    Logger.e("Tasks downloadFile:" + response);
                }
                else {
                    callBack.onResponse("downloadFileNow", false, model);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable throwable) {
                callBack.onError(throwable);
                Logger.e("Tasks downloadFile:" + "false");
                throwable.printStackTrace();
            }
        });
/*
        getCompositeDisposable().add(getDataManager()
                .downloadFile(model.getFile())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        //you can now get your file in the InputStream
*//*
                        Download download = Download.getInstance();
                        boolean isFile = download.saveFileToSdCard(model, response.byteStream());

                        callBack.onResponse("downloadFile", isFile);
                        *//*
                        callBack.onResponse("downloadFileNow", response, model);

                        Logger.e("Tasks downloadFile:" + response);
                    }
                    else {
                        callBack.onResponse("downloadFileNow", false, model);
                    }
                }, throwable ->{
                    callBack.onError(throwable);
                    Logger.e("Tasks downloadFile:" + "false");
                    throwable.printStackTrace();
                }));
        */
    }

    @Override
    public void downloadFile(TaskCallBack callBack, BookPremium model) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .downloadFile(model.getFile())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        //you can now get your file in the InputStream
                        Download download = Download.getInstance();
                        boolean isFile = download.saveFileToSdCard(model, response.byteStream());

                        callBack.onResponse("downloadFile", model.getFileName());

                        Logger.e("Tasks downloadFile:" + isFile);

//                        callBack.onResponse("downloadFile", model, response.byteStream());
                    }
                    else {
                        callBack.onResponse("downloadFile", null);
                    }
                    setIsLoading(false);
                }, throwable ->{
                    callBack.onError(throwable);
                    Logger.e("Tasks downloadFile:" + "false");
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }

    //Callable methods---------------------

    @Override
    public void downloadCalendar() {
        getCalendarApi();
    }

    //Save calendar data

    private void saveCalendar(List<CalendarModel> list){
        getCompositeDisposable().add(getDataManager()
                .saveCalendar(list)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> Logger.e("Calendar save successful"), Throwable::printStackTrace));
    }

    private void getCalendarApi() {
        String year = getDataManager().getYearId();

        getCompositeDisposable().add(getDataManager()
                .calendar(year)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getCalendarData() != null) {
                            ArrayList<CalendarModel> list = new ArrayList<>();

                            for (int i = 0; response.getCalendarData().getMonths() != null &&
                                    i < response.getCalendarData().getMonths().size(); i++) {
                                CalendarModel month = response.getCalendarData().getMonths().get(i);
                                month.setMonthCalendar(true);
                                list.add(month);

                                if (response.getCalendarData().getMonths().get(i).getMonthId() == 1) {
                                    list.addAll(response.getCalendarData().getJanuary());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 2) {
                                    list.addAll(response.getCalendarData().getFebruary());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 3) {
                                    list.addAll(response.getCalendarData().getMarch());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 4) {
                                    list.addAll(response.getCalendarData().getApril());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 5) {
                                    list.addAll(response.getCalendarData().getMay());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 6) {
                                    list.addAll(response.getCalendarData().getJune());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 7) {
                                    list.addAll(response.getCalendarData().getJuly());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 8) {
                                    list.addAll(response.getCalendarData().getAugust());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 9) {
                                    list.addAll(response.getCalendarData().getSeptember());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 10) {
                                    list.addAll(response.getCalendarData().getOctober());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 11) {
                                    list.addAll(response.getCalendarData().getNovember());
                                } else if (response.getCalendarData().getMonths().get(i).getMonthId() == 12) {
                                    list.addAll(response.getCalendarData().getDecember());
                                }
                            }

                            saveCalendar(list);
                        }
                    }

                }, Throwable::printStackTrace));
    }

    //Terms--------------------


    @Override
    public void loadTerms(){
        getCompositeDisposable().add(getDataManager()
                .termsAndPrivacy()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null) {
                            saveTerms(response.getData().getTerms());
                        }
                    }
                }, Throwable::printStackTrace));
    }

    private void saveTerms(List<Terms> terms) {
        getCompositeDisposable().add(getDataManager()
                .saveTerms(terms)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    //Saved succeful
                }, Throwable::printStackTrace));
    }


}
