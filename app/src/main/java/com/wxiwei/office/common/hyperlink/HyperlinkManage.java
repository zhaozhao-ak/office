/*
 * 文件名称:           HyperlinkManage.java
 *  
 * 编译器:             android2.2
 * 时间:               上午10:01:57
 */
package com.wxiwei.office.common.hyperlink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wxiwei.office.common.hyperlink.Hyperlink;


/**
 * hyperlink manage
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2012-3-7
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class HyperlinkManage
{    
    /**
     * 
     */
    public HyperlinkManage()
    {
        hlinks = new ArrayList<Hyperlink>();
        hlinkIndexs = new HashMap<String, Integer>();
    }
   
    /**
     * get hyperlink for hlinkID
     */
    public Hyperlink getHyperlink(int hlinkID)
    {
        if (hlinkID >= 0 && hlinkID < hlinks.size())
        {
            return hlinks.get(hlinkID);
        }
        return null;
    }
    
    /**
     * 
     */
    public Integer getHyperlinkIndex(String key)
    {
        return hlinkIndexs.get(key);
    }
    
    /**
     * add hyperlink
     */
    public int addHyperlink(String address, int linkType)
    {
        Integer index = hlinkIndexs.get(address);
        if (index == null)
        {
            Hyperlink hlink = new Hyperlink();
            hlink.setLinkType(linkType);
            hlink.setAddress(address);
            
            int size = hlinks.size();
            hlinks.add(hlink);
            hlinkIndexs.put(address, size);            
            return size;
        }
        return index;
    }
    
    /**
     * 
     */
    public void dispose()
    {
        if (hlinks != null)
        {
            for (Hyperlink hyperlink : hlinks)
            {
                hyperlink.dispose();
            }
            hlinks.clear();
        }
        if (hlinkIndexs != null)
        {
            hlinkIndexs.clear();
        }
    }
  
    //
    private List<Hyperlink> hlinks;
    //
    private Map<String, Integer> hlinkIndexs;
}
