// Copyright 2001, FreeHEP.

package com.example.alldocument.library.thirdpart.emf.data;

import java.io.IOException;

import com.example.alldocument.library.java.awt.Rectangle;
import com.example.alldocument.library.thirdpart.emf.EMFConstants;
import com.example.alldocument.library.thirdpart.emf.EMFInputStream;
import com.example.alldocument.library.thirdpart.emf.EMFTag;
import com.example.alldocument.library.thirdpart.emf.data.Gradient;
import com.example.alldocument.library.thirdpart.emf.data.GradientRectangle;
import com.example.alldocument.library.thirdpart.emf.data.GradientTriangle;
import com.example.alldocument.library.thirdpart.emf.data.TriVertex;

/**
 * GradientFill TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: GradientFill.java 10367 2007-01-22 19:26:48Z duns $
 */
public class GradientFill extends EMFTag implements EMFConstants
{

    private Rectangle bounds;

    private int mode;

    private com.example.alldocument.library.thirdpart.emf.data.TriVertex[] vertices;

    private com.example.alldocument.library.thirdpart.emf.data.Gradient[] gradients;

    public GradientFill()
    {
        super(118, 1);
    }

    public GradientFill(Rectangle bounds, int mode, com.example.alldocument.library.thirdpart.emf.data.TriVertex[] vertices, com.example.alldocument.library.thirdpart.emf.data.Gradient[] gradients)
    {
        this();
        this.bounds = bounds;
        this.mode = mode;
        this.vertices = vertices;
        this.gradients = gradients;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        Rectangle bounds = emf.readRECTL();
        com.example.alldocument.library.thirdpart.emf.data.TriVertex[] vertices = new com.example.alldocument.library.thirdpart.emf.data.TriVertex[emf.readDWORD()];
        com.example.alldocument.library.thirdpart.emf.data.Gradient[] gradients = new Gradient[emf.readDWORD()];
        int mode = emf.readULONG();

        for (int i = 0; i < vertices.length; i++)
        {
            vertices[i] = new TriVertex(emf);
        }
        for (int i = 0; i < gradients.length; i++)
        {
            if (mode == GRADIENT_FILL_TRIANGLE)
            {
                gradients[i] = new GradientTriangle(emf);
            }
            else
            {
                gradients[i] = new GradientRectangle(emf);
            }
        }
        return new GradientFill(bounds, mode, vertices, gradients);
    }

    public String toString()
    {
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append("\n");
        s.append("  bounds: ");
        s.append(bounds);
        s.append("\n");
        s.append("  mode: ");
        s.append(mode);
        s.append("\n");
        for (int i = 0; i < vertices.length; i++)
        {
            s.append("  vertex[");
            s.append(i);
            s.append("]: ");
            s.append(vertices[i]);
            s.append("\n");
        }
        for (int i = 0; i < gradients.length; i++)
        {
            s.append("  gradient[");
            s.append(i);
            s.append("]: ");
            s.append(gradients[i]);
            s.append("\n");
        }
        return s.toString();
    }
}
