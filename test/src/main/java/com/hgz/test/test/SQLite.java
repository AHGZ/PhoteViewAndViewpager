package com.hgz.test.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/8/15.
 */

public class SQLite extends SQLiteOpenHelper {
    public SQLite(Context context) {
        super(context, "pindao.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table pindao(_id Integer primary key autoincrement,name varchar(20),select Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
