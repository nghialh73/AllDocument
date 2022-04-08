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

package com.example.alldocument.library.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

import com.example.alldocument.library.fc.hslf.record.CString;
import com.example.alldocument.library.fc.hslf.record.ExMediaAtom;
import com.example.alldocument.library.fc.hslf.record.Record;
import com.example.alldocument.library.fc.hslf.record.RecordContainer;
import com.example.alldocument.library.fc.hslf.record.RecordTypes;
import com.example.alldocument.library.fc.util.LittleEndian;
import com.example.alldocument.library.fc.util.POILogger;


/**
 * A container record that specifies information about external video data.
 *
 * @author Yegor Kozlov
 */
public final class ExVideoContainer extends RecordContainer
{
    private byte[] _header;

    // Links to our more interesting children
    private com.example.alldocument.library.fc.hslf.record.ExMediaAtom mediaAtom;
    //the UNC or local path to a video file.
    private com.example.alldocument.library.fc.hslf.record.CString pathAtom;

    /**
     * Set things up, and find our more interesting children
     */
    protected ExVideoContainer(byte[] source, int start, int len)
    {
        // Grab the header
        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        // Find our children
        _children = com.example.alldocument.library.fc.hslf.record.Record.findChildRecords(source, start + 8, len - 8);
        findInterestingChildren();
    }

    /**
     * Go through our child records, picking out the ones that are
     *  interesting, and saving those for use by the easy helper
     *  methods.
     */
    private void findInterestingChildren()
    {

        // First child should be the ExMediaAtom
        if (_children[0] instanceof com.example.alldocument.library.fc.hslf.record.ExMediaAtom)
        {
            mediaAtom = (com.example.alldocument.library.fc.hslf.record.ExMediaAtom)_children[0];
        }
        else
        {
            logger.log(POILogger.ERROR, "First child record wasn't a ExMediaAtom, was of type "
                + _children[0].getRecordType());
        }
        if (_children[1] instanceof com.example.alldocument.library.fc.hslf.record.CString)
        {
            pathAtom = (com.example.alldocument.library.fc.hslf.record.CString)_children[1];
        }
        else
        {
            logger.log(POILogger.ERROR, "Second child record wasn't a CString, was of type "
                + _children[1].getRecordType());
        }
    }

    /**
     * Create a new ExVideoContainer, with blank fields
     */
    public ExVideoContainer()
    {
        // Setup our header block
        _header = new byte[8];
        _header[0] = 0x0f; // We are a container record
        LittleEndian.putShort(_header, 2, (short)getRecordType());

        _children = new Record[2];
        _children[0] = mediaAtom = new com.example.alldocument.library.fc.hslf.record.ExMediaAtom();
        _children[1] = pathAtom = new com.example.alldocument.library.fc.hslf.record.CString();
    }

    /**
     * We are of type 4103
     */
    public long getRecordType()
    {
        return RecordTypes.ExVideoContainer.typeID;
    }

    /**
     * Returns the ExMediaAtom of this link
     */
    public ExMediaAtom getExMediaAtom()
    {
        return mediaAtom;
    }

    /**
     * Returns the Path Atom (CString) of this link
     */
    public CString getPathAtom()
    {
        return pathAtom;
    }
    
    /**
     * 
     */
    public void dispose()
    {
        super.dispose();
        _header = null;
        if (pathAtom != null)
        {
            pathAtom.dispose();
            pathAtom = null;
        }
        if (mediaAtom != null)
        {
            mediaAtom.dispose();
            mediaAtom = null;
        }
    }

}
