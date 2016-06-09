/**
 * 
 */
package de.dfki.resc28.flapjack.services;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.HttpMethod;

/**
 * @author rmrschub
 *
 */
/**
 * Indicates that the annotated method responds to HTTP PATCH requests
 * http://tools.ietf.org/html/rfc5789
 * 
 * @see HttpMethod
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("PATCH")
public @interface PATCH { }