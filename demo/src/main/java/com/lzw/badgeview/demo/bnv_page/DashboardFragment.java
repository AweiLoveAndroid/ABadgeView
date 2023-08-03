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
import com.lzw.badgeview.IABadge;
import com.lzw.badgeview.OnBadgeDragStateChangedListener;
import com.lzw.badgeview.annotation.ABadgeDragState;
import com.lzw.badgeview.demo.R;

public class DashboardFragment extends Fragment {

    private ABadgeImageView ivLike;
    private ABadgeImageView ivComment;
    private ABadgeImageView ivFollow;
    private ABadgeImageView ivMe;

    public static final String TAG = DashboardFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ivLike = root.findViewById(R.id.like_badge);
        ivComment = root.findViewById(R.id.comment_badge);
        ivFollow = root.findViewById(R.id.follow_badge);
        ivMe = root.findViewById(R.id.me_badge);

        ivLike.setClickable(true);
        ivComment.setClickable(true);
        ivFollow.setClickable(true);
        ivMe.setClickable(true);

        ivLike.requestFocus();
        ivComment.requestFocus();
        ivFollow.requestFocus();
        ivMe.requestFocus();

        ivLike.setIsNeedShowBadge(true);
        ivLike.getABadgeView().bindTarget(ivLike)
                .setBadgeNumber(10)//设置显示数字
                .setBadgeGravity(Gravity.END | Gravity.TOP)//设置显示位置
                .setBadgeTextSize(5, true)//设置字体大小，后边的true是指使用sp为单位
                .setBadgeGravityOffset(0, 0, true)
                .setBadgePadding(5, true)
                .setBadgeBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.shape_bg_red, null))//设置自定义背景，红色填充，绿色边框
                .setBadgeIsDraggable(true)
                .setOnBadgeDragStateChangedListener(new OnBadgeDragStateChangedListener() {
                    @Override
                    public void onBadgeDragStateChanged(int dragState, String stateTag, IABadge badge, View targetView) {
                        Log.d(TAG, "onBadgeDragStateChanged: dragState=" + dragState + ", stateTag=" + stateTag);
                        if (dragState == ABadgeDragState.STATE_SUCCEED) {
                            ivLike.setIsNeedShowBadge(false);
                        }
                    }
                });
        return root;
    }

}