// Copyright 2001, FreeHEP.
package com.example.alldocument.library.thirdpart.emf.io;

import com.example.alldocument.library.thirdpart.emf.io.Action;

import java.io.IOException;

/**
 * Exception for the TaggedInputStream. Signals that the inputstream contains
 * more bytes than the stream has read for this action.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id:
 *          src/main/java/org/freehep/util/io/IncompleteActionException.java
 *          96b41b903496 2005/11/21 19:50:18 duns $
 */
public class IncompleteActionException extends IOException {

	/**
     * 
     */
	private static final long serialVersionUID = -6817511986951461967L;

	private com.example.alldocument.library.thirdpart.emf.io.Action action;

	private byte[] rest;

	/**
	 * Creates an Incomplete Action Exception
	 * 
	 * @param action
	 *            incompleted action
	 * @param rest
	 *            unused bytes
	 */
	public IncompleteActionException(com.example.alldocument.library.thirdpart.emf.io.Action action, byte[] rest) {
		super("Action " + action + " contains " + rest.length + " unread bytes");
		this.action = action;
		this.rest = rest;
	}

	/**
	 * @return action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @return unused bytes
	 */
	public byte[] getBytes() {
		return rest;
	}
}
