package com.stoyan.puzzlegame;

import android.graphics.drawable.Drawable;

public class DrawableInfo {
    private Drawable imageDrawable;
    private String tag;

    public DrawableInfo(Drawable drawable, String tag) {
        this.setImageDrawable(drawable);
        this.setTag(tag);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(Drawable imageDrawable) {
        this.imageDrawable = imageDrawable;
    }
}
