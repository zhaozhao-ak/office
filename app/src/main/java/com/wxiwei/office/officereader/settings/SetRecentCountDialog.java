/*
 * 文件名称:           SetRecentFileCount.java
 *  
 * 编译器:             android2.2
 * 时间:               上午9:05:27
 */
package com.wxiwei.office.officereader.settings;

import java.util.Vector;

import com.wxiwei.office.officereader.R;
import com.wxiwei.office.system.IControl;
import com.wxiwei.office.system.IDialogAction;
import com.wxiwei.office.system.beans.ADialog;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * set maximum count of recently files
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2012-1-12
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class SetRecentCountDialog extends ADialog
{
    // 最近打开的文档数最小值
    public static final int RECENT_COUNT_MIN = 1;
    // 最近打开的文档数最大值
    public static final int RECENT_COUNT_MAX = 20;
    
    /**
     * 
     * @param context
     * @param action
     * @param model
     * @param dialogID
     * @param titleResID
     */
    public SetRecentCountDialog(IControl control, Context context, IDialogAction action, Vector<Object> model,
        int dialogID, int titleResID)
    {
        super(control, context, action, model, dialogID, titleResID);
        init(context);
    }
 
    /**
     * 
     * @param context
     */
    public void init(Context context)
    {
        int mWidth = getContext().getResources().getDisplayMetrics().widthPixels - MARGIN * 2;
        //text viewt
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.TOP);
        if (model != null)
        {
            textView.setText((String)model.get(0));
        }
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
        if (model != null)
        {
            editText.setText((String)model.get(1));
        }
        editText.setSingleLine();
        editText.setKeyListener(new DigitsKeyListener(false, false));
        editText.addTextChangedListener(watcher);
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
        ok.setEnabled(true);
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
        if (v == ok)
        {
            Vector<Object> vector = new Vector<Object>();
            vector.add(Integer.parseInt(editText.getText().toString()));
            action.doAction(dialogID, vector);
        }
        dismiss();
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
        // editText
        LayoutParams params = new LayoutParams(mWidth, LayoutParams.WRAP_CONTENT);
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
            if (s.length() > 0 && s.length() < 3)
            {
                int maxCount = Integer.parseInt(s.toString());
                if (maxCount >= RECENT_COUNT_MIN && maxCount <= RECENT_COUNT_MAX)
                {
                    ok.setEnabled(true);
                    return;
                }
            }
            ok.setEnabled(false);
        }

        /**
         * 
         *
         */
        public void afterTextChanged(Editable s)
        {
            
        }       
    };
    
    /**
     * 
     *
     */
    public void dispose()
    {
        super.dispose();
        editText = null;
    }
    
    //
    protected EditText editText;
}
