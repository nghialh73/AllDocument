/*
 * 文件名称:           HSSFFreeform.java
 *  
 * 编译器:             android2.2
 * 时间:               下午2:18:18
 */
package com.example.alldocument.library.fc.hssf.usermodel;

import com.example.alldocument.library.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.example.alldocument.library.fc.ShapeKit;
import com.example.alldocument.library.fc.ddf.EscherContainerRecord;
import com.example.alldocument.library.fc.hssf.usermodel.HSSFAnchor;
import com.example.alldocument.library.fc.hssf.usermodel.HSSFAutoShape;
import com.example.alldocument.library.fc.hssf.usermodel.HSSFShape;
import com.example.alldocument.library.java.awt.Rectangle;
import com.example.alldocument.library.ss.model.XLSModel.AWorkbook;

import android.graphics.Path;
import android.graphics.PointF;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:       Read V1.0
 * <p>
 * 作者:           jhy1790
 * <p>
 * 日期:           2013-4-2
 * <p>
 * 负责人:         jhy1790
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class HSSFFreeform extends HSSFAutoShape
{   
    public HSSFFreeform(AWorkbook workbook, EscherContainerRecord escherContainer,
                        HSSFShape parent, HSSFAnchor anchor, int shapeType)
    {
        super(workbook, escherContainer, parent, anchor, shapeType);
        processLineWidth();
        processArrow(escherContainer);
    }

    /**
     * Gets the freeform path
     *
     * @return the freeform path
     */
    public Path[] getFreeformPath(Rectangle rect, PointF startArrowTailCenter, byte startArrowType,  PointF endArrowTailCenter, byte endArrowType)
    {
        return ShapeKit.getFreeformPath(escherContainer, rect, startArrowTailCenter, startArrowType, endArrowTailCenter, endArrowType);
    }
    
    public ArrowPathAndTail getStartArrowPath(Rectangle rect)
    {
        return ShapeKit.getStartArrowPathAndTail(escherContainer, rect);
    }
    
    public ArrowPathAndTail getEndArrowPath(Rectangle rect)
    {
        return ShapeKit.getEndArrowPathAndTail(escherContainer, rect);
    }    
}
