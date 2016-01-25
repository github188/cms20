package com.scsvision.cms.duty.manager.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.duty.dao.DutyRecordDAO;
import com.scsvision.cms.duty.dao.VisitRecordDAO;
import com.scsvision.cms.duty.manager.VisitRecordManager;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.entity.DutyRecord;
import com.scsvision.database.entity.VisitRecord;

/**
 * VisitRecordManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:17:00
 */
@Stateless
@Interceptors(CacheInterceptor.class)
public class VisitRecordManagerImpl implements VisitRecordManager {
	@EJB(beanName = "VisitRecordDAOImpl")
	private VisitRecordDAO visitRecordDAO;
	@EJB(beanName = "DutyRecordDAOImpl")
	private DutyRecordDAO dutyRecordDAO;

	@Override
	public Long createVisitRecord(String visitors, String phone, String reason,
			Long arriveTime, Long leaveTime, String result, String note)
			throws BusinessException {
		VisitRecord entity = new VisitRecord();
		entity.setArriveTime(arriveTime);
		entity.setLeaveTime(leaveTime);
		entity.setNote(note);
		entity.setPhone(phone);
		entity.setReason(reason);
		entity.setRecordTime(System.currentTimeMillis());
		entity.setResult(result);
		entity.setVisitors(visitors);
		// 查询当班情况设置monitors
		DutyRecord latest = dutyRecordDAO.getLatest();
//		entity.setMonitors(latest.getMonitors());
		visitRecordDAO.save(entity);
		return entity.getId();
	}

	@Override
//	@Cache(expire = 60)
	public List<VisitRecord> listVisitRecord(Long beginTime, Long endTime,
			String monitor, int start, int limit) {
		return visitRecordDAO.listVisitRecord(beginTime, endTime, monitor,
				start, limit);
	}

	@Override
//	@Cache(expire = 60)
	public int countVisitRecord(Long beginTime, Long endTime, String monitor) {
		return visitRecordDAO.countVisitRecord(beginTime, endTime, monitor);
	}
}
