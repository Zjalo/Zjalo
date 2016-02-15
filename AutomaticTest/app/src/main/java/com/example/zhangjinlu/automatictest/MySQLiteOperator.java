package com.example.zhangjinlu.automatictest;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zhangjinlu on 2016/1/12.
 */
public class MySQLiteOperator {
    private static final String DATABASENAME = "/data/data/com.example.zhangjinlu.automatictest/settings.db";


    private SQLiteDatabase sqLiteDatabase = null;
    private Cursor cursor = null;

    private String[] tableName = {"system","secure","global"};

    public Cursor query(String name){
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DATABASENAME,null);
        if(sqLiteDatabase != null){

            for(String table:tableName){
            Cursor resultcursor = sqLiteDatabase.query(table,null
                    ,"name = ?",new String[]{name}, null, null, null);
                if(resultcursor != null && resultcursor.getCount() > 0){
                    cursor =resultcursor;
                }
            }
        }
        cursor.moveToFirst();
        return cursor;
    }

    public void close(){
        cursor.close();
        sqLiteDatabase.close();
    }

    public Cursor launcherQuery(){

        sqLiteDatabase = SQLiteDatabase.openDatabase("system/lib/uitechno/primary/compound.db",null, SQLiteDatabase.OPEN_READONLY);

        Cursor cursor = sqLiteDatabase.query("compound",new String[] {"intent"},"intent like ?",new String[] {"%component%"},null,null,null);
        cursor.moveToFirst();
        return cursor;
    }
}
