package com.scsvision.cms.util.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Logon
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月12日 上午9:57:37
 */
@Retention(RUNTIME)
@Target({ METHOD })
public @interface Logon {
	boolean check() default true;
}
