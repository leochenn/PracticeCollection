package com.leo.demo.scroll_drag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.leo.demo.utils.LogUtil;

/**
 * Created by Leo on 2019/7/11.
 */
public class ViewDragHelperContainerLayout extends LinearLayout {

    private static final String TAG = "ViewDragHelperContainerLayout";

    public ViewDragHelperContainerLayout(@NonNull Context context) {
        this(context, null);
    }

    public ViewDragHelperContainerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperContainerLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        LogUtil.d(TAG, "onInterceptTouchEvent", intercept, ev.getAction());
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean intercept = super.onTouchEvent(event);
        LogUtil.d(TAG, "onTouchEvent", intercept, event.getAction());
        return intercept;
    }
}
