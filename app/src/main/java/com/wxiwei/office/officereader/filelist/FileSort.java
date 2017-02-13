/*
 * 文件名称:           FileSort.java
 *  
 * 编译器:             android2.2
 * 时间:               下午4:54:20
 */
package com.wxiwei.office.officereader.filelist;

import java.io.File;
import java.util.Comparator;

import com.wxiwei.office.constant.MainConstant;


/**
 *  文件排序
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2011-12-19
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class FileSort implements Comparator<FileItem>
{ 
    // 不改变排列顺序
    public static final int FILESORT_TYPE_NULL = -1;
    // 按目录，文件排序
    public static final int FILESORT_TYPE_DIR = 0;
    // 按修改时间排序
    public static final int FILESORT_TYPE_MODIFIED_DATE = 1;
    // 按修文件大小排序
    public static final int FILESORT_TYPE_SIZE = 2;
    // 按文件名排序
    public static final int FILESORT_TYPE_NAME = 3;
    // 按升序排序
    public static final int FILESORT_ASCENDING = 0;
    // 按降序排序
    public static final int FILESORT_TYPE_DESCENDING = 1;
    
    // doc文档格式
    public static final int FILE_TYPE_DOC = 0;
    // docx文档格式
    public static final int FILE_TYPE_DOCX = 1;
    // xls文档格式
    public static final int FILE_TYPE_XLS = 2;
    // xlsx文档格式
    public static final int FILE_TYPE_XLSX = 3;
    // ppt文档格式
    public static final int FILE_TYPE_PPT = 4;
    // pptx文档格式
    public static final int FILE_TYPE_PPTX = 5;
    // txt文档格式
    public static final int FILE_TYPE_TXT = 6;
    //
    public static final int FILE_TYPE_PDF = 7;
    
    //
    private static FileSort mt = new FileSort();
    
    /**
     * 
     */
    private FileSort()
    {
        
    }
    
    //instance
    public static FileSort instance()
    {
        return mt;
    }

    /**
     * 
     *
     */
    public int compare(FileItem object1, FileItem object2)
    {
        int result = 0;
        switch(sortType)
        {
            case FILESORT_TYPE_DIR:
                if (ascending == FILESORT_ASCENDING)
                {
                    result = compareByDirUp(object1, object2);
                }
                else
                {
                    result = compareByDirDown(object1, object2);
                }
                break;
                
            case FILESORT_TYPE_MODIFIED_DATE:
                if (ascending == FILESORT_ASCENDING)
                {
                    result = compareByModifiedDateUp(object1, object2);
                }
                else
                {
                    result = compareByModifiedDateDown(object1, object2);
                }
                break;
                
            case FILESORT_TYPE_SIZE:
                if (ascending == FILESORT_ASCENDING)
                {
                    result = compareBySizeUp(object1, object2);
                }
                else
                {
                    result = compareBySizeDown(object1, object2);
                }
                break;
                
            case FILESORT_TYPE_NAME:
                if (ascending == FILESORT_ASCENDING)
                {
                    result = comparaByNameUp(object1, object2);
                }
                else
                {
                    result = comparaByNameDown(object1, object2);
                }
                break;
                
            default:
                break;
        }
        return result;
    }

    /**
     * 按文件名降序排序
     * @param object1
     * @param object2
     * @return
     */
    private int comparaByNameDown(FileItem object1, FileItem object2)
    {
        File file1 = object1.getFile();
        File file2 = object2.getFile();
        if (file1.isDirectory() && file2.isFile()) 
        {
            return 1;
        }
        if (file1.isFile() && file2.isDirectory()) 
        {
            return -1;
        }
        String fileName1 = object1.getFileName();
        String fileName2 = object2.getFileName();
        if (fileName1.compareToIgnoreCase(fileName2) < 0)
        {
            return 1;
        }
        else if (fileName1.compareToIgnoreCase(fileName2) > 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * 按文件名升序排序
     * @param object1
     * @param object2
     * @return
     */
    private int comparaByNameUp(FileItem object1, FileItem object2)
    {
        File file1 = object1.getFile();
        File file2 = object2.getFile();
        if (file1.isDirectory() && file2.isFile()) 
        {
            return -1;
        }
        if (file1.isFile() && file2.isDirectory()) 
        {
            return 1;
        }
        String fileName1 = object1.getFileName();
        String fileName2 = object2.getFileName();
        if (fileName1.compareToIgnoreCase(fileName2) > 0)
        {
            return 1;
        }
        else if (fileName1.compareToIgnoreCase(fileName2) < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * 按文件修改时间，降序排序
     * @param object1
     * @param object2
     * @return
     */
    private int compareByModifiedDateDown(FileItem object1, FileItem object2)
    {
        File file1 = object1.getFile();
        File file2 = object2.getFile();
        if (file1.isDirectory() && file2.isFile()) 
        {
            return 1;
        }
        if (file1.isFile() && file2.isDirectory()) 
        {
            return -1;
        }
        long d1 = object1.getFile().lastModified();
        long d2 = object2.getFile().lastModified();
        if (d1 == d2)
        {
            return comparaByNameDown(object1, object2);
        }
        else
        {
            return d1 < d2 ? 1: -1;
        }
    }
    
    /**
     * 按文件修改时间，升序排序
     * @param object1
     * @param object2
     * @return
     */
    private int compareByModifiedDateUp(FileItem object1, FileItem object2)
    {
        File file1 = object1.getFile();
        File file2 = object2.getFile();
        if (file1.isDirectory() && file2.isFile()) 
        {
            return -1;
        }
        if (file1.isFile() && file2.isDirectory()) 
        {
            return 1;
        }
        long d1 = object1.getFile().lastModified();
        long d2 = object2.getFile().lastModified();
        if (d1 == d2)
        {
            return comparaByNameUp(object1, object2);
        }
        else
        {
            return d1 > d2 ? 1: -1;
        }
    }
 
    /**
     * 按文件大小，降序排序
     * @param object1
     * @param object2
     * @return
     */
    private int compareBySizeDown(FileItem object1, FileItem object2) 
    { 
        File file1 = object1.getFile();
        File file2 = object2.getFile();
        if (file1.isDirectory() && file2.isFile()) 
        {
            return 1;
        }
        if (file1.isDirectory() && file2.isDirectory()) 
        {
            return comparaByNameDown(object1, object2);
        }
        if (file1.isFile() && file2.isDirectory()) 
        {
            return -1;
        }
        long s1 = file1.length();
        long s2 = file2.length();        
        if (s1 == s2)
        {
            return comparaByNameDown(object1, object2);
        } 
        else 
        {
            return s1 < s2 ? 1 : -1;
        }
    }
 
    /**
     * 按文件大小，升序排序
     * @param object1
     * @param object2
     * @return
     */
    private int compareBySizeUp(FileItem object1, FileItem object2) 
    { 
        File file1 = object1.getFile();
        File file2 = object2.getFile();
        if (file1.isDirectory() && file2.isFile()) 
        {
            return -1;
        }
        if (file1.isDirectory() && file2.isDirectory()) 
        {
            return comparaByNameUp(object1, object2);
        }
        if (file1.isFile() && file2.isDirectory()) 
        {
            return 1;
        }        
        long s1 = file1.length();
        long s2 = file2.length();        
        if (s1 == s2)
        {
            return comparaByNameUp(object1, object2);
        } 
        else 
        {
            return s1 > s2 ? 1 : -1;
        }
    }

    /**
     * 按目录，文件排序
     * @param object1
     * @param object2
     * @return
     */
    private int compareByDirDown(FileItem object1, FileItem object2) 
    {
        File file1 = object1.getFile();
        File file2 = object2.getFile();
        if (file1.isDirectory() && file2.isFile()) 
        {
            return 1;
        } 
        else if (file1.isDirectory() && file2.isDirectory()) 
        {
            return comparaByNameDown(object1, object2);
        } 
        else if (file1.isFile() && file2.isDirectory()) 
        {
            return -1;
        } 
        else
        {
            int type1 = getFileType(object1.getFileName());
            int type2 = getFileType(object2.getFileName());
            if (type1 == type2)
            {
                return comparaByNameDown(object1, object2);
            }
            else
            {
                return type1 < type2 ? 1 : -1;
            }
        }
    }
    
    /**
     * 按目录，文件排序
     * @param object1
     * @param object2
     * @return
     */
    private int compareByDirUp(FileItem object1, FileItem object2) 
    {
        File file1 = object1.getFile();
        File file2 = object2.getFile();
        if (file1.isDirectory() && file2.isFile()) 
        {
            return -1;
        } 
        else if (file1.isDirectory() && file2.isDirectory()) 
        {
            return comparaByNameUp(object1, object2);
        } 
        else if (file1.isFile() && file2.isDirectory()) 
        {
            return 1;
        } 
        else
        { 
            int type1 = getFileType(object1.getFileName());
            int type2 = getFileType(object2.getFileName());
            if (type1 == type2)
            {
                return comparaByNameUp(object1, object2);
            }
            else
            {
                return type1 > type2 ? 1 : -1;
            }
        }
    }
 
    /**
     * get file type
     * @param fileName
     * @return
     */
    public int getFileType(String fileName)
    {
        fileName = fileName.toLowerCase();
        // doc
        if (fileName.endsWith(MainConstant.FILE_TYPE_DOC)
            || fileName.endsWith(MainConstant.FILE_TYPE_DOT))
        {
            return FILE_TYPE_DOC;
        }
        // docx
        else if (fileName.endsWith(MainConstant.FILE_TYPE_DOCX)
            || fileName.endsWith(MainConstant.FILE_TYPE_DOTX)
            || fileName.endsWith(MainConstant.FILE_TYPE_DOTM))
        {
            return FILE_TYPE_DOCX;
        }
        // xls
        else if (fileName.endsWith(MainConstant.FILE_TYPE_XLS)
            || fileName.endsWith(MainConstant.FILE_TYPE_XLT))
        {
            return FILE_TYPE_XLS;
        }
        // xlsx
        else if (fileName.endsWith(MainConstant.FILE_TYPE_XLSX)
                 || fileName.endsWith(MainConstant.FILE_TYPE_XLTX)
                 || fileName.endsWith(MainConstant.FILE_TYPE_XLTM)
                 || fileName.endsWith(MainConstant.FILE_TYPE_XLSM))
        {
            return FILE_TYPE_XLSX;
        }
        // ppt
        else if (fileName.endsWith(MainConstant.FILE_TYPE_PPT)
            || fileName.endsWith(MainConstant.FILE_TYPE_POT))
        {
            return FILE_TYPE_PPT;
        }
        // pptx
        else if (fileName.endsWith(MainConstant.FILE_TYPE_PPTX)
            || fileName.endsWith(MainConstant.FILE_TYPE_PPTM)
            || fileName.endsWith(MainConstant.FILE_TYPE_POTX)
            || fileName.endsWith(MainConstant.FILE_TYPE_POTM))
        {
            return FILE_TYPE_PPTX;   
        }
        // PDF document
        else if (fileName.endsWith(MainConstant.FILE_TYPE_PDF))
        {
            return FILE_TYPE_PDF;
        }
        // txt
        else
        {
            return FILE_TYPE_TXT;
        }
    }
  
    /**
     * 设置排序类型
     * @param typeName
     * @param childName
     */
    public void setType(int type, int ascending)
    {
        sortType = type;
        this.ascending = ascending;
    }
  
    /**
     * 
     */
    public void dispose()
    {
    }
    
    //排序类型
    private int sortType;
    //升降序
    private int ascending;
}
