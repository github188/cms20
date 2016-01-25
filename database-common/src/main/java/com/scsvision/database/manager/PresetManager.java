package com.scsvision.database.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.SvPreset;

/**
 * PresetManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:11:11
 */
@Local
public interface PresetManager {
	/**
	 * 创建预置点
	 * 
	 * @param presetValue
	 *            预置点索引
	 * @param deviceId
	 *            设备id
	 * @param name
	 *            预置点名称
	 * @param resourceId
	 *            资源id
	 * @return Long
	 */
	public Long createPreset(Integer presetValue, Long deviceId, String name,
			Long resourceId);

	/**
	 * 
	 * @param id
	 * @param presetValue
	 * @param deviceId
	 * @param name
	 */
	public void updatePreset(Long id, Integer presetValue, Long deviceId,
			String name);

	/**
	 * 列表查询预置点
	 * 
	 * @param deviceId
	 *            设备id
	 * @return List<SvPreset>
	 */
	public List<SvPreset> listPreset(Long deviceId);

	/**
	 * 删除预置点
	 * 
	 * @param id
	 *            预置点id
	 */
	public void deletePreset(Long id);

}
