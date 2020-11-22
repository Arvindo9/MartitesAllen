package com.maritesallen.almanac2020.ui.pdfViewer;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

/**
 * Author : Arvindo Mondal
 * Created on 31-12-2019
 */
public class PdfViewerViewModel extends BaseViewModel<PdfViewerNavigator> {

    public PdfViewerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
