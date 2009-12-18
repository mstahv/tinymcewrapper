package com.vaadin.tinymceeditor;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.TextField;

/**
 * Server side component for the VTinyMCETextField widget.
 */
@com.vaadin.ui.ClientWidget(com.vaadin.tinymceeditor.widgetset.client.ui.VTinyMCETextField.class)
public class TinyMCETextField extends TextField {

	private static final long serialVersionUID = -2109451005591590647L;
	private String config;

	public TinyMCETextField() {
		super();
		setWidth("100%");
		setHeight("280px");
	}

	public void setConfig(String jsConfig) {
		config = jsConfig;
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		if (config != null) {
			target.addAttribute("conf", config);
		}
	}
	
}
