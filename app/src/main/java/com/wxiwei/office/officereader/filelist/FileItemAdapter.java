/*
 * 文件名称:           MainControl.java
 * 
 * 编译器:             android2.2
 * 时间:               下午1:34:44
 */

package com.wxiwei.office.officereader.filelist;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.wxiwei.office.constant.MainConstant;
import com.wxiwei.office.officereader.R;
import com.wxiwei.office.system.IControl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
public class FileItemAdapter extends BaseAdapter
{
    private static final Calendar calendar = Calendar.getInstance();
    // 数值格式化
    private static final DecimalFormat df = new java.text.DecimalFormat("#0.00");
    // 日期格式 分为24和12小时制
    private static final SimpleDateFormat sdf_24 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private static final SimpleDateFormat sdf_12 = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
    // 目录
    public static final int ICON_TYPE_FOLDER = 0;// 0
    // doc
    public static final int ICON_TYPE_DOC = ICON_TYPE_FOLDER + 1; // 1
    // docx
    public static final int ICON_TYPE_DOCX = ICON_TYPE_DOC + 1; // 2
    // xls
    public static final int ICON_TYPE_XSL = ICON_TYPE_DOCX + 1; // 3
    // xlsx
    public static final int ICON_TYPE_XLSX = ICON_TYPE_XSL + 1; // 4
    // ppt
    public static final int ICON_TYPE_PPT = ICON_TYPE_XLSX + 1; // 5
    // pptx
    public static final int ICON_TYPE_PPTX = ICON_TYPE_PPT + 1; // 6
    // txt
    public static final int ICON_TYPE_TXT = ICON_TYPE_PPTX + 1; // 7
    // 
    public static final int ICON_TYPE_STAR = ICON_TYPE_TXT + 1; // 8
    //
    public static final int ICON_TYPE_PDF = ICON_TYPE_STAR + 1; // 9 
    
    // GB
    private static final int GB = 1024 * 1024 * 1024;
    // MB
    private static final int MB = 1024 * 1024;
    // KB
    private static final int KB = 1024;

    /**
     * 
     * @param context
     */
    public FileItemAdapter(Context context, IControl control)
    {       
        this.control = control;
        this.is24Hour = "24".equals(Settings.System.getString(context.getContentResolver(), 
            Settings.System.TIME_12_24));
        
        Resources res = context.getResources();
        iconMap = new Hashtable<Integer, Drawable>();    
        // folder        
        iconMap.put(ICON_TYPE_FOLDER, res.getDrawable(R.drawable.file_folder));
        // doc
        iconMap.put(ICON_TYPE_DOC, res.getDrawable(R.drawable.file_doc));
        // docx
        iconMap.put(ICON_TYPE_DOCX, res.getDrawable(R.drawable.file_docx));
        // xls
        iconMap.put(ICON_TYPE_XSL, res.getDrawable(R.drawable.file_xls));
        // xlsx
        iconMap.put(ICON_TYPE_XLSX, res.getDrawable(R.drawable.file_xlsx));
        // ppt
        iconMap.put(ICON_TYPE_PPT, res.getDrawable(R.drawable.file_ppt));
        // pptx
        iconMap.put(ICON_TYPE_PPTX, res.getDrawable(R.drawable.file_pptx));
        // txt
        iconMap.put(ICON_TYPE_TXT, res.getDrawable(R.drawable.file_txt));
        // stat
        iconMap.put(ICON_TYPE_STAR, res.getDrawable(R.drawable.file_icon_star));
        // pdf
        iconMap.put(ICON_TYPE_PDF, res.getDrawable(R.drawable.file_pdf));
    }

    /**
     * 
     * @param it
     */
    public void addItem(FileItem it)
    {
        mItems.add(it);
    }

    /**
     * 
     * @param lit
     */
    public void setListItems(List<FileItem> fileItem)
    {
        mItems = fileItem;
    }

    /**
     * 
     */
    public int getCount()
    {
        return mItems.size();
    }

    /**
     * 
     *
     */
    public Object getItem(int position)
    {
        return mItems.get(position);
    }

    /**
     * 
     * @return
     */
    public boolean areAllItemsSelectable()
    {
        return false;
    }

    /**
     * 
     * @param position
     * @return
     */
    public boolean isSelectable(int position)
    {
        return mItems.get(position).isCheck();
    }

    /**
     * 
     *
     */
    public long getItemId(int position)
    {
        return position;
    }
    
    /**
     * 
     *(non-Javadoc)
     * @see android.widget.BaseAdapter#isEmpty()
     *
     */
    public boolean isEmpty()
    {
        return mItems.size() == 0; 
    }

