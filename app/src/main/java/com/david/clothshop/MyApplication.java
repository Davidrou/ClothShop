package com.david.clothshop;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by luxiaolin on 17/12/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

    }
}
