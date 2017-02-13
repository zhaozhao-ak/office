/*
 * 文件名称:          AbortReaderException.java
 *  
 * 编译器:            android2.2
 * 时间:              下午5:06:13
 */
package com.wxiwei.office.system;

/**
 * 中断文件解析异常
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-4-24
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class AbortReaderError extends Error
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public AbortReaderError(String message)
    {
        super(message);
    }
}