    /**
     * 
     *
     */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        FileItem fileItem = mItems.get(position);
        if (fileItem == null)
        {
            return null;
        }
        if (convertView == null || convertView.getWidth() != parent.getContext().getResources().getDisplayMetrics().widthPixels)
        {
            convertView = new FileItemView(control.getActivity().getApplicationContext(), control, this, fileItem);
        }
        else
        {
            ((FileItemView)convertView).updateFileItem(fileItem, this);
        }
        return convertView;
    }

    /**
     * 
     * @param time
     * @return
     */
    public String formatDate(long time)
    {
        calendar.setTimeInMillis(time);
        return is24Hour ? sdf_24.format(calendar.getTime()) : sdf_12.format(calendar.getTime());
    }

    /**
     * 
     */
    public String formatSize(long size)
    {
        String str = "";
        if (size == 0)
        {
            return "0B";
        }
        
        if (size >= GB)
        {
            str += df.format((float)size / GB) + "GB";
        }
        // MB
        else if (size >= MB)
        {
            str += df.format((float)size / MB) + "MB";
        }
        // KB
        else if (size >= KB)
        {
            str += df.format((float)size / KB) + "KB";
        }
        // B
        else
        {
            str += size + " B";
        }
        return str;
    }
    
    /**
     * 
     * @param icontType
     * @return
     */
    public Drawable getIconDrawable(int icontType)
    {
        return iconMap.get(icontType);
    }
    
    /**
     * get file icon type
     * @param fileName
     * @return
     */
    public int getFileIconType(String fileName)
    {
        fileName = fileName.toLowerCase();
        // doc
        if (fileName.endsWith(MainConstant.FILE_TYPE_DOC)
            || fileName.endsWith(MainConstant.FILE_TYPE_DOT))
        {
            return FileItemAdapter.ICON_TYPE_DOC;
        }
        // docx
        else if (fileName.endsWith(MainConstant.FILE_TYPE_DOCX)
                 || fileName.endsWith(MainConstant.FILE_TYPE_DOTX)
                 || fileName.endsWith(MainConstant.FILE_TYPE_DOTM))
        {
            return FileItemAdapter.ICON_TYPE_DOCX;
        }
        // xls
        else if (fileName.endsWith(MainConstant.FILE_TYPE_XLS)
                 || fileName.endsWith(MainConstant.FILE_TYPE_XLT))
        {
            return FileItemAdapter.ICON_TYPE_XSL;
        }
        // xlsx
        else if (fileName.endsWith(MainConstant.FILE_TYPE_XLSX)
                 || fileName.endsWith(MainConstant.FILE_TYPE_XLTX)
                 || fileName.endsWith(MainConstant.FILE_TYPE_XLTM)
                 || fileName.endsWith(MainConstant.FILE_TYPE_XLSM))
        {
            return FileItemAdapter.ICON_TYPE_XLSX;
        }
        // ppt
        else if (fileName.endsWith(MainConstant.FILE_TYPE_PPT)
                 || fileName.endsWith(MainConstant.FILE_TYPE_POT))
        {
            return FileItemAdapter.ICON_TYPE_PPT;
        }
        // pptx
        else if (fileName.endsWith(MainConstant.FILE_TYPE_PPTX)
                 || fileName.endsWith(MainConstant.FILE_TYPE_PPTM)
                 || fileName.endsWith(MainConstant.FILE_TYPE_POTX)
                 || fileName.endsWith(MainConstant.FILE_TYPE_POTM))
        {
            return FileItemAdapter.ICON_TYPE_PPTX;   
        }
        // PDF document
        else if (fileName.endsWith(MainConstant.FILE_TYPE_PDF))
        {
            return FileItemAdapter.ICON_TYPE_PDF;
        }
        // txt
        else if (fileName.endsWith(MainConstant.FILE_TYPE_TXT))
        {
            return FileItemAdapter.ICON_TYPE_TXT;
        }
        
        return -1;
    }
    
    /**
     * 释放内存
     */
    public void dispose()
    {
        control = null;        
        if (mItems != null)
        {
            for (FileItem item : mItems)
            {
                item.dispose();
            }
            mItems.clear();
            mItems = null;
        }
        if (iconMap != null)
        {
            iconMap.clear();
            iconMap = null;
        }
    }

    // 是否24小时制
    private boolean is24Hour;
    //
    private IControl control;
    //
    private List<FileItem> mItems;
    // 文件列表选用的icon
    private Map<Integer, Drawable> iconMap;
}
