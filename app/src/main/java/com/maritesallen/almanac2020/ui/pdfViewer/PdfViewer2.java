package com.maritesallen.almanac2020.ui.pdfViewer;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.databinding.ActivityPdfViewerBinding;
import com.maritesallen.almanac2020.utils.AppConstants;

/**
 * Author : Arvindo Mondal
 * Created on 31-12-2019
 */
public class PdfViewer2 extends AppCompatActivity {

    private ActivityPdfViewerBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pdf_viewer);
        setUp();



//        Bundle bundle = getIntent().getBundleExtra("Bun");
//        byte[] bytes = bundle.getByteArray("sdf");
        byte[] bytes = AppConstants.pdfByte;
        setPdfViewINCanvas(bytes);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUp() {
        setSupportActionBar(binding.toolbarLayout.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        binding.toolbarLayout.toolbar.setVisibility(View.VISIBLE);
    }


    public void setPdfViewINCanvas(byte[] getByte){


        try {
            binding.pdfView.fromBytes(getByte)
                    .swipeHorizontal(false) // false in vertical scrolling
                    .defaultPage(0)
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
            binding.pdfView.fitToWidth();
        }catch(Exception e){e.printStackTrace();}
    }

}
