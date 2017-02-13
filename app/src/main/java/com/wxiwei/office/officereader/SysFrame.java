/*
 * 文件名称:          SysFrame.java
 *  
 * 编译器:            android2.2
 * 时间:              下午8:39:33
 */
package com.wxiwei.office.officereader;

import com.wxiwei.office.constant.EventConstant;
import com.wxiwei.office.constant.MainConstant;
import com.wxiwei.office.officereader.beans.AImageButton;
import com.wxiwei.office.officereader.beans.AImageTextButton;
import com.wxiwei.office.system.IControl;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 主控窗口
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
public class SysFrame extends LinearLayout
{
    private static final int FONT_SIZE = 32;
    private static final int GAP = MainConstant.GAP;
    /**
     * 
     * @param activity
     */
    public SysFrame(Context context, IControl control)
    {
        super(context);
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setBackgroundResource(R.drawable.sys_background);
        this.control = control;
        initListener();
    }
    
    /**
     * 
     */
    protected void init()
    {
        Resources res = getResources();
        opts.inJustDecodeBounds = true;
        // 竖屏
        if (control.getSysKit().isVertical(getContext()))
        {
            initVertical(res);
        }
        // 横屏 
        else
        {   
            initHorizontal(res);
        }
    }
    
    /**
     * 竖屏
     */
    private void initVertical(Resources res)
    {
        Context context = control.getActivity();        
        int mHeight = getResources().getDisplayMetrics().heightPixels;
        mHeight -= control.getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        // 搜索
        BitmapFactory.decodeResource(res, R.drawable.sys_search, opts);
        AImageButton search = new AImageButton(context, control, "",  R.drawable.sys_search, -1, EventConstant.SYS_SEARCH_ID);
        search.setPushBgResID(R.drawable.sys_search_bg_push);        
        search.setOnClickListener(onClickListener);
        LayoutParams params = new LayoutParams(opts.outWidth, opts.outHeight);
        params.gravity = Gravity.RIGHT;
        addView(search, params);
        mHeight -=opts.outHeight;
        
        // logo
        ImageView logo = new ImageView(context);
        //logo.setImageResource(R.drawable.sys_logo_vertical);
        addView(logo);
        
            
        BitmapFactory.decodeResource(context.getResources(),  R.drawable.sys_button_normal_bg_vertical, opts);
        params = new LayoutParams(opts.outWidth, opts.outHeight);
        params.gravity = Gravity.CENTER;
        params.bottomMargin = GAP * 6;
        // 最近打开的文件
        addView(createButtonVertical(context, params, R.drawable.sys_recent_vertical, 
            R.string.sys_button_recently_files, EventConstant.SYS_RECENTLY_FILES_ID));
        
        // 标星文件
        addView(createButtonVertical(context, params, R.drawable.sys_mark_star_vertical, 
            R.string.sys_button_mark_files, EventConstant.SYS_MARK_FILES_ID));
        
        // sdcard文件
        params = new LayoutParams(opts.outWidth, opts.outHeight);
        params.gravity = Gravity.CENTER;
        addView(createButtonVertical(context, params , R.drawable.sys_sacard_vertical, 
            R.string.sys_button_local_storage, EventConstant.SYS_MEMORY_CARD_ID));
        
        // 设置 logo 的 LayoutParams
        mHeight -= (opts.outHeight * 3 + GAP * 12 + 150);
       //BitmapFactory.decodeResource(res, R.drawable.sys_logo_vertical, opts);
        mHeight -= opts.outHeight;
        params = new LayoutParams(LayoutParams.MATCH_PARENT, opts.outHeight);
        params.topMargin = mHeight / 2;
        params.gravity = Gravity.CENTER;
        params.bottomMargin = mHeight / 2;
        logo.setLayoutParams(params);
        
    }
    
