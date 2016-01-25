package com.scsvision.database.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

/**
 * BaseDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:20:44
 */
@Local
public interface BaseDAO<T, PK extends Serializable> {
	public void save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public T get(PK id);
	
	public T loadByType(String type);

	public List<T> list();

	public void batchInsert(List<T> list);

	public Map<PK, T> map(List<PK> ids);

	public List<T> findByPropertys(LinkedHashMap<String, Object> map);

	public T findBySn(String sn);

	public void deleteById(Long id);
}
