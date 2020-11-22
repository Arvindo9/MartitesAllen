package com.maritesallen.almanac2020.core.dialogs.book;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.databinding.DialogBookPurchaseBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.bundles.Bundles;

import javax.inject.Inject;

/**
 * Author : Arvindo Mondal
 * Created on 17-12-2019
 */
public class BookPurchaseDialog extends BaseDialog<DialogBookPurchaseBinding, BookPurchaseViewModel>
        implements BookPurchaseNavigator{

    public static final String TAG = BookPurchaseDialog.class.getSimpleName();
    @Inject
    ViewModelProviderFactory factory;
    private BookPurchaseViewModel viewModel;
    private DialogBookPurchaseBinding binding;

    private DialogListener listener;
    private Books model = null;
    private int position = -1;

    public BookPurchaseDialog(int size){
        super(size);
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_book_purchase;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public BookPurchaseViewModel getViewModel() {
        model = Bundles.getBook(getArguments());
        position = Bundles.getBookId(getArguments());
        return viewModel = ViewModelProviders.of(this,factory).get(BookPurchaseViewModel.class);
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
     * @param binding activity class CalendarData binding
     */
    @Override
    public void getActivityBinding(DialogBookPurchaseBinding binding) {
        this.binding = binding;
    }

    /**
     * Do anything on onCreateView after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        viewModel.loadData(model);
    }

    @Override
    public void onCancelClick() {
        viewModel.cancel();
        listener.onSuccessDialogResponse(TAG);
        dismissDialog();
    }

    @Override
    public void onPurchaseClick() {
        viewModel.cancel();
        listener.onSuccessDialogResponse(TAG, String.valueOf(model.getId()), String.valueOf(position), AppConstants.YES);
        dismissDialog();
    }
}
