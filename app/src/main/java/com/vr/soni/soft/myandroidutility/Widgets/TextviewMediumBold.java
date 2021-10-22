package com.vr.soni.soft.myandroidutility.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class TextviewMediumBold extends AppCompatTextView {
    public TextviewMediumBold(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "ubuntu_medium.ttf"));
    }

    public TextviewMediumBold(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "ubuntu_medium.ttf"));
    }

    public TextviewMediumBold(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "ubuntu_medium.ttf"));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
