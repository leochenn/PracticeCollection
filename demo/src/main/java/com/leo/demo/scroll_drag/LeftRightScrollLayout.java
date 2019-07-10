package com.leo.demo.scroll_drag;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leo.demo.R;

/**
 * Created by ZengLei on 2019/7/4.
 */
public class LeftRightScrollLayout extends RelativeLayout {

    private static final String TAG = "LeftRightScrollLayout";

    View flScrollLeft, flScrollRight;
    Button btnLeftRightScroll;
    int flScrollLeftWidth, flScrollRightWidth;
    float flScrollLeftLastX, flScrollRightLastX;
    float lastX, lastY;
    int touchSlop;
    ScrollType scrollType;

    public LeftRightScrollLayout(Context context) {
        super(context);
    }

    public LeftRightScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        flScrollLeft = findViewById(R.id.fl_scroll_left);
        flScrollRight = findViewById(R.id.fl_scroll_right);
        btnLeftRightScroll = findViewById(R.id.btn_left_right_scroll);
        btnLeftRightScroll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), btnLeftRightScroll.getText(), Toast.LENGTH_LONG).show();
            }
        });

        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        flScrollRight.layout(width, flScrollRight.getTop(), width + flScrollRight.getWidth(),
                flScrollRight.getBottom());

        flScrollLeftWidth = flScrollLeft.getWidth();
        flScrollRightWidth = flScrollRight.getWidth();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            scrollType = ScrollType.UNKNOWN;
            lastX = event.getX();
            lastY = event.getY();
            flScrollLeftLastX = flScrollLeft.getX();
            flScrollRightLastX = flScrollRight.getX();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float xDist = event.getX() - lastX;
            float yDist = event.getY() - lastY;

            if (Math.abs(xDist) >= Math.abs(yDist)) {
                // 水平方向移动
                if (xDist > 0) {
                    // 向右移动
                    if (touchSlop < Math.abs(xDist)) {
                        // 已经达到移动的最小距离
                        if (scrollType == ScrollType.UNKNOWN) {
                            scrollType = ScrollType.RIGHT;
                        }
                    }
                } else {
                    // 向左移动
                    if (touchSlop < Math.abs(xDist)) {
                        if (scrollType == ScrollType.UNKNOWN) {
                            scrollType = ScrollType.LEFT;
                        }
                    }
                }
            } else {
                if (scrollType == ScrollType.UNKNOWN) {
                    scrollType = ScrollType.VERTICAL;
                }
            }

            if (scrollType == ScrollType.RIGHT) {
                if (xDist > 0) {
                    int distance = (int) (event.getX() - lastX) * 2 / 3;
                    if (distance <= flScrollLeftWidth) {
                        flScrollLeft.setTranslationX(distance);
                    } else {
                        if (distance > flScrollLeftWidth && flScrollLeft.getTranslationX() < flScrollLeftWidth) {
                            flScrollLeft.setTranslationX(flScrollLeftWidth);
                        }
                    }
                } else {
                    flScrollLeft.setTranslationX(0);
                }
            }
            if (scrollType == ScrollType.LEFT) {
                if (xDist < 0) {
                    int distance = (int) (event.getX() - lastX) * 2 / 3;
                    if (Math.abs(distance) <= flScrollRightWidth) {
                        flScrollRight.setTranslationX(distance);
                    } else {
                        if (Math.abs(distance) > flScrollRightWidth && Math.abs(flScrollRight.getTranslationX()) < flScrollRightWidth) {
                            flScrollRight.setTranslationX(-flScrollRightWidth);
                        }
                    }
                } else {
                    flScrollRight.setTranslationX(0);
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (scrollType == ScrollType.RIGHT) {
                float xx = flScrollLeft.getX() - flScrollLeftLastX;
                float xxx = event.getX() - lastX;
                log("xx:" + xx + ",, xxx:" + xxx);
                if (xx > 0) {
                    float curTranslationX = flScrollLeft.getTranslationX();
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(flScrollLeft, "translationX",
                            curTranslationX, 0.0f);
                    animator.setDuration((long) (xx * 500 / flScrollLeftWidth));
                    animator.start();
                }
            }
            if (scrollType == ScrollType.LEFT) {
                if (flScrollRight.getX() < flScrollRightLastX) {
                    float curTranslationX = flScrollRight.getTranslationX();
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(flScrollRight, "translationX",
                            curTranslationX, 0.0f);
                    animator.setDuration(Math.abs((long) ((flScrollRight.getX() - flScrollRightLastX) * 500 / flScrollRightWidth)));
                    animator.start();
                }
            }
        }
        return true;
    }

    public enum ScrollType {
        UNKNOWN, VERTICAL, LEFT, RIGHT
    }
}
