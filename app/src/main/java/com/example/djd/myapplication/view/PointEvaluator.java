package com.example.djd.myapplication.view;

import android.animation.TypeEvaluator;


/**
 * Created by djd on 18-8-3.
 */

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point starPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = starPoint.x + fraction * (endPoint.x - starPoint.x);
        float y = starPoint.x + fraction * (endPoint.y - starPoint.y);
        return new Point(x, y);
    }
}
