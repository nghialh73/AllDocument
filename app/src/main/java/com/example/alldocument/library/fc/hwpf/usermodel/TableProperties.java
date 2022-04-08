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

import com.example.alldocument.library.fc.hwpf.model.types.TAPAbstractType;
import com.example.alldocument.library.fc.hwpf.usermodel.BorderCode;
import com.example.alldocument.library.fc.hwpf.usermodel.ShadingDescriptor;
import com.example.alldocument.library.fc.hwpf.usermodel.TableAutoformatLookSpecifier;
import com.example.alldocument.library.fc.hwpf.usermodel.TableCellDescriptor;

public final class TableProperties extends TAPAbstractType implements Cloneable
{

    public TableProperties()
    {
        setTlp( new TableAutoformatLookSpecifier() );
        setShdTable( new ShadingDescriptor() );
        setBrcBottom( new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode() );
        setBrcHorizontal( new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode() );
        setBrcLeft( new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode() );
        setBrcRight( new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode() );
        setBrcTop( new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode() );
        setBrcVertical( new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode() );
        setRgbrcInsideDefault_0( new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode() );
        setRgbrcInsideDefault_1( new com.example.alldocument.library.fc.hwpf.usermodel.BorderCode() );
        setRgdxaCenter( new short[0] );
        setRgdxaCenterPrint( new short[0] );
        setRgshd( new ShadingDescriptor[0] );
        setRgtc( new TableCellDescriptor[0] );
    }

    public TableProperties( short columns )
    {
        this();

        setItcMac( columns );
        setRgshd( new ShadingDescriptor[columns] );

        for ( int x = 0; x < columns; x++ )
        {
            getRgshd()[x] = new ShadingDescriptor();
        }

        TableCellDescriptor[] tableCellDescriptors = new TableCellDescriptor[columns];
        for ( int x = 0; x < columns; x++ )
        {
            tableCellDescriptors[x] = new TableCellDescriptor();
        }
        setRgtc( tableCellDescriptors );

        setRgdxaCenter( new short[columns] );
        setRgdxaCenterPrint( new short[columns] );
    }

    public Object clone() throws CloneNotSupportedException
    {
        TableProperties tap = (TableProperties) super.clone();

        tap.setTlp( getTlp().clone() );
        tap.setRgshd( new ShadingDescriptor[getRgshd().length] );
        for ( int x = 0; x < getRgshd().length; x++ )
        {
            tap.getRgshd()[x] = (ShadingDescriptor) getRgshd()[x].clone();
        }

        tap.setBrcBottom( (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) getBrcBottom().clone() );
        tap.setBrcHorizontal( (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) getBrcHorizontal().clone() );
        tap.setBrcLeft( (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) getBrcLeft().clone() );
        tap.setBrcRight( (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) getBrcRight().clone() );
        tap.setBrcTop( (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) getBrcTop().clone() );
        tap.setBrcVertical( (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) getBrcVertical().clone() );

        tap.setShdTable( (ShadingDescriptor) getShdTable().clone() );

        tap.setRgbrcInsideDefault_0( (com.example.alldocument.library.fc.hwpf.usermodel.BorderCode) getRgbrcInsideDefault_0()
                .clone() );
        tap.setRgbrcInsideDefault_1( (BorderCode) getRgbrcInsideDefault_1()
                .clone() );

        tap.setRgdxaCenter( getRgdxaCenter().clone() );
        tap.setRgdxaCenterPrint( getRgdxaCenterPrint().clone() );

        tap.setRgtc( new TableCellDescriptor[getRgtc().length] );
        for ( int x = 0; x < getRgtc().length; x++ )
        {
            tap.getRgtc()[x] = (TableCellDescriptor) getRgtc()[x].clone();
        }
        return tap;
    }

}
