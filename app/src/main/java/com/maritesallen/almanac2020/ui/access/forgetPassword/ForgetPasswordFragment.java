package com.maritesallen.almanac2020.ui.access.forgetPassword;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.databinding.FragmentForgetPasswordBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.utils.Logger;

import javax.inject.Inject;

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
public class ForgetPasswordFragment extends BaseFragment<FragmentForgetPasswordBinding, ForgetPasswordViewModel>
        implements ForgetPasswordNavigator{

    @Inject
    ViewModelProviderFactory factory;
    private FragmentForgetPasswordBinding binding;
    private ForgetPasswordViewModel viewModel;


    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentForgetPasswordBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_forget_password;
    }

    /**
     * Override for set view model
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public ForgetPasswordViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(ForgetPasswordViewModel.class);
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
     * @return R.strings.text
     */
    @Override
    public int setTitle() {
        return 0;
    }

    /**
     * @param savedInstanceState save the instance of fragment before closing
     * @param args               if any Data transfer in form bundles
     */
    @Override
    protected void onCreateFragment(Bundle savedInstanceState, Bundle args) {

    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
    }

    @Override
    public void onSendEmailClick(View view) {
        getBaseActivity().hideKeyboard();
        submitForm();
    }

    @Override
    public void onSuccess(String message) {
        getBaseActivity().showToast(message);
        getBaseActivity().hideKeyboard();
        if(getView()!=null) {
            Navigation.findNavController(getView()).navigate(R.id.action_forgetPasswordFragment_to_forgetPasswordUpdateFragment);
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

        String email = binding.email.getText() != null ? binding.email.getText().toString() : "";

        Logger.e("email:" + email);

        viewModel.doForgetPassword(email);
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

}
