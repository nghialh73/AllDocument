/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package com.example.alldocument.library.fc.hslf.model;

import com.example.alldocument.library.fc.ShapeKit;
import com.example.alldocument.library.fc.ddf.EscherContainerRecord;
import com.example.alldocument.library.fc.ddf.EscherOptRecord;
import com.example.alldocument.library.fc.ddf.EscherProperties;
import com.example.alldocument.library.fc.hslf.model.Line;
import com.example.alldocument.library.fc.hslf.model.Shape;
import com.example.alldocument.library.fc.hslf.model.ShapeTypes;
import com.example.alldocument.library.fc.hslf.model.Table;
import com.example.alldocument.library.fc.hslf.model.TextBox;
import com.example.alldocument.library.java.awt.Rectangle;


/**
 * Represents a cell in a ppt table
 *
 * @author Yegor Kozlov
 */
public final class TableCell extends TextBox
{
    protected static final int DEFAULT_WIDTH = 100;
    protected static final int DEFAULT_HEIGHT = 40;

    private com.example.alldocument.library.fc.hslf.model.Line borderLeft;
    private com.example.alldocument.library.fc.hslf.model.Line borderRight;
    private com.example.alldocument.library.fc.hslf.model.Line borderTop;
    private com.example.alldocument.library.fc.hslf.model.Line borderBottom;

    /**
     * Create a TableCell object and initialize it from the supplied Record container.
     *
     * @param escherRecord       <code>EscherSpContainer</code> container which holds information about this shape
     * @param parent    the parent of the shape
     */
    protected TableCell(EscherContainerRecord escherRecord, com.example.alldocument.library.fc.hslf.model.Shape parent)
    {
        super(escherRecord, parent);
    }

    /**
     * Create a new TableCell. This constructor is used when a new shape is created.
     *
     * @param parent    the parent of this Shape. For example, if this text box is a cell
     * in a table then the parent is Table.
     */
    public TableCell(Shape parent)
    {
        super(parent);

        setShapeType(ShapeTypes.Rectangle);
        //_txtrun.setRunType(TextHeaderAtom.HALF_BODY_TYPE);
        //_txtrun.getRichTextRuns()[0].setFlag(false, 0, false);
    }

    protected EscherContainerRecord createSpContainer(boolean isChild)
    {
        _escherContainer = super.createSpContainer(isChild);
        EscherOptRecord opt = (EscherOptRecord)ShapeKit.getEscherChild(_escherContainer,
            EscherOptRecord.RECORD_ID);
        setEscherProperty(opt, EscherProperties.TEXT__TEXTID, 0);
        setEscherProperty(opt, EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 0x20000);
        setEscherProperty(opt, EscherProperties.FILL__NOFILLHITTEST, 0x150001);
        setEscherProperty(opt, EscherProperties.SHADOWSTYLE__SHADOWOBSURED, 0x20000);
        setEscherProperty(opt, EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 0x40000);

        return _escherContainer;
    }

    protected void anchorBorder(int type, com.example.alldocument.library.fc.hslf.model.Line line)
    {
        Rectangle cellAnchor = getAnchor();
        Rectangle lineAnchor = new Rectangle();
        switch (type)
        {
            case com.example.alldocument.library.fc.hslf.model.Table.BORDER_TOP:
                lineAnchor.x = cellAnchor.x;
                lineAnchor.y = cellAnchor.y;
                lineAnchor.width = cellAnchor.width;
                lineAnchor.height = 0;
                break;
            case com.example.alldocument.library.fc.hslf.model.Table.BORDER_RIGHT:
                lineAnchor.x = cellAnchor.x + cellAnchor.width;
                lineAnchor.y = cellAnchor.y;
                lineAnchor.width = 0;
                lineAnchor.height = cellAnchor.height;
                break;
            case com.example.alldocument.library.fc.hslf.model.Table.BORDER_BOTTOM:
                lineAnchor.x = cellAnchor.x;
                lineAnchor.y = cellAnchor.y + cellAnchor.height;
                lineAnchor.width = cellAnchor.width;
                lineAnchor.height = 0;
                break;
            case com.example.alldocument.library.fc.hslf.model.Table.BORDER_LEFT:
                lineAnchor.x = cellAnchor.x;
                lineAnchor.y = cellAnchor.y;
                lineAnchor.width = 0;
                lineAnchor.height = cellAnchor.height;
                break;
            default:
                throw new IllegalArgumentException("Unknown border type: " + type);
        }
        line.setAnchor(lineAnchor);
    }

    public com.example.alldocument.library.fc.hslf.model.Line getBorderLeft()
    {
        return borderLeft;
    }

    public void setBorderLeft(com.example.alldocument.library.fc.hslf.model.Line line)
    {
        if (line != null)
            anchorBorder(com.example.alldocument.library.fc.hslf.model.Table.BORDER_LEFT, line);
        this.borderLeft = line;
    }

    public com.example.alldocument.library.fc.hslf.model.Line getBorderRight()
    {
        return borderRight;
    }

    public void setBorderRight(com.example.alldocument.library.fc.hslf.model.Line line)
    {
        if (line != null)
            anchorBorder(com.example.alldocument.library.fc.hslf.model.Table.BORDER_RIGHT, line);
        this.borderRight = line;
    }

    public com.example.alldocument.library.fc.hslf.model.Line getBorderTop()
    {
        return borderTop;
    }

    public void setBorderTop(com.example.alldocument.library.fc.hslf.model.Line line)
    {
        if (line != null)
            anchorBorder(com.example.alldocument.library.fc.hslf.model.Table.BORDER_TOP, line);
        this.borderTop = line;
    }

    public com.example.alldocument.library.fc.hslf.model.Line getBorderBottom()
    {
        return borderBottom;
    }

    public void setBorderBottom(Line line)
    {
        if (line != null)
            anchorBorder(com.example.alldocument.library.fc.hslf.model.Table.BORDER_BOTTOM, line);
        this.borderBottom = line;
    }

    public void setAnchor(Rectangle anchor)
    {
        super.setAnchor(anchor);

        if (borderTop != null)
            anchorBorder(com.example.alldocument.library.fc.hslf.model.Table.BORDER_TOP, borderTop);
        if (borderRight != null)
            anchorBorder(com.example.alldocument.library.fc.hslf.model.Table.BORDER_RIGHT, borderRight);
        if (borderBottom != null)
            anchorBorder(com.example.alldocument.library.fc.hslf.model.Table.BORDER_BOTTOM, borderBottom);
        if (borderLeft != null)
            anchorBorder(Table.BORDER_LEFT, borderLeft);
    }
}
