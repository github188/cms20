package com.scsvision.cms.em.dao;

import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.EventManualReal;

@Local
public interface EventManualRealDAO extends EmBaseDAO<EventManualReal, Long> {
	/**
	 * 获取未处理事件列表
	 * 
	 * @param start
	 *            开始数
	 * @param limit
	 *            每页显示数量
	 * @return 未处理事件列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015年 下午1:37:01
	 */
	public List<EventManualReal> emanualRealList(Integer start, Integer limit);
}
