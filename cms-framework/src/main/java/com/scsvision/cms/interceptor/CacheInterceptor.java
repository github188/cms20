package com.scsvision.cms.interceptor;

import java.lang.annotation.Annotation;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.util.cache.SpyMemcacheUtil;

/**
 * CacheInterceptor
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午11:19:17
 */
public class CacheInterceptor {

	@AroundInvoke
	public Object cache(InvocationContext ctx) throws Exception {
		Annotation[] annotations = ctx.getMethod().getAnnotations();
		try {
			for (Annotation annotation : annotations) {
				// 使用缓存
				if (annotation instanceof Cache) {
					Cache cache = (Cache) annotation;
					Object[] params = ctx.getParameters();
					StringBuffer sb = new StringBuffer();
					for (Object param : params) {
						if (null != param) {
							sb.append(param.hashCode());
						} else {
							sb.append(0);
						}
					}
					sb.append(ctx.getTarget().getClass().getName());
					sb.append("#");
					sb.append(ctx.getMethod().getName());
					String key = sb.toString().hashCode() + "";
					Object value = SpyMemcacheUtil.get(key);
					if (null == value) {
						value = ctx.proceed();
						SpyMemcacheUtil.put(key, value, cache.expire());
					}
					return value;
				}
			}
			return ctx.proceed();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		}
	}
}
