package com.scsvision.database.dao;

import javax.ejb.Local;

import com.scsvision.database.entity.StandardNumber;

/**
 * StandardNumberDAO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:56:16
 */
@Local
public interface StandardNumberDAO extends BaseDAO<StandardNumber, Long> {
	/**
	 * 同步SN和名称
	 * 
	 * @param sn
	 *            资源SN
	 * @param name
	 *            资源名称
	 * @param clazz
	 *            资源实体对象Class
	 * @param type
	 *            资源类型
	 * @param deleteFlag
	 *            删除标识
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:07:12
	 */
	public void sync(String sn, String name, Class clazz, String type,
			boolean deleteFlag);

	/**
	 * 根据资源类型和SN，查询资源实体
	 * 
	 * @param className
	 *            资源类型名称
	 * @param sn
	 *            资源SN
	 * @return 资源实体
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:22:19
	 */
	public Object getBySn(String className, String sn);
}
