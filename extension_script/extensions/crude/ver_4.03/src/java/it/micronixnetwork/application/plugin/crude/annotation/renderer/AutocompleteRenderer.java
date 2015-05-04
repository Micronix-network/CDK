package it.micronixnetwork.application.plugin.crude.annotation.renderer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AutocompleteRenderer {
    String map() default "nill";
    String jsonQuery() default "nill";
    String viewRule() default "nill";
    String startValue() default "nill";
    String droppableFrom() default "";

}
