/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.application.plugin.crude.annotation.renderer;

import it.micronixnetwork.application.plugin.crude.annotation.NULLClass;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author kobo
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectRenderer{
    String map() default "nill";
    String viewRule() default "nill";
    String startValue() default "nill";
    String dependFrom() default "nill";
    String activeOnChange() default "";
    boolean append() default false;
    Class mappedObject() default NULLClass.class;
}
