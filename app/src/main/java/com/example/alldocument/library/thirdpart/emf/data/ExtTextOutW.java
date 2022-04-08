// Copyright 2002-2007, FreeHEP.
package com.example.alldocument.library.thirdpart.emf.data;

import java.io.IOException;

import com.example.alldocument.library.java.awt.Rectangle;
import com.example.alldocument.library.thirdpart.emf.EMFConstants;
import com.example.alldocument.library.thirdpart.emf.EMFInputStream;
import com.example.alldocument.library.thirdpart.emf.EMFTag;
import com.example.alldocument.library.thirdpart.emf.data.AbstractExtTextOut;
import com.example.alldocument.library.thirdpart.emf.data.Text;
import com.example.alldocument.library.thirdpart.emf.data.TextW;

/**
 * ExtTextOutW TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ExtTextOutW.java 10525 2007-02-12 04:08:30Z duns $
 */
public class ExtTextOutW extends AbstractExtTextOut implements EMFConstants {

    private com.example.alldocument.library.thirdpart.emf.data.TextW text;

    public ExtTextOutW() {
        super(84, 1, null, 0, 1, 1);
    }

    public ExtTextOutW(
        Rectangle bounds,
        int mode,
        float xScale,
        float yScale,
        com.example.alldocument.library.thirdpart.emf.data.TextW text) {

        super(84, 1, bounds, mode, xScale, yScale);
        this.text = text;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        return new ExtTextOutW(
            emf.readRECTL(),
            emf.readDWORD(),
            emf.readFLOAT(),
            emf.readFLOAT(),
            TextW.read(emf));
    }

    public Text getText() {
        return text;
    }
}
