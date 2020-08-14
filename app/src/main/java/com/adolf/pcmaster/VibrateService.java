package com.adolf.pcmaster;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;

import java.util.regex.Pattern;

public class VibrateService extends Service {

    private static final String TAG = "[jq]VibrateService";
    private VibrateBinder mVibrateBinder;
    private Vibrator mVib;

    public VibrateService() {
    }

    public class VibrateBinder extends Binder {
        VibrateService getService() {
            return VibrateService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mVibrateBinder = new VibrateBinder();
        mVib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        String model = intent.getStringExtra("model");
        int loop = Integer.parseInt(intent.getStringExtra("loop"));

        Log.d(TAG, "model: " + model + ", loop: " + loop);

        try {
            long[] pattern = str2long(model, loop);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            mVib.vibrate(pattern, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mVibrateBinder;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    private long[] str2long(String inStr, int loop) throws Exception {
        String sumStr = "";
        for (int i = 0; i < loop; i++) {
            sumStr = sumStr + " " + inStr;
        }

        String[] s = sumStr.trim().split(" ");
        long[] patten = new long[s.length];

        for (int i = 0; i < s.length; i++) {
            Pattern pattern = Pattern.compile("[0-9]*");
            boolean isNum = pattern.matcher(s[i]).matches();
            if (!isNum) {
                throw new Exception();
            }
            patten[i] = Long.valueOf(s[i]);
        }

        return patten;
    }
}
