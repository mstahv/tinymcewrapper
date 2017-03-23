package org.vaadin.tinymceeditor;

import org.vaadin.addonhelpers.TServer;

public class UiRunner extends TServer {

	/**
     * Starts and embedded server for the tests
	 */
	public static void main(String[] args) throws Exception {
        new UiRunner().startServer();
	}
}
