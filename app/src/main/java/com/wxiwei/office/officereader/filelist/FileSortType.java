/*
 * 文件名称:           FileSortType.java
 *  
 * 编译器:             android2.2
 * 时间:               下午3:24:27
 */
package com.wxiwei.office.officereader.filelist;

/**
 * file sort type
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2012-1-9
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class FileSortType
{
    /**
     * 
     */
    public FileSortType()
    {
      recentType = -1;  
    }
  
    /**
     * 
     * @return
     */
    public int getSdcardType()
    {
        return sdcardType;      
    }
    
    /**
     * 
     * @return
     */
    public int getRecentType()
    {
        return recentType;       
    }
    
    /**
     * 
     * @return
     */
    public int getStarType()
    {
        return starType;        
    }
    
   /**
    * 
    * @return
    */
    public int getSdcardAscending()
    {
        return sdcardAscending;        
    }
    
    /**
     * 
     * @return
     */
    public int getRecentAscending()
    {
        return recentAscending;        
    }
    
    /**
     * 
     * @return
     */
    public int getStarAscending()
    {
        return starAscending;        
    }
    
    /**
     * 
     * @param type
     * @param child
     */
    public void setSdcardType(int type, int ascending)
    {
        sdcardType = type;
        sdcardAscending = ascending;        
    }
    
    /**
     * 
     * @param type
     * @param child
     */
    public void setRecentType(int type, int ascending)
    {
        recentType = type;
        recentAscending = ascending;        
    }
    
    /**
     * 
     * @param type
     * @param child
     */
    public void setStarType(int type, int ascending)
    {
        starType = type;
        starAscending = ascending;        
    }
   
    /**
     * 
     */
    public void dispos()
    {
        
    }
    
    //
    private int sdcardType;
    //
    private int recentType;
    //
    private int starType;
    //
    private int sdcardAscending;
    //
    private int recentAscending;
    //
    private int starAscending;
}
