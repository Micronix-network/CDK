package it.micronixnetwork.application.plugin.crude.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValidField {
    
    public static final String EMAIL_VALIDATION="email";
    
    public static final String PIVA_VALIDATION="piva";
    
    public static final String CF_VALIDATION="cf";
    
    public static final String DATE_VALIDATION="date";
    
    public static final String INT_VALIDATION="int";
    
    public static final String DOUBLE_VALIDATION="double";
    
    public static final String FLOAT_VALIDATION="float";
    
    public static final String LONG_VALIDATION="long";
    
    public static final String TIME_VALIDATION="time";
    
    boolean empty() default true; 
    String type() default "";
    String regexp() default "";
    int minsize() default 0;
    int maxsize() default 8000;
    int size() default 0;
    String rule() default "";
}
