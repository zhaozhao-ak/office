/*
 * 文件名称:          ListManage.java
 *  
 * 编译器:            android2.2
 * 时间:              下午2:44:43
 */
package com.wxiwei.office.common.bulletnumber;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * bullet and number manage
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-6-18
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class ListManage
{
    
    public ListManage()
    {
        lists = new LinkedHashMap<Integer, ListData>();
    }
    
    /**
     * 
     */
    public int putListData(Integer key, ListData value)
    {
        lists.put(key, value);
        return lists.size() - 1;
    }
    
    /**
     * 
     */
    public ListData getListData(Integer key)
    {
        return lists.get(key);
    }
    
    /**
     * 
     */
    public void resetForNormalView()
    {
        Set<Integer> sets = lists.keySet();
        for (Integer key : sets)
        {
            ListData list = lists.get(key);
            if (list != null)
            {
                list.setNormalPreParaLevel((byte)0);
                list.resetForNormalView();
            }
        }
    }
    
    /**
     * 
     */
    public void dispose()
    {
        if (lists != null)
        {
            Set<Integer> sets = lists.keySet();
            for (Integer key : sets)
            {
                lists.get(key).dispose();
            }
            lists.clear();
        }
    }
    
    //
    private LinkedHashMap<Integer, ListData> lists;
}
