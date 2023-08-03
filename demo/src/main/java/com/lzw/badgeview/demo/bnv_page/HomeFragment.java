package com.lzw.badgeview.demo.bnv_page;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.lzw.badgeview.ABadgeImageView;
import com.lzw.badgeview.ABadgeShapeableImageView;
import com.lzw.badgeview.IABadge;
import com.lzw.badgeview.OnBadgeDragStateChangedListener;
import com.lzw.badgeview.annotation.ABadgeDragState;
import com.lzw.badgeview.demo.R;
import com.lzw.badgeview.demo.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment{

    public static final String TAG = HomeFragment.class.getSimpleName();

    private final String[] newNotices = new String[]{"", "60", "1000"};
    private final int[] newNoticesInt = new int[]{-1, 60, 1000};

    private ABadgeImageView imageView0;
    private ABadgeImageView imageView1;
    private ABadgeImageView imageView2;
    private ABadgeImageView imageView3;
    private ABadgeShapeableImageView imageView4;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        imageView0 = root.findViewById(R.id.image0);
        imageView1 = root.findViewById(R.id.image1);
        imageView2 = root.findViewById(R.id.image2);
        imageView3 = root.findViewById(R.id.image3);
        imageView4 = root.findViewById(R.id.image4);


        imageView0.setIsNeedShowBadge(true);
        imageView0.getABadgeView().bindTarget(imageView0)
                .setBadgeText("优惠")
                .setBadgeGravity(Gravity.END | Gravity.TOP)//设置显示位置
                .setBadgeTextSize(8, true)//设置字体大小，后边的true是指使用sp为单位
                .setBadgeGravityOffset(0, 0, true)
                .setBadgePadding(8, true)
                .setBadgeBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.shape_bg_orange, null));//设置自定义背景，红色填充，绿色边框


        imageView1.setIsNeedShowBadge(true);
        imageView1.getABadgeView().bindTarget(imageView1)
//                .setBadgeText(newNotices[0]) // 不传入字符串内容，显示小圆点
                .setBadgeNumber(newNoticesInt[0]) // 不传入字符串内容，显示小圆点
                .setBadgeGravity(Gravity.END | Gravity.TOP)//设置显示位置
                .setBadgeTextSize(8, true)//设置字体大小，后边的true是指使用sp为单位
                .setBadgeGravityOffset(0, 0, true)
                .setBadgePadding(5, true);

        imageView2.setIsNeedShowBadge(true);
        imageView2.getABadgeView().bindTarget(imageView2)
//                .setBadgeText(newNotices[1])//设置显示数字
                .setBadgeNumber(newNoticesInt[1])//设置显示数字
                .setBadgeGravity(Gravity.END | Gravity.CENTER)//设置显示位置
                .setBadgeTextSize(8, true)//设置字体大小，后边的true是指使用sp为单位
                .setBadgeGravityOffset(0, 0, true)
                .setBadgePadding(8, true)
                .setBadgeBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.shape_bg_red, null))//设置自定义背景，红色填充，绿色边框
                .setBadgeIsDraggable(true)
                .setOnBadgeDragStateChangedListener(new OnBadgeDragStateChangedListener() {
                    @Override
                    public void onBadgeDragStateChanged(int dragState, String stateTag, IABadge badge, View targetView) {
                        Log.d(TAG, "onBadgeDragStateChanged: dragState=" + dragState + ", stateTag=" + stateTag);
                        if (dragState == ABadgeDragState.STATE_SUCCEED) {
                            imageView2.setIsNeedShowBadge(false);
                        }
                    }
                });

        imageView3.setIsNeedShowBadge(true);
        imageView3.getABadgeView().bindTarget(imageView3)
//                .setBadgeText(newNotices[2])//设置显示数字
                .setBadgeNumber(newNoticesInt[2])//设置显示数字
                .setBadgeGravity(Gravity.END | Gravity.BOTTOM)//设置显示位置
                .setBadgeTextSize(8, true)//设置字体大小，后边的true是指使用sp为单位
                .setBadgeGravityOffset(0, 0, true)
                .setBadgePadding(8, true)
                .setBadgeBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.shape_bg_red, null))//设置自定义背景，红色填充，绿色边框
                .setBadgeIsDraggable(true)
                .setOnBadgeDragStateChangedListener(new OnBadgeDragStateChangedListener() {
                    @Override
                    public void onBadgeDragStateChanged(int dragState, String stateTag, IABadge badge, View targetView) {
                        Log.d(TAG, "onBadgeDragStateChanged: dragState=" + dragState + ", stateTag=" + stateTag);
                        if (dragState == ABadgeDragState.STATE_SUCCEED) {
                            imageView2.setIsNeedShowBadge(false);
                        }
                    }
                });


        imageView4.setIsNeedShowBadge(true);
        imageView4.getABadgeView().bindTarget(imageView4)
                .setBadgeText("")
                .setBadgeGravity(Gravity.END | Gravity.BOTTOM)//设置显示位置
                .setBadgeTextSize(30, true)//设置字体大小，后边的true是指使用sp为单位
                .setBadgeGravityOffset(0, 0, false)
                .setBadgeBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_vip_blue, null))
                .setBadgePadding(8, true)
//                .setBadgeBackgroundDrawable(getResources().getDrawable(R.drawable.base_a_little_red_dot))//设置自定义背景，红色填充，绿色边框
        ;

        return root;
    }

}