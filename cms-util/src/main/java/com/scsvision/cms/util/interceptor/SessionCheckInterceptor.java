package com.scsvision.cms.util.interceptor;

import java.lang.annotation.Annotation;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.util.annotation.Logon;

/**
 * SessionCheckInterceptor
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月12日 上午9:57:00
 */
public class SessionCheckInterceptor {

	@AroundInvoke
	public Object check(InvocationContext ctx) throws Exception {
		Annotation[] annotations = ctx.getMethod().getAnnotations();

		for (Annotation annotation : annotations) {
			if (annotation instanceof Logon) {
				Logon logon = (Logon) annotation;
				if (logon.check()) {
					Object[] params = ctx.getParameters();
					for (Object param : params) {
						if (param instanceof HttpServletRequest) {
							HttpServletRequest request = (HttpServletRequest) param;
							Long userId = (Long) request.getSession()
									.getAttribute("userId");
							if (null == userId) {
								BaseDTO dto = new BaseDTO();
								dto.setCode(ErrorCode.USER_SESSION_EXPIRED);
								dto.setMessage("用户会话过期，请重新登录！");
								dto.setMethod(request.getHeader(Header.METHOD));
								return dto;
							}
						}
					}
				}
			}
		}
		return ctx.proceed();
	}
}
