package com.scsvision.cms.maintain.dao;

import java.util.List;

/**
 * GatherBaseDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:02:34
 */
public interface OnlineBaseDAO<T> {

	public void batchInsert(List<T> list);

	public T insert(T entity);

	public void update(String id, T entity);

	public void delete(String id);

	public List<T> list(int start, int limit);
}
