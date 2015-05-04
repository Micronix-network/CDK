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
public @interface AsincInfo {
    String mappedObject() default "nill";
    String targetField() default "nill";
}
