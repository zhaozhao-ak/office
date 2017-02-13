/*
 * 文件名称:           MainControl.java
 * 
 * 编译器:             android2.2
 * 时间:               下午1:34:44
 */
package com.wxiwei.office.officereader.filelist;

import java.io.File;

/**
 * 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            梁金晶
 * <p>
 * 日期:            2011-10-31
 * <p>
 * 负责人:          梁金晶
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class FileItem implements Comparable<FileItem>
{
    /**
     * 
     * @param text
     * @param bullet
     */
    public FileItem(File file, int iconType, int fileStar)
    {
        this.file = file;
        this.iconType = iconType;
        this.fileStar = fileStar;
    }

    /**
     * 得到文件名称
     * @return
     */
    public String getFileName()
    {
        return getFile().getName();
    }
    
    /**
     * 排序比较
     *
     */
    public int compareTo(FileItem other)
    {   
        return getFileName().compareToIgnoreCase(other.getFileName());
    }

    /**
     * 得到文件
     * @return Returns the file.
     */
    public File getFile()
    {
        return file;
    }
    
    /**
     * @return Returns the mIcon.
     */
    public int getIconType()
    {
        return iconType;
    }

    /**
     * @param isCheck The isCheck to set.
     */
    public void setCheck(boolean isCheck)
    {
        this.isCheck = isCheck;
    }

    /**
     * 
     * @return
     */
    public boolean isCheck()
    {
        return isCheck;
    }
    
    /**
     * @return Returns the fileStar.
     */
    public int getFileStar()
    {
        return fileStar;
    }

    /**
     * @param fileStar The fileStar to set.
     */
    public void setFileStar(int fileStar)
    {
        this.fileStar = fileStar;
    }

    /**
     * @return Returns the showCheckView.
     */
    public boolean isShowCheckView()
    {
        return showCheckView;
    }

    /**
     * @param showCheckView The showCheckView to set.
     */
    public void setShowCheckView(boolean showCheckView)
    {
        this.showCheckView = showCheckView;
    }

    /**
     * 
     */
    public void dispose()
    {
        file = null;
    }
    
    // 是否显示chekc box
    private boolean showCheckView = true;
    //
    private boolean isCheck;
    // 文件星级，最高5级，最低1级，=0没有设置星级
    private int fileStar;
    //
    private int iconType;
    // 对应文件对象
    private File file;
    //
    
}
