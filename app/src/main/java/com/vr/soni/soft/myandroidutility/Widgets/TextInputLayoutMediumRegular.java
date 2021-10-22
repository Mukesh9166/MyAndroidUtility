package com.vr.soni.soft.myandroidutility.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputLayout;

public class TextInputLayoutMediumRegular extends TextInputLayout {
    public TextInputLayoutMediumRegular(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Medium.ttf"));
    }

    public TextInputLayoutMediumRegular(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Medium.ttf"));
    }

    public TextInputLayoutMediumRegular(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Medium.ttf"));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
