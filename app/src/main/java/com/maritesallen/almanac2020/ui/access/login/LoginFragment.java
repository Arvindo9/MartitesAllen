package com.maritesallen.almanac2020.ui.access.login;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.maritesallen.almanac2020.BR;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog;
import com.maritesallen.almanac2020.databinding.FragmentLoginBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.utils.Logger;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_TERMS;
import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_TERMS_INSIDE;
import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_FULL;

/**
 * Author       : Arvindo Mondal
 * Created on   : 11-11-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel>
        implements LoginNavigator, DialogListener {
    @Inject
    ViewModelProviderFactory factory;
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;
    private boolean showPassword = false;
    private boolean isTermsLoded = false;


    /**
     * Override for set binding variable
     *
     * @return BR.Data;
     */
    @Override
    public int getBindingVariable() {
        return BR.data;
    }

    /**
     * @return R.strings.text
     */
    @Override
    public int setTitle() {
        return 0;
    }

    /**
     * @param savedInstanceState save the instance of fragment before closing
     * @param args if any Data transfer in form bundles
     *
     * viewModel.setNavigator(this);
     */
    @Override
    protected void onCreateFragment(Bundle savedInstanceState, Bundle args) {
        viewModel.setNavigator(this);
    }

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentLoginBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_login;
    }

    /**
     * Override for get the instance of viewModel
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public LoginViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(LoginViewModel.class);
    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
//        setUpUIText();
    }


    private void setUpUIText() {
        SpannableString ss = new SpannableString(getString(R.string.signUpCondition));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NotNull View textView) {
                //Start Dialog
                if(isNetworkConnected()) {
                    getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, LoginFragment.this, TYPE_TERMS),
                            TermsConditionDialog.TAG);
                }
                else{
                    showMessage(R.string.network_error);
                }
            }
            @Override
            public void updateDrawState(@NotNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        ss.setSpan(clickableSpan, 69, 86, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan, 91, 105, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.terms.setText(ss);
        binding.terms.setMovementMethod(LinkMovementMethod.getInstance());
        binding.terms.setHighlightColor(Color.TRANSPARENT);
    }

    //Navigator----------------------

    @Override
    public void onForgetPasswordClick(View view) {
        getBaseActivity().hideKeyboard();
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgetPasswordFragment);
    }

    @Override
    public void onLoginClick(View view) {
        getBaseActivity().hideKeyboard();
        submitForm();
    }

    @Override
    public void onShowPasswordClick() {
        //to show
        if(!showPassword) {
            binding.password.setTransformationMethod(new PasswordTransformationMethod());
            showPassword = true;
            binding.showPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_eye));
        }
        else{
            //to hide
            binding.password.setTransformationMethod(null);
            showPassword = false;
            binding.showPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_eye_close));
        }
    }

    @Override
    public void onRememberClick() {

    }

    @Override
    public void onSignUpClick(View view) {
        getBaseActivity().hideKeyboard();
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
    }

    @Override
    public void onTermsClick() {
        if (getBaseActivity() != null) {
            getBaseActivity().hideKeyboard();
            if (getBaseActivity().isNetworkAvailable()) {
                getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_TERMS_INSIDE),
                        TermsConditionDialog.TAG);
            } else {
                getBaseActivity().showToast(R.string.network_error);
            }
        }
    }

    @Override
    public void onLoginSuccess() {
        if(getBaseActivity() != null) {
            getBaseActivity().startActivity(DashboardActivity.class);
            getBaseActivity().finishAffinity();
        }
    }

    @Override
    public void onLoginError(String message) {
//        getBaseActivity().showToast(R.string.authentication_fail);
        if(message != null){
            getBaseActivity().showToast(message);
        }
        else{
            getBaseActivity().showToast(R.string.authentication_fail);
        }
    }

    @Override
    public void showMessage(int message) {
        if(getBaseActivity() != null) {
            getBaseActivity().showToast(message);
        }
    }

    //Submit task------------------------------

    private void submitForm() {
        if (!validateEmail(binding.email)) {
            return;
        }

        if (!validatePassword(binding.password)) {
            getBaseActivity().showToast(getString(R.string.invalid_password));
            return;
        }

        String email = binding.email.getText() != null ? binding.email.getText().toString() : "";
        String password = binding.password.getText() != null ? binding.password.getText().toString() : "";

        Logger.e("email:" + email);
        Logger.e("password:" + password);

        viewModel.doLogin(email, password);
    }

    //Additional-------------------------------

    private boolean validateEmail(EditText editText) {
        String string = editText.getText().toString();
        if (string.matches("[*a-zA-Z]") ||
                !string.contains("@") ||
                !string.contains(".") ||
                string.contains("@.")||
                string.startsWith("@")||
                string.endsWith(".")||
                (string.lastIndexOf(".") < string.indexOf("@"))) {
            editText.setError(getString(R.string.invalid_email));
            getBaseActivity().requestFocus(editText);
            return false;
        }
        else {
            editText.setError(null);
        }

        return true;
    }

    private boolean validatePassword(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
//            editText.setError(getString(R.string.invalid_password));
            getBaseActivity().requestFocus(editText);
            return false;
        }
        else {
            editText.setError(null);
        }

        return true;
    }

    /**
     * Default response method of a dialog
     *
     * @param tag    class name from which the response is getting
     * @param params string array with relative Data
     */
    @Override
    public void onSuccessDialogResponse(String tag, String... params) {

    }
}
