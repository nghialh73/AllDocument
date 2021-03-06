// Copyright 2007, FreeHEP.
package com.example.alldocument.library.thirdpart.emf.data;

import com.example.alldocument.library.java.awt.Rectangle;
import com.example.alldocument.library.thirdpart.emf.EMFRenderer;
import com.example.alldocument.library.thirdpart.emf.data.AbstractPolyPolygon;

import android.graphics.Point;

/**
 * Parent class for a group of PolyLines. Childs are
 * rendered as not closed polygons.
 *
 * @author Steffen Greiffenberg
 * @version $Id$? */
public abstract class AbstractPolyPolyline extends AbstractPolyPolygon {

    protected AbstractPolyPolyline(
        int id,
        int version,
        Rectangle bounds,
        int[] numberOfPoints,
        Point[][] points) {

        super(id, version, bounds, numberOfPoints, points);
    }

    /**
     * displays the tag using the renderer. The default behavior
     * is not to close the polygons and not to fill them.
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        render(renderer, false);
    }
}
