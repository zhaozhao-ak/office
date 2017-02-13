/*
 * 文件名称:          HighSearch.java
 *  
 * 编译器:            android2.2
 * 时间:              上午10:51:56
 */
package com.wxiwei.office.officereader.search;

import java.io.File;

import com.wxiwei.office.constant.MainConstant;
import com.wxiwei.office.fc.doc.DOCReader;
import com.wxiwei.office.fc.doc.DOCXReader;
import com.wxiwei.office.fc.doc.TXTReader;
import com.wxiwei.office.fc.pdf.PDFReader;
import com.wxiwei.office.fc.ppt.PPTReader;
import com.wxiwei.office.fc.ppt.PPTXReader;
import com.wxiwei.office.fc.xls.XLSReader;
import com.wxiwei.office.fc.xls.XLSXReader;
import com.wxiwei.office.system.AbortReaderError;
import com.wxiwei.office.system.FileKit;
import com.wxiwei.office.system.IControl;
import com.wxiwei.office.system.IReader;


/**
 * high level search
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-3-26
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class Search
{
    // by name
    public static final byte SEARCH_BY_NAME = 0;
    // by content
    public static final byte SEARCH_BY_CONTENT = 1;
    // by author
    public static final byte SEARCH_BY_AUTHOR = 2;
    
    /**
     * 
     */
    public Search(IControl control, ISearchResult searchResult)
    {
        this.control = control;
        this.searchResult = searchResult;
        
        reader = null;
    }
    
    
    /**
     * 
     */
    public void doSearch(File directory, String key, byte searchType)
    {
        stopSearch = false;
        if (searching)
        {
            return;
        }
        searching = true;
        new SearchThread(directory, key, searchType).start();
    }
    
    /**
     * 
     */
    public void stopSearch()
    {
        if(reader != null)
        {
            reader.abortReader();
        }
        
        stopSearch = true;
    }
    
    /**
     * search thread
     */
    class SearchThread extends Thread
    {
        
        public SearchThread(File directory, String key, byte searchType)
        {
            this.directory = directory;
            this.key = key;
            this.searchType = searchType;
        }
        
        /**
         * 
         */
        public void run()
        {
            searchFiles(directory, key);
            searching = false;
            if (searchResult != null)
            {
                searchResult.searchFinish();
            }
        }
        
        /**
         * search file
         * @param query
         * @param directory
         * @param fileList
         */
        private void searchFiles(File directory, String key)
        {
            key = key.toLowerCase();
            
            File[] files = directory.listFiles();
            if (files == null)
            {
                return;
            }
            for (File file : files)
            {
                if (stopSearch)
                {
                    return;
                }
                if (file.isDirectory())
                {
                    searchFiles(file, key);
                }
                else
                {
                    String fileName = file.getName();
                    if (FileKit.instance().isSupport(fileName))
                    {
                        if (searchType == SEARCH_BY_NAME)
                        {
                            if (fileName.toLowerCase().indexOf(key) > -1)
                            {
                                searchResult.onResult(file);
                            }
                        }
                        else if (searchType == SEARCH_BY_CONTENT)
                        {
                            try
                            {
                                searchContent(file);
                            }
                            catch(AbortReaderError e)
                            {
                                if(reader != null)
                                {
                                    reader.dispose();
                                    reader = null;
                                }
                                
                                break;
                            }
                            catch (Exception e)
                            {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        
        /**
         * 
         */
        private void searchContent(File file) throws Exception
        {            
            String fileName = file.getName().toLowerCase();
            // doc
            if (fileName.endsWith(MainConstant.FILE_TYPE_DOC)
                || fileName.endsWith(MainConstant.FILE_TYPE_DOT))
            {
                reader = new DOCReader(null, file.getAbsolutePath());
            }
            // docx
            else if (fileName.endsWith(MainConstant.FILE_TYPE_DOCX)
                || fileName.endsWith(MainConstant.FILE_TYPE_DOTX)
                || fileName.endsWith(MainConstant.FILE_TYPE_DOTM))
            {
                reader = new DOCXReader(null, file.getAbsolutePath());
            }
            //
            else if (fileName.endsWith(MainConstant.FILE_TYPE_TXT))
            {
                reader = new TXTReader(null, file.getAbsolutePath(), "GBK");
                reader.dispose();                
            }
            // xls
            else if (fileName.endsWith(MainConstant.FILE_TYPE_XLS)
                || fileName.endsWith(MainConstant.FILE_TYPE_XLT))
            {
                reader = new XLSReader(control, file.getAbsolutePath());
            }
            // xlsx
            else if (fileName.endsWith(MainConstant.FILE_TYPE_XLSX)
                || fileName.endsWith(MainConstant.FILE_TYPE_XLTX)
                || fileName.endsWith(MainConstant.FILE_TYPE_XLTM)
                || fileName.endsWith(MainConstant.FILE_TYPE_XLSM))
            {
                reader = new XLSXReader(control, file.getAbsolutePath());
            }
            // ppt
            else if (fileName.endsWith(MainConstant.FILE_TYPE_PPT)
                || fileName.endsWith(MainConstant.FILE_TYPE_POT))
            {
                reader = new PPTReader(control, file.getAbsolutePath());                
            }
            // pptx
            else if (fileName.endsWith(MainConstant.FILE_TYPE_PPTX)
                || fileName.endsWith(MainConstant.FILE_TYPE_PPTM)
                || fileName.endsWith(MainConstant.FILE_TYPE_POTX)
                || fileName.endsWith(MainConstant.FILE_TYPE_POTM))
            {
                reader = new PPTXReader(control, file.getAbsolutePath());
            }  
            // PDF document
            else if (fileName.endsWith(MainConstant.FILE_TYPE_PDF))
            {
                reader = new PDFReader(control, file.getAbsolutePath());;
            }
            reader.dispose();
            reader = null;
        }
        
        //
        private byte searchType;
        //
        private File directory;
        //
        private String key;
    }
    
    /**
     * 
     */
    public void dispose()
    {
        //searchResult = null;
        control = null;
        searchResult = null;
        reader = null;
    }
    
    //
    private boolean stopSearch;
    //
    private boolean searching;
    //
    private ISearchResult searchResult;
    //
    private IControl control;
    
    //search content reader
    private IReader reader;

}
