package com.example.weatherforcastproject.base;

import android.app.Application;

import com.example.weatherforcastproject.utils.TypefaceUtil;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/OpenSans-Light.ttf");

    }
}
