package com.maritesallen.almanac2020.ui.dashboard.books;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.maritesallen.almanac2020.BuildConfig;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.book.BookPurchaseDialog;
import com.maritesallen.almanac2020.core.dialogs.guest.GuestDialog;
import com.maritesallen.almanac2020.core.util.BookCallback;
import com.maritesallen.almanac2020.core.util.Constant;
import com.maritesallen.almanac2020.core.util.Download;
import com.maritesallen.almanac2020.core.util.ReadBook;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.databinding.FragmentBooksBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.ui.dashboard.books.adapter.BooksAdapter;
import com.maritesallen.almanac2020.ui.pdfViewer.PdfViewerActivity;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.bundles.Bundles;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.maritesallen.almanac2020.core.util.Constant.DIRECTORY_NOT_CREATED;
import static com.maritesallen.almanac2020.core.util.ReadBook.PERMISSIONS_REQUESTCODE;
import static com.maritesallen.almanac2020.core.util.ReadBook.SD_PERMISSIONS;
import static com.maritesallen.almanac2020.core.util.ReadBook.hasPermissions;
import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_FULL_MARGIN;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class BooksFragment extends BaseFragment<FragmentBooksBinding, BooksViewModel>
        implements BooksNavigator, BooksAdapter.Listener, DialogListener, BookCallback {
    public static final String TAG = BooksFragment.class.getSimpleName();

    @Inject
    BooksAdapter adapter;
    @Inject
    ViewModelProviderFactory factory;
    private FragmentBooksBinding binding;
    private BooksViewModel viewModel;
    private ProgressDialog dialog;
    private boolean onReadBookClick = true;
    private Books modelBook;

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentBooksBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_books;
    }

    /**
     * Override for set view model
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public BooksViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(getBaseActivity(),factory).get(BooksViewModel.class);
    }

    /**
     * Override for set binding variable
     *
     * @return BR.Data;
     */
    @Override
    public int getBindingVariable() {
        return com.maritesallen.almanac2020.BR.data;
    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        subscribeToLiveData();
        setUp();
        setUpAdapter();
        setUpAds();

        viewModel.loadBooksApi();
    }

    private void setUpAdapter() {
        adapter.setListener(this);
        binding.listView.setAdapter(adapter);
    }

    private void setUpAds() {
//        if(viewModel.isPremium()) {
//            binding.layoutAds.layoutAds.setVisibility(View.GONE);
//        }
//        else{
        AdView adView = new AdView(getBaseActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(viewModel.getAdsUnitId());
//        adView.setAdUnitId(AppConstants.ADS_UNIT_ID);

        MobileAds.initialize(getBaseActivity(), initializationStatus -> {

        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        binding.layoutAds.layoutAds.addView(adView, params);

//            binding.layoutAds.adView.loadAd(adRequest);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        binding.shimmerViewContainer.stopShimmer();
        super.onPause();
    }

    private void setUp() {
        ((DashboardActivity)getBaseActivity()).setBooks();
        ((DashboardActivity)getBaseActivity()).resetBilling();
    }

    private void subscribeToLiveData() {
        viewModel.getLiveData().observe(this, data -> {
                    viewModel.addDataToList(data);
                    loader();
                }
        );

        viewModel.getPremiumBookLiveData().observe(this, data->{
                    if(data != null && !data.isEmpty()) {
                        viewModel.addPremiumBookDataToList(data);
                        setPremiumBook(data);
                    }
                });
    }

    private void setPremiumBook(List<BookPremium> premiumBookModel){
        adapter.setPremiumBook(premiumBookModel);
    }

    private void loader(){
            new Handler().postDelayed(() -> {
                binding.shimmerViewContainer.stopShimmer();
                binding.shimmerViewContainer.setVisibility(View.GONE);

                binding.listView.setVisibility(View.VISIBLE);
            }, 600);
    }

    //Navigator------------------------

    @Override
    public void onRetryClick() {
        ((DashboardActivity)getBaseActivity()).booksFragmentReload();
    }

    @Override
    public void onBookClick(Books data, int position) {
        if(!viewModel.isGuestLogin()) {
            if(onReadBookClick) {
                onReadBookClick = false;
                if (data.getStatus() == 1) {
                    readBookDialog(true);
                    boolean isPurchase = false;
                    Logger.e("Premium book size:" + viewModel.getModelPremiumBookObservableList().size());
                    for (int i = 0; i < viewModel.getModelPremiumBookObservableList().size() && !isPurchase; i++) {
                        if (data.getId().equals(viewModel.getModelPremiumBookObservableList().get(i).getBookId())) {
                            isPurchase = true;
                        }
                    }

                    if (isPurchase) {
                        //TODO on success payment read the book
                        openFile(data);
                    } else if (getBaseActivity() != null) {
                        readBookDialog(false);
                        onReadBookClick = false;
//                        ((DashboardActivity) getBaseActivity()).getBilling().setUp(data.getProductId());
                        ((DashboardActivity) getBaseActivity()).setBilling(data.getProductId());
                        BookPurchaseDialog dialog = new BookPurchaseDialog(DIALOG_SIZE_FULL_MARGIN);
                        dialog.setListener(this);
                        getBaseActivity().startDialog(dialog, BookPurchaseDialog.TAG, Bundles.setBook(data, position));
                    } else {
                        readBookDialog(false);
                    }
                } else {
                    getBaseActivity().showToast(R.string.comming_soon);
                }
            }
        }
        else {
            getBaseActivity().startDialog(new GuestDialog(this), GuestDialog.TAG);
        }
    }

    /**
     * Default response method of a dialog
     *
     * @param tag    class name from which the response is getting
     * @param params string array with relative Data
     */
    @Override
    public void onSuccessDialogResponse(String tag, String... params) {
        viewModel.clear1();
        if(tag.equals(BookPurchaseDialog.TAG)){
            if (params != null && params.length > 0){
                int id = Integer.parseInt(params[0]);
                int position = Integer.parseInt(params[1]);

                if(viewModel.getBooksList().get(position).getId() == id){
                    //TODO on purchase book click, on buy button click

                    //TODO open payment method

                    doPayment(id, viewModel.getBooksList().get(position));
                }
            }

            onReadBookClick = true;
        }
        else if(tag.equals(GuestDialog.TAG)){
            if(params.length > 0 && params[0].equals(AppConstants.YES)){
                ((DashboardActivity)getBaseActivity()).logout();
            }
        }
    }

    //Book Callbacks Download-----------------------------

    @Override
    public void onFileDecryptionSuccess(File file) {
        Logger.e("onFileDecryptionSuccess " + ", name:" + file.getName());
        Logger.e("size " + file.length());
        Logger.e("isFile " + file.exists() + "");
/*
        Uri uri = FileProvider.getUriForFile(getBaseActivity(), BuildConfig.APPLICATION_ID + ".provider" , file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
*/
        viewModel.clear1();
        if(getBaseActivity()!=null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(getBaseActivity(), BuildConfig.APPLICATION_ID + ".provider", file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
    }

    @Override
    public void onBookDecrypt(byte[] bytes, int requestCode) {
        if(bytes != null && bytes.length > 0){
//            ReadBook.getInstance().readDecryptFile(getBaseActivity(), bytes);


            readBookDialog(false);
            AppConstants.pdfByte = bytes;

            startActivity(PdfViewerActivity.getInstance(getBaseActivity(), this::onErrorOnDowunload));


//            getBaseActivity().startActivity(PdfViewerActivity.class);
        }
    }

    public void onErrorOnDowunload() {
        downloadFile(modelBook);
    }


    /*
        This method is responsible for reading the book
     */
    @Override
    public void onResponse(int statusCode, Books model) {
        if(statusCode == Constant.FILE_EXIST){
            //TODO open do encryption and read

            ReadBook readBook = ReadBook.getInstance();
            Logger.e("readFile");

            //TODO setUp Read book drm keys
            readBook.setUp(viewModel.getDrmToken(), viewModel.getCompanyId(), viewModel.getApplication(),
                    viewModel.getPrivateKey(), viewModel.getPublicKey(), viewModel.getProjectId(), viewModel.getUserId(),
                    viewModel.getProjectName());
            Logger.e("DrmId:" + model.getDrmId());
            readBook.setUp(model.getDrmId());
            readBook.setCallback(this);
            readBook.readFile(getBaseActivity(), model);
            this.modelBook = model;
        }
        else if(statusCode == Constant.FILE_NOT_EXIST){
            readBookDialog(false);
//            viewModel.downloadFile(model);
            downloadFile(model);
        }
    }

    @Override
    public void onError(int statusCode, Throwable throwable) {
        readBookDialog(false);
        if(statusCode == DIRECTORY_NOT_CREATED){
            if(getBaseActivity() != null){
                getBaseActivity().showToast(R.string.error_default);
            }
        }
    }

    //Book Callbacks End-----------------------------X

    private void openFile(Books model){
        Download download = Download.getInstance(getApplicationContext());
        download.setCallback(BooksFragment.this);
        download.downloadBook(model);
    }

    //Activity-------------------------

    //Permission-----------------------

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUESTCODE:
                boolean isGotPermission = hasPermissions(getApplicationContext(), getBaseActivity(),
                        PERMISSIONS_REQUESTCODE, SD_PERMISSIONS);
                if (isGotPermission) {

                }
                break;
        }
    }

    //Purchase --------------------------

    @Override
    public void showMessage(int message) {
        if(getBaseActivity() != null){
            getBaseActivity().showToast(message);
        }
    }


    private void doPayment(int id, Books books) {
        if(id == books.getId()) {
            ((DashboardActivity)getBaseActivity()).setSubscriptionYear(books.getPublishYear());
//            ((DashboardActivity)getBaseActivity()).getBilling().doPurchase(viewModel.getPayload()); //TODO this change
            ((DashboardActivity)getBaseActivity()).doBookPurchase(viewModel.getPayload());
        }
    }

    public void loadBookData(int bookId) {
        viewModel.loadBooks(bookId);
    }

    @Override
    public void downloadStatus(boolean status) {
        ((DashboardActivity)getBaseActivity()).downloadStatus(status);
    }

    //Responsible for download book---------------
    @Override
    public void downloadFile(Books model) {
        viewModel.setBookModelCurrent(model);
        ((DashboardActivity)getBaseActivity()).downloadFile(model);
    }

    // Successful download of the book----------------

    @Override
    public void downloadFileStatus(boolean status) {
        ((DashboardActivity)getBaseActivity()).downloadStatus(false);
        if(status){
            if(getBaseActivity() != null){
                new AlertDialog.Builder(getBaseActivity())
                        .setTitle(getString(R.string.book_downloaded))
                        .setMessage(getString(R.string.on_download_book_positive_message))
                        .setCancelable(false)
                        .setPositiveButton(R.string.continue_, (dialog, which) -> onResponse(
                                Constant.FILE_EXIST,
                                viewModel.getBookModelCurrent()))
                        .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                        .show();
            }
        }
        else{
            if(getBaseActivity() != null){new AlertDialog.Builder(getBaseActivity())
                    .setTitle(getString(R.string.book_downloaded_failure))
                    .setMessage(getString(R.string.on_download_book_negative_message))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .show();
            }
        }
    }

    //Read book-----------------------

    public void readBookDialog(boolean status) {
        if(status){
            onReadBookClick = false;
            dialog = new ProgressDialog(getBaseActivity());
            dialog.setMessage(getString(R.string.loading_wait));
            dialog.setCancelable(false);
            dialog.show();
        }
        else if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            onReadBookClick = true;
        }
    }

    public void onBookDownloaded(int fileExist) {
        onResponse(fileExist, viewModel.getBookModelCurrent());
    }

}
