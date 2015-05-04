package it.micronixnetwork.application.plugin.crude.helper;

import java.util.Iterator;

import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.util.ValueStack;

public class I18n {
    
    public static String getText(String key, String defaultMessage,ValueStack stack) {
	String msg = null;
	TextProvider tp = null;
	for (Iterator iterator = stack.getRoot().iterator(); iterator.hasNext();) {
	    Object o = iterator.next();
	    if (o instanceof TextProvider) {
		tp = (TextProvider) o;
		msg = tp.getText(key);
		if(!key.equals(msg) && msg!=null)
		    break;
	    }
	}
	
	if(key.equals(msg) || msg==null){
	    if(defaultMessage!=null)
		msg=defaultMessage;
	    else msg=key;
	}
	return msg;
    }

}
