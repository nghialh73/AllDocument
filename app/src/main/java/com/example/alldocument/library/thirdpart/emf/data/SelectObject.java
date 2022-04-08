// Copyright 2002, FreeHEP.

package com.example.alldocument.library.thirdpart.emf.data;

import java.io.IOException;

import com.example.alldocument.library.thirdpart.emf.EMFInputStream;
import com.example.alldocument.library.thirdpart.emf.EMFRenderer;
import com.example.alldocument.library.thirdpart.emf.EMFTag;
import com.example.alldocument.library.thirdpart.emf.data.GDIObject;
import com.example.alldocument.library.thirdpart.emf.data.StockObjects;

/**
 * SelectObject TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SelectObject.java 10515 2007-02-06 18:42:34Z duns $
 */
public class SelectObject extends EMFTag
{

    private int index;

    public SelectObject()
    {
        super(37, 1);
    }

    public SelectObject(int index)
    {
        this();
        this.index = index;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new SelectObject(emf.readDWORD());
    }

    public String toString()
    {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(index);
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        GDIObject gdiObject;

        if (index < 0)
        {
            gdiObject = StockObjects.getStockObject(index);
        }
        else
        {
            gdiObject = renderer.getGDIObject(index);
        }

        if (gdiObject != null)
        {
            // render that object
            gdiObject.render(renderer);
        }
        else
        {
        }
    }
}
