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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler;
import com.example.alldocument.library.fc.hwpf.model.FIBLongHandler;
import com.example.alldocument.library.fc.hwpf.model.FIBShortHandler;
import com.example.alldocument.library.fc.hwpf.model.FSPADocumentPart;
import com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart;
import com.example.alldocument.library.fc.hwpf.model.NoteType;
import com.example.alldocument.library.fc.hwpf.model.SubdocumentType;
import com.example.alldocument.library.fc.hwpf.model.types.FIBAbstractType;
import com.example.alldocument.library.fc.util.Internal;


/**
 * The File Information Block (FIB). Holds pointers
 *  to various bits of the file, and lots of flags which
 *  specify properties of the document.
 *
 * The parent class, {@link FIBAbstractType}, holds the
 *  first 32 bytes, which make up the FibBase.
 * The next part, the fibRgW / FibRgW97, is handled
 *  by {@link com.example.alldocument.library.fc.hwpf.model.FIBShortHandler}.
 * The next part, the fibRgLw / The FibRgLw97, is
 *  handled by the {@link com.example.alldocument.library.fc.hwpf.model.FIBLongHandler}.
 * Finally, the rest of the fields are handled by
 *  the {@link com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler}.
 *
 * @author  andy
 */
@Internal
public final class FileInformationBlock extends FIBAbstractType
  implements Cloneable
{

    com.example.alldocument.library.fc.hwpf.model.FIBLongHandler _longHandler;
    com.example.alldocument.library.fc.hwpf.model.FIBShortHandler _shortHandler;
    com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler _fieldHandler;

    /** Creates a new instance of FileInformationBlock */
    public FileInformationBlock(byte[] mainDocument)
    {
        fillFields(mainDocument, 0);
    }

    public void fillVariableFields( byte[] mainDocument, byte[] tableStream )
    {
        _shortHandler = new com.example.alldocument.library.fc.hwpf.model.FIBShortHandler( mainDocument );
        _longHandler = new com.example.alldocument.library.fc.hwpf.model.FIBLongHandler( mainDocument, com.example.alldocument.library.fc.hwpf.model.FIBShortHandler.START
                + _shortHandler.sizeInBytes() );

        /*
         * Listed fields won't be treat as UnhandledDataStructure. For all other
         * fields FIBFieldHandler will load it content into
         * UnhandledDataStructure and save them on save.
         */
        HashSet<Integer> knownFieldSet = new HashSet<Integer>();
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STSHF ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.CLX ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.DOP ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTECHPX ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTEPAPX ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFSED ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFLST ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLFLFO ) );

        // field info
        for ( com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart part : com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart.values() )
            knownFieldSet.add( Integer.valueOf( part.getFibFieldsField() ) );

        // bookmarks
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKF ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKL ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFBKMK ) );

        // notes
        for ( com.example.alldocument.library.fc.hwpf.model.NoteType noteType : com.example.alldocument.library.fc.hwpf.model.NoteType.values() )
        {
            knownFieldSet.add( Integer.valueOf( noteType
                    .getFibDescriptorsFieldIndex() ) );
            knownFieldSet.add( Integer.valueOf( noteType
                    .getFibTextPositionsFieldIndex() ) );
        }

        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFFFN ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFRMARK ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBSAVEDBY ) );
        knownFieldSet.add( Integer.valueOf( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.MODIFIED ) );

        _fieldHandler = new com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler( mainDocument,
                FIBShortHandler.START + _shortHandler.sizeInBytes()
                        + _longHandler.sizeInBytes(), tableStream,
                knownFieldSet, true );
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder( super.toString() );
        stringBuilder.append( "[FIB2]\n" );
        stringBuilder.append( "\tSubdocuments info:\n" );
        for ( com.example.alldocument.library.fc.hwpf.model.SubdocumentType type : com.example.alldocument.library.fc.hwpf.model.SubdocumentType.values() )
        {
            stringBuilder.append( "\t\t" );
            stringBuilder.append( type );
            stringBuilder.append( " has length of " );
            stringBuilder.append( getSubdocumentTextStreamLength( type ) );
            stringBuilder.append( " char(s)\n" );
        }
        stringBuilder.append( "\tFields PLCF info:\n" );
        for ( com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart part : com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart.values() )
        {
            stringBuilder.append( "\t\t" );
            stringBuilder.append( part );
            stringBuilder.append( ": PLCF starts at " );
            stringBuilder.append( getFieldsPlcfOffset( part ) );
            stringBuilder.append( " and have length of " );
            stringBuilder.append( getFieldsPlcfLength( part ) );
            stringBuilder.append( "\n" );
        }
        stringBuilder.append( "\tNotes PLCF info:\n" );
        for ( com.example.alldocument.library.fc.hwpf.model.NoteType noteType : com.example.alldocument.library.fc.hwpf.model.NoteType.values() )
        {
            stringBuilder.append( "\t\t" );
            stringBuilder.append( noteType );
            stringBuilder.append( ": descriptions starts " );
            stringBuilder.append( getNotesDescriptorsOffset( noteType ) );
            stringBuilder.append( " and have length of " );
            stringBuilder.append( getNotesDescriptorsSize( noteType ) );
            stringBuilder.append( " bytes\n" );
            stringBuilder.append( "\t\t" );
            stringBuilder.append( noteType );
            stringBuilder.append( ": text positions starts " );
            stringBuilder.append( getNotesTextPositionsOffset( noteType ) );
            stringBuilder.append( " and have length of " );
            stringBuilder.append( getNotesTextPositionsSize( noteType ) );
            stringBuilder.append( " bytes\n" );
        }
        try
        {
            stringBuilder.append( "\tJava reflection info:\n" );
            for ( Method method : FileInformationBlock.class.getMethods() )
            {
                if ( !method.getName().startsWith( "get" )
                        || !Modifier.isPublic( method.getModifiers() )
                        || Modifier.isStatic( method.getModifiers() )
                        || method.getParameterTypes().length > 0 )
                    continue;
                stringBuilder.append( "\t\t" );
                stringBuilder.append( method.getName() );
                stringBuilder.append( " => " );
                stringBuilder.append( method.invoke( this ) );
                stringBuilder.append( "\n" );
            }
        }
        catch ( Exception exc )
        {
            stringBuilder.append( "(exc: " + exc.getMessage() + ")" );
        }
        stringBuilder.append( "[/FIB2]\n" );
        return stringBuilder.toString();
    }

    /**
     * 在表流的偏移量（正文文本框所在文字范围）
     * @return
     */
    public int getFcPlcfTxbxBkd()
    {
        return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFTXBXBKD);
    }
    
    /**
     * 在表流的长度（正文文本框所在文字范围）
     * @return
     */
    public int getLcbPlcfTxbxBkd()
    {
        return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFTXBXBKD);
    }
    
    /**
     * 在表流的偏移量（页眉页脚文本框所在文字范围）
     * @return
     */
    public int getFcPlcfTxbxHdrBkd()
    {
        return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFTXBXHDRBKD);
    }
    
    /**
     * 在表流的长度（页眉页脚文本框所在文字范围）
     * @return
     */
    public int getLcbPlcfTxbxHdrBkd()
    {
        return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFTXBXHDRBKD);
    }
    
    public int getFcDop()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.DOP);
    }

    public void setFcDop(int fcDop)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.DOP, fcDop);
    }

    public int getLcbDop()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.DOP);
    }

    public void setLcbDop(int lcbDop)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.DOP, lcbDop);
    }

    public int getFcStshf()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STSHF);
    }

    public int getLcbStshf()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STSHF);
    }

    public void setFcStshf(int fcStshf)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STSHF, fcStshf);
    }

    public void setLcbStshf(int lcbStshf)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STSHF, lcbStshf);
    }

    public int getFcClx()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.CLX);
    }

    public int getLcbClx()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.CLX);
    }

    public void setFcClx(int fcClx)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.CLX, fcClx);
    }

    public void setLcbClx(int lcbClx)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.CLX, lcbClx);
    }

    public int getFcPlcfbteChpx()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTECHPX);
    }

    public int getLcbPlcfbteChpx()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTECHPX);
    }

    public void setFcPlcfbteChpx(int fcPlcfBteChpx)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTECHPX, fcPlcfBteChpx);
    }

    public void setLcbPlcfbteChpx(int lcbPlcfBteChpx)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTECHPX, lcbPlcfBteChpx);
    }

    public int getFcPlcfbtePapx()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTEPAPX);
    }

    public int getLcbPlcfbtePapx()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTEPAPX);
    }

    public void setFcPlcfbtePapx(int fcPlcfBtePapx)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTEPAPX, fcPlcfBtePapx);
    }

    public void setLcbPlcfbtePapx(int lcbPlcfBtePapx)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBTEPAPX, lcbPlcfBtePapx);
    }

    public int getFcPlcfsed()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFSED);
    }

    public int getLcbPlcfsed()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFSED);
    }

    public void setFcPlcfsed(int fcPlcfSed)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFSED, fcPlcfSed);
    }

    public void setLcbPlcfsed(int lcbPlcfSed)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFSED, lcbPlcfSed);
    }

    public int getFcPlcfLst()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFLST);
    }

    public int getLcbPlcfLst()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFLST);
    }

    public void setFcPlcfLst(int fcPlcfLst)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFLST, fcPlcfLst);
    }

    public void setLcbPlcfLst(int lcbPlcfLst)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFLST, lcbPlcfLst);
    }

    public int getFcPlfLfo()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLFLFO);
    }

    public int getLcbPlfLfo()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLFLFO);
    }

    /**
     * @return Offset in table stream of the STTBF that records bookmark names
     *         in the main document
     */
    public int getFcSttbfbkmk()
    {
        return _fieldHandler.getFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFBKMK );
    }

    public void setFcSttbfbkmk( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFBKMK, offset );
    }

    /**
     * @return Count of bytes in Sttbfbkmk
     */
    public int getLcbSttbfbkmk()
    {
        return _fieldHandler.getFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFBKMK );
    }

    public void setLcbSttbfbkmk( int length )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFBKMK, length );
    }

    /**
     * @return Offset in table stream of the PLCF that records the beginning CP
     *         offsets of bookmarks in the main document. See BKF structure
     *         definition.
     */
    public int getFcPlcfbkf()
    {
        return _fieldHandler.getFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKF );
    }

    public void setFcPlcfbkf( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKF, offset );
    }

    /**
     * @return Count of bytes in Plcfbkf
     */
    public int getLcbPlcfbkf()
    {
        return _fieldHandler.getFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKF );
    }

    public void setLcbPlcfbkf( int length )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKF, length );
    }

    /**
     * @return Offset in table stream of the PLCF that records the ending CP
     *         offsets of bookmarks recorded in the main document. No structure
     *         is stored in this PLCF.
     */
    public int getFcPlcfbkl()
    {
        return _fieldHandler.getFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKL );
    }

    public void setFcPlcfbkl( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKL, offset );
    }

    /**
     * @return Count of bytes in Plcfbkl
     */
    public int getLcbPlcfbkl()
    {
        return _fieldHandler.getFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKL );
    }

    public void setLcbPlcfbkl( int length )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFBKL, length );
    }

    public void setFcPlfLfo(int fcPlfLfo)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLFLFO, fcPlfLfo);
    }

    public void setLcbPlfLfo(int lcbPlfLfo)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLFLFO, lcbPlfLfo);
    }

    public int getFcSttbfffn()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFFFN);
    }

    public int getLcbSttbfffn()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFFFN);
    }

    public void setFcSttbfffn(int fcSttbFffn)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFFFN, fcSttbFffn);
    }

    public void setLcbSttbfffn(int lcbSttbFffn)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFFFN, lcbSttbFffn);
    }
    
    public int getFcSttbfRMark()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFRMARK);
    }

    public int getLcbSttbfRMark()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFRMARK);
    }
    
    public void setFcSttbfRMark(int fcSttbfRMark)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFRMARK, fcSttbfRMark);
    }

    public void setLcbSttbfRMark(int lcbSttbfRMark)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBFRMARK, lcbSttbfRMark);
    }

    /**
     * Return the offset to the PlcfHdd, in the table stream,
     * i.e. fcPlcfHdd
     */
    public int getPlcfHddOffset() {
       return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFHDD);
    }
    /**
     * Return the size of the PlcfHdd, in the table stream,
     * i.e. lcbPlcfHdd
     */
    public int getPlcfHddSize() {
    	return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFHDD);
    }
    public void setPlcfHddOffset(int fcPlcfHdd) {
    	_fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFHDD, fcPlcfHdd);
    }
    public void setPlcfHddSize(int lcbPlcfHdd) {
    	_fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFHDD, lcbPlcfHdd);
    }

    public int getFcSttbSavedBy()
    {
        return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBSAVEDBY);
    }

    public int getLcbSttbSavedBy()
    {
        return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBSAVEDBY);
    }

    public void setFcSttbSavedBy(int fcSttbSavedBy)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBSAVEDBY, fcSttbSavedBy);
    }

    public void setLcbSttbSavedBy(int fcSttbSavedBy)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.STTBSAVEDBY, fcSttbSavedBy);
    }

    public int getModifiedLow()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLFLFO);
    }

    public int getModifiedHigh()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLFLFO);
    }

    public void setModifiedLow(int modifiedLow)
    {
      _fieldHandler.setFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLFLFO, modifiedLow);
    }

    public void setModifiedHigh(int modifiedHigh)
    {
      _fieldHandler.setFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLFLFO, modifiedHigh);
    }
    
    /**
     * How many bytes of the main stream contain real data.
     */
    public int getCbMac() {
       return _longHandler.getLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CBMAC);
    }

    /**
     * Updates the count of the number of bytes in the
     * main stream which contain real data
     */
    public void setCbMac(int cbMac) {
       _longHandler.setLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CBMAC, cbMac);
    }

    /**
     * @return length of specified subdocument text stream in characters
     */
    public int getSubdocumentTextStreamLength( com.example.alldocument.library.fc.hwpf.model.SubdocumentType type )
    {
        return _longHandler.getLong( type.getFibLongFieldIndex() );
    }

    public void setSubdocumentTextStreamLength(SubdocumentType type, int length )
    {
        if ( length < 0 )
            throw new IllegalArgumentException(
                    "Subdocument length can't be less than 0 (passed value is "
                            + length + "). " + "If there is no subdocument "
                            + "length must be set to zero." );

        _longHandler.setLong( type.getFibLongFieldIndex(), length );
    }

    /**
     * The count of CPs in the main document
     */
    @Deprecated
    public int getCcpText() {
       return _longHandler.getLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPTEXT);
    }
    /**
     * Updates the count of CPs in the main document
     */
    @Deprecated
    public void setCcpText(int ccpText) {
       _longHandler.setLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPTEXT, ccpText);
    }

    /**
     * The count of CPs in the footnote subdocument
     */
    @Deprecated
    public int getCcpFtn() {
       return _longHandler.getLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPFTN);
    }
    /**
     * Updates the count of CPs in the footnote subdocument
     */
    @Deprecated
    public void setCcpFtn(int ccpFtn) {
       _longHandler.setLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPFTN, ccpFtn);
    }

    /**
     * The count of CPs in the header story subdocument
     */
    @Deprecated
    public int getCcpHdd() {
       return _longHandler.getLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPHDD);
    }
    /**
     * Updates the count of CPs in the header story subdocument
     */
    @Deprecated
    public void setCcpHdd(int ccpHdd) {
       _longHandler.setLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPHDD, ccpHdd);
    }

    /**
     * The count of CPs in the comments (atn) subdocument
     */
    @Deprecated
    public int getCcpAtn() {
       return _longHandler.getLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPATN);
    }

    @Deprecated
    public int getCcpCommentAtn() {
       return getCcpAtn();
    }
    /**
     * Updates the count of CPs in the comments (atn) story subdocument
     */
    @Deprecated
    public void setCcpAtn(int ccpAtn) {
       _longHandler.setLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPATN, ccpAtn);
    }

    /**
     * The count of CPs in the end note subdocument
     */
    @Deprecated
    public int getCcpEdn() {
       return _longHandler.getLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPEDN);
    }
    /**
     * Updates the count of CPs in the end note subdocument
     */
    @Deprecated
    public void setCcpEdn(int ccpEdn) {
       _longHandler.setLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPEDN, ccpEdn);
    }

    /**
     * The count of CPs in the main document textboxes
     */
    @Deprecated
    public int getCcpTxtBx() {
       return _longHandler.getLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPTXBX);
    }
    /**
     * Updates the count of CPs in the main document textboxes
     */
    @Deprecated
    public void setCcpTxtBx(int ccpTxtBx) {
       _longHandler.setLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPTXBX, ccpTxtBx);
    }

    /**
     * The count of CPs in the header textboxes
     */
    @Deprecated
    public int getCcpHdrTxtBx() {
       return _longHandler.getLong(com.example.alldocument.library.fc.hwpf.model.FIBLongHandler.CCPHDRTXBX);
    }
    /**
     * Updates the count of CPs in the header textboxes
     */
    @Deprecated
    public void setCcpHdrTxtBx(int ccpTxtBx) {
       _longHandler.setLong(FIBLongHandler.CCPHDRTXBX, ccpTxtBx);
    }


    public void clearOffsetsSizes()
    {
      _fieldHandler.clearFields();
    }

    public int getFieldsPlcfOffset( com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart part )
    {
        return _fieldHandler.getFieldOffset( part.getFibFieldsField() );
    }

    public int getFieldsPlcfLength( com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart part )
    {
        return _fieldHandler.getFieldSize( part.getFibFieldsField() );
    }

    public void setFieldsPlcfOffset(com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart part, int offset )
    {
        _fieldHandler.setFieldOffset( part.getFibFieldsField(), offset );
    }

    public void setFieldsPlcfLength(FieldsDocumentPart part, int length )
    {
        _fieldHandler.setFieldSize( part.getFibFieldsField(), length );
    }

    @Deprecated
    public int getFcPlcffldAtn()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDATN);
    }

    @Deprecated
    public int getLcbPlcffldAtn()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDATN);
    }

    @Deprecated
    public void setFcPlcffldAtn( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDATN, offset );
    }

    @Deprecated
    public void setLcbPlcffldAtn( int size )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDATN, size );
    }

    @Deprecated
    public int getFcPlcffldEdn()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDEDN);
    }

    @Deprecated
    public int getLcbPlcffldEdn()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDEDN);
    }

    @Deprecated
    public void setFcPlcffldEdn( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDEDN, offset );
    }

    @Deprecated
    public void setLcbPlcffldEdn( int size )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDEDN, size );
    }

    @Deprecated
    public int getFcPlcffldFtn()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDFTN);
    }

    @Deprecated
    public int getLcbPlcffldFtn()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDFTN);
    }

    @Deprecated
    public void setFcPlcffldFtn( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDFTN, offset );
    }

    @Deprecated
    public void setLcbPlcffldFtn( int size )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDFTN, size );
    }

    @Deprecated
    public int getFcPlcffldHdr()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDHDR);
    }

    @Deprecated
    public int getLcbPlcffldHdr()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDHDR);
    }

    @Deprecated
    public void setFcPlcffldHdr( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDHDR, offset );
    }

    @Deprecated
    public void setLcbPlcffldHdr( int size )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDHDR, size );
    }

    @Deprecated
    public int getFcPlcffldHdrtxbx()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDHDRTXBX);
    }

    @Deprecated
    public int getLcbPlcffldHdrtxbx()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDHDRTXBX);
    }

    @Deprecated
    public void setFcPlcffldHdrtxbx( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDHDRTXBX, offset );
    }

    @Deprecated
    public void setLcbPlcffldHdrtxbx( int size )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDHDRTXBX, size );
    }

    @Deprecated
    public int getFcPlcffldMom()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDMOM);
    }

    @Deprecated
    public int getLcbPlcffldMom()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDMOM);
    }

    @Deprecated
    public void setFcPlcffldMom( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDMOM, offset );
    }

    @Deprecated
    public void setLcbPlcffldMom( int size )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDMOM, size );
    }

    @Deprecated
    public int getFcPlcffldTxbx()
    {
      return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDTXBX);
    }

    @Deprecated
    public int getLcbPlcffldTxbx()
    {
      return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDTXBX);
    }

    @Deprecated
    public void setFcPlcffldTxbx( int offset )
    {
        _fieldHandler.setFieldOffset( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDTXBX, offset );
    }

    @Deprecated
    public void setLcbPlcffldTxbx( int size )
    {
        _fieldHandler.setFieldSize( com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCFFLDTXBX, size );
    }


    public int getFSPAPlcfOffset( com.example.alldocument.library.fc.hwpf.model.FSPADocumentPart part )
    {
        return _fieldHandler.getFieldOffset( part.getFibFieldsField() );
    }

    public int getFSPAPlcfLength( com.example.alldocument.library.fc.hwpf.model.FSPADocumentPart part )
    {
        return _fieldHandler.getFieldSize( part.getFibFieldsField() );
    }

    public void setFSPAPlcfOffset(com.example.alldocument.library.fc.hwpf.model.FSPADocumentPart part, int offset )
    {
        _fieldHandler.setFieldOffset( part.getFibFieldsField(), offset );
    }

    public void setFSPAPlcfLength(FSPADocumentPart part, int length )
    {
        _fieldHandler.setFieldSize( part.getFibFieldsField(), length );
    }

    @Deprecated
    public int getFcPlcspaMom()
    {
        return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCSPAMOM);
    }

    @Deprecated
    public int getLcbPlcspaMom()
    {
        return _fieldHandler.getFieldSize(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.PLCSPAMOM);
    }

    public int getFcDggInfo()
    {
        return _fieldHandler.getFieldOffset(com.example.alldocument.library.fc.hwpf.model.FIBFieldHandler.DGGINFO);
    }

    public int getLcbDggInfo()
    {
        return _fieldHandler.getFieldSize(FIBFieldHandler.DGGINFO);
    }

    public int getNotesDescriptorsOffset( com.example.alldocument.library.fc.hwpf.model.NoteType noteType )
    {
        return _fieldHandler.getFieldOffset( noteType
                .getFibDescriptorsFieldIndex() );
    }

    public void setNotesDescriptorsOffset(com.example.alldocument.library.fc.hwpf.model.NoteType noteType, int offset )
    {
        _fieldHandler.setFieldOffset( noteType.getFibDescriptorsFieldIndex(),
                offset );
    }

    public int getNotesDescriptorsSize( com.example.alldocument.library.fc.hwpf.model.NoteType noteType )
    {
        return _fieldHandler.getFieldSize( noteType
                .getFibDescriptorsFieldIndex() );
    }

    public void setNotesDescriptorsSize(com.example.alldocument.library.fc.hwpf.model.NoteType noteType, int offset )
    {
        _fieldHandler.setFieldSize( noteType.getFibDescriptorsFieldIndex(),
                offset );
    }

    public int getNotesTextPositionsOffset( com.example.alldocument.library.fc.hwpf.model.NoteType noteType )
    {
        return _fieldHandler.getFieldOffset( noteType
                .getFibTextPositionsFieldIndex() );
    }

    public void setNotesTextPositionsOffset(com.example.alldocument.library.fc.hwpf.model.NoteType noteType, int offset )
    {
        _fieldHandler.setFieldOffset( noteType.getFibTextPositionsFieldIndex(),
                offset );
    }

    public int getNotesTextPositionsSize( com.example.alldocument.library.fc.hwpf.model.NoteType noteType )
    {
        return _fieldHandler.getFieldSize( noteType
                .getFibTextPositionsFieldIndex() );
    }

    public void setNotesTextPositionsSize(NoteType noteType, int offset )
    {
        _fieldHandler.setFieldSize( noteType.getFibTextPositionsFieldIndex(),
                offset );
    }

    public int getSize()
    {
      return super.getSize() + _shortHandler.sizeInBytes() +
        _longHandler.sizeInBytes() + _fieldHandler.sizeInBytes();
    }
//    public Object clone()
//    {
//      try
//      {
//        return super.clone();
//      }
//      catch (CloneNotSupportedException e)
//      {
//        e.printStackTrace();
//        return null;
//      }
//    }

}
