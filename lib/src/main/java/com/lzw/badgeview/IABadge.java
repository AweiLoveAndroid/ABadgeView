package com.lzw.badgeview;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @author chqiu
 *         Email:qstumn@163.com
 */
public interface IABadge {

    IABadge bindTarget(View targetView);

    View getTargetView();

    IABadge setBadgeNumberExactMode(boolean isExact);

    boolean getBadgeNumberExactMode();

    IABadge setBadgeNumber(int badgeNum);

    int getBadgeNumber();

    IABadge setBadgeText(String badgeText);

    String getBadgeText();

    IABadge setBadgeTextSize(float size, boolean isSpValue);

    float getBadgeTextSize(boolean isSpValue);

    IABadge setBadgeTextColor(int color);

    int getBadgeTextColor();

    IABadge setShowBadgeShadow(boolean showShadow);

    boolean getIsShowBadgeShadow();

    IABadge setBadgeBackgroundColor(int color);

    int getBadgeBackgroundColor();

    IABadge setBadgeBackgroundDrawable(Drawable drawable);

    IABadge setBadgeBackgroundDrawable(Drawable drawable, boolean clip);

    Drawable getBadgeBackgroundDrawable();

    IABadge setBadgeBorderStroke(int color, float width, boolean isDpValue);

    IABadge setBadgeGravity(int gravity);

    int getBadgeGravity();

    IABadge setBadgePadding(float padding, boolean isDpValue);

    float getBadgePadding(boolean isDpValue);

    IABadge setBadgeGravityOffset(float offset, boolean isDpValue);

    IABadge setBadgeGravityOffset(float offsetX, float offsetY, boolean isDpValue);

    float getBadgeGravityOffsetX(boolean isDpValue);

    float getBadgeGravityOffsetY(boolean isDpValue);

    IABadge setBadgeIsDraggable(boolean draggable);

    boolean getBadgeIsDraggable();

    IABadge setOnBadgeDragStateChangedListener(OnBadgeDragStateChangedListener listener);

    PointF getBadgeDragCenter();

    void hideBadge(boolean animate);

    void resetBadge();

}