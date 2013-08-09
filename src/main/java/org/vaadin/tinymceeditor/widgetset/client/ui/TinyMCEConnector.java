package org.vaadin.tinymceeditor.widgetset.client.ui;

import org.vaadin.tinymceeditor.TinyMCETextField;
import org.vaadin.tinymceeditor.widgetset.client.ui.TinyMCEService.OnChangeListener;

import com.google.gwt.dom.client.NativeEvent;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.LegacyConnector;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.textfield.AbstractTextFieldState;


@Connect(TinyMCETextField.class) 
public class TinyMCEConnector extends LegacyConnector  implements OnChangeListener{

	private boolean browserNeedsAgressiveValueUpdate;
	private String oldContent;
	private ApplicationConnection client;
	private boolean inited;
	private boolean immediate;
	private String paintableId;

	@Override
	public VTinyMCETextField getWidget(){
		return (VTinyMCETextField) super.getWidget();
	}

	/**
	 * Called whenever an update is received from the server
	 */
	@Override
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		if (client.updateComponent(getWidget(), uidl, true)) {
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
		getWidget().getElement().setId(paintableId);

		if (!inited) {
			//set initial value
			this.getWidget().getElement().setInnerHTML(getState().text);
			//get optional configuration for the TinyMCE editor component
			String config = uidl.hasAttribute("conf") ? uidl.getStringAttribute("conf") : null;
			//load the editor component
			TinyMCEService.loadEditor(paintableId, this, config);
			/*
			 * On change events in tinymce are buggy (too late in some cases on some
			 * browsers). For these browsers we update value on each event. I have
			 * faced this on Safari and FF4 only, but reports from earlier FF and
			 * Opera exist.
			 */
			browserNeedsAgressiveValueUpdate = BrowserInfo.get().isWebkit()
					|| BrowserInfo.get().isFirefox() || BrowserInfo.get().isOpera();
			//mark the editor initialized
			inited = true;
		} else{
			//the editor is initialized, just update the text content
			TinyMCEService.get(paintableId).setContent(getState().text);
		}
	}


	@Override
	public void onChange() {
		updateVariable();

	}

	private void updateVariable() {
		TinyMCEditor tinyMCEditor = TinyMCEService.get(paintableId);
		if (tinyMCEditor == null) return;
		String content = tinyMCEditor.getContent();
		if (content != null && !content.equals(oldContent)) {
			client.updateVariable(paintableId, "text", content, immediate);
		}
		oldContent = content;
	}

	@Override
	public void onEvent(NativeEvent event) {
		// TinyMCE does not always fire onchange for safari, thus hook up the native events
		if (browserNeedsAgressiveValueUpdate
				&& ("mouseup".equals(event.getType()) || "keyup".equals(event
						.getType()))) {
			updateVariable();
		}
	}

	@Override
	public AbstractTextFieldState getState() {
		return (AbstractTextFieldState) super.getState();
	}
}
