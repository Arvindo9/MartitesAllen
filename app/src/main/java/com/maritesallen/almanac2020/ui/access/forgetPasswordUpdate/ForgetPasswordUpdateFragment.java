package com.maritesallen.almanac2020.ui.access.forgetPasswordUpdate;

import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.databinding.FragmentForgetPasswordUpdateBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.Logger;

import javax.inject.Inject;

/**
 * Author : Arvindo Mondal
 * Created on 18-12-2019
 */
public class ForgetPasswordUpdateFragment extends BaseFragment<FragmentForgetPasswordUpdateBinding, ForgetPasswordUpdateViewModel>
        implements ForgetPasswordUpdateNavigator{

    @Inject
    ViewModelProviderFactory factory;
    private FragmentForgetPasswordUpdateBinding binding;
    private ForgetPasswordUpdateViewModel viewModel;
    private boolean showPassword = false;

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentForgetPasswordUpdateBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_forget_password_update;
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
     * Override for get the instance of viewModel
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public ForgetPasswordUpdateViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(ForgetPasswordUpdateViewModel.class);
    }

    /**
     * Do anything on onCreate after binding
     * viewModel.setNavigator(this);
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
    }

    @Override
    public void onSuccess(String message) {
        getBaseActivity().showToast(message);

        if(getView()!=null) {
            Navigation.findNavController(getView()).navigate(R.id.action_forgetPasswordUpdateFragment_to_loginFragment);
        }
    }

    @Override
    public void onError(String message) {
        if(message != null){
            getBaseActivity().showToast(message);
        }
        else{
            getBaseActivity().showToast(R.string.error_default);
        }
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
    public void onSendEmailClick(View view) {
        getBaseActivity().hideKeyboard();
        submitForm();
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
        if (!validatePassword((binding.password))) {
            return;
        }
        if (!validateOTP(binding.otp)) {
            return;
        }

        String email = binding.email.getText() != null ? binding.email.getText().toString() : "";
        String password = binding.password.getText() != null ? binding.password.getText().toString() : "";
        String otp = binding.otp.getText() != null ? binding.otp.getText().toString() : "";

        Logger.e("email:" + email);

        viewModel.doForgetPassword(email, password, otp);
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

    private boolean validateOTP(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(getString(R.string.invalid_Otp));
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

}
