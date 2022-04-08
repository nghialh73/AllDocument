/*
 * 文件名称:          GroupShape.java
 *  
 * 编译器:            android2.2
 * 时间:              下午5:11:59
 */
package com.example.alldocument.library.common.shape;

import com.example.alldocument.library.common.shape.AbstractShape;
import com.example.alldocument.library.common.shape.IShape;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2013-4-7
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class GroupShape extends AbstractShape
{
    /**
     * 
     */
    public GroupShape()
    {
        shapes = new ArrayList<com.example.alldocument.library.common.shape.IShape>();
    }
    
    /**
     * 
     *
     */
    public short getType()
    {
        return SHAPE_GROUP;
    }
    
    public int getOffX()
    {
        return offX;
    }

    public void setOffX(int offX)
    {
        this.offX = offX;
    }

    public int getOffY()
    {
        return offY;
    }

    public void setOffY(int offY)
    {
        this.offY = offY;
    }

    public void setOffPostion(int offX, int offY)
    {
        this.offX = offX;
        this.offY = offY;
    }
    
    /**
     * append shape of this slide
     */
    public void appendShapes(com.example.alldocument.library.common.shape.IShape shape)
    {
        this.shapes.add(shape);
    }
    
    /**
     * get all shapes of this slide
     */
    public com.example.alldocument.library.common.shape.IShape[] getShapes()
    {
        return shapes.toArray(new com.example.alldocument.library.common.shape.IShape[shapes.size()]);
    }
    
    private int offX, offY;
    // shapes of this slide
    private List<IShape> shapes;
}
