/*
 * 文件名称:          	BookmarkManage.java
 *  
 * 编译器:            android2.2
 * 时间:             	下午5:05:13
 */
package com.wxiwei.office.common.bookmark;

import java.util.HashMap;
import java.util.Map;

/**
 * book mark manage
 * <p>
 * <p>
 * Read版本:        	Office engine V1.0
 * <p>
 * 作者:            	ljj8494
 * <p>
 * 日期:            	2013-5-9
 * <p>
 * 负责人:          	ljj8494
 * <p>
 * 负责小组:        	TMC
 * <p>
 * <p>
 */
public class BookmarkManage
{
    /**
     * 
     */
    public BookmarkManage()
    {
        bms = new HashMap<String, Bookmark>();
    }
    
    /**
     * 
     */
    public void addBookmark(Bookmark bm)
    {
        bms.put(bm.getName(), bm);
    }
    
    /**
     * 
     */
    public Bookmark getBookmark(String name)
    {
        return bms.get(name);
    }
    
    /**
     * 
     */
    public int getBookmarkCount()
    {
        return bms.size();
    }
    
    /**
     * 
     */
    public void dispose()
    {
        if (bms != null)
        {
            bms.clear();
            bms = null;
        }
    }
    
    //
    private Map<String, Bookmark> bms;
}
