// Copyright 2002, FreeHEP.
package com.example.alldocument.library.thirdpart.emf.io;

import com.example.alldocument.library.thirdpart.emf.io.RoutedInputStream;

import java.io.IOException;

/**
 * Listener to inform that Prompt of the PromptInputStream has been found.
 * 
 * @author Mark Donszelmann
 * @version $Id: src/main/java/org/freehep/util/io/PromptListener.java
 *          96b41b903496 2005/11/21 19:50:18 duns $
 */
public interface PromptListener {

	/**
	 * Prompt was found, and can now be read.
	 * 
	 * @param route
	 *            stream for reading prompt (and more)
	 * @throws IOException
	 *             if read fails
	 */
	public void promptFound(RoutedInputStream.Route route) throws IOException;
}
