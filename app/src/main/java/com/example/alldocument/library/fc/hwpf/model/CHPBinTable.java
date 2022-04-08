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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.alldocument.library.fc.hwpf.model.CHPFormattedDiskPage;
import com.example.alldocument.library.fc.hwpf.model.CHPX;
import com.example.alldocument.library.fc.hwpf.model.CharIndexTranslator;
import com.example.alldocument.library.fc.hwpf.model.ComplexFileTable;
import com.example.alldocument.library.fc.hwpf.model.GenericPropertyNode;
import com.example.alldocument.library.fc.hwpf.model.PlexOfCps;
import com.example.alldocument.library.fc.hwpf.model.PropertyModifier;
import com.example.alldocument.library.fc.hwpf.model.PropertyNode;
import com.example.alldocument.library.fc.hwpf.model.PropertyNode.StartComparator;
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

/**
 * This class holds all of the character formatting properties.
 *
 * @author Ryan Ackley
 */
@ Internal
public class CHPBinTable
{

    /** List of character properties.*/
    protected ArrayList<com.example.alldocument.library.fc.hwpf.model.CHPX> _textRuns = new ArrayList<com.example.alldocument.library.fc.hwpf.model.CHPX>();

    public CHPBinTable()
    {
    }

    /**
     * Constructor used to read a binTable in from a Word document.
     * 
     * @deprecated Use
     *             {@link #CHPBinTable(byte[],byte[],int,int, TextPieceTable)}
     *             instead
     */
    public CHPBinTable(byte[] documentStream, byte[] tableStream, int offset, int size, int fcMin,
        TextPieceTable tpt)
    {
        this(documentStream, tableStream, offset, size, tpt);
    }

    /**
     * Constructor used to read a binTable in from a Word document.
     */
    public CHPBinTable(byte[] documentStream, byte[] tableStream, int offset, int size,
        CharIndexTranslator translator)
    {
        /*
         * Page 35:
         * 
         * "Associated with each interval is a BTE. A BTE holds a four-byte PN
         * (page number) which identifies the FKP page in the file which
         * contains the formatting information for that interval. A CHPX FKP
         * further partitions an interval into runs of exception text."
         */
        com.example.alldocument.library.fc.hwpf.model.PlexOfCps bte = new com.example.alldocument.library.fc.hwpf.model.PlexOfCps(tableStream, offset, size, 4);

        int length = bte.length();
        for (int x = 0; x < length; x++)
        {
            GenericPropertyNode node = bte.getProperty(x);

            int pageNum = LittleEndian.getInt(node.getBytes());
            int pageOffset = POIFSConstants.SMALLER_BIG_BLOCK_SIZE * pageNum;

            CHPFormattedDiskPage cfkp = new CHPFormattedDiskPage(documentStream, pageOffset,
                translator);

            int fkpSize = cfkp.size();

            for (int y = 0; y < fkpSize; y++)
            {
                final com.example.alldocument.library.fc.hwpf.model.CHPX chpx = cfkp.getCHPX(y);
                if (chpx != null)
                    _textRuns.add(chpx);
            }
        }
    }

