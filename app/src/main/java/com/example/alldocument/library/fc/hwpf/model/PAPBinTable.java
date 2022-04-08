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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.example.alldocument.library.fc.hwpf.model.CharIndexTranslator;
import com.example.alldocument.library.fc.hwpf.model.ComplexFileTable;
import com.example.alldocument.library.fc.hwpf.model.GenericPropertyNode;
import com.example.alldocument.library.fc.hwpf.model.PAPFormattedDiskPage;
import com.example.alldocument.library.fc.hwpf.model.PAPX;
import com.example.alldocument.library.fc.hwpf.model.PlexOfCps;
import com.example.alldocument.library.fc.hwpf.model.PropertyModifier;
import com.example.alldocument.library.fc.hwpf.model.PropertyNode;
import com.example.alldocument.library.fc.hwpf.model.TextPiece;
import com.example.alldocument.library.fc.hwpf.model.TextPieceTable;
import com.example.alldocument.library.fc.hwpf.model.io.HWPFFileSystem;
import com.example.alldocument.library.fc.hwpf.model.io.HWPFOutputStream;
import com.example.alldocument.library.fc.hwpf.sprm.SprmBuffer;
import com.example.alldocument.library.fc.hwpf.sprm.SprmIterator;
import com.example.alldocument.library.fc.hwpf.sprm.SprmOperation;
import com.example.alldocument.library.fc.poifs.common.POIFSConstants;
import com.example.alldocument.library.fc.util.Internal;
import com.example.alldocument.library.fc.util.LittleEndian;
import com.example.alldocument.library.fc.util.POILogFactory;
import com.example.alldocument.library.fc.util.POILogger;


/**
 * This class represents the bin table of Word document but it also serves as a
 * holder for all of the paragraphs of document that have been loaded into
 * memory.
 *
 * @author Ryan Ackley
 */
@Internal
public class PAPBinTable
{

  protected ArrayList<com.example.alldocument.library.fc.hwpf.model.PAPX> _paragraphs = new ArrayList<com.example.alldocument.library.fc.hwpf.model.PAPX>();

  public PAPBinTable()
  {
  }

    /**
     * @deprecated Use
     *             {@link #PAPBinTable(byte[],byte[],byte[],int,int,int, TextPieceTable,boolean)}
     *             instead
     */
    @SuppressWarnings( "unused" )
    public PAPBinTable( byte[] documentStream, byte[] tableStream,
            byte[] dataStream, int offset, int size, int fcMin,
            TextPieceTable tpt )
    {
        this( documentStream, tableStream, dataStream, offset, size, tpt );
    }

    public PAPBinTable( byte[] documentStream, byte[] tableStream,
            byte[] dataStream, int offset, int size,
            CharIndexTranslator charIndexTranslator )
    {
        long start = System.currentTimeMillis();

        {
            com.example.alldocument.library.fc.hwpf.model.PlexOfCps binTable = new com.example.alldocument.library.fc.hwpf.model.PlexOfCps( tableStream, offset, size, 4 );

            int length = binTable.length();
            for ( int x = 0; x < length; x++ )
            {
                GenericPropertyNode node = binTable.getProperty( x );

                int pageNum = LittleEndian.getInt( node.getBytes() );
                int pageOffset = POIFSConstants.SMALLER_BIG_BLOCK_SIZE
                        * pageNum;

                PAPFormattedDiskPage pfkp = new PAPFormattedDiskPage(
                        documentStream, dataStream, pageOffset,
                        charIndexTranslator );

                int fkpSize = pfkp.size();

                for ( int y = 0; y < fkpSize; y++ )
                {
                    com.example.alldocument.library.fc.hwpf.model.PAPX papx = pfkp.getPAPX( y );

                    if ( papx != null )
                        _paragraphs.add( papx );
                }
            }
        }
    }

