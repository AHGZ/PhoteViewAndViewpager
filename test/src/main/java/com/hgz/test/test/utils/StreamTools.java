package com.hgz.test.test.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/8/4.
 */

public class StreamTools {
    public static String getData(InputStream is){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len=0;
            while((len=is.read(buffer))!=-1){
                baos.write(buffer,0,len);
            }
            is.close();
            baos.close();
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
