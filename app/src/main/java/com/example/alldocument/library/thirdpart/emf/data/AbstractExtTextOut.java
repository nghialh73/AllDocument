// Copyright 2007, FreeHEP

package com.example.alldocument.library.thirdpart.emf.data;

import com.example.alldocument.library.java.awt.Rectangle;
import com.example.alldocument.library.thirdpart.emf.EMFConstants;
import com.example.alldocument.library.thirdpart.emf.EMFRenderer;
import com.example.alldocument.library.thirdpart.emf.EMFTag;
import com.example.alldocument.library.thirdpart.emf.data.ExtTextOutA;
import com.example.alldocument.library.thirdpart.emf.data.ExtTextOutW;
import com.example.alldocument.library.thirdpart.emf.data.Text;

/**
 * Abstraction of commonality between the {@link ExtTextOutA} and {@link ExtTextOutW} tags.
 *
 * @author Daniel Noll (daniel@nuix.com)
 * @version $Id: ExtTextOutW.java 10140 2006-12-07 07:50:41Z duns $
 */
public abstract class AbstractExtTextOut extends EMFTag implements EMFConstants
{

    private Rectangle bounds;

    private int mode;

    private float xScale, yScale;

    /**
     * Constructs the tag.
     *
     * @param id id of the element
     * @param version emf version in which this element was first supported
     * @param bounds text boundary
     * @param mode text mode
     * @param xScale horizontal scale factor
     * @param yScale vertical scale factor
     */
    protected AbstractExtTextOut(int id, int version, Rectangle bounds, int mode, float xScale,
        float yScale)
    {

        super(id, version);
        this.bounds = bounds;
        this.mode = mode;
        this.xScale = xScale;
        this.yScale = yScale;
    }

    public abstract com.example.alldocument.library.thirdpart.emf.data.Text getText();

    public String toString()
    {
        return super.toString() + "\n  bounds: " + bounds + "\n  mode: " + mode + "\n  xScale: "
            + xScale + "\n  yScale: " + yScale + "\n" + getText().toString();
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        Text text = getText();
        renderer.drawOrAppendText(text.getString(), text.getPos().x, text.getPos().y);
    }
}
