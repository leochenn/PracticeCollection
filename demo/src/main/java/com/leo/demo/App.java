package com.leo.demo;

import android.app.Application;
import android.content.Context;

import com.leo.utils.log.LogUtil;

/**
 * Created by Leo on 2019/7/10.
 */
public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.d(TAG, "onCreate", BuildConfig.DEBUG);

        if (BuildConfig.DEBUG) {
            DebugTools.init(this);
        }
    }
}
