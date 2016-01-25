package com.scsvision.cms.duty.manager.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.duty.dao.DutyRecordDAO;
import com.scsvision.cms.duty.manager.DutyRecordManager;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.entity.DutyRecord;

/**
 * DutyRecordManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:21:19
 */
@Stateless
@Interceptors(CacheInterceptor.class)
public class DutyRecordManagerImpl implements DutyRecordManager {
	@EJB(beanName = "DutyRecordDAOImpl")
	private DutyRecordDAO dutyRecordDAO;

	@Override
	@Cache(expire = 60)
	public List<DutyRecord> listDutyRecord(String dutyUserName, Long beginTime,
			Long endTime, int start, int limit) {
		return dutyRecordDAO.listDutyRecord(dutyUserName, beginTime, endTime,
				start, limit);
	}

	@Override
	@Cache(expire = 60)
	public int countDutyRecord(String dutyUserName, Long beginTime, Long endTime) {
		return dutyRecordDAO.countDutyRecord(dutyUserName, beginTime, endTime);
	}
}
