package com.maritesallen.almanac2020.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.util.Dimensions;

import org.jetbrains.annotations.NotNull;

import dagger.android.support.AndroidSupportInjection;

import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_FULL;
import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_FULL_MARGIN;
import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_MEDIAM;

/**
 * Author       : Arvindo Mondal
 * Created on   : 22-05-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public abstract class BaseDialog<B extends ViewDataBinding, V extends BaseViewModel> extends DialogFragment {

    private B binding;
    private V viewModel;
    private BaseActivity activity;
    private Context context;

    private int size = DIALOG_SIZE_MEDIAM;
    private int screenHeightHalf = 0;
    private int screenHeightFull = 0;
    private int screenHeightMediamFull = 0;

    public BaseDialog(int size){
        super();
        this.size = size;
    }

    public BaseDialog(){
        super();
    }

    /**
     *
     * @return R.layout.layout_file
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * Override for get the instance of viewModel
     * String string = getArguments() != null ? getArguments().getString(KEY) : "";
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    public abstract V getViewModel();

    /**
     * Override for set binding variable
     *
     * @return BR.Data;
     */
    public abstract int getBindingVariable();

    /**
     *
     * @param binding activity class Data binding
     */
    public abstract void getActivityBinding(B binding);

    /**
     * Do anything on onCreateView after binding
     * viewModel.setNavigator(this);
     */
    protected abstract void init();

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.activity = activity;
            activity.onFragmentAttached(null);
        }
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().setGravity(Gravity.CENTER);
        }
        dialog.setCanceledOnTouchOutside(false);

        screenHeightHalf = (int) (Dimensions.getScreenHeight(getBaseActivity()) / 2);
        screenHeightMediamFull = (int) (Dimensions.getScreenHeight(getBaseActivity()) * 2 / 3);
        screenHeightFull = (int) (Dimensions.getScreenHeight(getBaseActivity()) * 11 / 13);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setGravity(Gravity.CENTER);
        }

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        performDataBinding();
/*

        RelativeLayout.LayoutParams layoutParams = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        binding.getRoot().setLayoutParams(layoutParams);

*/

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void performDependencyInjection(){
        AndroidSupportInjection.inject(this);
    }

    private void performDataBinding() {
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        binding.setVariable(getBindingVariable(), viewModel);
        binding.executePendingBindings();
        getActivityBinding(binding);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(size == DIALOG_SIZE_MEDIAM) {
            if (getDialog() != null) {
                Window window = getDialog().getWindow();
                if (window != null) {
                    window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, screenHeightHalf);
                    window.setGravity(Gravity.CENTER);
                    Logger.e("Dialog resized half");
                }
            }
        }
        else if(size == DIALOG_SIZE_FULL) {
/*
            if (getDialog() != null) {
                Window window = getDialog().getWindow();

                if (window != null) {
                    window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, screenHeightFull);
                    window.setGravity(Gravity.CENTER);
                    Logger.e("Dialog resized full");
                }
            }
*/

        }
        else if(size == DIALOG_SIZE_FULL_MARGIN) {
            if (getDialog() != null) {
                Window window = getDialog().getWindow();

                if (window != null) {
                    window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, screenHeightMediamFull);
                    window.setGravity(Gravity.CENTER);
                    Logger.e("Dialog resized full");
                }
            }
        }

    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    public void dismissDialog() {
        dismiss();
        getBaseActivity().onFragmentDetached();
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    public void hideLoading() {
//        if (activity != null) {
//            activity.hideLoading();
//        }
    }

    public boolean isNetworkConnected() {
        return activity != null && activity.isNetworkAvailable();
    }

    public void openActivityOnTokenExpire() {
        if (activity != null) {
            activity.openActivityOnTokenExpire();
        }
    }

    public void showLoading() {
        if (activity != null) {
//            activity.showLoading();
        }
    }
}
