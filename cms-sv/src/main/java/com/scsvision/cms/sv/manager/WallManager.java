/**
 * 
 */
package com.scsvision.cms.sv.manager;

import java.util.List;

import javax.ejb.Local;

import org.json.JSONArray;

import com.scsvision.cms.sv.vo.ListWallMonitorVO;
import com.scsvision.cms.sv.vo.ListWallSchemeVO;
import com.scsvision.database.entity.SvMonitor;
import com.scsvision.database.entity.SvWall;
import com.scsvision.database.entity.SvWallScheme;

/**
 * @author wangbinyu
 *
 */
@Local
public interface WallManager {

	/**
	 * 创建电视墙播放方案
	 * 
	 * @param wallId
	 *            电视墙ID
	 * @param name
	 *            播放方案名称
	 * @param userGroupId
	 *            用户组id
	 * @param monitors
	 *            通道绑定摄像头json
	 * @return Long
	 */
	public Long createWallScheme(Long wallId, String name, Long userGroupId,
			JSONArray monitors);

	/**
	 * 创建电视墙通道
	 * 
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param channelNumber
	 *            通道号
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param dwsId
	 *            电视墙服务器id
	 * @param wallId
	 *            电视墙id
	 * @return 通道号id
	 */
	public Long createMonitor(String name, String standardNumber,
			String channelNumber, String x, String y, String width,
			String height, Long dwsId, Long wallId);

	/**
	 * 更新电视墙通道
	 * 
	 * @param id
	 *            通道id
	 * @param name
	 *            通道名称
	 * @param standardNumber
	 *            标准号
	 * @param channelNumber
	 *            通道号
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param dwsId
	 *            电视墙服务器id
	 * @param wallId
	 *            电视墙id
	 */
	public void updateMonitor(Long id, String name, String standardNumber,
			String channelNumber, String x, String y, String width,
			String height, Long dwsId, Long wallId);

	/**
	 * 
	 * @param id
	 *            电视墙通道id
	 */
	public void deleteMonitor(Long id);

	/**
	 * 统计电视墙通道数
	 * 
	 * @param wallId
	 *            电视墙id
	 * @return Integer
	 */
	public Integer countMonitor(Long wallId);

	/**
	 * 查询电视墙通道数列表
	 * 
	 * @param wallId
	 *            电视墙id
	 * @param start
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return List<SvMonitor>
	 */
	public List<SvMonitor> listMonitor(Long wallId, Integer start, Integer limit);

	/**
	 * 创建电视墙
	 * 
	 * @param name
	 *            电视墙名称
	 * @param note
	 *            备注
	 * @return Long
	 */
	public Long createWall(String name, String note);

	/**
	 * 更新电视墙
	 * 
	 * @param id
	 *            电视墙id
	 * @param name
	 *            名称
	 * @param note
	 *            备注
	 */
	public void updateWall(Long id, String name, String note);

	/**
	 * 根据id删除电视墙
	 * 
	 * @param id
	 *            电视墙id
	 */
	public void deleteWall(Long id);

	/**
	 * 查询电视墙列表
	 * 
	 * @param limit
	 *            需要查询条数
	 * @param start
	 *            开始查询条数
	 * 
	 * @return List<SvWall>
	 */
	public List<SvWall> listWall(Integer start, Integer limit);

	/**
	 * 更新电视墙播放方案
	 * 
	 * @param id
	 *            播放方案id
	 * @param wallId
	 *            电视墙id
	 * @param name
	 *            播放方案名称
	 * @param monitors
	 *            电视墙通道
	 */
	public void updateWallScheme(Long id, Long wallId, String name,
			JSONArray monitors);

	/**
	 * 删除电视墙播放方案
	 * 
	 * @param id
	 *            电视墙播放方案id
	 */
	public void deleteWallScheme(Long id);

	/**
	 * 查询电视墙播放方案列表
	 * 
	 * @param userGroupId
	 *            用户组id
	 * @return List<ListWallSchemeVO>
	 */
	public List<ListWallSchemeVO> listWallScheme(Long userGroupId);

	/**
	 * 统计电视墙数量
	 * 
	 * @return Integer
	 */
	public Integer countWall();

	/**
	 * 查询电视墙以及下边通道
	 * 
	 * @return List<ListWallMonitorVO>
	 */
	public List<ListWallMonitorVO> listWallMonitor();

	/**
	 * 编辑电视播坐标
	 * 
	 * @param monitors
	 *            电视墙通道
	 */
	public void editWallLayout(JSONArray monitors);

}
