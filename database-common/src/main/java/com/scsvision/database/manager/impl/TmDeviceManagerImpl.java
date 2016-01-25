package com.scsvision.database.manager.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.database.dao.OrganDAO;
import com.scsvision.database.dao.StandardNumberDAO;
import com.scsvision.database.dao.TmDeviceDAO;
import com.scsvision.database.entity.DicManufacture;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.TmDeviceManager;

/**
 * TmDeviceManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:30:54
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class TmDeviceManagerImpl implements TmDeviceManager {

	private final Logger logger = LoggerFactory
			.getLogger(TmDeviceManagerImpl.class);

	@EJB(beanName = "TmDeviceDAOImpl")
	private TmDeviceDAO tmDeviceDAO;
	@EJB(beanName = "OrganDAOImpl")
	private OrganDAO organDAO;
	@EJB(beanName = "StandardNumberDAOImpl")
	private StandardNumberDAO snDAO;

	@Override
	@Cache
	public Map<Long, TmDevice> mapTmDevice(List<Long> ids) {
		return tmDeviceDAO.map(ids);
	}

	@Override
	@Cache
	public Element generateOrganTree(VirtualOrgan rootOrgan,
			Map<Long, Organ> organMap, Map<Long, TmDevice> deviceMap,
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
		// 是否继续循环标志
		boolean flag = true;
		// 二重循环构建机构树
		while (flag) {
			flag = false;
			for (VirtualOrgan vo : list) {
				int deep = MyStringUtil.countCharInString(vo.getPath(), '/');
				if (deep == currentDeep) {
					// 存在下级机构，继续循环
					flag = true;
					// 上级节点
					Element parentElement = nodeMap.get(vo.getParentId());
					if (null == parentElement) {
						throw new BusinessException(
								ErrorCode.DIRTY_DATA_FOUND,
								"VirtualOrgan["
										+ vo.getId()
										+ "]'s parent not found or parent is not a Organ !");
					}
					// 隧道、桥梁、收费站设备不加入机构树
					if (parentElement.attributeValue("Type").equals(
							TypeDefinition.ORGAN_BRIDGE + "")
							|| parentElement.attributeValue("Type").equals(
									TypeDefinition.ORGAN_TUNNEL + "")
							|| parentElement.attributeValue("Type").equals(
									TypeDefinition.ORGAN_TOLLGATE + "")) {
						continue;
					}
					int type = vo.getType().intValue();
					// 根据类型构建节点
					// 机构
					if (type <= TypeDefinition.VIRTUAL_ORGAN) {
						// 可见机构
						if (vo.getVisible().intValue() == 1) {
							Element child = DocumentHelper
									.createElement("Node");
							initOrgan(child, vo, organMap);
							nodeMap.put(vo.getId(), child);
							parentElement.element("SubNodes").add(child);
						}
						// 不可见机构，只需增加ID映射为上级机构
						else {
							nodeMap.put(vo.getId(), parentElement);
						}
					}
					// VD
					else if (type >= TypeDefinition.VEHICLE_DETECTOR_MICRO
							&& type < TypeDefinition.WEATHER_DETECTOR) {
						Element vd = DocumentHelper
								.createElement("VehicleDetector");
						initDevice(vd, vo, deviceMap, manufacturerMap);
						Element vehicleDetectorList = parentElement
								.element("VehicleDetectorList");
						if (null == vehicleDetectorList) {
							vehicleDetectorList = DocumentHelper
									.createElement("VehicleDetectorList");
							parentElement.add(vehicleDetectorList);
						}
						vehicleDetectorList.add(vd);
					}
					// CMS
					else if (type >= TypeDefinition.VMS_DOOR
							&& type < TypeDefinition.VEHICLE_DETECTOR_MICRO) {
						Element cms = DocumentHelper.createElement("Cms");
						initDevice(cms, vo, deviceMap, manufacturerMap);
						Element cmsList = parentElement.element("CmsList");
						if (null == cmsList) {
							cmsList = DocumentHelper.createElement("CmsList");
							parentElement.add(cmsList);
						}
						cmsList.add(cms);
					}
					// TSL
					else if (type == TypeDefinition.VMS_TRAFFIC_SIGN) {
						Element tsl = DocumentHelper.createElement("Tsl");
						initDevice(tsl, vo, deviceMap, manufacturerMap);
						Element tslList = parentElement.element("TslList");
						if (null == tslList) {
							tslList = DocumentHelper.createElement("TslList");
							parentElement.add(tslList);
						}
						tslList.add(tsl);
					}
					// LIL
					else if (type == TypeDefinition.VMS_LANE_INDICATOR) {
						Element lil = DocumentHelper.createElement("Lil");
						initDevice(lil, vo, deviceMap, manufacturerMap);
						Element lilList = parentElement.element("LilList");
						if (null == lilList) {
							lilList = DocumentHelper.createElement("LilList");
							parentElement.add(lilList);
						}
						lilList.add(lil);
					}
					// WST
					else if (type == TypeDefinition.WEATHER_DETECTOR) {
						Element wst = DocumentHelper
								.createElement("WeatherStat");
						initDevice(wst, vo, deviceMap, manufacturerMap);
						Element wstList = parentElement
								.element("WeatherStatList");
						if (null == wstList) {
							wstList = DocumentHelper
									.createElement("WeatherStatList");
							parentElement.add(wstList);
						}
						wstList.add(wst);
					}
					// WS
					else if (type == TypeDefinition.WEATHER_DETECTOR_WIND) {
						Element wst = DocumentHelper.createElement("WindSpeed");
						initDevice(wst, vo, deviceMap, manufacturerMap);
						Element wstList = parentElement
								.element("WindSpeedList");
						if (null == wstList) {
							wstList = DocumentHelper
									.createElement("WindSpeedList");
							parentElement.add(wstList);
						}
						wstList.add(wst);
					}
					// RoadDetector
					else if (type == TypeDefinition.WEATHER_DETECTOR_ROAD) {
						Element wst = DocumentHelper
								.createElement("RoadDetector");
						initDevice(wst, vo, deviceMap, manufacturerMap);
						Element wstList = parentElement
								.element("RoadDetectorList");
						if (null == wstList) {
							wstList = DocumentHelper
									.createElement("RoadDetectorList");
							parentElement.add(wstList);
						}
						wstList.add(wst);
					}
					// BridgeDetector
					else if (type == TypeDefinition.WEATHER_DETECTOR_BRIDGE) {
						Element wst = DocumentHelper
								.createElement("BridgeDetector");
						initDevice(wst, vo, deviceMap, manufacturerMap);
						Element wstList = parentElement
								.element("BridgeDetectorList");
						if (null == wstList) {
							wstList = DocumentHelper
									.createElement("BridgeDetectorList");
							parentElement.add(wstList);
						}
						wstList.add(wst);
					}
					// VI
					else if (type == TypeDefinition.WEATHER_DETECTOR_VI) {
						Element wst = DocumentHelper.createElement("Vi");
						initDevice(wst, vo, deviceMap, manufacturerMap);
						Element wstList = parentElement.element("ViList");
						if (null == wstList) {
							wstList = DocumentHelper.createElement("ViList");
							parentElement.add(wstList);
						}
						wstList.add(wst);
					}
					// COVI
					else if (type == TypeDefinition.DETECTOR_COVI) {
						Element covi = DocumentHelper.createElement("Covi");
						initDevice(covi, vo, deviceMap, manufacturerMap);
						Element coviList = parentElement.element("CoviList");
						if (null == coviList) {
							coviList = DocumentHelper.createElement("CoviList");
							parentElement.add(coviList);
						}
						coviList.add(covi);
					}
					// NO
					else if (type == TypeDefinition.DETECTOR_NO) {
						Element covi = DocumentHelper.createElement("No");
						initDevice(covi, vo, deviceMap, manufacturerMap);
						Element coviList = parentElement.element("NoList");
						if (null == coviList) {
							coviList = DocumentHelper.createElement("NoList");
							parentElement.add(coviList);
						}
						coviList.add(covi);
					}
					// LOLI
					else if (type == TypeDefinition.DETECTOR_LOLI) {
						Element covi = DocumentHelper.createElement("LoLi");
						initDevice(covi, vo, deviceMap, manufacturerMap);
						Element coviList = parentElement.element("LoLiList");
						if (null == coviList) {
							coviList = DocumentHelper.createElement("LoLiList");
							parentElement.add(coviList);
						}
						coviList.add(covi);
					}
					// SWITCH
					else if (type >= TypeDefinition.SWITCH_LIGHT
							&& type <= TypeDefinition.SWITCH_WATER_PUMP) {
						Element covi = DocumentHelper.createElement("Switch");
						initDevice(covi, vo, deviceMap, manufacturerMap);
						Element coviList = parentElement.element("SwitchList");
						if (null == coviList) {
							coviList = DocumentHelper
									.createElement("SwitchList");
							parentElement.add(coviList);
						}
						coviList.add(covi);
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
		node.addAttribute("Type", vo.getType().toString());
		// 真实机构
		Organ realOrgan = null;
		if (vo.getType().intValue() < TypeDefinition.VIRTUAL_ORGAN) {
			realOrgan = organMap.get(vo.getDeviceId());
		}
		if (realOrgan != null) {
			node.addAttribute("StandardNumber", MyStringUtil
					.object2StringNotNull(realOrgan.getStandardNumber()));
			node.addAttribute("Longitude",
					MyStringUtil.object2StringNotNull(realOrgan.getLongitude()));
			node.addAttribute("Latitude",
					MyStringUtil.object2StringNotNull(realOrgan.getLatitude()));
			node.addAttribute("StakeNumber", MyStringUtil
					.object2StringNotNull(realOrgan.getBeginStake()));
			node.addAttribute("Length",
					MyStringUtil.object2StringNotNull(realOrgan.getLength()));
			node.addAttribute("LaneNumber", MyStringUtil
					.object2StringNotNull(realOrgan.getLaneNumber()));
			node.addAttribute("EntranceNumber", MyStringUtil
					.object2StringNotNull(realOrgan.getEntranceNumber()));
			node.addAttribute("ExitNumber", MyStringUtil
					.object2StringNotNull(realOrgan.getExitNumber()));

		} else {
			node.addAttribute("StandardNumber", "");
			node.addAttribute("Longitude", "");
			node.addAttribute("Latitude", "");
			node.addAttribute("StakeNumber", "");
			node.addAttribute("Length", "");
			node.addAttribute("LaneNumber", "");
			node.addAttribute("EntranceNumber", "");
			node.addAttribute("ExitNumber", "");
		}
		// 子机构
		Element children = DocumentHelper.createElement("SubNodes");
		node.add(children);
	}

	private void initDevice(Element node, VirtualOrgan vo,
			Map<Long, TmDevice> deviceMap,
			Map<Long, DicManufacture> manufacturerMap) {
		node.addAttribute("Id", vo.getId().toString());
		node.addAttribute("Name",
				MyStringUtil.object2StringNotNull(vo.getName()));
		node.addAttribute("Type", vo.getType().toString());
		TmDevice device = deviceMap.get(vo.getDeviceId());
		if (null == device) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"TmDevice[" + vo.getDeviceId() + "] not found !");
		}
		node.addAttribute("StandardNumber",
				MyStringUtil.object2StringNotNull(device.getStandardNumber()));
		node.addAttribute("Longitude",
				MyStringUtil.object2StringNotNull(device.getLongitude()));
		node.addAttribute("Latitude",
				MyStringUtil.object2StringNotNull(device.getLatitude()));
		node.addAttribute("StakeNumber",
				MyStringUtil.object2StringNotNull(device.getStakeNumber()));

		DicManufacture man = manufacturerMap.get(device.getManufacturerId());
		if (null != man) {
			node.addAttribute("Manufacture",
					MyStringUtil.object2StringNotNull(man.getName()));
		} else {
			node.addAttribute("Manufacture", "");
		}
	}

	@Override
	public Map<Long, TmDevice> mapDeviceByOrgan(List<Organ> organs,
			Integer[] types) {
		return tmDeviceDAO.mapDeviceByOrgan(organs, types);
	}

	@Override
	public TmDevice getTmDevice(Long id) {
		return tmDeviceDAO.get(id);
	}

	@Override
	public void saveTmMarkers(TmDevice tmDevice) {
		tmDeviceDAO.update(tmDevice);
	}

	@Override
	public Element getdeviceList(List<VirtualOrgan> list,
			Map<Long, TmDevice> deviceMap,
			Map<Long, DicManufacture> manufacturerMap, VirtualOrgan node) {
		// 构建机构树
		Element root = DocumentHelper.createElement("Response");
		root.addAttribute("Method", "ListDevice");
		root.addAttribute("Code", "200");
		root.addAttribute("Message", "");
		for (VirtualOrgan vo : list) {
			int type = vo.getType().intValue();
			// VD
			if (type >= TypeDefinition.VEHICLE_DETECTOR_MICRO
					&& type < TypeDefinition.WEATHER_DETECTOR) {
				Element vd = DocumentHelper.createElement("VehicleDetector");
				initDevice(vd, vo, deviceMap, manufacturerMap);
				vd.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element vehicleDetectorList = root
						.element("VehicleDetectorList");
				if (null == vehicleDetectorList) {
					vehicleDetectorList = DocumentHelper
							.createElement("VehicleDetectorList");
					root.add(vehicleDetectorList);
				}
				vehicleDetectorList.add(vd);
			}
			// CMS
			else if (type >= TypeDefinition.VMS_DOOR
					&& type < TypeDefinition.VEHICLE_DETECTOR_MICRO) {
				Element cms = DocumentHelper.createElement("Cms");
				initDevice(cms, vo, deviceMap, manufacturerMap);
				cms.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element cmsList = root.element("CmsList");
				if (null == cmsList) {
					cmsList = DocumentHelper.createElement("CmsList");
					root.add(cmsList);
				}
				cmsList.add(cms);
			}
			// TSL
			else if (type == TypeDefinition.VMS_TRAFFIC_SIGN) {
				Element tsl = DocumentHelper.createElement("Tsl");
				initDevice(tsl, vo, deviceMap, manufacturerMap);
				tsl.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element tslList = root.element("TslList");
				if (null == tslList) {
					tslList = DocumentHelper.createElement("TslList");
					root.add(tslList);
				}
				tslList.add(tsl);
			}
			// LIL
			else if (type == TypeDefinition.VMS_LANE_INDICATOR) {
				Element lil = DocumentHelper.createElement("Lil");
				initDevice(lil, vo, deviceMap, manufacturerMap);
				lil.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element lilList = root.element("LilList");
				if (null == lilList) {
					lilList = DocumentHelper.createElement("LilList");
					root.add(lilList);
				}
				lilList.add(lil);
			}
			// WST
			else if (type == TypeDefinition.WEATHER_DETECTOR) {
				Element wst = DocumentHelper.createElement("WeatherStat");
				initDevice(wst, vo, deviceMap, manufacturerMap);
				wst.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element wstList = root.element("WeatherStatList");
				if (null == wstList) {
					wstList = DocumentHelper.createElement("WeatherStatList");
					root.add(wstList);
				}
				wstList.add(wst);
			}
			// WS
			else if (type == TypeDefinition.WEATHER_DETECTOR_WIND) {
				Element wst = DocumentHelper.createElement("WindSpeed");
				initDevice(wst, vo, deviceMap, manufacturerMap);
				wst.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element wstList = root.element("WindSpeedList");
				if (null == wstList) {
					wstList = DocumentHelper.createElement("WindSpeedList");
					root.add(wstList);
				}
				wstList.add(wst);
			}
			// RoadDetector
			else if (type == TypeDefinition.WEATHER_DETECTOR_ROAD) {
				Element wst = DocumentHelper.createElement("RoadDetector");
				initDevice(wst, vo, deviceMap, manufacturerMap);
				wst.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element wstList = root.element("RoadDetectorList");
				if (null == wstList) {
					wstList = DocumentHelper.createElement("RoadDetectorList");
					root.add(wstList);
				}
				wstList.add(wst);
			}
			// BridgeDetector
			else if (type == TypeDefinition.WEATHER_DETECTOR_BRIDGE) {
				Element wst = DocumentHelper.createElement("BridgeDetector");
				initDevice(wst, vo, deviceMap, manufacturerMap);
				wst.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element wstList = root.element("BridgeDetectorList");
				if (null == wstList) {
					wstList = DocumentHelper
							.createElement("BridgeDetectorList");
					root.add(wstList);
				}
				wstList.add(wst);
			}
			// VI
			else if (type == TypeDefinition.WEATHER_DETECTOR_VI) {
				Element wst = DocumentHelper.createElement("Vi");
				initDevice(wst, vo, deviceMap, manufacturerMap);
				wst.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element wstList = root.element("ViList");
				if (null == wstList) {
					wstList = DocumentHelper.createElement("ViList");
					root.add(wstList);
				}
				wstList.add(wst);
			}
			// COVI
			else if (type == TypeDefinition.DETECTOR_COVI) {
				Element covi = DocumentHelper.createElement("Covi");
				initDevice(covi, vo, deviceMap, manufacturerMap);
				covi.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element coviList = root.element("CoviList");
				if (null == coviList) {
					coviList = DocumentHelper.createElement("CoviList");
					root.add(coviList);
				}
				coviList.add(covi);
			}
			// NO
			else if (type == TypeDefinition.DETECTOR_NO) {
				Element covi = DocumentHelper.createElement("No");
				initDevice(covi, vo, deviceMap, manufacturerMap);
				covi.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element coviList = root.element("NoList");
				if (null == coviList) {
					coviList = DocumentHelper.createElement("NoList");
					root.add(coviList);
				}
				coviList.add(covi);
			}
			// LOLI
			else if (type == TypeDefinition.DETECTOR_LOLI) {
				Element covi = DocumentHelper.createElement("LoLi");
				initDevice(covi, vo, deviceMap, manufacturerMap);
				covi.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element coviList = root.element("LoLiList");
				if (null == coviList) {
					coviList = DocumentHelper.createElement("LoLiList");
					root.add(coviList);
				}
				coviList.add(covi);
			}
			// SWITCH
			else if (type >= TypeDefinition.SWITCH_LIGHT
					&& type <= TypeDefinition.SWITCH_WATER_PUMP) {
				Element covi = DocumentHelper.createElement("Switch");
				initDevice(covi, vo, deviceMap, manufacturerMap);
				covi.addAttribute(
						"Navigation",
						MyStringUtil.object2StringNotNull(deviceMap.get(
								vo.getDeviceId()).getNavigation()));
				Element coviList = root.element("SwitchList");
				if (null == coviList) {
					coviList = DocumentHelper.createElement("SwitchList");
					root.add(coviList);
				}
				coviList.add(covi);
			}
		}
		return root;
	}

	@Override
	public Map<Long, Long> mapDeviceByRoad(String type) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("type", type);
		List<TmDevice> devices = tmDeviceDAO.findByPropertys(params);
		// 所有的真实机构集合
		Map<Long, Organ> organMap = organDAO.map(null);

		Map<Long, Long> map = new HashMap<Long, Long>();
		for (TmDevice device : devices) {
			Organ organ = organMap.get(device.getOrganId());
			if (null == organ) {
				logger.error("OrganId[" + device.getOrganId()
						+ "] for TmDevice[" + device.getId()
						+ "] is dirty data !");
			}

			// 如果是路段/高速公路/普通机构,设备都归属为自身Organ
			if (organ.getType().intValue() == TypeDefinition.ORGAN_ROAD
					|| organ.getType().intValue() == TypeDefinition.ORGAN_MOTORWAY
					|| organ.getType().intValue() == TypeDefinition.ORGAN_GENERAL) {
				map.put(device.getId(), device.getOrganId());
			}
			// 如果不是(桥梁/隧道/收费站),查找上级,设备归属到上级机构
			else {
				Organ parent = organDAO.get(organ.getParentId());
				map.put(device.getId(), parent.getId());
			}

		}
		return map;
	}

	@Override
	@Cache
	public Map<String, TmDevice> mapDeviceBySn() {
		List<TmDevice> list = tmDeviceDAO.list();
		Map<String, TmDevice> map = new HashMap<String, TmDevice>();
		for (TmDevice device : list) {
			map.put(device.getStandardNumber(), device);
		}
		return map;
	}

	@Override
	public List<TmDevice> listDasDevice(Long dasId) {
		LinkedHashMap<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("dasId", dasId);
		return tmDeviceDAO.findByPropertys(param);
	}

	@Override
	public void deleteTmDevice(Long organId) {
		tmDeviceDAO.deleteTmDevice(organId);
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("organId", organId);
		List<TmDevice> list = tmDeviceDAO.findByPropertys(params);
		for (TmDevice entity : list) {
			snDAO.sync(entity.getStandardNumber(), entity.getName(),
					TmDevice.class, entity.getType().toString(), true);
		}
	}

	@Override
	public Long createTmDevice(TmDevice entity) {
		tmDeviceDAO.save(entity);
		snDAO.sync(entity.getStandardNumber(), entity.getName(),
				TmDevice.class, entity.getType() + entity.getSubType(), false);
		return entity.getId();
	}

	@Override
	public void updateTmDevice(TmDevice entity) {
		tmDeviceDAO.update(entity);
		snDAO.sync(entity.getStandardNumber(), entity.getName(),
				TmDevice.class, entity.getType() + entity.getSubType(), false);
	}

	@Override
	public void deleteTmDeviceById(Long id) {
		TmDevice entity = tmDeviceDAO.get(id);
		tmDeviceDAO.delete(entity);
		snDAO.sync(entity.getStandardNumber(), entity.getName(),
				TmDevice.class, entity.getType() + entity.getSubType(), true);
	}

	@Override
	public List<TmDevice> listTmDevice(Long organId, String name,
			String standardNumber, String stakeNumber, String Type, int start,
			int limit) {
		String type = null;
		String subType = null;
		if (StringUtils.isNotBlank(Type)) {
			type = Type.substring(0, 2);
			subType = Type.substring(2);
		}
		List<TmDevice> list = tmDeviceDAO.listTmDevice(organId, name,
				standardNumber, stakeNumber, type, subType, start, limit);
		return list;
	}

	@Override
	public Integer countTmDevice(Long organId, String name,
			String standardNumber, String stakeNumber, String Type) {
		String type = null;
		String subType = null;
		if (StringUtils.isNotBlank(Type)) {
			type = Type.substring(0, 2);
			subType = Type.substring(2);
		}
		Integer count = tmDeviceDAO.countTmDevice(organId, name,
				standardNumber, stakeNumber, type, subType);
		return count;
	}

	@Override
	@Cache
	public Map<String, Long> mapBySn() {
		List<TmDevice> list = tmDeviceDAO.list();
		Map<String, Long> map = new HashMap<String, Long>();
		for (TmDevice device : list) {
			map.put(device.getStandardNumber(), device.getId());
		}
		return map;
	}
}
