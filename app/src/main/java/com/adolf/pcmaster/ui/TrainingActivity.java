package com.adolf.pcmaster.ui;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adolf.pcmaster.R;
import com.adolf.pcmaster.VibrateService;
import com.adolf.pcmaster.adapter.TrainingAdapter;
import com.adolf.pcmaster.model.TrainingItemBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrainingActivity extends AppCompatActivity {

    private static final String TAG = "[jq]Training";
    private static final String CHECK_MORNING = "checkMorning";
    private static final String CHECK_AFTERNOON = "checkAfternoon";
    private static final String CHECK_NIGHT = "checkNight";
    private static final String COUNT_DAYS = "countDays";
    @BindView(R.id.bModel)
    TextView mBModel;
    @BindView(R.id.bLoop)
    TextView mBLoop;
    @BindView(R.id.eModel)
    TextView mEModel;
    @BindView(R.id.eLoop)
    TextView mELoop;
    @BindView(R.id.gModel)
    TextView mGModel;
    @BindView(R.id.gLoop)
    TextView mGLoop;
    @BindView(R.id.group_main)
    LinearLayout mGroupMain;
    @BindView(R.id.tv_days)
    TextView mTvDays;
    @BindView(R.id.btn_morning)
    Button mBtnMorning;
    @BindView(R.id.btn_afternoon)
    Button mBtnAfternoon;
    @BindView(R.id.btn_night)
    Button mBtnNight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_trainings)
    RecyclerView mRvTrainings;

    private String model = "";
    private String loop = "0";
    private Vibrator mVib;
    private FragmentManager fm;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private List<TrainingItemBean> mTrainings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
        //         WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_training);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        init();
    }

    private void init() {
        mVib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

        mTrainings.add(new TrainingItemBean("1500 1500 1500 1500", 10, "爆发1.5"));
        mTrainings.add(new TrainingItemBean("3000 3000 3000 3000", 5, "爆发3"));
        mTrainings.add(new TrainingItemBean("5000 5000 5000 5000", 4, "爆发5"));
        mTrainings.add(new TrainingItemBean("1500 1500 1500 1500", 10, "爆发1.5"));
        mTrainings.add(new TrainingItemBean("3000 3000 3000 3000", 5, "爆发3"));
        mTrainings.add(new TrainingItemBean("5000 5000 5000 5000", 4, "爆发5"));
        mTrainings.add(new TrainingItemBean("1500 1500 1500 1500", 10, "爆发1.5"));
        mTrainings.add(new TrainingItemBean("3000 3000 3000 3000", 5, "爆发3"));
        mTrainings.add(new TrainingItemBean("5000 5000 5000 5000", 4, "爆发5"));
        mTrainings.add(new TrainingItemBean("1500 1500 1500 1500", 10, "爆发1.5"));
        mTrainings.add(new TrainingItemBean("3000 3000 3000 3000", 5, "爆发3"));
        mTrainings.add(new TrainingItemBean("5000 5000 5000 5000", 4, "爆发5"));

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRvTrainings.setLayoutManager(layoutManager);
        TrainingAdapter adapter = new TrainingAdapter(mTrainings);
        mRvTrainings.setAdapter(adapter);
        // mGModel.setText("3000 500 1000 500 1000 2000 1000 500 1000 500");

        mPreferences = getSharedPreferences("check", MODE_PRIVATE);
        mEditor = mPreferences.edit();
        int days = mPreferences.getInt(COUNT_DAYS, 0);
        mTvDays.setText(days+"");

        boolean morning = mPreferences.getBoolean(CHECK_MORNING, false);
        boolean afternoon = mPreferences.getBoolean(CHECK_AFTERNOON, false);
        boolean night = mPreferences.getBoolean(CHECK_NIGHT, false);

        // mBtnMorning.setEnabled(!morning);
        // mBtnAfternoon.setEnabled(!afternoon);
        // mBtnNight.setEnabled(!night);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
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
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragment_train_activity, new VibrateFragment().newInstance(model, loop))
                        .addToBackStack("爆发")
                        .commit();

                break;
            case R.id.eStart:
                model = mEModel.getText().toString();
                loop = mELoop.getText().toString();
                ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragment_train_activity, new VibrateFragment().newInstance(model, loop))
                        .addToBackStack("耐力")
                        .commit();

                break;
            case R.id.gStart:
                model = mGModel.getText().toString();
                loop = mGLoop.getText().toString();
                ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragment_train_activity, new VibrateFragment().newInstance(model, loop))
                        .addToBackStack("阶梯")
                        .commit();

                break;
        }
        // 屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public Vibrator getVib() {
        return mVib;
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

    @OnClick({R.id.btn_morning, R.id.btn_afternoon, R.id.btn_night})
    public void onCheckClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_morning:
                mEditor.putBoolean(CHECK_MORNING, true);
                mBtnMorning.setEnabled(false);
                break;
            case R.id.btn_afternoon:
                mEditor.putBoolean(CHECK_AFTERNOON, true);
                mBtnAfternoon.setEnabled(false);
                break;
            case R.id.btn_night:
                mEditor.putBoolean(CHECK_NIGHT, true);
                mBtnNight.setEnabled(false);
                break;
        }
        mEditor.apply();
        boolean morning = mPreferences.getBoolean(CHECK_MORNING, false);
        boolean afternoon = mPreferences.getBoolean(CHECK_AFTERNOON, false);
        boolean night = mPreferences.getBoolean(CHECK_NIGHT, false);
        int days = mPreferences.getInt(COUNT_DAYS, Integer.parseInt(mTvDays.getText().toString()));
        Log.d(TAG, morning + ", " + afternoon + ", " + night);
        if (morning && afternoon && night) {
            Log.d(TAG, "all check!!");
            days++;
            mEditor.putInt(COUNT_DAYS, days);
            mEditor.apply();
            mTvDays.setText(days + "");
        }

    }
}