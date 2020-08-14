package com.adolf.pcmaster;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @program: PCMaster
 * @description:
 * @author: Adolf
 * @create: 2020-08-13 15:17
 **/
public class ZoomCircleView extends View implements View.OnClickListener {

    private int mWidth;
    private int mHeight;
    private float mRadius;
    private Paint circlePaint;
    private Timer timer;
    private boolean isTight;
    private float mMaxRadius;
    private float mMinRadius;
    private float mOrgTotalSeconds;
    private final float HOLD_RATIO = 0.6F;
    private final String TAG = "[jq]ZoomCircle";
    private boolean isFinishCycle;
    private int mCount;

    public ZoomCircleView(Context context) {
        this(context, null);
    }

    public ZoomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        timeLooper();
        setOnClickListener(this);
    }

    private void init() {

        mCount = 2;

        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#99CC99"));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

    }

    private void timeLooper() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                Log.v(TAG, "time: " + i + ", radius: " + mRadius);
                i++;
            }
        }, 0, 1000);
        // delay : 从定时器初始化成功 开始启动 的延迟时间。
        // period：定时器的间隔时间。
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mMaxRadius = Math.min(mWidth, mHeight) / 2;
        mMinRadius = Math.min(mWidth, mHeight) / 10;
        // mMaxRadius = 300;
        mRadius = mMaxRadius;
        mOrgTotalSeconds = ((mMaxRadius - mMinRadius) / 60.0f) * 1000;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, circlePaint);
        if (isFinishCycle) {
            updateRadius(2000, 3000);
            invalidate();
            if (mCount < 0) {
                isFinishCycle = false;
            }
        }
        // updateRadius(3000, 5000);
        // invalidate();
    }


    private void updateRadius(int tight, int loose) {
        if (isTight) {
            mRadius -= mOrgTotalSeconds / (tight * HOLD_RATIO);
            if (mRadius < mMinRadius) {

                isTight = false;
                mRadius = mMinRadius;

                try {
                    Thread.sleep((long) (tight * (1 - HOLD_RATIO)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            mRadius += mOrgTotalSeconds / (loose * HOLD_RATIO);
            if (mRadius > mMaxRadius) {

                isTight = true;
                // isFinishCycle = false;
                mCount--;
                mRadius = mMaxRadius;

                try {
                    Thread.sleep((long) (loose * (1 - HOLD_RATIO)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        // updateRadius(5000, 1500);
        // invalidate();
        isFinishCycle = true;
        invalidate();

    }
}
