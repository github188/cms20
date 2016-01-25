package com.scsvision.database.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.SvDevice;

/**
 * @author sjt
 *
 */
@Local
public interface SvDeviceDAO extends BaseDAO<SvDevice, Long> {

	/**
	 * 删除机构下的dvr和camera
	 * 
	 * @param organId
	 *            机构id
	 */
	public void deleteSvDevice(Long organId);

	/**
	 * 查询dvr条数
	 * 
	 * @param ip
	 *            ip地址
	 * @param name
	 *            名称
	 * @return Integer
	 */
	public Integer countDvr(String ip, String name);

	/**
	 * 条件查询视频服务器
	 * 
	 * @param ip
	 *            ip地址
	 * @param name
	 *            名称
	 * @param start
	 *            开始条数
	 * @param limit
	 *            需要查询条数
	 * @return List<SvDevice>
	 */
	public List<SvDevice> listDvr(String ip, String name, Integer start,
			Integer limit);

	/**
	 * 条件查询摄像头
	 * 
	 * @param name
	 *            摄像头名称
	 * @param stakeNumber
	 *            桩号
	 * @param standardNumber
	 *            标准号
	 * @param dvrId
	 *            视频服务器id
	 * @param start
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @return List<SvDevice>
	 */
	public List<SvDevice> listCamera(String name, String stakeNumber,
			String standardNumber, Long dvrId, Integer start, Integer limit);

	/**
	 * 统计查询摄像头数量
	 * 
	 * @param name
	 *            名称
	 * @param stakeNumber
	 *            桩号
	 * @param standardNumber
	 *            标准号
	 * @param dvrId
	 *            视频服务器id
	 * @return int
	 */
	public int countCamera(String name, String stakeNumber,
			String standardNumber, Long dvrId);

	/**
	 * 根据桩号查询摄像头列表
	 * 
	 * @param organId
	 *            路段机构ID
	 * @param beginStake
	 *            开始桩号
	 * @param endStake
	 *            结束桩号
	 * @return 范围内的摄像头列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月11日 下午3:45:22
	 */
	public List<SvDevice> listCameraByStake(Long organId, Float beginStake,
			Float endStake);
}