    public void rebuild( final StringBuilder docText,
            ComplexFileTable complexFileTable )
    {
        long start = System.currentTimeMillis();

        if ( complexFileTable != null )
        {
            SprmBuffer[] sprmBuffers = complexFileTable.getGrpprls();

            // adding PAPX from fast-saved SPRMs
            for ( TextPiece textPiece : complexFileTable.getTextPieceTable()
                    .getTextPieces() )
            {
                PropertyModifier prm = textPiece.getPieceDescriptor().getPrm();
                if ( !prm.isComplex() )
                    continue;
                int igrpprl = prm.getIgrpprl();

                if ( igrpprl < 0 || igrpprl >= sprmBuffers.length )
                {
                    continue;
                }

                boolean hasPap = false;
                SprmBuffer sprmBuffer = sprmBuffers[igrpprl];
                for ( SprmIterator iterator = sprmBuffer.iterator(); iterator
                        .hasNext(); )
                {
                    SprmOperation sprmOperation = iterator.next();
                    if ( sprmOperation.getType() == SprmOperation.TYPE_PAP )
                    {
                        hasPap = true;
                        break;
                    }
                }

                if ( hasPap )
                {
                    SprmBuffer newSprmBuffer = new SprmBuffer( 2 );
                    newSprmBuffer.append( sprmBuffer.toByteArray() );

                    com.example.alldocument.library.fc.hwpf.model.PAPX papx = new com.example.alldocument.library.fc.hwpf.model.PAPX( textPiece.getStart(),
                            textPiece.getEnd(), newSprmBuffer );
                    _paragraphs.add( papx );
                }
            }
        }

        List<com.example.alldocument.library.fc.hwpf.model.PAPX> oldPapxSortedByEndPos = new ArrayList<com.example.alldocument.library.fc.hwpf.model.PAPX>( _paragraphs );
        Collections.sort( oldPapxSortedByEndPos,
                PropertyNode.EndComparator.instance );
        
        start = System.currentTimeMillis();

        final Map<com.example.alldocument.library.fc.hwpf.model.PAPX, Integer> papxToFileOrder = new IdentityHashMap<com.example.alldocument.library.fc.hwpf.model.PAPX, Integer>();
        {
            int counter = 0;
            for ( com.example.alldocument.library.fc.hwpf.model.PAPX papx : _paragraphs )
            {
                papxToFileOrder.put( papx, Integer.valueOf( counter++ ) );
            }
        }
        final Comparator<com.example.alldocument.library.fc.hwpf.model.PAPX> papxFileOrderComparator = new Comparator<com.example.alldocument.library.fc.hwpf.model.PAPX>()
        {
            public int compare(com.example.alldocument.library.fc.hwpf.model.PAPX o1, com.example.alldocument.library.fc.hwpf.model.PAPX o2 )
            {
                Integer i1 = papxToFileOrder.get( o1 );
                Integer i2 = papxToFileOrder.get( o2 );
                return i1.compareTo( i2 );
            }
        };
        start = System.currentTimeMillis();

        List<com.example.alldocument.library.fc.hwpf.model.PAPX> newPapxs = new LinkedList<com.example.alldocument.library.fc.hwpf.model.PAPX>();
        int lastParStart = 0;
        int lastPapxIndex = 0;
        for ( int charIndex = 0; charIndex < docText.length(); charIndex++ )
        {
            final char c = docText.charAt( charIndex );
            if ( c != 13 && c != 7 && c != 12 )
                continue;

            final int startInclusive = lastParStart;
            final int endExclusive = charIndex + 1;

            boolean broken = false;
            List<com.example.alldocument.library.fc.hwpf.model.PAPX> papxs = new LinkedList<com.example.alldocument.library.fc.hwpf.model.PAPX>();
            for ( int papxIndex = lastPapxIndex; papxIndex < oldPapxSortedByEndPos
                    .size(); papxIndex++ )
            {
                broken = false;
                com.example.alldocument.library.fc.hwpf.model.PAPX papx = oldPapxSortedByEndPos.get( papxIndex );

                assert startInclusive == 0
                        || papxIndex + 1 == oldPapxSortedByEndPos.size()
                        || papx.getEnd() > startInclusive;

                if ( papx.getEnd() - 1 > charIndex )
                {
                    lastPapxIndex = papxIndex;
                    broken = true;
                    break;
                }

                papxs.add( papx );
            }
            if ( !broken )
            {
                lastPapxIndex = oldPapxSortedByEndPos.size() - 1;
            }

            if ( papxs.size() == 0 )
            {
                // create it manually
                com.example.alldocument.library.fc.hwpf.model.PAPX papx = new com.example.alldocument.library.fc.hwpf.model.PAPX( startInclusive, endExclusive,
                        new SprmBuffer( 2 ) );
                newPapxs.add( papx );

                lastParStart = endExclusive;
                continue;
            }

            if ( papxs.size() == 1 )
            {
                // can we reuse existing?
                com.example.alldocument.library.fc.hwpf.model.PAPX existing = papxs.get( 0 );
                if ( existing.getStart() == startInclusive
                        && existing.getEnd() == endExclusive )
                {
                    newPapxs.add( existing );
                    lastParStart = endExclusive;
                    continue;
                }
            }

            // restore file order of PAPX
            Collections.sort( papxs, papxFileOrderComparator );

            SprmBuffer sprmBuffer = null;
            for ( com.example.alldocument.library.fc.hwpf.model.PAPX papx : papxs )
            {
                if ( sprmBuffer == null )
                    try
                    {
                        sprmBuffer = (SprmBuffer) papx.getSprmBuf().clone();
                    }
                    catch ( CloneNotSupportedException e )
                    {
                        // can't happen
                        throw new Error( e );
                    }
                else
                    sprmBuffer.append( papx.getGrpprl(), 2 );
            }
            com.example.alldocument.library.fc.hwpf.model.PAPX newPapx = new com.example.alldocument.library.fc.hwpf.model.PAPX( startInclusive, endExclusive, sprmBuffer );
            newPapxs.add( newPapx );

            lastParStart = endExclusive;
            continue;
        }
        this._paragraphs = new ArrayList<com.example.alldocument.library.fc.hwpf.model.PAPX>( newPapxs );
        
        start = System.currentTimeMillis();
    }

