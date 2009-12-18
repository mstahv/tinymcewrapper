package com.vaadin.tinymceeditor.widgetset.client.ui;

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
	    
		    	// General options
			mode : "exact",
			elements : id ,
			theme : "advanced",
			plugins : "safari,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",
		
			// Theme options
			theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
			theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
			theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
			theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,spellchecker,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,blockquote,pagebreak,",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_statusbar_location : "bottom",
			theme_advanced_resizing : true,
	        
	        setup : function(ed) {
      			ed.onChange.add(function(ed, e) {
	 				listener.@com.vaadin.tinymceeditor.widgetset.client.ui.TinyMCEService.OnChangeListener::onChange()();
      			});
	        
      			ed.onEvent.add(function(ed, e) {
	 				listener.@com.vaadin.tinymceeditor.widgetset.client.ui.TinyMCEService.OnChangeListener::onEvent(Lcom/google/gwt/dom/client/NativeEvent;)(e);
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
	 	
	 	
	    $wnd.tinyMCE.init(conf);

	}-*/
	;
	
	/**
	 * Returns a javascript overlay of TinyMCE editor for given identifier.
	 * 
	 * @param id
	 * @return the overlay for TinyMCE.Editor or null in not yet inited
	 */
	public native static TinyMCEditor get(String id)
	/*-{
		return $wnd.tinyMCE.get(id);
	}-*/;

	public interface OnChangeListener {
		
		public void onChange();
		
		public void onEvent(NativeEvent event);
	}

}
