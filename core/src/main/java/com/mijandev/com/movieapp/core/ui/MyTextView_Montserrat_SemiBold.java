package com.mijandev.com.movieapp.core.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Mohammad Hamizan on 30/1/2020.
 */
/**
 * Custom Text with Montserrat font semi bold
 */
public class MyTextView_Montserrat_SemiBold extends AppCompatTextView {

    public MyTextView_Montserrat_SemiBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView_Montserrat_SemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView_Montserrat_SemiBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_semibold.ttf");
            setTypeface(tf);
        }
    }
}