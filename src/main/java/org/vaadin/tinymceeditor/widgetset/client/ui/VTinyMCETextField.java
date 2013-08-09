package org.vaadin.tinymceeditor.widgetset.client.ui;


import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;



/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VTinyMCETextField extends Widget{

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-tinymcetextfield";


	
	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VTinyMCETextField() {
		// TODO This example code is extending the GWT Widget class so it must
		// set a root element.
		// Change to a proper element or remove this line if extending another
		// widget.
		setElement(Document.get().createDivElement());

		// This method call of the Paintable interface sets the component
		// style name in DOM tree
		setStyleName(CLASSNAME);



	}
	

}
