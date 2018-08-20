package com.example.user.screenutil;

import android.app.Application;

import com.example.user.screenutil.screen.ScreenUtils;

public class MyApplication extends Application{
    private static MyApplication myApplication = null;
    @Override
    public void onCreate() {
        super.onCreate();

        ScreenUtils.activateScreenAdapt(this);
        myApplication = this;
    }


    public static MyApplication getApplication() {
        return myApplication;
    }
}
