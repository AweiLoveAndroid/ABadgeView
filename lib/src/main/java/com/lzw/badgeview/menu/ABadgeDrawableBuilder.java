package com.lzw.badgeview.menu;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;


/**
 * Created by mikepenz on 02.07.15.
 */
public class ABadgeDrawableBuilder {

    private
    @DrawableRes int mBadgeBackgroundDrawable;

    private int mColorNormal = 0;
    private int mColorPressed = 0;
    private int mCorners = -1;
    private int mStrokeWidth = -1;
    private int mStrokeColor = 0;
    private int mStrokeColorPressed = 0;

    private ABadgeStyleDefault mABadgeStyleDefault;

    public ABadgeDrawableBuilder() {
    }

    public ABadgeDrawableBuilder setDefaultBadgeStyle(ABadgeStyleDefault aBadgeStyleDefault) {
        this.mABadgeStyleDefault = aBadgeStyleDefault;
        return this;
    }

    /**
     * 设置背景shape
     * @param badgeBackgroundDrawable
     * @return
     */
    public ABadgeDrawableBuilder badgeBackgroundDrawable(int badgeBackgroundDrawable) {
        if(mABadgeStyleDefault != null) {
            this.mBadgeBackgroundDrawable = mABadgeStyleDefault.getStyle().getBadgeBackgroundDrawable();
        } else {
            this.mBadgeBackgroundDrawable = badgeBackgroundDrawable;
        }
        return this;
    }

    /**
     * 修改 {@link ABadgeDrawableBuilder#mBadgeBackgroundDrawable} 的 普通颜色
     * @param colorNormal
     * @return
     */
    public ABadgeDrawableBuilder colorNormal(@ColorInt int colorNormal) {
        if(mABadgeStyleDefault != null) {
            this.mColorNormal = mABadgeStyleDefault.getStyle().getColorNormal();
        } else {
            this.mColorNormal = colorNormal;
        }
        return this;
    }

    /**
     * 修改 选中时 {@link ABadgeDrawableBuilder#mBadgeBackgroundDrawable} 的 选中时的颜色
     * @param colorPressed
     * @return
     */
    public ABadgeDrawableBuilder colorPressed(@ColorInt int colorPressed) {
        if(mABadgeStyleDefault != null) {
            this.mColorPressed = mABadgeStyleDefault.getStyle().getColorNormal();
        } else {
            this.mColorPressed = colorPressed;
        }
        return this;
    }

    /**
     * 修改 {@link ABadgeDrawableBuilder#mBadgeBackgroundDrawable} 的 四个角的圆弧半径
     * @param corners
     * @return
     */
    public ABadgeDrawableBuilder corners(int corners) {
        this.mCorners = corners;
        return this;
    }

    /**
     * 修改 {@link ABadgeDrawableBuilder#mBadgeBackgroundDrawable} 的 边框宽度
     * @param strokeWidth
     * @return
     */
    public ABadgeDrawableBuilder strokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        return this;
    }

    /**
     * 修改 {@link ABadgeDrawableBuilder#mBadgeBackgroundDrawable} 的 边框颜色
     * @param strokeColor
     * @return
     */
    public ABadgeDrawableBuilder strokeColorNormal(@ColorInt int strokeColor) {
        this.mStrokeColor = strokeColor;
        return this;
    }

    /**
     * 修改 选中时 {@link ABadgeDrawableBuilder#mBadgeBackgroundDrawable} 的 边框颜色
     * @param strokeColor
     * @return
     */
    public ABadgeDrawableBuilder strokeColorPressed(@ColorInt int strokeColor) {
        this.mStrokeColorPressed = strokeColor;
        return this;
    }

    GradientDrawable normal;
    GradientDrawable selected;

    /**
     * 代码生成drawable选择器，包括未选中和选中时的效果。
     * @param ctx
     * @return
     */
    public StateListDrawable build(Context ctx) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        if(mABadgeStyleDefault != null) {
            ABadgeStyleBean badgeStyleBean = mABadgeStyleDefault.getStyle();
            int badgeBackgroundDrawable = badgeStyleBean.getBadgeBackgroundDrawable();
            int colorNormal = badgeStyleBean.getColorNormal();
            int colorPressed = badgeStyleBean.getColorPressed();

            this.mBadgeBackgroundDrawable = badgeBackgroundDrawable;
            this.mColorNormal = colorNormal;
            this.mColorPressed = colorPressed;

            // 需要传入一个drawable背景资源（R.drawable.xxx）
            normal = (GradientDrawable) ContextCompat.getDrawable(ctx, mBadgeBackgroundDrawable);
            selected = (GradientDrawable) normal.getConstantState().newDrawable().mutate();

            normal.setColor(mColorNormal);
            selected.setColor(mColorPressed);

        } else {
            // 需要传入一个drawable背景资源（R.drawable.xxx）
            normal = (GradientDrawable) ContextCompat.getDrawable(ctx, mBadgeBackgroundDrawable);
            selected = (GradientDrawable) normal.getConstantState().newDrawable().mutate();

            normal.setColor(mColorNormal);
            selected.setColor(mColorPressed);
        }

        if (mStrokeWidth > 0) {
            normal.setStroke(mStrokeWidth, mStrokeColor);
            selected.setStroke(mStrokeWidth, mStrokeColorPressed);
        }
        if (mCorners > 0) {
            normal.setCornerRadius(mCorners);
            selected.setCornerRadius(mCorners);
        }

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, selected);
        stateListDrawable.addState(StateSet.WILD_CARD, normal);

        return stateListDrawable;
    }

}