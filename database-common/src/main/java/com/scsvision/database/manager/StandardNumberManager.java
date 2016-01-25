package com.scsvision.database.manager;

import javax.ejb.Local;

import com.scsvision.database.entity.StandardNumber;

/**
 * StandardNumberManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:47:47
 */
@Local
public interface StandardNumberManager {

	/**
	 * 根据资源标准号查询资源信息
	 * 
	 * @param sn
	 *            资源标准号
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:51:01
	 */
	public Object getByStandardNumber(String sn);
}
