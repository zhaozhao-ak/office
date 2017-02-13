/*
 * 文件名称:          AUncaughtExceptionHandler.java
 *  
 * 编译器:            android2.2
 * 时间:              下午3:27:24
 */
package com.wxiwei.office.system;

import java.lang.Thread.UncaughtExceptionHandler;


/**
 * 异常处理
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-4-10
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class AUncaughtExceptionHandler implements UncaughtExceptionHandler
{
    
    /**
     * 
     */
    public AUncaughtExceptionHandler(IControl control)
    {
        this.control = control;
    }

    /**
     * 
     *
     */
    public void uncaughtException(Thread thread, final Throwable ex)
    {
        control.getSysKit().getErrorKit().writerLog(ex);
    }
    
    /**
     * 
     */
    public void dispose()
    {
        control = null;
    }

    /**
     * 
     */
    private IControl control;
}
