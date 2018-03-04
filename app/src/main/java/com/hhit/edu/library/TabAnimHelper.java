package com.hhit.edu.library;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 标签动画类
 * Created by long on 2018/2/27
 * Tab动画帮助类，前四个静态方法是需要的后面就不用了
 */
public class TabAnimHelper {
    /**
     * 改变Tab的顶部偏移
     * @param view
     * @param fromPadding
     * @param toPadding
     */
    public static void changeTopPadding(final View view, int fromPadding, int toPadding) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromPadding, toPadding);//从哪到哪的动画效果
        animator.setDuration(150);//动画时常设置为0.15秒
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//动画监听
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                view.setPadding(view.getPaddingLeft(),//设置偏移
                        (int) animatedValue,
                        view.getPaddingRight(),
                        view.getPaddingBottom());
            }
        });
        animator.start();//动画开始
    }

    /**
     * 改变字体大小
     * @param textView
     * @param from
     * @param to
     */
    public static void changeTextSize(final TextView textView, float from, float to) {
        ValueAnimator textSizeChangeAnimator = ValueAnimator.ofFloat(from, to);
        textSizeChangeAnimator.setDuration(150);
        textSizeChangeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) valueAnimator.getAnimatedValue());
            }
        });
        textSizeChangeAnimator.start();
    }

    /**
     * 改变字体颜色
     * @param textView
     * @param fromColor
     * @param toColor
     */
    public static void changeTextColor(final TextView textView, int fromColor, int toColor) {
        ValueAnimator changeTextColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        changeTextColorAnimation.setDuration(150);
        changeTextColorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((Integer) animator.getAnimatedValue());
            }
        });
        changeTextColorAnimation.start();
    }

    /**
     * 改变视图大小
     * @param image
     * @param fromScale
     * @param toScale
     */
    public static void changeImageSize(ImageView image, float fromScale, float toScale) {
        ObjectAnimator scaleX;
        ObjectAnimator scaleY;
        scaleX = ObjectAnimator.ofFloat(image, "scaleX", fromScale, toScale);
        scaleY = ObjectAnimator.ofFloat(image, "scaleY", fromScale, toScale);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(150);
        set.playTogether(scaleX, scaleY);
        set.start();
    }

    /**
     * 从中心展开Drawable
     * @param image
     * @param drawable
     * @param isActivated
     */
    @SuppressWarnings("deprecation")
    public static void clipDrawable(final View image, Drawable drawable, boolean isActivated) {
        if (drawable == null) {
            return;
        }
        if (isActivated) {
            final ClipDrawable scaleDrawable = new ClipDrawable(drawable, Gravity.CENTER,
                    ClipDrawable.HORIZONTAL | ClipDrawable.VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                image.setBackground(scaleDrawable);
            } else {
                image.setBackgroundDrawable(scaleDrawable);
            }
            image.setBackgroundDrawable(scaleDrawable);
            ValueAnimator animator = ValueAnimator.ofInt(0, 10000);
            animator.setDuration(200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleDrawable.setLevel((Integer) animation.getAnimatedValue());
                }
            });
            animator.start();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                image.setBackground(null);
            } else {
                image.setBackgroundDrawable(null);
            }
        }
    }

    /**
     * 波纹动画
     * @param view
     * @param frontColor
     * @param behindColor
     * @param mode
     * @param isActivated
     */
    @SuppressWarnings("deprecation")
    public static void rippleDrawable(final View view, int frontColor, int behindColor, int mode, boolean isActivated) {
        if (isActivated) {
            RippleDrawable rippleDrawable = new RippleDrawable(frontColor, behindColor, mode);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(rippleDrawable);
            } else {
                view.setBackgroundDrawable(rippleDrawable);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(null);
            } else {
                view.setBackgroundDrawable(null);
            }
        }
    }
}
