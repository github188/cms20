/**
 * 
 */
package com.scsvision.database.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.DicManagement;

/**
 * @author wangbinyu
 *
 */
@Local
public interface DicManagementManager {

	/**
	 * 查询行政单位列表
	 * 
	 * @return List<DicManagement>
	 */
	public List<DicManagement> list();

}
