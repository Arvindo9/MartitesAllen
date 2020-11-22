package com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity;

import com.maritesallen.almanac2020.R;

/**
 * Author : Arvindo Mondal
 * Created on 05-12-2019
 */
public class ForecastImageCard {

    private int[] images;

    public ForecastImageCard(){
        images = new int[]{R.drawable.auspicious_symbols_1,
                R.drawable.auspicious_symbols_2, R.drawable.auspicious_symbols_3,
                R.drawable.auspicious_symbols_4, R.drawable.auspicious_symbols_5,
                R.drawable.auspicious_symbols_6, R.drawable.auspicious_symbols_7,
                R.drawable.auspicious_symbols_8};
    }

    public int[] getImages() {
        return images;
    }
}
