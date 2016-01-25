package com.scsvision.cms.tm.manager.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.cms.tm.manager.TmCmsPublishManager;

/**
 * TmCmsPublishManagerImpl
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午2:59:54
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class TmCmsPublishManagerImpl implements TmCmsPublishManager {

}
