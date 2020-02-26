package com.company.akilovasi.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import kotlin.jvm.JvmOverloads;

public class DrawerContent extends MotionLayout implements DrawerLayout.DrawerListener {

    Context context;

    AttributeSet attrs = null;

    int defStyleAttr = 0;

    @JvmOverloads
    public DrawerContent(Context context) {
        super(context);
        this.context = context;
    }

    @JvmOverloads
    public DrawerContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
    }

    @JvmOverloads
    public DrawerContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        setProgress(slideOffset);
//        progress = slideOffset
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((DrawerLayout)getParent()).addDrawerListener(this);
    }
}