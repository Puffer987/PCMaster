package com.adolf.pcmaster.ui;

import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.adolf.pcmaster.R;
import com.adolf.pcmaster.view.ZoomCircleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VibrateFragment extends Fragment {

    private static final String TAG = "[jq]VibrateFragment";
    private static final String MODEL = "model";
    private static final String LOOP = "loop";
    @BindView(R.id.zoom_circle)
    ZoomCircleView mZoomCircle;
    @BindView(R.id.parent_frame)
    FrameLayout mParentFrame;

    private String mModel;
    private String mLoop;
    // private ZoomCircleView mZoomCircle;
    private Unbinder mUnbinder;
    private TrainingActivity mActivity;

    public VibrateFragment() {
        // Required empty public constructor
    }

    public static VibrateFragment newInstance(String param1, String param2) {
        VibrateFragment fragment = new VibrateFragment();
        Bundle args = new Bundle();
        args.putString(MODEL, param1);
        args.putString(LOOP, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mModel = getArguments().getString(MODEL);
            mLoop = getArguments().getString(LOOP);

            assert mLoop != null;
            Log.d(TAG, "mModel: " + mModel + "mLoop" + mLoop);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vibrate, container, false);

        mUnbinder = ButterKnife.bind(this, view);
        mZoomCircle.setModelAndLoop(mModel, Integer.parseInt(mLoop));

        mActivity = (TrainingActivity) getActivity();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @OnClick(R.id.zoom_circle)
    public void onView() {
        Log.d(TAG, "onViewClicked: ");
        Vibrator vib = mActivity.getVib();
        long[] circlePattern = mZoomCircle.getPattern();
        long[] pattern = new long[circlePattern.length + 1];

        pattern[0] = 0;
        for (int i = 0; i < circlePattern.length; i++) {
            pattern[i + 1] = circlePattern[i];
        }

        vib.vibrate(pattern, -1);
        mZoomCircle.setFinishListener(() -> {
            Toast.makeText(mActivity, "finish", Toast.LENGTH_SHORT).show();
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        });
    }
}