package org.vaadin.tinymceeditor;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.LegacyComponent;
import com.vaadin.ui.TextField;

/**
 * Server side component for the VTinyMCETextField widget.
 */
@JavaScript("theme://../../tiny_mce/tiny_mce.js")
public class TinyMCETextField extends TextField implements LegacyComponent {

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
