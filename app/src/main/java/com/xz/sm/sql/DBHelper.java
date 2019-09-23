package com.xz.sm.sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String name="record";//数据库名
    private static final int version=1;//版本号

    public DBHelper(Context context) {
        //1：上下文 2：数据库名称 3：游标创建工厂 4：数据库版本 版本只能是整数
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table history( danhao CHAR, bianhao CHAR, timestamp BIGINT )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
