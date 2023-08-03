package com.lzw.badgeview;

import android.view.View;

import com.lzw.badgeview.annotation.ABadgeDragState;

public interface OnBadgeDragStateChangedListener {
    void onBadgeDragStateChanged(@ABadgeDragState int dragState, String stateTag, IABadge badge, View targetView);
}