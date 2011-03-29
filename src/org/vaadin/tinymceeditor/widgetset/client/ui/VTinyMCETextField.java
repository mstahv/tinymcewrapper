package org.vaadin.tinymceeditor.widgetset.client.ui;

import org.vaadin.tinymceeditor.widgetset.client.ui.TinyMCEService.OnChangeListener;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.BrowserInfo;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VTinyMCETextField extends Widget implements Paintable,
		OnChangeListener {

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-tinymcetextfield";

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection client;

	private boolean immediate;

	private boolean inited = false;

	private String oldContent;

	private boolean browserNeedsAgressiveValueUpdate;

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

		/*
		 * On change events in tinymce are buggy (too late in some cases on some
		 * browsers). For these browsers we update value on each event. I have
		 * faced this on Safari and FF4 only, but reports from earlier FF and
		 * Opera exist.
		 */
		browserNeedsAgressiveValueUpdate = BrowserInfo.get().isWebkit()
				|| BrowserInfo.get().isFirefox() || BrowserInfo.get().isOpera();

	}

	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		if (client.updateComponent(this, uidl, true)) {
			// If client.updateComponent returns true there has been no changes
			// and we
			// do not need to update anything.
			return;
		}

		immediate = uidl.getBooleanAttribute("immediate");

		// Save reference to server connection object to be able to send
		// user interaction later
		this.client = client;

		// Save the client side identifier (paintable id) for the widget
		paintableId = uidl.getId();
		getElement().setId(paintableId);

		if (!inited) {
			getElement().setInnerHTML(uidl.getStringVariable("text"));
			String config = uidl.hasAttribute("conf") ? uidl
					.getStringAttribute("conf") : null;
			TinyMCEService.loadEditor(paintableId, this, config);
			inited = true;
		} else {
			TinyMCEService.get(paintableId).setContent(
					uidl.getStringVariable("text"));
		}

	}

	@Override
	public void setVisible(boolean visible) {
		if (inited) {
			// TODO do some magic (extend api for hiding the editor) to support
			// visible/hidden, now hiding setting visible again is not working
			// propertly
			TinyMCEditor tinyMCEditor = TinyMCEService.get(paintableId);
		} else {
			super.setVisible(visible);
		}
	}

	public void onChange() {
		updateVariable();

	}

	private void updateVariable() {
		TinyMCEditor tinyMCEditor = TinyMCEService.get(paintableId);
		if (tinyMCEditor == null)
			return;
		String content = tinyMCEditor.getContent();
		if (content != null && !content.equals(oldContent)) {
			client.updateVariable(paintableId, "text", content, immediate);
		}
		oldContent = content;
	}

	public void onEvent(NativeEvent event) {
		if (browserNeedsAgressiveValueUpdate
				&& ("mouseup".equals(event.getType()) || "keyup".equals(event
						.getType()))) {
			// TinyMCE does not always fire onchange for safari
			updateVariable();
		}
	}

}
