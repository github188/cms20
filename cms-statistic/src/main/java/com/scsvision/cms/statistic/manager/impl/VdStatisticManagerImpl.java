package com.scsvision.cms.statistic.manager.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.cms.statistic.dao.DayVdDAO;
import com.scsvision.cms.statistic.dao.HourVdDAO;
import com.scsvision.cms.statistic.dao.MonthVdDAO;
import com.scsvision.cms.statistic.manager.VdStatisticManager;
import com.scsvision.database.entity.DayVd;
import com.scsvision.database.entity.HourVd;
import com.scsvision.database.entity.MonthVd;

/**
 * VdStatisticManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:34:56
 */
@Stateless
@Interceptors(CacheInterceptor.class)
public class VdStatisticManagerImpl implements VdStatisticManager {
	@EJB(beanName = "HourVdDAOImpl")
	private HourVdDAO hourVdDAO;
	@EJB(beanName = "DayVdDAOImpl")
	private DayVdDAO dayVdDAO;
	@EJB(beanName = "MonthVdDAOImpl")
	private MonthVdDAO monthVdDAO;

	@Override
	public void batchInsertHourVd(List<HourVd> list) {
		hourVdDAO.batchInsert(list);
	}

	@Override
	public List<HourVd> listByHour(List<Long> organIds, Date beginTime,
			Date endTime, int start, int limit) {
		return hourVdDAO.list(organIds, beginTime, endTime, start, limit);
	}

	@Override
	public HourVd getLastestHourVd() {
		return hourVdDAO.getLatest();
	}

	@Override
	public HourVd getFirstHourVd() {
		return hourVdDAO.getFirst();
	}

	@Override
	public void batchInsertDayVd(List<DayVd> list) {
		dayVdDAO.batchInsert(list);
	}

	@Override
	public List<DayVd> listByDay(List<Long> organIds, Date beginTime,
			Date endTime, int start, int limit) {
		return dayVdDAO.list(organIds, beginTime, endTime, start, limit);
	}

	@Override
	public DayVd getLastestDayVd() {
		return dayVdDAO.getLatest();
	}

	@Override
	public MonthVd getLastestMonthVd() {
		return monthVdDAO.getLatest();
	}

	@Override
	public DayVd getFirstDayVd() {
		return dayVdDAO.getFirst();
	}

	@Override
	public void batchInsertMonthVd(List<MonthVd> list) {
		monthVdDAO.batchInsert(list);
	}

	@Override
	public List<MonthVd> listByMonth(List<Long> organIds, Date beginTime,
			Date endTime, int start, int limit) {
		List<MonthVd> list = monthVdDAO.list(organIds, beginTime, endTime,
				start, limit);
		return list;
	}
}
