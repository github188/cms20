/**
 * 
 */
package com.scsvision.cms.maintain.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * @author wangbinyu
 *
 */
@Local
public interface BaseMaintainDAO<T, PK extends Serializable> {
	public void save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public T get(PK id);

	public List<T> list();

	public List<T> list(List<PK> ids);

	public Map<PK, T> map(List<PK> ids);

	public void batchInsert(List<T> list);

	public List<T> findByPropertys(LinkedHashMap<String, Object> map);
}
