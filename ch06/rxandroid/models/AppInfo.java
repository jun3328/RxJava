package io.github.jesterz91.rxandroid.models;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private Drawable image;
    private String title;

    public AppInfo(Drawable image, String title) {
        this.image = image;
        this.title = title;
    }

    public Drawable getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "image=" + image +
                ", title='" + title + '\'' +
                '}';
    }
}
