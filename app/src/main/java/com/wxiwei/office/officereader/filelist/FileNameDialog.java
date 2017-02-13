/*
 * 文件名称:           FileNameDialog.java
 *  
 * 编译器:             android2.2
 * 时间:               上午9:12:31
 */
package com.wxiwei.office.officereader.filelist;

import java.util.Vector;

import com.wxiwei.office.officereader.R;
import com.wxiwei.office.system.IControl;
import com.wxiwei.office.system.IDialogAction;
import com.wxiwei.office.system.beans.ADialog;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * get file name
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2011-12-13
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class FileNameDialog extends ADialog
{
    /**
     * 
     * @param context
     * @param action
     * @param model
     * @param dialogID
     * @param titleResID
     */
    public FileNameDialog(IControl control, Context context, IDialogAction action, Vector<Object> model, int dialogID, int titleResID)
    {
        super(control, context, action, model, dialogID, titleResID);
        init(context);
    }
    
    /**
     * 
     */
    public void init(Context context)
    {
        int mWidth = getContext().getResources().getDisplayMetrics().widthPixels - MARGIN * 2;
        //text viewt
        textView = new TextView(context);
        textView.setGravity(Gravity.TOP);
        LayoutParams  params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        params.topMargin = GAP;
        params.bottomMargin = GAP;
        params.gravity = Gravity.CENTER;
        dialogFrame.addView(textView, params);
 
        //edit file name
        editText = new EditText(context);
        editText.setGravity(Gravity.TOP);
        editText.setSingleLine();
        params = new LayoutParams(mWidth, LayoutParams.WRAP_CONTENT);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        dialogFrame.addView(editText, params);
        
        //ok, cancel
        LinearLayout linearLayoutBtn = new LinearLayout(context);
        linearLayoutBtn.setGravity(Gravity.CENTER);
        linearLayoutBtn.setOrientation(LinearLayout.HORIZONTAL);
        params = new LayoutParams(mWidth / 2, LayoutParams.WRAP_CONTENT);
        
        ok = new Button(context);        
        ok.setText(R.string.sys_button_ok);
        ok.setOnClickListener(this);
        ok.setEnabled(false);
        linearLayoutBtn.addView(ok, params);
        
        cancel = new Button(context);        
        cancel.setText(R.string.sys_button_cancel);
        cancel.setOnClickListener(this);      
        linearLayoutBtn.addView(cancel, params);
        
        dialogFrame.addView(linearLayoutBtn);
    }    
    
    /**
     * 
     *
     */
    public void onClick(View v)
    {
        
    }
  
    /**
     * 
     *
     */
    public void doLayout()
    {
        int mWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        if (control.getSysKit().isVertical(getContext()))
        {
            mWidth -= MARGIN * 2;
        }
        else
        {
            mWidth -= MARGIN * 8;
        }
        //text view
        LayoutParams  params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        params.topMargin = GAP;
        params.bottomMargin = GAP;
        textView.setLayoutParams(params);
        
        // editText
        params = new LayoutParams(mWidth, LayoutParams.WRAP_CONTENT);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        editText.setLayoutParams(params);
        
        // ok、cancel
        params = new LayoutParams(mWidth / 2, LayoutParams.WRAP_CONTENT);
        ok.setLayoutParams(params);
        cancel.setLayoutParams(params);
    }
    
    /**
     * 
     *
     */
    public void onConfigurationChanged(Configuration newConfig)
    {        
        doLayout();
    }
 
    /**
     * 判断文件名是否合法
     * 
     * @param fileName
     * @return
     */
    public boolean isFileNameOK(String fileName)
    {
        if (fileName == null || fileName.length() < 1 || fileName.length() > 255)
        {
            return false;
        }
        
        String invalidateChars = "\\/:*?\"<>|";
        int len = invalidateChars.length();
        int index = 0;
        while(index < len)
        {
            if(fileName.indexOf(invalidateChars.charAt(index)) > -1)
            {
                return false;
            }
            index++;
        }
        return true; 
    }
    
    /**
     * 
     */
    public void dispose()
    {
        super.dispose();
        editText = null;
        textView = null;
    }

    //
    protected TextView textView;
    //
    protected EditText editText;
}
