package org.vaadin.tinymceeditor;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Window;

public class TinymceeditorApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Tinymceeditor Application", new CssLayout());
		setMainWindow(mainWindow);
		
		mainWindow.addComponent(new Button("Hit server"));
		
		TinyMCETextField tinyMCETextField = new TinyMCETextField();
		mainWindow.addComponent(tinyMCETextField);
		
		tinyMCETextField.setValue("Some test content<h1>Vaadin rocks!</h1>");
		
		tinyMCETextField.addListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				getMainWindow().showNotification("Content now: " + event.getProperty().toString());
			}
		});
		
		
		
		tinyMCETextField = new TinyMCETextField();
		tinyMCETextField.setCaption("Another, custom config");
		tinyMCETextField.setConfig("{theme: 'simple'}");
		mainWindow.addComponent(tinyMCETextField);
		
		tinyMCETextField.addListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				getMainWindow().showNotification("Content now: " + event.getProperty().toString());
			}
		});

		
	}

}
