package com.scsvision.cms.gather.dao;

import java.util.Date;
import java.util.List;

/**
 * GatherBaseDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:02:34
 */
public interface GatherBaseDAO<T> {

	public void batchInsert(List<T> list);

	public List<T> list(Long deviceId, int start, int limit);

	public List<T> list(List<Long> ids, Date beginTime, Date endTime);

	public T getGatherEntity(Long deviceId);

	public T getFirst();

	public List<T> list(Long deviceId, Date beginTime, Date endTime);
}
