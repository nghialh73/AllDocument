// Copyright 2002, FreeHEP.

package com.example.alldocument.library.thirdpart.emf.data;

import java.io.IOException;

import com.example.alldocument.library.java.awt.Rectangle;
import com.example.alldocument.library.thirdpart.emf.EMFInputStream;
import com.example.alldocument.library.thirdpart.emf.EMFRenderer;
import com.example.alldocument.library.thirdpart.emf.EMFTag;

/**
 * Rectangle TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: EMFRectangle.java 10367 2007-01-22 19:26:48Z duns $
 */
public class EMFRectangle extends EMFTag
{

    private Rectangle bounds;

    public EMFRectangle()
    {
        super(43, 1);
    }

    public EMFRectangle(Rectangle bounds)
    {
        this();
        this.bounds = bounds;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new EMFRectangle(emf.readRECTL());
    }

    public String toString()
    {
        return super.toString() + "\n  bounds: " + bounds;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        renderer.fillAndDrawOrAppend(bounds);
    }
}
