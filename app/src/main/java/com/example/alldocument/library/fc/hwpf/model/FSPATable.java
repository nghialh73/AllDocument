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

package com.example.alldocument.library.fc.hwpf.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.alldocument.library.fc.hwpf.model.FSPA;
import com.example.alldocument.library.fc.hwpf.model.FSPADocumentPart;
import com.example.alldocument.library.fc.hwpf.model.FileInformationBlock;
import com.example.alldocument.library.fc.hwpf.model.GenericPropertyNode;
import com.example.alldocument.library.fc.hwpf.model.PlexOfCps;
import com.example.alldocument.library.fc.hwpf.model.TextPiece;
import com.example.alldocument.library.fc.util.Internal;


/**
 * This class holds all the FSPA (File Shape Address) structures.
 * 
 * @author Squeeself
 */
@ Internal
public final class FSPATable
{

    private final Map<Integer, GenericPropertyNode> _byStart = new LinkedHashMap<Integer, GenericPropertyNode>();

    public FSPATable(byte[] tableStream, FileInformationBlock fib, FSPADocumentPart part)
    {
        int offset = fib.getFSPAPlcfOffset(part);
        int length = fib.getFSPAPlcfLength(part);

        PlexOfCps plex = new PlexOfCps(tableStream, offset, length, com.example.alldocument.library.fc.hwpf.model.FSPA.getSize());
        for (int i = 0; i < plex.length(); i++)
        {
            GenericPropertyNode property = plex.getProperty(i);
            _byStart.put(Integer.valueOf(property.getStart()), property);
        }
    }

    @ Deprecated
    public FSPATable(byte[] tableStream, int fcPlcspa, int lcbPlcspa, List<TextPiece> tpt)
    {
        // Will be 0 if no drawing objects in document
        if (fcPlcspa == 0)
            return;

        PlexOfCps plex = new PlexOfCps(tableStream, fcPlcspa, lcbPlcspa, com.example.alldocument.library.fc.hwpf.model.FSPA.FSPA_SIZE);
        for (int i = 0; i < plex.length(); i++)
        {
            GenericPropertyNode property = plex.getProperty(i);
            _byStart.put(Integer.valueOf(property.getStart()), property);
        }
    }

    public com.example.alldocument.library.fc.hwpf.model.FSPA getFspaFromCp(int cp)
    {
        GenericPropertyNode propertyNode = _byStart.get(Integer.valueOf(cp));
        if (propertyNode == null)
        {
            return null;
        }
        return new com.example.alldocument.library.fc.hwpf.model.FSPA(propertyNode.getBytes(), 0);
    }

    public com.example.alldocument.library.fc.hwpf.model.FSPA[] getShapes()
    {
        List<com.example.alldocument.library.fc.hwpf.model.FSPA> result = new ArrayList<com.example.alldocument.library.fc.hwpf.model.FSPA>(_byStart.size());
        for (GenericPropertyNode propertyNode : _byStart.values())
        {
            result.add(new com.example.alldocument.library.fc.hwpf.model.FSPA(propertyNode.getBytes(), 0));
        }
        return result.toArray(new com.example.alldocument.library.fc.hwpf.model.FSPA[result.size()]);
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append("[FPSA PLC size=").append(_byStart.size()).append("]\n");

        for (Map.Entry<Integer, GenericPropertyNode> entry : _byStart.entrySet())
        {
            Integer i = entry.getKey();
            buf.append("  ").append(i.toString()).append(" => \t");

            try
            {
                FSPA fspa = getFspaFromCp(i.intValue());
                buf.append(fspa.toString());
            }
            catch(Exception exc)
            {
                buf.append(exc.getMessage());
            }
            buf.append("\n");
        }
        buf.append("[/FSPA PLC]");
        return buf.toString();
    }
}
