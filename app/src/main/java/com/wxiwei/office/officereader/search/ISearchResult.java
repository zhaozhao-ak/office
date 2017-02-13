/*
 * 文件名称:          ISearchResult.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:59:31
 */
package com.wxiwei.office.officereader.search;

import java.io.File;

/**
 * 搜索接口
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-3-26
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public interface ISearchResult
{
    /**
     * return to Search Results. call with has match file
     * 
     * @param 
     */
    public void onResult(File file);
    
    /**
     * call with search finish
     */
    public void searchFinish();
    
}
