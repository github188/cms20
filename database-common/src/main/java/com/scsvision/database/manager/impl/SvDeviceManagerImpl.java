package com.scsvision.database.manager.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.database.dao.StandardNumberDAO;
import com.scsvision.database.dao.SvDeviceDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.DicManufacture;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.SvDeviceManager;
import com.scsvision.database.service.vo.OrganDeviceVO;

/**
 * @author sjt
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class SvDeviceManagerImpl implements SvDeviceManager {

	@EJB(beanName = "SvDeviceDAOImpl")
	private SvDeviceDAO svDeviceDAO;

	@EJB(beanName = "StandardNumberDAOImpl")
	private StandardNumberDAO snDAO;

	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vOrganDAO;

	@Override
	public SvDevice getSvDevice(Long deviceId) {
		return svDeviceDAO.get(deviceId);
	}

	@Override
	public Map<Long, SvDevice> getSvDevices() {
		List<SvDevice> list = svDeviceDAO.list();
		Map<Long, SvDevice> map = new HashMap<Long, SvDevice>();
		for (SvDevice sd : list) {
			map.put(sd.getId(), sd);
		}
		return map;
	}

	@Override
	public void saveSvMarkers(SvDevice svDevice) {
		svDeviceDAO.update(svDevice);
	}

	@Override
	@Cache
	public Map<Long, SvDevice> mapSvDevice(List<Long> ids) {
		return svDeviceDAO.map(ids);
	}

	@Override
	public Element generateOrganTree(VirtualOrgan rootOrgan,
			Map<Long, Organ> organMap, Map<Long, SvDevice> deviceMap,
			List<VirtualOrgan> list, Map<Long, DicManufacture> manufacturerMap) {
		// 机构节点映射
		Map<Long, Element> nodeMap = new HashMap<Long, Element>();

		// 构建机构树
		Element parent = DocumentHelper.createElement("Node");
		initOrgan(parent, rootOrgan, organMap);
		nodeMap.put(rootOrgan.getId(), parent);
		// 从根机构的下级开始构建
		int currentDeep = MyStringUtil.countCharInString(rootOrgan.getPath(),
				'/') + 1;
		// 是否循环的标志
		boolean flag = true;
		// 二重循环构建机构树
		while (flag) {
			flag = false;
			for (VirtualOrgan vo : list) {
				int deep = MyStringUtil.countCharInString(vo.getPath(), '/');
				if (deep == currentDeep) {
					// 存在下级机构，继续循环
					flag = true;
					// 上级机构
					Element parentElement = nodeMap.get(vo.getParentId());
					if (vo.getType().intValue() <= TypeDefinition.VIRTUAL_ORGAN) {
						// 可见机构
						if (vo.getVisible().intValue() == 1) {
							Element child = DocumentHelper
									.createElement("Node");
							initOrgan(child, vo, organMap);
							nodeMap.put(vo.getId(), child);
							parentElement.element("SubNodes").add(child);
						}
						// 不可见，只需增加映射为上级机构
						else {
							nodeMap.put(vo.getId(), parentElement);
						}
					} else if (vo.getType().intValue() > TypeDefinition.VIDEO_DVS
							&& vo.getType().intValue() < TypeDefinition.VMS_DOOR) {
						Element channel = DocumentHelper
								.createElement("Channel");
						initDevice(channel, vo, deviceMap, manufacturerMap);
						Element channelList = parentElement
								.element("ChannelList");
						if (null == channelList) {
							channelList = DocumentHelper
									.createElement("ChannelList");
							channelList.add(channel);
							parentElement.add(channelList);
						} else {
							channelList.add(channel);
						}

					}
				}
			}
			currentDeep++;
		}
		return parent;
	}

	private void initOrgan(Element node, VirtualOrgan vo,
			Map<Long, Organ> organMap) {
		node.addAttribute("Id", vo.getId().toString());
		node.addAttribute("Name",
				MyStringUtil.object2StringNotNull(vo.getName()));
		node.addAttribute("Type",
				MyStringUtil.object2StringNotNull(vo.getType()));

		// 真实机构
		Organ realOrgan = null;
		if (vo.getType().intValue() < TypeDefinition.VIRTUAL_ORGAN) {
			realOrgan = organMap.get(vo.getDeviceId());
		}
		if (null != realOrgan) {
			node.addAttribute("StandardNumber", MyStringUtil
					.object2StringNotNull(realOrgan.getStandardNumber()));
			if (StringUtils.isNotBlank(realOrgan.getBeginStake())
					&& StringUtils.isNotBlank(realOrgan.getEndStake())) {
				node.addAttribute("StakeNumber", MyStringUtil
						.object2StringNotNull(realOrgan.getBeginStake() + "+"
								+ realOrgan.getEndStake()));
			} else {
				node.addAttribute("StakeNumber", MyStringUtil
						.object2StringNotNull(realOrgan.getBeginStake()));
			}

		} else {
			node.addAttribute("StandardNumber", "");
			node.addAttribute("StakeNumber", "");
		}

		// 子机构
		Element children = DocumentHelper.createElement("SubNodes");
		node.add(children);
	}

	private void initDevice(Element node, VirtualOrgan vo,
			Map<Long, SvDevice> deviceMap,
			Map<Long, DicManufacture> manufacturerMap) {
		node.addAttribute("Id", vo.getId().toString());
		node.addAttribute("Name",
				MyStringUtil.object2StringNotNull(vo.getName()));
		SvDevice device = deviceMap.get(vo.getDeviceId());
		if (null == device) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"SvDevice[" + vo.getDeviceId() + "] not found !");
		}
		node.addAttribute("StandardNumber",
				MyStringUtil.object2StringNotNull(device.getStandardNumber()));
		node.addAttribute("ChannelNumber",
				MyStringUtil.object2StringNotNull(device.getChannelNumber()));
		node.addAttribute("StakeNumber",
				MyStringUtil.object2StringNotNull(device.getStakeNumber()));
		node.addAttribute("Location",
				MyStringUtil.object2StringNotNull(device.getLocation()));
		node.addAttribute(
				"Manufacture",
				MyStringUtil.object2StringNotNull(manufacturerMap.get(
						device.getManufacturerId()).getName()));
		node.addAttribute("IP",
				MyStringUtil.object2StringNotNull(device.getIp()));
		node.addAttribute("Port",
				MyStringUtil.object2StringNotNull(device.getPort()));
		node.addAttribute(
				"Type",
				MyStringUtil.object2StringNotNull(device.getType()
						+ device.getSubType()));
	}

	@Override
	public Element createOrganTree(Long organId, VirtualOrgan rootOrgan,
			List<OrganDeviceVO> devices, String isRec) {
		// 自身节点
		Element node = DocumentHelper.createElement("Node");

		// 设备节点
		Element channelList = DocumentHelper.createElement("ChannelList");
		node.add(channelList);

		// 子机构节点
		Element subNodes = DocumentHelper.createElement("SubNodes");
		node.add(subNodes);
		// 不可见节点map
		Map<Long, OrganDeviceVO> nuvo = new HashMap<Long, OrganDeviceVO>();
		// 可见节点列表
		List<OrganDeviceVO> list = new LinkedList<OrganDeviceVO>();
		for (OrganDeviceVO vo : devices) {
			if (vo.getVisible().intValue() == 0) {
				nuvo.put(vo.getId(), vo);
			} else {
				list.add(vo);
			}
		}
		if (null != rootOrgan) {
			node.addAttribute("Id", rootOrgan.getId().toString());
			node.addAttribute("StandardNumber", "");
			node.addAttribute("Name", rootOrgan.getName());
			node.addAttribute("Latitude", "");
			node.addAttribute("Longitude", "");
			node.addAttribute("StakeNumber", "");
			node.addAttribute("Type", rootOrgan.getType().toString());
		}
		for (OrganDeviceVO vo : list) {
			if (vo.getId().equals(organId)) {
				node.addAttribute("Id", vo.getId().toString());
				node.addAttribute("StandardNumber", vo.getStandardNumber());
				node.addAttribute("Name", vo.getName());
				node.addAttribute("Latitude", vo.getLatitude());
				node.addAttribute("Longitude", vo.getLongitude());
				node.addAttribute("StakeNumber", vo.getStakeNumber());
				node.addAttribute("Type", vo.getType().toString());
			} else if (null != vo.getParentId()
					&& (vo.getParentId().equals(organId) || null != nuvo.get(vo
							.getParentId())
							&& (vo.getParentId().equals(
									nuvo.get(vo.getParentId()).getId()) && nuvo
									.get(vo.getParentId()).getParentId()
									.equals(organId)))) {
				if (vo.getType().intValue() > TypeDefinition.VIDEO_DVS
						&& vo.getType().intValue() < TypeDefinition.VMS_DOOR) {
					Element channel = createChannel(vo);
					channelList.add(channel);
				} else {
					if ("1".equals(isRec)) {
						Element subNode = createOrganTree(vo.getId(),
								rootOrgan, devices, isRec);
						subNodes.add(subNode);
					} else {
						Element subNode = createSubNode(vo);
						subNodes.add(subNode);
					}
				}
			}
		}
		return node;
	}

	private Element createSubNode(OrganDeviceVO vo) {
		Element node = DocumentHelper.createElement("Node");
		node.addAttribute("Id", vo.getId().toString());
		node.addAttribute("StandardNumber",
				MyStringUtil.object2StringNotNull(vo.getStandardNumber()));
		node.addAttribute("Name",
				MyStringUtil.object2StringNotNull(vo.getName()));
		node.addAttribute("Latitude",
				MyStringUtil.object2StringNotNull(vo.getLatitude()));
		node.addAttribute("Longitude",
				MyStringUtil.object2StringNotNull(vo.getLongitude()));
		node.addAttribute("StakeNumber",
				MyStringUtil.object2StringNotNull(vo.getStakeNumber()));
		node.addAttribute("Type",
				MyStringUtil.object2StringNotNull(vo.getType()));
		return node;
	}

	private Element createChannel(OrganDeviceVO vo) {
		Element channel = DocumentHelper.createElement("Channel");
		channel.addAttribute("Id", vo.getId().toString());
		channel.addAttribute("StandardNumber",
				MyStringUtil.object2StringNotNull(vo.getStandardNumber()));
		channel.addAttribute("Name",
				MyStringUtil.object2StringNotNull(vo.getName()));
		channel.attributeValue("ChannelNumber",
				MyStringUtil.object2StringNotNull(vo.getChannelNumber()));
		channel.addAttribute("Latitude",
				MyStringUtil.object2StringNotNull(vo.getLatitude()));
		channel.addAttribute("Longitude",
				MyStringUtil.object2StringNotNull(vo.getLongitude()));
		channel.addAttribute("StakeNumber",
				MyStringUtil.object2StringNotNull(vo.getStakeNumber()));
		channel.addAttribute("Type",
				MyStringUtil.object2StringNotNull(vo.getType()));
		channel.addAttribute("Manufacture",
				MyStringUtil.object2StringNotNull(vo.getManufacture()));
		channel.addAttribute("SolarId",
				MyStringUtil.object2StringNotNull(vo.getSolarId()));
		channel.addAttribute("Ip",
				MyStringUtil.object2StringNotNull(vo.getIp()));
		channel.addAttribute("Port",
				MyStringUtil.object2StringNotNull(vo.getPort()));
		channel.addAttribute("DeviceModel",
				MyStringUtil.object2StringNotNull(vo.getDeviceModel()));
		channel.addAttribute("Decode",
				MyStringUtil.object2StringNotNull(vo.getDecode()));
		return channel;
	}

	@Override
	public Element getChannelList(List<VirtualOrgan> list,
			Map<Long, SvDevice> map, Map<Long, DicManufacture> manufactureMap,
			VirtualOrgan node) {
		// 设备节点
		Element root = DocumentHelper.createElement("Response");
		root.addAttribute("Method", "ListChannel");
		root.addAttribute("Code", "200");
		root.addAttribute("Message", "");
		for (VirtualOrgan vo : list) {
			if (vo.getVisible() == 1
					&& vo.getType().intValue() >= TypeDefinition.VIDEO_DVR
					&& vo.getType().intValue() < TypeDefinition.VMS_DOOR) {
				Element channel = DocumentHelper.createElement("Channel");
				channel.addAttribute("Id", vo.getId().toString());
				channel.addAttribute(
						"StandardNumber",
						MyStringUtil.object2StringNotNull(map.get(
								vo.getDeviceId()).getStandardNumber()));
				channel.addAttribute("Name",
						MyStringUtil.object2StringNotNull(vo.getName()));
				channel.attributeValue(
						"ChannelNumber",
						MyStringUtil.object2StringNotNull(map.get(
								vo.getDeviceId()).getChannelNumber()));
				channel.addAttribute(
						"Latitude",
						MyStringUtil.object2StringNotNull(map.get(
								vo.getDeviceId()).getLatitude()));
				channel.addAttribute(
						"Longitude",
						MyStringUtil.object2StringNotNull(map.get(
								vo.getDeviceId()).getLongitude()));
				channel.addAttribute(
						"StakeNumber",
						MyStringUtil.object2StringNotNull(map.get(
								vo.getDeviceId()).getStakeNumber()));
				channel.addAttribute("Type",
						MyStringUtil.object2StringNotNull(vo.getType()));
				channel.addAttribute("Manufacture", MyStringUtil
						.object2StringNotNull(manufactureMap.get(
								map.get(vo.getDeviceId()).getManufacturerId())
								.getName()));
				channel.addAttribute(
						"SolarId",
						MyStringUtil.object2StringNotNull(map.get(
								vo.getDeviceId()).getSolarBatteryId()));
				channel.addAttribute(
						"DeviceModel",
						MyStringUtil.object2StringNotNull(map.get(
								vo.getDeviceId()).getDeviceModel()));
				root.add(channel);
			}
		}
		return root;
	}

	@Override
	public List<SvDevice> listSvDeviceByPtsId(Long ptsId) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("ptsId", ptsId);
		List<SvDevice> list = svDeviceDAO.findByPropertys(params);
		return list;
	}

	@Override
	public Element getPTSChannelList(List<SvDevice> list,
			Map<Long, DicManufacture> manufactureMap) {
		// 设备节点
		Element root = DocumentHelper.createElement("Response");
		root.addAttribute("Method", "ListPtsDevice");
		root.addAttribute("Code", "200");
		root.addAttribute("Message", "");

		// 设备树节点映射
		Map<Long, Element> nodeMap = new HashMap<Long, Element>();
		// 设备节点映射
		Map<Long, SvDevice> map = new HashMap<Long, SvDevice>();
		for (SvDevice device : list) {
			if ((device.getType() + device.getSubType())
					.equals(TypeDefinition.VIDEO_DVR + "")) {
				Element node = DocumentHelper.createElement("Platform");
				node.addAttribute("IP",
						MyStringUtil.object2StringNotNull(device.getIp()));
				node.addAttribute("Port",
						MyStringUtil.object2StringNotNull(device.getPort()));
				node.addAttribute("User",
						MyStringUtil.object2StringNotNull(device.getUser()));
				node.addAttribute("Pwd",
						MyStringUtil.object2StringNotNull(device.getPassword()));
				node.addAttribute(
						"Protocol",
						MyStringUtil.object2StringNotNull(manufactureMap.get(
								device.getManufacturerId()).getProtocol()));
				Element extend = DocumentHelper.createElement("Extend");
				extend.addText(MyStringUtil.object2StringNotNull(device
						.getExtend()));
				node.add(extend);
				root.add(node);
				nodeMap.put(device.getId(), node);
				map.put(device.getId(), device);
			}
		}
		for (SvDevice device : list) {
			if (!device.getType().equals("30")) {
				SvDevice sd = map.get(device.getParentId());
				if (device.getParentId().longValue() == sd.getId().longValue()) {
					Element node = DocumentHelper.createElement("Camera");
					node.addAttribute("SN", MyStringUtil
							.object2StringNotNull(device.getStandardNumber()));
					node.addAttribute("Id", MyStringUtil
							.object2StringNotNull(device.getChannelNumber()));
					nodeMap.get(device.getParentId()).add(node);
				}
			}
		}
		return root;
	}

	@Override
	public void deleteSvDevice(Long organId) {
		svDeviceDAO.deleteSvDevice(organId);
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("organId", organId);
		List<SvDevice> list = svDeviceDAO.findByPropertys(params);
		for (SvDevice entity : list) {
			snDAO.sync(entity.getStandardNumber(), entity.getName(),
					SvDevice.class, entity.getType().toString(), true);
		}
	}

	@Override
	public Long createDvr(Long organId, String subType, String standardNumber,
			String name, String location, Long ptsId, Long manufacturerId,
			Integer channelAmount, String ip, String port, String extend,
			String user, String password) {
		SvDevice sd = new SvDevice();
		sd.setOrganId(organId);
		sd.setStandardNumber(standardNumber);
		sd.setName(name);
		sd.setLocation(location);
		sd.setPtsId(ptsId);
		sd.setManufacturerId(manufacturerId);
		sd.setChannelAmount(channelAmount);
		sd.setIp(ip);
		sd.setPort(port);
		sd.setExtend(extend);
		sd.setUser(user);
		sd.setPassword(password);
		sd.setCreateTime(System.currentTimeMillis());
		sd.setIsLocal(TypeDefinition.LOCAL_PLATFORM_RESOURCE);
		sd.setType(TypeDefinition.VIDEO_DVR / 100 + "");
		sd.setSubType(subType);
		svDeviceDAO.save(sd);
		snDAO.sync(sd.getStandardNumber(), sd.getName(), SvDevice.class,
				sd.getType() + sd.getSubType(), false);
		return sd.getId();
	}

	@Override
	public void updateDvr(Long id, Long organId, String subType,
			String standardNumber, String name, String location, Long ptsId,
			Long manufacturerId, Integer channelAmount, String ip, String port,
			String extend, String user, String password) {
		SvDevice sd = svDeviceDAO.get(id);
		if (null != organId) {
			sd.setOrganId(organId);
		}
		if (StringUtils.isNotBlank(subType)) {
			sd.setSubType(subType);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			sd.setStandardNumber(standardNumber);
		}
		if (StringUtils.isNotBlank(name)) {
			sd.setName(name);
		}
		if (StringUtils.isNotBlank(location)) {
			sd.setLocation(location);
		}
		if (null != ptsId) {
			sd.setPtsId(ptsId);
		}
		if (null != manufacturerId) {
			sd.setManufacturerId(manufacturerId);
		}
		if (null != channelAmount) {
			// 可以加一个自身下摄像头的数量判断
			sd.setChannelAmount(channelAmount);
		}
		if (StringUtils.isNotBlank(ip)) {
			sd.setIp(ip);
		}
		if (StringUtils.isNotBlank(port)) {
			sd.setPort(port);
		}
		if (StringUtils.isNotBlank(extend)) {
			sd.setExtend(extend);
		}
		if (StringUtils.isNotBlank(user)) {
			sd.setUser(user);
		}
		if (StringUtils.isNotBlank(password)) {
			sd.setPassword(password);
		}
		svDeviceDAO.update(sd);
		snDAO.sync(sd.getStandardNumber(), sd.getName(), SvDevice.class,
				sd.getType() + sd.getSubType(), false);
	}

	@Override
	public SvDevice getDvr(Long id) {
		return svDeviceDAO.get(id);
	}

	@Override
	public Integer countDvr(String ip, String name) {
		return svDeviceDAO.countDvr(ip, name);
	}

	@Override
	public List<SvDevice> listDvr(String ip, String name, Integer start,
			Integer limit) {
		return svDeviceDAO.listDvr(ip, name, start, limit);
	}

	@Override
	public Long createCamera(String standardNumber, String name, Long parentId,
			String location, Long organId, Long crsId, Long manufacturerId,
			Long solarBatteryId, Long gpsId, String channelNumber,
			String longitude, String latitude, String stakeNumber,
			String navigation, Integer storeType, String extend, String owner,
			String civilCode, String block, String certNum, String certifiable,
			String errCode, Long endTime, String secrecy, String deviceModel,
			String subType, Long ptsId) {
		// 检查通道号是否重复
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("parentId", parentId);
		params.put("channelNumber", channelNumber);
		List<SvDevice> list = svDeviceDAO.findByPropertys(params);
		if (list.size() > 0) {
			throw new BusinessException(ErrorCode.CHANNEL_NUMBER_EXIST,
					"channelNumber[" + channelNumber + "] is already exist !");
		}
		params.clear();
		// 检查dvr下视频通道是否达到最大数
		SvDevice dvr = svDeviceDAO.get(parentId);
		params.put("parentId", parentId);
		List<SvDevice> count = svDeviceDAO.findByPropertys(params);
		if (dvr.getChannelAmount() <= count.size()) {
			throw new BusinessException(ErrorCode.CHANNEL_AMOUNT_OVER_LIMIT,
					"Channel amount[" + dvr.getChannelAmount()
							+ "] over limit !");
		}

		// 保存摄像头
		SvDevice camera = new SvDevice();
		camera.setBlock(block);
		camera.setCertifiable(certifiable);
		camera.setCertNum(certNum);
		camera.setChannelNumber(channelNumber);
		camera.setCivilCode(civilCode);
		camera.setCreateTime(System.currentTimeMillis());
		camera.setCrsId(crsId);
		camera.setDeviceModel(deviceModel);
		camera.setEndTime(endTime);
		camera.setErrCode(errCode);
		camera.setExtend(extend);
		camera.setGpsId(gpsId);
		camera.setIsLocal(TypeDefinition.LOCAL_PLATFORM_RESOURCE);
		camera.setLatitude(latitude);
		camera.setLocation(location);
		camera.setLongitude(longitude);
		camera.setManufacturerId(manufacturerId);
		camera.setName(name);
		camera.setNavigation(navigation);
		camera.setOrganId(organId);
		camera.setOwner(owner);
		camera.setParentId(parentId);
		// camera.setPathName(pathName);
		camera.setSecrecy(secrecy);
		camera.setSolarBatteryId(solarBatteryId);
		camera.setStakeNumber(stakeNumber);
		camera.setStandardNumber(standardNumber);
		camera.setStoreType(storeType);
		camera.setSubType(subType);
		camera.setPtsId(ptsId);
		camera.setType(TypeDefinition.VIDEO_CAMERA_BALL / 100 + "");
		if (storeType == 0) {
			camera.setLocalStorePlan(TypeDefinition.LOCAL_STORE_PLAN_DEFAULT);
			camera.setCenterStorePlan(null);
		} else if (storeType == 1) {
			if (null != crsId) {
				camera.setCenterStorePlan(TypeDefinition.STORE_PLAN_DEFAULT);
				camera.setLocalStorePlan(null);
			} else {
				camera.setCenterStorePlan(null);
				camera.setLocalStorePlan(null);
			}
		} else if (storeType == 2) {
			if (null != crsId) {
				camera.setCenterStorePlan(TypeDefinition.STORE_PLAN_DEFAULT);
				camera.setLocalStorePlan(TypeDefinition.LOCAL_STORE_PLAN_DEFAULT);
			} else {
				camera.setCenterStorePlan(null);
				camera.setLocalStorePlan(TypeDefinition.LOCAL_STORE_PLAN_DEFAULT);
			}
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"StoreType :" + storeType + " invalid");
		}
		svDeviceDAO.save(camera);
		snDAO.sync(camera.getStandardNumber(), camera.getName(), Organ.class,
				camera.getType().toString(), false);
		return camera.getId();
	}

	@Override
	public void updateCamera(Long id, String standardNumber, String name,
			Long parentId, String location, Long organId, Long crsId,
			Long manufacturerId, Long solarBatteryId, Long gpsId,
			String channelNumber, String longitude, String latitude,
			String stakeNumber, String navigation, Integer storeType,
			String extend, String owner, String civilCode, String block,
			String certNum, String certifiable, String errCode, Long endTime,
			String secrecy, String deviceModel, String subType,
			String centerStorePlan, String localStorePlan, Long ptsId) {
		// 检查通道号是否重复
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("channelNumber", channelNumber);
		params.put("parent.id", parentId);
		List<SvDevice> list = svDeviceDAO.findByPropertys(params);
		if (list.size() >= 1) {
			if (!id.equals(list.get(0).getId())) {
				throw new BusinessException(ErrorCode.CHANNEL_NUMBER_EXIST,
						"channelId[" + channelNumber + "] is already exist !");
			}
		}
		params.clear();
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = svDeviceDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		SvDevice camera = svDeviceDAO.get(id);
		if (null != standardNumber) {
			camera.setStandardNumber(standardNumber);
		}
		if (null != name) {
			camera.setName(name);
		}
		if (null != parentId) {
			camera.setParentId(parentId);
		}
		if (null != location) {
			camera.setLocation(location);
		}
		if (null != organId) {
			camera.setOrganId(organId);
		}
		if (null != crsId) {
			camera.setCrsId(crsId);
		}
		if (null != manufacturerId) {
			camera.setManufacturerId(manufacturerId);
		}
		if (null != solarBatteryId) {
			camera.setSolarBatteryId(solarBatteryId);
		}
		if (null != gpsId) {
			camera.setGpsId(gpsId);
		}
		if (null != channelNumber) {
			camera.setChannelNumber(channelNumber);
		}
		if (null != longitude) {
			camera.setLongitude(longitude);
		}
		if (null != latitude) {
			camera.setLatitude(latitude);
		}
		if (null != stakeNumber) {
			camera.setStakeNumber(stakeNumber);
		}
		if (null != navigation) {
			camera.setNavigation(navigation);
		}
		if (null != storeType) {
			if (storeType == 0) {
				if (null != localStorePlan) {
					camera.setLocalStorePlan(localStorePlan);
				}
				camera.setCenterStorePlan(null);
			} else if (storeType == 1) {
				if (null != crsId) {
					if (null != centerStorePlan) {
						camera.setCenterStorePlan(centerStorePlan);
					}
				} else {
					camera.setCenterStorePlan(null);
				}
				camera.setLocalStorePlan(null);
			} else if (storeType == 2) {
				if (null != crsId) {
					if (null != centerStorePlan) {
						camera.setCenterStorePlan(centerStorePlan);
					}
				} else {
					camera.setCenterStorePlan(null);
				}
				if (null != localStorePlan) {
					camera.setLocalStorePlan(localStorePlan);
				}
			}
			camera.setStoreType(storeType);
		} else {
			if (null != centerStorePlan) {
				camera.setCenterStorePlan(centerStorePlan);
			}
			if (null != localStorePlan) {
				camera.setLocalStorePlan(localStorePlan);
			}
		}
		if (null != extend) {
			camera.setExtend(extend);
		}
		if (null != owner) {
			camera.setOwner(owner);
		}
		if (null != civilCode) {
			camera.setCivilCode(civilCode);
		}
		if (null != block) {
			camera.setBlock(block);
		}
		if (null != certNum) {
			camera.setCertNum(certNum);
		}
		if (null != certifiable) {
			camera.setCertifiable(certifiable);
		}
		if (null != errCode) {
			camera.setErrCode(errCode);
		}
		if (null != endTime) {
			camera.setEndTime(endTime);
		}
		if (null != secrecy) {
			camera.setSecrecy(secrecy);
		}
		if (null != deviceModel) {
			camera.setDeviceModel(deviceModel);
		}
		if (null != subType) {
			camera.setSubType(subType);
		}
		if (null != ptsId) {
			camera.setPtsId(ptsId);
		}
		svDeviceDAO.update(camera);
		snDAO.sync(camera.getStandardNumber(), camera.getName(),
				SvDevice.class, camera.getType(), false);
	}

	@Override
	public void deleteCamera(Long id) {
		// 删除虚拟机构
		vOrganDAO.deleteByDeviceId(id);
		// 删除事件？

		// 删除摄像头
		SvDevice camera = svDeviceDAO.get(id);
		svDeviceDAO.delete(camera);
		// 同步sn
		snDAO.sync(camera.getStandardNumber(), camera.getName(), Organ.class,
				camera.getType().toString(), true);
	}

	@Override
	public List<SvDevice> listCamera(String name, String stakeNumber,
			String standardNumber, Long dvrId, Integer start, Integer limit) {
		return svDeviceDAO.listCamera(name, stakeNumber, standardNumber, dvrId,
				start, limit);
	}

	@Override
	public int countCamera(String name, String stakeNumber,
			String standardNumber, Long dvrId) {
		return svDeviceDAO
				.countCamera(name, stakeNumber, standardNumber, dvrId);
	}

	@Override
	public void deleteDvr(Long id) {
		List<SvDevice> cameras = svDeviceDAO.listCamera(null, null, null, id,
				0, 1000);
		// 删除摄像头
		for (SvDevice sd : cameras) {
			deleteCamera(sd.getId());
		}
		// 删除视频服务器
		SvDevice dvr = svDeviceDAO.get(id);
		svDeviceDAO.delete(dvr);
		// 同步sn
		snDAO.sync(dvr.getStandardNumber(), dvr.getName(), Organ.class, dvr
				.getType().toString(), true);
	}

	@Override
	@Cache
	public Map<String, Long> mapBySn() {
		List<SvDevice> list = svDeviceDAO.list();
		Map<String, Long> map = new HashMap<String, Long>();
		for (SvDevice device : list) {
			map.put(device.getStandardNumber(), device.getId());
		}
		return map;
	}

	@Override
	@Cache
	public List<SvDevice> listCameraByStake(float floatStake, Long organId,
			Integer range) {
		Float beginStake = Float
				.valueOf(floatStake - range.floatValue() / 1000);
		Float endStake = Float.valueOf(floatStake + range.floatValue() / 1000);
		List<SvDevice> cameras = svDeviceDAO.listCameraByStake(organId,
				beginStake, endStake);
		return cameras;
	}

	@Override
	public void updateDeviceLocation(Long id, String longitude, String latitude) {
		SvDevice entity = svDeviceDAO.get(id);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
		svDeviceDAO.update(entity);
	}
}
