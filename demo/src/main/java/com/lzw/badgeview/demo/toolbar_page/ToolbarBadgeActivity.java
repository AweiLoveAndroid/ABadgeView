package com.lzw.badgeview.demo.toolbar_page;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lzw.badgeview.demo.R;
import com.lzw.badgeview.menu.ABadgeMenuItemClickListener;
import com.lzw.badgeview.menu.ABadgeMenuItemHelper;
import com.lzw.badgeview.menu.ABadgeStyleDefault;

import java.lang.reflect.Method;


/**
 * toolbar显示badge 可以参考：https://github.com/mikepenz/Android-ActionItemBadge，就不需要自己去写了。
 */
public class ToolbarBadgeActivity extends AppCompatActivity {

    private int cartGoodsCount = 1;
    private int cartGoodsCount2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_badge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.show_badge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        findViewById(R.id.add_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartGoodsCount++;
            }
        });
        findViewById(R.id.add_double).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartGoodsCount2 += 10;
            }
        });
    }

    int badgeCount = 99;
    int badgeCount2 = 5;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        //you can add some logic
        MenuItem menuItem1 = menu.findItem(R.id.toolbar_menu_item1);
        MenuItem menuItem2 = menu.findItem(R.id.toolbar_menu_item2);
        MenuItem menuItem3 = menu.findItem(R.id.toolbar_menu_item3);

        Drawable menuItemDrawable1 = menuItem1.getIcon();
        Drawable menuItemDrawable2 = menuItem2.getIcon();
        Drawable menuItemDrawable3 = menuItem3.getIcon();

        // 隐藏当前MenuItem
//        new ABadgeMenuItemHelper(ToolbarBadgeActivity.this).hideMenuItem(menuItemHome);

        // 如果传入负数，或者0 则不会badge
        new ABadgeMenuItemHelper(ToolbarBadgeActivity.this).show(
                menuItem1,
                getResources().getDrawable(R.mipmap.tab_discover_normal),
                getResources().getDrawable(R.mipmap.tab_discover_checked),
                0,
                ABadgeStyleDefault.RED_ROUND,
                null);

        new ABadgeMenuItemHelper(ToolbarBadgeActivity.this).show(
                menuItem2,
                getResources().getDrawable(R.mipmap.tab_me_normal),
                getResources().getDrawable(R.mipmap.tab_me_checked),
                100,
                ABadgeStyleDefault.RED_ROUND_RECT,
                new ABadgeMenuItemClickListener() {
                    @Override
                    public void onOptionsItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.toolbar_menu_item2) {
                            Toast.makeText(ToolbarBadgeActivity.this, "自定义监听 ==> 点击了按钮2", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        new ABadgeMenuItemHelper(ToolbarBadgeActivity.this).show(
                menuItem3,
                menuItemDrawable3,
                "测试",
                ABadgeStyleDefault.RED_ROUND_RECT,
                new ABadgeMenuItemClickListener() {
                    @Override
                    public void onOptionsItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.toolbar_menu_item3) {
                            Toast.makeText(ToolbarBadgeActivity.this, "自定义监听 ==> 点击了按钮3", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_menu_item1:
                Toast.makeText(ToolbarBadgeActivity.this, "onOptionsItemSelected ==> 点击了按钮1", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.toolbar_menu_item2:
//                Toast.makeText(ToolbarBadgeActivity.this, "onOptionsItemSelected ==> 点击了按钮2", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.toolbar_menu_item3:
//                Toast.makeText(ToolbarBadgeActivity.this, "onOptionsItemSelected ==> 点击了按钮3", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.toolbar_menu_item4:
                Toast.makeText(ToolbarBadgeActivity.this, "onOptionsItemSelected ==> 点击了按钮4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_menu_item5:
                Toast.makeText(ToolbarBadgeActivity.this, "onOptionsItemSelected ==> 点击了按钮5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_menu_item6:
                Toast.makeText(ToolbarBadgeActivity.this, "onOptionsItemSelected ==> 点击了按钮6", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 重写onPrepareOptionsPanel，反射MenuBuilder给toolbar弹出的menuitem添加图标,
     *
     * @param view
     * @param menu
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (Exception e) {
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }


}