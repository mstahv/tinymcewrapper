package org.vaadin.tinymceeditor;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@PreserveOnRefresh
public class TinymceeditorApplication extends UI {

    private TinyMCETextField tinyMCETextField;
    private TinyMCETextField tinyMCETextField2;

    @Override
    protected void init(VaadinRequest request) {

        setPollInterval(4000);

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        
        VerticalLayout content1 = new VerticalLayout();
        content1.setSpacing(true);
        content1.setMargin(true);
        content1.setSizeFull();
        
        content1.addComponent(new Button("Show Html in editor 1", new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Notification notification = new Notification("Content in editor 1");
                notification.setHtmlContentAllowed(false);
                notification.setDescription(tinyMCETextField.getValue());
                notification.show(Page.getCurrent());
            }
        }));
        tinyMCETextField = new TinyMCETextField();
        content1.addComponent(tinyMCETextField);

        tinyMCETextField.setValue("Some test content<h1>Vaadin rocks!</h1>");

        tinyMCETextField.addValueChangeListener(event -> {
            new Notification("Content now: " + event.getValue(), "", Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
        }
        );
        
        VerticalLayout content2 = new VerticalLayout();
        content2.setSpacing(true);
        content2.setMargin(true);
        content2.setSizeFull();
        content2.addComponent(new Button("Show Html in editor 2", new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                Notification notification = new Notification("Content in editor 2");
                notification.setHtmlContentAllowed(false);
                notification.setDescription(tinyMCETextField2.getValue());
                notification.show(Page.getCurrent());
            }
        }));

        tinyMCETextField2 = new TinyMCETextField();
        tinyMCETextField2.setCaption("Another, custom config");
        tinyMCETextField2.setConfig("{"
                + "menubar: false,"
                + "plugins: [ 'advlist autolink lists link image charmap print preview anchor','searchreplace visualblocks code fullscreen','insertdatetime media table contextmenu paste' ], "
                + "toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image'}");
        content2.addComponent(tinyMCETextField2);

        tinyMCETextField2.addValueChangeListener(event -> {
            new Notification("Content now: " + event.getValue(), "", Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
        });

        tabSheet.addTab(content1,"Editor 1");
        tabSheet.addTab(content2,"Editor 2");
        
        setContent(tabSheet);

    }

}
