package org.vaadin.tinymceeditor;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TinymceeditorApplication extends UI {

	public static void main(String[] args) throws Exception {
		startInEmbeddedJetty();
	}

	public static Server startInEmbeddedJetty() throws Exception {
		Server server = new Server(8888);
		ServletContextHandler handler = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		handler.setResourceBase("target/testwebapp");
		handler.setInitParameter("widgetset", "org.vaadin.tinymceeditor.widgetset.TinymceeditorWidgetset");
		handler.setInitParameter("ui", "org.vaadin.tinymceeditor.TinymceeditorApplication");
		handler.setInitParameter("productionMode", "false");
		handler.addServlet(Servlet.class, "/*");
		server.setHandler(handler);
		server.start();
		return server;
	}

	public static class Servlet extends VaadinServlet {
	}	



	@Override
	protected void init(VaadinRequest request) {
		
		setPollInterval(4000);
		
		VerticalLayout content=new VerticalLayout();
		content.addComponent(new Button("Hit server"));
		TinyMCETextField tinyMCETextField = new TinyMCETextField();
		content.addComponent(tinyMCETextField);

		tinyMCETextField.setValue("Some test content<h1>Vaadin rocks!</h1>");

		tinyMCETextField.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				new Notification("Content now: " + event.getProperty().getValue().toString(),"",Type.HUMANIZED_MESSAGE,true).show(Page.getCurrent());
			}
		});

		tinyMCETextField = new TinyMCETextField();
		tinyMCETextField.setCaption("Another, custom config");
		tinyMCETextField.setConfig("{theme: 'simple'}");
		content.addComponent(tinyMCETextField);

		tinyMCETextField.addValueChangeListener( new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				new Notification("Content now: " + event.getProperty().getValue().toString(),"",Type.HUMANIZED_MESSAGE,true).show(Page.getCurrent());
			}
		});
		
		setContent(content);
		
		
		
	}

}
