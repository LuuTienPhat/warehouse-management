package com.example.warehousemanagement.animations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.warehousemanagement.R;

public class ScaleAnimation {

    private Context context;
    private Animation scaleDown, scaleUp;

    public ScaleAnimation(Context context) {
        this.context = context;
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
    }

    @SuppressLint("ClickableViewAccessibility")
    public Button attachAnimation(Button button) {

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    button.startAnimation(scaleDown);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    button.startAnimation(scaleUp);
                }
                return false;
            }
        });

        return button;
    }

    @SuppressLint("ClickableViewAccessibility")
    public ImageButton attachAnimation(ImageButton imageButton) {

        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imageButton.startAnimation(scaleDown);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton.startAnimation(scaleUp);
                }
                return false;
            }
        });

        return imageButton;
    }

    @SuppressLint("ClickableViewAccessibility")
    public LinearLayout attachAnimation(LinearLayout layout) {

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    layout.startAnimation(scaleDown);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    layout.startAnimation(scaleUp);
                }
                return false;
            }
        });

        return layout;
    }
}
