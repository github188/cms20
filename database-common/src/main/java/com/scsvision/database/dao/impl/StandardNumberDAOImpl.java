package com.scsvision.database.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.database.dao.StandardNumberDAO;
import com.scsvision.database.entity.StandardNumber;

/**
 * StandardNumberDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:57:04
 */
@Stateless
public class StandardNumberDAOImpl extends BaseDAOImpl<StandardNumber, Long>
		implements StandardNumberDAO {

	private final Logger logger = LoggerFactory
			.getLogger(StandardNumberDAOImpl.class);

	@Override
	public void sync(String sn, String name, Class clazz, String type,
			boolean deleteFlag) {
		StandardNumber entity = findBySn(sn);
		// 检查是否存在
		if (null == entity) {
			if (deleteFlag) {
				// do nothing
			}
			// 新增一条记录
			else {
				entity = new StandardNumber();
				entity.setClassName(clazz.getSimpleName());
				entity.setName(name);
				entity.setStandardNumber(sn);
				entity.setType(type);
				save(entity);
			}
		} else {
			// 删除
			if (deleteFlag) {
				delete(entity);
			}
			// 修改
			else {
				entity.setClassName(clazz.getSimpleName());
				// 名称不为空才修改
				if (null != name) {
					entity.setName(name);
				}
				// 类型不为空才修改
				if (null != type) {
					entity.setType(type);
				}
				entity.setStandardNumber(sn);
				update(entity);
			}
		}
	}

	@Override
	public Object getBySn(String className, String sn) {
		String sql = "select o from " + className
				+ " o where o.standardNumber = :sn";
		Query q = entityManager.createQuery(sql);
		q.setParameter("sn", sn);
		List<Object> list = q.getResultList();
		if (list.size() == 0) {
			return null;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			logger.error("StandardNumber[" + sn + "] in " + className
					+ " is duplicate !");
			return list.get(0);
		}
	}
}
