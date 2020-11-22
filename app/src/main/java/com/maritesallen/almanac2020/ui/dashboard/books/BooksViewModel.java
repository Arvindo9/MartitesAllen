package com.maritesallen.almanac2020.ui.dashboard.books;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;
import com.maritesallen.almanac2020.utils.tasks.Task;

import java.net.ConnectException;
import java.util.List;

import javax.inject.Inject;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class BooksViewModel extends BaseViewModel<BooksNavigator> implements Task.TaskCallBack{
    public final ObservableList<Books> modelObservableList = new ObservableArrayList<>();
    public final ObservableList<BookPremium> modelPremiumBookObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<Books>> modelLiveData;
    private final MutableLiveData<List<BookPremium>> modelPremiumBookLiveData;
    private Task task;
    private Books bookModelCurrent;

/*
    public BooksViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        modelLiveData = new MutableLiveData<>();
        modelPremiumBookLiveData = new MutableLiveData<>();
    }
*/

    @Inject
    public BooksViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Task task) {
        super(dataManager, schedulerProvider);
        this.task = task;
        modelLiveData = new MutableLiveData<>();
        modelPremiumBookLiveData = new MutableLiveData<>();
    }

    //List view---------------------------------------

    ObservableList<BookPremium> getModelPremiumBookObservableList() {
        return modelPremiumBookObservableList;
    }

    List<Books> getBooksList() {
        return modelObservableList;
    }

    void addDataToList(List<Books> list) {
        modelObservableList.clear();
        modelObservableList.addAll(list);
    }

    void addPremiumBookDataToList(List<BookPremium> list) {
        modelPremiumBookObservableList.clear();
        modelPremiumBookObservableList.addAll(list);
    }

    MutableLiveData<List<Books>> getLiveData() {
        return modelLiveData;
    }

    MutableLiveData<List<BookPremium>> getPremiumBookLiveData() {
        return modelPremiumBookLiveData;
    }

    private void setLiveData(List<Books> list){
        modelLiveData.setValue(list);
    }

    public Books getBookModelCurrent() {
        return bookModelCurrent;
    }

    void setBookModelCurrent(Books bookModelCurrent) {
        this.bookModelCurrent = bookModelCurrent;
    }

    //Resources------------------------------------

    //Load--------------
    void loadBooksApi() {
        String year = getDataManager().getYearId();

        if(getDataManager().getLoggedInMode() != DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType()) {
            getPremiumBooks();
        }
        getCompositeDisposable().add(getDataManager()
                .books(year)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null && response.getData().getBooks() != null) {
                            modelLiveData.setValue(response.getData().getBooks());
                            saveBooksDb(response.getData().getBooks());
                            return;
                        }
                    }
                    loadBooksDb(false);
                }, throwable ->{
                    if(throwable instanceof ConnectException){
                        loadBooksDb(true);
                    }
                    throwable.printStackTrace();
                }));
    }

    private void loadBooksDb(boolean networkError) {
        if(getDataManager().getLoggedInMode() != DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType()) {
            getPremiumBooks();
        }
        getCompositeDisposable().add(getDataManager()
                .getBooks()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && !response.isEmpty()){
                        modelLiveData.setValue(response);
                    }
                    else if(networkError){
                        getNavigator().showMessage(R.string.network_error);
                    }
                },throwable -> {
                    if(networkError){
                        getNavigator().showMessage(R.string.network_error);
                    }
                }));
    }

    private void saveBooksDb(List<Books> list) {
        getCompositeDisposable().add(getDataManager()
                .saveBooks(list)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                }, Throwable::printStackTrace));
    }

    //
    //Load-------------- end

    void loadBooks(final int bookId) {
        String year = getDataManager().getYearId();

        if(getDataManager().getLoggedInMode() != DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType()) {
            getPremiumBooks();
        }
        getCompositeDisposable().add(getDataManager()
                .books(year)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null && response.getData().getBooks() != null) {
                            modelLiveData.setValue(response.getData().getBooks());

                            boolean ok = true;
                            for(int i=0; i < response.getData().getBooks().size() && ok; i++){
                                if(bookId == response.getData().getBooks().get(i).getId()){
                                    bookModelCurrent = response.getData().getBooks().get(i);
                                    downloadFile(response.getData().getBooks().get(i));
                                    ok = false;
                                }
                            }
                        }
                    }
                }, throwable ->{
                    throwable.printStackTrace();
                    if(throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.network_error);
                    }
                }));
    }

    private void downloadFile(Books model) {
//        getNavigator().downloadStatus(true);
        getNavigator().downloadFile(model);
/*
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .downloadFile(model.getFile())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        response.byteStream();
                        //you can now get your file in the InputStream
                        getNavigator().saveFileToSdCard(model, response.byteStream());
                    }
                    setIsLoading(false);
                }, throwable ->{
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
        */
    }

    private void getPremiumBooks() {
        getCompositeDisposable().add(getDataManager()
                .getBookPremium()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response != null && !response.isEmpty()) {
                        modelPremiumBookLiveData.setValue(response);
                    }
                }, Throwable::printStackTrace));
    }

    void clear1(){
        setIsLoading(false);
    }

    void setLoading(boolean status){
        setIsLoading(status);
    }

    @Override
    public void onResponse(String responseCode, Object object) {
        getNavigator().downloadStatus(false);
        if(responseCode.equals("downloadFile")){
            getNavigator().downloadFileStatus((boolean)object);
        }

    }

    @Override
    public void onError(Throwable throwable) {
        getNavigator().downloadFileStatus(false);
    }

    boolean isGuestLogin() {
        return getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType();
    }

    public String getPayload() {
        return getDataManager().getUserRegistrationId();
    }

    public String getDrmToken() {
        return getDataManager().getDrmToken();
    }
    public String getCompanyId() {
        return getDataManager().getCompanyIdDrm();
    }
    public String getApplication() {
        return getDataManager().getApplicationIdDrm();
    }
    public String getPrivateKey() {
        return getDataManager().getPrivateKeyDrm();
    }
    public String getPublicKey() {
        String ksdf=getDataManager().getPublicKeyDrm();
        return getDataManager().getPublicKeyDrm();
    }
    public String getProjectId() {
        return getDataManager().getProjectIdDrm();
    }
    public String getUserId() {
        return getDataManager().getUserIdDrm();
    }
    public String getProjectName() {
        return getDataManager().getProjectNameDrm();
    }

    //SetUp drm----------------------------------

    public String getAdsUnitId() {
        return getDataManager().getAdsUnitId();
    }
}