  public void insert(int listIndex, int cpStart, SprmBuffer buf)
  {

    com.example.alldocument.library.fc.hwpf.model.PAPX forInsert = new com.example.alldocument.library.fc.hwpf.model.PAPX(0, 0, buf);

    // Ensure character offsets are really characters
    forInsert.setStart(cpStart);
    forInsert.setEnd(cpStart);

    if (listIndex == _paragraphs.size())
    {
       _paragraphs.add(forInsert);
    }
    else
    {
      com.example.alldocument.library.fc.hwpf.model.PAPX currentPap = _paragraphs.get(listIndex);
      if (currentPap != null && currentPap.getStart() < cpStart)
      {
        SprmBuffer clonedBuf = null;
        try
        {
          clonedBuf = (SprmBuffer)currentPap.getSprmBuf().clone();
        }
        catch (CloneNotSupportedException exc)
        {
          exc.printStackTrace();
        }

    	// Copy the properties of the one before to afterwards
    	// Will go:
    	//  Original, until insert at point
    	//  New one
    	//  Clone of original, on to the old end
        com.example.alldocument.library.fc.hwpf.model.PAPX clone = new com.example.alldocument.library.fc.hwpf.model.PAPX(0, 0, clonedBuf);
        // Again ensure contains character based offsets no matter what
        clone.setStart(cpStart);
        clone.setEnd(currentPap.getEnd());

        currentPap.setEnd(cpStart);

        _paragraphs.add(listIndex + 1, forInsert);
        _paragraphs.add(listIndex + 2, clone);
      }
      else
      {
        _paragraphs.add(listIndex, forInsert);
      }
    }

  }

