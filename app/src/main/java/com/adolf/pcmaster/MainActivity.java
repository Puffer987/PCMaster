package com.adolf.pcmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.adolf.pcmaster.ui.TrainingActivity;
import com.adolf.pcmaster.ui.NavigationDrawerActivity;
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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
        //         WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);

        mZoomCircle.setModelAndLoop("500 500 500 500 1300 1300 500 3000", 0);

        setSupportActionBar(mToolbar);
        // startActivity(new Intent(this, NavigationDrawerActivity.class));
        startActivity(new Intent(this, TrainingActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "Backup..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "Delete..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "Setting..", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
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