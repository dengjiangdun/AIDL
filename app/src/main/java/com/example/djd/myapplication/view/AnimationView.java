package com.example.djd.myapplication.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by djd on 18-8-3.
 */

public class AnimationView extends View {
    private static final float RADIUS = 50f;
    private Point currentPoint;
    private Paint mPaint;
    private String color;

    public AnimationView(Context context, AttributeSet attrs) {
        super(context,attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }


    public AnimationView(Context context) {
        super(context,null);
    }

    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);

    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        currentPoint = new Point(event.getX(), event.getY());
        invalidate();
        return super.onDragEvent(event);
    }

    @Override
    public void setOnDragListener(OnDragListener l) {
        super.setOnDragListener(l);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(currentPoint.getX(), currentPoint.getY(), RADIUS, mPaint);
    }

    private void startAnimation() {
       /* Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);*/

       Point startPoint = new Point(getWidth() /2, RADIUS);
       Point endPoint = new Point(getWidth() / 2, getHeight() / 2 - RADIUS);

        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this,"color",
                                        new ColorEvaluator(),"#0000FF","#FF0000");
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.play(animator).with(objectAnimator);
        animatorSet.setDuration(5000);
        animatorSet.start();
        //animator.setDuration(5000);
        //animator.start();
    }


}
