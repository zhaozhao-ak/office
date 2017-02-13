/*
 * 文件名称:          FileFrame.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:58:05
 */
package com.wxiwei.office.officereader;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * 文凭列表视图容器
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2011-12-9
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class FileFrame extends LinearLayout
{

    /**
     * 
     * @param context
     */
    public FileFrame(Context context)
    {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
    }
    
    /**
     * 
     */
    public void dispose()
    {
        
    }
}
