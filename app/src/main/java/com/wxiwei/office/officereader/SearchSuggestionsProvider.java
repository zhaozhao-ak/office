/*
 * 文件名称:           SearchSuggestionsProvider.java
 *  
 * 编译器:             android2.2
 * 时间:               下午1:44:04
 */
package com.wxiwei.office.officereader;

import android.content.SearchRecentSuggestionsProvider;

/**
 * search provider
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2011-12-26
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class SearchSuggestionsProvider extends SearchRecentSuggestionsProvider
{
    final static String AUTHORITY = "searchprovider";
    final static int MODE = DATABASE_MODE_QUERIES;
   
    /**
     * 
     */
    public SearchSuggestionsProvider() 
    {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}
