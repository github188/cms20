package com.scsvision.cms.common.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
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

import com.scsvision.cms.common.dto.ListUserDTO;
import com.scsvision.cms.common.dto.UserDTO;
import com.scsvision.cms.common.dto.UserGroupDTO;
import com.scsvision.cms.constant.Header;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.util.number.NumberUtil;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.User;
import com.scsvision.database.entity.UserGroup;
import com.scsvision.database.manager.UserGroupManager;
import com.scsvision.database.manager.UserManager;

/**
 * @author MIKE
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;
	@EJB(beanName = "UserGroupManagerImpl")
	private UserGroupManager userGroupManager;

	public Object createUserGroupJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		String note = reader.getString("note", true);
		UserGroup userGroup = new UserGroup();
		userGroup.setName(name);
		userGroup.setNote(note);
		Long id = userGroupManager.createUserGroup(userGroup);
		BaseDTO dto = new BaseDTO();
		dto.setMessage(id + "");
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object updateUserGroupJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		String name = reader.getString("name", true);
		String note = reader.getString("note", true);
		UserGroup userGroup = userGroupManager.getUserGroup(id);
		if (StringUtils.isNotBlank(name)) {
			userGroup.setName(name);
		}
		if (StringUtils.isNotBlank(note)) {
			userGroup.setNote(note);
		}
		userGroupManager.updateUserGroup(userGroup);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteUserGroupJson(HttpServletRequest request) {
		String json = request.getParameter("json");
		JSONArray array = new JSONArray(json);
		String[] ids = new String[array.length()];
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			ids[i] = obj.getString("id");
		}
		for (String id : ids) {
			userGroupManager.deleteUserGroup(Long.valueOf(id));
		}
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object listUserGroupJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", true);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}
		List<UserGroup> list = userGroupManager.listUserGroup(name, start,
				limit);
		Integer count = userGroupManager.countUserGroup(name);
		UserGroupDTO dto = new UserGroupDTO();
		dto.setMessage(count + "");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<UserGroup> dtoList = new LinkedList<UserGroup>();
		for (UserGroup ug : list) {
			UserGroup ugDto = new UserGroup();
			ugDto.setId(ug.getId());
			ugDto.setName(ug.getName());
			ugDto.setNote(ug.getNote());
			dtoList.add(ugDto);
		}
		dto.setList(dtoList);
		return dto;
	}

	public Object createUserJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", true);
		Long organId = reader.getLong("organId", false);
		String loginName = reader.getString("loginName", false);
		String standardNumber = reader.getString("standardNumber", true);
		String password = reader.getString("password", false);
		Long userGroupId = reader.getLong("userGroupId", true);
		Short tunnelPriv = reader.getShort("tunnelPriv", true);
		Short eventPriv = reader.getShort("eventPriv", true);
		Short statisticPriv = reader.getShort("statisticPriv", true);
		Short roadPriv = reader.getShort("roadPriv", true);
		Short videoPriv = reader.getShort("videoPriv", true);
		Short workPriv = reader.getShort("workPriv", true);
		Short alarmPriv = reader.getShort("alarmPriv", true);
		Short infoPriv = reader.getShort("infoPriv", true);
		Short systemPriv = reader.getShort("systemPriv", true);
		Short adminPriv = reader.getShort("adminPriv", true);

		// 如果sn为空生成32位随机数
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = NumberUtil.randomStandardNumber();
		}
		User user = new User();
		user.setAdminPriv(adminPriv);
		user.setAlarmPriv(alarmPriv);
		user.setEventPriv(eventPriv);
		user.setInfoPriv(infoPriv);
		user.setOrganId(organId);
		user.setLogonName(loginName);
		user.setName(name);
		user.setPassword(password);
		user.setRoadPriv(roadPriv);
		user.setStandardNumber(standardNumber);
		user.setStatisticPriv(statisticPriv);
		user.setSystemPriv(systemPriv);
		user.setTunnelPriv(tunnelPriv);
		user.setUserGroupId(userGroupId);
		user.setViedoPriv(videoPriv);
		user.setWorkPriv(workPriv);
		User result = userManager.createUser(user);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(result.getId() + "::" + result.getStandardNumber());
		return dto;

	}

	public Object updateUserJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", true);
		String name = reader.getString("name", true);
		Long organId = reader.getLong("organId", true);
		String loginName = reader.getString("loginName", true);
		String standardNumber = reader.getString("standardNumber", true);
		String password = reader.getString("password", true);
		Long userGroupId = reader.getLong("userGroupId", true);
		Short tunnelPriv = reader.getShort("tunnelPriv", true);
		Short eventPriv = reader.getShort("eventPriv", true);
		Short statisticPriv = reader.getShort("statisticPriv", true);
		Short roadPriv = reader.getShort("roadPriv", true);
		Short videoPriv = reader.getShort("videoPriv", true);
		Short workPriv = reader.getShort("workPriv", true);
		Short alarmPriv = reader.getShort("alarmPriv", true);
		Short infoPriv = reader.getShort("infoPriv", true);
		Short systemPriv = reader.getShort("systemPriv", true);
		Short adminPriv = reader.getShort("adminPriv", true);
		User user = userManager.getUser(id);
		if (StringUtils.isNotBlank(name)) {
			user.setName(name);
		}
		if (StringUtils.isNotBlank(loginName)) {
			user.setLogonName(loginName);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			user.setStandardNumber(standardNumber);
		}
		if (StringUtils.isNotBlank(password)) {
			user.setPassword(password);
		}
		if (null != userGroupId) {
			user.setUserGroupId(userGroupId);
		}
		if (null != organId) {
			user.setOrganId(organId);
		}
		if (null != tunnelPriv) {
			user.setTunnelPriv(tunnelPriv);
		}
		if (null != eventPriv) {
			user.setEventPriv(eventPriv);
		}
		if (null != statisticPriv) {
			user.setStatisticPriv(statisticPriv);
		}
		if (null != roadPriv) {
			user.setRoadPriv(roadPriv);
		}
		if (null != videoPriv) {
			user.setViedoPriv(videoPriv);
		}
		if (null != workPriv) {
			user.setWorkPriv(workPriv);
		}
		if (null != alarmPriv) {
			user.setAlarmPriv(alarmPriv);
		}
		if (null != infoPriv) {
			user.setInfoPriv(infoPriv);
		}
		if (null != systemPriv) {
			user.setSystemPriv(systemPriv);
		}
		if (null != adminPriv) {
			user.setAdminPriv(adminPriv);
		}
		userManager.updateUser(user);

		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object getUserJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", true);
		User user = userManager.getUser(id);

		UserDTO dto = new UserDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		UserDTO.User userdt = dto.new User();
		userdt.setId(user.getId());
		userdt.setName(user.getName());
		userdt.setOrganId(user.getOrganId());
		userdt.setLoginName(user.getLogonName());
		userdt.setPassword(user.getPassword());
		userdt.setStandardNumber(user.getStandardNumber());
		userdt.setUserGroupId(user.getUserGroupId());
		userdt.setTunnelPriv(user.getTunnelPriv());
		userdt.setEventPriv(user.getEventPriv());
		userdt.setStatisticPriv(user.getStatisticPriv());
		userdt.setRoadPriv(user.getRoadPriv());
		userdt.setViedoPriv(user.getViedoPriv());
		userdt.setWorkPriv(user.getWorkPriv());
		userdt.setAlarmPriv(user.getAlarmPriv());
		userdt.setAdminPriv(user.getAdminPriv());
		userdt.setInfoPriv(user.getInfoPriv());
		userdt.setSystemPriv(user.getSystemPriv());
		dto.setUser(userdt);
		return dto;
	}

	public Object listUserJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String loginName = reader.getString("loginName", true);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}
		Integer countUser = userManager.countUser(loginName, start, limit);
		List<User> users = userManager.listUser(loginName, start, limit);
		ListUserDTO dto = new ListUserDTO();
		//获取用户组以id为key，UserGroup为value
		Map<Long, UserGroup> map = userGroupManager.getMapUserGroup();
		List<ListUserDTO.User> userdts = new ArrayList<ListUserDTO.User>();
		for (User user : users) {
			ListUserDTO.User userdt = dto.new User();
			userdt.setId(user.getId());
			userdt.setName(user.getName());
			userdt.setOrganId(user.getOrganId());
			userdt.setOrganName(map.get(user.getId()).getName());
			userdt.setLoginName(user.getLogonName());
			userdt.setPassword(user.getPassword());
			userdt.setStandardNumber(user.getStandardNumber());
			userdt.setUserGroupId(user.getUserGroupId());
			userdt.setTunnelPriv(user.getTunnelPriv());
			userdt.setEventPriv(user.getEventPriv());
			userdt.setStatisticPriv(user.getStatisticPriv());
			userdt.setRoadPriv(user.getRoadPriv());
			userdt.setViedoPriv(user.getViedoPriv());
			userdt.setWorkPriv(user.getWorkPriv());
			userdt.setAlarmPriv(user.getAlarmPriv());
			userdt.setAdminPriv(user.getAdminPriv());
			userdt.setInfoPriv(user.getInfoPriv());
			userdt.setSystemPriv(user.getSystemPriv());
			userdts.add(userdt);
		}
		dto.setList(userdts);
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(countUser + "");
		return dto;
	}

	public Object deleteUserJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject object = new JSONObject(json);
		JSONArray ids = object.getJSONArray("ids");
		for (int i = 0; i < ids.length(); i++) {
			userManager.deleteUser(ids.getLong(i));
			;
		}
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object getUserNameJson(HttpServletRequest request) {
		String userName = request.getSession().getAttribute("userName") + "";
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(userName);
		dto.setMethod("GetUserName");
		return dto;
	}

	public Object getUserTicketJson(HttpServletRequest request) {
		Object userTicket = request.getSession().getAttribute("ticket");
		if(null != userTicket){
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(userTicket+"");
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
		}else{
			throw new BusinessException(ErrorCode.USER_SESSION_EXPIRED,"user session timeOut!");
		}
	}
}
