package com.scsvision.cms.sv.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scsvision.cms.sv.dao.SvBaseDAO;

/**
 * BaseDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:23:59
 */
@Stateless
public class SvBaseDAOImpl<T, PK extends Serializable> implements
		SvBaseDAO<T, PK> {

	@PersistenceContext(unitName = "pu-sv")
	protected EntityManager entityManager;

	protected Class<T> clazz;

	public SvBaseDAOImpl() {
		this.clazz = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void save(T entity) {
		entityManager.persist(entity);
	}

	public void update(T entity) {
		entityManager.merge(entity);
	}

	public void delete(T entity) {
		entityManager.remove(entity);
	}

	public T get(PK id) {
		return entityManager.find(clazz, id);
	}

	public List<T> list() {
		Query q = entityManager.createQuery("select o from "
				+ clazz.getSimpleName() + " o");
		return q.getResultList();
	}

	public void batchInsert(List<T> list) {
		int index = 0;
		for (T entity : list) {
			entityManager.persist(entity);
			index++;
			if (index % 100 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	@Override
	public Map<PK, T> map(List<PK> ids) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o from ");
		sb.append(clazz.getSimpleName());
		sb.append(" o");
		if (null != ids && ids.size() > 0) {
			sb.append(" where id in :ids");
		}
		Query q = entityManager.createQuery(sb.toString());

		if (null != ids && ids.size() > 0) {
			q.setParameter("ids", ids);
		}
		List<T> list = q.getResultList();
		Map<PK, T> map = new HashMap<PK, T>();
		try {
			Method getter = clazz.getMethod("getId");
			for (T t : list) {

				Object key = getter.invoke(t);
				map.put((PK) key, t);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<T> list(List<PK> ids) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o from ");
		sb.append(clazz.getSimpleName());
		sb.append(" o");
		if (null != ids && ids.size() > 0) {
			sb.append(" where id in :ids");
		}
		Query q = entityManager.createQuery(sb.toString());

		if (null != ids && ids.size() > 0) {
			q.setParameter("ids", ids);
		}
		return q.getResultList();
	}

	@Override
	public List<T> findByPropertys(LinkedHashMap<String, Object> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o from ");
		sb.append(clazz.getSimpleName());
		sb.append(" o where 1=1 ");
		sb.append(buildWhereSql(map));
		System.out.println(sb.toString());
		Query q = entityManager.createQuery(sb.toString());
		for (Entry<String, Object> entry : map.entrySet()) {
			q.setParameter(entry.getKey(), entry.getValue());
		}
		return q.getResultList();
	}

	private String buildWhereSql(LinkedHashMap<String, Object> map) {
		StringBuilder builder = new StringBuilder();
		if (map != null && map.size() > 0) {
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				builder.append(" and o." + key + "= :" + key);
			}
			return builder.toString();
		}
		return "";
	}

	public void deleteById(Long id) {
		Query q = entityManager.createQuery("delete from "
				+ clazz.getSimpleName() + " o where o.id =:id");
		q.setParameter("id", id);
		q.executeUpdate();
	}
}
