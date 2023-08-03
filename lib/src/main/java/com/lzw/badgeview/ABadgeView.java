package com.lzw.badgeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.lzw.badgeview.annotation.ABadgeDragState;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chqiu
 * Email:qstumn@163.com
 */
public class ABadgeView extends View implements IABadge {
    protected int mColorBackground;
    protected int mColorBackgroundBorder;
    protected int mColorBadgeText;
    protected Drawable mDrawableBackground;
    protected Bitmap mBitmapClip;
    protected boolean mDrawableBackgroundClip;
    protected float mBackgroundBorderWidth;
    protected float mBadgeTextSize;
    protected float mBadgePadding;
    protected int mBadgeNumber;
    protected String mBadgeText;
    protected boolean mDraggable;
    protected boolean mDragging;
    protected boolean mExact;
    protected boolean mShowShadow;
    protected int mBadgeGravity;
    protected float mGravityOffsetX;
    protected float mGravityOffsetY;

    protected float mDefalutRadius;
    protected float mFinalDragDistance;
    protected int mDragQuadrant;
    protected boolean mDragOutOfRange;

    protected RectF mBadgeTextRect;
    protected RectF mBadgeBackgroundRect;
    protected Path mDragPath;

    protected Paint.FontMetrics mBadgeTextFontMetrics;

    protected PointF mBadgeCenter;
    protected PointF mDragCenter;
    protected PointF mRowBadgeCenter;
    protected PointF mControlPoint;

    protected List<PointF> mInnertangentPoints;

    protected View mTargetView;

    protected int mWidth;
    protected int mHeight;

    protected TextPaint mBadgeTextPaint;
    protected Paint mBadgeBackgroundPaint;
    protected Paint mBadgeBackgroundBorderPaint;

    protected ABadgeAnimator mAnimator;

    protected OnBadgeDragStateChangedListener mBadgeDragStateChangedListener;

    protected ViewGroup mActivityRoot;

    public ABadgeView(Context context) {
        this(context, null);
    }

