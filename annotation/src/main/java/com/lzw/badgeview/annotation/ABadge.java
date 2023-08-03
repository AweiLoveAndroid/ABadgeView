package com.lzw.badgeview.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * badge annotation.Used in class.
 * <p>Use the bytecode of those classes that require add badges functionality as a parameter in the ABadge annotation.
 * <pThe following code block provides an example, and the corresponding lines can be deleted if not needed.)></p>
 * <pre>
 * @ABadge({
 *    View.class, // equeals: com.lzw.badgeview.ABadgeView, if you don't use it, just delete this line.
 *    ImageView.class, // equeals: com.lzw.badgeview.ABadgeImageVieww, if you don't use it, just delete this line.
 *    TextView.class, // equeals: com.lzw.badgeview.ABadgeFloatingTextView, if you don't use it, just delete this line.
 *    RadioButton.class, // equeals: com.lzw.badgeview.ABadgeRadioButton, if you don't use it, just delete this line.
 *    LinearLayout.class, // equeals: com.lzw.badgeview.ABadgeLinearLayout, if you don't use it, just delete this line.
 *    FrameLayout.class, // equeals: com.lzw.badgeview.ABadgeFrameLayout, if you don't use it, just delete this line.
 *    RelativeLayout.class, // equeals: com.lzw.badgeview.ABadgeRelativeLayout, if you don't use it, just delete this line.
 *    FloatingActionButton.class, // equeals: com.lzw.badgeview.ABadgeFloatingActionButton, if you don't use it, just delete this line.
 *    ...
 * })
 * public class ABadgeInit {
 * }
 * </pre>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ABadge {
    Class<? extends View>[] value() default {};
}