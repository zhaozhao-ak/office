/*
 * 文件名称:          AnimationManager.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:58:01
 */
package com.wxiwei.office.pg.animate;

import com.wxiwei.office.common.IOfficeToPicture;
import com.wxiwei.office.constant.EventConstant;
import com.wxiwei.office.system.IControl;
import com.wxiwei.office.system.ITimerListener;
import com.wxiwei.office.system.beans.ATimer;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-11-22
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class AnimationManager implements ITimerListener
{
    public AnimationManager(IControl control)
    {
        this.control = control;
    }    
    
    public void setAnimation(IAnimation animation)
    {
        //stop last animation
        if(this.animation != null && timer != null && timer.isRunning())
        {            
            timer.stop();
            this.animation.stop();
        }
        
        this.animation = animation;
    }
    
    /**
     * 
     * @param delay
     */
    public void beginAnimation(int delay)
    {
        if(timer ==  null)
        {
            timer = new ATimer(delay, this);
        }
        
        if(animation != null)
        {            
            actionIndex = 0;
            animation.start();
            
            timer.start();
            if(control.getOfficeToPicture() != null)
            {
                control.getOfficeToPicture().setModeType(IOfficeToPicture.VIEW_CHANGING);
            }
        }        
    }
    
    public void restartAnimationTimer()
    {
    	if(timer != null)
    	{
    		timer.restart();
    	}
    }
    
    public void killAnimationTimer()
    {
    	if (timer != null)
        {
            timer.stop();
        }
    }
    
    /**
     * 
     */
    public void stopAnimation()
    {
        if(animation != null)
        {           
            if (timer != null)
            {
                timer.stop();
            }
            if (animation != null)
            {
                animation.stop();
            }
            
            if(control.getOfficeToPicture() != null)
            {
                control.getOfficeToPicture().setModeType(IOfficeToPicture.VIEW_CHANGE_END);
            }            
            control.actionEvent(EventConstant.PG_REPAINT_ID, null);
        } 
    }
    
    /**
     * 定时器
     */
    public void actionPerformed()
    {
        if(animation != null && animation.getAnimationStatus() != Animation.AnimStatus_End)
        {
            animation.animation(++actionIndex); 
            
            control.actionEvent(EventConstant.PG_REPAINT_ID, null);

            if (timer != null)
            {
                timer.restart();
            }
        }
        else
        {
            if (timer != null)
            {
                timer.stop();
            }
            if(control.getOfficeToPicture() != null)
            {
                control.getOfficeToPicture().setModeType(IOfficeToPicture.VIEW_CHANGE_END);
            }
            control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, null);
        }
             
    }
    
    /**
     * animation stoped or not
     * @return
     */
    public boolean hasStoped()
    {
        if(animation != null)
        {            
            return animation.getAnimationStatus() == Animation.AnimStatus_End;
        }
        
        return true;
    }
    
    public void dispose()
    {
        control =  null;
        animation = null;
        if(timer != null)
        {
            timer.dispose();
            timer = null;
        }
    }
    
    
    private IAnimation animation;
    
    private ATimer timer;
    private int actionIndex;
    private IControl control;
}
