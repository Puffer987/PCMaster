package com.adolf.pcmaster.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.adolf.pcmaster.R;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationDrawerActivity extends AppCompatActivity {

    private final static String TAG = "[jq]NavigationDrawer";
    @BindView(R.id.parent_drawer)
    DrawerLayout mParentDrawer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 开启左侧的按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 设置左侧按钮图标，默认是一个返回箭头图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_48);
        }
        mNavView.setCheckedItem(R.id.nav_setting);// 默认选中项目

        mNavView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_back:
                    Toast.makeText(NavigationDrawerActivity.this, "Backup..", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_bin:
                    Toast.makeText(NavigationDrawerActivity.this, "Delete..", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_setting:
                    Toast.makeText(NavigationDrawerActivity.this, "Setting..", Toast.LENGTH_SHORT).show();
                    break;
            }
            mParentDrawer.closeDrawers();
            return true;
        });

        mParentDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //滑动时调用
                // Log.d(TAG, "onDrawerSlide: ");
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                //打开菜单时调用
                Log.d(TAG, "onDrawerOpened: ");
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                //关闭菜单时调用
                Log.d(TAG, "onDrawerClosed: ");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                //菜单状态改变时调用
                // Log.d(TAG, "onDrawerStateChanged: ");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 特殊的id，来自系统提供
                mParentDrawer.openDrawer(GravityCompat.START);
                Toast.makeText(this, "Menu..", Toast.LENGTH_SHORT).show();
                break;
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
}