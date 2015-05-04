/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.application.plugin.crude.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author kobo
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldStyleDirective {
    int group() default 1;
    int priority() default 0;
    String tableCellStyle() default "";
    String tableCellStyleRule() default "";
    String inputFieldStyle() default "";
    String inputFieldStyleRule() default "";
    String labelFieldStyle() default "";
    String labelFieldStyleRule() default "";
}
