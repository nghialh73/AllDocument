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
import com.example.alldocument.library.fc.hslf.record.HeadersFootersAtom;
import com.example.alldocument.library.fc.hslf.record.Record;
import com.example.alldocument.library.fc.hslf.record.RecordContainer;
import com.example.alldocument.library.fc.hslf.record.RecordTypes;
import com.example.alldocument.library.fc.util.LittleEndian;
import com.example.alldocument.library.fc.util.POILogger;


/**
 * A container record that specifies information about the footers on a presentation slide.
 * <p>
 * It contains:<br>
 * <li> 1. {@link com.example.alldocument.library.fc.hslf.record.HeadersFootersAtom}
 * <li> 2. {@link com.example.alldocument.library.fc.hslf.record.CString }, Instance UserDate (0), optional: Stores the user's date.
 *    This is the date that the user wants in the footers, instead of today's date.
 * <li> 3. {@link com.example.alldocument.library.fc.hslf.record.CString }, Instance Header (1), optional: Stores the Header's contents.
 * <li> 4. {@link com.example.alldocument.library.fc.hslf.record.CString }, Instance Footer (2), optional: Stores the Footer's contents.
 * </p>
 *
 * @author Yegor Kozlov
 */
public final class HeadersFootersContainer extends RecordContainer {

    /**
     * "instance" field in the record header indicating that this HeadersFootersContaine
     *  is applied for slides
     */
    public static final short SlideHeadersFootersContainer = 0x3F;
    /**
      * "instance" field in the record header indicating that this HeadersFootersContaine
     *   is applied for notes and handouts
      */
    public static final short NotesHeadersFootersContainer = 0x4F;

    public static final int USERDATEATOM    = 0;
    public static final int HEADERATOM      = 1;
    public static final int FOOTERATOM      = 2;


    protected HeadersFootersContainer(byte[] source, int start, int len) {
        // Grab the header
        _header = new byte[8];
        System.arraycopy(source,start,_header,0,8);

        _children = com.example.alldocument.library.fc.hslf.record.Record.findChildRecords(source,start+8,len-8);
        for(int i=0; i < _children.length; i++){
            if(_children[i] instanceof com.example.alldocument.library.fc.hslf.record.HeadersFootersAtom) hdAtom = (com.example.alldocument.library.fc.hslf.record.HeadersFootersAtom)_children[i];
            else if(_children[i] instanceof com.example.alldocument.library.fc.hslf.record.CString) {
                com.example.alldocument.library.fc.hslf.record.CString cs = (com.example.alldocument.library.fc.hslf.record.CString)_children[i];
                int opts = cs.getOptions() >> 4;
                switch(opts){
                    case USERDATEATOM: csDate = cs; break;
                    case HEADERATOM: csHeader = cs; break;
                    case FOOTERATOM: csFooter = cs; break;
                    default:
                        logger.log(POILogger.WARN, "Unexpected CString.Options in HeadersFootersContainer: " + opts);
                        break;
                }
            } else {
                logger.log(POILogger.WARN, "Unexpected record in HeadersFootersContainer: " + _children[i]);
            }
        }

    }

    public HeadersFootersContainer(short options) {
        _header = new byte[8];
        LittleEndian.putShort(_header, 0, options);
        LittleEndian.putShort(_header, 2, (short)getRecordType());

        hdAtom = new com.example.alldocument.library.fc.hslf.record.HeadersFootersAtom();
        _children = new com.example.alldocument.library.fc.hslf.record.Record[]{
            hdAtom
        };
        csDate = csHeader = csFooter = null;

    }

    /**
     * Return the type, which is <code>{@link com.example.alldocument.library.fc.hslf.record.RecordTypes#HeadersFooters}</code>
     */
    public long getRecordType() {
        return RecordTypes.HeadersFooters.typeID;
    }

    /**
     * Must be either {@link #SlideHeadersFootersContainer} or {@link #NotesHeadersFootersContainer}
     *
     * @return "instance" field in the record header
     */
    public int getOptions(){
        return LittleEndian.getShort(_header, 0);
    }

    /**
     * HeadersFootersAtom stores the basic information of the header and footer structure.
     *
     * @return <code>HeadersFootersAtom</code>
     */
    public com.example.alldocument.library.fc.hslf.record.HeadersFootersAtom getHeadersFootersAtom(){
        return hdAtom;
    }

    /**
     * A {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the user's date.
     * <p>This is the date that the user wants in the footers, instead of today's date.</p>
     *
     * @return A {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the user's date or <code>null</code>
     */
    public com.example.alldocument.library.fc.hslf.record.CString getUserDateAtom(){
        return csDate;
    }

    /**
     * A {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the Header's contents.
     *
     * @return A {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the Header's contents or <code>null</code>
     */
    public com.example.alldocument.library.fc.hslf.record.CString getHeaderAtom(){
        return csHeader;
    }

    /**
     * A {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the Footers's contents.
     *
     * @return A {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the Footers's contents or <code>null</code>
     */
    public com.example.alldocument.library.fc.hslf.record.CString getFooterAtom(){
        return csFooter;
    }

    /**
     * Insert a {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the user's date.
     *
     * @return  the created {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the user's date.
     */
    public com.example.alldocument.library.fc.hslf.record.CString addUserDateAtom(){
        if(csDate != null) return csDate;

        csDate = new com.example.alldocument.library.fc.hslf.record.CString();
        csDate.setOptions(USERDATEATOM << 4);

        addChildAfter(csDate, hdAtom);

        return csDate;
    }

    /**
     * Insert a {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the user's date.
     *
     * @return  the created {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the user's date.
     */
    public com.example.alldocument.library.fc.hslf.record.CString addHeaderAtom(){
        if(csHeader != null) return csHeader;

        csHeader = new com.example.alldocument.library.fc.hslf.record.CString();
        csHeader.setOptions(HEADERATOM << 4);

        com.example.alldocument.library.fc.hslf.record.Record r = hdAtom;
        if(csDate != null) r = hdAtom;
        addChildAfter(csHeader, r);

        return csHeader;
    }

    /**
     * Insert a {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the user's date.
     *
     * @return  the created {@link com.example.alldocument.library.fc.hslf.record.CString} record that stores the user's date.
     */
    public com.example.alldocument.library.fc.hslf.record.CString addFooterAtom(){
        if(csFooter != null) return csFooter;

        csFooter = new com.example.alldocument.library.fc.hslf.record.CString();
        csFooter.setOptions(FOOTERATOM << 4);

        Record r = hdAtom;
        if(csHeader != null) r = csHeader;
        else if(csDate != null) r = csDate;
        addChildAfter(csFooter, r);

        return csFooter;
    }
    
    
    /**
     * 
     */
    public void dispose()
    {
        _header = null;
        if (hdAtom != null)
        {
            hdAtom.dispose();
            hdAtom = null;
        }
        if (csDate != null)
        {
            csDate.dispose();
            csDate = null;
        }
        if (csHeader != null)
        {
            csHeader.dispose();
            csHeader = null;
        }
        if (csFooter != null)
        {
            csFooter.dispose();
            csFooter = null;
        }
    }
    
    
    private byte[] _header;
    private HeadersFootersAtom hdAtom;
    private CString csDate, csHeader, csFooter;
}
