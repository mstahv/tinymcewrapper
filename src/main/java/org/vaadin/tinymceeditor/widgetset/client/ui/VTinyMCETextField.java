package org.vaadin.tinymceeditor.widgetset.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.AbstractTextFieldWidget;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VTinyMCETextField extends Widget implements AbstractTextFieldWidget {

    /**
     * Set the CSS class name to allow styling.
     */
    public static final String CLASSNAME = "v-tinymcetextfield";
	private String content = null;
	private TinyMCEConnector tinyMCEConnector;
	private String conf;
	private boolean initialized;

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

    @Override
    public void onAttach() {
    	super.onAttach();
  		if (initialized && getElement().getId() != null && !getElement().getId().isEmpty()) {
  			TinyMCEService.loadEditor(getElement().getId(), tinyMCEConnector, conf);
  		}
    }

    @Override
    public void onDetach() {
    	if (getElement().getId() != null && !getElement().getId().isEmpty()) {
    		remove();
    	}
    	super.onDetach();
    }

    private static native void remove()
    /*-{
	    $wnd.tinymce.remove();
	}-*/;

    @Override
    public void setSelectionRange(int start, int length) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValue() {
    	content = TinyMCEService.get(getElement().getId()).getContent();
        return content;
    }

    @Override
    public void selectAll() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setReadOnly(boolean readOnly) {
//    	if (getElement().getId() != null && !getElement().getId().isEmpty()) {
//    		TinyMCEService.get(getElement().getId()).setContentEditable(!readOnly);
//    	}
    }

    @Override
    public int getCursorPos() {
        return 0;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setMaxLength(int maxlenght) {
        // not supported
    }

    public void setPlaceholder(String placeHolder) {
        // not supported
    }

    public void setText(String text) {
        //TinyMCEService.get(getElement().getId()).setContent(text);
    }

	public void setListener(TinyMCEConnector tinyMCEConnector) {
		this.tinyMCEConnector = tinyMCEConnector;		
	}

	public void setConf(String conf) {
		this.conf = conf;		
	}

	public void setInitialized() {
		initialized = true;
		
	}

}
