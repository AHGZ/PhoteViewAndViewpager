package com.hgz.test.test.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

/**
 * Created by Administrator on 2017/8/4.
 */

public class MyApplication extends Application {
    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        UMShareAPI.get(this);
        if (getSharedPreferences("theme", MODE_PRIVATE).getBoolean("night_theme", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
