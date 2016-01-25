package com.scsvision.cms.sv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.sv.dto.ListCameraByStakeNumberDTO;
import com.scsvision.cms.sv.dto.ListPresetDTO;
import com.scsvision.cms.sv.dto.ListSvOrganTreeDTO;
import com.scsvision.cms.sv.dto.ListSvOrganTreeDTO.ListSvOrganTree;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.cache.SpyMemcacheUtil;
import com.scsvision.cms.util.interceptor.SessionCheckInterceptor;
import com.scsvision.cms.util.number.NumberUtil;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.DicManufacture;
import com.scsvision.database.entity.Server;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.SvPreset;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.DicManufactureManager;
import com.scsvision.database.manager.ErrorMsgManager;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.PresetManager;
import com.scsvision.database.manager.ServerManager;
import com.scsvision.database.manager.SvDeviceManager;
import com.scsvision.database.manager.UserManager;

@Stateless
@Interceptors(SessionCheckInterceptor.class)
public class SvDeviceController {
	private final Logger logger = LoggerFactory
			.getLogger(SvDeviceController.class);

	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;

	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;

	@EJB(beanName = "SvDeviceManagerImpl")
	private SvDeviceManager svDeviceManager;

	@EJB(beanName = "DicManufactureManagerImpl")
	private DicManufactureManager dicManufactureManager;

	@EJB(beanName = "ServerManagerImpl")
	private ServerManager serverManager;

	@EJB(beanName = "PresetManagerImpl")
	private PresetManager presetManager;

	@EJB(beanName = "ErrorMsgManagerImpl")
	private ErrorMsgManager errorMsgManager;

