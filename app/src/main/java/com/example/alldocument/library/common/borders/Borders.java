/*
 * 文件名称:          	Borders.java
 *  
 * 编译器:            android2.2
 * 时间:             	上午9:40:44
 */
package com.example.alldocument.library.common.borders;

import com.example.alldocument.library.common.borders.Border;

/**
 * border
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
public class Borders
{

    public Borders()
    {
        
    }
    
    /**
     * 
     * @return
     */
    public com.example.alldocument.library.common.borders.Border getTopBorder()
    {
        return this.top;
    }
    /**
     *
     */
    public void setTopBorder(com.example.alldocument.library.common.borders.Border b)
    {
        this.top = b;
    }
    
    
    /**
     * 
     * @return
     */
    public com.example.alldocument.library.common.borders.Border getLeftBorder()
    {
        return this.left;
    }
    /**
     *
     */
    public void setLeftBorder(com.example.alldocument.library.common.borders.Border b)
    {
        this.left = b;
    }
    
    
    /**
     * 
     * @return
     */
    public com.example.alldocument.library.common.borders.Border getRightBorder()
    {
        return this.right;
    }
    /**
     *
     */
    public void setRightBorder(com.example.alldocument.library.common.borders.Border b)
    {
        this.right = b;
    }
    
    /**
     * 
     * @return
     */
    public com.example.alldocument.library.common.borders.Border getBottomBorder()
    {
        return this.bottom;
    }
    /**
     *
     */
    public void setBottomBorder(com.example.alldocument.library.common.borders.Border b)
    {
        this.bottom = b;
    }
    
    /**
     * 
     * @return
     */
    public byte getOnType()
    {
        return onType;
    }
    /**
     * 
     */
    public void setOnType(byte onType)
    {
        this.onType = onType;
    }
    
    //
    private com.example.alldocument.library.common.borders.Border left;
    //
    private com.example.alldocument.library.common.borders.Border top;
    //
    private com.example.alldocument.library.common.borders.Border right;
    //
    private Border bottom;
    // on page or on text
    private byte onType;
}