    /**
     * 
     */
    private AImageTextButton createButtonVertical(Context context, LayoutParams params, 
        int iconID, int textID, int actionID)
    {
        
        AImageTextButton tb = new AImageTextButton(context, control, context.getString(textID), "", 
            iconID, -1, actionID, AImageTextButton.TEXT_RIGHT, FONT_SIZE);
        tb.setLeftIndent(GAP * 6);
        tb.setLayoutParams(params);
        tb.setNormalBgResID(R.drawable.sys_button_normal_bg_vertical);
        tb.setPushBgResID(R.drawable.sys_button_push_bg_vertical);
        tb.setFocusBgResID(R.drawable.sys_button_focus_bg_vertical);
        tb.setOnClickListener(onClickListener);
        return tb;
    }
    
    
    /**
     * 横屏
     */
    private void initHorizontal(Resources res)
    {
        Context context = control.getActivity();
        int mHeight = getResources().getDisplayMetrics().heightPixels;
        
        LinearLayout hor_1 = new LinearLayout(context);     
        hor_1.setOrientation(VERTICAL);
        //hor_1.setGravity(Gravity.CENTER); 
        // 搜索               
        BitmapFactory.decodeResource(res, R.drawable.sys_search, opts);
        AImageButton search = new AImageButton(context, control, "", R.drawable.sys_search, -1, EventConstant.SYS_SEARCH_ID);
        search.setPushBgResID(R.drawable.sys_search_bg_push);
        search.setOnClickListener(onClickListener);
        LayoutParams params = new LayoutParams(opts.outWidth, opts.outHeight);
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        hor_1.addView(search, params);
        // logo
        //BitmapFactory.decodeResource(res, R.drawable.sys_logo_horizontal, opts);
        ImageView logo = new ImageView(context);
        //logo.setImageResource(R.drawable.sys_logo_horizontal);
        params = new LayoutParams(opts.outWidth, opts.outHeight);
        params.gravity = Gravity.CENTER;
        hor_1.addView(logo, params);
        hor_1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (mHeight - 60) / 2));
        addView(hor_1);
        
        BitmapFactory.decodeResource(context.getResources(), R.drawable.sys_button_normal_bg_horizontal, opts);
        LinearLayout hor_2 = new LinearLayout(context);
        params = new LayoutParams(opts.outWidth, opts.outHeight);
        params.gravity = Gravity.CENTER;
        params.rightMargin = GAP * 6;
        
        // 最近打开的文件
        hor_2.addView(createButtonHorizontal(context, params, R.drawable.sys_recent_horizontal, 
            R.string.sys_button_recently_files, EventConstant.SYS_RECENTLY_FILES_ID));
        
        // 标星文件
        hor_2.addView(createButtonHorizontal(context, params, R.drawable.sys_mark_star_horizontal, 
            R.string.sys_button_mark_files, EventConstant.SYS_MARK_FILES_ID));
        
        // sdcard文件
        params = new LayoutParams(opts.outWidth, opts.outHeight);
        params.gravity = Gravity.CENTER;
        hor_2.addView(createButtonHorizontal(context, params, R.drawable.sys_sacard_horizontal, 
            R.string.sys_button_local_storage, EventConstant.SYS_MEMORY_CARD_ID));
        
        hor_2.setGravity(Gravity.CENTER);
        addView(hor_2);
    }
    

    /**
     * 
     */
    private AImageTextButton createButtonHorizontal(Context context, LayoutParams params, 
        int iconID, int textID, int actionID)
    {        
        AImageTextButton tb = new AImageTextButton(context, control, context.getString(textID), "", 
            iconID, -1, actionID, AImageTextButton.TEXT_BOTTOM, FONT_SIZE);
        tb.setTopIndent(GAP * 6);
        tb.setLayoutParams(params);
        tb.setNormalBgResID(R.drawable.sys_button_normal_bg_horizontal);
        tb.setPushBgResID(R.drawable.sys_button_push_bg_horizontal);
        tb.setFocusBgResID(R.drawable.sys_button_focus_bg_horizontal);
        tb.setOnClickListener(onClickListener);
        return tb;
    }
    /**
     * 
     */
    private void initListener()
    {
        onClickListener = new OnClickListener()
        {
            /**
             *
             */
            public void onClick(View v)
            {
                int actionID = ((AImageButton)v).getActionID();
                String fileListType = "";
                switch (actionID)
                {
                    case EventConstant.SYS_SEARCH_ID: // 搜索
                        control.actionEvent(actionID, null);
                        return;
                        
                    case EventConstant.SYS_RECENTLY_FILES_ID: // 最近打开的文档
                        fileListType = MainConstant.INTENT_FILED_RECENT_FILES;
                        break;
                        
                    case EventConstant.SYS_MARK_FILES_ID: // 标星文档
                        fileListType = MainConstant.INTENT_FILED_MARK_FILES;
                        break;
                        
                    case EventConstant.SYS_MEMORY_CARD_ID: // sdcard
                        fileListType = MainConstant.INTENT_FILED_SDCARD_FILES;
                        break;
                        
                    default:
                        break;
                }
                
                Intent intent = new Intent();
                intent.setClass(control.getActivity(), FileListActivity.class);
                intent.putExtra(MainConstant.INTENT_FILED_FILE_LIST_TYPE, fileListType);
                control.getActivity().startActivity(intent);
            }
        };
    }
    
    /**
     * 
     */
    public void dispose()
    {
        onClickListener = null;
        opts = null;
        control = null;
    }
    //
    private OnClickListener onClickListener;
    //
    private Options opts = new BitmapFactory.Options();
    //
    private IControl control;
    
}
