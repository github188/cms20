package com.scsvision.cms.maintain.controller;

import java.text.SimpleDateFormat;
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
import org.dom4j.DocumentException;
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
import com.scsvision.cms.maintain.dto.GetCameraStatusDTO;
import com.scsvision.cms.maintain.dto.ListDeviceStatusDTO;
import com.scsvision.cms.maintain.dto.ListOrganDeviceDTO;
import com.scsvision.cms.maintain.dto.ListSvOrganDeviceDTO;
import com.scsvision.cms.maintain.dto.OnlineRealDTO;
import com.scsvision.cms.maintain.manager.OnlineManager;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.interceptor.SessionCheckInterceptor;
import com.scsvision.cms.util.md5.MD5Util;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.DicManufacture;
import com.scsvision.database.entity.Online;
import com.scsvision.database.entity.OnlineReal;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.User;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.DicManufactureManager;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.SvDeviceManager;
import com.scsvision.database.manager.TmDeviceManager;
import com.scsvision.database.manager.UserManager;

@Stateless
@Interceptors(SessionCheckInterceptor.class)
public class OnlineController {
	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;

	@EJB(beanName = "OnlineManagerImpl")
	private OnlineManager onlineManager;
	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;
	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;
	@EJB(beanName = "SvDeviceManagerImpl")
	private SvDeviceManager svDeviceManager;
	@EJB(beanName = "DicManufactureManagerImpl")
	private DicManufactureManager dicManufactureManager;

	private final Logger logger = LoggerFactory
			.getLogger(OnlineController.class);

	public String csLogin(String message) {
		RequestReader reader = new RequestReader(message);
		String loginName = reader.getString("Request/LoginName", false);
		String passwd = reader.getString("Request/Password", false);
		String type = reader.getString("Request/ClientType", false);
		String ip = reader.getString("Request/IP", false);

		List<User> user = userManager.getUserByName(loginName);
		if (user.size() <= 0) {
			throw new BusinessException(ErrorCode.USER_NOT_FOUND,
					"login_name [" + loginName + "] not found!");
		}
		if (!passwd.equals(MD5Util.MD5(user.get(0).getPassword()))) {
			throw new BusinessException(ErrorCode.PASSWORD_ERROR,
					"password error!");
		}
		// 判断OMC登录权限
		if (type.equals(TypeDefinition.CLIENT_TYPE_OMC)) {
			if (user.get(0).getAdminPriv() == 0) {
				throw new BusinessException(ErrorCode.USER_ROLE_INVALID,
						"user [" + user.get(0).getLogonName()
								+ "] is not admin");
			}
		}
		// 生成ticket
		String ticket = onlineManager.csLogin(user.get(0), type, ip)
				.getTicket();

		Element root = DocumentHelper.createElement("Response");
		root.addAttribute("Method", "UserLogin");
		root.addAttribute("Code", "200");
		root.addAttribute("Message", "");
		Document doc = DocumentHelper.createDocument(root);
		Element ticketE = DocumentHelper.createElement("Ticket");
		Element userId = DocumentHelper.createElement("UseId");
		root.add(ticketE);
		root.add(userId);
		ticketE.setText(ticket);
		userId.setText(user.get(0).getId().toString());
		return XmlUtil.xmlToString(doc);
	}

	public String offline(String message) {
		RequestReader reader = new RequestReader(message);
		String ticket = reader.getString("Request/Ticket", false);
		Integer kickFlag = reader.getInteger("Request/KickFlag", true);

		OnlineReal onlineReal = onlineManager.getOnlineReal(ticket);
		if (null != onlineReal) {
			if (logger.isDebugEnabled()) {
				logger.debug("Offline Ticket[" + ticket + "]");
			}
			onlineManager.offline(onlineReal, kickFlag);
		}

		Document doc = XmlUtil.generateResponse("Offline", ErrorCode.SUCCESS,
				"");
		return XmlUtil.xmlToString(doc);
	}

