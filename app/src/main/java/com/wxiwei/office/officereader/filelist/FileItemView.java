/*
 * 文件名称:          FileItemView.java
 *  
 * 编译器:            android2.2
 * 时间:              下午12:39:26
 */
package com.wxiwei.office.officereader.filelist;

import com.wxiwei.office.constant.MainConstant;
import com.wxiwei.office.officereader.R;
import com.wxiwei.office.system.IControl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 文件列表视图
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2011-11-30
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class FileItemView extends LinearLayout
{
    private static final int GAP = MainConstant.GAP;
    //
    public static final int PUSH_COLOR = Color.rgb(195, 255, 100);
    /**
     * 
     * @param activity
     */
    public FileItemView(Context context, IControl control, FileItemAdapter fia, FileItem fileItem)
    {
        super(context);
        setOrientation(HORIZONTAL);
        //setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        //this.control = control;
        mWidth = getResources().getDisplayMetrics().widthPixels;
        init(control, fileItem, fia);
    }
    
    /**
     * 
     */
    private void init(IControl control, FileItem fileItem, FileItemAdapter fia)
    {
        Resources res = getResources();
        Context context = getContext();
        Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        
        // 图标
        BitmapFactory.decodeResource(res, R.drawable.file_doc, opts);
        int iconWidth = opts.outWidth + GAP * 2;
        int iconHeight= opts.outHeight +  GAP * 4;
        LayoutParams params = new LayoutParams(iconWidth, iconHeight);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        params.topMargin = GAP;
        params.bottomMargin = GAP;
        params.gravity = Gravity.CENTER;
        icon = new ImageView(context);
        icon.setImageDrawable(fia.getIconDrawable(fileItem.getIconType()));
        addView(icon, params);
        
        // 文件属性
        int propWidth = mWidth - iconWidth * 2 - GAP * 6;
        int propHeight = iconHeight;
        LinearLayout fileProp = new LinearLayout(context);
        fileProp.setOrientation(VERTICAL); 
        params.gravity = Gravity.CENTER_VERTICAL;        
        params = new LayoutParams(propWidth, propHeight);
        addView(fileProp, params);
        
        
        //文件属性上半部
        BitmapFactory.decodeResource(res, R.drawable.file_star, opts);
        RelativeLayout filePropTop = new RelativeLayout(context);
        params = new LayoutParams(propWidth, propHeight / 2);
        params.topMargin = GAP * 3;
        params.gravity = Gravity.CENTER_VERTICAL;
        fileProp.addView(filePropTop, params);
        
        // 文件名称
        fileName = new TextView(context);
        fileName.setSingleLine(true);
        fileName.setEllipsize(TextUtils.TruncateAt.END);
        fileName.setText(fileItem.getFileName());
        params = new LayoutParams(propWidth - opts.outWidth - GAP * 2, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        filePropTop.addView(fileName, params);
        
        // 文件标星
        fileStar = new ImageView(context);
        RelativeLayout.LayoutParams reParams = new RelativeLayout.LayoutParams(opts.outWidth, opts.outHeight);
        reParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        if (fileItem.getFileStar() > 0)
        {
            fileStar.setImageDrawable(fia.getIconDrawable(FileItemAdapter.ICON_TYPE_STAR));
        }
        filePropTop.addView(fileStar, reParams);
        
        
        //文件属性下半部
        RelativeLayout filePropBotom = new RelativeLayout(context);
        params = new LayoutParams(propWidth, propHeight / 2);
        params.gravity = Gravity.CENTER_VERTICAL;
        fileProp.addView(filePropBotom, params);
        
        // 文件建立日期
        fileCreateDate = new TextView(context);
        fileCreateDate.setSingleLine(true);
        fileCreateDate.setEllipsize(TextUtils.TruncateAt.END);
        fileCreateDate.setText(fia.formatDate(fileItem.getFile().lastModified()));
        params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        filePropBotom.addView(fileCreateDate, params);
        
        // 文件大小
        fileSize = new TextView(context);
        reParams = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT, 
            android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
        reParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        fileSize.setText(fileItem.getFile().isDirectory() ? "" : fia.formatSize(fileItem.getFile().length()));
        filePropBotom.addView(fileSize, reParams);
        
        // 文件选择框
        if (fileItem.isShowCheckView())
        {   
            checkBox = new FileCheckBox(context, control, fileItem);
            params = new LayoutParams(iconWidth + GAP * 6, iconHeight + GAP * 2);
            params.gravity = Gravity.CENTER_VERTICAL;
            params.leftMargin = GAP;
            params.rightMargin = GAP;
            addView(checkBox, params);
        }
    }
    
    /**
     * 
     */
    public void updateFileItem(FileItem fileItem, FileItemAdapter fia)
    {
        // 图标
        icon.setImageDrawable(fia.getIconDrawable(fileItem.getIconType()));        
        // 文件名称
        fileName.setText(fileItem.getFileName());        
        // 文件标星
        if (fileItem.getFileStar() > 0)
        {
            fileStar.setImageDrawable(fia.getIconDrawable(FileItemAdapter.ICON_TYPE_STAR));
        }
        else
        {
            fileStar.setImageDrawable(null);
        }
        // 文件建立日期
        fileCreateDate.setText(fia.formatDate(fileItem.getFile().lastModified()));
        // 文件大小
        fileSize.setText(fileItem.getFile().isDirectory() ? "" : fia.formatSize(fileItem.getFile().length()));
        // 文件选择框
        checkBox.setFileItem(fileItem);
    }
    
    /**
     * set file selected state
     */
    public void setSelected(boolean isSelected)
    {
        checkBox.setChecked(isSelected);
    }    
    
    /**
     * 
     */
    public void dispose()
    {
        icon = null;
        fileName = null;
        fileStar = null;
        fileCreateDate = null;
        fileSize = null;
        if (checkBox != null)
        {
            checkBox.dispose();
            checkBox = null;
        }
    }
    
    // 屏幕宽度
    private int mWidth;
    // 
    //private IControl control;
    //
    private ImageView icon;
    //
    private TextView fileName;
    //
    private ImageView fileStar;
    //
    private TextView fileCreateDate;
    //
    private TextView fileSize;
    //
    private FileCheckBox checkBox;    
}
