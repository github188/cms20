package com.scsvision.database.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.ErrorMsgDAO;
import com.scsvision.database.entity.ErrorMessage;
import com.scsvision.database.manager.ErrorMsgManager;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class ErrorMsgManagerImpl implements ErrorMsgManager {

	@EJB(beanName = "ErrorMsgDAOImpl")
	private ErrorMsgDAO errorMsgDAO;

	@Cache(expire = 86400)
	public Map<String, String> getErrorMsgMap() {
		// 数据库查
		List<ErrorMessage> list = errorMsgDAO.list();
		Map<String, String> map = new HashMap<String, String>();
		for (ErrorMessage em : list) {
			map.put(em.getErrormsgCode(), em.getMsg());
		}
		return map;
	}

}
