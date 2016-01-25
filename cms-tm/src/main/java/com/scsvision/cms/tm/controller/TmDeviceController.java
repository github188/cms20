package com.scsvision.cms.tm.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.maintain.dto.ListDeviceDTO;
import com.scsvision.cms.maintain.dto.ListDeviceDTO.Device;
import com.scsvision.cms.maintain.manager.OnlineManager;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.tm.dto.DeviceDTO;
import com.scsvision.cms.tm.dto.TmDeviceDTO;
import com.scsvision.cms.util.cache.SpyMemcacheUtil;
import com.scsvision.cms.util.number.NumberUtil;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.DicManufacture;
import com.scsvision.database.entity.OnlineReal;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.Server;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.DicManufactureManager;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.ServerManager;
import com.scsvision.database.manager.SvDeviceManager;
import com.scsvision.database.manager.TmDeviceManager;
import com.scsvision.database.manager.UserManager;

/**
 * TmDeviceController
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午4:13:17
 */
@Stateless
public class TmDeviceController {
	private final Logger logger = LoggerFactory
			.getLogger(TmDeviceController.class);

	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;
	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;
	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;
	@EJB(beanName = "DicManufactureManagerImpl")
	private DicManufactureManager dicManufactureManager;
	@EJB(beanName = "ServerManagerImpl")
	private ServerManager serverManager;
	@EJB(beanName = "SvDeviceManagerImpl")
	private SvDeviceManager svDeviceManager;
	@EJB(beanName = "OnlineManagerImpl")
	private OnlineManager onlineManager;

