package com.lzw.badgeview.demo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.imageview.ShapeableImageView;
import com.lzw.badgeview.annotation.ABadge;

/**
 * 描述:初始化 ABadgeView
 * 1.在项目任意一个类上面添加 ABadge 注解
 * 2.需要哪些类具有徽章功能，就把那些类的 class 作为 ABadge 注解的参数
 * 3.再 AS 中执行 Build => Rebuild Project
 * 4.经过前面三个步骤后就可以通过「ABadge原始类名」来使用徽章控件了
 */
@ABadge({
//        View.class, // 注意：使用View或者ViewGroup会报错，请直接使用具体的View或ViewGroup子类。
//        ViewGroup.class, // 注意：使用View或者ViewGroup会报错，请直接使用具体的View或ViewGroup子类。
        ImageView.class, // 对应 ABadgeImageView，不想用这个类的话就删了这一行
//        TextView.class, // 对应 ABadgeFloatingTextView，不想用这个类的话就删了这一行
//        RadioButton.class, // 对应 ABadgeRadioButton，不想用这个类的话就删了这一行
//        LinearLayout.class, // 对应 ABadgeLinearLayout，不想用这个类的话就删了这一行
//        FrameLayout.class, // 对应 ABadgeFrameLayout，不想用这个类的话就删了这一行
//        RelativeLayout.class, // 对应 ABadgeRelativeLayout，不想用这个类的话就删了这一行
//        FloatingActionButton.class, // 对应 ABadgeFloatingActionButton，不想用这个类的话就删了这一行
//        AppCompatTextView.class, // appcompat兼容包的控件也可以使用Badge，对应 ABadgeAppCompatTextView，不想用这个类的话就删了这一行
//        MyFrameLayout.class,  // 自定义控件也可以使用Badge，对应 ABadgeMyFrameLayout，不想用这个类的话就删了这一行

        ShapeableImageView.class,
        BottomNavigationView.class,

})
public class ABadgeInit {
}
