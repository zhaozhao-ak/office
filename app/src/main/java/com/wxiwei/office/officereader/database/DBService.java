/*
 * 文件名称:           DBService.java
 *  
 * 编译器:             android2.2
 * 时间:               上午9:16:03
 */
package com.wxiwei.office.officereader.database;

import java.io.File;
import java.util.List;

import com.wxiwei.office.constant.MainConstant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 表插入记录，获取记录
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2011-12-29
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class DBService
{ 
    /**
     * 
     */
    public DBService(Context context)
    {
        createDataBase(context);
    }

    /**
     * 
     * @param context
     */
    public void createDataBase(Context context)
    {
        if (dbHelper == null)
        {
            dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db != null)
            {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + MainConstant.TABLE_RECENT + 
                    " ('name' VARCHAR(30))");
                db.execSQL("CREATE TABLE IF NOT EXISTS " + MainConstant.TABLE_STAR + 
                    " ('name' VARCHAR(30))");
                db.execSQL("CREATE TABLE IF NOT EXISTS " + MainConstant.TABLE_SETTING + 
                    " ('count' VARCHAR(30))");
            }
        }
    }
    
    /**
     * 插入一条记录
     * @param name
     */
    public void insertRecentFiles(String tableName, String name) 
    {
        if (queryItem(tableName, name))
        {
            deleteItem(tableName, name);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            Cursor cursor = db.rawQuery("select * from " + tableName, null);
            if (cursor != null)
            {
                deleteItems(tableName, cursor.getCount() - getRecentMax() + 1);
                cursor.close();
                db.execSQL("INSERT INTO " + tableName + " (name) values(?)", 
                    new Object[]{name});
            }
        }
    }

    /**
     * 插入一条记录
     */
    public void insertStarFiles(String tableName, String name)
    {
        if (queryItem(tableName, name))
        {
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            db.execSQL("INSERT INTO " + tableName + " (name) values(?)", 
                new Object[]{name});
        }
    }
    
    /**
     * 查询一条记录是否存在
     * @param name
     * @return
     */
    public boolean queryItem(String tableName, String name)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db == null)
        {
            return false;
        }
        else
        {
            Cursor cursor = db.rawQuery("select * from " + tableName + 
                " where name like ?", new String[]{name});
            if ( cursor != null && cursor.moveToFirst())
            {
                cursor.close();
                return true;
            }
            else
            {
                cursor.close();
                return false;
            }
        }
    }
    
    /**
     * 删除一条记录
     * @param name
     */
    public void deleteItem(String tableName, String name)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            db.execSQL("delete from " + tableName + " where name=?",  
                new Object[]{name});
        }
    }
    
    /**
     * 删除多条记录
     * @param name
     */
    public void deleteItems(String tableName, int count) 
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            Cursor cursor = db.rawQuery("select * from " + tableName, null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                while(cursor != null && count > 0)
                {
                    deleteItem(tableName, cursor.getString(0));
                    cursor.moveToNext();
                    count --;
                }
                cursor.close();
            }
        }
    }
  
    /**
     * 获取记录
     * @param fileList
     */
    public void get(String tableName, List<File> fileList)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            Cursor cursor = db.rawQuery("select * from " + tableName, null);
            if (cursor != null)
            {
                while (cursor.moveToNext()) 
                { 
                    File file = new File(cursor.getString(0));
                    if (file.exists())
                    {
                        fileList.add(file);
                    }
                    else
                    {
                        deleteItem(tableName, file.getAbsolutePath());
                    }
                } 
                cursor.close();
            }
        }
    }
    
    /**
     * 获取记录大小
     * @param name
     */
    public int getCount(String tableName) 
    {
        int count = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            Cursor cursor = db.rawQuery("select * from " + tableName, null);
            if (cursor != null)
            {
                count = cursor.getCount();
                cursor.close();
            }
        }
        return count;
    }
    
    /**
     * 获取最近打开文档的最大值
     * @return
     */
    public int getRecentMax()
    {
        int max = 10;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            Cursor cursor = db.rawQuery("select * from " + MainConstant.TABLE_SETTING, null);
            if (cursor != null)
            {
                if (cursor.moveToFirst())
                {
                    max = cursor.getInt(0);
                }
                else
                {
                    db.execSQL("INSERT INTO " + MainConstant.TABLE_SETTING + 
                        " (count) values(?)", new Object[]{10});
                }
                cursor.close();
            }
        }
        return max;
    }
    
    /**
     * 设置最近打开文档数
     * @param value
     */
    public void changeRecentCount(int value)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            Cursor cursor = db.rawQuery("select * from " + MainConstant.TABLE_SETTING, null);
            if (cursor != null)
            {
                if (cursor.moveToFirst())
                {
                    int count = cursor.getInt(0);
                    if (count != value)
                    {
                        db.execSQL("update " + MainConstant.TABLE_SETTING + " set count = " 
                            + value + " where count = " + count);
                        int has = getCount(MainConstant.TABLE_RECENT);
                        if (has > value)
                        {
                            deleteItems(MainConstant.TABLE_RECENT, has - value);
                        }
                    }
                }
                else
                {
                    db.execSQL("INSERT INTO " + MainConstant.TABLE_SETTING + 
                        " (count) values(?)", new Object[]{10});
                }
                cursor.close();
            }
        }
    }
  
    /**
     * 删除表
     * @param tableName
     */
    public void dropTable(String tableName) 
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            db.execSQL("DROP TABLE IF EXISTS " + tableName); 
        }
    }
 
    /**
     * 关闭数据库
     * @param DatabaseName
     */
    public void closeDatabase() 
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null)
        {
            db.close();
        }
    }

    /**
     * 
     */
    public void dispose()
    {
        closeDatabase();
        dbHelper.dispose();
        dbHelper = null;
    }
    
    //
    private DBHelper dbHelper;
}
