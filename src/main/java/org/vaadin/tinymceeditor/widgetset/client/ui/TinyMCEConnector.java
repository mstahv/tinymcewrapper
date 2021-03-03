package org.vaadin.tinymceeditor.widgetset.client.ui;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.ScriptInjector;
import org.vaadin.tinymceeditor.TinyMCETextField;
import org.vaadin.tinymceeditor.widgetset.client.ui.TinyMCEService.OnChangeListener;

import com.google.gwt.dom.client.NativeEvent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.textfield.AbstractTextFieldConnector;
import com.vaadin.shared.ui.Connect;
import java.util.ArrayList;
import org.vaadin.tinymceeditor.widgetset.shared.TinymceState;

@Connect(value = TinyMCETextField.class, loadStyle = Connect.LoadStyle.EAGER)
public class TinyMCEConnector extends AbstractTextFieldConnector implements OnChangeListener {
    
    private static boolean tinymeInitialized = false;
    static {
        String tinymcscript = GWT.getModuleBaseForStaticFiles() + "tinymce/tinymce.min.js";
        ScriptInjector.fromUrl(tinymcscript).setWindow(ScriptInjector.TOP_WINDOW).setCallback(new Callback<Void, Exception>() {
            @Override
            public void onFailure(Exception reason) {
                throw new RuntimeException("Error loading tinymce");
            }

            @Override
            public void onSuccess(Void result) {
                for (Scheduler.ScheduledCommand lazyInit : lazyInits) {
                    lazyInit.execute();
                }
                tinymeInitialized = true;
            }
        }).inject();
    }
    
    private static ArrayList<Scheduler.ScheduledCommand> lazyInits = new ArrayList<>();

    private boolean inited;
    private Object oldContent;
    private String paintableId;

    @Override
    protected void init() {
        getWidget().setListener(TinyMCEConnector.this);
        super.init();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        // Save the client side identifier (paintable id) for the widget
        paintableId = getState().id;
        if (paintableId == null) {
            paintableId = getConnectorId();
        }
        getWidget().getElement().setId(paintableId);
        getWidget().setConf(getState().conf);

        if (!inited) {
            Scheduler.ScheduledCommand cmd = new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    //set initial value
                    getWidget().getElement().setInnerHTML(getState().text);
                    //load the editor component
                    TinyMCEService.loadEditor(paintableId, TinyMCEConnector.this, getState().conf);
                    //mark the editor initialized
                    inited = true;
                    getWidget().setInitialized();
                }
            };
            
            if(tinymeInitialized) {
                cmd.execute();
            } else {
                lazyInits.add(cmd);
            }
            
        } else {

            //the editor is initialized, just update the text content
            // Also check if Vaadin decided to post the already known content to client
            // This might cause invalid state in some rare conditions
            boolean shouldSkipUpdate = oldContent != null && getState().text.equals(oldContent);
            if (!shouldSkipUpdate) {
                TinyMCEService.get(paintableId).setContent(getState().text);
            }
        }
    }

    @Override
    public TinymceState getState() {
        return (TinymceState) super.getState();
    }

    @Override
    public VTinyMCETextField getWidget() {
        return (VTinyMCETextField) super.getWidget();
    }

    @Override
    public void onChange() {
        sendValueChange();
    }

    @Override
    public void onEvent(NativeEvent event) {
        // Could hook lazy/eager mode here
    }

//
//    private void updateVariable() {
//        TinyMCEditor tinyMCEditor = TinyMCEService.get(paintableId);
//        if (tinyMCEditor == null) {
//            return;
//        }
//        String content = tinyMCEditor.getContent();
//        if (content != null && !content.equals(oldContent)) {
//            
//            client.updateVariable(paintableId, "text", content, immediate);
//        }
//        oldContent = content;
//    }
}
