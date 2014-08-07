package com.okry.ppw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by mr on 14-8-5.
 * An extended PopupWindow which could add a pointer to the anchor view.
 * You could set your own pointer image,
 * this widget will compute the pointer location for you automatically.
 */
public class PointerPopupWindow extends PopupWindow {

    private LinearLayout mContainer;
    private ImageView mAnchorImage;
    private FrameLayout mContent;
    private int mScreenPadding;
    private AlignMode mAlignMode = AlignMode.DEFAULT;

    public PointerPopupWindow(Context context, int width) {
        this(context, width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public PointerPopupWindow(Context context, int width, int height) {
        super(width, height);
        mContainer = new LinearLayout(context);
        mContainer.setOrientation(LinearLayout.VERTICAL);
        mAnchorImage = new ImageView(context);
        mContent = new FrameLayout(context);
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
    }

    public AlignMode getAlignMode() {
        return mAlignMode;
    }

    public void setAlignMode(AlignMode mAlignMode) {
        this.mAlignMode = mAlignMode;
    }

    public AlignMode getOffsetMode() {
        return mAlignMode;
    }

    public void setOffsetMode(AlignMode mAlignMode) {
        this.mAlignMode = mAlignMode;
    }

    public int getScreenPadding() {
        return mScreenPadding;
    }

    public void setScreenPadding(int mScreenPadding) {
        this.mScreenPadding = mScreenPadding;
    }

    public void setPointerImageDrawable(Drawable d) {
        mAnchorImage.setImageDrawable(d);
    }

    public void setPointerImageRes(int res) {
        mAnchorImage.setImageResource(res);
    }

    public void setPointerImageBitmap(Bitmap bitmap) {
        mAnchorImage.setImageBitmap(bitmap);
    }

    @Override
    public void setContentView(View contentView) {
        if (contentView != null) {
            mContainer.removeAllViews();
            mContainer.addView(mAnchorImage, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mContainer.addView(mContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mContent.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        super.setContentView(mContainer);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        //noinspection deprecation
        mContent.setBackgroundDrawable(background);
        super.setBackgroundDrawable(new ColorDrawable());
    }

    public void showAsPointer(View anchor) {
        showAsPointer(anchor, 0, 0);
    }

    public void showAsPointer(View anchor, int yoff) {
        showAsPointer(anchor, 0, yoff);
    }

    public void showAsPointer(View anchor, int xoff, int yoff) {
        // get location and size
        final Rect displayFrame = new Rect();
        anchor.getWindowVisibleDisplayFrame(displayFrame);
        final int displayFrameWidth = displayFrame.right - displayFrame.left;
        int[] loc = new int[2];
        anchor.getLocationInWindow(loc);//get anchor location
        if (mAlignMode == AlignMode.AUTO_OFFSET) {
            // 计算anchor相对于屏幕中心的偏移量
            float offCenterRate = (displayFrame.centerX() - loc[0]) / (float) displayFrameWidth;
            xoff = (int) ((anchor.getWidth() - getWidth()) / 2 + offCenterRate * getWidth() / 2);
        } else if (mAlignMode == AlignMode.AUTO_OFFSET.CENTER_FIX) {
            xoff = (anchor.getWidth() - getWidth()) / 2;
        }
        // 计算x轴偏移量，使箭头可以对准anchor
        int left = loc[0] + xoff;
        int right = left + getWidth();
        // 重置x轴偏移，使弹出框处于可视范围内
        if (right > displayFrameWidth - mScreenPadding) {
            xoff = (displayFrameWidth - mScreenPadding - getWidth()) - loc[0];
        }
        if (left < displayFrame.left + mScreenPadding) {
            xoff = displayFrame.left + mScreenPadding - loc[0];
        }
        computePointerLocation(anchor, xoff);
        super.showAsDropDown(anchor, xoff, yoff);
    }

    private void computePointerLocation(View anchor, int xoff) {
        int aw = anchor.getWidth();
        int dw = mAnchorImage.getDrawable().getIntrinsicWidth();
        mAnchorImage.setPadding((aw - dw) / 2 - xoff, 0, 0, 0);
    }

    @Deprecated
    /**
     * won't take effect in this widget,
     */
    public void setClippingEnabled(boolean enabled) {
        super.setClippingEnabled(enabled);
    }

    public static enum AlignMode {
        /**
         * default align mode,align the left|bottom of the anchor view
         */
        DEFAULT,
        /**PopupWindowMain
         * align center of the anchor view
         */
        CENTER_FIX,
        /**
         * according to the location of the anchor view in the display window,
         * auto offset the popup window to display center.
         */
        AUTO_OFFSET
    }


}
