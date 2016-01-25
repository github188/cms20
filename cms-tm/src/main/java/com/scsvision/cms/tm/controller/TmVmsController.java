/**
 * 
 */
package com.scsvision.cms.tm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.tm.dto.CmsDTO;
import com.scsvision.cms.tm.dto.GetPlayListDTO;
import com.scsvision.cms.tm.dto.ListCMSPubItemDTO;
import com.scsvision.cms.tm.dto.TmCmsPublishDTO;
import com.scsvision.cms.tm.manager.TmVmsManager;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.database.entity.TmCmsPublish;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.TmPlayList;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.TmDeviceManager;
import com.scsvision.database.manager.UserManager;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class TmVmsController {
	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;

	@EJB(beanName = "TmVmsManagerImpl")
	private TmVmsManager tmVmsManager;
	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;
	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;

	/**
	 * 查询上级机构如果屏蔽继续查询上级
	 * 
	 * @param vd
	 *            车检器
	 * @param vorganMap
	 *            机构map
	 * @param paths
	 *            车检器path
	 * @param i
	 * @return 机构id
	 */
	private Long queryParentOrgan(VirtualOrgan vd,
			Map<Long, VirtualOrgan> vorganMap, List<String> paths, int i) {
		if (vorganMap.get(Long.parseLong(paths.get(i - 1))) == null) {
			i--;
			return queryParentOrgan(vd, vorganMap, paths, i);
		} else {
			return Long.parseLong(paths.get(i - 1));
		}
	}

	public Object saveTmCmsPublishJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject jsonObj = new JSONObject(json);
		List<TmCmsPublish> list = new ArrayList<TmCmsPublish>();
		if (null != jsonObj) {
			String playlistId = jsonObj.getString("playlistId");
			// 根据节目单id删除常用信息避免累加
			tmVmsManager.deleteCmsPublish(Long.parseLong(playlistId));
			// 解析常用数据json数组
			JSONArray array = jsonObj.getJSONArray("list");
			for (int i = 0; i < array.length(); i++) {
				TmCmsPublish entity = new TmCmsPublish();
				JSONObject readerjson = array.getJSONObject(i);
				entity.setContent(readerjson.getString("content"));
				entity.setFont(StringUtils.isNotBlank(readerjson
						.getString("font")) ? Integer.valueOf(readerjson
						.getString("font")) : null);
				entity.setPlaySize(StringUtils.isNotBlank(readerjson
						.getString("playSize")) ? Integer.valueOf(readerjson
						.getString("playSize")) : null);
				entity.setDuration(StringUtils.isNotBlank(readerjson
						.getString("duration")) ? Integer.valueOf(readerjson
						.getString("duration")) : null);
				entity.setColor(readerjson.getString("color"));
				entity.setSpace(StringUtils.isNotBlank(readerjson
						.getString("space")) ? Integer.valueOf(readerjson
						.getString("space")) : null);
				entity.setRowSize(StringUtils.isNotBlank(readerjson
						.getString("rowSize")) ? Integer.valueOf(readerjson
						.getString("rowSize")) : null);
				entity.setMarginLeft(StringUtils.isNotBlank(readerjson
						.getString("marginLeft")) ? Integer.valueOf(readerjson
						.getString("marginLeft")) : null);
				entity.setMarginTop(StringUtils.isNotBlank(readerjson
						.getString("marginTop")) ? Integer.valueOf(readerjson
						.getString("marginTop")) : null);
				entity.setBackgroundColor(readerjson
						.getString("backgroundColor"));
				entity.setPlaylistId(Long.parseLong(playlistId));
				// 标语类型 0-施工标语 1-交通提示
				entity.setType(StringUtils.isNotBlank(readerjson
						.getString("type")) ? Integer.valueOf(readerjson
						.getString("type")) : null);
				list.add(entity);
			}
		}

		if (list.size() > 0) {
			tmVmsManager.batchTmCmsPublish(list);
		}
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object updateTmCmsPublishJson(HttpServletRequest request) {
		String json = request.getParameter("json");
		JSONObject reader = new JSONObject(json);
		String id = reader.getString("id");
		String content = reader.getString("content");
		String font = reader.getString("font");
		String playSize = reader.getString("playSize");
		String duration = reader.getString("duration");
		String color = reader.getString("color");
		String space = reader.getString("space");
		String rowSize = reader.getString("rowSize");
		String marginLeft = reader.getString("marginLeft");
		String marginTop = reader.getString("marginTop");
		String backgroundColor = reader.getString("backgroundColor");
		String type = reader.getString("type");
		TmCmsPublish entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = tmVmsManager.getTmCmsPublish(Long.valueOf(id));
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [" + id + "]");
		}
		if (StringUtils.isNotBlank(content)) {
			entity.setContent(content);
		}
		if (StringUtils.isNotBlank(font)) {
			entity.setFont(Integer.valueOf(font));
		}
		if (StringUtils.isNotBlank(playSize)) {
			entity.setPlaySize(Integer.valueOf(playSize));
		}
		if (StringUtils.isNotBlank(duration)) {
			entity.setDuration(Integer.valueOf(duration));
		}
		if (StringUtils.isNotBlank(color)) {
			entity.setColor(color);
		}
		if (StringUtils.isNotBlank(space)) {
			entity.setSpace(Integer.valueOf(space));
		}
		if (StringUtils.isNotBlank(rowSize)) {
			entity.setRowSize(Integer.valueOf(rowSize));
		}
		if (StringUtils.isNotBlank(marginLeft)) {
			entity.setMarginLeft(Integer.valueOf(marginLeft));
		}
		if (StringUtils.isNotBlank(marginTop)) {
			entity.setMarginTop(Integer.valueOf(marginTop));
		}
		if (StringUtils.isNotBlank(backgroundColor)) {
			entity.setBackgroundColor(backgroundColor);
		}
		if (StringUtils.isNotBlank(type)) {
			entity.setType(Integer.valueOf(type));
		}
		tmVmsManager.update(entity);
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteTmCmsPublishJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject jsonObj = new JSONObject(json);
		JSONArray array = jsonObj.getJSONArray("ids");
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < array.length(); i++) {
			ids.add(Long.parseLong(array.get(i).toString()));
		}
		tmVmsManager.delete(ids);
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object listCmsPublishJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long playlistId = reader.getLong("playlistId", true);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}
		List<TmCmsPublish> list = tmVmsManager.listCmsPublish(start, limit,
				playlistId);
		TmCmsPublishDTO dto = new TmCmsPublishDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<TmCmsPublishDTO.TmCmsPublish> dtoList = new LinkedList<TmCmsPublishDTO.TmCmsPublish>();
		for (TmCmsPublish e : list) {
			TmCmsPublishDTO.TmCmsPublish tmCmsPublish = dto.new TmCmsPublish();
			tmCmsPublish.setBackgroundColor(e.getBackgroundColor());
			tmCmsPublish.setColor(e.getColor());
			tmCmsPublish.setContent(e.getContent());
			tmCmsPublish.setDuration(e.getDuration() != null ? e.getDuration()
					+ "" : "");
			tmCmsPublish.setFont(e.getFont() != null ? e.getFont() + "" : "");
			tmCmsPublish
					.setSpace(e.getSpace() != null ? e.getSpace() + "" : "");
			tmCmsPublish.setId(e.getId() != null ? e.getId() + "" : "");
			tmCmsPublish.setRowSize(e.getRowSize() != null ? e.getRowSize()
					+ "" : "");
			tmCmsPublish.setPlaySize(e.getPlaySize() != null ? e.getPlaySize()
					+ "" : "");
			tmCmsPublish.setMarginLeft(e.getMarginLeft() != null ? e
					.getMarginLeft() + "" : "");
			tmCmsPublish.setMarginTop(e.getMarginTop() != null ? e
					.getMarginTop() + "" : "");
			tmCmsPublish.setType(e.getType() != null ? e.getType() + "" : "");
			dtoList.add(tmCmsPublish);
		}
		dto.setVms(dtoList);
		return dto;
	}

	public Object getCmsPublishDetailJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		TmCmsPublish entity = tmVmsManager.getCmsPublishDetail(id);
		TmCmsPublishDTO dto = new TmCmsPublishDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		TmCmsPublishDTO.TmCmsPublish tmCmsPublish = dto.new TmCmsPublish();
		tmCmsPublish.setBackgroundColor(entity.getBackgroundColor());
		tmCmsPublish.setColor(entity.getColor());
		tmCmsPublish.setContent(entity.getContent());
		tmCmsPublish.setDuration(entity.getDuration() != null ? entity
				.getDuration() + "" : "");
		tmCmsPublish.setFont(entity.getFont() != null ? entity.getFont() + ""
				: "");
		tmCmsPublish.setSpace(entity.getSpace() != null ? entity.getSpace()
				+ "" : "");
		tmCmsPublish.setId(entity.getId() != null ? entity.getId() + "" : "");
		tmCmsPublish.setRowSize(entity.getRowSize() != null ? entity
				.getRowSize() + "" : "");
		tmCmsPublish.setPlaySize(entity.getPlaySize() != null ? entity
				.getPlaySize() + "" : "");
		tmCmsPublish.setMarginLeft(entity.getMarginLeft() != null ? entity
				.getMarginLeft() + "" : "");
		tmCmsPublish.setMarginTop(entity.getMarginTop() != null ? entity
				.getMarginTop() + "" : "");
		tmCmsPublish.setType(entity.getType() != null ? entity.getType() + ""
				: "");
		dto.setTmCmsPublish(tmCmsPublish);
		return dto;
	}

	public Object listCMSJson(HttpServletRequest request) {
		Map<Long, TmDevice> map = tmDeviceManager.mapTmDevice(null);
		List<TmDevice> list = new LinkedList<TmDevice>();
		for (Entry<Long, TmDevice> entry : map.entrySet()) {
			String deviceType = entry.getValue().getType()
					+ entry.getValue().getSubType();
			if (Integer.valueOf(deviceType) >= TypeDefinition.VMS_DOOR
					&& Integer.valueOf(deviceType) < TypeDefinition.VEHICLE_DETECTOR_MICRO) {
				list.add(entry.getValue());
			}
		}
		Map<String, List<TmDevice>> mapDevice = new HashMap<String, List<TmDevice>>();
		for (int i = 0; i < list.size(); i++) {
			if (mapDevice.get(list.get(i).getExtend()) == null) {
				List<TmDevice> tempList = new LinkedList<TmDevice>();
				TmDevice tm = list.get(i);
				tempList.add(tm);
				for (int j = i + 1; j < list.size(); j++) {
					if (tm.getExtend().equals(list.get(j).getExtend())) {
						tempList.add(list.get(j));
					}
				}
				mapDevice.put(tm.getExtend(), tempList);
			}
		}
		CmsDTO dto = new CmsDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<CmsDTO.Cms> dtoList = new LinkedList<CmsDTO.Cms>();
		for (Entry<String, List<TmDevice>> tmDmap : mapDevice.entrySet()) {
			CmsDTO.Cms cms = dto.new Cms();
			String[] types = tmDmap.getKey().split("::");
			String stype = types[0].split("=")[1] + "*"
					+ types[1].split("=")[1];
			cms.setStype(stype);
			List<CmsDTO.Cms.Children> childList = new LinkedList<CmsDTO.Cms.Children>();
			for (TmDevice t : tmDmap.getValue()) {
				CmsDTO.Cms.Children child = cms.new Children();
				child.setId(t.getId());
				child.setName(t.getName());
				child.setSn(t.getStandardNumber());
				child.setType(Integer.valueOf(t.getType() + t.getSubType()));
				childList.add(child);
			}
			cms.setList(childList);
			dtoList.add(cms);
		}
		dto.setList(dtoList);
		return dto;
	}

	public Object createPlayListJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		String sizeType = reader.getString("sizeType", false);

		Long id = tmVmsManager.createPlayList(name, sizeType);

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(id + "");
		dto.setMethod("CreatePlayList");
		return dto;
	}

	public Object updatePlayListJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		String name = reader.getString("name", true);
		String sizeType = reader.getString("sizeType", true);

		tmVmsManager.updatePlayListJson(id, name, sizeType);

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("UpdatePlayList");
		return dto;
	}

	public Object deletePlayListJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);

		tmVmsManager.deletePlayListJson(id);

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("DeletePlayList");
		return dto;
	}

	public Object getPlayListJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);

		TmPlayList playlist = tmVmsManager.getPlayListJson(id);

		GetPlayListDTO dto = new GetPlayListDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("GetPlayList");
		dto.setPlaylist(playlist);
		return dto;
	}

	public Object listCMSPubItemJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sizeType = reader.getString("sizeType", true);

		List<TmPlayList> list = tmVmsManager.listPlaylist(sizeType);
		ListCMSPubItemDTO dto = new ListCMSPubItemDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setList(list);
		dto.setMessage("");
		dto.setMethod("ListCMSPubItem");
		return dto;
	}

	public Object listStypeJson(HttpServletRequest request) {
		Map<Long, TmDevice> map = tmDeviceManager.mapTmDevice(null);
		List<TmDevice> list = new LinkedList<TmDevice>();
		for (Entry<Long, TmDevice> entry : map.entrySet()) {
			String deviceType = entry.getValue().getType()
					+ entry.getValue().getSubType();
			if (Integer.valueOf(deviceType) >= TypeDefinition.VMS_DOOR
					&& Integer.valueOf(deviceType) < TypeDefinition.VEHICLE_DETECTOR_MICRO) {
				list.add(entry.getValue());
			}
		}
		Map<String, List<TmDevice>> mapDevice = new HashMap<String, List<TmDevice>>();
		for (int i = 0; i < list.size(); i++) {
			if (mapDevice.get(list.get(i).getExtend()) == null) {
				TmDevice tm = list.get(i);
				mapDevice.put(tm.getExtend(), null);
			}
		}
		CmsDTO dto = new CmsDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<CmsDTO.Cms> dtoList = new LinkedList<CmsDTO.Cms>();
		for (Entry<String, List<TmDevice>> tmDmap : mapDevice.entrySet()) {
			CmsDTO.Cms cms = dto.new Cms();
			String[] types = tmDmap.getKey().split("::");
			String stype = types[0].split("=")[1] + "*"
					+ types[1].split("=")[1];
			cms.setStype(stype);
			dtoList.add(cms);
		}
		dto.setList(dtoList);
		return dto;

	}
}