	public Object saveSvMarkersJson(HttpServletRequest request) {
		String json = request.getParameter("json");
		HashMap<String, String> map = new HashMap<String, String>();
		JSONArray array = new JSONArray(json);
		String[] ids = new String[array.length()];
		String[] types = new String[array.length()];
		String[] latitudes = new String[array.length()];
		String[] longitudes = new String[array.length()];
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			ids[i] = obj.getString("id");
			types[i] = obj.getString("type");
			latitudes[i] = obj.getString("latitude");
			longitudes[i] = obj.getString("longitude");
			map.put(ids[i] + "-" + types[i], latitudes[i] + "-" + longitudes[i]);
		}
		List<Long> idList = new LinkedList<Long>();
		Iterator iters = map.keySet().iterator();
		while (iters.hasNext()) {
			String key = iters.next().toString(); // 拿到键

			if (Integer.valueOf(key.split("-")[1]) >= TypeDefinition.VIDEO_DVR
					&& Integer.valueOf(key.split("-")[1]) < TypeDefinition.VMS_DOOR) {
				idList.add(Long.valueOf(key.split("-")[0]));
			}
		}
		Map<Long, VirtualOrgan> vorganMap = organManager.mapVOrgan(idList);

		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			// 拿到键
			String key = iter.next().toString();
			// 拿到值
			String val = map.get(key).toString();
			SvDevice device = svDeviceManager.getSvDevice(vorganMap.get(
					Long.valueOf(key.split("-")[0])).getDeviceId());
			if (!val.equals("-")) {
				if (val.endsWith("-")) {
					device.setLatitude(val.split("-")[0]);
				} else if (val.startsWith("-")) {
					device.setLongitude(val.split("-")[1]);
				} else {
					device.setLatitude(val.split("-")[0]);
					device.setLongitude(val.split("-")[1]);
				}
				svDeviceManager.saveSvMarkers(device);
			}
		}
		// 清空缓存
		SpyMemcacheUtil.clear();
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage("");
		return dto;
	}

	public String listPtsDevice(String message) {
		RequestReader reader = new RequestReader(message);
		String ptsSn = reader.getString("/Request/PtsSN", false);
		Server server = serverManager.getServerBySN(ptsSn);
		List<SvDevice> list = svDeviceManager.listSvDeviceByPtsId(server
				.getId());
		// 查询厂商Map
		Map<Long, DicManufacture> manufactureMap = dicManufactureManager
				.getvDicManufactureList();
		Element root = svDeviceManager.getPTSChannelList(list, manufactureMap);
		Document doc = DocumentHelper.createDocument(root);
		return XmlUtil.xmlToString(doc);
	}

	public Object createDvrJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("organId", false);
		String subType = reader.getString("subType", false);
		String standardNumber = reader.getString("standardNumber", true);
		String name = reader.getString("name", true);
		String location = reader.getString("location", true);
		Long ptsId = reader.getLong("ptsId", false);
		Long manufacturerId = reader.getLong("manufacturerId", true);
		Integer channelAmount = reader.getInteger("channelAmount", true);
		String ip = reader.getString("ip", true);
		String port = reader.getString("port", true);
		String extend = reader.getString("extend", true);
		String userName = reader.getString("userName", true);
		String password = reader.getString("password", true);
		// 如果页面没有传sn，系统默认
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = NumberUtil.randomStandardNumber();
		}
		// 默认8通道
		if (null == channelAmount) {
			channelAmount = 8;
		}

		Long id = svDeviceManager.createDvr(organId, subType, standardNumber,
				name, location, ptsId, manufacturerId, channelAmount, ip, port,
				extend, userName, password);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(id + "");
		return dto;
	}

	public Object updateDvrJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		Long organId = reader.getLong("organId", true);
		String subType = reader.getString("subType", true);
		String standardNumber = reader.getString("standardNumber", true);
		String name = reader.getString("name", true);
		String location = reader.getString("location", true);
		Long ptsId = reader.getLong("ptsId", true);
		Long manufacturerId = reader.getLong("manufacturerId", true);
		Integer channelAmount = reader.getInteger("channelAmount", true);
		String ip = reader.getString("ip", true);
		String port = reader.getString("port", true);
		String extend = reader.getString("extend", true);
		String userName = reader.getString("userName", true);
		String password = reader.getString("password", true);

		svDeviceManager.updateDvr(id, organId, subType, standardNumber, name,
				location, ptsId, manufacturerId, channelAmount, ip, port,
				extend, userName, password);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	@Logon
	public Object listSvOrganTreeJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();
		VirtualOrgan root = organManager.getUgRootOrgan(userId);
		List<VirtualOrgan> vorganlist = organManager.listAllOrgan(root.getId());
		ListSvOrganTreeDTO dto = new ListSvOrganTreeDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("ListSvOrganTree");
		List<ListSvOrganTree> list = new ArrayList<ListSvOrganTree>();
		for (VirtualOrgan vorgan : vorganlist) {
			if (TypeDefinition.ORGAN_GENERAL == vorgan.getType()
					|| TypeDefinition.ORGAN_MOTORWAY == vorgan.getType()
					|| TypeDefinition.ORGAN_HIGHWAY == vorgan.getType()
					|| TypeDefinition.ORGAN_ROAD == vorgan.getType()
					|| TypeDefinition.VIRTUAL_ORGAN == vorgan.getType()) {
				ListSvOrganTreeDTO.ListSvOrganTree vo = dto.new ListSvOrganTree();
				vo.setId(vorgan.getId() + "");
				vo.setName(vorgan.getName());
				if (null == vorgan.getParentId()) {
					vo.setPid("0");
				} else {
					vo.setPid(vorgan.getParentId() + "");
				}
				vo.setType(vorgan.getType() + "");
				list.add(vo);
			}
		}
		dto.setChildren(list);
		return dto;
	}

	public Object createPresetJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer presetValue = reader.getInteger("presetValue", true);
		Long deviceId = reader.getLong("deviceId", false);
		String name = reader.getString("name", true);
		Long resourceId = reader.getLong("resourceId", true);

		VirtualOrgan device = organManager.getVOrgan(deviceId);
		Long id = presetManager.createPreset(presetValue, device.getDeviceId(),
				name, resourceId);

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(id + "");
		dto.setMethod("CreatePreset");
		return dto;
	}

	public Object updatePresetJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		Integer presetValue = reader.getInteger("presetValue", true);
		Long deviceId = reader.getLong("deviceId", true);
		String name = reader.getString("name", true);

		presetManager.updatePreset(id, presetValue, deviceId, name);

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod("UpdatePreset");
		return dto;
	}

	public Object deletePresetJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);

		presetManager.deletePreset(id);

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod("DeletePreset");
		return dto;
	}

	public Object listPresetJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		Long deviceId = reader.getLong("deviceId", false);

		VirtualOrgan device = organManager.getVOrgan(deviceId);
		List<SvPreset> list = presetManager.listPreset(device.getDeviceId());

		ListPresetDTO dto = new ListPresetDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod("ListPreset");
		dto.setList(list);
		return dto;
	}

	@Logon
	public Object listCameraByStakeNumberJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		ListCameraByStakeNumberDTO dto = new ListCameraByStakeNumberDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		// 判断是否登录
		Long userId = reader.getUserId();

		String stakeNumber = reader.getString("stakeNumber", false);
		Integer range = reader.getInteger("range", false);
		Long organId = reader.getLong("organId", false);

		float floatStake = NumberUtil.floatStake(stakeNumber);
		List<SvDevice> list = svDeviceManager.listCameraByStake(floatStake,
				organId, range);
		// 过滤用户权限
		Map<Long, VirtualOrgan> authMap = null;

		if (list.size() > 0) {
			authMap = organManager.mapUgRealDevice(reader.getUserGroupId());
		} else {
			return dto;
		}
		// 厂商字典
		Map<Long, DicManufacture> manufacturerMap = dicManufactureManager
				.getvDicManufactureList();

		for (SvDevice svDevice : list) {
			VirtualOrgan vo = authMap.get(svDevice.getId());
			if (vo != null) {
				ListCameraByStakeNumberDTO.Camera camera = dto.new Camera();
				camera.setId(vo.getId().toString());
				camera.setIp(MyStringUtil.object2StringNotNull(svDevice.getIp()));
				camera.setLocation(MyStringUtil.object2StringNotNull(svDevice
						.getLocation()));
				if (null != svDevice.getManufacturerId()) {
					DicManufacture dicManufacture = manufacturerMap
							.get(svDevice.getManufacturerId());
					if (null == dicManufacture) {
						logger.error("Dirty data found, manufacturer["
								+ svDevice.getManufacturerId()
								+ "] set for SvDevice is not found in DicManufacturer !");
						camera.setManufacturer("");
					} else {
						camera.setManufacturer(dicManufacture.getName());
					}
				}
				camera.setName(vo.getName());
				camera.setPort(MyStringUtil.object2StringNotNull(svDevice
						.getPort()));
				camera.setStakeNumber(MyStringUtil
						.object2StringNotNull(svDevice.getStakeNumber()));
				camera.setStandardNumber(svDevice.getStandardNumber());
				camera.setType(vo.getType().toString());
				dto.getCameras().add(camera);
			}
		}
		return dto;
	}

}
