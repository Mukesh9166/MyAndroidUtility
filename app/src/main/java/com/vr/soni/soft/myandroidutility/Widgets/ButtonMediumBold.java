package com.vr.soni.soft.myandroidutility.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class ButtonMediumBold extends AppCompatButton {
    public ButtonMediumBold(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-SemiBold.ttf"));
    }

    public ButtonMediumBold(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-SemiBold.ttf"));
    }

    public ButtonMediumBold(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-SemiBold.ttf"));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
