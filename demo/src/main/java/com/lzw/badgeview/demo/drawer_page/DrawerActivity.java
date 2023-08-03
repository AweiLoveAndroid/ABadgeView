package com.lzw.badgeview.demo.drawer_page;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.lzw.badgeview.demo.R;
import com.lzw.badgeview.menu.ABadgeMenuItemClickListener;
import com.lzw.badgeview.menu.ABadgeMenuItemHelper;
import com.lzw.badgeview.menu.ABadgeStyleDefault;

public class DrawerActivity extends AppCompatActivity {

    public static final String TAG = DrawerActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        LinearLayout linearLayout = drawerLayout.findViewById(R.id.content_area);
        Toolbar toolbar = linearLayout.findViewById(R.id.toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.drawer_area);

        //mDrawerLayout与mToolbar关联起来
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        //初始化状态
        actionBarDrawerToggle.syncState();

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                Log.d(TAG, "滑动中");
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Log.d(TAG, "打开");
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Log.d(TAG, "关闭");
            }

            @Override
            public void onDrawerStateChanged(int i) {
                Log.d(TAG, "状态改变");
            }
        });


        MenuItem menuItem1 = navigationView.getMenu().getItem(0);
        MenuItem menuItem2 = navigationView.getMenu().getItem(1);
        MenuItem menuItem3 = navigationView.getMenu().getItem(2);

        Drawable menuItemDrawable1 = menuItem1.getIcon();
        Drawable menuItemDrawable2 = menuItem2.getIcon();
        Drawable menuItemDrawable3 = menuItem3.getIcon();

        // 隐藏当前MenuItem
//        new ABadgeMenuItemHelper(DrawerActivity.this).hideMenuItem(menuItemHome);

        // 如果传入负数，或者0 则不会badge
        new ABadgeMenuItemHelper(DrawerActivity.this).show(
                menuItem1,
                getResources().getDrawable(R.mipmap.tab_discover_normal),
                getResources().getDrawable(R.mipmap.tab_discover_checked),
                0,
                ABadgeStyleDefault.RED_ROUND,
                new ABadgeMenuItemClickListener() {
                    @Override
                    public void onOptionsItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.drawer_menu_item1) {
                            Toast.makeText(DrawerActivity.this, "自定义监听 ==> 点击了按钮2", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        new ABadgeMenuItemHelper(DrawerActivity.this).show(
                menuItem2,
                getResources().getDrawable(R.mipmap.tab_me_normal),
                getResources().getDrawable(R.mipmap.tab_me_checked),
                100,
                ABadgeStyleDefault.RED_ROUND_RECT,
                new ABadgeMenuItemClickListener() {
                    @Override
                    public void onOptionsItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.drawer_menu_item2) {
                            Toast.makeText(DrawerActivity.this, "自定义监听 ==> 点击了按钮2", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        new ABadgeMenuItemHelper(DrawerActivity.this).show(
                menuItem3,
                getResources().getDrawable(R.drawable.ic_home_black_24dp),
                getResources().getDrawable(R.drawable.ic_home_black_24dp),
                "测试",
                ABadgeStyleDefault.RED_ROUND_RECT,
                new ABadgeMenuItemClickListener() {
                    @Override
                    public void onOptionsItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.drawer_menu_item3) {
                            Toast.makeText(DrawerActivity.this, "自定义监听 ==> 点击了按钮3", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        

    }

}