package com.hgz.test.test.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.library.ChannelBean;
import com.hgz.test.test.SQLite;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/15.
 */

public class SQLiteDao {

    private final SQLiteDatabase db;
    private ChannelBean channelBean;

    public SQLiteDao(Context context){
        SQLite sqLite = new SQLite(context);
        db = sqLite.getWritableDatabase();

    }
    public boolean add(String name,int select){
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("select",select);
        long result = db.insert("pindao", null, values);
        if (result!=-1){
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<ChannelBean> findAll(){
        Cursor cursor = db.query(false, "pindao", null, null, null, null, null, null, null);
        ArrayList<ChannelBean> pinDaos = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int select = cursor.getInt(cursor.getColumnIndex("select"));
            if (select==0){
                channelBean.setSelect(true);
            }else if (select==1){
                channelBean.setSelect(false);
            }
            channelBean.setName(name);
            pinDaos.add(channelBean);
        }
        return pinDaos;
    }
}
