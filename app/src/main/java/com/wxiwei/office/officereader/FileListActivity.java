/*
 * 文件名称:          OfficeRead.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:14:13
 */
package com.wxiwei.office.officereader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.wxiwei.office.constant.DialogConstant;
import com.wxiwei.office.constant.EventConstant;
import com.wxiwei.office.constant.MainConstant;
import com.wxiwei.office.officereader.beans.AToolsbar;
import com.wxiwei.office.officereader.database.DBService;
import com.wxiwei.office.officereader.filelist.FileDialogAction;
import com.wxiwei.office.officereader.filelist.FileItem;
import com.wxiwei.office.officereader.filelist.FileItemAdapter;
import com.wxiwei.office.officereader.filelist.FileItemView;
import com.wxiwei.office.officereader.filelist.FileRenameDialog;
import com.wxiwei.office.officereader.filelist.FileSort;
import com.wxiwei.office.officereader.filelist.FileSortType;
import com.wxiwei.office.officereader.filelist.FileToolsbar;
import com.wxiwei.office.officereader.filelist.NewFolderDialog;
import com.wxiwei.office.officereader.filelist.SortDialog;
import com.wxiwei.office.officereader.search.ISearchResult;
import com.wxiwei.office.officereader.search.Search;
import com.wxiwei.office.system.FileKit;
import com.wxiwei.office.system.IControl;
import com.wxiwei.office.system.dialog.MessageDialog;
import com.wxiwei.office.system.dialog.QuestionDialog;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 文件列表页面
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
public class FileListActivity extends Activity implements ISearchResult
{
    // explore
    public static final byte LIST_TYPE_EXPLORE = 0;
    // recently
    public static final byte LIST_TYPE_RECENTLY = LIST_TYPE_EXPLORE + 1;
    // marked 
    public static final byte LIST_TYPE_MARKED = LIST_TYPE_RECENTLY + 1;
    // search 
    public static final byte LIST_TYPE_SEARCH = LIST_TYPE_MARKED + 1;
    /**
     * 
     *
     */
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        toast = Toast.makeText(getApplicationContext(), "", 0);
        selectFileItem = new ArrayList<FileItem>();
        directoryEntries = new ArrayList<FileItem>();
        control = new FileListControl(this);
        
        sdcardPath = control.getSysKit().getSDPath();
        if (sdcardPath == null)
        {
            sdcardPath = new File("/mnt/sdcard"); 
        }
        mHeight = getResources().getDisplayMetrics().heightPixels;
        fileFrame = new FileFrame(getApplicationContext());
        
