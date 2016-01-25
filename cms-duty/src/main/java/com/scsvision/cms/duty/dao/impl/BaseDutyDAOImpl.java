package com.scsvision.cms.duty.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scsvision.cms.duty.dao.BaseDutyDAO;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;


@Stateless
public class BaseDutyDAOImpl<T, PK extends Serializable> implements
		BaseDutyDAO<T, PK> {
	@PersistenceContext(unitName = "pu-duty")
	protected EntityManager entityManager;

	protected Class<T> clazz;

	public BaseDutyDAOImpl() {
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
		T t = entityManager.find(clazz, id);
		if (null == t) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					clazz.getSimpleName() + "[" + id + "] not found !");
		}
		return t;
	}

	public List<T> list() {
		Query q = entityManager.createQuery("select o from "
				+ clazz.getSimpleName() + " o");
		return q.getResultList();
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
}
