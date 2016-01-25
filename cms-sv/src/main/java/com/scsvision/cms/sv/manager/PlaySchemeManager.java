package com.scsvision.cms.sv.manager;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.SvPlayScheme;

/**
 * PlaySchemeManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月12日 下午4:52:08
 */
@Local
public interface PlaySchemeManager {

	/**
	 * 创建播放方案
	 * 
	 * @param name
	 *            播放方案名称
	 * @param screenNo
	 *            分屏数量
	 * @param schemeConfig
	 *            播放方案json内容
	 * @return 创建成功的ID
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月12日 下午4:53:56
	 */
	public Long createPlayScheme(String name, Integer screenNo,
			String schemeConfig);

	/**
	 * 获取平台中的播放方案列表
	 * 
	 * @return 平台中的播放方案列表
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月14日 上午10:39:11
	 */
	public List<SvPlayScheme> listPlayScheme();

	/**
	 * 修改播放方案
	 * 
	 * @param id
	 *            播放方案ID
	 * @param name
	 *            播放方案名称
	 * @param screenNo
	 *            分屏数量
	 * @param schemeConfig
	 *            播放方案json内容
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月14日 下午3:08:28
	 */
	public void updatePlayScheme(Long id, String name, Integer screenNo,
			String schemeConfig);

	/**
	 * 删除播放方案
	 * 
	 * @param id
	 *            播放方案ID
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月14日 下午3:17:53
	 */
	public void deletePlayScheme(Long id);
}
