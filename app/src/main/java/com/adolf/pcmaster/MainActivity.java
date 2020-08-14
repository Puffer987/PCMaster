package com.adolf.pcmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.btn_lets_go)
    Button mBtnLetsGo;
    @BindView(R.id.zoom_circle)
    ZoomCircleView mZoomCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mZoomCircle.setModelAndLoop("1500 1500 1500 1500 500 500",0);
    }

    @OnClick(R.id.btn_lets_go)
    public void onViewClicked() {
        startActivity(new Intent(this, TrainingActivity.class));
    }
}