package com.maritesallen.almanac2020.base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.core.binding.BindingUtils;
import com.maritesallen.almanac2020.data.model.apis.calender.activity.Activity;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.util.Dimensions;

import java.util.ArrayList;
import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 18-10-2019
 * Email        : arvindo@aiprog.in

 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 *
 */
public class BaseLinearTextHorizontal extends LinearLayoutCompat {
    @LayoutRes
    private int layout;
    @LayoutRes
    private int layoutText;
    private float rightMargin = 5f;
    private ArrayList<Activity> list;
    private Listener listener;
    private String date;
    private int viewSize = 70;

    public interface Listener{
        void onBaseCalendarIconClick(View view, String date);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public BaseLinearTextHorizontal(Context context) {
        super(context);
        init(context, null);
        list = new ArrayList<>();
    }

    public BaseLinearTextHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(attrs);
        init(context, attrs);
        list = new ArrayList<>();
    }

    public BaseLinearTextHorizontal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(attrs);
        init(context, attrs);
        list = new ArrayList<>();
    }

    public void addItems(List<Activity> model, String date) {
        this.date = date;
        list.clear();
        list.addAll(model);
        onItemUpdates();
    }

    public void clearItems() {
        list.clear();
    }

    private void getAttributes(AttributeSet attrs) {
        TypedArray type = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.BaseLinearTextHorizontal,
                0, 0);

        try {
            int textBackground = type.getColor(R.styleable.BaseLinearTextHorizontal_TextBackground,
                    Color.parseColor("#FFFFFF"));
            int textColor = type.getColor(R.styleable.BaseLinearTextHorizontal_TextColor,
                    Color.parseColor("#FFFFFF"));
            layout = type.getResourceId(R.styleable.BaseLinearTextHorizontal_Layout, 0);
            layoutText = type.getResourceId(R.styleable.BaseLinearTextHorizontal_LayoutText,0);
            viewSize = (int) type.getDimension(R.styleable.BaseLinearTextHorizontal_ViewSize,0);
            rightMargin = type.getDimension(R.styleable.BaseLinearTextHorizontal_RightMargin,0);
        } finally {
            type.recycle();
        }
    }

    private void init(Context context, AttributeSet attrs) {

    }

    private void onItemUpdates() {
//        final int textSize = 70;
        int screenWidth = Dimensions.getScreenWidth(getContext());
        for(int i=0; i < list.size(); i++){
            LinearLayoutCompat mainView = (LinearLayoutCompat)
                    LayoutInflater.from(getContext()).inflate(layout,null);

            LayoutParams params =
                    new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10, 0,0);
            mainView.setLayoutParams(params);

            boolean ok = true;
            View lastView = null;
            float viewWidth = 0;
            while(ok && i < list.size()) {
                ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(layoutText, null);
                BindingUtils.setImageUrl(view, list.get(i).getImage());

                params = new LayoutParams(viewSize, viewSize);
                params.setMargins(0,0, (int) rightMargin,0);
                view.setLayoutParams(params);

                view.setOnClickListener(v -> {
                    if(listener != null) {
                        listener.onBaseCalendarIconClick(view, date);
                    }
                });

                if(lastView == null){
                    lastView = view;
                    mainView.addView(view);
                    viewWidth = Dimensions.getTextViewWeight(view);// + rightMargin;
                    i++;
                }
                else {
                    viewWidth = viewWidth + viewSize;

                    Logger.e(" i =" + i);
                    Logger.e(" viewWidth =" + viewWidth);
                    Logger.e(" screenWidth =" + screenWidth);
                    Logger.e(" rightMargin =" + rightMargin);
                    Logger.e(" (screenWidth - viewWidth) =" + (screenWidth - viewWidth));

                    if (viewWidth < screenWidth && ((screenWidth - viewWidth) > rightMargin)) {
                        mainView.addView(view);
                        lastView = view;
                        i++;
                    } else {
                        ok = false;
                        i--;
                    }
                }
            }
            this.addView(mainView);
        }
    }

    private void onItemUpdates5() {
//        final int textSize = 70;
        int screenWidth = Dimensions.getScreenWidth(getContext());
        for(int i=0; i < list.size(); i++){
            LinearLayoutCompat mainView = (LinearLayoutCompat)
                    LayoutInflater.from(getContext()).inflate(layout,null);

            LayoutParams params =
                    new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10, 0,0);
            mainView.setLayoutParams(params);

            boolean ok = true;
            View lastView = null;
            float viewWidth = 0;
            while(ok && i < list.size()) {
                ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(layoutText, null);
                BindingUtils.setImageUrl(view, list.get(i).getImage());

                params = new LayoutParams(viewSize, viewSize);
                params.setMargins(0,0, (int) rightMargin,0);
                view.setLayoutParams(params);

                view.setOnClickListener(v -> {
                    if(listener != null) {
                        listener.onBaseCalendarIconClick(view, date);
                    }
                });

                if(lastView == null){
                    lastView = view;
                    mainView.addView(view);
                    viewWidth = Dimensions.getTextViewWeight(view);// + rightMargin;
                    i++;
                }
                else {
                    viewWidth = viewWidth + viewSize;

                    Logger.e(" i =" + i);
                    Logger.e(" viewWidth =" + viewWidth);
                    Logger.e(" screenWidth =" + screenWidth);
                    Logger.e(" rightMargin =" + rightMargin);
                    Logger.e(" (screenWidth - viewWidth) =" + (screenWidth - viewWidth));

                    if (viewWidth < screenWidth && ((screenWidth - viewWidth) > rightMargin)) {
                        mainView.addView(view);
                        lastView = view;
                        i++;
                    } else {
                        ok = false;
                        i--;
                    }
                }
            }
            this.addView(mainView);
        }
    }

    private void onItemUpdates4() {
//        final int textSize = 70;
        for(int i=0; i < list.size(); i++){
            LinearLayoutCompat mainView = (LinearLayoutCompat)
                    LayoutInflater.from(getContext()).inflate(layout,null);

            LayoutParams params =
                    new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10, 0,0);
            mainView.setLayoutParams(params);

            boolean ok = true;
            View lastView = null;
            float viewWidth = 0;
            while(ok && i < list.size()) {
                ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(layoutText, null);
                BindingUtils.setImageUrl(view, list.get(i).getImage());

                params = new LayoutParams(viewSize, viewSize);
                params.setMargins(0,0, (int) rightMargin,0);
                view.setLayoutParams(params);

                view.setOnClickListener(v -> {
                    if(listener != null) {
                        listener.onBaseCalendarIconClick(view, date);
                    }
                });

                if(lastView == null){
                    lastView = view;
                    mainView.addView(view);
                    viewWidth = Dimensions.getTextViewWeight(view) + rightMargin;
                    i++;
                }
                else {
//                    float textSize = Dimensions.getTextViewWeight(view);
//                    viewWidth = viewWidth + textSize + rightMargin;
                    viewWidth = viewWidth + viewSize;
                    int screenWidth = Dimensions.getScreenWidth(getContext());


                    Logger.e(" i =" + i);
                    Logger.e(" viewWidth =" + viewWidth);
                    Logger.e(" screenWidth =" + screenWidth);
                    Logger.e(" rightMargin =" + rightMargin);
                    Logger.e(" (screenWidth - viewWidth) =" + (screenWidth - viewWidth));

                    if (viewWidth < screenWidth && ((screenWidth - viewWidth) > rightMargin)) {
                        mainView.addView(view);
                        lastView = view;
                        i++;
                    } else {
                        ok = false;
                        i--;
                    }
                }
            }
            this.addView(mainView);
        }
    }

    private void onItemUpdates3() {
        for(int i=0; i < list.size(); i++){
            LinearLayoutCompat mainView = (LinearLayoutCompat)
                    LayoutInflater.from(getContext()).inflate(layout,null);

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10, 0,0);
            mainView.setLayoutParams(params);

            boolean ok = true;
            View lastView = null;
            float viewWidth = 0;
            while(ok && i < list.size()) {
                ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(layoutText, null);
                BindingUtils.setImageUrl(view, list.get(i).getImage());
/*
                int finalI = i;
                view.setOnClickListener(v -> {
                    if(listener != null) {
                        listener.onBaseCalendarIconClick(view, list.get(finalI));
                    }
                });
*/

                int newHeight = Dimensions.getScreenWidth(getContext()) / 12;
                int orgWidth = view.getDrawable().getIntrinsicWidth();
                int orgHeight = view.getDrawable().getIntrinsicHeight();

                //double check my math, this should be right, though
                int newWidth = (int) Math.floor((orgWidth * newHeight) / orgHeight);

                //Use RelativeLayout.LayoutParams if your parent is a RelativeLayout
                params = new LayoutParams(newWidth, newHeight);
                view.setLayoutParams(params);

                if(lastView == null){
                    lastView = view;
                    mainView.addView(view);
                    viewWidth = Dimensions.getTextViewWeight(view) + rightMargin;
                    i++;
                }
                else {
                    float textSize = Dimensions.getTextViewWeight(view);
                    viewWidth = viewWidth + textSize + rightMargin;
                    int screenWidth = Dimensions.getScreenWidth(getContext());

                    if (viewWidth < screenWidth && ((screenWidth - viewWidth) > rightMargin)) {
                        mainView.addView(view);
                        lastView = view;
                        i++;
/*
                        view.setOnClickListener(v -> {
                            if(listener != null) {
                                listener.onBaseCalendarIconClick(view, list.get(i));
                            }
                        });*/
                    } else {
                        ok = false;
                        i--;
                    }
                }
            }
            this.addView(mainView);
        }
    }

    private void onItemUpdates2() {
        for(int i=0; i < list.size(); i++){
            LinearLayoutCompat mainView = (LinearLayoutCompat)
                    LayoutInflater.from(getContext()).inflate(layout,null);

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10, 0,0);
            mainView.setLayoutParams(params);

            boolean ok = true;
            View lastView = null;
            float viewWidth = 0;
            while(ok && i < list.size()) {
//                TextView view = (TextView) LayoutInflater.from(getContext()).inflate(layoutText, null);
//                view.setText(list.get(i).getCalendarData());
//                ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.base_imageview, null);
                ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(layoutText, null);
                BindingUtils.setImageUrl(view, list.get(i).getImage());

                int finalI = i;
                view.setOnClickListener(v -> {
                    if(listener != null) {
//                        listener.onBaseCalendarIconClick(view, list.get(finalI), date);
                    }
                });

                int newHeight = Dimensions.getScreenWidth(getContext()) / 12;
                int orgWidth = view.getDrawable().getIntrinsicWidth();
                int orgHeight = view.getDrawable().getIntrinsicHeight();

                //double check my math, this should be right, though
                int newWidth = (int) Math.floor((orgWidth * newHeight) / orgHeight);

                //Use RelativeLayout.LayoutParams if your parent is a RelativeLayout
                params = new LayoutParams(newWidth, newHeight);
                params.setMargins(0,0, (int) rightMargin,0);
                view.setLayoutParams(params);

/*
                params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                params.setMargins(0,0, (int) rightMargin,0);
                view.setLayoutParams(params);
*/

                if(lastView == null){
                    lastView = view;
                    mainView.addView(view);
                    viewWidth = Dimensions.getTextViewWeight(view) + rightMargin;
                    i++;
                    /*
                    view.setOnClickListener(v -> {
                        if(listener != null) {
                            listener.onBaseCalendarIconClick(view, list.get(i));
                        }
                    });*/
                }
                else {
                    float textSize = Dimensions.getTextViewWeight(view);
                    viewWidth = viewWidth + textSize + rightMargin;
                    int screenWidth = Dimensions.getScreenWidth(getContext());

                    if (viewWidth < screenWidth && ((screenWidth - viewWidth) > rightMargin)) {
                        mainView.addView(view);
                        lastView = view;
                        i++;
/*
                        view.setOnClickListener(v -> {
                            if(listener != null) {
                                listener.onBaseCalendarIconClick(view, list.get(i));
                            }
                        });*/
                    } else {
                        ok = false;
                        i--;
                    }
                }
            }
            this.addView(mainView);
        }
    }
}
