package com.hgz.test.tishigengxinbanbenxiazai.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2017/8/19.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
