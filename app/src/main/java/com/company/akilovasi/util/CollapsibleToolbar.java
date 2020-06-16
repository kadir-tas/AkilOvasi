package com.company.akilovasi.util;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.google.android.material.appbar.AppBarLayout;

import kotlin.jvm.JvmOverloads;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CollapsibleToolbar extends MotionLayout implements AppBarLayout.OnOffsetChangedListener {

    Context context;

    AttributeSet attrs = null;

    int defStyleAttr = 0;

    @JvmOverloads
    public CollapsibleToolbar(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
    }

    @JvmOverloads
    public CollapsibleToolbar(Context context) {
        super(context);
        this.context = context;
    }

    @JvmOverloads
    public CollapsibleToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        setProgress((-verticalOffset)/(float)appBarLayout.getTotalScrollRange());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((AppBarLayout)getParent()).addOnOffsetChangedListener(this);
    }
}
