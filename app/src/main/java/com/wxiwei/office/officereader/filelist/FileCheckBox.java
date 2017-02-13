/*
 * 文件名称:          FileCheckBox.java
 *  
 * 编译器:            android2.2
 * 时间:              上午11:13:38
 */

package com.wxiwei.office.officereader.filelist;

import com.wxiwei.office.officereader.FileListActivity;
import com.wxiwei.office.system.IControl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * 文件选择对话框
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2011-11-29
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class FileCheckBox extends CheckBox
{
    /**
     * 
     * @param context
     */
    public FileCheckBox(Context context, IControl control, FileItem fileItem)
    {
        super(context);
        this.fileItem = fileItem;
        this.control = control;
        this.setChecked(fileItem.isCheck());
        this.setFocusable(false);
    }

    /**
     * 
     * @param context
     * @param attrs
     */
    public FileCheckBox(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 
     */
    public boolean performClick()
    {
        boolean b = super.performClick();
        fileItem.setCheck(isChecked());
        FileListActivity activity = (FileListActivity)control.getActivity();
        if (isChecked())
        {
            activity.addSelectFileItem(fileItem);
        }
        else
        {
            activity.removeSelectFileItem(fileItem);
        }
        return b;
    }

    /**
     * 
     */
    public void setFileItem(FileItem fileItem)
    {
        this.fileItem = fileItem;
        setChecked(fileItem.isCheck());
    }

    /**
     * 
     */
    public void dispose()
    {
        fileItem = null;
        control = null;
    }
    //
    private FileItem fileItem;
    //
    private IControl control;

}
