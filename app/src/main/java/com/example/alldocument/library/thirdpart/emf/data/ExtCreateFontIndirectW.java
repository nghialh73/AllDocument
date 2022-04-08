// Copyright 2001, FreeHEP.

package com.example.alldocument.library.thirdpart.emf.data;

import java.io.IOException;

import com.example.alldocument.library.thirdpart.emf.EMFInputStream;
import com.example.alldocument.library.thirdpart.emf.EMFRenderer;
import com.example.alldocument.library.thirdpart.emf.EMFTag;
import com.example.alldocument.library.thirdpart.emf.data.ExtLogFontW;

/**
 * ExtCreateFontIndirectW TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ExtCreateFontIndirectW.java 10367 2007-01-22 19:26:48Z duns $
 */
public class ExtCreateFontIndirectW extends EMFTag
{

    private int index;

    private com.example.alldocument.library.thirdpart.emf.data.ExtLogFontW font;

    public ExtCreateFontIndirectW()
    {
        super(82, 1);
    }

    public ExtCreateFontIndirectW(int index, com.example.alldocument.library.thirdpart.emf.data.ExtLogFontW font)
    {
        this();
        this.index = index;
        this.font = font;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new ExtCreateFontIndirectW(emf.readDWORD(), new ExtLogFontW(emf));
    }

    public String toString()
    {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(index) + "\n"
            + font.toString();
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        renderer.storeGDIObject(index, font);
    }
}
