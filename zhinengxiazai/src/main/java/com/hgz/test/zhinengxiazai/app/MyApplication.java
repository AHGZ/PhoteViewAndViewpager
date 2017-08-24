package com.hgz.test.zhinengxiazai.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/8/10.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
    }
    public static Context getAppContext(){
        return myApplication;
    }
}