    public void rebuild(ComplexFileTable complexFileTable)
    {
        long start = System.currentTimeMillis();

        if (complexFileTable != null)
        {
            SprmBuffer[] sprmBuffers = complexFileTable.getGrpprls();

            // adding CHPX from fast-saved SPRMs
            for (TextPiece textPiece : complexFileTable.getTextPieceTable().getTextPieces())
            {
                PropertyModifier prm = textPiece.getPieceDescriptor().getPrm();
                if (!prm.isComplex())
                    continue;
                int igrpprl = prm.getIgrpprl();

                if (igrpprl < 0 || igrpprl >= sprmBuffers.length)
                {
                    continue;
                }

                boolean hasChp = false;
                SprmBuffer sprmBuffer = sprmBuffers[igrpprl];
                for (SprmIterator iterator = sprmBuffer.iterator(); iterator.hasNext();)
                {
                    SprmOperation sprmOperation = iterator.next();
                    if (sprmOperation.getType() == SprmOperation.TYPE_CHP)
                    {
                        hasChp = true;
                        break;
                    }
                }

                if (hasChp)
                {
                    SprmBuffer newSprmBuffer;
                    try
                    {
                        newSprmBuffer = (SprmBuffer)sprmBuffer.clone();
                    }
                    catch(CloneNotSupportedException e)
                    {
                        // shall not happen
                        throw new Error(e);
                    }

                    com.example.alldocument.library.fc.hwpf.model.CHPX chpx = new com.example.alldocument.library.fc.hwpf.model.CHPX(textPiece.getStart(), textPiece.getEnd(), newSprmBuffer);
                    _textRuns.add(chpx);
                }
            }
        }

        List<com.example.alldocument.library.fc.hwpf.model.CHPX> oldChpxSortedByStartPos = new ArrayList<com.example.alldocument.library.fc.hwpf.model.CHPX>(_textRuns);
        Collections.sort(oldChpxSortedByStartPos, StartComparator.instance);

        final Map<com.example.alldocument.library.fc.hwpf.model.CHPX, Integer> chpxToFileOrder = new IdentityHashMap<com.example.alldocument.library.fc.hwpf.model.CHPX, Integer>();
        {
            int counter = 0;
            for (com.example.alldocument.library.fc.hwpf.model.CHPX chpx : _textRuns)
            {
                chpxToFileOrder.put(chpx, Integer.valueOf(counter++));
            }
        }
        final Comparator<com.example.alldocument.library.fc.hwpf.model.CHPX> chpxFileOrderComparator = new Comparator<com.example.alldocument.library.fc.hwpf.model.CHPX>()
        {
            public int compare(com.example.alldocument.library.fc.hwpf.model.CHPX o1, com.example.alldocument.library.fc.hwpf.model.CHPX o2)
            {
                Integer i1 = chpxToFileOrder.get(o1);
                Integer i2 = chpxToFileOrder.get(o2);
                return i1.compareTo(i2);
            }
        };


        List<Integer> textRunsBoundariesList;
        {
            Set<Integer> textRunsBoundariesSet = new HashSet<Integer>();
            for (com.example.alldocument.library.fc.hwpf.model.CHPX chpx : _textRuns)
            {
                textRunsBoundariesSet.add(Integer.valueOf(chpx.getStart()));
                textRunsBoundariesSet.add(Integer.valueOf(chpx.getEnd()));
            }
            textRunsBoundariesSet.remove(Integer.valueOf(0));
            textRunsBoundariesList = new ArrayList<Integer>(textRunsBoundariesSet);
            Collections.sort(textRunsBoundariesList);
        }

        List<com.example.alldocument.library.fc.hwpf.model.CHPX> newChpxs = new LinkedList<com.example.alldocument.library.fc.hwpf.model.CHPX>();
        int lastTextRunStart = 0;
        for (Integer objBoundary : textRunsBoundariesList)
        {
            final int boundary = objBoundary.intValue();

            final int startInclusive = lastTextRunStart;
            final int endExclusive = boundary;
            lastTextRunStart = endExclusive;

            int startPosition = binarySearch(oldChpxSortedByStartPos, boundary);
            startPosition = Math.abs(startPosition);
            while (startPosition >= oldChpxSortedByStartPos.size())
                startPosition--;
            while (startPosition > 0
                && oldChpxSortedByStartPos.get(startPosition).getStart() >= boundary)
                startPosition--;

            List<com.example.alldocument.library.fc.hwpf.model.CHPX> chpxs = new LinkedList<com.example.alldocument.library.fc.hwpf.model.CHPX>();
            for (int c = startPosition; c < oldChpxSortedByStartPos.size(); c++)
            {
                com.example.alldocument.library.fc.hwpf.model.CHPX chpx = oldChpxSortedByStartPos.get(c);

                if (boundary < chpx.getStart())
                    break;

                int left = Math.max(startInclusive, chpx.getStart());
                int right = Math.min(endExclusive, chpx.getEnd());

                if (left < right)
                {
                    chpxs.add(chpx);
                }
            }

            if (chpxs.size() == 0)
            {
                // create it manually
                com.example.alldocument.library.fc.hwpf.model.CHPX chpx = new com.example.alldocument.library.fc.hwpf.model.CHPX(startInclusive, endExclusive, new SprmBuffer(0));
                newChpxs.add(chpx);
                continue;
            }

            if (chpxs.size() == 1)
            {
                // can we reuse existing?
                com.example.alldocument.library.fc.hwpf.model.CHPX existing = chpxs.get(0);
                if (existing.getStart() == startInclusive && existing.getEnd() == endExclusive)
                {
                    newChpxs.add(existing);
                    continue;
                }
            }

            Collections.sort(chpxs, chpxFileOrderComparator);

            SprmBuffer sprmBuffer = new SprmBuffer(0);
            for (com.example.alldocument.library.fc.hwpf.model.CHPX chpx : chpxs)
            {
                sprmBuffer.append(chpx.getGrpprl(), 0);
            }
            com.example.alldocument.library.fc.hwpf.model.CHPX newChpx = new com.example.alldocument.library.fc.hwpf.model.CHPX(startInclusive, endExclusive, sprmBuffer);
            newChpxs.add(newChpx);

            continue;
        }
        this._textRuns = new ArrayList<com.example.alldocument.library.fc.hwpf.model.CHPX>(newChpxs);


        com.example.alldocument.library.fc.hwpf.model.CHPX previous = null;
        for (Iterator<com.example.alldocument.library.fc.hwpf.model.CHPX> iterator = _textRuns.iterator(); iterator.hasNext();)
        {
            com.example.alldocument.library.fc.hwpf.model.CHPX current = iterator.next();
            if (previous == null)
            {
                previous = current;
                continue;
            }

            if (previous.getEnd() == current.getStart()
                && Arrays.equals(previous.getGrpprl(), current.getGrpprl()))
            {
                previous.setEnd(current.getEnd());
                iterator.remove();
                continue;
            }

            previous = current;
        }
    }

