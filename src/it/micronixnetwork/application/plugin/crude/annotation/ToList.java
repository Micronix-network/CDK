package it.micronixnetwork.application.plugin.crude.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author kobo
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ToList {
    boolean ordered() default false;
    boolean defaultOrdered() default false;
    boolean descendant() default false;
    boolean filtered() default false;
    boolean listed() default true;
    String roles() default "";
    String filterRule() default "nill";
    String cellRule() default "nill";
    String fixValue() default "nill";
    String defaultValue() default "nill";
    boolean hidden() default false;
    String draggable() default "";
    boolean downlink() default false;
    boolean append() default false;
}
