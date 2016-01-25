package com.scsvision.cms.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Cache
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午11:25:35
 */
@Retention(RUNTIME)
@Target({ METHOD })
public @interface Cache {
	// 1800秒
	int expire() default 1800;
}
