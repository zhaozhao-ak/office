/*
 * 文件名称:          MarcoCustomDialog.java
 *  
 * 编译器:            android2.2
 * 时间:              下午12:49:58
 */
package com.wxiwei.office.macro;

import com.wxiwei.office.common.ICustomDialog;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-12-21
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class MacroCustomDialog implements ICustomDialog
{
    /**
     * 
     * 
     */
    protected MacroCustomDialog(DialogListener listener)
    {
        this.dailogListener = listener;
    }

    /**
     * 
     *
     */
    public void showDialog(byte type)
    {
        if (dailogListener != null)
        {
            dailogListener.showDialog(type);
        }
    }

    /**
     * 
     *
     */
    public void dismissDialog(byte type)
    {
        if (dailogListener != null)
        {
            dailogListener.dismissDialog(type);
        }

    }
    
    public void dispose()
    {
        dailogListener = null;
    }
    
    //
    private DialogListener dailogListener;

}
