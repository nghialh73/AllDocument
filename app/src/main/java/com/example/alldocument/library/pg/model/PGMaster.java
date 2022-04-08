/*
 * 文件名称:           PGMaster.java
 *  
 * 编译器:             android2.2
 * 时间:               下午12:03:57
 */
package com.example.alldocument.library.pg.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.alldocument.library.common.bg.BackgroundAndFill;
import com.example.alldocument.library.java.awt.Rectangle;
import com.example.alldocument.library.pg.model.PGPlaceholderUtil;
import com.example.alldocument.library.pg.model.PGStyle;
import com.example.alldocument.library.simpletext.model.IAttributeSet;

/**
 * master 数据
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2012-3-3
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class PGMaster
{
    /**
     * 
     */
    public PGMaster()
    {
        schemeColor = new HashMap<String, Integer>();
        styleByType = new HashMap<String, com.example.alldocument.library.pg.model.PGStyle>();
        styleByIdx = new HashMap<Integer, com.example.alldocument.library.pg.model.PGStyle>();
    }
    
    /**
     * get color
     */
    public int getColor(String schemeClr)
    {
        return schemeColor.get(schemeClr);
    }
    
    /**
     * set color
     */
    public void addColor(String schemeClr, int color)
    {
        schemeColor.put(schemeClr, color);
    }
    
    /**
     * get background
     */
    public BackgroundAndFill getBackgroundAndFill()
    {
        return bgFill;
    }
   
    /**
     * set background
     */
    public void setBackgroundAndFill(BackgroundAndFill bgFill)
    {
        this.bgFill = bgFill;
    }
 
    /**
     * set style by type
     */
    public void addStyleByType(String type, com.example.alldocument.library.pg.model.PGStyle style)
    {
        styleByType.put(type, style);
    }
    
    /**
     * set style by index
     */
    public void addStyleByIdx(int idx, com.example.alldocument.library.pg.model.PGStyle style)
    {
        styleByIdx.put(idx, style);
    }
    
    /**
     * set title style
     */
    public void setTitleStyle(com.example.alldocument.library.pg.model.PGStyle style)
    {
        titleStyle = style;
    }
    
    /**
     * set body style
     */
    public void setBodyStyle(com.example.alldocument.library.pg.model.PGStyle style)
    {
        bodyStyle = style;
    }
    
    /**
     * set other style
     */
    public void setDefaultStyle(com.example.alldocument.library.pg.model.PGStyle style)
    {
        otherStyle = style;
    }
    
    /**
     * get anchor by shape type or index
     */
    public Rectangle getAnchor(String type, int idx)
    {
        type = PGPlaceholderUtil.instance().checkTypeName(type);
        if (!PGPlaceholderUtil.instance().isBody(type))
        {
            com.example.alldocument.library.pg.model.PGStyle style = styleByType.get(type);
            if (style != null)
            {
                return style.getAnchor();
            }
        }
        else if (idx > 0)
        {
            com.example.alldocument.library.pg.model.PGStyle style = styleByIdx.get(idx);
            if (style == null)
            {
                Iterator<Integer> iter = styleByIdx.keySet().iterator();
                if (iter.hasNext())
                {
                    style = styleByIdx.get(iter.next());
                }
            }
            if (style != null)
            {
                return style.getAnchor();
            }
        }
        return null;
    }
 
    /**
     * get shape section attribute by type or index
     */
    public IAttributeSet getSectionAttr(String type, int idx)
    {
        type = PGPlaceholderUtil.instance().checkTypeName(type);        
        if (!PGPlaceholderUtil.instance().isBody(type))
        {
            com.example.alldocument.library.pg.model.PGStyle style = styleByType.get(type);
            if (style != null)
            {
                return style.getSectionAttr();
            }
        }
        else if (idx > 0)
        {
            com.example.alldocument.library.pg.model.PGStyle style = styleByIdx.get(idx);
            if (style == null)
            {
                Iterator<Integer> iter = styleByIdx.keySet().iterator();
                if (iter.hasNext())
                {
                    style = styleByIdx.get(iter.next());
                }
            }
            if (style != null)
            {
                return style.getSectionAttr();
            }
        }
        return null;
    }
   
    /**
     * get text default style
     */
    public int getTextStyle(String type, int idx, int lvl)
    {
        int styleID = -1;
        type = PGPlaceholderUtil.instance().checkTypeName(type);
        if (!PGPlaceholderUtil.instance().isBody(type))
        {
            com.example.alldocument.library.pg.model.PGStyle style = styleByType.get(type);
            if (style != null)
            {
                styleID = style.getStyle(lvl);
                if (styleID >= 0)
                {
                    return styleID;
                }
            }
            
            if (PGPlaceholderUtil.TITLE.equals(type))
            {
                if (titleStyle != null)
                {
                    return titleStyle.getStyle(lvl);
                }
            }
            else
            {
                if (otherStyle != null)
                {
                    return otherStyle.getStyle(lvl);
                }
            }
        }
        else if (idx > 0)
        {
            com.example.alldocument.library.pg.model.PGStyle style = styleByIdx.get(idx);
            if (style == null)
            {
                Iterator<Integer> iter = styleByIdx.keySet().iterator();
                if (iter.hasNext())
                {
                    style = styleByIdx.get(iter.next());
                }
            }
            
            if (style != null)
            {
                styleID = style.getStyle(lvl);
                if (styleID >= 0)
                {
                    return styleID;
                }
            }
            
            if (bodyStyle != null)
            {
                return bodyStyle.getStyle(lvl);
            }
        }
        return -1;
    }
 
    /**
     * 
     * @return
     */
    public Map<String, Integer> getSchemeColor()
    {
        return schemeColor;
    }
    
    /**
     * 
     * @return
     */
    public int getSlideMasterIndex()
    {
        return index;
    }
    
    /**
     * 
     * @param index
     */
    public void setSlideMasterIndex(int index)
    {
        this.index = index;
    }
    
    public void addTitleBodyID(int idx, int id)
    {
        if(titlebodyID == null)
        {
            titlebodyID = new HashMap<Integer, Integer>();
        }
        titlebodyID.put(idx, id);
    }
    
    public Integer getTitleBodyID(int idx)
    {
        if(titlebodyID != null)
        {
            return titlebodyID.get(idx);
        } 
        return null;
    }
    
    /**
     * dispose
     */
    public void dispose()
    {
        if (bgFill != null)
        {
            bgFill.dispose();
            bgFill = null;
        }
        schemeColor.clear();
        schemeColor = null;
        if (styleByType != null)
        {
            Iterator<String> iter = styleByType.keySet().iterator();
            while(iter.hasNext())
            {
                styleByType.get(iter.next()).dispose();
            }
            styleByType.clear();
            styleByType = null;
        }
        if (styleByIdx != null)
        {
            Iterator<Integer> iter = styleByIdx.keySet().iterator();
            while(iter.hasNext())
            {
                styleByIdx.get(iter.next()).dispose();
            }
            styleByIdx.clear();
            styleByIdx = null;
        }
        if (titleStyle != null)
        {
            titleStyle.dispose();
            titleStyle = null;
        }
        if (bodyStyle != null)
        {
            bodyStyle.dispose();
            bodyStyle = null;
        }
        if (otherStyle != null)
        {
            otherStyle.dispose();
            otherStyle = null;
        }      
        if(titlebodyID != null)
        {
            titlebodyID.clear();
            titlebodyID =  null;
        }
    }

    // background
    private BackgroundAndFill bgFill;
    // color map
    private Map<String, Integer> schemeColor;
    // style by type
    private Map<String, com.example.alldocument.library.pg.model.PGStyle> styleByType;
    // body style
    private Map<Integer, com.example.alldocument.library.pg.model.PGStyle> styleByIdx;
    //shape(just for title and body) idx, shape id
    private Map<Integer, Integer> titlebodyID;
    // title style
    private com.example.alldocument.library.pg.model.PGStyle titleStyle;
    // body style
    private com.example.alldocument.library.pg.model.PGStyle bodyStyle;
    // other style
    private PGStyle otherStyle;
    // masterslide index
    private int index = -1;
}
