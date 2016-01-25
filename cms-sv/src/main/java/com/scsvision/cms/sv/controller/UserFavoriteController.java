package com.scsvision.cms.sv.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.sv.dto.ListUserFavoriteDTO;
import com.scsvision.cms.sv.dto.ListUserFavoriteDTO.DeviceFavorite;
import com.scsvision.cms.sv.manager.UserFavoriteManager;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.interceptor.SessionCheckInterceptor;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.UserFavorite;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.SvDeviceManager;

/**
 * UserFavoriteController
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 下午1:47:42
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(SessionCheckInterceptor.class)
public class UserFavoriteController {
	@EJB(beanName = "UserFavoriteManagerImpl")
	private UserFavoriteManager userFavoriteManager;
	@EJB(beanName = "SvDeviceManagerImpl")
	private SvDeviceManager svDeviceManager;
	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;

	@Logon
	public Object createFavoriteJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject obj = new JSONObject(json);
		String name = obj.getString("name");
		String note = obj.getString("note");
		JSONArray ids = obj.getJSONArray("ids");
		List<Long> list = new LinkedList<Long>();
		for (int i = 0; i < ids.length(); i++) {
			VirtualOrgan vo = organManager.getVOrgan(ids.getLong(i));
			list.add(vo.getDeviceId());
		}
		Long userId = reader.getUserId();
		Long id = userFavoriteManager.createFavorite(name, note, userId, list);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(id.toString());
		return dto;
	}

	@Logon
	public Object updateFavoriteJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject obj = new JSONObject(json);
		String name = obj.getString("name");
		String note = obj.getString("note");
		Long id = obj.getLong("id");
		JSONArray ids = obj.getJSONArray("ids");
		List<Long> list = new LinkedList<Long>();
		for (int i = 0; i < ids.length(); i++) {
			VirtualOrgan vo = organManager.getVOrgan(ids.getLong(i));
			list.add(vo.getDeviceId());
		}
		userFavoriteManager.updateFavorite(name, note, id, list);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteFavoriteJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject obj = new JSONObject(json);
		JSONArray ids = obj.getJSONArray("ids");
		for (int i = 0; i < ids.length(); i++) {
			userFavoriteManager.deleteFavorite(ids.getLong(i));
		}
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	@Logon
	public Object listFavoriteJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();
		List<UserFavorite> list = userFavoriteManager.listUserFavorite(userId);
		Map<UserFavorite, Map<Long, SvDevice>> map = new HashMap<UserFavorite, Map<Long, SvDevice>>();
		for (UserFavorite entity : list) {
			List<Long> deviceIds = userFavoriteManager.listDeviceIds(entity
					.getId());
			if (deviceIds.size() > 0) {
				Map<Long, SvDevice> deviceMap = svDeviceManager
						.mapSvDevice(deviceIds);
				map.put(entity, deviceMap);
			} else {
				map.put(entity, null);
			}
		}
		ListUserFavoriteDTO dto = new ListUserFavoriteDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		List<DeviceFavorite> resultlist = new LinkedList<ListUserFavoriteDTO.DeviceFavorite>();
		for (Entry<UserFavorite, Map<Long, SvDevice>> entry : map.entrySet()) {
			DeviceFavorite df = dto.new DeviceFavorite();
			df.setId(entry.getKey().getId());
			df.setName(entry.getKey().getFavoriteName());
			List<SvDevice> deviceList = new LinkedList<SvDevice>();
			if (null != entry.getValue()) {
				for (Entry<Long, SvDevice> device : entry.getValue().entrySet()) {
					deviceList.add(device.getValue());
				}
				df.setChannelList(deviceList);
			} else {
				df.setChannelList(null);
			}
			resultlist.add(df);
		}
		dto.setFavoriteList(resultlist);
		return dto;
	}
}
