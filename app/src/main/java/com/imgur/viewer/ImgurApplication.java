package com.imgur.viewer;

import android.app.Application;

public class ImgurApplication extends Application {

    private static ImgurApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static ImgurApplication getInstance() {
        return sInstance;
    }
}
