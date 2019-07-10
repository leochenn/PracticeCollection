package com.leo.demo.scroll_drag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.leo.demo.R;
import com.leo.demo.utils.LogUtil;

/**
 * Created by Leo on 2019/7/10.
 */
public class ViewDragHelperLayout extends FrameLayout {

    private static final String TAG = "ViewDragHelperLayout";

    private ViewDragHelper mViewDragHelper;
    private View mMenuView;
    private MarginLayoutParams marginLayoutParams;

    public ViewDragHelperLayout(@NonNull Context context) {
        this(context, null);
    }

    public ViewDragHelperLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragCallback());
        //这个必须进行设置，否则就边界拖动的回调方法就不能使用
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_RIGHT);
//        mViewDragHelper.setMinVelocity(400/1080);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        marginLayoutParams = (MarginLayoutParams) mMenuView.getLayoutParams();
        mMenuView.layout(-(mMenuView.getMeasuredWidth() - marginLayoutParams.rightMargin), marginLayoutParams.topMargin,
                -marginLayoutParams.rightMargin, marginLayoutParams.topMargin + mMenuView.getMeasuredHeight());
    }

    public void openDrawer() {
        mViewDragHelper.smoothSlideViewTo(mMenuView, marginLayoutParams.leftMargin, mMenuView.getTop());
        invalidate();
    }

    public void closeDrawer() {
        mViewDragHelper.smoothSlideViewTo(mMenuView,
                -(mMenuView.getMeasuredWidth() + marginLayoutParams.rightMargin), mMenuView.getTop());
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = findViewById(R.id.ll_menu);
        LogUtil.d(TAG, mMenuView);
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {

        // 何时进行检测触摸事件, 判断当前滑动的view是不是目标view;
        // 该回调在触摸边界时不生效
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            LogUtil.d(TAG, "tryCaptureView", child);
            return mMenuView == child;
        }

        //拖动结束后使用
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            LogUtil.d(TAG, "onViewReleased", releasedChild);
            int left = releasedChild.getLeft();
            //显示出来
            if (left > -releasedChild.getMeasuredWidth() / 2) {
                //这里的源码和  mViewDragHelper.smoothSlideViewTo()其实差不多
                mViewDragHelper.settleCapturedViewAt(marginLayoutParams.leftMargin, releasedChild.getTop());
            } else {//隐藏
                mViewDragHelper.settleCapturedViewAt(-(releasedChild.getMeasuredWidth() + marginLayoutParams.rightMargin), releasedChild.getTop());
            }
            invalidate();
        }

        // 仅在触摸设置的边界才会有回调, 如当前未设置右边界,则触摸右边界不会回调
        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            LogUtil.d(TAG, "onEdgeTouched", edgeFlags);
        }

        //这里需要调用setEdgeTrackingEnabled()后才能回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            LogUtil.d(TAG, "onEdgeDragStarted", edgeFlags);
            //进行制定view里面会调用接口onViewCaptured()，然后将View回调出来
            mViewDragHelper.captureChildView(mMenuView, pointerId);
        }

        //这个是如果设置点击事件后，能够拖动里面view,否则就不能点击
        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }

        //处理横向滑动
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            LogUtil.d(TAG, "clampViewPositionHorizontal", left, dx, child);
            if (left >= 0) {
                left = 0;
            } else if (left < -(marginLayoutParams.rightMargin + mMenuView.getMeasuredWidth())) {
                left = marginLayoutParams.rightMargin + mMenuView.getMeasuredWidth();
            }
            return left;
        }

        //处理纵向滑动, 返回0则不处理
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }
    }
}
