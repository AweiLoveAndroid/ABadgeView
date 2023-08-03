package com.lzw.badgeview.demo.bnv_page;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.lzw.badgeview.ABadgeBottomNavigationView;
import com.lzw.badgeview.IABadge;
import com.lzw.badgeview.OnBadgeDragStateChangedListener;
import com.lzw.badgeview.annotation.ABadgeDragState;
import com.lzw.badgeview.demo.R;
import com.lzw.badgeview.demo.databinding.ActivityBottomNavBinding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BottomNavActivity extends AppCompatActivity {

    private ActivityBottomNavBinding binding;
    private ABadgeBottomNavigationView bottomNavigationView;

    public static final String TAG = BottomNavActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // 使用官方的BadgeDrawable：
        BadgeDrawable badgeDrawable = bottomNavigationView.addBadgeCustom(R.id.navigation_home);
//        reflectBadgeDrawable(badgeDrawable);
        badgeDrawable.setBadgeGravity(BadgeDrawable.TOP_END);
        badgeDrawable.setNumber(100);

        // 使用xml布局加载
        bottomNavigationView.addBadgeFromXmlLayout(BottomNavActivity.this, 1, R.layout.layout_custom_badge_xml);

        // 使用自定义ABadgeView
        bottomNavigationView.setIsNeedShowBadge(true);
        bottomNavigationView.bindTargetItemIndex(2)
//                .setBadgeText("10")//设置显示数字
                .setBadgeNumber(10)//设置显示数字
                .setBadgeGravity(Gravity.END | Gravity.TOP)//设置显示位置
                .setBadgeTextSize(8, true)//设置字体大小，后边的true是指使用sp为单位
                .setBadgeGravityOffset(5, 0, true)
                .setBadgePadding(8, true)
                .setBadgeBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.shape_bg_red, null))//设置自定义背景，红色填充，绿色边框
                .setBadgeIsDraggable(true)
                .setOnBadgeDragStateChangedListener(new OnBadgeDragStateChangedListener() {
                    @Override
                    public void onBadgeDragStateChanged(int dragState, String stateTag, IABadge badge, View targetView) {
                        Log.d(TAG, "onBadgeDragStateChanged: dragState=" + dragState + ", stateTag=" + stateTag);
                        if (dragState == ABadgeDragState.STATE_SUCCEED) {
                            bottomNavigationView.setIsNeedShowBadge(false);
                        }
                    }
                });

    }

    private void reflectBadgeDrawable(BadgeDrawable badgeDrawable) {
        try {
            // setTextAppearanceResource是设置画笔的就是文字大小样式之类
            Method method = BadgeDrawable.class.getDeclaredMethod("setTextAppearanceResource", int.class);
            method.setAccessible(true);
            method.invoke(badgeDrawable, R.style.CustomBadgeStyle);

            // 没有设置数字的时候，圆点的半径
            Field badgeRadius = BadgeDrawable.class.getDeclaredField("badgeRadius");
            badgeRadius.setAccessible(true);
            badgeRadius.set(badgeDrawable, 5f);

            // 有数字时，圆点的半径
            Field badgeWithTextRadius = BadgeDrawable.class.getDeclaredField("badgeWithTextRadius");
            badgeWithTextRadius.setAccessible(true);
            badgeWithTextRadius.set(badgeDrawable, 10f);

            // 有数字，当数字超过9时，文字左右两边的间距
            Field badgeWidePadding = BadgeDrawable.class.getDeclaredField("badgeWidePadding");
            badgeWidePadding.setAccessible(true);
            badgeWidePadding.set(badgeDrawable, 5f);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}