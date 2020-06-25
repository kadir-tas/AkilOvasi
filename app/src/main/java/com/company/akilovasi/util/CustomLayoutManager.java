package com.company.akilovasi.util;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import kotlin.jvm.JvmOverloads;

import static androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_START;

public class CustomLayoutManager extends GridLayoutManager {
    private static final String TAG = "CustomLayoutManager";

    private final float mShrinkAmount = 0.01f;
    private final float mShrinkDistance = 2.3f;

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

    /**
     * Cycles though each child and places them to their correct position
     * @param shrinkAmount
     * @param shrinkDistance
     */
    private void setChildPositions( float shrinkAmount, float shrinkDistance, boolean changeAlpha){
        float midpoint = getHeight() / 1.5f;
        float d0 = 0.1f;
        float d1 = shrinkDistance * midpoint * 2;
        //S0 and S1 represent the Point of view of items (small value of s0 means looking to items from a more upright perspective)
        float s0 = 0.7f;
        float s1 = -0.1f - shrinkAmount;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float childMidpoint =
                    (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.2f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
            if(scale < 0){
                scale = 0;
            }
            child.setScaleX(scale*1.35f);
            child.setScaleY(scale*1.35f);
            if (i % 2 == 0) child.setPivotX((getWidth() / 2.f) - scale);
            else child.setPivotX(scale/2);
            child.setPivotY(getHeight()/1.3f);

            if(changeAlpha){
                float alpha = (child.getY() / recyclerView.getHeight()) * 10;
                child.setAlpha( alpha );
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

//        if(!isPaddingSet){
//            isPaddingSet = true;
//            float scale = recyclerView.getResources().getDisplayMetrics().density;
//            int dpAsPixels = (int) (((recyclerView.getHeight()/4))*scale + 0.5f);
//            recyclerView.setPadding(0,dpAsPixels,0,dpAsPixels/2);
//        }
        int scrolled = super.scrollVerticallyBy(dy, recycler, state);
        setChildPositions(mShrinkAmount, mShrinkDistance,true);
        return scrolled;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        // if(!isPaddingSet) is used to calculate element height so that it fits the recyclerview,
        // so that elements are always shown when we used cool perspective effect but we are not using currently so bellow code is not necessary
        // NOTE THAT, if used good padding value with good mShrinkAmount and mShrinkDistance parameter we get cool looking perspective slide effect.

        if(!isPaddingSet){
            isPaddingSet = true;
            float scale = recyclerView.getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (((recyclerView.getHeight()/3))*scale );
            recyclerView.setPadding(0,dpAsPixels,0,dpAsPixels / 3);
        }
        setChildPositions(mShrinkAmount , mShrinkDistance, true);
        super.onLayoutCompleted(state);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        recyclerView = view;
        recyclerView.smoothScrollToPosition(0);
        super.onAttachedToWindow(view);
    }

//    @Override
//    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        int orientation = getOrientation();
//        if (orientation == HORIZONTAL) {
//            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
//
//            float midpoint = getWidth() / 2.f;
//            float d0 = 0.f;
//            float d1 = mShrinkDistance * midpoint;
//            float s0 = 1.f;
//            float s1 = 1.f - mShrinkAmount;
//            for (int i = 0; i < getChildCount(); i++) {
//                View child = getChildAt(i);
//                float childMidpoint =
//                        (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
//                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
//                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
//                child.setScaleX(scale);
//                child.setScaleY(scale);
//            }
//            return scrolled;
//        } else {
//            return 0;
//        }
//    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                       int position) {
        RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class TopSnappedSmoothScroller extends LinearSmoothScroller {
        public TopSnappedSmoothScroller(Context context) {
            super(context);

        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return CustomLayoutManager.this
                    .computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }
}