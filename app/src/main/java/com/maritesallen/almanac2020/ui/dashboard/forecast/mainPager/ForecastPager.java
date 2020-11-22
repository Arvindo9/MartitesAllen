package com.maritesallen.almanac2020.ui.dashboard.forecast.mainPager;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.maritesallen.almanac2020.data.model.apis.forecast.forecastActivity.ForecastCard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Author : Arvindo Mondal
 * Created on 06-12-2019
 */
public class ForecastPager extends FragmentStatePagerAdapter {

    private ArrayList<ForecastCard> list;
    private SparseArray<Fragment> mPageReferenceMap;

    public ForecastPager(FragmentManager fragmentManager) {
        super(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mPageReferenceMap = new SparseArray<>();
        list = new ArrayList<>();
    }

    public interface UpdateableFragment {
        void update(ForecastCard data);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void setValues(ArrayList<ForecastCard> list) {
//        this.list = list;
        try{
            this.list.addAll(list);
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearData(){
        this.list.clear();
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
//        if(list.get(position) == null){
//            return ChangeFragment.newInstance(position > 0, this);
//        }
        Fragment fragment = ForecastAdapterFragment.newInstance(list.get(position));
        mPageReferenceMap.put(position, fragment);
        return fragment;
    }


    public int getItemPosition(@NotNull Object object) {
        if (object instanceof ForecastAdapterFragment && !list.isEmpty()) {
            ((ForecastAdapterFragment) object).update(list.get(1));
        }
        //don't return POSITION_NONE, avoid fragment recreation.
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem (@NotNull ViewGroup container, int position, @NotNull Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }

    public Fragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }
}