  public void adjustForDelete(int listIndex, int offset, int length)
  {
    int size = _paragraphs.size();
    int endMark = offset + length;
    int endIndex = listIndex;

    com.example.alldocument.library.fc.hwpf.model.PAPX papx = _paragraphs.get(endIndex);
    while (papx.getEnd() < endMark)
    {
      papx = _paragraphs.get(++endIndex);
    }
    if (listIndex == endIndex)
    {
      papx = _paragraphs.get(endIndex);
      papx.setEnd((papx.getEnd() - endMark) + offset);
    }
    else
    {
      papx = _paragraphs.get(listIndex);
      papx.setEnd(offset);
      for (int x = listIndex + 1; x < endIndex; x++)
      {
        papx = _paragraphs.get(x);
        papx.setStart(offset);
        papx.setEnd(offset);
      }
      papx = _paragraphs.get(endIndex);
      papx.setEnd((papx.getEnd() - endMark) + offset);
    }

    for (int x = endIndex + 1; x < size; x++)
    {
      papx = _paragraphs.get(x);
      papx.setStart(papx.getStart() - length);
      papx.setEnd(papx.getEnd() - length);
    }
  }


  public void adjustForInsert(int listIndex, int length)
  {
    int size = _paragraphs.size();
    com.example.alldocument.library.fc.hwpf.model.PAPX papx = _paragraphs.get(listIndex);
    papx.setEnd(papx.getEnd() + length);

    for (int x = listIndex + 1; x < size; x++)
    {
      papx = _paragraphs.get(x);
      papx.setStart(papx.getStart() + length);
      papx.setEnd(papx.getEnd() + length);
    }
  }


  public ArrayList<com.example.alldocument.library.fc.hwpf.model.PAPX> getParagraphs()
  {
    return _paragraphs;
  }

    @Deprecated
    public void writeTo( HWPFFileSystem sys, CharIndexTranslator translator )
            throws IOException
    {
        HWPFOutputStream wordDocumentStream = sys.getStream( "WordDocument" );
        HWPFOutputStream tableStream = sys.getStream( "1Table" );

        writeTo( wordDocumentStream, tableStream, translator );
    }

    public void writeTo( HWPFOutputStream wordDocumentStream,
            HWPFOutputStream tableStream, CharIndexTranslator translator )
            throws IOException
    {

    com.example.alldocument.library.fc.hwpf.model.PlexOfCps binTable = new PlexOfCps(4);

    // each FKP must start on a 512 byte page.
    int docOffset = wordDocumentStream.getOffset();
    int mod = docOffset % POIFSConstants.SMALLER_BIG_BLOCK_SIZE;
    if (mod != 0)
    {
      byte[] padding = new byte[POIFSConstants.SMALLER_BIG_BLOCK_SIZE - mod];
      wordDocumentStream.write(padding);
    }

    // get the page number for the first fkp
    docOffset = wordDocumentStream.getOffset();
    int pageNum = docOffset/POIFSConstants.SMALLER_BIG_BLOCK_SIZE;

        // get the ending fc
        // int endingFc = _paragraphs.get(_paragraphs.size() - 1).getEnd();
        // endingFc += fcMin;
        int endingFc = translator.getByteIndex( _paragraphs.get(
                _paragraphs.size() - 1 ).getEnd() );

    ArrayList<com.example.alldocument.library.fc.hwpf.model.PAPX> overflow = _paragraphs;
    do
    {
      PAPX startingProp = overflow.get(0);

            // int start = startingProp.getStart() + fcMin;
            int start = translator.getByteIndex( startingProp.getStart() );

      PAPFormattedDiskPage pfkp = new PAPFormattedDiskPage();
      pfkp.fill(overflow);

      byte[] bufFkp = pfkp.toByteArray(tableStream, translator);
      wordDocumentStream.write(bufFkp);
      overflow = pfkp.getOverflow();

      int end = endingFc;
      if (overflow != null)
      {
                // end = overflow.get(0).getStart() + fcMin;
                end = translator.getByteIndex( overflow.get( 0 ).getStart() );
      }

      byte[] intHolder = new byte[4];
      LittleEndian.putInt(intHolder, pageNum++);
      binTable.addProperty(new GenericPropertyNode(start, end, intHolder));

    }
    while (overflow != null);
    tableStream.write(binTable.toByteArray());
  }
}
