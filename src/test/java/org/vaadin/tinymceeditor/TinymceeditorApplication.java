package org.vaadin.tinymceeditor;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Window;

public class TinymceeditorApplication extends Application {

	public static void main(String[] args) throws Exception {
		startInEmbeddedJetty();
	}

	public static Server startInEmbeddedJetty() throws Exception {
		Server server = new Server(8888);
		ServletContextHandler handler = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		handler.setResourceBase("target/testwebapp");
		handler.setInitParameter("widgetset", "org.vaadin.tinymceeditor.widgetset.TinymceeditorWidgetset");
		handler.addServlet(Servlet.class, "/*");
		server.setHandler(handler);
		server.start();
		return server;
	}

	public static class Servlet extends AbstractApplicationServlet {
		@Override
		protected Application getNewApplication(HttpServletRequest request)
				throws ServletException {
			return new TinymceeditorApplication();
		}
		
		@Override
		protected Class<? extends Application> getApplicationClass()
				throws ClassNotFoundException {
			return TinymceeditorApplication.class;
		}
	}

	@Override
	public void init() {
		Window mainWindow = new Window("Tinymceeditor Application",
				new CssLayout());
		setMainWindow(mainWindow);

		mainWindow.addComponent(new Button("Hit server"));

		TinyMCETextField tinyMCETextField = new TinyMCETextField();
		mainWindow.addComponent(tinyMCETextField);

		tinyMCETextField.setValue("Some test content<h1>Vaadin rocks!</h1>");

		tinyMCETextField.addListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				getMainWindow().showNotification(
						"Content now: " + event.getProperty().toString());
			}
		});

		tinyMCETextField = new TinyMCETextField();
		tinyMCETextField.setCaption("Another, custom config");
		tinyMCETextField.setConfig("{theme: 'simple'}");
		mainWindow.addComponent(tinyMCETextField);

		tinyMCETextField.addListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				getMainWindow().showNotification(
						"Content now: " + event.getProperty().toString());
			}
		});

	}

}
