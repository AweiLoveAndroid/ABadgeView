package com.lzw.badgeview.menu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lzw.badgeview.R;

/**
 * 显示和隐藏Toolbar的menu item badge
 */
public class ABadgeMenuItemHelper {

    public static final String TAG = ABadgeMenuItemHelper.class.getSimpleName();

    @NonNull
    private Activity mActivity;
    private FrameLayout badge;
    private ImageView imageView;
    private TextView badgeView;

    public ABadgeMenuItemHelper(@NonNull Activity activity) {
        this.mActivity = activity;
    }


    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     int badgeCount,
                     ABadgeStyleBean badgeStyleBean) {
        return show(menuItem, iconNormal, iconNormal, badgeCount, badgeStyleBean, null, null);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     int badgeCount,
                     ABadgeStyleBean badgeStyleBean,
                     ABadgeMenuItemClickListener listener) {
        return show(menuItem, iconNormal, iconNormal, badgeCount, badgeStyleBean, null, listener);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     String badgeCount,
                     ABadgeStyleBean badgeStyleBean) {
        return  show(menuItem, iconNormal, iconNormal, badgeCount, badgeStyleBean, null, null);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     String badgeCount,
                     ABadgeStyleBean badgeStyleBean,
                     ABadgeMenuItemClickListener listener) {
        return  show(menuItem, iconNormal, iconNormal, badgeCount, badgeStyleBean, null, listener);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     Drawable iconPressed,
                     int badgeCount,
                     ABadgeStyleBean badgeStyleBean) {
        return  show(menuItem, iconNormal, iconPressed, badgeCount, badgeStyleBean, null, null);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     Drawable iconPressed,
                     int badgeCount,
                     ABadgeStyleBean badgeStyleBean,
                     ABadgeMenuItemClickListener listener) {
        return   show(menuItem, iconNormal, iconPressed, badgeCount, badgeStyleBean, null, listener);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     Drawable iconPressed,
                     String badgeCount,
                     ABadgeStyleBean badgeStyleBean) {
        return show(menuItem, iconNormal, iconPressed, badgeCount, badgeStyleBean, null, null);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     Drawable iconPressed,
                     String badgeCount,
                     ABadgeStyleBean badgeStyleBean,
                     ABadgeMenuItemClickListener listener) {
        return show(menuItem, iconNormal, iconPressed, badgeCount, badgeStyleBean, null, listener);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     int badgeCount,
                     ABadgeStyleDefault badgeStyleDefault) {
        return    show(menuItem, iconNormal, iconNormal, badgeCount, null, badgeStyleDefault, null);
    }


    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     int badgeCount,
                     ABadgeStyleDefault badgeStyleDefault,
                     ABadgeMenuItemClickListener listener) {
        return show(menuItem, iconNormal, iconNormal, badgeCount, null, badgeStyleDefault, listener);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     String badgeCount,
                     ABadgeStyleDefault badgeStyleDefault) {
        return   show(menuItem, iconNormal, iconNormal, badgeCount, null, badgeStyleDefault, null);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     String badgeCount,
                     ABadgeStyleDefault badgeStyleDefault,
                     ABadgeMenuItemClickListener listener) {
        return   show(menuItem, iconNormal, iconNormal, badgeCount, null, badgeStyleDefault, listener);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     Drawable iconPressed,
                     int badgeCount,
                     ABadgeStyleDefault badgeStyleDefault) {
        return  show(menuItem, iconNormal, iconPressed, badgeCount, null, badgeStyleDefault, null);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     Drawable iconPressed,
                     int badgeCount,
                     ABadgeStyleDefault badgeStyleDefault,
                     ABadgeMenuItemClickListener listener) {
        return  show(menuItem, iconNormal, iconPressed, badgeCount, null, badgeStyleDefault, listener);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     Drawable iconPressed,
                     String badgeCount,
                     ABadgeStyleDefault badgeStyleDefault) {
        return   show(menuItem, iconNormal, iconPressed, badgeCount, null, badgeStyleDefault, null);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal,
                     Drawable iconPressed,
                     String badgeCount,
                     ABadgeStyleDefault badgeStyleDefault,
                     ABadgeMenuItemClickListener listener) {
        return  show(menuItem, iconNormal, iconPressed, badgeCount, null, badgeStyleDefault, listener);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal, Drawable iconPressed,
                     int badgeCount,
                     ABadgeStyleBean badgeStyleBean,
                     ABadgeStyleDefault badgeStyleDefault,
                     ABadgeMenuItemClickListener listener) {
        return  show(menuItem, iconNormal, iconPressed, String.valueOf(badgeCount), badgeStyleBean, badgeStyleDefault, listener);
    }

    private void initDefaultMenuLayout(MenuItem menuItem) {
        badge = (FrameLayout) menuItem.getActionView();

        if (badge == null) {
            throw new IllegalArgumentException(TAG + "Current menu id =" + menuItem.getItemId() + ". The menu item must add actionLayout, just copy this code in youe menu item:  app:actionLayout=\"@layout/layout_default_menu_action_item_badge_common\"");
        }
        imageView = badge.findViewById(R.id.menu_badge_icon);
        badgeView = badge.findViewById(R.id.menu_badge);
    }

    public ABadgeMenuItemHelper show(MenuItem menuItem,
                     Drawable iconNormal, Drawable iconPressed,
                     String badgeCount,
                     ABadgeStyleBean badgeStyleBean,
                     ABadgeStyleDefault badgeStyleDefault,
                     ABadgeMenuItemClickListener listener) {

        initDefaultMenuLayout(menuItem);

        // 点击事件
        badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) { // 设置了监听
                    listener.onOptionsItemSelected(menuItem);
                    System.out.println(TAG + "调用 ABadgeMenuItemClickListener的 onOptionsItemSelected 回调");
                } else {
                    // activity.onMenuItemSelected(Window.FEATURE_OPTIONS_PANEL, menu);  // 调用 Activity的 onMenuItemSelected 方法
                    mActivity.onOptionsItemSelected(menuItem);  // 调用 Activity的 onOptionsItemSelected 方法
                    System.out.println(TAG + "调用 Activity的 onOptionsItemSelected 方法");
                }
            }
        });


        // 设置menu图标有关逻辑：
        int toolbarHeight = mActivity.getResources().getDimensionPixelSize(androidx.appcompat.R.dimen.abc_action_bar_default_height_material);
        int marginPX = ABadgeUIUtil.convertDpToPx(mActivity, 20);
        // 宽高默认40dp，可以根据实际情况调整，计算方式：总高度(默认60dp)-topMargin(10dp)-bottomMargin(10dp)=40dp
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                toolbarHeight - marginPX, toolbarHeight - marginPX);
//        layoutParams.leftMargin = ABadgeUIUtil.convertDpToPx(activity, 10);
        layoutParams.topMargin = ABadgeUIUtil.convertDpToPx(mActivity, 10);
        layoutParams.bottomMargin = ABadgeUIUtil.convertDpToPx(mActivity, 10);
        imageView.setLayoutParams(layoutParams);

        Drawable drawableIcon = menuItem.getIcon();
        if (imageView != null) {
            if (iconNormal != null) { // 如果代码传入drawable，就用这个作为menu的图标
//                imageView.setImageDrawable(icon);
                ABadgeUIUtil.buildImageDrawable(imageView, iconNormal, iconPressed);
            } else {
                if (drawableIcon != null) { // 如果menu设置了icon属性，就从menu中获取图标
//                    imageView.setImageDrawable(drawableIcon);
//                    ABadgeUIUtil.setBackground(imageView, drawableIcon);
                    ABadgeUIUtil.buildImageDrawable(imageView, drawableIcon, drawableIcon);
                } else {
                    throw new IllegalArgumentException("The menu icon cannot be empty.");
                }
            }
        }

        // 设置badge文字和badge背景
        if (badgeView != null) { // 不为空，说明要设置badge
            if (TextUtils.isEmpty(badgeCount)) { // 没有输入任何值，则隐藏badge
                System.out.println(TAG + " badgeCount = null，没有输入任何值，则隐藏badge");
                badgeView.setVisibility(View.GONE);
            } else {  // 如果输入了内容，那么就分类讨论
                setTextBadge(badgeView, badgeCount, badgeStyleBean, badgeStyleDefault);
            }
        }

        menuItem.setVisible(true);
        return this;
    }


    /**
     * 隐藏指定的menu item
     *
     * @param menuItem
     */
    public void hideMenuItem(MenuItem menuItem) {
        menuItem.setVisible(false);
    }

    /**
     *
     * @param size The scaled pixel size.
     * @return
     */
    public ABadgeMenuItemHelper setTextSize(float size) {
        badgeView.setTextSize(size);
        return this;
    }

    public ABadgeMenuItemHelper setGravity(int gravity) {
//        badgeView.setGravity(gravity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = gravity;
        badgeView.setLayoutParams(layoutParams);
        return this;
    }

    public ABadgeMenuItemHelper setMargins(int left, int top, int right, int bottom) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = left;
        layoutParams.topMargin = top;
        layoutParams.rightMargin = right;
        layoutParams.bottomMargin = bottom;
        badgeView.setLayoutParams(layoutParams);
        return this;
    }

    public ABadgeMenuItemHelper setPaddings(int left, int top, int right, int bottom) {
        badgeView.setPadding(left, top, right, bottom);
        return this;
    }

    /**
     * 使用默认的badge样式
     *
     * @param badgeView
     * @param badgeCount
     * @param badgeStyleBean
     * @param badgeStyleDefault
     */
    private void setTextBadge(TextView badgeView, String badgeCount, ABadgeStyleBean badgeStyleBean, ABadgeStyleDefault badgeStyleDefault) {
        boolean isNumber = ABadgeUIUtil.isNumeric(badgeCount);
        if (isNumber) { // 如果是数字类型的字符串
            int parseInt = Integer.parseInt(badgeCount);
            if (parseInt > 0 && parseInt <= 99) { // 0到99之间的数字，使用圆形，显示输入的数字
                badgeView.setVisibility(View.VISIBLE);
                System.out.println(TAG + " 输入的数字 = " + parseInt + "，输入的是：0到99之间的数字");
                badgeView.setText(parseInt + "");
                badgeView.setTextColor(Color.WHITE);

                if (badgeStyleDefault != null) {
                    // textview使用默认badge样式
                    if (badgeStyleDefault.getTagName().equals("ROUND")) {
                        throw new IllegalArgumentException("Severe bug: the number of current badge is greater than 99, the type of [ABadgeStyleDefault] must be [ROUND_RECT].");
                    }
                    StateListDrawable stateListDrawable =
                            new ABadgeDrawableBuilder()
                                    .setDefaultBadgeStyle(badgeStyleDefault)
                                    .build(mActivity);
                    ABadgeUIUtil.setBackground(badgeView, stateListDrawable);
                }
                if (badgeStyleBean != null) {
                    StateListDrawable stateListDrawable =
                            new ABadgeDrawableBuilder()
                                    .badgeBackgroundDrawable(badgeStyleBean.getBadgeBackgroundDrawable())
                                    .corners(badgeStyleBean.getCorner())
                                    .colorNormal(badgeStyleBean.getColorNormal())
                                    .colorPressed(badgeStyleBean.getColorPressed())
                                    .strokeColorNormal(badgeStyleBean.getStrokeColor())
                                    .strokeColorPressed(badgeStyleBean.getStrokeColorPressed())
                                    .strokeWidth(badgeStyleBean.getStrokeWidth())
                                    .build(mActivity);
                    ABadgeUIUtil.setBackground(badgeView, stateListDrawable);
                }

            } else if (parseInt > 99) { // 大于99，使用圆角矩形，显示内容：99+
                System.out.println(TAG + " 输入的数字 = " + parseInt + "，输入的是：大于99的数字");
                badgeView.setVisibility(View.VISIBLE);
                badgeView.setText("99+");
                badgeView.setTextColor(Color.WHITE);

                if (badgeStyleDefault != null) {
                    // textview使用默认badge样式
                    if (badgeStyleDefault.getTagName().equals("ROUND")) {
                        throw new IllegalArgumentException("Severe bug: the number of current badge is greater than 99, the type of [ABadgeStyleDefault] must be [ROUND_RECT].");
                    }
                    StateListDrawable stateListDrawable2 =
                            new ABadgeDrawableBuilder()
                                    .setDefaultBadgeStyle(badgeStyleDefault)
                                    .build(mActivity);
                    ABadgeUIUtil.setBackground(badgeView, stateListDrawable2);
                }
                if (badgeStyleBean != null) {
                    StateListDrawable stateListDrawable2 =
                            new ABadgeDrawableBuilder()
                                    .badgeBackgroundDrawable(badgeStyleBean.getBadgeBackgroundDrawable())
                                    .corners(badgeStyleBean.getCorner())
                                    .colorNormal(badgeStyleBean.getColorNormal())
                                    .colorPressed(badgeStyleBean.getColorPressed())
                                    .strokeColorNormal(badgeStyleBean.getStrokeColor())
                                    .strokeColorPressed(badgeStyleBean.getStrokeColorPressed())
                                    .strokeWidth(badgeStyleBean.getStrokeWidth())
                                    .build(mActivity);
                    ABadgeUIUtil.setBackground(badgeView, stateListDrawable2);
                }

            } else if (parseInt <= 0) { // 小于等于0，不显示badge
                System.out.println(TAG + " 输入的数字 = " + parseInt + "， 输入的是：小于等于0， 隐藏badge");
                badgeView.setVisibility(View.GONE);
            }
        } else { // 不是纯数字类型的字符串，例如："测试"
            System.out.println(TAG + " 输入的字符串 = " + badgeCount + "，当前输入的不是纯数字类型的字符串");
            badgeView.setVisibility(View.VISIBLE);
            badgeView.setText(badgeCount);
            badgeView.setTextColor(Color.WHITE);

            if (badgeStyleDefault != null) {
                // textview使用默认badge样式
                if (badgeStyleDefault.getTagName().equals("ROUND")) {
                    throw new IllegalArgumentException("Severe bug: When the number of current badge is greater than 99, the type of [ABadgeStyleDefault] must be [ROUND_RECT].");
                }
                StateListDrawable stateListDrawable3 =
                        new ABadgeDrawableBuilder()
                                .setDefaultBadgeStyle(badgeStyleDefault)
                                .build(mActivity);
                ABadgeUIUtil.setBackground(badgeView, stateListDrawable3);
            }
            if (badgeStyleBean != null) {
                StateListDrawable stateListDrawable3 =
                        new ABadgeDrawableBuilder()
                                .badgeBackgroundDrawable(badgeStyleBean.getBadgeBackgroundDrawable())
                                .corners(badgeStyleBean.getCorner())
                                .colorNormal(badgeStyleBean.getColorNormal())
                                .colorPressed(badgeStyleBean.getColorPressed())
                                .strokeColorNormal(badgeStyleBean.getStrokeColor())
                                .strokeColorPressed(badgeStyleBean.getStrokeColorPressed())
                                .strokeWidth(badgeStyleBean.getStrokeWidth())
                                .build(mActivity);
                ABadgeUIUtil.setBackground(badgeView, stateListDrawable3);
            }

        }
    }

}
