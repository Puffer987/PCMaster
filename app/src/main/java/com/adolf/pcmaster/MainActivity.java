package com.adolf.pcmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adolf.pcmaster.ui.TrainingActivity;
import com.adolf.pcmaster.view.ZoomCircleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.btn_lets_go)
    Button mBtnLetsGo;
    @BindView(R.id.zoom_circle)
    ZoomCircleView mZoomCircle;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);

        mZoomCircle.setModelAndLoop("500 500 500 500 1300 1300 500 3000", 0);
    }

    @OnClick(R.id.btn_lets_go)
    public void onViewClicked() {
        startActivity(new Intent(this, TrainingActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}