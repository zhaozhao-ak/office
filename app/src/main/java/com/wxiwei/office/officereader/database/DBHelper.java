/*
 * 文件名称:           DBHelper.java
 *  
 * 编译器:             android2.2
 * 时间:               下午4:26:40
 */
package com.wxiwei.office.officereader.database;

import com.wxiwei.office.constant.MainConstant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库，表
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2011-12-28
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class DBHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "wxiweiReader.db";

    /**
     * 
     * @param context
     */
    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    /**
     * 
     *(non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     *
     */
    public void onCreate(SQLiteDatabase db)
    {
        if (db != null)
        {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + MainConstant.TABLE_RECENT + " ('name' VARCHAR(30))");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + MainConstant.TABLE_STAR + " ('name' VARCHAR(30))");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + MainConstant.TABLE_SETTING + " ('count' VARCHAR(30))");
        }
    }
 
    /**
     * 
     *(non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     *
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (db != null)
        {
            db.execSQL("DROP TABLE IF EXISTS " + MainConstant.TABLE_RECENT);
            db.execSQL("DROP TABLE IF EXISTS " + MainConstant.TABLE_STAR);
            db.execSQL("DROP TABLE IF EXISTS " + MainConstant.TABLE_SETTING);
            onCreate(db);
        }
    }
  
    /**
     * 
     */
    public void dispose()
    {
        
    }
}
