package com.lzw.badgeview.menu;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.applyDimension;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.view.View;
import android.widget.ImageView;

import java.util.regex.Pattern;

/**
 * Created by mikepenz on 02.07.15.
 */
public class ABadgeUIUtil {

    /**
     * helper method to set the background depending on the android version
     *
     * @param v
     * @param d
     */
    @SuppressLint("NewApi")
    public static void setBackground(View v, Drawable d) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackgroundDrawable(d);
        } else {
            v.setBackground(d);
        }
    }

    /**
     * 代码生成drawable选择器，包括未选中和选中时的效果，适用于图片
     * @param imageView
     * @param drawableNormal
     * @param drawablePressed
     * @return
     */
    public static void buildImageDrawable(ImageView imageView, Drawable drawableNormal, Drawable drawablePressed) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
        stateListDrawable.addState(StateSet.WILD_CARD, drawableNormal);

        setBackground(imageView, stateListDrawable);
    }

    public static int convertDpToPx(Context context, float dp) {
        return (int) applyDimension(COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int convertspToPx(Context context, float sp) {
        return (int) applyDimension(COMPLEX_UNIT_PX, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * judge str of the badge text is numer.
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        // 匹配所有的数字，包括负数
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        boolean result = pattern.matcher(str).matches();
        return result;
    }

}