	public Object listOnlineRealJson(HttpServletRequest request) {
		String json = request.getParameter("json");
		JSONObject obj = new JSONObject(json);
		JSONArray array = obj.getJSONArray("types");
		List<Integer> types = new LinkedList<Integer>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj1 = (JSONObject) array.get(i);
			types.add(obj1.getInt("type"));
		}
		Integer start = obj.getInt("start");
		if (null == start) {
			start = 0;
		}
		Integer limit = obj.getInt("limit");
		if (null == limit) {
			limit = 50;
		}
		List<OnlineReal> list = onlineManager.listOnlineRealByType(types,
				start, limit);
		Integer count = onlineManager.countOnlineRealList(types);
		OnlineRealDTO dto = new OnlineRealDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(count + "");
		List<OnlineRealDTO.OnlineRealDto> dtoList = new LinkedList<OnlineRealDTO.OnlineRealDto>();
		for (OnlineReal o : list) {
			OnlineRealDTO.OnlineRealDto entity = dto.new OnlineRealDto();
			entity.setClientType(o.getClientType());
			entity.setId(o.getId());
			entity.setIp(o.getIp());
			entity.setName(o.getName());
			entity.setOnlineTime(o.getOnlineTime());
			entity.setResourceId(o.getResourceId());
			entity.setStandardNumber(o.getStandardNumber());
			entity.setTicket(o.getTicket());
			entity.setType(o.getType());
			entity.setUpdateTime(o.getUpdateTime());
			dtoList.add(entity);
		}
		dto.setList(dtoList);
		return dto;
	}

	public Object forceLogoffJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		OnlineReal onlineReal = onlineManager.getOnlineRealById(id);
		if (null != onlineReal) {
			onlineManager.offline(onlineReal, TypeDefinition.OFFLINE_KICKOFF);
		} else {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "该资源不在线！");
		}
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage("");
		return dto;
	}

	public void updateStatus(String message) {
		RequestReader reader = new RequestReader(message);
		Element root = reader.getRoot();
		List<Element> resources = root.elements("Resource");

		// 在线列表
		List<String> onlineSns = new LinkedList<String>();
		// 离线列表
		List<String> offlineSns = new LinkedList<String>();
		for (Element resource : resources) {
			if (TypeDefinition.STATUS_ONLINE.equals(resource
					.attributeValue("Status"))) {
				onlineSns.add(resource.attributeValue("SN"));
			} else {
				offlineSns.add(resource.attributeValue("SN"));
			}
		}

		// 获取所有的设备ID，key为SN, value为ID
		Map<String, Long> svDeviceMap = svDeviceManager.mapBySn();
		Map<String, Long> tmDeviceMap = tmDeviceManager.mapBySn();
		svDeviceMap.putAll(tmDeviceMap);

		if (onlineSns.size() > 0) {
			onlineManager.reportOnline(onlineSns, svDeviceMap);
		}
		if (offlineSns.size() > 0) {
			onlineManager.reportOffline(offlineSns);
		}
	}

	public Object userLogin(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String loginName = reader.getString("loginName", false);
		String passwd = reader.getString("password", false);
		String type = reader.getString("clientType", false);
		String ip = reader.getClientIp();

		List<User> users = userManager.getUserByName(loginName);
		if (users.size() <= 0) {
			throw new BusinessException(ErrorCode.USER_NOT_FOUND,
					"login_name [" + loginName + "] not found!");
		}

		User user = users.get(0);

		if (!passwd.equals(MD5Util.MD5(user.getPassword()))) {
			throw new BusinessException(ErrorCode.PASSWORD_ERROR,
					"password error!");
		}
		// 判断OMC登录权限
		if (type.equals(TypeDefinition.CLIENT_TYPE_OMC)) {
			if (user.getAdminPriv() == 0) {
				throw new BusinessException(ErrorCode.USER_ROLE_INVALID,
						"user [" + user.getLogonName() + "] is not admin");
			}
		}
		// 生成ticket
		String ticket = onlineManager.csLogin(user, type, ip).getTicket();
		// 设置到用户session中
		request.getSession().setAttribute("userId", user.getId());
		request.getSession().setAttribute("userName", user.getName());
		request.getSession().setAttribute("ticket", ticket);
		request.getSession().setAttribute("userGroupId", user.getUserGroupId());

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("UserLogin");

		return dto;
	}

	@Logon
	public Object listOrganDeviceJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();
		// 获取用户组根机构
		User user = userManager.getUser(userId);
		VirtualOrgan rootOrgan = organManager.getUgRootOrgan(user
				.getUserGroupId());
		// 用户组资源集合
		Map<Long, VirtualOrgan> map = organManager.mapUgVirtualOrgan(rootOrgan
				.getId());

		List<Long> tmDeviceIds = new LinkedList<Long>();
		List<Long> svDeviceIds = new LinkedList<Long>();
		for (VirtualOrgan vo : map.values()) {
			if (vo.getType().intValue() >= TypeDefinition.VMS_DOOR
					&& vo.getType().intValue() < TypeDefinition.VMS_DOOR + 2000) {
				tmDeviceIds.add(vo.getDeviceId());
			} else if (vo.getType().intValue() >= TypeDefinition.VIDEO_DVR
					&& vo.getType().intValue() < TypeDefinition.VIDEO_DVR + 1000) {
				svDeviceIds.add(vo.getDeviceId());
			}
		}

		// 查询数据设备Map, 小于500个用in查询，大于500个查询全表
		if (tmDeviceIds.size() > 500) {
			tmDeviceIds = null;
		}
		Map<Long, TmDevice> tmDeviceMap = tmDeviceManager
				.mapTmDevice(tmDeviceIds);
		// 查询视频设备Map, 小于500个用in查询，大于500个查询全表
		if (svDeviceIds.size() > 500) {
			svDeviceIds = null;
		}
		Map<Long, SvDevice> svDeviceMap = svDeviceManager
				.mapSvDevice(svDeviceIds);
		// 查询机构Map
		Map<Long, Organ> organMap = organManager.getOrgans();
		// 查询厂商Map
		Map<Long, DicManufacture> manufacturerMap = dicManufactureManager
				.getvDicManufactureList();
		// 获取当前在线设备列表
		Map<String, OnlineReal> onlineMap = onlineManager.mapOnlineDevice();
		// 记录各机构节点的在线/总数量, key为机构ID, value[0]为在线数量, value[1]为总数量
		Map<String, int[]> organNumberMap = new HashMap<String, int[]>();
		// 所有的机构节点缓存, 处理最后的在线/总量改名, key为机构ID
		Map<String, ListOrganDeviceDTO.Node> organCacheMap = new HashMap<String, ListOrganDeviceDTO.Node>();

		ListOrganDeviceDTO dto = new ListOrganDeviceDTO();
		List<ListOrganDeviceDTO.Node> children = dto.getChildren();

		// 首先构建所有的机构
		for (VirtualOrgan vo : map.values()) {
			Integer type = vo.getType();
			if (type.intValue() <= TypeDefinition.VIRTUAL_ORGAN
					&& vo.getVisible().intValue() == 1) {
				// 初始化机构设备在线统计数字
				organNumberMap.put(vo.getId().toString(), initNumber());

				VirtualOrgan parent = map.get(vo.getParentId());

				ListOrganDeviceDTO.Node node = dto.new Node();
				node.setId(vo.getId().toString());
				node.setName(vo.getName());
				node.setRealName(vo.getName());
				// 只支持一层的不可见，将下级的资源加入到上级中
				if (null != parent) {
					if (parent.getVisible().intValue() == 1) {
						node.setPid(vo.getParentId().toString());
					} else {
						node.setPid(MyStringUtil.object2StringNotNull(parent
								.getParentId()));
					}
				} else {
					node.setPid("");
				}
				node.setType(type.toString());
				// 默认都是在线
				node.setIsOnline(true);
				// 真实机构
				if (TypeDefinition.ORGAN_GENERAL <= type.intValue()
						&& TypeDefinition.VIRTUAL_ORGAN > type.intValue()) {
					Organ realOrgan = organMap.get(vo.getDeviceId());
					if (null == realOrgan) {
						logger.error("VirtualOrgan[" + vo.getId()
								+ "] is a dirty data, which deviceId["
								+ vo.getDeviceId()
								+ "] is not found in Organ !");
						continue;
					}
					node.setEntranceNumber(MyStringUtil
							.object2StringNotNull(realOrgan.getEntranceNumber()));
					node.setExitNumber(MyStringUtil
							.object2StringNotNull(realOrgan.getExitNumber()));
					node.setLatitude(MyStringUtil
							.object2StringNotNull(realOrgan.getLatitude()));
					node.setLength(MyStringUtil.object2StringNotNull(realOrgan
							.getLength()));
					node.setLongitude(MyStringUtil
							.object2StringNotNull(realOrgan.getLongitude()));
					node.setStakeNumber(MyStringUtil
							.object2StringNotNull(realOrgan.getBeginStake()));
					node.setStandarNumber(MyStringUtil
							.object2StringNotNull(realOrgan.getStandardNumber()));
					node.setLaneNumber(MyStringUtil
							.object2StringNotNull(realOrgan.getLaneNumber()));
				}
				// 虚拟机构
				else {
					// do nothing
				}
				organCacheMap.put(node.getId(), node);
				children.add(node);
			}
		}

		// 构建设备
		for (VirtualOrgan vo : map.values()) {
			Integer type = vo.getType();
			if (type.intValue() <= TypeDefinition.VIRTUAL_ORGAN) {
				continue;
			}
			// 不可见的忽略
			if (vo.getVisible().intValue() == 0) {
				continue;
			}
			VirtualOrgan parent = map.get(vo.getParentId());

			ListOrganDeviceDTO.Node node = dto.new Node();
			node.setId(vo.getId().toString());
			node.setName(vo.getName());
			node.setRealName(vo.getName());
			// 只支持一层的不可见，将下级的资源加入到上级中
			if (null != parent) {
				if (parent.getVisible().intValue() == 1) {
					node.setPid(vo.getParentId().toString());
				} else {
					node.setPid(MyStringUtil.object2StringNotNull(parent
							.getParentId()));
				}
			} else {
				node.setPid("");
			}
			node.setType(type.toString());
			// 默认都是在线
			node.setIsOnline(true);
			// 视频设备
			if (TypeDefinition.VIDEO_DVR <= type.intValue()
					&& (TypeDefinition.VIDEO_DVR + 1000) > type.intValue()) {
				// 隧道、桥梁、收费站设备不加入机构树
				if (null != parent) {
					if (parent.getType().intValue() == TypeDefinition.ORGAN_BRIDGE
							|| parent.getType().intValue() == TypeDefinition.ORGAN_TUNNEL
							|| parent.getType().intValue() == TypeDefinition.ORGAN_TOLLGATE) {
						continue;
					}
				}

				SvDevice svDevice = svDeviceMap.get(vo.getDeviceId());
				if (null == svDevice) {
					logger.error("VirtualOrgan[" + vo.getId()
							+ "] is a dirty data, which deviceId["
							+ vo.getDeviceId() + "] is not found in SvDevice !");
					continue;
				}
				node.setLatitude(svDevice.getLatitude());
				node.setLongitude(svDevice.getLongitude());
				// 设置厂商
				if (null == svDevice.getManufacturerId()) {
					logger.warn("Manufacture for SvDevice[" + svDevice.getId()
							+ "] is null !");
				} else {
					DicManufacture manufacture = manufacturerMap.get(svDevice
							.getManufacturerId());
					if (null != manufacture) {
						node.setManufacturer(manufacture.getName());
					} else {
						logger.error("SvDevice[" + svDevice.getId()
								+ "] is a dirty data, which  Manufacture["
								+ svDevice.getManufacturerId()
								+ "] is not found in DicManufacture !");
					}
				}
				node.setStakeNumber(svDevice.getStakeNumber());
				node.setStandarNumber(svDevice.getStandardNumber());
				node.setNavigation(svDevice.getNavigation());
				// 离线
				if (null == onlineMap.get(svDevice.getStandardNumber())) {
					node.setIsOnline(false);
					updateOrganNumber(organNumberMap, organCacheMap, node,
							false);
				}
				// 在线
				else {
					updateOrganNumber(organNumberMap, organCacheMap, node, true);
				}
			}
			// 数据设备
			else if (TypeDefinition.VMS_DOOR <= type.intValue()
					&& (TypeDefinition.VMS_DOOR + 2000) > type.intValue()) {
				// 隧道、桥梁、收费站设备不加入机构树
				if (null != parent) {
					if (parent.getType().intValue() == TypeDefinition.ORGAN_BRIDGE
							|| parent.getType().intValue() == TypeDefinition.ORGAN_TUNNEL
							|| parent.getType().intValue() == TypeDefinition.ORGAN_TOLLGATE) {
						continue;
					}
				}

				TmDevice tmDevice = tmDeviceMap.get(vo.getDeviceId());
				if (null == tmDevice) {
					logger.error("VirtualOrgan[" + vo.getId()
							+ "] is a dirty data, which deviceId["
							+ vo.getDeviceId() + "] is not found in TmDevice !");
					continue;
				}
				node.setLatitude(tmDevice.getLatitude());
				node.setLongitude(tmDevice.getLongitude());
				// 设置厂商
				if (null == tmDevice.getManufacturerId()) {
					logger.warn("Manufacture for SvDevice[" + tmDevice.getId()
							+ "] is null !");
				} else {
					DicManufacture manufacture = manufacturerMap.get(tmDevice
							.getManufacturerId());
					if (null != manufacture) {
						node.setManufacturer(manufacture.getName());
					} else {
						logger.error("SvDevice[" + tmDevice.getId()
								+ "] is a dirty data, which  Manufacture["
								+ tmDevice.getManufacturerId()
								+ "] is not found in DicManufacture !");
					}
				}
				node.setStakeNumber(tmDevice.getStakeNumber());
				node.setStandarNumber(tmDevice.getStandardNumber());
				node.setNavigation(tmDevice.getNavigation());
				// 情报板增加width和height
				if (TypeDefinition.VMS_DOOR <= type.intValue()
						&& (TypeDefinition.VMS_DOOR + 100) > type.intValue()) {
					if (StringUtils.isNotBlank(tmDevice.getExtend())) {
						String[] exts = tmDevice.getExtend().split("::");
						node.setWidth(MyStringUtil.getAttributeValue(exts,
								"width"));
						node.setHeight(MyStringUtil.getAttributeValue(exts,
								"height"));
					}
				}

				// 离线
				if (null == onlineMap.get(tmDevice.getStandardNumber())) {
					node.setIsOnline(false);
					updateOrganNumber(organNumberMap, organCacheMap, node,
							false);
				}
				// 在线
				else {
					updateOrganNumber(organNumberMap, organCacheMap, node, true);
				}
			} else {
				// do nothing
			}
			children.add(node);
		}

		// 替换机构名称加上(在线/总量)
		for (ListOrganDeviceDTO.Node organ : organCacheMap.values()) {
			// 隧道、桥梁、收费站不加数量
			if (organ.getType().equals(TypeDefinition.ORGAN_BRIDGE + "")
					|| organ.getType().equals(
							TypeDefinition.ORGAN_TOLLGATE + "")
					|| organ.getType().equals(TypeDefinition.ORGAN_TUNNEL + "")) {
				continue;
			}
			int[] countNumber = organNumberMap.get(organ.getId());
			organ.setName(organ.getName() + "(" + countNumber[0] + "/"
					+ countNumber[1] + ")");
			organ.setOnlineCount(countNumber[0]);
			organ.setTotalCount(countNumber[1]);
		}

		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	@Logon
	public Object listSvOrganDeviceJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();
		// 获取用户组根机构
		User user = userManager.getUser(userId);
		VirtualOrgan rootOrgan = organManager.getUgRootOrgan(user
				.getUserGroupId());
		// 用户组资源集合
		Map<Long, VirtualOrgan> map = organManager
				.mapUgSvVirtualOrgan(rootOrgan.getId());

		List<Long> svDeviceIds = new LinkedList<Long>();
		for (VirtualOrgan vo : map.values()) {
			if (vo.getType().intValue() >= TypeDefinition.VIDEO_DVR
					&& vo.getType().intValue() < TypeDefinition.VIDEO_DVR + 1000) {
				svDeviceIds.add(vo.getDeviceId());
			}
		}

		// 查询视频设备Map, 小于500个用in查询，大于500个查询全表
		if (svDeviceIds.size() > 500) {
			svDeviceIds = null;
		}
		Map<Long, SvDevice> svDeviceMap = svDeviceManager
				.mapSvDevice(svDeviceIds);
		// 查询机构Map
		Map<Long, Organ> organMap = organManager.getOrgans();
		// 查询厂商Map
		Map<Long, DicManufacture> manufacturerMap = dicManufactureManager
				.getvDicManufactureList();
		// 获取当前在线设备列表
		Map<String, OnlineReal> onlineMap = onlineManager.mapOnlineDevice();
		// 记录各机构节点的在线/总数量, key为机构ID, value[0]为在线数量, value[1]为总数量
		Map<String, int[]> organNumberMap = new HashMap<String, int[]>();
		// 所有的机构节点缓存, 处理最后的在线/总量改名, key为机构ID
		Map<String, ListSvOrganDeviceDTO.Node> organCacheMap = new HashMap<String, ListSvOrganDeviceDTO.Node>();

		ListSvOrganDeviceDTO dto = new ListSvOrganDeviceDTO();
		List<ListSvOrganDeviceDTO.Node> children = dto.getChildren();

		// 首先构建所有的机构
		for (VirtualOrgan vo : map.values()) {
			Integer type = vo.getType();
			if (type.intValue() <= TypeDefinition.VIRTUAL_ORGAN
					&& vo.getVisible().intValue() == 1) {
				// 初始化机构设备在线统计数字
				organNumberMap.put(vo.getId().toString(), initNumber());

				VirtualOrgan parent = map.get(vo.getParentId());

				ListSvOrganDeviceDTO.Node node = dto.new Node();
				node.setId(vo.getId().toString());
				node.setName(vo.getName());
				node.setRealName(vo.getName());
				// 只支持一层的不可见，将下级的资源加入到上级中
				if (null != parent) {
					if (parent.getVisible().intValue() == 1) {
						node.setPid(vo.getParentId().toString());
					} else {
						node.setPid(MyStringUtil.object2StringNotNull(parent
								.getParentId()));
					}
				} else {
					node.setPid("");
				}
				node.setType(type.toString());
				// 默认都是在线
				node.setIsOnline(true);
				// 真实机构
				if (TypeDefinition.ORGAN_GENERAL <= type.intValue()
						&& TypeDefinition.VIRTUAL_ORGAN > type.intValue()) {
					Organ realOrgan = organMap.get(vo.getDeviceId());
					if (null == realOrgan) {
						logger.error("VirtualOrgan[" + vo.getId()
								+ "] is a dirty data, which deviceId["
								+ vo.getDeviceId()
								+ "] is not found in Organ !");
						continue;
					}
					node.setStakeNumber(MyStringUtil
							.object2StringNotNull(realOrgan.getBeginStake()));
					node.setStandarNumber(MyStringUtil
							.object2StringNotNull(realOrgan.getStandardNumber()));
				}
				// 虚拟机构
				else {
					// 默认都是在线
					node.setIsOnline(true);
				}
				organCacheMap.put(node.getId(), node);
				children.add(node);
			}
		}

		// 构建设备
		for (VirtualOrgan vo : map.values()) {
			Integer type = vo.getType();
			if (type.intValue() <= TypeDefinition.VIRTUAL_ORGAN) {
				continue;
			}
			// 不可见的忽略
			if (vo.getVisible().intValue() == 0) {
				continue;
			}
			VirtualOrgan parent = map.get(vo.getParentId());

			ListSvOrganDeviceDTO.Node node = dto.new Node();
			node.setId(vo.getId().toString());
			node.setName(vo.getName());
			node.setRealName(vo.getName());
			// 只支持一层的不可见，将下级的资源加入到上级中
			if (null != parent) {
				if (parent.getVisible().intValue() == 1) {
					node.setPid(vo.getParentId().toString());
				} else {
					node.setPid(MyStringUtil.object2StringNotNull(parent
							.getParentId()));
				}
			} else {
				node.setPid("");
			}
			node.setType(type.toString());
			// 默认都是在线
			node.setIsOnline(true);
			SvDevice svDevice = svDeviceMap.get(vo.getDeviceId());
			if (null == svDevice) {
				logger.error("VirtualOrgan[" + vo.getId()
						+ "] is a dirty data, which deviceId["
						+ vo.getDeviceId() + "] is not found in SvDevice !");
				continue;
			}
			node.setIp(svDevice.getIp());
			node.setLocation(svDevice.getLocation());
			node.setPort(svDevice.getPort());
			// 设置厂商
			if (null == svDevice.getManufacturerId()) {
				logger.warn("Manufacture for SvDevice[" + svDevice.getId()
						+ "] is null !");
			} else {
				DicManufacture manufacture = manufacturerMap.get(svDevice
						.getManufacturerId());
				if (null != manufacture) {
					node.setManufacturer(manufacture.getName());
				} else {
					logger.error("SvDevice[" + svDevice.getId()
							+ "] is a dirty data, which  Manufacture["
							+ svDevice.getManufacturerId()
							+ "] is not found in DicManufacture !");
				}
			}
			node.setStakeNumber(svDevice.getStakeNumber());
			node.setStandarNumber(svDevice.getStandardNumber());

			// 离线
			if (null == onlineMap.get(svDevice.getStandardNumber())) {
				node.setIsOnline(false);
				updateSvOrganNumber(organNumberMap, organCacheMap, node, false);
			}
			// 在线
			else {
				updateSvOrganNumber(organNumberMap, organCacheMap, node, true);
			}

			children.add(node);
		}

		// 替换机构名称加上(在线/总量)
		for (ListSvOrganDeviceDTO.Node organ : organCacheMap.values()) {
			int[] countNumber = organNumberMap.get(organ.getId());
			organ.setName(organ.getName() + "(" + countNumber[0] + "/"
					+ countNumber[1] + ")");
			organ.setOnlineCount(countNumber[0]);
			organ.setTotalCount(countNumber[1]);
		}

		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	private int[] initNumber() {
		return new int[] { 0, 0 };
	}

	private void updateOrganNumber(Map<String, int[]> organNumberMap,
			Map<String, ListOrganDeviceDTO.Node> organCacheMap,
			ListOrganDeviceDTO.Node node, boolean onlineFlag) {
		ListOrganDeviceDTO.Node parent = organCacheMap.get(node.getPid());
		if (null == parent) {
			return;
		}
		int[] parentNumber = organNumberMap.get(node.getPid());
		// 在线
		if (onlineFlag) {
			parentNumber[0]++;
			parentNumber[1]++;
		} else {
			parentNumber[1]++;
		}
		organNumberMap.put(node.getPid(), parentNumber);
		updateOrganNumber(organNumberMap, organCacheMap, parent, onlineFlag);
	}

	public Object loginOut(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer kickFlag = reader.getInteger("kickFlag", true);
		String ticket = (String) request.getSession().getAttribute("ticket");
		// kickFlag默认为0正常退出
		if (null == kickFlag) {
			kickFlag = 0;
		}

		OnlineReal onlineReal = onlineManager.getOnlineReal(ticket);
		if (null != onlineReal) {
			if (logger.isDebugEnabled()) {
				logger.debug("Offline Ticket[" + ticket + "]");
			}
			onlineManager.offline(onlineReal, kickFlag);
		}

		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("LoginOut");

		return dto;
	}

	private void updateSvOrganNumber(Map<String, int[]> organNumberMap,
			Map<String, ListSvOrganDeviceDTO.Node> organCacheMap,
			ListSvOrganDeviceDTO.Node node, boolean onlineFlag) {
		ListSvOrganDeviceDTO.Node parent = organCacheMap.get(node.getPid());
		if (null == parent) {
			return;
		}
		int[] parentNumber = organNumberMap.get(node.getPid());
		// 在线
		if (onlineFlag) {
			parentNumber[0]++;
			parentNumber[1]++;
		} else {
			parentNumber[1]++;
		}
		organNumberMap.put(node.getPid(), parentNumber);
		updateSvOrganNumber(organNumberMap, organCacheMap, parent, onlineFlag);
	}

	@Logon
	public Object listDeviceStatusJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();

		User user = userManager.getUser(userId);
		// 根据用户组获取设备列表
		List<VirtualOrgan> devices = organManager.listUgDevice(user
				.getUserGroupId());
		// 获取当前在线设备列表
		Map<Long, OnlineReal> map = onlineManager.mapOnlineAllDevice();

		ListDeviceStatusDTO dto = new ListDeviceStatusDTO();
		List<ListDeviceStatusDTO.DeviceStatusVO> list = new ArrayList<ListDeviceStatusDTO.DeviceStatusVO>();
		for (VirtualOrgan device : devices) {
			ListDeviceStatusDTO.DeviceStatusVO vo = dto.new DeviceStatusVO();
			vo.setId(device.getId() + "");
			if (null == map.get(device.getDeviceId())) {
				vo.setOnline(false);
			} else {
				vo.setOnline(true);
			}
			list.add(vo);
		}
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setListStatus(list);
		return dto;
	}

	@Logon
	public Object listCameraStatusJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();

		User user = userManager.getUser(userId);
		// 获取虚拟机构摄像头列表
		List<VirtualOrgan> listCamera = organManager.listUgAllCamera(user
				.getUserGroupId());
		// 获取当前在线摄像头列表
		Map<Long, OnlineReal> onlineMap = onlineManager.mapOnlineCamera();
		ListDeviceStatusDTO dto = new ListDeviceStatusDTO();
		List<ListDeviceStatusDTO.DeviceStatusVO> list = new ArrayList<ListDeviceStatusDTO.DeviceStatusVO>();
		for (VirtualOrgan camera : listCamera) {
			ListDeviceStatusDTO.DeviceStatusVO vo = dto.new DeviceStatusVO();
			vo.setId(camera.getId() + "");
			if (null == onlineMap.get(camera.getDeviceId())) {
				vo.setOnline(false);
			} else {
				vo.setOnline(true);
			}
			list.add(vo);
		}
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setListStatus(list);
		return dto;
	}

	public Object getCameraStatusJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		VirtualOrgan camera = organManager.getVOrgan(id);
		if (null == camera) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"param id " + id + " invalid");
		}
		Long deviceId = camera.getDeviceId();
		OnlineReal real = onlineManager.getOnlineRealByDeviceId(deviceId);
		Online online = null;
		if (real == null) {
			online = onlineManager.getOnlineByDeviceId(deviceId);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GetCameraStatusDTO dto = new GetCameraStatusDTO();
		GetCameraStatusDTO.CameraStatus status = dto.new CameraStatus();
		if (real != null) {
			status.setId(id + "");
			status.setOnline("1");
			status.setUpdateTime(sdf.format(real.getUpdateTime()));
		} else {
			if (online != null) {
				status.setId(id + "");
				status.setOnline("0");
				status.setUpdateTime(sdf.format(online.getOfflineTime()));
			}
		}
		dto.setCameraStatus(status);
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}
}
