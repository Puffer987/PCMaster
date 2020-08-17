package com.adolf.pcmaster;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VibrateFragment extends Fragment {

    private static final String TAG = "VibrateFragment";
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

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    // @OnClick(R.id.zoom_circle)
    // public void onViewClicked() {
    //     Log.d(TAG, "onClick: ");
    // }
}