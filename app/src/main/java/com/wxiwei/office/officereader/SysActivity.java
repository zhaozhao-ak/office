/*
 * 文件名称:          SysActivity.java
 *  
 * 编译器:            android2.2
 * 时间:              下午4:48:16
 */

package com.wxiwei.office.officereader;

import com.wxiwei.office.system.IControl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

/**
 * Office Reader 主控activity
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2011-11-28
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class SysActivity extends Activity
{
    /**
     * 构造器
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        control = new SysControl(this);
        sysFrame = new SysFrame(this, control);
        sysFrame.post(new Runnable()
        {
            /**
             */
            public void run()
            {
                init();
            }
        });
                
        setContentView(sysFrame);
    }

    /**
     * 
     */
    public void init()
    {
        sysFrame.init();
    }
    
    /**
     * 
     *
     */
    public View getSysFrame()
    {
        return sysFrame;
    }
    
    /**
     * 
     */
    public void onBackPressed()
    {
        super.onBackPressed();
        System.exit(0);
    }

    /**
     * 
     *
     */
    public void dispose()
    {
        sysFrame.dispose();
        control.dispose();
    }
    
    //
    private SysFrame sysFrame;
    //
    private IControl control;
}
