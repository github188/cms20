package com.scsvision.cms.statistic.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

/**
 * StatisticBaseDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午1:59:59
 */
@Local
public interface StatisticBaseDAO<T> {

	public void batchInsert(List<T> list);

	public List<T> list(List<Long> organIds, Date beginTime, Date endTime,
			int start, int limit);

	public T getLatest();
	
	public T getFirst();
}
