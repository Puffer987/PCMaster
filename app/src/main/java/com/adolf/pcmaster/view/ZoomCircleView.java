package com.adolf.pcmaster.view;

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
import java.util.regex.Pattern;

/**
 * @program: PCMaster
 * @description:
 * @author: Adolf
 * @create: 2020-08-13 15:17
 **/
public class ZoomCircleView extends View {
    private final String TAG = "[jq]ZoomCircle";
    private int mWidth, mHeight; // 控件的整体宽高
    private Paint circlePaint;
    private Timer timer;
    private boolean isTight;
    private float mRadius, mMaxRadius, mMinRadius;// 圆半径和其范围
    private float mOrgTotalSeconds;

    private boolean isFinishACycle;
    // private int mCount; // 循环次数
    // private long mTight, mTightHold, mLoose, mLooseHold;// 振动频率
    private long[] mPattern = {0, 0, 0, 0};
    private int curIndex;
    private boolean isClick;

    public ZoomCircleView(Context context) {
        this(context, null);
    }

    public ZoomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        // timeLooper();
    }

    private void init() {
        isTight = true;

        curIndex = -1; // 保证正在执行时，再次点击触发更新操作

        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#3399CC"));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

    }

    public void setModelAndLoop(String model, int loop) {
        try {
            loop = Math.max(1, loop);
            mPattern = str2long(model, loop);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        int min = Math.min(mWidth, mHeight);
        // mHeight = min;
        // mWidth = min;
        // mMaxRadius = (min >> 1) - (min >> 3);
        // mMinRadius = min >> 3;
        mMaxRadius = (float) Math.sqrt(mWidth * mWidth + mHeight * mHeight) / 2;
        mMinRadius = min >> 3;
        mRadius = mMaxRadius;
        mOrgTotalSeconds = ((mMaxRadius - mMinRadius) / 60.0f) * 1000;
        // setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isClick) {
            if (!isFinishACycle) {
                updateRadius(mPattern[curIndex], mPattern[curIndex + 1], mPattern[curIndex + 2], mPattern[curIndex + 3]);

            } else if (curIndex + 4 < mPattern.length) {
                curIndex += 4;
                isFinishACycle = false; // 继续update
            } else {
                isClick = false;// 退出
                curIndex = -1; // 循环结束，再次设为负数
                Log.d(TAG, "fishing...  mFinishListener: "+(mFinishListener != null));
                if (mFinishListener != null)
                    mFinishListener.showInfo();
            }
            invalidate();
        }

        canvas.drawCircle(mWidth >> 1, mHeight >> 1, mRadius, circlePaint);
    }

    private void updateRadius(long tight, long tightHold, long loose, long looseHold) {
        if (isTight) {
            if (mRadius > mMinRadius) {
                mRadius = Math.max(mRadius - (mOrgTotalSeconds / tight), mMinRadius);
            } else if (mRadius == mMinRadius) {
                isTight = false;
                try {
                    Thread.sleep(tightHold);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (mRadius < mMaxRadius) {
                mRadius = Math.min(mRadius + (mOrgTotalSeconds / loose), mMaxRadius);
            } else if (mRadius == mMaxRadius) {
                isTight = true;
                isFinishACycle = true;
                try {
                    Thread.sleep(looseHold);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "down");
                // break;
            case MotionEvent.ACTION_UP:
                if (curIndex < 0) {
                    Log.d(TAG, "up");
                    isTight = true;
                    isFinishACycle = false;
                    isClick = true;
                    curIndex = 0;
                    invalidate();
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    public long[] getPattern() {
        return mPattern;
    }

    // 以下三个是为了向外界传递循环结束的信息
    public interface OnFinishListener {
        void showInfo();
    }

    private OnFinishListener mFinishListener;

    public void setFinishListener(OnFinishListener finishListener) {
        mFinishListener = finishListener;
    }

    private long[] str2long(String inStr, int loop) throws Exception {
        String sumStr = "";
        for (int i = 0; i < loop; i++) {
            sumStr = sumStr + " " + inStr;
        }

        Log.d(TAG, "str2long: sumStr" + sumStr);

        String[] s = sumStr.trim().split(" ");

        if (s.length % 4 != 0) {

        }
        long[] patten = new long[s.length + s.length % 4];

        for (int i = 0; i < s.length; i++) {
            Pattern pattern = Pattern.compile("[0-9]*");
            boolean isNum = pattern.matcher(s[i]).matches();
            if (!isNum) {
                throw new Exception();
            }
            patten[i] = Long.valueOf(s[i]);
        }
        for (int i = 0; i < s.length % 4; i++) {
            patten[s.length + i] = 0;
        }
        patten[patten.length - 1] = 0; // 最后一个停顿不要

        Log.d(TAG, "length：" + patten.length);
        return patten;
    }
}
