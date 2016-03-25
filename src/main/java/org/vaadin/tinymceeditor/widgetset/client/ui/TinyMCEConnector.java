package org.vaadin.tinymceeditor.widgetset.client.ui;

import org.vaadin.tinymceeditor.TinyMCETextField;
import org.vaadin.tinymceeditor.widgetset.client.ui.TinyMCEService.OnChangeListener;

import com.google.gwt.dom.client.NativeEvent;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.LegacyConnector;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.textfield.AbstractTextFieldState;


@Connect(TinyMCETextField.class) 
public class TinyMCEConnector extends LegacyConnector  implements OnChangeListener{

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
			//mark the editor initialized
			inited = true;
		} else {

			//the editor is initialized, just update the text content
			// Also check if Vaadin decided to post the already known content to client
			// This might cause invalid state in some rare conditions
			boolean shouldSkipUpdate = oldContent != null && getState().text.equals(oldContent);
			if(!shouldSkipUpdate) {
				TinyMCEService.get(paintableId).setContent(getState().text);
			}
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
//		if (browserNeedsAgressiveValueUpdate
//				&& ("mouseup".equals(event.getType()) || "keyup".equals(event
//						.getType()))) {
//			updateVariable();
//		}
	}

	@Override
	public AbstractTextFieldState getState() {
		return (AbstractTextFieldState) super.getState();
	}
}
