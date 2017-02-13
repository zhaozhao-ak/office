/*
 * 文件名称:          IUpdateStatusListener.java
 *  
 * 编译器:            android2.2
 * 时间:              下午3:06:28
 */
package com.wxiwei.office.macro;

import java.util.List;

/**
 * update status listener
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-9-10
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public interface UpdateStatusListener
{
	//header or footer contains vector graph,so need update all pages
	public static final byte ALLPages = -1;
		
    /**
     * update status for UI, ex. changes in the current slide
     */
    public void updateStatus();
    
    /** 
     * callback this method after zoom change
     */
    public void changeZoom();
    
    /**
     * 
     */
    public void changePage();
    
    /**
     * 
     */
    public void completeLayout();
    
    /**
     * 
     * @param views
     */
    public void updateViewImage(Integer[] views);
    
}
