/*
 * 文件名称:           FileDialogAction.java
 *  
 * 编译器:             android2.2
 * 时间:               下午4:33:07
 */
package com.wxiwei.office.officereader.filelist;

import java.io.File;
import java.util.Vector;

import com.wxiwei.office.constant.DialogConstant;
import com.wxiwei.office.constant.EventConstant;
import com.wxiwei.office.system.FileKit;
import com.wxiwei.office.system.IControl;
import com.wxiwei.office.system.IDialogAction;


/**
 * file toolbar 用到dialogAction
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2011-12-6
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class FileDialogAction implements IDialogAction
{
    /**
     * 
     * @param control
     */
    public FileDialogAction(IControl control)
    {
        this.control = control;
    }
    
    /**
     * 
     * @param id    对话框的ID
     * @param obj   回调action需要数据
     */
    public void doAction(int id, Vector<Object> model)
    {
        if (model == null)
        {
            return;
        }
        switch (id)
        {
            case DialogConstant.CREATEFOLDER_DIALOG_ID: // create new folder
                if (((File)(model.get(0))).mkdir())
                {
                    control.actionEvent(EventConstant.FILE_REFRESH_ID, null);
                }
                else
                {
                    control.actionEvent(EventConstant.FILE_CREATE_FOLDER_FAILED_ID, null);
                }
                break;
                
            case DialogConstant.RENAMEFILE_DIALOG_ID: // file rename
                File file = (File)model.get(0);
                File newFile = (File)model.get(1);
                file.renameTo(newFile);
                control.actionEvent(EventConstant.FILE_REFRESH_ID, (Object)true);
                break;
                
            case DialogConstant.DELETEFILE_DIALOG_ID: // delete file 
                int nCount = model.size();
                for (int i = 0; i < nCount; i++)
                {
                    FileKit.instance().deleteFile((File)model.get(i));
                }
                control.actionEvent(EventConstant.FILE_REFRESH_ID, null);
                break;
                
            case DialogConstant.OVERWRITEFILE_DIALOG_ID: // ask whether overwrite file or not
                File fromFile = (File)model.get(1);
                File toFile = (File)model.get(2);
                if (!fromFile.equals(toFile))
                {
                    FileKit.instance().pasteFile(fromFile, toFile);
                    if ((Boolean)model.get(0))
                    {
                        FileKit.instance().deleteFile(fromFile);
                    }
                }
                break;
                
            case DialogConstant.FILESORT_DIALOG_ID:
                control.actionEvent(EventConstant.FILE_SORT_TYPE_ID, model);
                break;

            default:
                break;
        }
    }
    
    /**
     * 
     */
    public IControl getControl()
    {
        return this.control;
    }

    /**
     * 
     */
    public void dispose()
    {
        control = null;
    }
    
    //
    public IControl control;
}
