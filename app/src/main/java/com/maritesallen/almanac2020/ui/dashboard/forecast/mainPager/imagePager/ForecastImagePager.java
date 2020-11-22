package com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager.imagePager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.data.model.apis.forecast.forecastSlider.Slider;
import com.maritesallen.almanac2020.databinding.AdapterImageForecastBinding;
import com.maritesallen.almanac2020.databinding.AdapterImageForecastStaticBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 05-12-2019
 */
public class ForecastImagePager extends PagerAdapter {

    public static final int TYPE_STATIC = 1;
    public static final int TYPE_DYNAMIC = 2;
    private int type = TYPE_STATIC;
    private final ArrayList<Slider> list;
    private LayoutInflater layoutInflater;

    public ForecastImagePager(Context context, ArrayList<Slider> list) {
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
        if(view == object){
            return true;
        }
        else {
            return false;
        }
//        return view == object;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, final int position) {

        if(type == TYPE_DYNAMIC) {
            AdapterImageForecastBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_image_forecast,
                    container, false);
            binding.setData(new ImageViewModel(list.get(position)));

            container.addView(binding.getRoot());
            return binding.getRoot();
        }
        else{
            AdapterImageForecastStaticBinding
                    binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_image_forecast_static,
                    container, false);
            binding.setData(new ImageViewModel(list.get(position), type));

            container.addView(binding.getRoot());
            return binding.getRoot();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    public void setType(int type) {
        this.type = type;
    }

    //-------------------------------

}
