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

package com.example.alldocument.library.fc.hwpf.usermodel;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import com.example.alldocument.library.fc.hwpf.model.types.SEPAbstractType;
import com.example.alldocument.library.fc.hwpf.usermodel.BorderCode;
import com.example.alldocument.library.fc.hwpf.usermodel.DateAndTime;


public final class SectionProperties extends SEPAbstractType
{
    public SectionProperties()
    {
        field_20_brcTop = new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode();
        field_21_brcLeft = new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode();
        field_22_brcBottom = new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode();
        field_23_brcRight = new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode();
        field_26_dttmPropRMark = new com.example.alldocument.library.fc.hwpf.usermodel.DateAndTime();
    }

    public Object clone() throws CloneNotSupportedException
    {
        SectionProperties copy = (SectionProperties) super.clone();
        copy.field_20_brcTop = (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) field_20_brcTop.clone();
        copy.field_21_brcLeft = (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) field_21_brcLeft.clone();
        copy.field_22_brcBottom = (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) field_22_brcBottom.clone();
        copy.field_23_brcRight = (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) field_23_brcRight.clone();
        copy.field_26_dttmPropRMark = (DateAndTime) field_26_dttmPropRMark
                .clone();

        return copy;
    }
    
    /**
     */
    public com.example.alldocument.library.fc.hwpf.usermodel.BorderCode getTopBorder()
    {
        return field_20_brcTop;
    }
    
    /**
     */
    public com.example.alldocument.library.fc.hwpf.usermodel.BorderCode getBottomBorder()
    {
        return field_22_brcBottom;
    }
    /**
     */
    public com.example.alldocument.library.fc.hwpf.usermodel.BorderCode getLeftBorder()
    {
        return field_21_brcLeft;
    }
    /**
     */
    public BorderCode getRightBorder()
    {
        return field_23_brcRight;
    }
    
    /**
     *
     */
    public int getSectionBorder()
    {
        return getPgbProp();
    }

    public boolean equals( Object obj )
    {
        Field[] fields = SectionProperties.class.getSuperclass()
                .getDeclaredFields();
        AccessibleObject.setAccessible( fields, true );
        try
        {
            for ( int x = 0; x < fields.length; x++ )
            {
                Object obj1 = fields[x].get( this );
                Object obj2 = fields[x].get( obj );
                if ( obj1 == null && obj2 == null )
                {
                    continue;
                }
                if ( !obj1.equals( obj2 ) )
                {
                    return false;
                }
            }
            return true;
        }
        catch ( Exception e )
        {
            return false;
        }
    }

}
