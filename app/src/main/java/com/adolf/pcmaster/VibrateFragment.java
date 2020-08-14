package com.adolf.pcmaster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VibrateFragment extends Fragment {

    private static final String TAG = "VibrateFragment";
    private static final String MODEL = "model";
    private static final String LOOP = "loop";

    private String mModel;
    private String mLoop;

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

            Log.d(TAG, "mModel: " + mModel + "mLoop" + mLoop);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vibrate, container, false);
    }
}