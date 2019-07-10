package com.leo.demo.scroll_drag;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
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
public class LeftSlideLayout extends RelativeLayout {
    private static final String TAG = "LeftSlideLayout";

    Button btnLeftSlide;
    DrageType drageType;
    int touchSlop, width, dragSlop;
    int rightMargin = 100;
    float lastRawX, lastRawY;

    public LeftSlideLayout(Context context) {
        super(context);
    }

    public LeftSlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        btnLeftSlide = findViewById(R.id.btn_left_slide);
        btnLeftSlide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), btnLeftSlide.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (dragSlop == 0) {
            width = getWidth();
            dragSlop = width / 2;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            drageType = DrageType.UNKNOWN;
            lastRawX = event.getRawX();
            lastRawY = event.getRawY();
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (drageType == DrageType.UNKNOWN) {
                interceptMove(event.getRawX(), event.getRawY());
            }
        }
        return drageType == DrageType.LEFT;
    }

    private void interceptMove(float rawX, float rawY) {
        float xDist = rawX - lastRawX;
        float yDist = rawY - lastRawY;
        if (Math.abs(xDist) >= Math.abs(yDist)) {
            if (touchSlop < Math.abs(xDist)) {
                if (xDist <= 0) {
                    if (lastRawX > getWidth() - rightMargin) {
                        drageType = DrageType.LEFT;
                    } else {
                        drageType = DrageType.INVALID;
                    }
                } else {
                    drageType = DrageType.RIGHT;
                }
            }
        } else {
            if (touchSlop < Math.abs(yDist)) {
                drageType = DrageType.VERTICAL;
            }
        }

        if (drageType == DrageType.LEFT) {
            dragLeft(rawX);
        }
    }

    private void dragLeft(float rawX) {
        if (rawX - lastRawX <= 0) {
            int distance = (int) (rawX - lastRawX) * 4 / 5;
            if (Math.abs(distance) <= width) {
                setTranslationX(distance);
            } else {
                if (Math.abs(distance) > width && Math.abs(getTranslationX()) < width) {
                    setTranslationX(-width);
                }
            }
        } else {
            setTranslationX(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_MOVE) {
            if (drageType == DrageType.LEFT) {
                dragLeft(event.getRawX());
            }
        } else if (action == MotionEvent.ACTION_UP | action == MotionEvent.ACTION_CANCEL) {
            if (drageType == DrageType.LEFT) {
                float xDist = event.getRawX() - lastRawX;
                if (xDist < 0 && Math.abs(xDist) < width) {
                    translateAnim(getTranslationX(), Math.abs(xDist));
                }
            }
        }
        return true;
    }

    private void translateAnim(float curTranslationX, float xDist) {
        long duration;
        float dst;
        if (xDist < dragSlop) {
            duration = (long) (xDist * 500 / width);
            dst = 0.0f;
        } else {
            duration = (long) ((width - xDist) * 500 / width);
            dst = -width;
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationX", curTranslationX,
                dst);
        animator.setDuration(duration);
        animator.start();
    }

    public enum DrageType {
        UNKNOWN, INVALID, LEFT, RIGHT, VERTICAL
    }
}
