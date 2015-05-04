package it.micronixnetwork.application.plugin.crude.gui.component.renderer;

import it.micronixnetwork.application.plugin.crude.annotation.renderer.AutocompleteRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.ContributorsRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextAreaRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.YesNoRenderer;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.element.Autocomplete;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.element.Contributors;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.element.Select;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.element.Text;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.element.TextArea;
import it.micronixnetwork.application.plugin.crude.gui.component.renderer.element.YesNo;

import java.lang.reflect.Field;
import java.util.HashMap;

public class RendererFactory {

    private static RendererFactory instance = new RendererFactory();
    
    
    private HashMap<String,FieldRenderer> rendererCache =new HashMap<String,FieldRenderer>();
    
    
    private RendererFactory() {
	super();
    }

    public static RendererFactory getInstance() {
	return instance;
    }

    public FieldRenderer produce(Class targetClass, String fieldname, Field field) {
	FieldRenderer result=null;
	
	String keyCache=targetClass.getName()+fieldname+field.getName();

	TextRenderer ti = field.getAnnotation(TextRenderer.class);
	if (ti != null){
	    result=getFromCache(keyCache,Text.class);
	    if(result==null){
		result= new Text(targetClass, fieldname, field, false);
		rendererCache.put(keyCache, result);
	    }
	}

	TextAreaRenderer tai = field.getAnnotation(TextAreaRenderer.class);
	if (tai != null){
	    result=getFromCache(keyCache,TextAreaRenderer.class);
	    if(result==null){
		result= new TextArea(targetClass, fieldname, field);
		rendererCache.put(keyCache, result);
	    }
	}
	

	YesNoRenderer yni = field.getAnnotation(YesNoRenderer.class);
	if (yni != null){
	    result=getFromCache(keyCache,YesNo.class);
	    if(result==null){
		result= new YesNo(targetClass, fieldname, field);
		rendererCache.put(keyCache, result);
	    }
	}
	

	SelectRenderer si = field.getAnnotation(SelectRenderer.class);
	if (si != null) {
	    result=getFromCache(keyCache,Select.class);
	    if(result==null){
		result= new Select(targetClass, fieldname, field);
		rendererCache.put(keyCache, result);
	    }
	}
	
	ContributorsRenderer ci=field.getAnnotation(ContributorsRenderer.class);
	if (ci != null) {
	    result=getFromCache(keyCache,Contributors.class);
	    if(result==null){
		result= new Contributors(targetClass, fieldname, field);
		rendererCache.put(keyCache, result);
	    }
	}
	
	AutocompleteRenderer ai=field.getAnnotation(AutocompleteRenderer.class);
	if (ai != null) {
	    result=getFromCache(keyCache,Autocomplete.class);
	    if(result==null){
		result= new Autocomplete(targetClass, fieldname, field);
		rendererCache.put(keyCache, result);
	    }
	}
	
	if(result==null){
	    result=getFromCache(keyCache,Text.class);
	    if(result==null){
		result= new Text(targetClass, fieldname, field,false);
		rendererCache.put(keyCache, result);
	    }
	}
	
	return result;
    }
    
    
    private FieldRenderer getFromCache(String key, Class renderClass){
	FieldRenderer result=rendererCache.get(key);
	if(result!=null && result.getClass()==renderClass){
	    return result;
	}
	return null;
    }

}