    private static int binarySearch(List<com.example.alldocument.library.fc.hwpf.model.CHPX> chpxs, int startPosition)
    {
        int low = 0;
        int high = chpxs.size() - 1;

        while (low <= high)
        {
            int mid = (low + high) >>> 1;
            com.example.alldocument.library.fc.hwpf.model.CHPX midVal = chpxs.get(mid);
            int midValue = midVal.getStart();

            if (midValue < startPosition)
                low = mid + 1;
            else if (midValue > startPosition)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1); // key not found.
    }

    public void adjustForDelete(int listIndex, int offset, int length)
    {
        int size = _textRuns.size();
        int endMark = offset + length;
        int endIndex = listIndex;

        com.example.alldocument.library.fc.hwpf.model.CHPX chpx = _textRuns.get(endIndex);
        while (chpx.getEnd() < endMark)
        {
            chpx = _textRuns.get(++endIndex);
        }
        if (listIndex == endIndex)
        {
            chpx = _textRuns.get(endIndex);
            chpx.setEnd((chpx.getEnd() - endMark) + offset);
        }
        else
        {
            chpx = _textRuns.get(listIndex);
            chpx.setEnd(offset);
            for (int x = listIndex + 1; x < endIndex; x++)
            {
                chpx = _textRuns.get(x);
                chpx.setStart(offset);
                chpx.setEnd(offset);
            }
            chpx = _textRuns.get(endIndex);
            chpx.setEnd((chpx.getEnd() - endMark) + offset);
        }

        for (int x = endIndex + 1; x < size; x++)
        {
            chpx = _textRuns.get(x);
            chpx.setStart(chpx.getStart() - length);
            chpx.setEnd(chpx.getEnd() - length);
        }
    }

