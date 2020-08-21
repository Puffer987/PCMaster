package com.adolf.pcmaster.ui;

import android.app.Service;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adolf.pcmaster.R;
import com.adolf.pcmaster.adapter.TrainingAdapter;
import com.adolf.pcmaster.model.TrainingItemBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrainingActivity extends AppCompatActivity {

    private static final String TAG = "[jq]Training";
    private static final String CHECK_MORNING = "checkMorning";
    private static final String CHECK_AFTERNOON = "checkAfternoon";
    private static final String CHECK_NIGHT = "checkNight";
    private static final String COUNT_DAYS = "countDays";
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
    @BindView(R.id.cv_bg)
    ImageView mCvBg;
    @BindView(R.id.fb_add)
    FloatingActionButton mFbAdd;
    @BindView(R.id.fb_stop)
    FloatingActionButton mFbStop;

    private String model = "";
    private String loop = "0";
    private Vibrator mVib;
    private FragmentManager fm;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private List<TrainingItemBean> mTrainings = new ArrayList<>();
    private TrainingAdapter mAdapter;
    private int mCurPosition = -1;
    private boolean isItemFinish = true;

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

        mTrainings.add(new TrainingItemBean("1500 1500 1500 1500", 2, "爆发1.5"));
        mTrainings.add(new TrainingItemBean("3000 3000 3000 3000", 5, "爆发3"));
        mTrainings.add(new TrainingItemBean("5000 5000 5000 5000", 4, "爆发5"));

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRvTrainings.setLayoutManager(layoutManager);
        mAdapter = new TrainingAdapter(mTrainings, this);
        mRvTrainings.setAdapter(mAdapter);

        mPreferences = getSharedPreferences("check", MODE_PRIVATE);
        mEditor = mPreferences.edit();
        int days = mPreferences.getInt(COUNT_DAYS, 0);
        mTvDays.setText(days + "");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        int curDate = Integer.parseInt(format.format(new Date()));
        int latestOpenDate = mPreferences.getInt("latestOpenDate", 0);
        if (curDate > latestOpenDate) {
            mEditor.putBoolean(CHECK_MORNING, true);
            mEditor.putBoolean(CHECK_AFTERNOON, true);
            mEditor.putBoolean(CHECK_NIGHT, true);
            mEditor.putInt("latestOpenDate", curDate);
        }

        mBtnMorning.setEnabled(!mPreferences.getBoolean(CHECK_MORNING, false));
        mBtnAfternoon.setEnabled(!mPreferences.getBoolean(CHECK_AFTERNOON, false));
        mBtnNight.setEnabled(!mPreferences.getBoolean(CHECK_NIGHT, false));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    public Vibrator getVib() {
        return mVib;
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

    @OnClick({R.id.fb_add, R.id.fb_stop})
    public void onFloatBtnClicked(View view) {
        switch (view.getId()) {
            case R.id.fb_add:
                break;
            case R.id.fb_stop:
                mAdapter.notifyItemChanged(mCurPosition);
                break;
        }
    }

    interface OnItemClickListener {
        void setCurPosition();
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public int getCurPosition() {
        return mCurPosition;
    }

    public void setCurPosition(int curPosition) {
        mCurPosition = curPosition;
        Log.d(TAG, "setCurPosition: " + mCurPosition);
    }
    public boolean isItemFinish() {
        return isItemFinish;
    }

    public void setItemFinish(boolean itemFinish) {
        isItemFinish = itemFinish;
    }

}