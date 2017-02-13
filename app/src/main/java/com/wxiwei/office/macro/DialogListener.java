/*
 * 文件名称:          DialogListener.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:35:58
 */
package com.wxiwei.office.macro;

import com.wxiwei.office.common.ICustomDialog;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-12-17
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public interface DialogListener
{
    //password dialog
    public static final byte DIALOGTYPE_PASSWORD = ICustomDialog.DIALOGTYPE_PASSWORD;
    //txt encode dialog
    public static final byte DIALOGTYPE_ENCODE = ICustomDialog.DIALOGTYPE_ENCODE;
    //loading dialog
    public static final byte DIALOGTYPE_LOADING = ICustomDialog.DIALOGTYPE_LOADING;
    //error dialog
    public static final byte DIALOGTYPE_ERROR = ICustomDialog.DIALOGTYPE_ERROR;
    //
    public static final byte DIALOGTYPE_FIND = ICustomDialog.DIALOGTYPE_FIND;
    
    
    /**
     * 
     * @param type dialog type
     */
    public void showDialog(byte type);
    
    /**
     * 
     * @param type
     */
    public void dismissDialog(byte type);
}
