package it.micronixnetwork.application.plugin.crude.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Computed {
    String value() default "nill";
    boolean modifiable() default false;
}
