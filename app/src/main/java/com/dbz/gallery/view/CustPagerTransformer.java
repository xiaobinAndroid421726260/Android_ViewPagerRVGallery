package com.dbz.gallery.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class CustPagerTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.8f;
    private static final float MIN_ALPHA = 0.5f;

    private static final float MAX_SCALE = 1.0f;
    private static final float MAX_ALPHA = 1.0f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position < -1) {
            view.setScaleY(MIN_SCALE);
            view.setAlpha(MIN_ALPHA);
        } else if (position == 0) {
            view.setScaleY(MAX_SCALE);
            view.setAlpha(MAX_ALPHA);
        } else if (position <= 1) {
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            float alphaFactor = MIN_ALPHA + (1 - MIN_ALPHA) * (1 - Math.abs(position));
            view.setScaleY(scaleFactor);
            view.setAlpha(alphaFactor);
        } else {
            view.setScaleY(MIN_SCALE);
            view.setAlpha(MIN_ALPHA);
        }
    }
}