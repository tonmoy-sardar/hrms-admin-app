package com.sft.hrmsadmin.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by ncrts on 13/9/17.
 */

public class GothiciBoldTextView extends AppCompatTextView {

    public GothiciBoldTextView(Context context) {
        super(context);
        this.setTypeface(getTypeface(), getTypeface().getStyle());
    }

    public GothiciBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(getTypeface(), getTypeface().getStyle());

    }

    public GothiciBoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface tf = getTypeface();
        this.setTypeface(getTypeface(), getTypeface().getStyle());
    }


    @Override
    public void setTypeface(Typeface tf, int style) {
        System.out.println();
        if (style == 1) {
            //replace "HelveticaBOLD.otf" with the name of your bold font
            tf = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "GOTHICB.TTF");
            //tf = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "Montserrat_Bold.ttf");
        }
        else {
            //replace "HelveticaNORMAL.otf" with the name of your normal font
            tf = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "GOTHICB.TTF");
            //tf = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(), "Montserrat_Bold.ttf");
        }
        super.setTypeface(tf, 0);
    }
    //avenirltstd-black
}
