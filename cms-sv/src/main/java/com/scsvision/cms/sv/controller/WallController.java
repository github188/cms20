/**
 * 
 */
package com.scsvision.cms.sv.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.sv.dto.ListMonitorDTO;
import com.scsvision.cms.sv.dto.ListWallDTO;
import com.scsvision.cms.sv.dto.ListWallMonitorDTO;
import com.scsvision.cms.sv.dto.ListWallSchemeDTO;
import com.scsvision.cms.sv.manager.WallManager;
import com.scsvision.cms.sv.vo.ListWallMonitorVO;
import com.scsvision.cms.sv.vo.ListWallSchemeVO;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.interceptor.SessionCheckInterceptor;
import com.scsvision.cms.util.number.NumberUtil;
import com.scsvision.cms.util.request.JsonRequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.database.entity.SvMonitor;
import com.scsvision.database.entity.SvWall;
import com.scsvision.database.entity.User;
import com.scsvision.database.manager.UserManager;

/**
 * @author wangbinyu
 *
 */
@Stateless
@Interceptors(SessionCheckInterceptor.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WallController {

	@EJB(beanName = "WallManagerImpl")
	private WallManager wallManager;

	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;

	public Object createWallJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", true);
		String note = reader.getString("note", true);

		Long id = wallManager.createWall(name, note);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(id + "");
		return dto;
	}

	public Object updateWallJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		String name = reader.getString("name", true);
		String note = reader.getString("note", true);

		wallManager.updateWall(id, name, note);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteWallJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);

		wallManager.deleteWall(id);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object listWallJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}
		Integer count = wallManager.countWall();
		List<SvWall> list = wallManager.listWall(start, limit);

		ListWallDTO dto = new ListWallDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setWalls(list);
		dto.setMessage(count + "");
		return dto;
	}

	public Object createMonitorJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		String standardNumber = reader.getString("standardNumber", true);
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = NumberUtil.randomStandardNumber();
		}
		String channelNumber = reader.getString("channelNumber", false);
		String x = reader.getString("x", true);
		String y = reader.getString("y", true);
		String width = reader.getString("width", true);
		String height = reader.getString("height", true);
		Long dwsId = reader.getLong("dwsId", false);
		Long wallId = reader.getLong("wallId", true);

		Long id = wallManager.createMonitor(name, standardNumber,
				channelNumber, x, y, width, height, dwsId, wallId);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(id + "");
		return dto;
	}

	public Object updateMonitorJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		String name = reader.getString("name", true);
		String standardNumber = reader.getString("standardNumber", true);
		String channelNumber = reader.getString("channelNumber", false);
		String x = reader.getString("x", true);
		String y = reader.getString("y", true);
		String width = reader.getString("width", true);
		String height = reader.getString("height", true);
		Long dwsId = reader.getLong("dwsId", true);
		Long wallId = reader.getLong("wallId", true);

		wallManager.updateMonitor(id, name, standardNumber, channelNumber, x,
				y, width, height, dwsId, wallId);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteMonitorJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);

		wallManager.deleteMonitor(id);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object listMonitorJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long wallId = reader.getLong("wallId", false);

		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}
		Integer count = wallManager.countMonitor(wallId);
		List<SvMonitor> list = wallManager.listMonitor(wallId, start, limit);
		ListMonitorDTO dto = new ListMonitorDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMonitors(list);
		dto.setMessage(count + "");
		return dto;
	}

	@Logon
	public Object createWallSchemeJson(HttpServletRequest request) {
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		User user = userManager.getUser((Long) request.getSession()
				.getAttribute("userId"));

		JsonRequestReader reader = new JsonRequestReader(request);
		JSONObject jsonParam = reader.getJsonParam();
		Long wallId = JsonRequestReader.getLong(jsonParam, "wallId");
		String name = JsonRequestReader.getString(jsonParam, "name");
		JSONArray monitors = jsonParam.getJSONArray("monitors");
		Long id = wallManager.createWallScheme(wallId, name,
				user.getUserGroupId(), monitors);
		dto.setMessage(id + "");
		return dto;
	}

	public Object updateWallSchemeJson(HttpServletRequest request) {
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		JsonRequestReader reader = new JsonRequestReader(request);
		JSONObject jsonParam = reader.getJsonParam();
		Long id = JsonRequestReader.getLong(jsonParam, "id");
		Long wallId = JsonRequestReader.getLong(jsonParam, "wallId");
		String name = JsonRequestReader.getString(jsonParam, "name");
		JSONArray monitors = jsonParam.getJSONArray("monitors");

		wallManager.updateWallScheme(id, wallId, name, monitors);

		return dto;
	}

	public Object deleteWallSchemeJson(HttpServletRequest request) {
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);

		wallManager.deleteWallScheme(id);

		return dto;
	}

	public Object listWallSchemeJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		User user = userManager.getUser(reader.getUserId());
		List<ListWallSchemeVO> list = wallManager.listWallScheme(user
				.getUserGroupId());

		ListWallSchemeDTO dto = new ListWallSchemeDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setList(list);

		return JSONObject.wrap(dto).toString();

	}

	public Object listWallMonitorJson(HttpServletRequest request) {
		List<ListWallMonitorVO> list = wallManager.listWallMonitor();
		ListWallMonitorDTO dto = new ListWallMonitorDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setWalls(list);

		return dto;
	}

	public Object editWallLayoutJson(HttpServletRequest request) {
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		JsonRequestReader reader = new JsonRequestReader(request);
		JSONObject jsonParam = reader.getJsonParam();
		JSONArray monitors = jsonParam.getJSONArray("monitors");
		wallManager.editWallLayout(monitors);
		return dto;
	}
}
