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

    private final float mShrinkAmount = 0.07f;
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

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

//        if(!isPaddingSet){
//            isPaddingSet = true;
//            float scale = recyclerView.getResources().getDisplayMetrics().density;
//            int dpAsPixels = (int) (((recyclerView.getHeight()/4))*scale + 0.5f);
//            recyclerView.setPadding(0,dpAsPixels,0,dpAsPixels/2);
//        }
        int scrolled = super.scrollVerticallyBy(dy, recycler, state);
        float midpoint = getHeight() / 1.5f;
        float d0 = 0.1f;
        float d1 = mShrinkDistance * midpoint * 2;
        //S0 and S1 represent the Point of view of items (small value of s0 means looking to items from a more upright perspective)
        float s0 = 0.7f;
        float s1 = -0.1f - mShrinkAmount;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
//            if (computeVerticalScrollOffset(state) == 0) {
//              0th item
//            }
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
        }
        return scrolled;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {

      /*  if(!isPaddingSet){
            isPaddingSet = true;
            float scale = recyclerView.getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (((recyclerView.getHeight()/3))*scale + 0.5f);
            recyclerView.setPadding(0,dpAsPixels*4,0,dpAsPixels);
        }*/
        float midpoint = getHeight() / 1.5f;
        float d0 = 0.1f;
        float d1 = mShrinkDistance * midpoint * 2;
        float s0 = 0.7f;
        float s1 = -0.1f - mShrinkAmount;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            float childMidpoint = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.2f;
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
        }
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