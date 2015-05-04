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
public @interface TextAreaRenderer {
     int height() default 100;
}
