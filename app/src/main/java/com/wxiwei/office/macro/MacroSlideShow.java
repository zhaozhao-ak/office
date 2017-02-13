/*
 * 文件名称:          MacroSlideShow.java
 *  
 * 编译器:            android2.2
 * 时间:              上午9:51:54
 */
package com.wxiwei.office.macro;

import com.wxiwei.office.common.ISlideShow;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-12-28
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class MacroSlideShow implements ISlideShow
{
    /**
     * 
     * 
     */
    protected MacroSlideShow(SlideShowListener listener)
    {
        this.listener = listener;
    }
    
//    /**
//     * 
//     * @param actionType
//     */
//    public void slideshow(byte actionType)
//    {
//        if(listener != null)
//        {
//            listener.slideshow(actionType);
//        }
//    }
    
    /**
     * exit slideshow
     */
    public void exit()
    {
        if(listener !=  null)
        {
            listener.exit();
        }
    }
    
    public void dispose()
    {
        listener = null;
    }
    
    //
    private SlideShowListener listener;
}
