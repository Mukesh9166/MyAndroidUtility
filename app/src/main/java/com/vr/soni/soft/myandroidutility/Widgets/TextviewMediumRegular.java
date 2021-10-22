package com.vr.soni.soft.myandroidutility.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class TextviewMediumRegular extends AppCompatTextView {
    public TextviewMediumRegular(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "ubuntu_regular_ttf.ttf"));
    }

    public TextviewMediumRegular(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "ubuntu_regular_ttf.ttf"));
    }

    public TextviewMediumRegular(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "ubuntu_regular_ttf.ttf"));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
