package com.mijandev.com.movieapp.core.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

/**
 * Created by Mohammad Hamizan on 1/2/2020.
 */
/**
 * Custom Button with Montserrat font regular
 */
public class MyButton_Montserrat_Regular extends AppCompatButton {

    public MyButton_Montserrat_Regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyButton_Montserrat_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton_Montserrat_Regular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_regular.ttf");
            setTypeface(tf);
        }
    }
}