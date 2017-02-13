/*
 * 文件名称:           NewFolderDialog.java
 *  
 * 编译器:             android2.2
 * 时间:               下午4:01:19
 */
package com.wxiwei.office.officereader.filelist;

import java.io.File;
import java.util.Vector;

import com.wxiwei.office.constant.DialogConstant;
import com.wxiwei.office.officereader.R;
import com.wxiwei.office.system.IControl;
import com.wxiwei.office.system.IDialogAction;
import com.wxiwei.office.system.dialog.MessageDialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * create a new folder dialog
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
public class NewFolderDialog extends FileNameDialog
{
    /**
     * 
     * @param context
     * @param action
     * @param model
     * @param dialogID
     */
    public NewFolderDialog(IControl control, Context context, IDialogAction action, Vector<Object> model, int dialogID, int titleResID)
    {
        super(control, context, action, model, dialogID, titleResID);
        initDialog();
    }
    
    /**
     * 
     */
    public void initDialog()
    {
        textView.setText(R.string.dialog_folder_name);
        editText.addTextChangedListener(watcher);
    }
    
    /**
     * 
     */
    public void onClick(View v)
    {
        if (v == ok)
        {
            if (model == null)
            {
                dismiss();
                return;
            }
            File newFolder;
            String filePath = model.get(0).toString();
            String name = editText.getText().toString().trim();
            if (filePath.endsWith(File.separator))
            {
                newFolder = new File(filePath + name);
            }
            else
            {
                newFolder = new File(filePath + File.separator + name);
            }
            Vector<Object> vector = new Vector<Object>();
            vector.add(newFolder);
            if (!newFolder.exists())
            {
                action.doAction(dialogID, vector);
                dismiss();
            }
            else
            {
                CharSequence text = getContext().getResources().getText(R.string.dialog_name_error);
                String message = text.toString().replace("%s", name);
                new MessageDialog(control, getContext(), action, null, 
                    DialogConstant.MESSAGE_DIALOG_ID, 
                    R.string.dialog_create_folder_error, message).show();
            }
        }
        else
        {
            dismiss();
        }
    }
    
    /**
     * 监听text
     */
    private TextWatcher watcher = new TextWatcher()
    {
        /**
         * 
         *
         */
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            
        }

        /**
         * 
         *
         */
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            ok.setEnabled(isFileNameOK(s.toString()));
        }

        /**
         * 
         *
         */
        public void afterTextChanged(Editable s)
        {
            
        }       
    };
}
