/*
 *  ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package com.example.alldocument.library.fc.hwpf.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart;
import com.example.alldocument.library.fc.hwpf.model.FileInformationBlock;
import com.example.alldocument.library.fc.hwpf.model.GenericPropertyNode;
import com.example.alldocument.library.fc.hwpf.model.PlexOfCps;
import com.example.alldocument.library.fc.hwpf.model.PlexOfField;
import com.example.alldocument.library.fc.hwpf.model.io.HWPFOutputStream;
import com.example.alldocument.library.fc.util.Internal;


/**
 * This class provides access to all the fields Plex.
 * 
 * @author Cedric Bosdonnat <cbosdonnat@novell.com>
 * 
 */
@ Internal
public class FieldsTables
{
    // The size in bytes of the FLD data structure
    private static final int FLD_SIZE = 2;

    /**
     * annotation subdocument
     */
    @ Deprecated
    public static final int PLCFFLDATN = 0;

    /**
     * endnote subdocument
     */
    @ Deprecated
    public static final int PLCFFLDEDN = 1;
    /**
     * footnote subdocument
     */
    @ Deprecated
    public static final int PLCFFLDFTN = 2;
    /**
     * header subdocument
     */
    @ Deprecated
    public static final int PLCFFLDHDR = 3;
    /**
     * header textbox subdoc
     */
    @ Deprecated
    public static final int PLCFFLDHDRTXBX = 4;
    /**
     * main document
     */
    @ Deprecated
    public static final int PLCFFLDMOM = 5;
    /**
     * textbox subdoc
     */
    @ Deprecated
    public static final int PLCFFLDTXBX = 6;

    private static ArrayList<com.example.alldocument.library.fc.hwpf.model.PlexOfField> toArrayList(com.example.alldocument.library.fc.hwpf.model.PlexOfCps plexOfCps)
    {
        if (plexOfCps == null)
            return new ArrayList<com.example.alldocument.library.fc.hwpf.model.PlexOfField>();

        ArrayList<com.example.alldocument.library.fc.hwpf.model.PlexOfField> fields = new ArrayList<com.example.alldocument.library.fc.hwpf.model.PlexOfField>(plexOfCps.length());
        for (int i = 0; i < plexOfCps.length(); i++)
        {
            GenericPropertyNode propNode = plexOfCps.getProperty(i);
            com.example.alldocument.library.fc.hwpf.model.PlexOfField plex = new com.example.alldocument.library.fc.hwpf.model.PlexOfField(propNode);
            fields.add(plex);
        }

        return fields;
    }

    private Map<FieldsDocumentPart, com.example.alldocument.library.fc.hwpf.model.PlexOfCps> _tables;

    public FieldsTables(byte[] tableStream, FileInformationBlock fib)
    {
        _tables = new HashMap<FieldsDocumentPart, com.example.alldocument.library.fc.hwpf.model.PlexOfCps>(FieldsDocumentPart.values().length);

        for (FieldsDocumentPart part : FieldsDocumentPart.values())
        {
            final com.example.alldocument.library.fc.hwpf.model.PlexOfCps plexOfCps = readPLCF(tableStream, fib, part);
            _tables.put(part, plexOfCps);
        }
    }

    public ArrayList<com.example.alldocument.library.fc.hwpf.model.PlexOfField> getFieldsPLCF(FieldsDocumentPart part)
    {
        return toArrayList(_tables.get(part));
    }

    @ Deprecated
    public ArrayList<PlexOfField> getFieldsPLCF(int partIndex)
    {
        return getFieldsPLCF(FieldsDocumentPart.values()[partIndex]);
    }

    private com.example.alldocument.library.fc.hwpf.model.PlexOfCps readPLCF(byte[] tableStream, FileInformationBlock fib,
                                                               FieldsDocumentPart documentPart)
    {
        int start = fib.getFieldsPlcfOffset(documentPart);
        int length = fib.getFieldsPlcfLength(documentPart);

        if (start <= 0 || length <= 0)
            return null;

        return new com.example.alldocument.library.fc.hwpf.model.PlexOfCps(tableStream, start, length, FLD_SIZE);
    }

    private int savePlex(FileInformationBlock fib, FieldsDocumentPart part, com.example.alldocument.library.fc.hwpf.model.PlexOfCps plexOfCps,
        HWPFOutputStream outputStream) throws IOException
    {
        if (plexOfCps == null || plexOfCps.length() == 0)
        {
            fib.setFieldsPlcfOffset(part, outputStream.getOffset());
            fib.setFieldsPlcfLength(part, 0);
            return 0;
        }

        byte[] data = plexOfCps.toByteArray();

        int start = outputStream.getOffset();
        int length = data.length;

        outputStream.write(data);

        fib.setFieldsPlcfOffset(part, start);
        fib.setFieldsPlcfLength(part, length);

        return length;
    }

    public void write(FileInformationBlock fib, HWPFOutputStream tableStream) throws IOException
    {
        for (FieldsDocumentPart part : FieldsDocumentPart.values())
        {
            PlexOfCps plexOfCps = _tables.get(part);
            savePlex(fib, part, plexOfCps, tableStream);
        }
    }

}
