/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.application.plugin.crude.annotation.renderer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author kobo
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TextRenderer{
    
    public static final String INT_TYPE="int";
    
    public static final String CURRENCY_TYPE="currency";
    
    public static final String PERC_TYPE="perc";
    
    public static final String DATE_TYPE="date";
    
    public static final String TIME_TYPE="time";
    
    public static final String REAL_TYPE="real";
    
    public static final String MINUTES_TYPE="minutes";
    
    public static final String PASSWORD_TYPE="password";
    
    String type() default "text";
    
    String viewRule() default "nill";
    
    String initValue() default "";
    
    String droppableFrom() default "";
    
}
