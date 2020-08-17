package com.adolf.pcmaster;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrainingActivity extends AppCompatActivity {

    @BindView(R.id.bModel)
    EditText mBModel;
    @BindView(R.id.bLoop)
    EditText mBLoop;
    @BindView(R.id.eModel)
    EditText mEModel;
    @BindView(R.id.eLoop)
    EditText mELoop;
    @BindView(R.id.gModel)
    EditText mGModel;
    @BindView(R.id.gLoop)
    EditText mGLoop;

    private String model = "";
    private String loop = "0";
    private Vibrator mVib;
    private FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
        //         WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_training);
        ButterKnife.bind(this);

        mVib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);


        init();
    }

    private void init() {
        mBModel.setText("1500 1500 1500 1500");
        mBLoop.setText("10");
        mEModel.setText("3000 3000 3000 3000");
        mELoop.setText("5");
        mGModel.setText("3000 500 1000 500 1000 2000 1000 500 1000 500");
        mGLoop.setText("5");
    }

    @OnClick(R.id.btn_stop)
    public void onStopBtn(View view) {
        mVib.vibrate(new long[]{200, 200}, -1);
        mVib.cancel();
    }

    @OnClick({R.id.bStart, R.id.eStart, R.id.gStart})
    public void onViewClicked(View view) {
        Intent i = new Intent(this, VibrateService.class);
        switch (view.getId()) {
            case R.id.bStart:
                model = mBModel.getText().toString();
                loop = mBLoop.getText().toString();
                i.putExtra("model", model);
                i.putExtra("loop", loop);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.fragment_train_activity,new VibrateFragment().newInstance(model,loop)).commit();

                break;
            case R.id.eStart:
                model = mEModel.getText().toString();
                loop = mELoop.getText().toString();
                i.putExtra("model", model);
                i.putExtra("loop", loop);
                break;
            case R.id.gStart:
                model = mGModel.getText().toString();
                loop = mGLoop.getText().toString();
                i.putExtra("model", model);
                i.putExtra("loop", loop);
                break;
        }
        // startService(i);


        // 屏幕常亮
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