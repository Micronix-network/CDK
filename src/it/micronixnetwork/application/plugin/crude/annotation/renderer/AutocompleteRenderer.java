package it.micronixnetwork.application.plugin.crude.annotation.renderer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AutocompleteRenderer {
    String jsonQuery() default "nill";
    String viewRule() default "nill";
    String droppableFrom() default "";
    String dependFrom() default "nill";
    String activeOnChange() default "";
    boolean append() default false;

}
