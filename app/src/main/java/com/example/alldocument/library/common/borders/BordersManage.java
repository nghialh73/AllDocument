/*
 * 文件名称:          	BordersManage.java
 *  
 * 编译器:            android2.2
 * 时间:             	上午9:38:51
 */
package com.example.alldocument.library.common.borders;

import com.example.alldocument.library.common.borders.Borders;

import java.util.ArrayList;
import java.util.List;


/**
 * border manage
 * <p>
 * <p>
 * Read版本:        	Office engine V1.0
 * <p>
 * 作者:            	ljj8494
 * <p>
 * 日期:            	2013-3-18
 * <p>
 * 负责人:          	ljj8494
 * <p>
 * 负责小组:        	TMC
 * <p>
 * <p>
 */
public class BordersManage
{
    /**
     * 
     */
    public int addBorders(com.example.alldocument.library.common.borders.Borders bs)
    {
        int size = borders.size();
        borders.add(bs);
        return size;
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public com.example.alldocument.library.common.borders.Borders getBorders(int index)
    {
        return borders.get(index);
    }
    
    /**
     * 
     */
    public void dispose()
    {
        if (borders != null)
        {
            borders.clear();
            borders = null;
        }
    }
    
    private List<com.example.alldocument.library.common.borders.Borders> borders = new ArrayList<Borders>();
}
