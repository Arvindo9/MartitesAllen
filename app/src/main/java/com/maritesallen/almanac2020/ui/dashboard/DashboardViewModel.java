package com.maritesallen.almanac2020.ui.dashboard;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.maritesallen.almanac2020.BuildConfig;
import com.maritesallen.almanac2020.core.util.AESUtils;
import com.maritesallen.almanac2020.core.util.BookCallback;
import com.maritesallen.almanac2020.core.util.Constant;
import com.maritesallen.almanac2020.core.util.Download;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.tasks.Task;
import com.facebook.login.LoginManager;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.utils.billing.core.Purchase;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;
import com.maritesallen.almanac2020.utils.util.General;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import okhttp3.ResponseBody;

import static com.maritesallen.almanac2020.core.util.Constant.DIRECTORY_NOT_CREATED;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Author       : Arvindo Mondal
 * Created on   : 12-11-2019
 * Email        : arvindo@aiprog.in

 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 *
 */
public class DashboardViewModel extends BaseViewModel<DashboardNavigation> implements Task.TaskCallBack,
        Task.TaskCallBackDownload,
        BookCallback.BookPrrmium {

    private final Task task;
    private List<BookPremium> downloadModel;
    private int modelIndex = 0;
    private DownloadFileFromURL downloadFileTask;

    public DashboardViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Task task) {
        super(dataManager, schedulerProvider);
        this.task = task;
        checkAdsUnit();
    }

    int getLogginMode(){
        return getDataManager().getLoggedInMode();
    }

    //Resources--------------------------

    public void onForecastClick(View view){getNavigator().onForecastClick(view);}
    public void onCalenderClick(View view){
        if(getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType()) {
            getNavigator().onCalenderClick(view, false, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType());
        }
        else if((getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType()) &&
                !getDataManager().isPremium()) {
            getNavigator().onCalenderClick(view, false, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType());
        }
        else if(getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType()){
            getNavigator().onCalenderClick(view, true, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType());
        }
        else if((getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_FACEBOOK.getType()) &&
                !getDataManager().isPremium()) {
            getNavigator().onCalenderClick(view, false, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_FACEBOOK.getType());
        }
        else{
            getNavigator().onCalenderClick(view, true, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_FACEBOOK.getType());
        }
    }
    public void onBooksClick(View view){getNavigator().onBooksClick(view);}

    public void onProfileClick(View view){
        if(getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType()) {
            getNavigator().onProfileClick(view, false);
        }
        else{
            getNavigator().onProfileClick(view, true);
        }
    }

    private void clearPremiumBook(){
        getCompositeDisposable().add(getDataManager()
                .deleteBookPremium()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response){
                        //Delete successfully
                    }
                }, Throwable::printStackTrace));
    }

    public void cancel() {
        setIsLoading(false);
    }

    void loading(boolean status) {
        setIsLoading(status);
    }

    String getPayload() {
        return getDataManager().getUserRegistrationId();
    }

    String getSubscriptionProductId(){
        return getDataManager().getSubscribeProductId();
    }

    private void checkAdsUnit(){
        if(getDataManager().getAdsUnitId() == null || getDataManager().getAdsUnitId().isEmpty()) {
            getDataManager().setAdsUnitId(BuildConfig.ADS_BANNER_UNIT);
        }
    }

    boolean isPaymentSync(){
        return getDataManager().isPaymentSync();
    }

    void syncPrevoiusPayment(String subscriptionYear) {
        Purchase data = getDataManager().getPurchase();
        if(data != null) {
            if (data.getPurchaseType() == AppConstants.PAYMENT_TYPE_PREMIUM) {
                onSuccessfulPurchasePremium(data);
            } else {
                onSuccessfulPurchase(data, subscriptionYear);
            }
        }
        else{
            getDataManager().setPaymentSync(true);
        }
    }

    void checkForPaymentSync() {
        if(!getDataManager().isPaymentSync()){
            syncPrevoiusPayment(getDataManager().getYearId());
        }
    }

    // Premium
    void onSuccessfulPurchasePremium(Purchase purchase) {
        String year = getDataManager().getYearId();
        String token = "Bearer " + getDataManager().getToken();
//        String token = "Bearer " + "";

        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .premiumSuccessfulPurchase(token, year, purchase.getOrderId(),
                        purchase.getSku(), String.valueOf(purchase.getPurchaseTime()), purchase.getToken())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess()) {
                            if(response.getData() != null) {
                                getDataManager().setToken(response.getData().getToken());
                                getDataManager().setUserName(response.getData().getName());
                                getDataManager().setUserRegistrationId(response.getData().getRegistrationId());
                                getDataManager().setYearId(response.getData().getYearId());
                                getDataManager().setBirthDate(response.getData().getBirthDate());
                                getDataManager().setPremium(response.getData().getIsPremium() != 0);
                                getDataManager().setAnimalId(String.valueOf(response.getData().getAnimalId()));
                                getDataManager().setAnimal(String.valueOf(response.getData().getUserAnimalTitle()));
                                getDataManager().setAnimalLink(String.valueOf(response.getData().getUserAnimalImage()));
                                getDataManager().setProfilePic(response.getData().getProfilePic());
                                getDataManager().setLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);

                                if (response.getData().getBookPremium() != null && !response.getData().getBookPremium().isEmpty()) {
                                    savePremiumBooks(purchase.getSku(), response.getData().getBookPremium());
                                }
                                getNavigator().onPemiumPurchaseSuccess(true);

                                getDataManager().setPaymentSync(true);
                                setIsLoading(false);
                                return;
                            }
                        }
                        else{
                            getNavigator().unauthorosizePayment(false);
                            return;
                        }
                    }

                    getDataManager().setPaymentSync(false);
                    purchase.setPurchaseType(AppConstants.PAYMENT_TYPE_PREMIUM);
                    getDataManager().setPurchase(purchase);
                    setIsLoading(false);
                }, throwable ->{
                    getDataManager().setPaymentSync(false);
                    purchase.setPurchaseType(AppConstants.PAYMENT_TYPE_PREMIUM);
                    getDataManager().setPurchase(purchase);

                    getNavigator().onPemiumPurchaseSuccess(false);
                    throwable.printStackTrace();
                    setIsLoading(false);
                    if(throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.login_again);
                    }
                }));
    }

    // Books-----------------------

    void onSuccessfulPurchase(Purchase purchase, String purchaseYear) {
        String token = "Bearer " + getDataManager().getToken();
//        String token = "Bearer " + "";

        Logger.e("token:" + token);
        Logger.e("purchaseYear:" + purchaseYear);
        Logger.e("OrderId:" + purchase.getOrderId());
        Logger.e("productId:" + purchase.getSku());
        Logger.e("PurchaseTime:" + purchase.getPurchaseTime());
        Logger.e("getToken : " + purchase.getToken());

        getCompositeDisposable().add(getDataManager()
                .bookSuccessfulPurchase(token, purchaseYear, purchase.getOrderId(),
                        purchase.getSku(), String.valueOf(purchase.getPurchaseTime()), purchase.getToken())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess()) {
                            if (response.getData() != null && response.getData().getBookPremium() != null &&
                                    !response.getData().getBookPremium().isEmpty()) {
                                getNavigator().puchPurchaseLoader(true);
                                savePremiumBooks(purchase.getSku(), response.getData().getBookPremium());
                                getDataManager().setPaymentSync(true);
                                return;
                            }
                        }
                        else{
                            getNavigator().unauthorosizePayment(false);
                            return;
                        }
                    }

                    getDataManager().setPaymentSync(false);
                    purchase.setPurchaseType(AppConstants.PAYMENT_TYPE_BOOK);
                    getDataManager().setPurchase(purchase);
                }, throwable ->{
                    getDataManager().setPaymentSync(false);
                    purchase.setPurchaseType(AppConstants.PAYMENT_TYPE_BOOK);
                    getDataManager().setPurchase(purchase);

                    throwable.printStackTrace();
                    if(throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.login_again);
                    }
                    getNavigator().puchPurchaseLoader(false);
                }));
    }

    private void savePremiumBooks(String productId, List<BookPremium> bookPremium) {
        getCompositeDisposable().add(getDataManager()
                .saveBookPremium(bookPremium)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response){
                        //Saved succeful

                        int bookId = -1;
                        boolean ok = true;
                        for(int i=0; i < bookPremium.size() && ok; i++){
                            if(productId.equals(bookPremium.get(i).getProductId())){
                                bookId = bookPremium.get(i).getBookId();
                                ok = false;
                            }
                        }

                        getNavigator().puchPurchaseLoader(false);
                        getNavigator().refreshBooks(bookId);
                    }
                }, throwable -> {
                    getNavigator().puchPurchaseLoader(false);
                    throwable.printStackTrace();
                }));
    }

    private void savePremiumBooksRestore(List<BookPremium> bookPremium) {
        getCompositeDisposable().add(getDataManager()
                .saveBookPremium(bookPremium)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response){
                        //Saved succeful
                    }
                }, throwable -> {
                    getNavigator().puchPurchaseLoader(false);
                    throwable.printStackTrace();
                }));
    }

    String getBase64EncodedPublicKey() {
        return getDataManager().getBase64EncodedPublicKey();
    }

    boolean isGuestUser() {
        return getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType();
    }

    public void restore() {
        String token = "Bearer " + getDataManager().getToken();
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .restoreApp(token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getData() != null){
                        if(response.getData().getBookPremium() != null && !response.getData().getBookPremium().isEmpty()){
                            downloadBooks(response.getData().getBookPremium());
                            savePremiumBooksRestore(response.getData().getBookPremium());
                        }
                    }
                    else{
                        getNavigator().restoreSuccessful(false);
                    }
                    setIsLoading(false);
                }, throwable ->{
                    throwable.printStackTrace();
                    setIsLoading(false);
                    if(throwable instanceof ConnectException){
                        getNavigator().showMessage(R.string.network_error);
                    }
                }));
    }

    private void downloadBooks(List<BookPremium> model) {
//        getNavigator().showMessage(R.string.file_download_start);

        this.downloadModel = model;
        this.modelIndex = 0;

        if(model.size() > modelIndex) {
            BookPremium data = model.get(modelIndex++);
            Download download =
                    Download.getInstance(getApplicationContext());
            download.setCallback(this);
            download.downloadBook(data);
        }
        else{
            getNavigator().restoreSuccessful(true);
        }
    }

    @Override
    public void onResponse(@NotNull String responseCode, Object object) {
        Logger.e("DashboardViewModel onResponse:" + responseCode);
        getNavigator().downloadStatus(false);
        if(responseCode.equals("downloadFile")){
            if(object instanceof String) {
                Logger.e("DashboardViewModel onResponse:" + responseCode + ", " + object);
//                getNavigator().showMessage(object + " file successfully downloaded");
                getNavigator().showMessage(R.string.restore_file_download_message);

                if(downloadModel != null) {
                    if (downloadModel.size() > modelIndex) {
                        BookPremium data = downloadModel.get(modelIndex++);
                        Download download =
                                Download.getInstance(getApplicationContext());
                        download.setCallback(this);
                        download.downloadBook(data);
                    } else {
                        getNavigator().restoreSuccessful(true);
                    }
                }
            }
            else if(object instanceof Boolean) {
                getNavigator().downloadFileStatus((boolean)object);
            }
        }
    }

    @Override
    public void onResponse(String responseCode, Object object, Books model) {
        Logger.e("DashboardViewModel Noew onResponse:" + responseCode);
        getNavigator().downloadStatus(false);
        if(responseCode.equals("downloadFileNow")){
            if(object instanceof ResponseBody){
//                downloadFileTask = new DownloadFileTask(model);
//                downloadFileTask.execute((ResponseBody) object);
            }
            else if(object instanceof Boolean) {
                getNavigator().downloadFileStatus((boolean)object);
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        getNavigator().downloadFileStatus(false);
    }

    //Download class-------------

    @Override
    public void onResponse(int statusCode, BookPremium model) {
        if(statusCode == Constant.FILE_EXIST){
//            getNavigator().showMessage(model.getFileName() + " already downloaded");

            if(downloadModel.size() > modelIndex) {
                BookPremium data = downloadModel.get(modelIndex++);
                Download download = Download.getInstance(getApplicationContext());
                download.setCallback(this);
                download.downloadBook(data);
            }
            else{
                getNavigator().restoreSuccessful(true);
            }
        }
        else if(statusCode == Constant.FILE_NOT_EXIST){
            Download.getInstance(getApplicationContext());
            task.downloadFile(this, model);
        }
    }

    @Override
    public void onError(int statusCode, Throwable throwable) {
        if(statusCode == DIRECTORY_NOT_CREATED){
            getNavigator().showMessage(R.string.error_default);
        }
    }

    public void downloadFile(Books model) {
        Download.getInstance(getApplicationContext());
//        task.downloadFile(this, model);


        downloadFileTask = new DownloadFileFromURL(model);
        downloadFileTask.execute();
    }

    public boolean isPremium() {
        return getDataManager().isPremium();
    }

    //Downloading----------------

    ///----------------------------------

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, Boolean> {

        private final Books model;

        public DownloadFileFromURL(Books model){
            this.model = model;
        }

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected Boolean doInBackground(String... f_url) {
            int count;
            try {
                String fileName;
                try {
                    fileName = AESUtils.encrypt(model.getFileName());
                    model.setFileNameEncrypt(fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    fileName = model.getFileName();
                    model.setFileNameEncrypt(model.getFileName());
                }

                File directory = Download.getInstance().getDirectory();

                File destinationFile = new File(directory + "/" + fileName);


                URL url = new URL(model.getFile());
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                OutputStream output = new FileOutputStream(destinationFile);

                byte[] data = new byte[1024];

                long total = 0;
                int progress = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return true;
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                e.printStackTrace();
            }

            return false;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
//            pDialog.setProgress(Integer.parseInt(progress[0]));
//            int currentProgress = (int) ((double) progress[0].first / (double) progress[0].second * 100);
            getNavigator().downloadProgress(Integer.parseInt(progress[0] + ""));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Boolean fileStatus) {
            getNavigator().downloadStatus(false);
            getNavigator().downloadFileStatus(fileStatus);
//            onResponse("downloadFile", fileStatus);
        }

    }

    // update--------------

    void getVersion() {
        getCompositeDisposable().add(getDataManager()
                .getVersion()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getAndroidAppVersion()!=null){
                        if(!response.getAndroidAppVersion().isEmpty()) {
                            if (getDataManager().getVersionDate().isEmpty() ||
                                    !getDataManager().getVersionDate().equals(General.getDateSend())) {
                                try {
                                    double serverVersion = Double.parseDouble(response.getAndroidAppVersion());
                                    double appVersion = Double.parseDouble(BuildConfig.VERSION_NAME);
                                    if (appVersion < serverVersion) {
                                        getNavigator().openPlayStore();
                                    }
                                }
                                catch(Exception e){
                                    if (!response.getAndroidAppVersion().equals(BuildConfig.VERSION_NAME)) {
                                        getNavigator().openPlayStore();
                                    }
                                }
                            }
                        }
                    }
                }, Throwable::printStackTrace));
    }

    void updateVersionDate(){
        getDataManager().setVersionDate(General.getDateSend());
    }


    void pushFirebaseToken(String fbToken) {
        if(!getDataManager().getFbToken().equals(fbToken)) {
            String token = "Bearer " + getDataManager().getToken();
            getCompositeDisposable().add(getDataManager()
                    .pushFcmToken(token, fbToken)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && response.getSuccess() != null && response.getSuccess()) {
                            getDataManager().setFbToken(fbToken);
                        }
                    }, Throwable::printStackTrace));
        }
    }

    //verify book purchase
    void verifyPremium() {
        String token = "Bearer " + getDataManager().getToken();
//        setIsLoading(true);
        //                        updateVersionDate();


        //Do logout
        //Do Block user


        getCompositeDisposable().add(getDataManager()
                .restoreApp(token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getSuccess() != null) {
                        if(response.getSuccess() && response.getData() != null && response.getData().getStatusCode() !=null){
                            if(String.valueOf(response.getData().getStatusCode()).equals(AppConstants.LOGOUT_CODE)){
                                blockUser(AppConstants.LOGOUT_CODE);
//                                doLogout();
                            }
                            else if(String.valueOf(response.getData().getStatusCode()).equals(AppConstants.BLOCK_CODE)){
                                blockUser(AppConstants.BLOCK_CODE);
                            }
                            else{
                                if(response.getData().getBookPremium() != null && !response.getData().getBookPremium().isEmpty()){
                                    savePremiumBooksRestore(response.getData().getBookPremium());
                                }
                            }
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                }));
    }

    void blockUser(String statusCode) {
        if(statusCode.equals(AppConstants.BLOCK_CODE)){
            logout();
            getNavigator().blockUser(statusCode);
        }
        else if(statusCode.equals(AppConstants.LOGOUT_CODE)){
            logout();
            getNavigator().doLogout(true, "");
        }
    }


    void doLogout() {
        if(getDataManager().getLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN_GUEST.getType()){
            setLogout("");
        }
        else{
            setIsLoading(true);

            String token = "Bearer " + getDataManager().getToken();
            getCompositeDisposable().add(getDataManager()
                    .logout(token)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && response.getMessage()!= null){
                            if(response.getSuccess()) {
                                setLogout(response.getMessage());
                                return;
                            }
                        }

                        setIsLoading(false);
                        setLogout("");
                    }, throwable ->{

                        setIsLoading(false);
                        setLogout("");
                        throwable.printStackTrace();
                    }));
        }

        getCompositeDisposable().add(getDataManager()
                .clearAllDb()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response){
                        Logger.e("Database clear");
                    }
                    else{
                        Logger.e("Database not clear");
                    }
                }, Throwable::printStackTrace));
    }

    private void setLogout(String message) {
        clearPremiumBook();
        getDataManager().setLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT);
        getDataManager().setAnimalLink("");
        getDataManager().setToken("");
        getDataManager().setAnimal("");
        getDataManager().setBirthDate("");
        getDataManager().setCountryCode("");
        getDataManager().setEmail("");
        getDataManager().setProfilePic("");
        getDataManager().setPremium(false);

        LoginManager.getInstance().logOut();
        getNavigator().doLogout(true, message);

    }

    private void logout() {
        clearPremiumBook();
        getDataManager().setLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT);
        getDataManager().setAnimalLink("");
        getDataManager().setToken("");
        getDataManager().setAnimal("");
        getDataManager().setBirthDate("");
        getDataManager().setCountryCode("");
        getDataManager().setEmail("");
        getDataManager().setProfilePic("");
        getDataManager().setPremium(false);


        LoginManager.getInstance().logOut();
        getCompositeDisposable().add(getDataManager()
                .clearAllDb()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response){
                        Logger.e("Database clear");
                    }
                    else{
                        Logger.e("Database not clear");
                    }
                }, Throwable::printStackTrace));
    }
}
