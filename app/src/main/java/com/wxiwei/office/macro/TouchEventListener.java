/*
 * 文件名称:          ITouchListener.java
 *  
 * 编译器:            android2.2
 * 时间:              下午2:34:20
 */
package com.wxiwei.office.macro;

import com.wxiwei.office.system.IMainFrame;

import android.view.MotionEvent;
import android.view.View;

/**
 * touch event listener
 * 
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-9-10
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public interface TouchEventListener
{
    // onTouch
    public final static byte EVENT_TOUCH = IMainFrame.ON_TOUCH;
    // onDown
    public final static byte EVENT_DOWN = IMainFrame.ON_DOWN;
    // onShowPress
    public final static byte EVENT_SHOW_PRESS = IMainFrame.ON_SHOW_PRESS;
    // onSingleTapUp
    public final static byte EVENT_SINGLE_TAP_UP = IMainFrame.ON_SINGLE_TAP_UP;
    // onScroll
    public final static byte EVENT_SCROLL = IMainFrame.ON_SCROLL;
    // onLongPress
    public final static byte EVENT_LONG_PRESS = IMainFrame.ON_LONG_PRESS;
    // onFling
    public final static byte EVENT_FLING = IMainFrame.ON_FLING;
    // onSingleTapConfirmed
    public final static byte EVENT_SINGLE_TAP_CONFIRMED = IMainFrame.ON_SINGLE_TAP_CONFIRMED;
    // onDoubleTap
    public final static byte EVENT_DOUBLE_TAP = IMainFrame.ON_DOUBLE_TAP;
    // onDoubleTapEvent
    public final static byte EVENT_DOUBLE_TAP_EVENT = IMainFrame.ON_DOUBLE_TAP_EVENT;
    // onClick
    public final static byte EVENT_CLICK = IMainFrame.ON_CLICK;
    
    /**
     * event method, office engine dispatch 
     * 
     * @param       v             event source
     * @param       e1            MotionEvent instance
     * @param       e2            MotionEvent instance
     * @param       velocityX     x axis velocity
     * @param       velocityY     y axis velocity  
     * @param       eventNethodType  event method      
     *              @see TouchEventListener#EVENT_CLICK
     *              @see TouchEventListener#EVENT_DOUBLE_TAP
     *              @see TouchEventListener#EVENT_DOUBLE_TAP_EVENT
     *              @see TouchEventListener#EVENT_DOWN
     *              @see TouchEventListener#EVENT_FLING
     *              @see TouchEventListener#EVENT_LONG_PRESS
     *              @see TouchEventListener#EVENT_SCROLL
     *              @see TouchEventListener#EVENT_SHOW_PRESS
     *              @see TouchEventListener#EVENT_SINGLE_TAP_CONFIRMED
     *              @see TouchEventListener#EVENT_SINGLE_TAP_UP
     *              @see TouchEventListener#EVENT_TOUCH
     */
    public boolean onEventMethod(View v, MotionEvent e1, MotionEvent e2, float velocityX, float velocityY, byte eventMethodType);
}
