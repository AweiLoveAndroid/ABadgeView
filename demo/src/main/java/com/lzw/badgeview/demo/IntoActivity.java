package com.lzw.badgeview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lzw.badgeview.demo.bnv_page.BottomNavActivity;
import com.lzw.badgeview.demo.drawer_page.DrawerActivity;
import com.lzw.badgeview.demo.tab_page.TabActivity;
import com.lzw.badgeview.demo.toolbar_page.ToolbarBadgeActivity;

public class IntoActivity extends AppCompatActivity {

    public static final String TAG = IntoActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into);

    }

    public void btn(View view) {
        Intent intent = new Intent(IntoActivity.this, BottomNavActivity.class);
        startActivity(intent);
    }

    public void btn2(View view) {
        Intent intent = new Intent(IntoActivity.this, TabActivity.class);
        startActivity(intent);
    }

    public void btn3(View view) {
        Intent intent = new Intent(IntoActivity.this, ToolbarBadgeActivity.class);
        startActivity(intent);
    }

    public void btn4(View view) {
        Intent intent = new Intent(IntoActivity.this, DrawerActivity.class);
        startActivity(intent);
    }

}