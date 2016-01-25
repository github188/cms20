package com.scsvision.database.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.Organ;

/**
 * @author sjt
 *
 */
@Local
public interface OrganDAO extends BaseDAO<Organ, Long> {

	/**
	 * 
	 * 查询该机构以及下级机构
	 * 
	 * @param organId
	 *            机构id
	 * @return 机构列表
	 */
	public List<Organ> listOrganlikePath(Long organId);

	/**
	 * 根据需求查询机构列表
	 * 
	 * @param OrganId
	 *            机构的id
	 * @param name
	 *            机构名称
	 * @param start
	 *            起始数量
	 * @param limit
	 *            每页条数
	 * @return 列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:31:32
	 */
	public List<Organ> listOrgan(Long organId, String name, int start, int limit);

	/**
	 * 根据需求查询机构条数
	 * 
	 * @param OrganId
	 *            机构的id
	 * @param name
	 *            机构名称
	 * @return 数量
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 上午9:39:59
	 */
	public Integer countOrgan(Long organId, String name);

	/**
	 * 根据机构ids查询机构列表
	 * 
	 * @param realOrgans
	 *            真实机构ids
	 * @return List<Organ>
	 */
	public List<Organ> listOrganByIds(List<Long> ids);

}
