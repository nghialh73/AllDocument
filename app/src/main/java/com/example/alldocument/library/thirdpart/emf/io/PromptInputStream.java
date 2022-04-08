// Copyright 2002, FreeHEP.
package com.example.alldocument.library.thirdpart.emf.io;

import com.example.alldocument.library.thirdpart.emf.io.PromptListener;
import com.example.alldocument.library.thirdpart.emf.io.RouteListener;
import com.example.alldocument.library.thirdpart.emf.io.RoutedInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * The PromptInputStream reads from an inputstream until it reads any prompt for
 * which a listener is added. The listener is informed that the prompt is found.
 * The route which contains the prompt is supplied as a parameter to the
 * listener. Returning from the prompt listener without reading the route to its
 * end will allow the main stream to read it. The implementation of this class
 * is based on the RoutedInputStream.
 * 
 * @author Mark Donszelmann
 * @version $Id: src/main/java/org/freehep/util/io/PromptInputStream.java
 *          96b41b903496 2005/11/21 19:50:18 duns $
 */
public class PromptInputStream extends RoutedInputStream {

	/**
	 * Craete a Prompt input stream.
	 * 
	 * @param input
	 *            stream to read
	 */
	public PromptInputStream(InputStream input) {
		super(input);
	}

	/**
	 * Add a prompt listener for given prompt.
	 * 
	 * @param prompt
	 *            prompt to listen for
	 * @param listener
	 *            listener to be informed
	 */
	public void addPromptListener(String prompt, com.example.alldocument.library.thirdpart.emf.io.PromptListener listener) {
		final PromptListener promptListener = listener;
		addRoute(prompt, prompt, new RouteListener() {
			public void routeFound(Route input)
					throws IOException {
				promptListener.promptFound(input);
			}
		});
	}
}
