package com.scsvision.cms.util.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 * ContextBean
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午9:12:43
 */
@Stateless
public class ContextBean {
	@Resource
	private SessionContext context;

	public SessionContext getContext() {
		return context;
	}

}
