package com.scsvision.cms.duty.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.duty.dto.DutyUserDTO;
import com.scsvision.cms.duty.manager.DutyRecordManager;
import com.scsvision.cms.duty.manager.DutyUserManager;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.interceptor.SessionCheckInterceptor;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.DutyRecord;
import com.scsvision.database.entity.DutyUser;
import com.scsvision.database.manager.OrganManager;

/**
 * DutyController
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:30:30
 */
@Stateless
@Interceptors(SessionCheckInterceptor.class)
public class DutyController {
	@EJB(beanName = "DutyRecordManagerImpl")
	private DutyRecordManager dutyRecordManager;
	@EJB(beanName = "DutyUserManagerImpl")
	private DutyUserManager dutyUserManager;
	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;

	private final Logger logger = LoggerFactory.getLogger(DutyController.class);

	public Object dutyLoginJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Short action = reader.getShort("action", false);
		Long dutyUserId = reader.getLong("dutyUserId", false);

		DutyUser dutyUser = dutyUserManager.getDutyUser(dutyUserId);
		if (null == dutyUser) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"DutyUser id[" + dutyUserId + "] not found !");
		}
		dutyUserManager.dutyLogin(dutyUser, action);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object createDutyUserJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String loginName = reader.getString("loginName", true);
		String password = reader.getString("password", true);
		String name = reader.getString("name", false);
		Short status = reader.getShort("status", false);
		String phone = reader.getString("phone", true);
		Long organId = reader.getLong("organId", false);
		DutyUser dutyUser = new DutyUser();
		dutyUser.setLoginName(loginName);
		dutyUser.setStatus(status);
		dutyUser.setPhone(phone);
		dutyUser.setName(name);
		dutyUser.setPassword(password);
		dutyUser.setOrganId(organId);
		Long id = dutyUserManager.createDutyUser(dutyUser);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(id + "");
		return dto;
	}

	public Object updateDutyUserJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		String loginName = reader.getString("loginName", true);
		String password = reader.getString("password", true);
		String name = reader.getString("name", true);
		Short status = reader.getShort("status", true);
		String phone = reader.getString("phone", true);
		Long organId = reader.getLong("organId", true);
		DutyUser dutyUser = dutyUserManager.getDutyUser(id);
		if (StringUtils.isNotBlank(loginName)) {
			dutyUser.setLoginName(loginName);
		}
		if (null != status) {
			dutyUser.setStatus(status);
		}
		if (StringUtils.isNotBlank(name)) {
			dutyUser.setName(name);
		}
		if (StringUtils.isNotBlank(password)) {
			dutyUser.setPassword(password);
		}
		if (StringUtils.isNotBlank(phone)) {
			dutyUser.setPhone(phone);
		}
		if (null != organId) {
			dutyUser.setOrganId(organId);
		}
		dutyUserManager.updateDutyUser(dutyUser);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteDutyUserJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject object = new JSONObject(json);
		JSONArray ids = object.getJSONArray("ids");
		for (int i = 0; i < ids.length(); i++) {
			dutyUserManager.deleteDutyUser(ids.getLong(i));
		}
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object dutyUserList(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("organId", true);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 20;
		}
		List<DutyUser> list = dutyUserManager.dutyUserList(organId, start,
				limit);
		Integer count = dutyUserManager.dutyUserCount(organId);
		DutyUserDTO dto = new DutyUserDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(count + "");

		List<DutyUserDTO.DutyUser> dutyUserlist = new ArrayList<DutyUserDTO.DutyUser>();
		for (DutyUser entity : list) {
			DutyUserDTO.DutyUser dutyUser = dto.new DutyUser();
			dutyUser.setId(entity.getId());
			dutyUser.setName(entity.getName());
			dutyUser.setPhone(entity.getPhone());
			dutyUser.setStatus(entity.getStatus());
			dutyUser.setOrganId(entity.getOrganId());
			dutyUser.setLoginName(entity.getLoginName());
			dutyUser.setPassword(entity.getPassword());
			dutyUserlist.add(dutyUser);
		}
		dto.setList(dutyUserlist);
		return dto;
	}

	@Logon
	public Object listDutyUserJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();
		List<DutyUser> list = dutyUserManager.listDutyUser(userId);
		DutyUserDTO dto = new DutyUserDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		List<DutyUserDTO.DutyUser> dutyUserlist = new ArrayList<DutyUserDTO.DutyUser>();
		for (DutyUser entity : list) {
			DutyUserDTO.DutyUser dutyUser = dto.new DutyUser();
			dutyUser.setId(entity.getId());
			dutyUser.setName(entity.getName());
			dutyUser.setPhone(entity.getPhone());
			dutyUser.setStatus(entity.getStatus());
			dutyUser.setOrganId(entity.getOrganId());
			dutyUser.setOrganName(organManager.getOrganById(entity.getOrganId()) != null ? organManager
					.getOrganById(entity.getOrganId()).getName() : "");
			dutyUserlist.add(dutyUser);
		}
		dto.setList(dutyUserlist);
		return dto;
	}
}