    private ABadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private ABadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBadgeTextRect = new RectF();
        mBadgeBackgroundRect = new RectF();
        mDragPath = new Path();
        mBadgeCenter = new PointF();
        mDragCenter = new PointF();
        mRowBadgeCenter = new PointF();
        mControlPoint = new PointF();
        mInnertangentPoints = new ArrayList<>();
        mBadgeTextPaint = new TextPaint();
        mBadgeTextPaint.setAntiAlias(true);
        mBadgeTextPaint.setSubpixelText(true);
        mBadgeTextPaint.setFakeBoldText(true);
        mBadgeTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mBadgeBackgroundPaint = new Paint();
        mBadgeBackgroundPaint.setAntiAlias(true);
        mBadgeBackgroundPaint.setStyle(Paint.Style.FILL);
        mBadgeBackgroundBorderPaint = new Paint();
        mBadgeBackgroundBorderPaint.setAntiAlias(true);
        mBadgeBackgroundBorderPaint.setStyle(Paint.Style.STROKE);
        mColorBackground = 0xFFE84E40;
        mColorBadgeText = 0xFFFFFFFF;
        mBadgeTextSize = dp2px(getContext(), 11);
        mBadgePadding = dp2px(getContext(), 5);
        mBadgeNumber = 0;
        mBadgeGravity = Gravity.END | Gravity.TOP;
        mGravityOffsetX = dp2px(getContext(), 1);
        mGravityOffsetY = dp2px(getContext(), 1);
        mFinalDragDistance = dp2px(getContext(), 90);
        mShowShadow = true;
        mDrawableBackgroundClip = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslationZ(1000);
        }
    }

    //////////////////////////////////////////////////////////////////////////////
    ////////
    //////// set and get attrs.
    ////////
    //////////////////////////////////////////////////////////////////////////////


    @Override
    public IABadge bindTarget(final View targetView) {
        if (targetView == null) {
            throw new IllegalStateException("targetView can not be null");
        }
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        ViewParent targetParent = targetView.getParent();
        if (targetParent != null && targetParent instanceof ViewGroup) {
            mTargetView = targetView;
            if (targetParent instanceof BadgeContainer) {
                ((BadgeContainer) targetParent).addView(this);
            } else {
                ViewGroup targetContainer = (ViewGroup) targetParent;
                int index = targetContainer.indexOfChild(targetView);
                ViewGroup.LayoutParams targetParams = targetView.getLayoutParams();
                targetContainer.removeView(targetView);
                final BadgeContainer badgeContainer = new BadgeContainer(getContext());
                if (targetContainer instanceof RelativeLayout) {
                    badgeContainer.setId(targetView.getId());
                }
                targetContainer.addView(badgeContainer, index, targetParams);
                badgeContainer.addView(targetView);
                badgeContainer.addView(this);
            }
        } else {
            throw new IllegalStateException("targetView must have a parent");
        }
        return this;
    }

    @Override
    public View getTargetView() {
        return mTargetView;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * <p>设置精确模式</p>
     *
     * @param isExact
     * @return
     */
    @Override
    public IABadge setBadgeNumberExactMode(boolean isExact) {
        mExact = isExact;
//        if (mBadgeNumber > 99) {
//            setBadgeNumber(mBadgeNumber);
//        }
        return this;
    }

    @Override
    public boolean getBadgeNumberExactMode() {
        return mExact;
    }

    /**
     * set badge numbers.The badge number equals to zero badge will be hidden, less than zero show dot.
     * <p>badgeNumber等于零徽章将被隐藏，小于零显示小圆点</p>
     *
     * @param badgeNumber equal to zero badge will be hidden, less than zero show dot
     */
    @Override
    public IABadge setBadgeNumber(int badgeNumber) {
        mBadgeNumber = badgeNumber;
        if (mBadgeNumber < 0) {
            mBadgeText = "";
        } else if (mBadgeNumber == 0) {
            mBadgeText = null;
        } else if (mBadgeNumber > 0 && mBadgeNumber <= 99) {
            mBadgeText = String.valueOf(mBadgeNumber);
        } else if (mBadgeNumber > 99) {
            mBadgeText = mExact ? String.valueOf(mBadgeNumber) : "99+";
        }
        measureText();
        invalidate();
        return this;
    }

    @Override
    public int getBadgeNumber() {
        return mBadgeNumber;
    }

    private static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        // 匹配所有的数字，包括负数
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        boolean result = pattern.matcher(str).matches();
        return result;
    }

    @Override
    public IABadge setBadgeText(String badgeText) {
        boolean isNumber = isNumeric(badgeText);
        if (isNumber) { // 如果是数字类型的字符串，例如："99"
            int parseInt = Integer.parseInt(badgeText);
            setBadgeNumber(parseInt);
        } else {
            mBadgeText = badgeText;
        }
//        mBadgeNumber = 1;
//        measureText();
//        invalidate();
        return this;
    }

    @Override
    public String getBadgeText() {
        return mBadgeText;
    }

    @Override
    public IABadge setBadgeTextSize(float size, boolean isSpValue) {
        mBadgeTextSize = isSpValue ? dp2px(getContext(), size) : size;
        measureText();
        invalidate();
        return this;
    }

    @Override
    public float getBadgeTextSize(boolean isSpValue) {
        return isSpValue ? px2dp(getContext(), mBadgeTextSize) : mBadgeTextSize;
    }

    @Override
    public IABadge setBadgeTextColor(int color) {
        mColorBadgeText = color;
        invalidate();
        return this;
    }

    @Override
    public int getBadgeTextColor() {
        return mColorBadgeText;
    }

    @Override
    public IABadge setShowBadgeShadow(boolean showShadow) {
        mShowShadow = showShadow;
        invalidate();
        return this;
    }

    @Override
    public boolean getIsShowBadgeShadow() {
        return mShowShadow;
    }

    @Override
    public IABadge setBadgeBackgroundColor(int color) {
        mColorBackground = color;
        if (mColorBackground == Color.TRANSPARENT) {
            mBadgeTextPaint.setXfermode(null);
        } else {
            mBadgeTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        invalidate();
        return this;
    }

    @Override
    public int getBadgeBackgroundColor() {
        return mColorBackground;
    }


    @Override
    public IABadge setBadgeBackgroundDrawable(Drawable drawable) {
        return setBadgeBackgroundDrawable(drawable, false);
    }

    @Override
    public IABadge setBadgeBackgroundDrawable(Drawable drawable, boolean clip) {
        mDrawableBackgroundClip = clip;
        mDrawableBackground = drawable;
        createClipLayer();
        invalidate();
        return this;
    }

    @Override
    public Drawable getBadgeBackgroundDrawable() {
        return mDrawableBackground;
    }

    @Override
    public IABadge setBadgeBorderStroke(int color, float width, boolean isDpValue) {
        mColorBackgroundBorder = color;
        mBackgroundBorderWidth = isDpValue ? dp2px(getContext(), width) : width;
        invalidate();
        return this;
    }

    /**
     * @param gravity only support Gravity.START | Gravity.TOP , Gravity.END | Gravity.TOP ,
     *                Gravity.START | Gravity.BOTTOM , Gravity.END | Gravity.BOTTOM ,
     *                Gravity.CENTER , Gravity.CENTER | Gravity.TOP , Gravity.CENTER | Gravity.BOTTOM ,
     *                Gravity.CENTER | Gravity.START , Gravity.CENTER | Gravity.END
     */
    @Override
    public IABadge setBadgeGravity(int gravity) {
        if (gravity == (Gravity.START | Gravity.TOP) ||
                gravity == (Gravity.END | Gravity.TOP) ||
                gravity == (Gravity.START | Gravity.BOTTOM) ||
                gravity == (Gravity.END | Gravity.BOTTOM) ||
                gravity == (Gravity.CENTER) ||
                gravity == (Gravity.CENTER | Gravity.TOP) ||
                gravity == (Gravity.CENTER | Gravity.BOTTOM) ||
                gravity == (Gravity.CENTER | Gravity.START) ||
                gravity == (Gravity.CENTER | Gravity.END)) {
            mBadgeGravity = gravity;
            invalidate();
        } else {
            throw new IllegalStateException("only support Gravity.START | Gravity.TOP , Gravity.END | Gravity.TOP , " +
                    "Gravity.START | Gravity.BOTTOM , Gravity.END | Gravity.BOTTOM , Gravity.CENTER" +
                    " , Gravity.CENTER | Gravity.TOP , Gravity.CENTER | Gravity.BOTTOM ," +
                    "Gravity.CENTER | Gravity.START , Gravity.CENTER | Gravity.END");
        }
        return this;
    }

    @Override
    public int getBadgeGravity() {
        return mBadgeGravity;
    }

    @Override
    public IABadge setBadgePadding(float padding, boolean isDpValue) {
        mBadgePadding = isDpValue ? dp2px(getContext(), padding) : padding;
        createClipLayer();
        invalidate();
        return this;
    }

    @Override
    public float getBadgePadding(boolean isDpValue) {
        return isDpValue ? px2dp(getContext(), mBadgePadding) : mBadgePadding;
    }

    @Override
    public IABadge setBadgeGravityOffset(float offset, boolean isDpValue) {
        return setBadgeGravityOffset(offset, offset, isDpValue);
    }

    @Override
    public IABadge setBadgeGravityOffset(float offsetX, float offsetY, boolean isDpValue) {
        mGravityOffsetX = isDpValue ? dp2px(getContext(), offsetX) : offsetX;
        mGravityOffsetY = isDpValue ? dp2px(getContext(), offsetY) : offsetY;
        invalidate();
        return this;
    }

    @Override
    public float getBadgeGravityOffsetX(boolean isDpValue) {
        return isDpValue ? px2dp(getContext(), mGravityOffsetX) : mGravityOffsetX;
    }

    @Override
    public float getBadgeGravityOffsetY(boolean isDpValue) {
        return isDpValue ? px2dp(getContext(), mGravityOffsetY) : mGravityOffsetY;
    }


    //////////////////////////////////////////////////////////////////////////////
    ////////
    //////// draw badge text and background.
    ////////
    //////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onDraw(Canvas canvas) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.draw(canvas);
            return;
        }
        if (mBadgeText != null) {
            initPaints();
            float badgeRadius = getBadgeCircleRadius();
            float startCircleRadius = mDefalutRadius * (1 - getPointDistance
                    (mRowBadgeCenter, mDragCenter) / mFinalDragDistance);
            if (mDraggable && mDragging) {
                mDragQuadrant = getQuadrant(mDragCenter, mRowBadgeCenter);
                showShadowImpl(mShowShadow);
                if (mDragOutOfRange = startCircleRadius < dp2px(getContext(), 1.5f)) {
                    updataListener(ABadgeDragState.STATE_DRAGGING_OUT_OF_RANGE, "STATE_DRAGGING_OUT_OF_RANGE");
                    drawBadge(canvas, mDragCenter, badgeRadius);
                } else {
                    updataListener(ABadgeDragState.STATE_DRAGGING, "STATE_DRAGGING");
                    drawDragging(canvas, startCircleRadius, badgeRadius);
                    drawBadge(canvas, mDragCenter, badgeRadius);
                }
            } else {
                findBadgeCenter();
                drawBadge(canvas, mBadgeCenter, badgeRadius);
            }
        }
    }

    private void initPaints() {
        showShadowImpl(mShowShadow);
        mBadgeBackgroundPaint.setColor(mColorBackground);
        mBadgeBackgroundBorderPaint.setColor(mColorBackgroundBorder);
        mBadgeBackgroundBorderPaint.setStrokeWidth(mBackgroundBorderWidth);
        mBadgeTextPaint.setColor(mColorBadgeText);
        mBadgeTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void showShadowImpl(boolean showShadow) {
        int x = dp2px(getContext(), 1);
        int y = dp2px(getContext(), 1.5f);
        switch (mDragQuadrant) {
            case 1:
                x = dp2px(getContext(), 1);
                y = dp2px(getContext(), -1.5f);
                break;
            case 2:
                x = dp2px(getContext(), -1);
                y = dp2px(getContext(), -1.5f);
                break;
            case 3:
                x = dp2px(getContext(), -1);
                y = dp2px(getContext(), 1.5f);
                break;
            case 4:
                x = dp2px(getContext(), 1);
                y = dp2px(getContext(), 1.5f);
                break;
        }
        mBadgeBackgroundPaint.setShadowLayer(showShadow ? dp2px(getContext(), 2f)
                : 0, x, y, 0x33000000);
    }

    private void drawDragging(Canvas canvas, float startRadius, float badgeRadius) {
        float dy = mDragCenter.y - mRowBadgeCenter.y;
        float dx = mDragCenter.x - mRowBadgeCenter.x;
        mInnertangentPoints.clear();
        if (dx != 0) {
            double k1 = dy / dx;
            double k2 = -1 / k1;
            getInnertangentPoints(mDragCenter, badgeRadius, k2, mInnertangentPoints);
            getInnertangentPoints(mRowBadgeCenter, startRadius, k2, mInnertangentPoints);
        } else {
            getInnertangentPoints(mDragCenter, badgeRadius, 0d, mInnertangentPoints);
            getInnertangentPoints(mRowBadgeCenter, startRadius, 0d, mInnertangentPoints);
        }
        mDragPath.reset();
        mDragPath.addCircle(mRowBadgeCenter.x, mRowBadgeCenter.y, startRadius,
                mDragQuadrant == 1 || mDragQuadrant == 2 ? Path.Direction.CCW : Path.Direction.CW);
        mControlPoint.x = (mRowBadgeCenter.x + mDragCenter.x) / 2.0f;
        mControlPoint.y = (mRowBadgeCenter.y + mDragCenter.y) / 2.0f;
        mDragPath.moveTo(mInnertangentPoints.get(2).x, mInnertangentPoints.get(2).y);
        mDragPath.quadTo(mControlPoint.x, mControlPoint.y, mInnertangentPoints.get(0).x, mInnertangentPoints.get(0).y);
        mDragPath.lineTo(mInnertangentPoints.get(1).x, mInnertangentPoints.get(1).y);
        mDragPath.quadTo(mControlPoint.x, mControlPoint.y, mInnertangentPoints.get(3).x, mInnertangentPoints.get(3).y);
        mDragPath.lineTo(mInnertangentPoints.get(2).x, mInnertangentPoints.get(2).y);
        mDragPath.close();
        canvas.drawPath(mDragPath, mBadgeBackgroundPaint);

        //draw dragging border
        if (mColorBackgroundBorder != 0 && mBackgroundBorderWidth > 0) {
            mDragPath.reset();
            mDragPath.moveTo(mInnertangentPoints.get(2).x, mInnertangentPoints.get(2).y);
            mDragPath.quadTo(mControlPoint.x, mControlPoint.y, mInnertangentPoints.get(0).x, mInnertangentPoints.get(0).y);
            mDragPath.moveTo(mInnertangentPoints.get(1).x, mInnertangentPoints.get(1).y);
            mDragPath.quadTo(mControlPoint.x, mControlPoint.y, mInnertangentPoints.get(3).x, mInnertangentPoints.get(3).y);
            float startY;
            float startX;
            if (mDragQuadrant == 1 || mDragQuadrant == 2) {
                startX = mInnertangentPoints.get(2).x - mRowBadgeCenter.x;
                startY = mRowBadgeCenter.y - mInnertangentPoints.get(2).y;
            } else {
                startX = mInnertangentPoints.get(3).x - mRowBadgeCenter.x;
                startY = mRowBadgeCenter.y - mInnertangentPoints.get(3).y;
            }
            float startAngle = 360 - (float) radianToAngle(getTanRadian(Math.atan(startY / startX),
                    mDragQuadrant - 1 == 0 ? 4 : mDragQuadrant - 1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDragPath.addArc(mRowBadgeCenter.x - startRadius, mRowBadgeCenter.y - startRadius,
                        mRowBadgeCenter.x + startRadius, mRowBadgeCenter.y + startRadius, startAngle,
                        180);
            } else {
                mDragPath.addArc(new RectF(mRowBadgeCenter.x - startRadius, mRowBadgeCenter.y - startRadius,
                        mRowBadgeCenter.x + startRadius, mRowBadgeCenter.y + startRadius), startAngle, 180);
            }
            canvas.drawPath(mDragPath, mBadgeBackgroundBorderPaint);
        }
    }


    private void drawBadge(Canvas canvas, PointF center, float radius) {
        if (center.x == -1000 && center.y == -1000) {
            return;
        }
//        if (mBadgeText.isEmpty() || mBadgeText.length() == 1) { // 文字内容为空显示小圆点，只有1位数时显示小圆点
        if (mBadgeText.isEmpty() || isNumeric(mBadgeText)) {// 文字内容为空显示小圆点，数字在0到99之间也显示小圆点
            mBadgeBackgroundRect.left = center.x - (int) radius;
            mBadgeBackgroundRect.top = center.y - (int) radius;
            mBadgeBackgroundRect.right = center.x + (int) radius;
            mBadgeBackgroundRect.bottom = center.y + (int) radius;
            if (mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawCircle(center.x, center.y, radius, mBadgeBackgroundPaint);
                if (mColorBackgroundBorder != 0 && mBackgroundBorderWidth > 0) {
                    canvas.drawCircle(center.x, center.y, radius, mBadgeBackgroundBorderPaint);
                }
            }
        } else {
            mBadgeBackgroundRect.left = center.x - (mBadgeTextRect.width() / 2f + mBadgePadding);
            mBadgeBackgroundRect.top = center.y - (mBadgeTextRect.height() / 2f + mBadgePadding * 0.5f);
            mBadgeBackgroundRect.right = center.x + (mBadgeTextRect.width() / 2f + mBadgePadding);
            mBadgeBackgroundRect.bottom = center.y + (mBadgeTextRect.height() / 2f + mBadgePadding * 0.5f);
            radius = mBadgeBackgroundRect.height() / 2f;
            if (mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawRoundRect(mBadgeBackgroundRect, radius, radius, mBadgeBackgroundPaint);
                if (mColorBackgroundBorder != 0 && mBackgroundBorderWidth > 0) {
                    canvas.drawRoundRect(mBadgeBackgroundRect, radius, radius, mBadgeBackgroundBorderPaint);
                }
            }
        }
        if (!mBadgeText.isEmpty()) {
            canvas.drawText(mBadgeText, center.x,
                    (mBadgeBackgroundRect.bottom + mBadgeBackgroundRect.top
                            - mBadgeTextFontMetrics.bottom - mBadgeTextFontMetrics.top) / 2f,
                    mBadgeTextPaint);
        }
    }

    private void drawBadgeBackground(Canvas canvas) {
        mBadgeBackgroundPaint.setShadowLayer(0, 0, 0, 0);
        int left = (int) mBadgeBackgroundRect.left;
        int top = (int) mBadgeBackgroundRect.top;
        int right = (int) mBadgeBackgroundRect.right;
        int bottom = (int) mBadgeBackgroundRect.bottom;
        if (mDrawableBackgroundClip) {
            right = left + mBitmapClip.getWidth();
            bottom = top + mBitmapClip.getHeight();
            canvas.saveLayer(left, top, right, bottom, null, Canvas.ALL_SAVE_FLAG);
        }
        mDrawableBackground.setBounds(left, top, right, bottom);
        mDrawableBackground.draw(canvas);
        if (mDrawableBackgroundClip) {
            mBadgeBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(mBitmapClip, left, top, mBadgeBackgroundPaint);
            canvas.restore();
            mBadgeBackgroundPaint.setXfermode(null);

//            if (mBadgeText.isEmpty() || mBadgeText.length() == 1) { // badge文字是空，或者是一个数字时，画圆形背景
            if (mBadgeText.isEmpty() || isNumeric(mBadgeText)) {// 文字内容为空显示小圆点，数字在0到99之间也显示小圆点
                canvas.drawCircle(mBadgeBackgroundRect.centerX(), mBadgeBackgroundRect.centerY(),
                        mBadgeBackgroundRect.width() / 2f, mBadgeBackgroundBorderPaint);
            } else { // 其他情况画圆角矩形
                canvas.drawRoundRect(mBadgeBackgroundRect,
                        mBadgeBackgroundRect.height() / 2, mBadgeBackgroundRect.height() / 2,
                        mBadgeBackgroundBorderPaint);
            }
        } else {
            canvas.drawRect(mBadgeBackgroundRect, mBadgeBackgroundBorderPaint);
        }
    }

    private void createClipLayer() {
        if (mBadgeText == null) {
            return;
        }
        if (!mDrawableBackgroundClip) {
            return;
        }
        if (mBitmapClip != null && !mBitmapClip.isRecycled()) {
            mBitmapClip.recycle();
        }
        float radius = getBadgeCircleRadius();
//            if (mBadgeText.isEmpty() || mBadgeText.length() == 1) { // badge文字是空，或者是一个数字时，画圆形背景
        if (mBadgeText.isEmpty() || isNumeric(mBadgeText)) { // 文字内容为空显示小圆点，数字在0到99之间也显示小圆点
            mBitmapClip = Bitmap.createBitmap((int) radius * 2, (int) radius * 2,
                    Bitmap.Config.ARGB_4444);
            Canvas srcCanvas = new Canvas(mBitmapClip);
            srcCanvas.drawCircle(srcCanvas.getWidth() / 2f, srcCanvas.getHeight() / 2f,
                    srcCanvas.getWidth() / 2f, mBadgeBackgroundPaint);
        } else {
            mBitmapClip = Bitmap.createBitmap((int) (mBadgeTextRect.width() + mBadgePadding * 2),
                    (int) (mBadgeTextRect.height() + mBadgePadding), Bitmap.Config.ARGB_4444);
            Canvas srcCanvas = new Canvas(mBitmapClip);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                srcCanvas.drawRoundRect(0, 0, srcCanvas.getWidth(), srcCanvas.getHeight(), srcCanvas.getHeight() / 2f,
                        srcCanvas.getHeight() / 2f, mBadgeBackgroundPaint);
            } else {
                srcCanvas.drawRoundRect(new RectF(0, 0, srcCanvas.getWidth(), srcCanvas.getHeight()),
                        srcCanvas.getHeight() / 2f, srcCanvas.getHeight() / 2f, mBadgeBackgroundPaint);
            }
        }
    }

    private float getBadgeCircleRadius() {
        if (mBadgeText.isEmpty()) {
            return mBadgePadding;
//        } else if (mBadgeText.length() == 1) {
        } else if (mBadgeText.length() == 2) {
            return mBadgeTextRect.height() > mBadgeTextRect.width() ?
                    mBadgeTextRect.height() / 2f + mBadgePadding * 0.5f :
                    mBadgeTextRect.width() / 2f + mBadgePadding * 0.5f;
        } else {
            return mBadgeBackgroundRect.height() / 2f;
        }
    }

    // todo:
    //  默认没有区分上下左右边距，需要修改一下：
    private void findBadgeCenter() {
        float rectWidth = mBadgeTextRect.height() > mBadgeTextRect.width() ?
                mBadgeTextRect.height() : mBadgeTextRect.width();
        switch (mBadgeGravity) {
            case Gravity.START | Gravity.TOP:
                mBadgeCenter.x = mGravityOffsetX + mBadgePadding + rectWidth / 2f;
                mBadgeCenter.y = mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f;
                break;
            case Gravity.START | Gravity.BOTTOM:
                mBadgeCenter.x = mGravityOffsetX + mBadgePadding + rectWidth / 2f;
                mBadgeCenter.y = mHeight - (mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f);
                break;
            case Gravity.END | Gravity.TOP:
                mBadgeCenter.x = mWidth - (mGravityOffsetX + mBadgePadding + rectWidth / 2f);
                mBadgeCenter.y = mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f;
                break;
            case Gravity.END | Gravity.BOTTOM:
                mBadgeCenter.x = mWidth - (mGravityOffsetX + mBadgePadding + rectWidth / 2f);
                mBadgeCenter.y = mHeight - (mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f);
                break;
            case Gravity.CENTER:
                mBadgeCenter.x = mWidth / 2f;
                mBadgeCenter.y = mHeight / 2f;
                break;
            case Gravity.CENTER | Gravity.TOP:
                mBadgeCenter.x = mWidth / 2f;
                mBadgeCenter.y = mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f;
                break;
            case Gravity.CENTER | Gravity.BOTTOM:
                mBadgeCenter.x = mWidth / 2f;
                mBadgeCenter.y = mHeight - (mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f);
                break;
            case Gravity.CENTER | Gravity.START:
                mBadgeCenter.x = mGravityOffsetX + mBadgePadding + rectWidth / 2f;
                mBadgeCenter.y = mHeight / 2f;
                break;
            case Gravity.CENTER | Gravity.END:
                mBadgeCenter.x = mWidth - (mGravityOffsetX + mBadgePadding + rectWidth / 2f);
                mBadgeCenter.y = mHeight / 2f;
                break;
        }
        initRowBadgeCenter();
    }

    private void measureText() {
        mBadgeTextRect.left = 0;
        mBadgeTextRect.top = 0;
        if (TextUtils.isEmpty(mBadgeText)) {
            mBadgeTextRect.right = 0;
            mBadgeTextRect.bottom = 0;
        } else {
            mBadgeTextPaint.setTextSize(mBadgeTextSize);
            mBadgeTextRect.right = mBadgeTextPaint.measureText(mBadgeText);
            mBadgeTextFontMetrics = mBadgeTextPaint.getFontMetrics();
            mBadgeTextRect.bottom = mBadgeTextFontMetrics.descent - mBadgeTextFontMetrics.ascent;
        }
        createClipLayer();
    }


    //////////////////////////////////////////////////////////////////////////////
    ////////
    //////// about touch and drag
    ////////
    //////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mActivityRoot == null) {
            findViewRoot(mTargetView);
        }
    }

    private void findViewRoot(View view) {
        mActivityRoot = (ViewGroup) view.getRootView();
        if (mActivityRoot == null) {
            findActivityRoot(view);
        }
    }

    private void findActivityRoot(View view) {
        if (view.getParent() != null && view.getParent() instanceof View) {
            findActivityRoot((View) view.getParent());
        } else if (view instanceof ViewGroup) {
            mActivityRoot = (ViewGroup) view;
        }
    }

    @Override
    public IABadge setBadgeIsDraggable(boolean draggable) {
        this.mDraggable = draggable;
        return this;
    }

    @Override
    public boolean getBadgeIsDraggable() {
        return this.mDraggable;
    }

    @Override
    public IABadge setOnBadgeDragStateChangedListener(OnBadgeDragStateChangedListener listener) {
//        mDraggable = listener != null;
//        this.mBadgeDragStateChangedListener = listener;
        if (this.mDraggable) {
            if (listener == null) {
                throw new IllegalArgumentException("The parameter [OnDragStateChangedListener] cannot be null.");
            } else {
                this.mBadgeDragStateChangedListener = listener;
            }
        } else {
            throw new IllegalArgumentException("If you don't drag the badge, you don't need to invoke the [setOnDragStateChangedListener] method.");
        }
        return this;
    }

    @Override
    public PointF getBadgeDragCenter() {
        if (mDraggable && mDragging) return mDragCenter;
        return null;
    }

    @Override
    public void resetBadge() {
        mDragCenter.x = -1000;
        mDragCenter.y = -1000;
        mDragQuadrant = 4;
        screenFromWindow(false);
        getParent().requestDisallowInterceptTouchEvent(false);
        invalidate();
    }

    @Override
    public void hideBadge(boolean animate) {
        if (animate && mActivityRoot != null) {
            initRowBadgeCenter();
            animateHide(mRowBadgeCenter);
        } else {
            setBadgeNumber(0);
        }
    }

    protected void animateHide(PointF center) {
        if (mBadgeText == null) {
            return;
        }
        if (mAnimator == null || !mAnimator.isRunning()) {
            screenFromWindow(true);
            mAnimator = new ABadgeAnimator(createBadgeBitmap(), center, this);
            mAnimator.start();
            setBadgeNumber(0);
        }
    }

    protected Bitmap createBadgeBitmap() {
        Bitmap bitmap = Bitmap.createBitmap((int) mBadgeBackgroundRect.width() + dp2px(getContext(), 3),
                (int) mBadgeBackgroundRect.height() + dp2px(getContext(), 3), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawBadge(canvas, new PointF(canvas.getWidth() / 2f, canvas.getHeight() / 2f), getBadgeCircleRadius());
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                float x = event.getX();
                float y = event.getY();
                if (mDraggable && event.getPointerId(event.getActionIndex()) == 0
                        && (x > mBadgeBackgroundRect.left && x < mBadgeBackgroundRect.right &&
                        y > mBadgeBackgroundRect.top && y < mBadgeBackgroundRect.bottom)
                        && mBadgeText != null) {
                    initRowBadgeCenter();
                    mDragging = true;
                    updataListener(ABadgeDragState.STATE_START, "STATE_START");
                    mDefalutRadius = dp2px(getContext(), 7);
                    getParent().requestDisallowInterceptTouchEvent(true);
                    screenFromWindow(true);
                    mDragCenter.x = event.getRawX();
                    mDragCenter.y = event.getRawY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDragging) {
                    mDragCenter.x = event.getRawX();
                    mDragCenter.y = event.getRawY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                if (event.getPointerId(event.getActionIndex()) == 0 && mDragging) {
                    mDragging = false;
                    onPointerUp();
                }
                break;
        }
        return mDragging || super.onTouchEvent(event);
    }

    private void initRowBadgeCenter() {
        int[] screenPoint = new int[2];
        getLocationOnScreen(screenPoint);
        mRowBadgeCenter.x = mBadgeCenter.x + screenPoint[0];
        mRowBadgeCenter.y = mBadgeCenter.y + screenPoint[1];
    }

    private void updataListener(int state, String stateTag) {
        if (mBadgeDragStateChangedListener != null) {
            mBadgeDragStateChangedListener.onBadgeDragStateChanged(state, stateTag, this, mTargetView);
        }
    }

    protected void screenFromWindow(boolean screen) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        if (screen) {
            mActivityRoot.addView(this, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
        } else {
            bindTarget(mTargetView);
        }
    }

    private void onPointerUp() {
        if (mDragOutOfRange) {
            animateHide(mDragCenter);
            updataListener(ABadgeDragState.STATE_SUCCEED, "STATE_SUCCEED");
        } else {
            resetBadge();
            updataListener(ABadgeDragState.STATE_CANCELED, "STATE_CANCELED");
        }
    }


    //////////////////////////////////////////////////////////////////////////////
    ////////
    ////////  container
    ////////
    //////////////////////////////////////////////////////////////////////////////

    private class BadgeContainer extends ViewGroup {

        @Override
        protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
            if (!(getParent() instanceof RelativeLayout)) {
                super.dispatchRestoreInstanceState(container);
            }
        }

        public BadgeContainer(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            View targetView = null, badgeView = null;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!(child instanceof ABadgeView)) {
                    targetView = child;
                } else {
                    badgeView = child;
                }
            }
            if (targetView == null) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                targetView.measure(widthMeasureSpec, heightMeasureSpec);
                if (badgeView != null) {
                    badgeView.measure(MeasureSpec.makeMeasureSpec(targetView.getMeasuredWidth(), MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(targetView.getMeasuredHeight(), MeasureSpec.EXACTLY));
                }
                setMeasuredDimension(targetView.getMeasuredWidth(), targetView.getMeasuredHeight());
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////
    //////// 
    //////// utils
    //////// 
    //////////////////////////////////////////////////////////////////////////////

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private static final double CIRCLE_RADIAN = 2 * Math.PI;

    private double getTanRadian(double atan, int quadrant) {
        if (atan < 0) {
            atan += CIRCLE_RADIAN / 4;
        }
        atan += CIRCLE_RADIAN / 4 * (quadrant - 1);
        return atan;
    }

    private double radianToAngle(double radian) {
        return 360 * (radian / CIRCLE_RADIAN);
    }

    private int getQuadrant(PointF p, PointF center) {
        if (p.x > center.x) {
            if (p.y > center.y) {
                return 4;
            } else if (p.y < center.y) {
                return 1;
            }
        } else if (p.x < center.x) {
            if (p.y > center.y) {
                return 3;
            } else if (p.y < center.y) {
                return 2;
            }
        }
        return -1;
    }

    private float getPointDistance(PointF p1, PointF p2) {
        return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    /**
     * this formula is designed by mabeijianxi
     * website : http://blog.csdn.net/mabeijianxi/article/details/50560361
     *
     * @param circleCenter The circle center point.
     * @param radius       The circle radius.
     * @param slopeLine    The slope of line which cross the pMiddle.
     */
    private void getInnertangentPoints(PointF circleCenter, float radius, Double slopeLine, List<PointF> points) {
        float radian, xOffset, yOffset;
        if (slopeLine != null) {
            radian = (float) Math.atan(slopeLine);
            xOffset = (float) (Math.cos(radian) * radius);
            yOffset = (float) (Math.sin(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points.add(new PointF(circleCenter.x + xOffset, circleCenter.y + yOffset));
        points.add(new PointF(circleCenter.x - xOffset, circleCenter.y - yOffset));
    }

}