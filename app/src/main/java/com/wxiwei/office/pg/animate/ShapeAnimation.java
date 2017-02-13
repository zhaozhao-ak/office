/*
 * 文件名称:          ShapeAnimation.java
 *  
 * 编译器:            android2.2
 * 时间:              下午3:30:56
 */
package com.wxiwei.office.pg.animate;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-11-9
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class ShapeAnimation
{
    //enter
    public static final byte SA_ENTR = 0;    
    //Emphasize
    public static final byte SA_EMPH = 1;    
    //exit
    public static final byte SA_EXIT = 2;
    //path
    public static final byte SA_PATH = 3;
    //verb
    public static final byte SA_VERB = 4;
    //mediacall
    public static final byte SA_MEDIACALL = 5;
    
    public static final int Para_BG = -1;
    public static final int Para_All = -2;
    public static final int Slide = -3;
    
    public ShapeAnimation(int shapeID, byte type)
    {
        this.shapeID = shapeID;
        this.animType = type;
    }
    
    public ShapeAnimation(int shapeID, byte type, int paraStart, int paraEnd)
    {
        this.shapeID = shapeID;
        this.animType = type;
        this.paraBegin = paraStart;
        this.paraEnd = paraEnd;
    }
    
    /**
     * get shape id
     * @return
     */
    public int getShapeID()
    {
        return shapeID;
    }
    
    /**
     * animation type
     * @return
     */
    public byte getAnimationType()
    {
        return animType;
    }
    
    /**
     * just for text element
     * @return
     */
    public int getParagraphBegin()
    {
        return paraBegin;
    }
    
    /**
     * just for text element
     * @return
     */
    public int getParagraphEnd()
    {
        return paraEnd;
    }
    
    private int shapeID;
    
    private byte animType = -1;
    
    //for Text Element, Paragraph Text Range
    private int paraBegin = -2;
    private int paraEnd = -2;
}
