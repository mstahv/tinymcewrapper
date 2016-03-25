package org.vaadin.tinymceeditor.widgetset.client.ui;

import com.google.gwt.dom.client.NativeEvent;

/**
 * GWT wrapper for TinyMCE. 
 *
 */
public class TinyMCEService {

	/**
	 * Use this method to load editor to given identifier.
	 * 
	 * @param id the identifier for the element you want to replace with TinyMCE
	 * @param listener this listener will get notified by changes in editor
	 * @param cc possible custom configuration for editor as string (eg. {theme : "simple"}
	 */
	public static native void loadEditor(String id, OnChangeListener listener, String cc)
	/*-{
	 
	 	var conf = {
	    
			selector : '#' + id ,
			height: 500,
			plugins: [
			    'advlist autolink lists link image charmap print preview anchor',
			    'searchreplace visualblocks code fullscreen',
			    'insertdatetime media table contextmenu paste code'
			],
			toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
    	    setup : function(ed) {
	        
	            ed.on('setContent', function(e) {
	 				listener.@org.vaadin.tinymceeditor.widgetset.client.ui.TinyMCEService.OnChangeListener::onChange()();
    			});
	            ed.on('change', function(e) {
      				console.log('Editor was changed');
	 				listener.@org.vaadin.tinymceeditor.widgetset.client.ui.TinyMCEService.OnChangeListener::onChange()();
    			});
      		}
	    };
	    try {
		 	if(cc) {
		 		var customConfig = eval('('+cc+')');
			 	for(var j in customConfig) {
			 		conf[j] = customConfig[j];
			 	}
		 	}
	 	} catch (e) {}
	 	
	 	
	    $wnd.tinymce.init(conf);

	}-*/
	;
	
	/**
	 * Returns a javascript overlay of TinyMCE editor for given identifier.
	 * 
	 * @param id
	 * @return the overlay for TinyMCE.Editor or null in not yet inited
	 */
	public static native TinyMCEditor get(String id)
	/*-{
		return $wnd.tinyMCE.get(id);
	}-*/;

	public interface OnChangeListener {
		
		public void onChange();
		
		public void onEvent(NativeEvent event);
	}

}