    public void insert(int listIndex, int cpStart, SprmBuffer buf)
    {

        com.example.alldocument.library.fc.hwpf.model.CHPX insertChpx = new com.example.alldocument.library.fc.hwpf.model.CHPX(0, 0, buf);

        // Ensure character offsets are really characters
        insertChpx.setStart(cpStart);
        insertChpx.setEnd(cpStart);

        if (listIndex == _textRuns.size())
        {
            _textRuns.add(insertChpx);
        }
        else
        {
            com.example.alldocument.library.fc.hwpf.model.CHPX chpx = _textRuns.get(listIndex);
            if (chpx.getStart() < cpStart)
            {
                // Copy the properties of the one before to afterwards
                // Will go:
                //  Original, until insert at point
                //  New one
                //  Clone of original, on to the old end
                com.example.alldocument.library.fc.hwpf.model.CHPX clone = new com.example.alldocument.library.fc.hwpf.model.CHPX(0, 0, chpx.getSprmBuf());
                // Again ensure contains character based offsets no matter what
                clone.setStart(cpStart);
                clone.setEnd(chpx.getEnd());

                chpx.setEnd(cpStart);

                _textRuns.add(listIndex + 1, insertChpx);
                _textRuns.add(listIndex + 2, clone);
            }
            else
            {
                _textRuns.add(listIndex, insertChpx);
            }
        }
    }

    public void adjustForInsert(int listIndex, int length)
    {
        int size = _textRuns.size();
        com.example.alldocument.library.fc.hwpf.model.CHPX chpx = _textRuns.get(listIndex);
        chpx.setEnd(chpx.getEnd() + length);

        for (int x = listIndex + 1; x < size; x++)
        {
            chpx = _textRuns.get(x);
            chpx.setStart(chpx.getStart() + length);
            chpx.setEnd(chpx.getEnd() + length);
        }
    }

    public List<com.example.alldocument.library.fc.hwpf.model.CHPX> getTextRuns()
    {
        return _textRuns;
    }

    @ Deprecated
    public void writeTo(HWPFFileSystem sys, int fcMin, CharIndexTranslator translator)
        throws IOException
    {
        HWPFOutputStream docStream = sys.getStream("WordDocument");
        HWPFOutputStream tableStream = sys.getStream("1Table");

        writeTo(docStream, tableStream, fcMin, translator);
    }

    public void writeTo(HWPFOutputStream wordDocumentStream, HWPFOutputStream tableStream,
        int fcMin, CharIndexTranslator translator) throws IOException
    {

        /*
         * Page 35:
         * 
         * "Associated with each interval is a BTE. A BTE holds a four-byte PN
         * (page number) which identifies the FKP page in the file which
         * contains the formatting information for that interval. A CHPX FKP
         * further partitions an interval into runs of exception text."
         */
        com.example.alldocument.library.fc.hwpf.model.PlexOfCps bte = new PlexOfCps(4);

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
        int pageNum = docOffset / POIFSConstants.SMALLER_BIG_BLOCK_SIZE;

        // get the ending fc
        // CHPX lastRun = _textRuns.get(_textRuns.size() - 1);
        // int endingFc = lastRun.getEnd();
        // endingFc += fcMin;
        int endingFc = translator.getByteIndex(_textRuns.get(_textRuns.size() - 1).getEnd());

        ArrayList<com.example.alldocument.library.fc.hwpf.model.CHPX> overflow = _textRuns;
        do
        {
            CHPX startingProp = overflow.get(0);
            // int start = startingProp.getStart() + fcMin;
            int start = translator.getByteIndex(startingProp.getStart());

            CHPFormattedDiskPage cfkp = new CHPFormattedDiskPage();
            cfkp.fill(overflow);

            byte[] bufFkp = cfkp.toByteArray(translator);
            wordDocumentStream.write(bufFkp);
            overflow = cfkp.getOverflow();

            int end = endingFc;
            if (overflow != null)
            {
                // end = overflow.get(0).getStart() + fcMin;
                end = translator.getByteIndex(overflow.get(0).getStart());
            }

            byte[] intHolder = new byte[4];
            LittleEndian.putInt(intHolder, pageNum++);
            bte.addProperty(new GenericPropertyNode(start, end, intHolder));

        }
        while (overflow != null);
        tableStream.write(bte.toByteArray());
    }
}
