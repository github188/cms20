/**
 * 
 */
package com.scsvision.cms.sv.manager.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.sv.dao.SvMonitorDAO;
import com.scsvision.cms.sv.dao.SvWallDAO;
import com.scsvision.cms.sv.dao.SvWallSchemeDAO;
import com.scsvision.cms.sv.dao.SvWallSchemeItemDAO;
import com.scsvision.cms.sv.dto.ListWallSchemeDTO;
import com.scsvision.cms.sv.manager.WallManager;
import com.scsvision.cms.sv.vo.ListWallMonitorVO;
import com.scsvision.cms.sv.vo.ListWallMonitorVO.SvMonitorVO;
import com.scsvision.cms.sv.vo.ListWallSchemeVO;
import com.scsvision.cms.sv.vo.ListWallSchemeVO.WallSchemeItemVO;
import com.scsvision.database.dao.ServerDAO;
import com.scsvision.database.dao.StandardNumberDAO;
import com.scsvision.database.entity.SvMonitor;
import com.scsvision.database.entity.SvWall;
import com.scsvision.database.entity.SvWallScheme;
import com.scsvision.database.entity.SvWallSchemeItem;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class WallManagerImpl implements WallManager {

	@EJB(beanName = "SvWallSchemeDAOImpl")
	private SvWallSchemeDAO svWallSchemeDAO;

	@EJB(beanName = "SvWallSchemeItemDAOImpl")
	private SvWallSchemeItemDAO svWallSchemeItemDAO;

	@EJB(beanName = "SvMonitorDAOImpl")
	private SvMonitorDAO svMonitorDAO;

	@EJB(beanName = "StandardNumberDAOImpl")
	private StandardNumberDAO snDAO;

	@EJB(beanName = "SvWallDAOImpl")
	private SvWallDAO svWallDAO;

	@EJB(beanName = "ServerDAOImpl")
	private ServerDAO serverDAO;

	@Override
	public Long createWallScheme(Long wallId, String name, Long userGroupId,
			JSONArray monitors) {
		SvWallScheme ws = new SvWallScheme();
		ws.setName(name);
		ws.setUserGroupId(userGroupId);
		ws.setWallId(wallId);
		svWallSchemeDAO.save(ws);
		Long wallSchemeId = ws.getId();
		for (int i = 0; i < monitors.length(); i++) {
			JSONObject monitor = monitors.getJSONObject(i);
			SvWallSchemeItem item = new SvWallSchemeItem();
			item.setContent(monitor.getJSONArray("schemes") + "");
			item.setWallSchemeId(wallSchemeId);
			item.setMonitorId(monitor.getLong("monitorId"));
			svWallSchemeItemDAO.save(item);
		}
		return wallSchemeId;
	}

	@Override
	public Long createMonitor(String name, String standardNumber,
			String channelNumber, String x, String y, String width,
			String height, Long dwsId, Long wallId) {
		// 判断通道号是否重复
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("channelNumber", channelNumber);
		List<SvMonitor> list = svMonitorDAO.findByPropertys(params);
		if (list.size() > 0) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"channelNumber:" + channelNumber + " is already exist !");
		}
		SvMonitor monitor = new SvMonitor();
		monitor.setChannelNumber(channelNumber);
		monitor.setDwsId(dwsId);
		monitor.setHeight(height);
		monitor.setName(name);
		monitor.setStandardNumber(standardNumber);
		monitor.setWallId(wallId);
		monitor.setWidth(width);
		monitor.setX(x);
		monitor.setY(y);
		svMonitorDAO.save(monitor);
		snDAO.sync(monitor.getStandardNumber(), monitor.getName(),
				SvMonitor.class, TypeDefinition.VIDEO_MONITOR + "", false);
		return monitor.getId();
	}

	@Override
	public void updateMonitor(Long id, String name, String standardNumber,
			String channelNumber, String x, String y, String width,
			String height, Long dwsId, Long wallId) {
		// 判断通道号是否重复
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("channelNumber", channelNumber);
		List<SvMonitor> list = svMonitorDAO.findByPropertys(params);
		if (list.size() > 0) {
			if (!list.get(0).getId().equals(id)) {
				throw new BusinessException(ErrorCode.CHANNEL_NUMBER_EXIST,
						"channelNumber[" + channelNumber
								+ "] is already exist !");
			}
		}
		SvMonitor monitor = svMonitorDAO.get(id);
		if (null != channelNumber) {
			monitor.setChannelNumber(channelNumber);
		}
		if (null != wallId) {
			monitor.setWallId(wallId);
		}
		if (null != dwsId) {
			monitor.setDwsId(dwsId);
		}
		if (null != name) {
			monitor.setName(name);
		}
		if (StringUtils.isNotBlank(y)) {
			monitor.setY(y);
		}
		if (StringUtils.isNotBlank(x)) {
			monitor.setX(x);
		}
		if (StringUtils.isNotBlank(width)) {
			monitor.setWidth(width);
		}
		if (StringUtils.isNotBlank(height)) {
			monitor.setHeight(height);
		}
		if (null != standardNumber) {
			// 同步SN
			snDAO.sync(monitor.getStandardNumber(), monitor.getName(),
					SvMonitor.class, TypeDefinition.VIDEO_MONITOR + "", false);
			monitor.setStandardNumber(standardNumber);
		}
		svMonitorDAO.update(monitor);
	}

	@Override
	public void deleteMonitor(Long id) {
		SvMonitor monitor = svMonitorDAO.get(id);
		svMonitorDAO.delete(monitor);
		snDAO.sync(monitor.getStandardNumber(), monitor.getName(),
				SvMonitor.class, TypeDefinition.VIDEO_MONITOR + "", true);
	}

	@Override
	public Integer countMonitor(Long wallId) {
		return svMonitorDAO.countMonitor(wallId);
	}

	@Override
	public List<SvMonitor> listMonitor(Long wallId, Integer start, Integer limit) {
		return svMonitorDAO.listMonitor(wallId, start, limit);
	}

	@Override
	public Long createWall(String name, String note) {
		SvWall wall = new SvWall();
		wall.setName(name);
		wall.setNote(note);
		svWallDAO.save(wall);
		return wall.getId();
	}

	@Override
	public void updateWall(Long id, String name, String note) {
		SvWall wall = svWallDAO.get(id);
		if (StringUtils.isNotBlank(name)) {
			wall.setName(name);
		}
		if (StringUtils.isNotBlank(note)) {
			wall.setNote(note);
		}
		svWallDAO.update(wall);
	}

	@Override
	public void deleteWall(Long id) {
		// 删除电视墙下的通道
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("wallId", id);
		List<SvMonitor> list = svMonitorDAO.findByPropertys(params);
		for (SvMonitor monitor : list) {
			deleteMonitor(monitor.getId());
		}

		// 删除电视墙
		svWallDAO.deleteById(id);
	}

	@Override
	public List<SvWall> listWall(Integer start, Integer limit) {
		return svWallDAO.listWall(start, limit);
	}

	@Override
	public void updateWallScheme(Long id, Long wallId, String name,
			JSONArray monitors) {
		SvWallScheme ws = svWallSchemeDAO.get(id);
		if (null != wallId) {
			ws.setWallId(wallId);
		}
		if (null != name) {
			ws.setName(name);
		}
		svWallSchemeDAO.update(ws);

		svWallSchemeItemDAO.deleteItem(id);

		for (int i = 0; i < monitors.length(); i++) {
			JSONObject monitor = monitors.getJSONObject(i);
			SvWallSchemeItem item = new SvWallSchemeItem();
			item.setContent(monitor.getJSONArray("schemes") + "");
			item.setWallSchemeId(id);
			item.setMonitorId(monitor.getLong("monitorId"));
			svWallSchemeItemDAO.save(item);
		}
	}

	@Override
	public void deleteWallScheme(Long id) {
		// 删除item
		svWallSchemeItemDAO.deleteItem(id);

		svWallSchemeDAO.deleteById(id);

	}

	@Override
	public List<ListWallSchemeVO> listWallScheme(Long userGroupId) {
		List<ListWallSchemeVO> list = new ArrayList<ListWallSchemeVO>();
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userGroupId", userGroupId);
		List<SvWallScheme> wss = svWallSchemeDAO.findByPropertys(params);
		params.clear();
		for (SvWallScheme ws : wss) {
			ListWallSchemeVO vo = new ListWallSchemeVO();
			vo.setId(ws.getId());
			vo.setName(ws.getName());
			vo.setWallId(ws.getWallId());

			List<WallSchemeItemVO> voItems = new ArrayList<WallSchemeItemVO>();
			params.put("wallSchemeId", ws.getId());
			List<SvWallSchemeItem> items = svWallSchemeItemDAO
					.findByPropertys(params);
			params.clear();
			if (items.size() > 0) {
				for (SvWallSchemeItem item : items) {
					WallSchemeItemVO voItem = vo.new WallSchemeItemVO();
					voItem.setMonitorId(item.getMonitorId());
					voItem.setSchemes(new JSONArray(item.getContent()));
					voItems.add(voItem);
				}
			}
			vo.setMonitors(voItems);
			list.add(vo);
		}
		return list;
	}

	@Override
	public Integer countWall() {
		return svWallDAO.countWall();
	}

	@Override
	public List<ListWallMonitorVO> listWallMonitor() {
		List<ListWallMonitorVO> list = new ArrayList<ListWallMonitorVO>();
		List<SvWall> walls = svWallDAO.list();
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		for (SvWall wall : walls) {
			ListWallMonitorVO vo = new ListWallMonitorVO();
			vo.setId(wall.getId());
			vo.setName(wall.getName());
			vo.setNote(wall.getNote());
			params.put("wallId", wall.getId());
			List<SvMonitor> monitors = svMonitorDAO.findByPropertys(params);
			List<SvMonitorVO> monitorvos = new ArrayList<SvMonitorVO>();
			for (SvMonitor monitor : monitors) {
				ListWallMonitorVO.SvMonitorVO monitorvo = vo.new SvMonitorVO();
				monitorvo.setChannelNumber(monitor.getChannelNumber());
				monitorvo.setDwsId(monitor.getDwsId());
				monitorvo.setDwsStandardNumber(serverDAO
						.get(monitor.getDwsId()).getStandardNumber());
				monitorvo.setHeight(monitor.getHeight());
				monitorvo.setId(monitor.getId());
				monitorvo.setName(monitor.getName());
				monitorvo.setStandardNumber(monitor.getStandardNumber());
				monitorvo.setWallId(monitor.getWallId());
				monitorvo.setWidth(monitor.getWidth());
				monitorvo.setX(monitor.getX());
				monitorvo.setY(monitor.getY());
				monitorvos.add(monitorvo);
			}
			params.clear();
			vo.setMonitors(monitorvos);
			list.add(vo);
		}
		return list;
	}

	@Override
	public void editWallLayout(JSONArray monitors) {
		for (int i = 0; i < monitors.length(); i++) {
			JSONObject json = monitors.getJSONObject(i);
			Long id = json.getLong("id");
			SvMonitor sm = svMonitorDAO.get(id);
			sm.setX(json.getString("x"));
			sm.setY(json.getString("y"));
			sm.setWidth(json.getString("width"));
			sm.setHeight(json.getString("height"));
			svMonitorDAO.update(sm);
		}
	}
}
