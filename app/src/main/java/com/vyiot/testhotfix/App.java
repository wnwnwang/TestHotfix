package com.vyiot.testhotfix;

import android.app.Application;

import com.vyiot.hotfix.HotFixManager;

public class App  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HotFixManager.getInstance().init(this);
        HotFixManager.getInstance().applyPatch();
    }
}
