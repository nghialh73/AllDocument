// Copyright 2002, FreeHEP.

package com.example.alldocument.library.thirdpart.emf.data;

import android.graphics.Point;

import java.io.IOException;

import com.example.alldocument.library.java.awt.Rectangle;
import com.example.alldocument.library.thirdpart.emf.EMFConstants;
import com.example.alldocument.library.thirdpart.emf.EMFInputStream;
import com.example.alldocument.library.thirdpart.emf.EMFTag;

/**
 * PolyDraw TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: PolyDraw.java 10367 2007-01-22 19:26:48Z duns $
 */
public class PolyDraw extends EMFTag implements EMFConstants
{

    private Rectangle bounds;

    private Point[] points;

    private byte[] types;

    public PolyDraw()
    {
        super(56, 1);
    }

    public PolyDraw(Rectangle bounds, Point[] points, byte[] types)
    {
        this();
        this.bounds = bounds;
        this.points = points;
        this.types = types;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        int n;
        return new PolyDraw(emf.readRECTL(), emf.readPOINTL(n = emf.readDWORD()), emf.readBYTE(n));
    }

    public String toString()
    {
        return super.toString() + "\n  bounds: " + bounds + "\n  #points: " + points.length;
    }
}
