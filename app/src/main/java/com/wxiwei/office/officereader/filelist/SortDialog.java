/*
 * 文件名称:          SortDialog.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:44:46
 */
package com.wxiwei.office.officereader.filelist;

import java.util.Vector;

import com.wxiwei.office.officereader.R;
import com.wxiwei.office.officereader.beans.SingleChoiceList;
import com.wxiwei.office.system.IControl;
import com.wxiwei.office.system.IDialogAction;
import com.wxiwei.office.system.beans.ADialog;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Sort dialog
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2011-12-20
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class SortDialog extends ADialog
{

    /**
     * 
     * @param context
     * @param action
     * @param model
     * @param dialogID
     * @param titleResID
     * @param itemsResID
     */
    public SortDialog(IControl control, Context context, IDialogAction action, Vector<Object> model, int dialogID,
        int titleResID, int itemsResID)
    {
        super(control, context, action, model, dialogID, titleResID);
        init(context, itemsResID);
    }
    
    /**
     * 
     * @param context
     * @param itemsResID
     */
    public void init(Context context, int itemsResID)
    {
        int mWidth = context.getResources().getDisplayMetrics().widthPixels - MARGIN * 2;
        //
        int index = 0;
        if (model != null)
        {
            index = (Integer)model.get(0);
        }
        singleChoiceList = new SingleChoiceList(context, itemsResID);
        if (index >= 0)
        {
            singleChoiceList.setItemChecked(index, true);
        }
        
        // add ListView
        LayoutParams params = new LayoutParams(mWidth, 100);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        dialogFrame.addView(singleChoiceList, params);
        
        // add separated line
        View view = new View(context);
        view.setBackgroundColor(Color.GRAY);
        dialogFrame.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, 1));
        
        //add ascending, descending       
        sortGroup = new RadioGroup(context);
        sortGroup.setOrientation(LinearLayout.HORIZONTAL);
        sortGroup.setGravity(Gravity.CENTER);
        params = new LayoutParams(mWidth / 2, LayoutParams.WRAP_CONTENT);
        
        RadioButton btnAscending = new RadioButton(context);
        btnAscending.setText(R.string.dialog_ascending);
        sortGroup.addView(btnAscending, params);
        
        RadioButton btnDescending = new RadioButton(context);
        btnDescending.setText(R.string.dialog_descending);
        sortGroup.addView(btnDescending, params);
        
        params = new LayoutParams(mWidth, LayoutParams.WRAP_CONTENT);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        dialogFrame.addView(sortGroup, params);
 
        //设置 ascending, descending
        int pos = 0;
        if (model != null)
        {
            pos = (Integer)model.get(1);
        }
        ((RadioButton)sortGroup.getChildAt(pos)).setChecked(true);
        
        //ok, cancel
        LinearLayout linearLayoutBtn = new LinearLayout(context);
        linearLayoutBtn.setGravity(Gravity.CENTER);
        linearLayoutBtn.setOrientation(LinearLayout.HORIZONTAL);
        params = new LayoutParams(mWidth / 2, LayoutParams.WRAP_CONTENT);
        
        ok = new Button(context);        
        ok.setText(R.string.sys_button_ok);
        ok.setOnClickListener(this);
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
    public void doLayout()
    {
        int mWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        int mHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        // 需要减去标题栏的高度
        mHeight -= getWindow().getDecorView().getHeight() - dialogFrame.getHeight();
        if (control.getSysKit().isVertical(getContext()))
        {
            mWidth -= MARGIN * 2;
            mHeight -= MARGIN * 11;
        }
        else
        {
            mWidth -= MARGIN * 8;
            mHeight -= MARGIN * 2;
        }
        
        //set singleChoice
        LayoutParams params = new LayoutParams(mWidth - GAP * 2, 
            mHeight - sortGroup.getHeight() - ok.getHeight() - GAP * 4);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        singleChoiceList.setLayoutParams(params);
        
        //sortGroup        
        params = new LayoutParams(mWidth / 2, LayoutParams.WRAP_CONTENT);        
        ((RadioButton)sortGroup.getChildAt(0)).setLayoutParams(params);
        ((RadioButton)sortGroup.getChildAt(1)).setLayoutParams(params);
        
        params = new LayoutParams(mWidth, sortGroup.getHeight());
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        params.gravity = Gravity.CENTER;
        sortGroup.setLayoutParams(params);
        
        // ok, cancel
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
     * 
     *
     */
    public void onClick(View v)
    {
        if (v == ok)
        {
            int typePos = singleChoiceList.getCheckedItemPosition();
            int checkedID = sortGroup.getCheckedRadioButtonId();
            int childPos = sortGroup.indexOfChild((RadioButton)sortGroup.findViewById(checkedID));
            Vector<Object> vector = new Vector<Object>();
            vector.add(typePos);
            vector.add(childPos);
            action.doAction(dialogID, vector);
        }
        dismiss();
    }
    
    /**
     * 
     */
    public void dispose()
    {
        super.dispose();
        singleChoiceList = null;
        sortGroup = null;
    }

    //
    private SingleChoiceList singleChoiceList;
    //
    private RadioGroup sortGroup;
}
