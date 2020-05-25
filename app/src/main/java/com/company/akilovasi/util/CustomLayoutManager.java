package com.company.akilovasi.util;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kotlin.jvm.JvmOverloads;

public class CustomLayoutManager extends GridLayoutManager {
    private static final String TAG = "CustomLayoutManager";

    private final float mShrinkAmount = 0.35f;
    private final float mShrinkDistance = 1.f;

    private RecyclerView recyclerView;
    private boolean isPaddingSet = false;

    @JvmOverloads
    public CustomLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @JvmOverloads
    public CustomLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        if(!isPaddingSet){
            isPaddingSet = true;
            float scale = recyclerView.getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (((recyclerView.getHeight()/4))*scale + 0.5f);
            recyclerView.setPadding(0,dpAsPixels,0,0);
        }
        int scrolled = super.scrollVerticallyBy(dy, recycler, state);
        float midpoint = getHeight() / 1.05f;
        float d0 = 0.f;
        float d1 = mShrinkDistance * midpoint * 2;
        float s0 = 1.f;
        float s1 = 0.f - mShrinkAmount;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
//            if (computeVerticalScrollOffset(state) == 0) {
//
//            }
            float childMidpoint =
                    (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
            child.setScaleX(scale);
            child.setScaleY(scale);
            if (i % 2 == 0) child.setPivotX((getWidth() / 2) - scale);
            else child.setPivotX(scale);
            child.setPivotY(getHeight());
        }
        return scrolled;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {

        float midpoint = getHeight() / 1.05f;
        float d0 = 0.f;
        float d1 = mShrinkDistance * midpoint * 2;
        float s0 = 1.f;
        float s1 = 0.f - mShrinkAmount;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            float childMidpoint = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);

            child.setScaleX(scale);
            child.setScaleY(scale);

            if (i % 2 == 0) child.setPivotX((getWidth() / 2.f) - scale);
            else child.setPivotX(scale);

            child.setPivotY(getHeight());
        }
        super.onLayoutCompleted(state);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        recyclerView = view;
        super.onAttachedToWindow(view);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == HORIZONTAL) {
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);

            float midpoint = getWidth() / 2.f;
            float d0 = 0.f;
            float d1 = mShrinkDistance * midpoint;
            float s0 = 1.f;
            float s1 = 1.f - mShrinkAmount;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                float childMidpoint =
                        (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
            }
            return scrolled;
        } else {
            return 0;
        }

    }
}