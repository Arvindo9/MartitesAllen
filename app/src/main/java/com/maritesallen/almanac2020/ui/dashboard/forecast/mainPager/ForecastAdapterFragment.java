package com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.lifecycle.ViewModelProviders;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ForecastCard;
import com.maritesallen.almanac2020.databinding.FragmentForecastAdapterBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager.imagePager.ForecastImagePager;

import javax.inject.Inject;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class ForecastAdapterFragment extends BaseFragment<FragmentForecastAdapterBinding, ForecastAdapterViewModel>
        implements ForecastAdapterNavigator, ForecastPager.UpdateableFragment {

    private static final String KEY_CARD_DATA = "KEY_CARD_DATA";
    private static final long PAGER_TRANSITION_DURATION_MS = 2500;
    private static final int SLIDE_TIME = 7000;

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    ForecastImagePager adapter;

    private FragmentForecastAdapterBinding binding;
    private ForecastAdapterViewModel viewModel;
    private Runnable runnable;
    private Handler handler;
    private ForecastCard model;
    private boolean descriptionStatus = false;

    private int page = 0;

    public static ForecastAdapterFragment newInstance(ForecastCard model) {
        Bundle args = new Bundle();
        ForecastAdapterFragment fragment = new ForecastAdapterFragment();
        args.putParcelable(KEY_CARD_DATA, model);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentForecastAdapterBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_forecast_adapter;
    }

    /**
     * Override for set view model
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public ForecastAdapterViewModel getViewModel() {
        model = getArguments() != null ? getArguments().getParcelable(KEY_CARD_DATA) : null;
        return viewModel = ViewModelProviders.of(this,factory).get(ForecastAdapterViewModel.class);
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
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        if(model != null) {
            subscribeToLiveData();
            setUp();
            setUpPager();
            viewModel.setData(model);
        }
    }

    private void setUp() {
        binding.shopNow.setOnClickListener(v -> {
            String url;
            if(viewModel.isPhilippines() && model.getShowNowPh() != null && !model.getShowNowPh().isEmpty()){
                url = model.getShowNowPh();
            }
            else{
                url = model.getShowNow();
            }
            if(url == null || url.isEmpty()){
                return;
            }
            Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            binding.getRoot().getContext().startActivity(intent);
        });

        binding.dutyOfficer.setOnClickListener(v -> {
            if(getBaseActivity() != null){
//                getBaseActivity().startDialog(new DutyOfficerDialog(), DutyOfficerDialog.TAG,
//                        Bundles.setDutyOfficer(model.getDutyOfficer(), model.getDutyOfficerDescription()));

                if(descriptionStatus){
                    binding.layoutDutyOfficeDescription.setVisibility(View.GONE);
                    descriptionStatus = false;
                }
                else{
                    binding.layoutDutyOfficeDescription.setVisibility(View.VISIBLE);
                    descriptionStatus =true;
                }
            }
        });
/*
        RelativeLayout.LayoutParams layoutParams = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                (int)(Dimensions.getScreenHeight(getBaseActivity()) /2.8));
        binding.layout.setLayoutParams(layoutParams);
        */
    }


    private void setUpPager() {
//        viewModel.loadSlider();
        viewModel.loadSliderStatic();

        adapter.setType(ForecastImagePager.TYPE_STATIC);
        binding.pager.setAdapter(adapter);

        autoScroll();
    }

    private void subscribeToLiveData() {
        viewModel.getModelSliderLiveData().observe(this, data ->
                viewModel.addSliderData(data));
    }

    private void autoScroll() {
        /*After setting the adapter use the timer */
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (adapter.getCount() == page) {
                    page = 0;
                    binding.pager.setCurrentItem(page, true);
                } else {
                    page++;
                    animatePagerTransition(true);
                }

//                binding.pager.setCurrentItem(page, true);

                handler.postDelayed(this, SLIDE_TIME);
            }
        };

        handler.postDelayed(runnable, SLIDE_TIME);
    }


    private void animatePagerTransition(final boolean forward) {
        ValueAnimator animator = ValueAnimator.ofInt(0, binding.pager.getWidth() -
                ( forward ? binding.pager.getPaddingLeft() : binding.pager.getPaddingRight() ));
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    binding.pager.endFakeDrag();
                }catch (Exception ignore){}
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                try {
                    binding.pager.endFakeDrag();
                }catch (Exception ignore){}
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private int oldDragPosition = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int dragPosition = (Integer) animation.getAnimatedValue();
                int dragOffset = dragPosition - oldDragPosition;
                oldDragPosition = dragPosition;
                try {
                    binding.pager.fakeDragBy(dragOffset * (forward ? -1 : 1));
                }catch (Exception ignore){}

//                binding.pager.fakeDragBy(dragOffset * page);
            }
        });

        animator.setDuration(PAGER_TRANSITION_DURATION_MS);
        try {
            binding.pager.beginFakeDrag();
        }catch (Exception ignore){}
        animator.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void update(ForecastCard data) {
        model = data;
        viewModel.setData(data);
    }
}
