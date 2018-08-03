package com.example.djd.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by djd on 18-8-3.
 */

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtnStart;
    private TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animtion);
        mBtnStart = findViewById(R.id.btn_animation);
        mBtnStart.setOnClickListener(this);
        mTv = findViewById(R.id.tv_say_hello);

    }

    @Override
    public void onClick(View v) {
        startAnimation();
    }

    private void startAnimation() {
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(mTv, "translationX", -500f, 0f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mTv, "rotation", 0f,360f);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(mTv, "alpha",1f,0f,1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotation).with(fadeOut).after(moveIn);
        animatorSet.setDuration(5000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
