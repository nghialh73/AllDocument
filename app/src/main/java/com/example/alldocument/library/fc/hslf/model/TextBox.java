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

import com.example.alldocument.library.fc.ddf.EscherContainerRecord;
import com.example.alldocument.library.fc.ddf.EscherProperties;
import com.example.alldocument.library.fc.hslf.model.Shape;
import com.example.alldocument.library.fc.hslf.model.ShapeTypes;
import com.example.alldocument.library.fc.hslf.model.TextRun;
import com.example.alldocument.library.fc.hslf.model.TextShape;


/**
 * Represents a TextFrame shape in PowerPoint.
 * <p>
 * Contains the text in a text frame as well as the properties and methods
 * that control alignment and anchoring of the text.
 * </p>
 *
 * @author Yegor Kozlov
 */
public class TextBox extends TextShape
{
    /**
     * Create a TextBox object and initialize it from the supplied Record container.
     *
     * @param escherRecord       <code>EscherSpContainer</code> container which holds information about this shape
     * @param parent    the parent of the shape
     */
    protected TextBox(EscherContainerRecord escherRecord, com.example.alldocument.library.fc.hslf.model.Shape parent)
    {
        super(escherRecord, parent);
    }

    /**
     * Create a new TextBox. This constructor is used when a new shape is created.
     *
     * @param parent    the parent of this Shape. For example, if this text box is a cell
     * in a table then the parent is Table.
     */
    public TextBox(Shape parent)
    {
        super(parent);
    }

    /**
     * Create a new TextBox. This constructor is used when a new shape is created.
     *
     */
    public TextBox()
    {
        this(null);
    }

    /**
     * Create a new TextBox and initialize its internal structures
     *
     * @return the created <code>EscherContainerRecord</code> which holds shape data
     */
    protected EscherContainerRecord createSpContainer(boolean isChild)
    {
        _escherContainer = super.createSpContainer(isChild);

        setShapeType(ShapeTypes.TextBox);

        //set default properties for a TextBox
        setEscherProperty(EscherProperties.FILL__FILLCOLOR, 0x8000004);
        setEscherProperty(EscherProperties.FILL__FILLBACKCOLOR, 0x8000000);
        setEscherProperty(EscherProperties.FILL__NOFILLHITTEST, 0x100000);
        setEscherProperty(EscherProperties.LINESTYLE__COLOR, 0x8000001);
        setEscherProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 0x80000);
        setEscherProperty(EscherProperties.SHADOWSTYLE__COLOR, 0x8000002);

        _txtrun = createTextRun();

        return _escherContainer;
    }

    protected void setDefaultTextProperties(TextRun _txtrun)
    {
        setVerticalAlignment(TextBox.AnchorTop);
        setEscherProperty(EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 0x20002);
    }

}
