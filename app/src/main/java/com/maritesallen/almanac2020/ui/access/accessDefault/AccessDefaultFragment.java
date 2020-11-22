package com.maritesallen.almanac2020.ui.access.accessDefault;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog;
import com.maritesallen.almanac2020.databinding.FragmentAccessDefaultBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.bundles.Bundles;
import com.maritesallen.almanac2020.utils.util.DeviceId;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;

import javax.inject.Inject;

import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_DISCLAMIER;
import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_TERMS;
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
public class AccessDefaultFragment extends BaseFragment<FragmentAccessDefaultBinding, AccessDefaultViewModel>
        implements AccessDefaultNavigator, DialogListener {

    @Inject
    ViewModelProviderFactory factory;
    private AccessDefaultViewModel viewModel;
    private FragmentAccessDefaultBinding binding;

    private static final String PUBLIC_PROFILE = "public_profile";
    private static final String EMAIL = "email";
//    private static final String USER_BIRTHDAY = "user_birthday";
    private CallbackManager callbackManager;
    private boolean isGuestLogin = true;
    private Location location;
    private String deviceId;


    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentAccessDefaultBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_access_default;
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
     * @param args if any Data transfer in form bundles
     *
     * viewModel.setNavigator(this);
     */
    @Override
    protected void onCreateFragment(Bundle savedInstanceState, Bundle args) {
        viewModel.setNavigator(this);
//        setUpFacebookWithManager();
    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        setUp();
        setUpLocation();
        setUpFacebook();
    }

    private void setUpLocation() {
        try {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getBaseActivity());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getBaseActivity(), location -> {
                        // Got last known location. In some rare situations this can be null.
                        this.location = location;
                    });
        }
        catch (Exception e){
            e.printStackTrace();
        }

        deviceId = DeviceId.GetDeviceId(new WeakReference<>(getBaseActivity()));
    }


    private void setUpFacebook() {
        callbackManager = CallbackManager.Factory.create();

        binding.loginButton.setReadPermissions(Arrays.asList(PUBLIC_PROFILE, EMAIL));
//        binding.loginButton.setReadPermissions(Collections.singletonList(EMAIL));
        // If you are using in a fragment, call
         binding.loginButton.setFragment(this);


        // Callback registration
        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {
                            Log.e("LoginActivity", response.toString());

                            String email = "";
                            String birthday = ""; // 01/31/1980 format
                            String fbId = "";
                            String name = "";

                            // Application code
                            try {
                                email = object.getString("email");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
/*
                            try {
                                birthday = object.getString("birthday");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }*/

                            try {
                                fbId = object.getString("id");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                name = object.getString("name");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String latitude = "0.0";
                            String longitude = "0.0";

                            if(location != null){
                                latitude = String.valueOf(location.getLatitude());
                                longitude = String.valueOf(location.getLongitude());
                            }

//                            FacebookModel model = new FacebookModel(fbId, name, email);

                            viewModel.doLoginWithFacebook(fbId, name, email, latitude + "," + longitude, deviceId);

                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,birthday");
                request.setParameters(parameters);
                request.executeAsync();
/*
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if(isLoggedIn) {
                    viewModel.setLoginFBSuccess();
                    openDashboard();
                }
*/
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


    }

    private void setUpFacebookWithManager() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
    }

    private void setUp() {
//        binding.facebookIcon.bringToFront();
    }

    /**
     * Override for get the instance of viewModel
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public AccessDefaultViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(AccessDefaultViewModel.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Navigator----------------------

    @Override
    public void onLoginFacebookClick(View view) {
        //Start Dialog
        if (getBaseActivity() != null) {
            if (getBaseActivity().isNetworkAvailable()) {
                isGuestLogin = false;
                getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_TERMS),
                        TermsConditionDialog.TAG);
            } else {
                getBaseActivity().showToast(R.string.network_error);
            }
        }

//        binding.loginButton.performClick();


//        Navigation.findNavController(view).navigate(R.id.action_accessDefaultFragment_to_registrationFacebookFragment);
    }

    @Override
    public void onSignUpClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_accessDefaultFragment_to_registrationFragment);
    }

    @Override
    public void onLoginInClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_accessDefaultFragment_to_loginFragment);
    }

    @Override
    public void onLoginGuestClick(View view) {
        //Start Dialog
        if (getBaseActivity() != null) {
            if (getBaseActivity().isNetworkAvailable()) {
                isGuestLogin = true;
                getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_DISCLAMIER),
                        TermsConditionDialog.TAG);
            } else {
                getBaseActivity().showToast(R.string.network_error);
            }
        }

//        getBaseActivity().startActivity(DashboardActivity.class);
//        getBaseActivity().finish();
    }

    @Override
    public void openDashboard(){
        getBaseActivity().startActivity(DashboardActivity.class);
        getBaseActivity().finish();
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
    public void openFacebookForm(String email) {
        if(getView() != null) {
            Bundle bundle = Bundles.setFacebookData(email != null && !email.isEmpty()? email : "");
            Navigation.findNavController(getView())
                    .navigate(R.id.action_accessDefaultFragment_to_registrationFacebookFragment,  bundle);
        }
    }

    @Override
    public void showMessage(int message) {
        if(getBaseActivity() != null) {
            getBaseActivity().showToast(message);
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
        if(tag.equals(TermsConditionDialog.TAG)){
            if(isGuestLogin) {
                if (params.length > 0 && params[0].equals(AppConstants.YES) && params[1].equals(String.valueOf(TYPE_DISCLAMIER))) {
                    openDashboard();
                    viewModel.setLoginGuest();
                }
            }
            else{
                if (params.length > 0 && params[0].equals(AppConstants.YES) && params[1].equals(String.valueOf(TYPE_TERMS))) {
                    binding.loginButton.performClick();
                }
            }
        }
    }
}
