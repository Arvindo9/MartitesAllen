package com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author : Arvindo Mondal
 * Created on 24-12-2019
 */
public class ChangeFragment extends Fragment {

    private static final String KEY_CARD_DATA = "KEY_CARD_DATA";
    private boolean forward = true;

    private static ChangeFragmentListener listener;

    public interface ChangeFragmentListener{
        void onChangeFragmentCallBack(boolean forward);
    }

    public static ForecastAdapterFragment newInstance(boolean status, ChangeFragmentListener listener) {
        Bundle args = new Bundle();
        ChangeFragment.listener = listener;
        ForecastAdapterFragment fragment = new ForecastAdapterFragment();
        args.putBoolean(KEY_CARD_DATA, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean forward = getArguments() == null || getArguments().getBoolean(KEY_CARD_DATA);
        listener.onChangeFragmentCallBack(forward);
    }
}