	public Object saveTmMarkersJson(HttpServletRequest request) {
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
			// 拿到键
			String key = iters.next().toString();
			if ((Integer.valueOf(key.split("-")[1]) >= TypeDefinition.VMS_DOOR && Integer
					.valueOf(key.split("-")[1]) <= TypeDefinition.FACP)
					|| (Integer.valueOf(key.split("-")[1]) >= TypeDefinition.ORGAN_GENERAL && Integer
							.valueOf(key.split("-")[1]) < TypeDefinition.VIRTUAL_ORGAN)) {
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

			if (Integer.valueOf(key.split("-")[1]) >= TypeDefinition.VMS_DOOR) {
				TmDevice device = tmDeviceManager.getTmDevice(vorganMap.get(
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
					tmDeviceManager.saveTmMarkers(device);
				}
			}
			if (Integer.valueOf(key.split("-")[1]) < TypeDefinition.VIRTUAL_ORGAN) {
				Organ organ = organManager.getOrganById(vorganMap.get(
						Long.valueOf(key.split("-")[0])).getDeviceId());
				if (!val.equals("-")) {
					if (val.endsWith("-")) {
						organ.setLatitude(val.split("-")[0]);
					} else if (val.startsWith("-")) {
						organ.setLongitude(val.split("-")[1]);
					} else if (!val.equals("-")) {
						organ.setLatitude(val.split("-")[0]);
						organ.setLongitude(val.split("-")[1]);
					}
					organManager.updateOrgan(organ);
				}
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

	public Object saveMarkersJson(HttpServletRequest request) {
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
			// 拿到键
			String key = iters.next().toString();
			idList.add(Long.valueOf(key.split("-")[0]));
		}
		Map<Long, VirtualOrgan> vorganMap = organManager.mapVOrgan(idList);
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().toString();
			// 拿到值
			String val = map.get(key).toString();
			// 修改视频设备
			if (Integer.valueOf(key.split("-")[1]) >= TypeDefinition.VIDEO_DVR
					&& Integer.valueOf(key.split("-")[1]) < TypeDefinition.VMS_DOOR) {
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
			// 修改数据设备
			if (Integer.valueOf(key.split("-")[1]) >= TypeDefinition.VMS_DOOR) {
				TmDevice device = tmDeviceManager.getTmDevice(vorganMap.get(
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
					tmDeviceManager.saveTmMarkers(device);
				}
			}
			// 修改桥隧
			if (Integer.valueOf(key.split("-")[1]) < TypeDefinition.VIRTUAL_ORGAN) {
				Organ organ = organManager.getOrganById(vorganMap.get(
						Long.valueOf(key.split("-")[0])).getDeviceId());
				if (!val.equals("-")) {
					if (val.endsWith("-")) {
						organ.setLatitude(val.split("-")[0]);
					} else if (val.startsWith("-")) {
						organ.setLongitude(val.split("-")[1]);
					} else if (!val.equals("-")) {
						organ.setLatitude(val.split("-")[0]);
						organ.setLongitude(val.split("-")[1]);
					}
					organManager.updateOrgan(organ);
				}
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

	public Object listDeviceJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("organId", false);
		VirtualOrgan parent = organManager.getVOrgan(organId);
		List<VirtualOrgan> list = organManager.listChildVirtualOrgan(organId);
		Map<Long, DicManufacture> manufactureMap = dicManufactureManager
				.getvDicManufactureList();
		List<Long> tmDeviceIds = new LinkedList<Long>();
		List<Long> svDeviceIds = new LinkedList<Long>();
		for (VirtualOrgan vo : list) {
			if (vo.getType().intValue() >= TypeDefinition.VMS_DOOR
					&& vo.getType().intValue() < TypeDefinition.VMS_DOOR + 2000) {
				tmDeviceIds.add(vo.getDeviceId());
			} else if (vo.getType().intValue() >= TypeDefinition.VIDEO_DVR
					&& vo.getType().intValue() < TypeDefinition.VIDEO_DVR + 1000) {
				svDeviceIds.add(vo.getDeviceId());
			}
		}
		// 查询设备Map
		Map<Long, TmDevice> tmDeviceMap = tmDeviceManager
				.mapTmDevice(tmDeviceIds);
		Map<Long, SvDevice> svDeviceMap = svDeviceManager
				.mapSvDevice(svDeviceIds);
		// 获取当前在线设备列表
		Map<String, OnlineReal> onlineMap = onlineManager.mapOnlineDevice();
		ListDeviceDTO dto = new ListDeviceDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		List<Device> svList = new LinkedList<Device>();
		List<Device> vdList = new LinkedList<Device>();
		List<Device> cmsList = new LinkedList<Device>();
		List<Device> tslList = new LinkedList<Device>();
		List<Device> lilList = new LinkedList<Device>();
		List<Device> wstList = new LinkedList<Device>();
		List<Device> wsList = new LinkedList<Device>();
		List<Device> rdList = new LinkedList<Device>();
		List<Device> bdList = new LinkedList<Device>();
		List<Device> viList = new LinkedList<Device>();
		List<Device> coviList = new LinkedList<Device>();
		List<Device> noList = new LinkedList<Device>();
		List<Device> loLiList = new LinkedList<Device>();
		List<Device> switchList = new LinkedList<Device>();
		for (VirtualOrgan vo : list) {
			int type = vo.getType().intValue();
			// SV
			if (type >= TypeDefinition.VIDEO_DVR
					&& type < TypeDefinition.VMS_DOOR) {
				Device device = dto.new Device();
				device.setDeviceModel(svDeviceMap.get(vo.getDeviceId())
						.getDeviceModel());
				device.setId(vo.getId() + "");
				device.setOrganName(parent.getName());
				device.setLatitude(svDeviceMap.get(vo.getDeviceId())
						.getLatitude());
				device.setLongitude(svDeviceMap.get(vo.getDeviceId())
						.getLongitude());
				device.setType(vo.getType() + "");
				device.setManufacturer(manufactureMap.get(
						svDeviceMap.get(vo.getDeviceId()).getManufacturerId())
						.getName());
				device.setName(vo.getName());
				device.setStandarNumber(svDeviceMap.get(vo.getDeviceId())
						.getStandardNumber());
				device.setStakeNumber(svDeviceMap.get(vo.getDeviceId())
						.getStakeNumber());
				device.setChannelNumber(svDeviceMap.get(vo.getDeviceId())
						.getChannelNumber());
				device.setOnline(onlineMap.get(svDeviceMap
						.get(vo.getDeviceId()).getStandardNumber()) != null ? true
						: false);
				svList.add(device);
			}
			// VD
			else if (type >= TypeDefinition.VEHICLE_DETECTOR_MICRO
					&& type < TypeDefinition.WEATHER_DETECTOR) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				vdList.add(device);
			}
			// CMS
			else if (type >= TypeDefinition.VMS_DOOR
					&& type < TypeDefinition.VEHICLE_DETECTOR_MICRO) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				cmsList.add(device);
			}
			// TSL
			else if (type == TypeDefinition.VMS_TRAFFIC_SIGN) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				tslList.add(device);
			}
			// LIL
			else if (type == TypeDefinition.VMS_LANE_INDICATOR) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				lilList.add(device);
			}
			// WST
			else if (type == TypeDefinition.WEATHER_DETECTOR) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				wstList.add(device);
			}
			// WS
			else if (type == TypeDefinition.WEATHER_DETECTOR_WIND) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				wsList.add(device);
			}
			// RoadDetector
			else if (type == TypeDefinition.WEATHER_DETECTOR_ROAD) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				rdList.add(device);
			}
			// BridgeDetector
			else if (type == TypeDefinition.WEATHER_DETECTOR_BRIDGE) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				bdList.add(device);
			}
			// VI
			else if (type == TypeDefinition.WEATHER_DETECTOR_VI) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				viList.add(device);
			}
			// COVI
			else if (type == TypeDefinition.DETECTOR_COVI) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				coviList.add(device);
			}
			// NO
			else if (type == TypeDefinition.DETECTOR_NO) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				noList.add(device);
			}
			// LOLI
			else if (type == TypeDefinition.DETECTOR_LOLI) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				loLiList.add(device);
			}
			// SWITCH
			else if (type >= TypeDefinition.SWITCH_LIGHT
					&& type <= TypeDefinition.SWITCH_WATER_PUMP) {
				Device device = dto.new Device();
				initDevice(device, vo, tmDeviceMap, manufactureMap, onlineMap,
						parent);
				switchList.add(device);
			}
		}
		dto.setBdList(bdList);
		dto.setCmsList(cmsList);
		dto.setSvList(svList);
		dto.setCoviList(coviList);
		dto.setLilList(lilList);
		dto.setLoLiList(loLiList);
		dto.setNoList(noList);
		dto.setRdList(rdList);
		dto.setWstList(wstList);
		dto.setWsList(wsList);
		dto.setViList(viList);
		dto.setVdList(vdList);
		dto.setTslList(tslList);
		dto.setSwitchList(switchList);
		return dto;
	}

	private void initDevice(Device device, VirtualOrgan vo,
			Map<Long, TmDevice> deviceMap,
			Map<Long, DicManufacture> manufacturerMap,
			Map<String, OnlineReal> onlineMap, VirtualOrgan parent) {
		device.setId(vo.getId().toString());
		device.setName(MyStringUtil.object2StringNotNull(vo.getName()));
		device.setType(vo.getType().toString());
		device.setOrganName(parent.getName());
		TmDevice tmdevice = deviceMap.get(vo.getDeviceId());
		if (null == tmdevice) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"TmDevice[" + vo.getDeviceId() + "] not found !");
		}
		device.setStandarNumber(MyStringUtil.object2StringNotNull(tmdevice
				.getStandardNumber()));
		device.setLongitude(MyStringUtil.object2StringNotNull(tmdevice
				.getLongitude()));
		device.setLatitude(MyStringUtil.object2StringNotNull(tmdevice
				.getLatitude()));
		device.setStakeNumber(MyStringUtil.object2StringNotNull(tmdevice
				.getStakeNumber()));
		if (StringUtils.isNotBlank(tmdevice.getExtend())) {
			device.setHeight(tmdevice.getExtend().split("::")[0].split("=")[1]);
			device.setWidth(tmdevice.getExtend().split("::")[1].split("=")[1]);
		}
		device.setNavigation(MyStringUtil.object2StringNotNull(tmdevice
				.getNavigation()));
		DicManufacture man = manufacturerMap.get(tmdevice.getManufacturerId());
		if (null != man) {
			device.setManufacturer(MyStringUtil.object2StringNotNull(man
					.getName()));
		} else {
			device.setManufacturer("");
		}
		if (null != onlineMap.get(tmdevice.getStandardNumber())) {
			device.setOnline(true);
		} else {
			device.setOnline(false);
		}
	}

	public String listDasDevice(String message) {
		RequestReader reader = new RequestReader(message);
		String dasSn = reader.getString("Request/DasSN", false);
		Server das = serverManager.getServerBySN(dasSn);

		List<TmDevice> list = tmDeviceManager.listDasDevice(das.getId());
		Map<Long, DicManufacture> manufacturerMap = dicManufactureManager
				.getvDicManufactureList();

		Document doc = XmlUtil.generateResponse("ListDasDevice",
				ErrorCode.SUCCESS, "");
		Element root = doc.getRootElement();

		// 所有的父设备类型节点，key为id，value为对象本身
		Map<Long, Element> plcs = new HashMap<Long, Element>();
		// 构建所有的Device节点，并记录父设备类型节点
		for (TmDevice td : list) {
			// 存在parentId的做为Sub节点
			if (td.getParentId() != null) {
				continue;
			}
			Element device = DocumentHelper.createElement("Device");
			device.addAttribute("Note", td.getName());
			device.addAttribute("StandardNumber", td.getStandardNumber());
			String type = td.getType()
					+ MyStringUtil.object2StringNotNull(td.getSubType());
			device.addAttribute("Type", type);
			device.addAttribute("IP",
					MyStringUtil.object2StringNotNull(td.getIp()));
			device.addAttribute("Port",
					MyStringUtil.object2StringNotNull(td.getPort()));
			device.addAttribute("User",
					MyStringUtil.object2StringNotNull(td.getUser()));
			device.addAttribute("Password",
					MyStringUtil.object2StringNotNull(td.getPassword()));
			device.addAttribute("GatherInterval",
					MyStringUtil.object2StringNotNull(td.getGatherInterval()));
			// 如果configValue不为空，为主设备添加Extend节点
			if (StringUtils.isNotBlank(td.getConfigValue())) {
				device.add(XmlUtil.stringToDetachNode(td.getConfigValue()));
			}
			Long manufacturerId = td.getManufacturerId();
			if (null != manufacturerId) {
				DicManufacture manufacture = manufacturerMap
						.get(manufacturerId);
				if (null != manufacture) {
					device.addAttribute("Protocol", MyStringUtil
							.object2StringNotNull(manufacture.getProtocol()));
				} else {
					throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
							"TmDevice[" + td.getId()
									+ "] has a dirty Manufacturer["
									+ manufacturerId + "] !");
				}
			} else {
				device.addAttribute("Protocol", "");
			}
			// 如果存在阀值添加阀值属性, threshold格式定义为： name::value::rule::alarmType,
			// 例如Co阀值超过100的报警规则为：COConct::100::ge:8000
			if (StringUtils.isNotBlank(td.getThreshold1())
					|| StringUtils.isNotBlank(td.getThreshold2())
					|| StringUtils.isNotBlank(td.getThreshold3())
					|| StringUtils.isNotBlank(td.getThreshold4())) {
				Element threshold = DocumentHelper.createElement("Threshold");
				threshold.addAttribute("AlarmFlag",
						MyStringUtil.object2StringNotNull(td.getAlarmFlag()));
				device.add(threshold);
				// 添加Filter
				Element filter1 = getFilter(td.getThreshold1());
				if (null != filter1) {
					threshold.add(filter1);
				}
				Element filter2 = getFilter(td.getThreshold2());
				if (null != filter2) {
					threshold.add(filter2);
				}
				Element filter3 = getFilter(td.getThreshold3());
				if (null != filter3) {
					threshold.add(filter3);
				}
				Element filter4 = getFilter(td.getThreshold4());
				if (null != filter4) {
					threshold.add(filter4);
				}
			}

			// PLC设备
			if (TypeDefinition.PLC == Integer.parseInt(type)) {
				device.addAttribute("SlaveId",
						MyStringUtil.object2StringNotNull(td.getSlaveId()));
				device.addAttribute("AddrsForSet",
						MyStringUtil.object2StringNotNull(td.getAddrsForSet()));
				device.addAttribute("AddrsForGet",
						MyStringUtil.object2StringNotNull(td.getAddrsForGet()));
				plcs.put(td.getId(), device);
			}
			// 火灾报警平台
			else if (TypeDefinition.FACP == Integer.parseInt(type)) {
				// 没有特殊属性
				plcs.put(td.getId(), device);
			} else {
				// do nothing
			}

			root.add(device);
		}

		// 构建所有的Sub节点
		for (TmDevice td : list) {
			// 不存在parentId的是Device节点已经构建
			if (td.getParentId() == null) {
				continue;
			}
			Element device = plcs.get(td.getParentId());
			if (null == device) {
				logger.error("TmDevice[" + td.getId()
						+ "] has a dirty parent or not under the same DAS !");
				continue;
			}
			Element sub = DocumentHelper.createElement("Sub");
			sub.addAttribute("Note", td.getName());
			sub.addAttribute("StandardNumber", td.getStandardNumber());
			String type = td.getType()
					+ MyStringUtil.object2StringNotNull(td.getSubType());
			sub.addAttribute("Type", type);

			// 如果存在阀值添加阀值属性, threshold格式定义为： name::value::rule,
			// 例如Co阀值超过100的报警规则为：COConct::100::ge
			if (StringUtils.isNotBlank(td.getThreshold1())
					|| StringUtils.isNotBlank(td.getThreshold2())
					|| StringUtils.isNotBlank(td.getThreshold3())
					|| StringUtils.isNotBlank(td.getThreshold4())) {
				Element threshold = DocumentHelper.createElement("Threshold");
				threshold.addAttribute("AlarmFlag",
						MyStringUtil.object2StringNotNull(td.getAlarmFlag()));
				sub.add(threshold);
				// 添加Filter
				Element filter1 = getFilter(td.getThreshold1());
				if (null != filter1) {
					threshold.add(filter1);
				}
				Element filter2 = getFilter(td.getThreshold2());
				if (null != filter2) {
					threshold.add(filter2);
				}
				Element filter3 = getFilter(td.getThreshold3());
				if (null != filter3) {
					threshold.add(filter3);
				}
				Element filter4 = getFilter(td.getThreshold4());
				if (null != filter4) {
					threshold.add(filter4);
				}
			}

			// PLC子设备
			if (TypeDefinition.PLC == XmlUtil.getInteger(device, "Type")
					.intValue()) {
				sub.addAttribute("AddrsForSet",
						MyStringUtil.object2StringNotNull(td.getAddrsForSet()));
				sub.addAttribute("AddrsForGet",
						MyStringUtil.object2StringNotNull(td.getAddrsForGet()));
			}
			// 火灾报警子设备
			else if (TypeDefinition.FACP == XmlUtil.getInteger(device, "Type")
					.intValue()) {
				sub.addAttribute("ChannelNumber", MyStringUtil
						.object2StringNotNull(td.getChannelNumber()));
			} else {
				// do nothing
			}
			device.add(sub);
		}
		return XmlUtil.xmlToString(doc);
	}

	public Object createTmDeviceJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("organId", false);
		Long parentId = reader.getLong("parentId", true);
		String name = reader.getString("name", false);
		String location = reader.getString("location", true);
		String type = reader.getString("type", false);
		String subType = reader.getString("subType", false);
		String standardNumber = reader.getString("standardNumber", true);
		String longitude = reader.getString("longitude", true);
		String latitude = reader.getString("latitude", true);
		String stakeNumber = reader.getString("stakeNumber", true);
		Long manufacturerId = reader.getLong("manufacturerId", true);
		Long dasId = reader.getLong("dasId", false);
		String ip = reader.getString("ip", true);
		String port = reader.getString("port", true);
		String navigation = reader.getString("navigation", true);
		String threshold1 = reader.getString("threshold1", true);
		String threshold2 = reader.getString("threshold2", true);
		String threshold3 = reader.getString("threshold3", true);
		String threshold4 = reader.getString("threshold4", true);
		String slaveId = reader.getString("slaveId", true);
		String addrsForSet = reader.getString("addrsForSet", true);
		String addrsForGet = reader.getString("addrsForGet", true);
		String user = reader.getString("user", true);
		String password = reader.getString("password", true);
		String channelNumber = reader.getString("channelNumber", true);
		String configValue = reader.getString("configValue", true);
		Integer gatherInterval = reader.getInteger("gatherInterval", true);
		String width = reader.getString("width", true);
		String height = reader.getString("height", true);
		String floatStake = reader.getString("floatStake", true);
		String extend = "";
		if (StringUtils.isNotBlank(height) && StringUtils.isNotBlank(width)) {
			extend = "height=" + height + "::width=" + width;
		}

		TmDevice entity = new TmDevice();
		entity.setAddrsForGet(addrsForGet);
		entity.setAddrsForSet(addrsForSet);
		entity.setChannelNumber(channelNumber);
		entity.setConfigValue(configValue);
		entity.setCreateTime(System.currentTimeMillis());
		entity.setDasId(dasId);
		entity.setExtend(extend);
		entity.setGatherInterval(gatherInterval);
		entity.setIp(ip);
		entity.setLatitude(latitude);
		entity.setLocation(location);
		entity.setLongitude(longitude);
		entity.setManufacturerId(manufacturerId);
		entity.setName(name);
		entity.setNavigation(navigation);
		entity.setUser(user);
		entity.setType(type);
		entity.setSubType(subType);
		entity.setStakeNumber(stakeNumber);
		entity.setSlaveId(slaveId);
		entity.setOrganId(organId);
		entity.setPort(port);
		entity.setPassword(password);
		entity.setParentId(parentId);
		entity.setThreshold1(threshold1);
		entity.setThreshold2(threshold2);
		entity.setThreshold3(threshold3);
		entity.setThreshold4(threshold4);
		entity.setFloatStake(StringUtils.isNotBlank(floatStake) ? Float
				.valueOf(floatStake) : 0f);
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = NumberUtil.randomStandardNumber();
		}
		entity.setStandardNumber(standardNumber);
		Long id = tmDeviceManager.createTmDevice(entity);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(id + "::" + entity.getStandardNumber());
		return dto;
	}

	public Object updateTmDeviceJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		Long organId = reader.getLong("organId", true);
		Long parentId = reader.getLong("parentId", true);
		String name = reader.getString("name", true);
		String location = reader.getString("location", true);
		String type = reader.getString("type", true);
		String subType = reader.getString("subType", true);
		String standardNumber = reader.getString("standardNumber", true);
		String longitude = reader.getString("longitude", true);
		String latitude = reader.getString("latitude", true);
		String stakeNumber = reader.getString("stakeNumber", true);
		Long manufacturerId = reader.getLong("manufacturerId", true);
		Long dasId = reader.getLong("dasId", true);
		String ip = reader.getString("ip", true);
		String port = reader.getString("port", true);
		String navigation = reader.getString("navigation", true);
		String threshold1 = reader.getString("threshold1", true);
		String threshold2 = reader.getString("threshold2", true);
		String threshold3 = reader.getString("threshold3", true);
		String threshold4 = reader.getString("threshold4", true);
		String slaveId = reader.getString("slaveId", true);
		String addrsForSet = reader.getString("addrsForSet", true);
		String addrsForGet = reader.getString("addrsForGet", true);
		String user = reader.getString("user", true);
		String password = reader.getString("password", true);
		String channelNumber = reader.getString("channelNumber", true);
		String configValue = reader.getString("configValue", true);
		Integer gatherInterval = reader.getInteger("gatherInterval", true);
		String width = reader.getString("width", true);
		String height = reader.getString("height", true);
		String floatStake = reader.getString("floatStake", true);
		String extend = "";
		if (StringUtils.isNotBlank(height) && StringUtils.isNotBlank(width)) {
			extend = "height=" + height + "::width=" + width;
		}
		TmDevice entity = tmDeviceManager.getTmDevice(id);
		if (null != organId) {
			entity.setOrganId(organId);
		}
		if (null != parentId) {
			entity.setParentId(parentId);
		}
		if (null != manufacturerId) {
			entity.setManufacturerId(manufacturerId);
		}
		if (null != dasId) {
			entity.setDasId(dasId);
		}
		if (null != gatherInterval) {
			entity.setGatherInterval(gatherInterval);
		}
		if (StringUtils.isNotBlank(configValue)) {
			entity.setConfigValue(configValue);
		}
		if (StringUtils.isNotBlank(name)) {
			entity.setName(name);
		}
		if (StringUtils.isNotBlank(location)) {
			entity.setLocation(location);
		}
		if (StringUtils.isNotBlank(type)) {
			entity.setType(type);
		}
		if (StringUtils.isNotBlank(subType)) {
			entity.setSubType(subType);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			entity.setStandardNumber(standardNumber);
		}
		if (StringUtils.isNotBlank(longitude)) {
			entity.setLongitude(longitude);
		}
		if (StringUtils.isNotBlank(latitude)) {
			entity.setLatitude(latitude);
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			entity.setStakeNumber(stakeNumber);
		}
		if (StringUtils.isNotBlank(ip)) {
			entity.setIp(ip);
		}
		if (StringUtils.isNotBlank(port)) {
			entity.setPort(port);
		}
		if (StringUtils.isNotBlank(navigation)) {
			entity.setNavigation(navigation);
		}
		if (StringUtils.isNotBlank(threshold1)) {
			entity.setThreshold1(threshold1);
		}
		if (StringUtils.isNotBlank(threshold2)) {
			entity.setThreshold2(threshold2);
		}
		if (StringUtils.isNotBlank(threshold3)) {
			entity.setThreshold3(threshold3);
		}
		if (StringUtils.isNotBlank(threshold4)) {
			entity.setThreshold4(threshold4);
		}
		if (StringUtils.isNotBlank(extend)) {
			entity.setExtend(extend);
		}
		if (StringUtils.isNotBlank(slaveId)) {
			entity.setSlaveId(slaveId);
		}
		if (StringUtils.isNotBlank(addrsForSet)) {
			entity.setAddrsForSet(addrsForSet);
		}
		if (StringUtils.isNotBlank(addrsForGet)) {
			entity.setAddrsForGet(addrsForGet);
		}
		if (StringUtils.isNotBlank(user)) {
			entity.setUser(user);
		}
		if (StringUtils.isNotBlank(password)) {
			entity.setPassword(password);
		}
		if (StringUtils.isNotBlank(channelNumber)) {
			entity.setChannelNumber(channelNumber);
		}
		if (StringUtils.isNotBlank(floatStake)) {
			entity.setFloatStake(Float.valueOf(floatStake));
		}
		tmDeviceManager.updateTmDevice(entity);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteTmDeviceByIdJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject object = new JSONObject(json);
		JSONArray array = object.getJSONArray("ids");
		for (int i = 0; i < array.length(); i++) {
			tmDeviceManager.deleteTmDeviceById(array.getLong(i));
		}
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object listTmDeviceJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("organId", false);
		String name = reader.getString("name", true);
		String standardNumber = reader.getString("standardNumber", true);
		String stakeNumber = reader.getString("stakeNumber", true);
		String type = reader.getString("type", true);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}
		List<TmDevice> list = tmDeviceManager.listTmDevice(organId, name,
				standardNumber, stakeNumber, type, start, limit);
		Integer count = tmDeviceManager.countTmDevice(organId, name,
				standardNumber, stakeNumber, type);
		TmDeviceDTO dto = new TmDeviceDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(count + "");
		List<TmDevice> dlist = new LinkedList<TmDevice>();
		for (TmDevice entity : list) {
			TmDevice td = new TmDevice();
			td.setAddrsForGet(entity.getAddrsForGet());
			td.setAddrsForSet(entity.getAddrsForSet());
			td.setAlarmFlag(entity.getAlarmFlag());
			td.setChannelNumber(entity.getChannelNumber());
			td.setConfigValue(entity.getConfigValue());
			td.setCreateTime(entity.getCreateTime());
			td.setDasId(entity.getDasId());
			td.setExtend(entity.getExtend());
			td.setGatherInterval(entity.getGatherInterval());
			td.setId(entity.getId());
			td.setIp(entity.getIp());
			td.setUser(entity.getUser());
			td.setType(entity.getType());
			td.setThreshold1(entity.getThreshold1());
			td.setThreshold2(entity.getThreshold2());
			td.setThreshold3(entity.getThreshold3());
			td.setThreshold4(entity.getThreshold4());
			td.setSubType(entity.getSubType());
			td.setLatitude(entity.getLatitude());
			td.setStandardNumber(entity.getStandardNumber());
			td.setStakeNumber(entity.getStakeNumber());
			td.setLocation(entity.getLocation());
			td.setLongitude(entity.getLongitude());
			td.setManufacturerId(entity.getManufacturerId());
			td.setName(entity.getName());
			td.setNavigation(entity.getNavigation());
			td.setSlaveId(entity.getSlaveId());
			td.setOrganId(entity.getOrganId());
			td.setParentId(entity.getParentId());
			td.setPassword(entity.getPassword());
			td.setPort(entity.getPort());
			td.setFloatStake(entity.getFloatStake());
			dlist.add(td);
		}
		dto.setList(dlist);
		return dto;
	}

	public Element getFilter(String threshold) {
		if (StringUtils.isBlank(threshold)) {
			return null;
		}
		String[] values = threshold.split("::");
		if (values.length != 4) {
			logger.error("Threshold[" + threshold + "] is invalid !");
			return null;
		}
		Element filter = DocumentHelper.createElement("Filter");
		filter.addAttribute("Name", values[0]);
		filter.addAttribute("Value", values[1]);
		filter.addAttribute("Rule", values[2]);
		filter.addAttribute("AlarmType", values[3]);
		return filter;
	}

	public Object updateLayoutOfDevicesJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject obj = new JSONObject(json);
		String beginMarker = obj.getString("beginMarker");
		String endMarker = obj.getString("endMarker");
		Integer type = obj.getInt("type");
		JSONArray ids = obj.getJSONArray("ids");
		Float beginStake = Float.valueOf(beginMarker.split("-")[0]);
		Float endStake = Float.valueOf(endMarker.split("-")[0]);
		Float interval = (endStake - beginStake) / 6;
		List<TmDevice> tmList = new LinkedList<TmDevice>();
		List<SvDevice> svList = new LinkedList<SvDevice>();
		for (int i = 0; i < ids.length(); i++) {
			Long deviceId = organManager.getVOrgan(ids.getLong(i))
					.getDeviceId();
			if (type == 1) {
				SvDevice entity = svDeviceManager.getSvDevice(deviceId);
				svDeviceManager.updateDeviceLocation(deviceId,
						(beginStake + interval * (1 + i)) + "",
						beginMarker.split("-")[1]);
				svList.add(entity);
			} else {
				TmDevice entity = tmDeviceManager.getTmDevice(deviceId);
				entity.setLongitude((beginStake + interval * (1 + i)) + "");
				entity.setLatitude(beginMarker.split("-")[1]);
				tmDeviceManager.updateTmDevice(entity);
				tmList.add(entity);
			}
		}
		DeviceDTO dto = new DeviceDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		if (type == 1) {
			dto.setSvList(svList);
		} else {
			dto.setTmList(tmList);
		}
		return dto;
	}

}