        fileFrame.post(new Runnable()
        {
            public void run()
            {
                initListener();
                init();
            }
        });
        setTheme(control.getSysKit().isVertical(this) ? 
            R.style.title_background_vertical : R.style.title_background_horizontal);
        setContentView(fileFrame);
        dialogAction = new FileDialogAction(control);
        fileSortType = new FileSortType();
        dbService = new DBService(getApplicationContext());
    }
    
    /**
     * 
     */
    public void onBackPressed()
    {
        if (search != null)
        {
            search.stopSearch();
        }
        if (listType == LIST_TYPE_SEARCH)
        {
            if (currentDirectory == null)
            {
                super.onBackPressed();
            }
            else
            {
                createFileList(currentDirectory);
            }
        }
        else if (listType == LIST_TYPE_RECENTLY
            || listType == LIST_TYPE_MARKED)
        {
            super.onBackPressed();
        }
        else
        {
            if (currentDirectory != null && !currentDirectory.equals(sdcardPath))
            {                    
                browseTo(currentDirectory.getParentFile());
            }
            else
            {
                super.onBackPressed();
            }
        }
    }

    /**
     * 
     *
     */
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mHeight = getResources().getDisplayMetrics().heightPixels;
        mHeight -= getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mHeight - listView.getTop()));
    }
    
    /**
     * 
     *
     */
    protected void onDestroy()
    {
        super.onDestroy();
        dispose();
    }
    
    /**
     * List 单击事件
     * 
     */
    private void initListener()
    {
        onItemClickListener = new AdapterView.OnItemClickListener()
        {
            /**
             *
             */
            public void onItemClick(AdapterView< ? > parent, View view, int position, long id)
            {
                if (onLongPress)
                {
                    onLongPress = false;
                    toast.cancel();
                    return;
                }
                currentPos = position;
                browseTo(directoryEntries.get(position).getFile()); 
            }
        };
        
        onItemLongClickListener = new AdapterView.OnItemLongClickListener()
        {
            /**
             * 
             *(non-Javadoc)
             * @see android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)
             *
             */
            public boolean onItemLongClick(AdapterView< ? > parent, View view, int position, long id)
            {
                if (listType != LIST_TYPE_EXPLORE)
                {
                    FileItem item = directoryEntries.get(position);
                    if (item != null)
                    {
                        onLongPress = true;
                        toast.setText(item.getFile().getAbsolutePath().substring(1));
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, listView.getTop());
                        toast.show();
                    }
                }
                return false;
            }        
        };
        
    }
    
    /**
     * 
     */
    private void init()
    {
        mHeight -= getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        fileAdapter = new FileItemAdapter(getApplicationContext(), control);
        emptyView = new TextView(getApplicationContext());
        emptyView.setText(R.string.file_message_empty_directory);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        // 工具条
        FileToolsbar fileToolsbar = new FileToolsbar(getApplicationContext(), control);
        fileFrame.addView(fileToolsbar);
        
        // 列表
        listView = new ListView(getApplicationContext());
        listView.setOnItemClickListener(onItemClickListener);
        listView.setOnItemLongClickListener(onItemLongClickListener);
        fileFrame.addView(listView, new LayoutParams(LayoutParams.MATCH_PARENT, mHeight - fileToolsbar.getButtonHeight()));
        
        // 设置默认排序类型
        initSortType();
        createFileList(sdcardPath);
    }
    
    /**
     * 
     */
    private void createFileList(File file)
    {        
        Intent intent = getIntent();
        String type = intent.getStringExtra(MainConstant.INTENT_FILED_FILE_LIST_TYPE);        
        // marked
        if (MainConstant.INTENT_FILED_MARK_FILES.equals(type))
        {
            listType = LIST_TYPE_MARKED;
            currentDirectory = sdcardPath;            
            // get files from database
            List<File> fileList = new ArrayList<File>();
            dbService.get(MainConstant.TABLE_STAR, fileList);
            listFiles(fileList.toArray(new File[fileList.size()]));
            updateToolsbarStatus();
        }
        // recently
        else if(MainConstant.INTENT_FILED_RECENT_FILES.equals(type))
        {
            listType = LIST_TYPE_RECENTLY;
            currentDirectory = sdcardPath;  
            // get files from database
            List<File> fileList = new ArrayList<File>();
            dbService.get(MainConstant.TABLE_RECENT, fileList);
            // sort by opened time
            int nCount = fileList.size();
            File[] files = new File[nCount];
            for (int i = nCount - 1; i >= 0; i--)
            {
                files[nCount -1 - i] = fileList.get(i);
            }
            listFiles(files);
            updateToolsbarStatus();
        }
        // search files
        else if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
        {
            listType = LIST_TYPE_SEARCH;
            createSearchFileList(intent);
        }
        // explore
        else
        {
            listType = LIST_TYPE_EXPLORE;
            browseTo(file);
        }
    }
    
    /**
     * search file from current directory
     * @param query
     */
    public void createSearchFileList(Intent intent)
    {  
        String key = intent.getStringExtra(SearchManager.QUERY).trim();
        if (key.length() > 0)
        {
            listType = LIST_TYPE_SEARCH;
            if (search == null)
            {
                search = new Search(control, this);
            }
            search.doSearch(currentDirectory == null ? sdcardPath : currentDirectory, key, Search.SEARCH_BY_NAME);
            
            /*search.doSearch(currentDirectory == null ? sdcardPath : currentDirectory, 
                key, Search.SEARCH_BY_CONTENT);*/
            
            directoryEntries.clear();
            selectFileItem.clear();
            fileAdapter.setListItems(directoryEntries);
            listView.setAdapter(fileAdapter);
            setTitle(getResources().getString(R.string.sys_button_search));
            setProgressBarIndeterminateVisibility(true);
            
            updateToolsbarStatus();
        }
    }
    
    /**
     * 
     *
     */
    public void onResult(final File file)
    {      
        fileFrame.post(new Runnable()
        {   
            /**
             * 
             */
            public void run()
            {
                directoryEntries.add(new FileItem(file,
                    fileAdapter.getFileIconType(file.getName()), 0));
                fileAdapter.notifyDataSetChanged();
                
                updateToolsbarStatus();
            }
        });
        
    }
    
    /**
     * call with search finish
     */
    public void searchFinish()
    {
        if (fileFrame == null)
        {
            return;
        }        
        fileFrame.post(new Runnable()
        {   
            /**
             *
             */
            public void run()
            {
                setProgressBarIndeterminateVisibility(false);
                // 一个都没有搜索到
                if (directoryEntries.size() == 0)
                {
                    if (emptyView.getParent() == null)
                    {
                        fileFrame.addView(emptyView);
                    }
                    if (listType == LIST_TYPE_SEARCH)
                    {
                        emptyView.setText(R.string.sys_no_match);
                    }
                    else
                    {
                        emptyView.setText(R.string.file_message_empty_directory);
                    }
                    listView.setEmptyView(emptyView);
                }
                
            }
        });

    }
    
    /**
     * 
     *
     */
    public void actionEvent(int actionID, Object obj)
    {
        if (search != null)
        {
            search.stopSearch();
        }
        switch (actionID)
        {
            case EventConstant.SYS_SHOW_TOOLTIP:        // show tools tip
                if (obj != null && obj instanceof String)
                {
                    toast.setText((String)obj);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
                
            case EventConstant.SYS_CLOSE_TOOLTIP:       // close tools tip
                toast.cancel();
                break;
                
            case EventConstant.FILE_CREATE_FOLDER_ID:   // 新建文件夹
                Vector<Object> vector = new Vector<Object>();
                vector.add(currentDirectory.getAbsolutePath());
                new NewFolderDialog(control, this, dialogAction, vector, 
                    DialogConstant.CREATEFOLDER_DIALOG_ID, 
                    R.string.file_toolsbar_create_folder).show();
                break;               
                
            case EventConstant.FILE_RENAME_ID:          // 重命名
                fileRename();
                break;
                
            case EventConstant.FILE_COPY_ID:            // 复制
                bCopy = true;
                fileCopy();
                clearSelectFileItem();
                break;
                
            case EventConstant.FILE_CUT_ID:             // 剪切
                bCut = true;
                fileCopy();
                clearSelectFileItem();
                break;
                
            case EventConstant.FILE_PASTE_ID:           // 粘贴
                if (currentDirectory.canWrite())
                {
                    filePaste();
                    bCopy = false;
                    bCut = false;
                    updateToolsbarStatus();
                }
                else
                {
                    CharSequence message = getResources().getText(R.string.dialog_move_file_error);
                    new MessageDialog(control, this, dialogAction, null, 
                        DialogConstant.MESSAGE_DIALOG_ID, 
                        R.string.dialog_error_title, message.toString()).show();
                }
                break;
                
            case EventConstant.FILE_DELETE_ID:          // 删除 
                fileDelete();
                break;
                
            case EventConstant.SYS_SEARCH_ID:           // 搜索
                onSearchRequested();
                break;
                
            case EventConstant.FILE_SHARE_ID:           // 分享
                fileShare();
                break;
                
            case EventConstant.FILE_SORT_ID:            // 排序
                Vector<Object> vectorSort = new Vector<Object>();
                vectorSort.add(getSortType());
                vectorSort.add(getAscending());
                new SortDialog(control, this, dialogAction, vectorSort, 
                    DialogConstant.FILESORT_DIALOG_ID, 
                    R.string.file_toolsbar_sort, R.array.file_sort_items).show();
                break;
                
            case EventConstant.FILE_MARK_STAR_ID:       // 标星
                fileMarkStar();
                break;
                
            case EventConstant.FILE_PRINT_ID:           // 打印
                break;
                
            case EventConstant.FILE_REFRESH_ID:         // 刷新
                if (obj != null && (Boolean)obj)
                {
                    clearSelectFileItem();
                }
                browseTo(currentDirectory);
                break;
                
            case EventConstant.SYS_HELP_ID:             // 帮助
                Intent intent = new Intent(Intent.ACTION_VIEW, 
                    Uri.parse(getResources().getString(R.string.sys_url_wxiwei)));        
                startActivity(intent);
                break;
                
            case EventConstant.SYS_UPDATE_TOOLSBAR_BUTTON_STATUS: // 更新toolsbar button状态
                updateToolsbarStatus();
                break;
                
            case EventConstant.FILE_SORT_TYPE_ID:
                if (obj != null)
                {
                    @ SuppressWarnings("unchecked")
                    Vector<Object> model = (Vector<Object>)obj;
                    if (model != null)
                    {
                        setSortType((Integer)model.get(0), (Integer)model.get(1));
                        Collections.sort(directoryEntries, FileSort.instance());
                        fileAdapter.setListItems(directoryEntries);
                        listView.setAdapter(fileAdapter);
                    }
                }
                break;
                
            case EventConstant.FILE_CREATE_FOLDER_FAILED_ID:
                CharSequence text = getResources().getText(R.string.file_toolsbar_create_folder);
                new MessageDialog(control, this, dialogAction, null, 
                    DialogConstant.MESSAGE_DIALOG_ID, 
                    R.string.dialog_create_folder_error, text.toString()).show();
                break;

            default:
                break;
        }
    }
    
    /**
     * 浏览指定目录
     * 
     * @param aDirectory
     */
    private void browseTo(File aDirectory)
    {
        if (aDirectory.isDirectory())
        {
            currentDirectory = aDirectory;
            listFiles(aDirectory.listFiles());
            
            setTitle(aDirectory.getAbsolutePath());
            // update toolsbar
            updateToolsbarStatus();
            
        }
        else
        {
            if (FileKit.instance().isSupport(aDirectory.getName()))
            {
                Intent intent = new Intent();
                intent.setClass(this, AppActivity.class);
                intent.putExtra(MainConstant.INTENT_FILED_FILE_PATH, aDirectory.getAbsolutePath());
                startActivityForResult(intent, RESULT_FIRST_USER);
            }
        }
    }
    
    /**
     * 获取文件列表
     * @param files
     */
    private void listFiles(File[] files)
    {
        directoryEntries.clear();
        selectFileItem.clear();
        if (files != null)
        {
            // current directory is "/mnt"
            if (listType == LIST_TYPE_EXPLORE && isCurrentDirectory4mnt())
            {
                for (File currentFile : files)
                {                
                    if (currentFile.isDirectory())
                    {
                        String fileName = currentFile.getName();
                        if (fileName.startsWith("sdcard")
                            || fileName.startsWith("extern_sd")
                            || fileName.startsWith("usbhost"))
                        {
                            FileItem item  = new FileItem(currentFile, FileItemAdapter.ICON_TYPE_FOLDER, 0);
                            item.setShowCheckView(false);
                            directoryEntries.add(item);
                        }
                    }
                }
            }
            else
            {
                // 获取标星文档
                List<File> fileList = null;
                if (listType != LIST_TYPE_MARKED)
                {
                    fileList = new ArrayList<File>();
                    dbService.get(MainConstant.TABLE_STAR, fileList);
                }
                
                for (File currentFile : files)
                {
                    String fileName = currentFile.getName();
                    // 
                    if (fileName.startsWith("."))
                    {
                        continue;
                    }
                    else if (currentFile.isDirectory())
                    {
                        int iconType = FileItemAdapter.ICON_TYPE_FOLDER;
                        directoryEntries.add(new FileItem(currentFile, iconType, 0));
                    }
                    else
                    {                
                        int iconType = fileAdapter.getFileIconType(fileName);
                        if (iconType < 0)
                        {
                            continue;
                        }
                        int marked = listType == LIST_TYPE_MARKED || 
                            FileKit.instance().isFileMarked(currentFile.getAbsolutePath(), fileList) ? 1 : 0;
                        
                        directoryEntries.add(new FileItem(currentFile, iconType, marked));
                    }
                }
            }
        }        
        // 非空文件夹
        if (directoryEntries.size() > 0)
        {
            Collections.sort(directoryEntries, FileSort.instance());
            fileAdapter.setListItems(directoryEntries);
            listView.setAdapter(fileAdapter);
        }
        else
        {
            if (emptyView.getParent() == null)
            {
                fileFrame.addView(emptyView);
            }
            emptyView.setText(R.string.file_message_empty_directory);
            listView.setEmptyView(emptyView);
        }
    } 
    

    /**
     * 从AppActivity 返回
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_FIRST_USER)
        {
            if (resultCode == RESULT_OK)
            {
                updateMarkStatus(data.getBooleanExtra(MainConstant.INTENT_FILED_MARK_STATUS, false) ? 1 : 0);
            }
        }
    }
    
    /**
     * 从AppActivity 返回时更新标星状态发生改变的文档
     * @param marked
     */
    public void updateMarkStatus(int marked)
    {
        FileItem fileItem = directoryEntries.get(currentPos);
        if (fileItem != null)
        {
            // 标星文档
            if (listType == LIST_TYPE_MARKED && marked != 1)
            {
                directoryEntries.remove(currentPos);
                fileAdapter.notifyDataSetChanged();
                updateToolsbarStatus();
            }
            else
            {
                fileItem.setFileStar(marked);
                fileAdapter.notifyDataSetChanged();
            }
        }
    }
    
    /**
     * 增加选择fileItem
     */
    public void addSelectFileItem(FileItem fileItem)
    {
        selectFileItem.add(fileItem);
        updateToolsbarStatus();
    }
    
    /**
     * 删除选择的fileItem 
     */
    public void removeSelectFileItem(FileItem fileItem)
    {
        selectFileItem.remove(fileItem);
        updateToolsbarStatus();
    }
    
    /**
     * 清空选择的fileItem
     */
    public void clearSelectFileItem()
    {
        for (int i = 0; i < listView.getChildCount(); i++)
        {
            View v = listView.getChildAt(i);
            if (v instanceof FileItemView)
            {
                ((FileItemView)v).setSelected(false);
            }
        }
        selectFileItem.clear();
        updateToolsbarStatus();
    }
    
    /* ======= 以下为工具条相关操作 ========== */  
    /**
     * 更新工具条的状态
     */
    private void updateToolsbarStatus()
    {
        for (int i = 0; i < fileFrame.getChildCount(); i++)
        {
            View v = fileFrame.getChildAt(i);
            if (v instanceof AToolsbar)
            {
                ((AToolsbar)v).updateStatus();
            }
        }
    }
    
    /**
     * rename
     */
    public void fileRename()
    {
        Vector<Object> renameVector = new Vector<Object>();
        renameVector.add(selectFileItem.get(0).getFile());
        new FileRenameDialog(control, this, dialogAction, renameVector, 
            DialogConstant.RENAMEFILE_DIALOG_ID, 
            R.string.file_toolsbar_rename).show();
    }

    /**
     * copy
     */
    public void fileCopy()
    {
        StringBuffer fileNames = new StringBuffer();
        for (int i = 0; i < selectFileItem.size(); i++)
        {
            fileNames.append(selectFileItem.get(i).getFile().getAbsolutePath());
            fileNames.append(";");
        }
        ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(fileNames);
    }
    
    /**
     * check paste file
     */
    public void filePaste()
    {
        ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        CharSequence fileNames = clip.getText();
        String[] fileNameList = fileNames.toString().split(";");
        boolean paste = true;
        for (int i = 0; i < fileNameList.length; i++)
        {
            File tempFile = new File(fileNameList[i]);
            if (tempFile.exists())
            {
                if ((currentDirectory.getAbsolutePath() + File.separator).contains(
                    (tempFile.getAbsolutePath() + File.separator)))
                {
                    paste = false;
                    break;
                }
            }
        }
        if (paste)
        {
            //Vector<Object> vector = new Vector<Object>();
            for (int i = 0; i < fileNameList.length; i++)
            {
                File tempFile = new File(fileNameList[i]);
                if (tempFile.exists())
                {
                    File file;
                    String filePath = currentDirectory.getAbsolutePath();
                    if (filePath.endsWith(File.separator))
                    {
                        file = new File(filePath + tempFile.getName());
                    }
                    else
                    {
                        file = new File(filePath + File.separator + tempFile.getName());
                    }
                    if (!file.exists())
                    {
                        FileKit.instance().pasteFile(tempFile, file);
                        if (bCut)
                        {
                            FileKit.instance().deleteFile(tempFile);
                        }
                    }
                    else
                    {
                        Vector<Object> vector = new Vector<Object>();
                        vector.clear();  
                        vector.add(bCut);
                        vector.add(tempFile);
                        vector.add(file);
                        CharSequence text = getResources().getText(R.string.dialog_name_error);
                        String message = text.toString().replace("%s", tempFile.getName())
                            .concat(getResources().getText(R.string.dialog_overwrite_file).toString());
                        new QuestionDialog(control, this, dialogAction, vector, 
                            DialogConstant.OVERWRITEFILE_DIALOG_ID, 
                            R.string.dialog_error_title, message).show();
                    }
                }
            }
            browseTo(currentDirectory);
        }
        else
        {
            CharSequence message = getResources().getText(R.string.dialog_move_file_error);
            new MessageDialog(control, this, dialogAction, null, 
                DialogConstant.MESSAGE_DIALOG_ID, 
                R.string.dialog_error_title, message.toString()).show();
        }
    }
    
    /**
     * delete file
     */
    public void fileDelete()
    {
        Vector<Object> vector = new Vector<Object>();
        for (int i = 0; i < selectFileItem.size(); i++)
        {
            vector.add(selectFileItem.get(i).getFile());
        }
        CharSequence message = getResources().getText(R.string.dialog_delete_file);
        new QuestionDialog(control, this, dialogAction, vector, 
            DialogConstant.DELETEFILE_DIALOG_ID, 
            R.string.file_toolsbar_delete, message.toString()).show();
    }
    
    /**
     * search intent
     *
     */
    protected void onNewIntent(Intent intent) 
    {      
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
        {        
            createSearchFileList(intent);
        }  
    }
    
    /**
     * 发送邮件
     */
    public void fileShare()
    {
        ArrayList<Uri> list = new ArrayList<Uri>();
        for (int i = 0; i < selectFileItem.size(); i++)
        {
            list.add(Uri.fromFile(selectFileItem.get(i).getFile()));
        }
        Intent intent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);     
        intent.putExtra(Intent.EXTRA_STREAM, list);
        intent.setType("application/octet-stream");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.sys_share_title)));
    }
    
    /**
     * 设置默认排序类型
     */
    public void initSortType()
    {
        int type = FileSort.FILESORT_TYPE_DIR;
        String fileListType = getIntent().getStringExtra(MainConstant.INTENT_FILED_FILE_LIST_TYPE);
        // 最近打开的文档
        if(MainConstant.INTENT_FILED_RECENT_FILES.equals(fileListType))
        {
            type = FileSort.FILESORT_TYPE_NULL;;
        }
        FileSort.instance().setType(type, FileSort.FILESORT_ASCENDING);
    }
    
    /**
     * 
     * 获得排序类型
     */
    public int getSortType()
    {
        // 标星
        if (listType == LIST_TYPE_MARKED)
        {
            return fileSortType.getStarType();
        }
        // 最近打开的文档
        else if(listType == LIST_TYPE_RECENTLY)
        {
            return fileSortType.getRecentType();
        }
        // sdcard文档
        else
        {
            return fileSortType.getSdcardType();
        }
    }

    /**
     * 
     * 获得升降序
     */
    public int getAscending()
    {
        // 标星
        if (listType == LIST_TYPE_MARKED)
        {
            return fileSortType.getStarAscending();
        }
        // 最近打开的文档
        else if(listType == LIST_TYPE_RECENTLY)
        {
            return fileSortType.getRecentAscending();
        }
        // sdcard文档
        else
        {
            return fileSortType.getSdcardAscending();
        }
    }
 
    /**
     * set file sort type
     * @param typeName
     * @param childName
     */
    public void setSortType(int type, int ascending)
    {
        // 标星
        if (listType == LIST_TYPE_MARKED)
        {
            fileSortType.setStarType(type, ascending);
        }
        // 最近打开的文档
        else if(listType == LIST_TYPE_RECENTLY)
        {
            fileSortType.setRecentType(type, ascending);
        }
        // sdcard文档
        else
        {
            fileSortType.setSdcardType(type, ascending);
        }
        FileSort.instance().setType(type, ascending);
    }
  
    /**
     * file mark star
     */
    public void fileMarkStar()
    {        
        FileItem fileItem = selectFileItem.get(0);
        if (fileItem.getFileStar() == 1)
        {
            fileItem.setFileStar(0);
            dbService.deleteItem(MainConstant.TABLE_STAR, fileItem.getFile().getAbsolutePath());
        }
        else
        {
            fileItem.setFileStar(1);
            dbService.insertStarFiles(MainConstant.TABLE_STAR, fileItem.getFile().getAbsolutePath());
        }
        fileAdapter.notifyDataSetChanged();
    }
    
    /**
     * 
     */
    public boolean isCut()
    {
        return bCut;
    }
    /**
     * 
     */
    public boolean isCopy()
    {
        return bCopy;
    }
    
    /**
     * 
     */
    public byte getListType()
    {
        return listType;
    }
    
    /**
     * 
     */
    public List<FileItem> getSelectFileItem()
    {
        return selectFileItem;
    }
    
    /**
     * 
     */
    public boolean isCurrentDirectory4mnt()
    {
        return listType == LIST_TYPE_EXPLORE 
            && currentDirectory != null 
            && currentDirectory.getAbsolutePath().equals("/mnt");
    }
    
    /**
     * 
     */
    public int getCurrentDirectoryFileSize()
    {
        return directoryEntries.size();
    }
    
    /**
     * 
     *
     */
    public void dispose()
    {
        currentDirectory = null;
        sdcardPath = null;
        if (directoryEntries != null)
        {
            directoryEntries.clear();
            directoryEntries = null;
        }
        if (selectFileItem != null)
        {
            selectFileItem.clear();
            selectFileItem = null;
        }
        if (fileAdapter != null)
        {
            fileAdapter.dispose();
            fileAdapter = null;   
        }
        int count = listView.getChildCount();
        for (int i = 0; i < count; i++)
        {
            View v = listView.getChildAt(i);
            if (v instanceof FileItemView)
            {
                ((FileItemView)v).dispose();
            }
        }
        listView = null;
        toast = null;
        onItemClickListener = null;
        onItemLongClickListener = null;
        emptyView = null;
        
        count = fileFrame.getChildCount();
        for (int i = 0; i < count; i++)
        {
            View v = fileFrame.getChildAt(i);
            if (v instanceof AToolsbar)
            {
                ((AToolsbar)v).dispose();
            }
        }
        fileFrame = null;
        if (dialogAction != null)
        {
            dialogAction.dispose();
            dialogAction = null;
        }
        if (fileSortType != null)
        {
            fileSortType.dispos();
            fileSortType = null;
        }
        if (dbService != null)
        {
            dbService.dispose();
            dbService = null;
        }
        if (search != null)
        {
            search.dispose();
            search = null;
        }
        if (control != null)
        {
            control.dispose();
            control = null;
        }
    }
    
    // list byte
    private byte listType;
    //
    private boolean onLongPress;
    //
    private boolean bCut;
    //
    private boolean bCopy;
    // 
    private int currentPos;
    //
    private int mHeight;
    //
    private IControl control;
    // 当前所在目录
    private File currentDirectory;
    // sdcard path
    private File sdcardPath;
    //
    private ListView listView;
    //
    private FileFrame fileFrame;
    // 当前目录下的子目录
    private List<FileItem> directoryEntries;
    // 记录选取的FileItem
    private List<FileItem> selectFileItem;
    //toast
    private Toast toast;
    //
    private AdapterView.OnItemClickListener onItemClickListener;
    //
    private AdapterView.OnItemLongClickListener onItemLongClickListener;
    //
    private FileItemAdapter fileAdapter;
    // 空文件夹显示的信息 
    private TextView emptyView;
    //
    private FileDialogAction dialogAction;
    //
    private FileSortType fileSortType;
    //
    private DBService dbService;
    //
    private Search search;    
}
