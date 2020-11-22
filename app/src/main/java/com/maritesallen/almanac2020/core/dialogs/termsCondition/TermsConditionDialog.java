package com.maritesallen.almanac2020.core.dialogs.termsCondition;

import android.text.Html;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseDialog;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.data.model.db.terms.Terms;
import com.maritesallen.almanac2020.databinding.DialogTermsConditionBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;

public class TermsConditionDialog extends BaseDialog<DialogTermsConditionBinding, TermsConditionViewModel>
        implements TermsConditionNavigator{

    public static final String TAG = TermsConditionDialog.class.getSimpleName();
    private DialogListener listener;
    @Inject
    ViewModelProviderFactory factory;
    private TermsConditionViewModel viewModel;
    private boolean isTermsLoded = false;
    private DialogTermsConditionBinding binding;

    private int type = 0;
    public static final int TYPE_TERMS = 0;
    public static final int TYPE_TERMS_INSIDE = 2;
    public static final int TYPE_PRIVACY = 1;
    public static final int TYPE_DISCLAMIER = 3;
    public static final int TYPE_ABOUT = 4;
    public static final int TYPE_APP_INFO= 5;

    public TermsConditionDialog(int size, int type){
        super(size);
        this.type = type;
    }

    public TermsConditionDialog(int size, DialogListener listener, int type){
        super(size);
        this.listener = listener;
        this.type = type;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.dialog_terms_condition;
    }

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public TermsConditionViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(TermsConditionViewModel.class);
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
    public void getActivityBinding(DialogTermsConditionBinding binding) {
        this.binding = binding;
    }

    /**
     * Do anything on onCreateView after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        binding.acceptTerms.setEnabled(false);
        binding.acceptPrivacy.setEnabled(false);
        if(type == TYPE_TERMS) {
            binding.acceptPrivacyLayout.setVisibility(View.GONE);
            return;
        }
        else if(type == TYPE_TERMS_INSIDE) {
            binding.acceptTerms.setText(R.string.ok);
            binding.acceptPrivacyLayout.setVisibility(View.GONE);
        }
        else if(type == TYPE_PRIVACY) {
//            binding.cancel.setVisibility(View.GONE);
            binding.termsTitle.setText(getString(R.string.privacyPolicy));
        }
        else if(type == TYPE_DISCLAMIER) {
            binding.termsTitle.setText(getString(R.string.disclaimer));
        }
        else if(type == TYPE_ABOUT) {
            binding.termsTitle.setText(getString(R.string.about));
        }
        else if(type == TYPE_APP_INFO) {
            binding.termsTitle.setText(getString(R.string.appInfo));
        }
        binding.acceptTermsLayout.setVisibility(View.GONE);
        binding.acceptPrivacyLayout.setVisibility(View.VISIBLE);

        if(!getBaseActivity().isNetworkAvailable()){
            getBaseActivity().showToast(R.string.network_error);
        }
    }


    @Override
    public void onCancelClick() {
        viewModel.cancel();
        dismissDialog();
    }

    @Override
    public void onAcceptClick() {
        viewModel.cancel();
        dismissDialog();
        if(isTermsLoded) {
            listener.onSuccessDialogResponse(TAG, AppConstants.YES, String.valueOf(type));
        }
    }

    @Override
    public void onDeclineClick() {
        viewModel.cancel();
        dismissDialog();
        listener.onSuccessDialogResponse(TAG, AppConstants.NO, String.valueOf(type));
    }

    @Override
    public void onTermsUpdate(List<Terms> terms) {
        viewModel.cancel();
        if(terms != null && !terms.isEmpty()){
            binding.acceptTerms.setEnabled(true);
            binding.acceptPrivacy.setEnabled(true);
            isTermsLoded = true;
            for (Terms term: terms) {
                if(type == TYPE_TERMS || type == TYPE_TERMS_INSIDE) {
                    if (term.getPageTitle().equals(AppConstants.TERMS)) {
//                        binding.termsBody.setText(term.getPageContent());
                        binding.termsBody.setText(Html.fromHtml(term.getPageContent()).toString());
                    }
                }
                else if(type == TYPE_PRIVACY) {
                    if (term.getPageTitle().equals(AppConstants.PRIVACY)) {
//                        binding.termsBody.setText(term.getPageContent());
                        binding.termsBody.setText(Html.fromHtml(term.getPageContent()).toString());
                    }
                }
                else if(type == TYPE_DISCLAMIER) {
                    if (term.getPageTitle().equals(AppConstants.DISCLAIMER)) {
//                        binding.termsBody.setText(term.getPageContent());
                        binding.termsBody.setText(Html.fromHtml(term.getPageContent()).toString());
                    }
                }
                else if(type == TYPE_ABOUT) {
                    if (term.getPageTitle().equals(AppConstants.ABOUT)) {
//                        binding.termsBody.setText(term.getPageContent());
                        binding.termsBody.setText(Html.fromHtml(term.getPageContent()).toString());
                    }
                }
                else if(type == TYPE_APP_INFO) {
                    if (term.getPageTitle().equals(AppConstants.APP_INFO)) {
//                        binding.termsBody.setText(term.getPageContent());
                        binding.termsBody.setText(Html.fromHtml(term.getPageContent()).toString());
                    }
                }
            }
        }
        else{
            isTermsLoded = false;
            if(getBaseActivity()!=null){
                if(!getBaseActivity().isNetworkAvailable()){
                    getBaseActivity().showToast(R.string.network_error);
                }
                else {
                    getBaseActivity().showToast(R.string.error_default);
                }
            }
        }
    }
}
