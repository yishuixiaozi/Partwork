package com.hhit.edu.library;

import android.graphics.drawable.Drawable;

/**
 * Created by long on 2016/4/15.
 * Tab项
 */
public class TabItem {

    private String title;//字符标题
    private Drawable drawable;//应该对应的就是view
    private int imageRes;//整形变量图标

    /*构造方法1*/
    public TabItem(String title, Drawable drawable, int imageRes) {
        this.title = title;
        this.drawable = drawable;
        this.imageRes = imageRes;
    }
    /*构造方法2*/
    public TabItem(String title, int imageRes) {
        this.title = title;
        this.drawable = null;
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
