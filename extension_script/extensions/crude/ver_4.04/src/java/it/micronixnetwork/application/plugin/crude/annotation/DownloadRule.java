package it.micronixnetwork.application.plugin.crude.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface DownloadRule {
    String downloadDirectPath() default "";
    String downloadRulePath() default "";
    String fileName() default "test.gaf";
}
