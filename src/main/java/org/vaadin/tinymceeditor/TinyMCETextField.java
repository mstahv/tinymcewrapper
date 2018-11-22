
package org.vaadin.tinymceeditor;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.TextField;
import org.vaadin.tinymceeditor.widgetset.shared.TinymceState;

/**
 * Server side component for the VTinyMCETextField widget.
 */
@JavaScript("//cdn.tinymce.com/4.6/tinymce.min.js")
public class TinyMCETextField extends TextField {

    private static final long serialVersionUID = -2109451005591590647L;

    public TinyMCETextField() {
        super();
        setWidth("100%");
        setHeight("280px");
    }

    public void setConfig(String jsConfig) {
        getState().conf = jsConfig;
    }

    @Override
    protected TinymceState getState() {
        return (TinymceState) super.getState();
    }


}
