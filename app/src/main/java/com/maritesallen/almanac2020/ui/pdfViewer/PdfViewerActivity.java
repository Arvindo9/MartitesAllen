package com.maritesallen.almanac2020.ui.pdfViewer;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseActivity;
import com.maritesallen.almanac2020.databinding.ActivityPdfViewerBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Author : Arvindo Mondal
 * Created on 31-12-2019
 */
public class PdfViewerActivity extends BaseActivity<ActivityPdfViewerBinding, PdfViewerViewModel>
        implements PdfViewerNavigator{
    private static PdfOnErrorCallback callback;
    @Inject
    ViewModelProviderFactory factory;
    private ActivityPdfViewerBinding binding;
    private PdfViewerViewModel viewModel;

    public static Intent getInstance(Activity activity, PdfOnErrorCallback callback){
        PdfViewerActivity.callback = callback;
        return new Intent(activity, PdfViewerActivity.class);
    }


    public interface PdfOnErrorCallback{
        void onErrorOnDowunload();
    }


    /**
     * @param binding activity class CalendarData binding
     */
    @Override
    public void getActivityBinding(ActivityPdfViewerBinding binding) {

        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_pdf_viewer;
    }

    /**
     * Override for set binding variable
     *
     * @return BR.CalendarData;
     */
    @Override
    public int getBindingVariable() {
        return com.maritesallen.almanac2020.BR.data;
    }

    /**
     * Override for get the instance of viewModel
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public PdfViewerViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(PdfViewerViewModel.class);
    }

    /**
     * Do anything on onCreate after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        setUp();

        byte[] bytes = AppConstants.pdfByte;
        setPdfViewINCanvas(bytes);
    }


    private void setUp() {
        setSupportActionBar(binding.toolbarLayout.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        binding.toolbarLayout.toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setPdfViewINCanvas(byte[] getByte){
        try {
            binding.pdfView.fromBytes(getByte)
                    .swipeHorizontal(false) // false in vertical scrolling
                    .defaultPage(0)
                    .onError(t -> {
                        t.printStackTrace();
                        Logger.e("PdfViewerActivity: ", t.getMessage());
                        if(t instanceof IOException) {
                            finish();
                            callback.onErrorOnDowunload();
                        }
                    })
                    .spacing(10).onPageScroll((page, positionOffset) -> {
                        if(page<3){

                        }
                    }
            ).onTap(e -> {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        // get here the touch event
                        return true;
                }
                return false;
            }).onPageChange((page, pageCount) -> {
                // current page
            }).load();
        }catch(Exception e){
            e.printStackTrace();
            Logger.e("PdfViewerActivity: ", e.getMessage());
        }
    }
}
