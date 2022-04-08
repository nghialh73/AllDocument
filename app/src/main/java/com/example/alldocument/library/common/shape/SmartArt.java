/*
 * 文件名称:          SmartArt.java
 *  
 * 编译器:            android2.2
 * 时间:              上午9:10:05
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
 * 日期:            2013-4-17
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class SmartArt extends AbstractShape
{

    /**
     * 
     */
    public SmartArt()
    {
        shapes = new ArrayList<com.example.alldocument.library.common.shape.IShape>();
    }
    
    /**
     * 
     *
     */
    public short getType()
    {
        return SHAPE_SMARTART;
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
