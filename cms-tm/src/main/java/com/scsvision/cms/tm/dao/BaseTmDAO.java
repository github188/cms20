package com.scsvision.cms.tm.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * BaseTmDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:23:09
 */
@Local
public interface BaseTmDAO<T, PK extends Serializable> {
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
