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
import com.maritesallen.almanac2020.utils.util.Dimensions;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 12-12-2019
 */
public class tmp extends LinearLayoutCompat {
    @LayoutRes
    private int layout;
    @LayoutRes
    private int layoutText;
    private float rightMargin = 20f;
    private ArrayList<BaseModelView> list;
    private BaseLinearTextHorizontal.Listener listener;

    public interface Listener{
        void onViewClick(View view, BaseModelView model);
    }

    public void setListener(BaseLinearTextHorizontal.Listener listener){
        this.listener = listener;
    }

    public tmp(Context context) {
        super(context);
        init(context, null);
        list = new ArrayList<>();
    }

    public tmp(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(attrs);
        init(context, attrs);
        list = new ArrayList<>();
    }

    public tmp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(attrs);
        init(context, attrs);
        list = new ArrayList<>();
    }

    public void addItems(List<BaseModelView> model) {
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
            rightMargin = type.getDimension(R.styleable.BaseLinearTextHorizontal_RightMargin,0);
        } finally {
            type.recycle();
        }
    }

    private void init(Context context, AttributeSet attrs) {

    }

    private void onItemUpdates() {
        for(int i=0; i < list.size(); i++){
            LinearLayoutCompat mainView = (LinearLayoutCompat)
                    LayoutInflater.from(getContext()).inflate(layout,null);

            LinearLayoutCompat.LayoutParams params =
                    new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                            LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10, 0,0);
//            mainView.setLayoutParams(params);

            boolean ok = true;
            View lastView = null;
            float viewWidth = 0;
            while(ok && i < list.size()) {
                ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(layoutText, null);
//                view.setImageDrawable(VectorDrawableCompat.create(getResources(), list.get(i).getLayoutId(), null));
                view.setImageDrawable(getResources().getDrawable(list.get(i).getLayoutId()));
//                view.setText(list.get(i).getCalendarData());
                int finalI = i;
                view.setOnClickListener(v -> {
                    if(listener != null) {
//                        listener.onViewClick(view, list.get(finalI));
                    }
                });

                params = new LinearLayoutCompat.LayoutParams(70, 70);
                params.setMargins(0,0, (int) rightMargin,0);
                view.setLayoutParams(params);

                if(lastView == null){
                    lastView = view;
                    mainView.addView(view);
                    viewWidth = Dimensions.getTextViewWeight(view) + rightMargin;
                    i++;
                    /*
                    view.setOnClickListener(v -> {
                        if(listener != null) {
                            listener.onViewClick(view, list.get(i));
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
                                listener.onViewClick(view, list.get(i));
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
