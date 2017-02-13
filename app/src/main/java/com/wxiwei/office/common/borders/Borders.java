/*
 * 文件名称:          	Borders.java
 *  
 * 编译器:            android2.2
 * 时间:             	上午9:40:44
 */
package com.wxiwei.office.common.borders;

/**
 * border
 * <p>
 * <p>
 * Read版本:        	Office engine V1.0
 * <p>
 * 作者:            	ljj8494
 * <p>
 * 日期:            	2013-3-18
 * <p>
 * 负责人:          	ljj8494
 * <p>
 * 负责小组:        	TMC
 * <p>
 * <p>
 */
public class Borders
{

    public Borders()
    {
        
    }
    
    /**
     * 
     * @return
     */
    public Border getTopBorder()
    {
        return this.top;
    }
    /**
     *
     */
    public void setTopBorder(Border b)
    {
        this.top = b;
    }
    
    
    /**
     * 
     * @return
     */
    public Border getLeftBorder()
    {
        return this.left;
    }
    /**
     *
     */
    public void setLeftBorder(Border b)
    {
        this.left = b;
    }
    
    
    /**
     * 
     * @return
     */
    public Border getRightBorder()
    {
        return this.right;
    }
    /**
     *
     */
    public void setRightBorder(Border b)
    {
        this.right = b;
    }
    
    /**
     * 
     * @return
     */
    public Border getBottomBorder()
    {
        return this.bottom;
    }
    /**
     *
     */
    public void setBottomBorder(Border b)
    {
        this.bottom = b;
    }
    
    /**
     * 
     * @return
     */
    public byte getOnType()
    {
        return onType;
    }
    /**
     * 
     */
    public void setOnType(byte onType)
    {
        this.onType = onType;
    }
    
    //
    private Border left;
    //
    private Border top;
    //
    private Border right;
    //
    private Border bottom;
    // on page or on text
    private byte onType;
}
