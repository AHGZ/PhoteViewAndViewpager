package com.hgz.test.zhinengxiazai;

import android.content.Context;

import com.hgz.test.zhinengxiazai.app.MyApplication;

/**
 * Created by Administrator on 2017/8/10.
 */

public class NetUtils {
    public static final String SP_NAME="SP_NAME";
    public  static final String PICTURE_LOAD_MODE_KEY="PICTURE_LOAD_MODE_KEY";

    private static final String BASE_URL_BIG_PICTURE="http://www.big.picture";
    private static final String BASE_URL_ZHINENG_PICTURE="http://www.zhineng.picture";
    private static final String BASE_URL_NO_PICTURE="http://www.no.picture";
    private static NetUtils mNetUtils;
    private boolean isConnectionState=true;
    private static String BASE_URL=BASE_URL_BIG_PICTURE;

    private NetUtils(){

    }

    //单例模式
    public static NetUtils getInstance(){
        if (mNetUtils==null){
            synchronized (NetUtils.class){
                mNetUtils=new NetUtils();
            }

        }
        return  mNetUtils;
    }
    public String getBASE_URL(){

        if (isConnectionState){
            int mode = MyApplication.getAppContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getInt(PICTURE_LOAD_MODE_KEY, 0);
            switch (mode){
                case 0:
                    BASE_URL=BASE_URL_BIG_PICTURE;
                    break;

                case 1:
                    BASE_URL=BASE_URL_ZHINENG_PICTURE;
                    break;

                case 2:
                    BASE_URL=BASE_URL_NO_PICTURE;
                    break;
            }
        }else{
            BASE_URL=BASE_URL_BIG_PICTURE;
        }
        return BASE_URL;
    }
    public void changeNetWorkState(boolean isConnectionState){
        this.isConnectionState=isConnectionState;
    }
}
