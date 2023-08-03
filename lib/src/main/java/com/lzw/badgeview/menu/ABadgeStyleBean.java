package com.lzw.badgeview.menu;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;


public class ABadgeStyleBean {

    private int mBadgeBackgroundDrawable;

    @ColorInt
    private int colorNormal = 0;
    @ColorInt
    private int colorPressed = 0;

    private int corner = -1;

    private int strokeWidth = -1;

    @ColorInt
    private int strokeColor = Color.RED;
    @ColorInt
    private int strokeColorPressed = Color.RED;


    public ABadgeStyleBean(@ColorInt int colorNormal, @ColorInt int colorPressed) {
        this.colorNormal = colorNormal;
        this.colorPressed = colorPressed;
    }

    public ABadgeStyleBean(int badgeBackgroundDrawable, @ColorInt int colorNormal, @ColorInt int colorPressed) {
        this.mBadgeBackgroundDrawable = badgeBackgroundDrawable;
        this.colorNormal = colorNormal;
        this.colorPressed = colorPressed;
    }

    public ABadgeStyleBean(
            @NonNull int badgeBackgroundDrawable,
            @ColorInt int colorNormal,
            @ColorInt int colorPressed,
            int corner,
            int strokeWidth,
            @ColorInt int strokeColor,
            @ColorInt int strokeColorPressed) {
        this.mBadgeBackgroundDrawable = badgeBackgroundDrawable;
        this.colorNormal = colorNormal;
        this.colorPressed = colorPressed;
        this.corner = corner;
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.strokeColorPressed = strokeColorPressed;
    }


    public int getBadgeBackgroundDrawable() {
        return mBadgeBackgroundDrawable;
    }

    public int getColorNormal() {
        return colorNormal;
    }

    public int getColorPressed() {
        return colorPressed;
    }

    public int getCorner() {
        return corner;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public int getStrokeColorPressed() {
        return strokeColorPressed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ABadgeStyleBean that = (ABadgeStyleBean) o;

        if (mBadgeBackgroundDrawable != that.mBadgeBackgroundDrawable) return false;
        if (colorNormal != that.colorNormal) return false;
        if (colorPressed != that.colorPressed) return false;
        if (corner != that.corner) return false;
        if (strokeWidth != that.strokeWidth) return false;
        if (strokeColor != that.strokeColor) return false;
        return strokeColorPressed == that.strokeColorPressed;
    }

    @Override
    public int hashCode() {
        int result = mBadgeBackgroundDrawable;
        result = 31 * result + colorNormal;
        result = 31 * result + colorPressed;
        result = 31 * result + corner;
        result = 31 * result + strokeWidth;
        result = 31 * result + strokeColor;
        result = 31 * result + strokeColorPressed;
        return result;
    }

    @Override
    public String toString() {
        return "ABadgeStyleBean{" +
                ", colorNormal=" + colorNormal +
                ", colorPressed=" + colorPressed +
                ", corner=" + corner +
                ", strokeWidth=" + strokeWidth +
                ", strokeColor=" + strokeColor +
                ", strokeColorPressed=" + strokeColorPressed +
                '}';
    }
}
