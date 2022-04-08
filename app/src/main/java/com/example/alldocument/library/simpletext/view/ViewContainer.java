/*
 * 文件名称:          ViewContainer.java
 *  
 * 编译器:            android2.2
 * 时间:              下午2:03:24
 */
package com.example.alldocument.library.simpletext.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.alldocument.library.constant.wp.WPModelConstant;
import com.example.alldocument.library.simpletext.view.IView;

/**
 * paragraph view container
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-11-20
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class ViewContainer
{    
    
    public ViewContainer()
    {
        paras = new ArrayList<com.example.alldocument.library.simpletext.view.IView>();
    }
    
    /**
     * 
     */
    public synchronized void add(com.example.alldocument.library.simpletext.view.IView para)
    {
        if (para != null && para.getEndOffset(null) < WPModelConstant.HEADER)
        {
            paras.add(para);
        }
    }
    
    /**
     * 
     */
    public synchronized void sort()
    {
        try
        {
            Collections.sort(paras, new Comparator<com.example.alldocument.library.simpletext.view.IView>()
            {
                public int compare(com.example.alldocument.library.simpletext.view.IView prePara, com.example.alldocument.library.simpletext.view.IView nextPara)
                {
                    return prePara.getEndOffset(null) <= nextPara.getStartOffset(null) ? -1 : 1;
                }
            });
        }
        catch (Exception e)
        {
            
        }
    }
    
    /**
     * 
     */
    public synchronized com.example.alldocument.library.simpletext.view.IView getParagraph(long offset, boolean isBack)
    {
        int size = paras.size();
        if (size == 0 || offset < 0 || offset >= paras.get(size - 1).getEndOffset(null))
        {
            return null;
        }
        int max = size;
        int min = 0;
        com.example.alldocument.library.simpletext.view.IView view;
        long start;
        long end;
        int mid = -1;
        while (true)
        {
            mid = (max + min) / 2;
            view = paras.get(mid);
            start = view.getStartOffset(null);
            end = view.getEndOffset(null);
            if (offset >= start && offset < end)
            {
                break;
            }
            else if (start > offset)
            {
                max = mid - 1;
            }
            else if (end <= offset)
            {
                min = mid + 1;
            }
        }
        return view;
    }    
    
    /**
     * 
     */
    public synchronized void clear()
    {
        if (paras != null)
        {
            paras.clear();
        }
    }
    
    /**
     * 
     */
    public synchronized void dispose()
    {
        if (paras != null)
        {
            paras.clear();
            paras = null;
        }
    }
    
    // 
    private List<IView> paras;
}
