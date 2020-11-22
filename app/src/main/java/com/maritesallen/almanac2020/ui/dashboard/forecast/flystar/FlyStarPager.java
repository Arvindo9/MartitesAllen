package com.maritesallen.almanac2020.ui.dashboard.forecast.flystar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.data.model.apis.slider.Slider;
import com.maritesallen.almanac2020.databinding.AdapterFlyStarBinding;
import com.maritesallen.almanac2020.databinding.AdapterGhostMonthBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 05-12-2019
 */
public class FlyStarPager extends PagerAdapter {

    private final ArrayList<Slider> list;
    private LayoutInflater layoutInflater;

    public FlyStarPager(Context context, ArrayList<Slider> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItems(List<Slider> model) {
        list.addAll(model);
        notifyDataSetChanged();
    }

    public void clearItems() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == object;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, final int position) {

            AdapterFlyStarBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_fly_star,
                    container, false);
            binding.setData(new FlyStarViewModel(list.get(position)));

            container.addView(binding.getRoot());
            return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((RelativeLayout) object);
    }


    //-------------------------------

}
