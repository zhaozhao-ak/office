// Copyright 2002, FreeHEP.

package com.wxiwei.office.thirdpart.emf.data;

import java.io.IOException;

import com.wxiwei.office.thirdpart.emf.EMFInputStream;
import com.wxiwei.office.thirdpart.emf.EMFTag;

/**
 * ResizePalette TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ResizePalette.java 10367 2007-01-22 19:26:48Z duns $
 */
public class ResizePalette extends EMFTag
{

    private int index, entries;

    public ResizePalette()
    {
        super(51, 1);
    }

    public ResizePalette(int index, int entries)
    {
        this();
        this.index = index;
        this.entries = entries;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new ResizePalette(emf.readDWORD(), emf.readDWORD());
    }

    public String toString()
    {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(index) + "\n  entries: "
            + entries;
    }
}
