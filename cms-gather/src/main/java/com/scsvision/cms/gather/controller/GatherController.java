/**
 * 
 */
package com.scsvision.cms.gather.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.gather.dto.GetGatherEntityDTO;
import com.scsvision.cms.gather.dto.ListDeviceRealDTO;
import com.scsvision.cms.gather.manager.DeviceRealManager;
import com.scsvision.cms.gather.manager.GatherCdManager;
import com.scsvision.cms.gather.manager.GatherCoviManager;
import com.scsvision.cms.gather.manager.GatherLilManager;
import com.scsvision.cms.gather.manager.GatherLoliManager;
import com.scsvision.cms.gather.manager.GatherNodManager;
import com.scsvision.cms.gather.manager.GatherRsdManager;
import com.scsvision.cms.gather.manager.GatherTslManager;
import com.scsvision.cms.gather.manager.GatherVdManager;
import com.scsvision.cms.gather.manager.GatherVmsManager;
import com.scsvision.cms.gather.manager.GatherWsManager;
import com.scsvision.cms.gather.manager.GatherWstManager;
import com.scsvision.cms.gather.service.vo.GatherCdVO;
import com.scsvision.cms.gather.service.vo.GatherCoviVO;
import com.scsvision.cms.gather.service.vo.GatherLilVO;
import com.scsvision.cms.gather.service.vo.GatherLoliVO;
import com.scsvision.cms.gather.service.vo.GatherNodVO;
import com.scsvision.cms.gather.service.vo.GatherRsdVO;
import com.scsvision.cms.gather.service.vo.GatherTslVO;
import com.scsvision.cms.gather.service.vo.GatherWsVO;
import com.scsvision.cms.gather.service.vo.GatherWstVO;
import com.scsvision.cms.gather.service.vo.GetCmsVO;
import com.scsvision.cms.gather.service.vo.GetGatherVdVO;
import com.scsvision.cms.maintain.manager.LogManager;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.interceptor.SessionCheckInterceptor;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.GatherCd;
import com.scsvision.database.entity.GatherCovi;
import com.scsvision.database.entity.GatherLil;
import com.scsvision.database.entity.GatherLoli;
import com.scsvision.database.entity.GatherNod;
import com.scsvision.database.entity.GatherRsd;
import com.scsvision.database.entity.GatherTsl;
import com.scsvision.database.entity.GatherVd;
import com.scsvision.database.entity.GatherVms;
import com.scsvision.database.entity.GatherWs;
import com.scsvision.database.entity.GatherWst;
import com.scsvision.database.entity.TmCmsPublishLog;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.ErrorMsgManager;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.TmDeviceManager;

/**
 * @author wangbinyu
 *
 */
@Stateless
@Interceptors(SessionCheckInterceptor.class)
public class GatherController {
	private final Logger logger = LoggerFactory
			.getLogger(GatherController.class);

	@EJB(beanName = "GatherVdManagerImpl")
	private GatherVdManager gatherVdManager;

	@EJB(beanName = "GatherVmsManagerImpl")
	private GatherVmsManager gatherVmsManager;

	@EJB(beanName = "GatherTslManagerImpl")
	private GatherTslManager gatherTslManager;

	@EJB(beanName = "GatherWstManagerImpl")
	private GatherWstManager gatherWstManager;

	@EJB(beanName = "GatherLilManagerImpl")
	private GatherLilManager gatherLilManager;

	@EJB(beanName = "GatherWsManagerImpl")
	private GatherWsManager gatherWsManager;

	@EJB(beanName = "GatherRsdManagerImpl")
	private GatherRsdManager gatherRsdManager;

	@EJB(beanName = "GatherCoviManagerImpl")
	private GatherCoviManager gatherCoviManager;

	@EJB(beanName = "GatherNodManagerImpl")
	private GatherNodManager gatherNodManager;

	@EJB(beanName = "GatherLoliManagerImpl")
	private GatherLoliManager gatherLoliManager;

	@EJB(beanName = "GatherCdManagerImpl")
	private GatherCdManager gatherCdManager;

	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;

	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;

	@EJB(beanName = "LogManagerImpl")
	private LogManager logManager;

	@EJB(beanName = "ErrorMsgManagerImpl")
	private ErrorMsgManager errorMsgManager;

	@EJB(beanName = "DeviceRealManagerImpl")
	private DeviceRealManager deviceRealManager;

	public String dasReport(String message) {
		RequestReader reader = new RequestReader(message);
		Element root = reader.getRoot();
		List<Element> list = root.elements("Device");
		// 分类处理
		List<Element> vdList = new LinkedList<Element>();
		List<Element> wstList = new LinkedList<Element>();
		List<Element> loliList = new LinkedList<Element>();
		List<Element> coviList = new LinkedList<Element>();
		List<Element> nodList = new LinkedList<Element>();
		List<Element> vmsList = new LinkedList<Element>();
		List<Element> cdList = new LinkedList<Element>();
		List<Element> tslList = new LinkedList<Element>();
		List<Element> lilList = new LinkedList<Element>();
		List<Element> rsdList = new LinkedList<Element>();
		List<Element> wsList = new LinkedList<Element>();
		List<Element> gpsList = new LinkedList<Element>();

		for (Element device : list) {
			String typeString = device.attributeValue("Type");
			int type = Integer.valueOf(typeString);
			// VMS
			if (type == TypeDefinition.VMS_DOOR) {
				vmsList.add(device);
			} else if (type == TypeDefinition.VMS_CANTILEVER) {
				vmsList.add(device);
			} else if (type == TypeDefinition.VMS_COLUMN) {
				vmsList.add(device);
			} else if (type == TypeDefinition.VMS_TUNNEL) {
				vmsList.add(device);
			} else if (type > TypeDefinition.VMS_TRAFFIC_SIGN
					&& type < TypeDefinition.VEHICLE_DETECTOR_MICRO) {
				vmsList.add(device);
			}
			// LIL
			else if (type == TypeDefinition.VMS_LANE_INDICATOR) {
				lilList.add(device);
			}
			// TSL
			else if (type == TypeDefinition.VMS_TRAFFIC_SIGN) {
				tslList.add(device);
			}
			// VD
			else if (type >= TypeDefinition.VEHICLE_DETECTOR_MICRO
					&& type < TypeDefinition.WEATHER_DETECTOR) {
				vdList.add(device);
			}
			// WST,BD,VI
			else if (type == TypeDefinition.WEATHER_DETECTOR
					|| type == TypeDefinition.WEATHER_DETECTOR_BRIDGE
					|| type == TypeDefinition.WEATHER_DETECTOR_VI) {
				wstList.add(device);
			}
			// WS
			else if (type == TypeDefinition.WEATHER_DETECTOR_WIND) {
				wsList.add(device);
			}
			// RSD
			else if (type == TypeDefinition.WEATHER_DETECTOR_ROAD) {
				rsdList.add(device);
			}
			// COVI
			else if (type == TypeDefinition.DETECTOR_COVI) {
				coviList.add(device);
			}
			// NOD
			else if (type == TypeDefinition.DETECTOR_NO) {
				nodList.add(device);
			}
			// LOLI
			else if (type == TypeDefinition.DETECTOR_LOLI) {
				loliList.add(device);
			}
			// CD
			else if (type >= TypeDefinition.SWITCH_LIGHT
					&& type < TypeDefinition.DEVICE_GPS) {
				cdList.add(device);
			}
			// GPS
			else if (type == TypeDefinition.DEVICE_GPS) {
				gpsList.add(device);
			} else {
				// do nothing
			}
		}

		// 获取所有的设备
		Map<String, TmDevice> map = tmDeviceManager.mapDeviceBySn();
		// 保存采集数据
		gatherVmsManager.storeData(vmsList, map);
		gatherLilManager.storeData(lilList, map);
		gatherTslManager.storeData(tslList, map);
		gatherVdManager.storeData(vdList, map);
		gatherWstManager.storeData(wstList, map);
		gatherWsManager.storeData(wsList, map);
		gatherRsdManager.storeData(rsdList, map);
		gatherCoviManager.storeData(coviList, map);
		gatherNodManager.storeData(nodList, map);
		gatherLoliManager.storeData(loliList, map);
		gatherCdManager.storeData(cdList, map);

		Document doc = XmlUtil.generateResponse("DasReport", ErrorCode.SUCCESS,
				"");
		return XmlUtil.xmlToString(doc);
	}

	@Logon
	public Object getGatherEntityJson(HttpServletRequest request)
			throws DocumentException {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("deviceId", false);
		Integer type = reader.getInteger("type", false);
		Long userId = reader.getUserId();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 获取设备真实id
		VirtualOrgan device = organManager.getVOrgan(id);
		Long deviceId = device.getDeviceId();

		GetGatherEntityDTO dto = new GetGatherEntityDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("GetTmDeviceGather");
		// 情报板
		if (type >= TypeDefinition.VMS_DOOR
				&& type <= TypeDefinition.VMS_TUNNEL) {
			// GatherVms vms = gatherVmsManager.getGatherEntity(deviceId,
			// userId,
			// type);
			// if (null != vms) {
			// vms.setDeviceId(id);
			// } else {
			// vms = new GatherVms();
			// }
			// 查询情报发布日志
			List<TmCmsPublishLog> logs = logManager.getCmsLog(deviceId);
			List<GetCmsVO> list = new ArrayList<GetCmsVO>();
			if (logs.size() > 0) {
				String recTime = sdf
						.format(new Date(logs.get(0).getSendTime()));
				for (TmCmsPublishLog log : logs) {
					Short flag = log.getFlag();
					Document document = DocumentHelper.parseText(log
							.getContent());
					Element item = document.getRootElement();
					GetCmsVO vms = new GetCmsVO();
					vms.setColor(item.attributeValue("Color"));
					vms.setDispCont(item.attributeValue("Text"));
					vms.setDispTime(item.attributeValue("Duration"));
					vms.setFlag(flag + "");
					vms.setFont(item.attributeValue("Font"));
					vms.setFontSize(item.attributeValue("Size"));
					vms.setDeviceId(deviceId);
					vms.setPosX(item.attributeValue("PosX"));
					vms.setPosY(item.attributeValue("PosY"));
					list.add(vms);
				}
				dto.setRecTime(recTime);
			}
			dto.setGather(list);
		}
		// 交通信号灯
		else if (type == TypeDefinition.VMS_TRAFFIC_SIGN) {
			GatherTsl tsl = gatherTslManager.getGatherEntity(deviceId, userId,
					type);
			GatherTslVO vo = new GatherTslVO();
			if (null != tsl) {
				vo.setCommStatus(MyStringUtil.object2StringNotNull(tsl
						.getCommStatus()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(tsl
						.getDeviceId()));
				vo.setGreenStatus(MyStringUtil.object2StringNotNull(tsl
						.getGreenStatus()));
				vo.setId(MyStringUtil.object2StringNotNull(tsl.getId()));
				vo.setRecTime(sdf.format(tsl.getRecTime()));
				vo.setRedStatus(MyStringUtil.object2StringNotNull(tsl
						.getRedStatus()));
				vo.setStatus(MyStringUtil.object2StringNotNull(tsl.getStatus()));
				vo.setTurnStatus(MyStringUtil.object2StringNotNull(tsl
						.getTurnStatus()));
				vo.setYellowStatus(MyStringUtil.object2StringNotNull(tsl
						.getYellowStatus()));
			}
			dto.setGather(vo);
		}
		// 车道指示器
		else if (type == TypeDefinition.VMS_LANE_INDICATOR) {
			GatherLil lil = gatherLilManager.getGatherEntity(deviceId, userId,
					type);
			GatherLilVO vo = new GatherLilVO();
			if (null != lil) {
				vo.setBackArrowStatus(MyStringUtil.object2StringNotNull(lil
						.getBackArrowStatus()));
				vo.setBackXStatus(MyStringUtil.object2StringNotNull(lil
						.getBackXStatus()));
				vo.setCommStatus(MyStringUtil.object2StringNotNull(lil
						.getCommStatus()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(lil
						.getDeviceId()));
				vo.setFrontArrowStatus(MyStringUtil.object2StringNotNull(lil
						.getFrontArrowStatus()));
				vo.setFrontXStatus(MyStringUtil.object2StringNotNull(lil
						.getFrontXStatus()));
				vo.setId(MyStringUtil.object2StringNotNull(lil.getId()));
				vo.setRecTime(sdf.format(lil.getRecTime()));
				vo.setStatus(MyStringUtil.object2StringNotNull(lil.getStatus()));
			}
			dto.setGather(vo);
		}
		// 车检器
		else if (type >= TypeDefinition.VEHICLE_DETECTOR_MICRO
				&& type <= TypeDefinition.VEHICLE_DETECTOR_VIDEO) {
			GatherVd vd = gatherVdManager.getGatherEntity(deviceId, userId,
					type);
			GetGatherVdVO vo = new GetGatherVdVO();
			if (null != vd) {
				vo.setGatherInterval(tmDeviceManager.getTmDevice(
						vd.getDeviceId()).getGatherInterval());
				vo.setDeviceId(id);
				vo.setRecTime(sdf.format(vd.getRecTime()));
				// 下行总车流量：合并车道4、5、6总车流量
				Double dwFlux = (vd.getLane4Flux() != null ? vd.getLane4Flux()
						: 0.0)
						+ (vd.getLane5Flux() != null ? vd.getLane5Flux() : 0.0)
						+ (vd.getLane6Flux() != null ? vd.getLane6Flux() : 0.0);
				vo.setDwFlux(dwFlux);
				// 下行大车流量：合并车道4、5、6大车、中车流量
				Double dwFluxb = (vd.getLane4Fluxb() != null ? vd
						.getLane4Fluxb() : 0.0)
						+ (vd.getLane5Fluxb() != null ? vd.getLane5Fluxb()
								: 0.0)
						+ (vd.getLane6Fluxb() != null ? vd.getLane6Fluxb()
								: 0.0)
						+ (vd.getLane4Fluxm() != null ? vd.getLane4Fluxm()
								: 0.0)
						+ (vd.getLane5Fluxm() != null ? vd.getLane5Fluxm()
								: 0.0)
						+ (vd.getLane6Fluxm() != null ? vd.getLane6Fluxm()
								: 0.0);
				vo.setDwFluxb(dwFluxb);
				// 下行小车流量：合并车道4、5、6中小车、小车流量
				// Double dwFluxs = (vd.getLane4Fluxms() != null ? vd
				// .getLane4Fluxms() : 0.0)
				// + (vd.getLane5Fluxms() != null ? vd.getLane5Fluxms()
				// : 0.0)
				// + (vd.getLane6Fluxms() != null ? vd.getLane6Fluxms()
				// : 0.0)
				// + (vd.getLane4Fluxs() != null ? vd.getLane4Fluxs()
				// : 0.0)
				// + (vd.getLane5Fluxs() != null ? vd.getLane5Fluxs()
				// : 0.0)
				// + (vd.getLane6Fluxs() != null ? vd.getLane6Fluxs()
				// : 0.0);
				vo.setDwFluxs(dwFlux - dwFluxb);
				// 下行平均速度
				int i = 0;
				Double dwSpeed = 0.0;
				if (vd.getLane4Speed() != null) {
					dwSpeed += vd.getLane4Speed();
					i++;
				}
				if (vd.getLane5Speed() != null) {
					dwSpeed += vd.getLane5Speed();
					i++;
				}
				if (vd.getLane6Speed() != null) {
					dwSpeed += vd.getLane6Speed();
					i++;
				}
				vo.setDwSpeed(i != 0 ? dwSpeed / i : 0.0);
				// 下行占有率
				Double dwOcc = 0.0;
				i = 0;
				if (vd.getLane4Occ() != null) {
					dwOcc += vd.getLane4Occ();
					i++;
				}
				if (vd.getLane5Occ() != null) {
					dwOcc += vd.getLane5Occ();
					i++;
				}
				if (vd.getLane6Occ() != null) {
					dwOcc += vd.getLane6Occ();
					i++;
				}
				vo.setDwOcc(i != 0 ? dwOcc / i : 0.0);
				// 上行总车流量：合并1、2、3车道总车流量
				Double upFlux = (vd.getLane1Flux() != null ? vd.getLane1Flux()
						: 0.0)
						+ (vd.getLane2Flux() != null ? vd.getLane2Flux() : 0.0)
						+ (vd.getLane3Flux() != null ? vd.getLane3Flux() : 0.0);
				vo.setUpFlux(upFlux);
				// 上行大车流量：合并1、2、3车道大车、中车流量
				Double upFluxb = (vd.getLane1Fluxb() != null ? vd
						.getLane1Fluxb() : 0.0)
						+ (vd.getLane2Fluxb() != null ? vd.getLane2Fluxb()
								: 0.0)
						+ (vd.getLane3Fluxb() != null ? vd.getLane3Fluxb()
								: 0.0)
						+ (vd.getLane1Fluxm() != null ? vd.getLane1Fluxm()
								: 0.0)
						+ (vd.getLane2Fluxm() != null ? vd.getLane2Fluxm()
								: 0.0)
						+ (vd.getLane3Fluxm() != null ? vd.getLane3Fluxm()
								: 0.0);
				vo.setUpFluxb(upFluxb);
				// 上行小车流量：总流量-大车流量
				vo.setUpFluxs(upFlux - upFluxb);
				// 上行平均速度
				i = 0;
				Double upSpeed = 0.0;
				if (vd.getLane1Speed() != null) {
					upSpeed += vd.getLane1Speed();
					i++;
				}
				if (vd.getLane2Speed() != null) {
					upSpeed += vd.getLane2Speed();
					i++;
				}
				if (vd.getLane3Speed() != null) {
					upSpeed += vd.getLane3Speed();
					i++;
				}
				vo.setUpSpeed(i != 0 ? upSpeed / i : 0.0);
				// 上行占有率
				Double upOcc = 0.0;
				i = 0;
				if (vd.getLane1Occ() != null) {
					upOcc += vd.getLane1Occ();
					i++;
				}
				if (vd.getLane2Occ() != null) {
					upOcc += vd.getLane2Occ();
					i++;
				}
				if (vd.getLane3Occ() != null) {
					upOcc += vd.getLane3Occ();
					i++;
				}
				vo.setUpOcc(i != 0 ? upOcc / i : 0.0);
			}
			dto.setGather(vo);
		}
		// 气象检测器
		else if (type == TypeDefinition.WEATHER_DETECTOR) {
			GatherWst wst = gatherWstManager.getGatherEntity(deviceId, userId,
					type);
			GatherWstVO vo = new GatherWstVO();
			if (null != wst) {
				vo.setAirTemp(MyStringUtil.object2StringNotNull(wst
						.getAirTemp()));
				vo.setCommStatus(MyStringUtil.object2StringNotNull(wst
						.getCommStatus()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(wst
						.getDeviceId()));
				vo.setDirection(MyStringUtil.object2StringNotNull(wst
						.getDirection()));
				vo.setHumi(MyStringUtil.object2StringNotNull(wst.getHumi()));
				vo.setId(MyStringUtil.object2StringNotNull(wst.getId()));
				vo.setRainVol(MyStringUtil.object2StringNotNull(wst
						.getRainVol()));
				vo.setRecTime(sdf.format(wst.getRecTime()));
				vo.setRoadSurface(MyStringUtil.object2StringNotNull(wst
						.getRoadSurface()));
				vo.setRoadTemp(MyStringUtil.object2StringNotNull(wst
						.getRoadTemp()));
				vo.setSnowVol(MyStringUtil.object2StringNotNull(wst
						.getSnowVol()));
				vo.setStatus(MyStringUtil.object2StringNotNull(wst.getStatus()));
				vo.setVi(MyStringUtil.object2StringNotNull(wst.getVi()));
				vo.setWs(MyStringUtil.object2StringNotNull(wst.getWs()));
			}
			dto.setGather(vo);
		}
		// 风速风向检测器
		else if (type == TypeDefinition.WEATHER_DETECTOR_WIND) {
			GatherWs ws = gatherWsManager.getGatherEntity(deviceId, userId,
					type);
			GatherWsVO vo = new GatherWsVO();
			if (null != ws) {
				vo.setCommStatus(MyStringUtil.object2StringNotNull(ws
						.getCommStatus()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(ws
						.getDeviceId()));
				vo.setDirection(MyStringUtil.object2StringNotNull(ws
						.getDirection()));
				vo.setId(MyStringUtil.object2StringNotNull(ws.getId()));
				vo.setRecTime(sdf.format(ws.getRecTime()));
				vo.setSpeed(MyStringUtil.object2StringNotNull(ws.getSpeed()));
				vo.setStatus(MyStringUtil.object2StringNotNull(ws.getStatus()));
			}
			dto.setGather(vo);
		}
		// 路面检测器或者桥面检测器
		else if (type >= TypeDefinition.WEATHER_DETECTOR_ROAD
				&& type <= TypeDefinition.WEATHER_DETECTOR_BRIDGE) {
			GatherRsd rsd = gatherRsdManager.getGatherEntity(deviceId, userId,
					type);
			GatherRsdVO vo = new GatherRsdVO();
			if (null != rsd) {
				vo.setCommStatus(MyStringUtil.object2StringNotNull(rsd
						.getCommStatus()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(rsd
						.getDeviceId()));
				vo.setId(MyStringUtil.object2StringNotNull(rsd.getId()));
				vo.setRainVol(MyStringUtil.object2StringNotNull(rsd
						.getRainVol()));
				vo.setRecTime(sdf.format(rsd.getRecTime()));
				vo.setRoadSurface(MyStringUtil.object2StringNotNull(rsd
						.getRoadSurface()));
				vo.setRoadTemp(MyStringUtil.object2StringNotNull(rsd
						.getRoadTemp()));
				vo.setSnowVol(MyStringUtil.object2StringNotNull(rsd
						.getSnowVol()));
				vo.setStatus(MyStringUtil.object2StringNotNull(rsd.getStatus()));
			}
			dto.setGather(vo);
		}
		// 能见度仪 暂时归为气象检测器
		else if (type == TypeDefinition.WEATHER_DETECTOR_VI) {
			GatherWst wst = gatherWstManager.getGatherEntity(deviceId, userId,
					type);
			GatherWstVO vo = new GatherWstVO();
			if (null != wst) {
				vo.setCommStatus(MyStringUtil.object2StringNotNull(wst
						.getStatus()));
				vo.setVi(MyStringUtil.object2StringNotNull(wst.getVi()));
				vo.setStatus(MyStringUtil.object2StringNotNull(wst.getStatus()));
				vo.setRecTime(sdf.format(wst.getRecTime()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(wst
						.getDeviceId()));
			}
			dto.setGather(vo);
		}
		// COVI检测器
		else if (type == TypeDefinition.DETECTOR_COVI) {
			GatherCovi covi = gatherCoviManager.getGatherEntity(deviceId,
					userId, type);
			GatherCoviVO vo = new GatherCoviVO();
			if (null != covi) {
				vo.setCo(MyStringUtil.object2StringNotNull(covi.getCo()));
				vo.setCommStatus(MyStringUtil.object2StringNotNull(covi
						.getCommStatus()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(covi
						.getDeviceId()));
				vo.setId(MyStringUtil.object2StringNotNull(covi.getId()));
				vo.setRecTime(sdf.format(covi.getRecTime()));
				vo.setStatus(MyStringUtil.object2StringNotNull(covi.getStatus()));
				vo.setVi(MyStringUtil.object2StringNotNull(covi.getVi()));
			}
			dto.setGather(vo);
		}
		// 氮氧化物
		else if (type == TypeDefinition.DETECTOR_NO) {
			GatherNod nod = gatherNodManager.getGatherEntity(deviceId, userId,
					type);
			GatherNodVO vo = new GatherNodVO();
			if (null != nod) {
				vo.setCommStatus(MyStringUtil.object2StringNotNull(nod
						.getCommStatus()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(nod
						.getDeviceId()));
				vo.setId(MyStringUtil.object2StringNotNull(nod.getId()));
				vo.setNo(MyStringUtil.object2StringNotNull(nod.getNo()));
				vo.setNo2(MyStringUtil.object2StringNotNull(nod.getNo2()));
				vo.setRecTime(sdf.format(nod.getRecTime()));
				vo.setStatus(MyStringUtil.object2StringNotNull(nod.getStatus()));
			}
			dto.setGather(vo);
		}
		// 光强检测器
		else if (type == TypeDefinition.DETECTOR_LOLI) {
			GatherLoli loli = gatherLoliManager.getGatherEntity(deviceId,
					userId, type);
			GatherLoliVO vo = new GatherLoliVO();
			if (null != loli) {
				vo.setCommStatus(MyStringUtil.object2StringNotNull(loli
						.getCommStatus()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(loli
						.getDeviceId()));
				vo.setId(MyStringUtil.object2StringNotNull(loli.getId()));
				vo.setLo(MyStringUtil.object2StringNotNull(loli.getLo()));
				vo.setLi(MyStringUtil.object2StringNotNull(loli.getLi()));
				vo.setRecTime(sdf.format(loli.getRecTime()));
				vo.setStatus(MyStringUtil.object2StringNotNull(loli.getStatus()));
			}
			dto.setGather(vo);
		}
		// 开关量控制设备
		else if (type >= TypeDefinition.SWITCH_LIGHT
				&& type <= TypeDefinition.SWITCH_WATER_PUMP) {
			GatherCd cd = gatherCdManager.getGatherEntity(deviceId, userId,
					type);
			GatherCdVO vo = new GatherCdVO();
			if (null != cd) {
				vo.setCommStatus(MyStringUtil.object2StringNotNull(cd
						.getCommStatus()));
				vo.setDeviceId(MyStringUtil.object2StringNotNull(cd
						.getDeviceId()));
				vo.setId(MyStringUtil.object2StringNotNull(cd.getId()));
				vo.setRecTime(sdf.format(cd.getRecTime()));
				vo.setStatus(MyStringUtil.object2StringNotNull(cd.getStatus()));
				vo.setType(MyStringUtil.object2StringNotNull(cd.getType()));
				vo.setWorkState(MyStringUtil.object2StringNotNull(cd
						.getWorkState()));
			}
			dto.setGather(vo);
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"param type[" + type + "] invalid");
		}

		return dto;
	}

	public Object listDeviceRealJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String ids = reader.getString("ids", false);
		// 返回结果
		ListDeviceRealDTO dto = new ListDeviceRealDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		String[] array = ids.split(",");
		List<Long> longIds = new LinkedList<Long>();
		try {
			for (String id : array) {
				longIds.add(Long.valueOf(id));
			}
		} catch (NumberFormatException e) {
			dto.setCode(ErrorCode.PARAMETER_INVALID);
			dto.setMessage(errorMsgManager.getErrorMsgMap().get(
					ErrorCode.PARAMETER_INVALID));
			return dto;
		}

		// 虚拟ID转换为真实ID
		Map<Long, VirtualOrgan> map = organManager.mapVOrgan(longIds);
		// 真实ID与虚拟ID映射表
		Map<Long, Long> dvMap = new HashMap<Long, Long>();
		List<Long> deviceIds = new LinkedList<Long>();
		for (Long id : longIds) {
			VirtualOrgan vo = map.get(id);
			if (null != vo) {
				deviceIds.add(vo.getDeviceId());
				dvMap.put(vo.getDeviceId(), id);
			}
		}
		// 设备ID与SN的映射表
		Map<Long, TmDevice> tmDeviceMap = tmDeviceManager
				.mapTmDevice(deviceIds);

		List<org.bson.Document> datas = deviceRealManager
				.listDeviceReal(deviceIds);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		List<ListDeviceRealDTO.Device> devices = dto.getDevices();
		for (org.bson.Document data : datas) {
			ListDeviceRealDTO.Device device = dto.new Device();
			Long deviceId = data.getLong("deviceId");
			TmDevice tmDevice = tmDeviceMap.get(deviceId);
			if (null == tmDevice) {
				logger.error("Dirty data found, DeviceId["
						+ deviceId
						+ "] is not found in TmDevice, please check the VirtualOrgan !");
				continue;
			}

			device.setAirTemp(MyStringUtil.object2String(data
					.getDouble("airTemp")));
			device.setAltitude(data.getString("altitude"));
			device.setBackArrowStatus(MyStringUtil.object2String(data
					.getInteger("backArrowStatus")));
			device.setBackXStatus(MyStringUtil.object2String(data
					.getInteger("backXStatus")));
			device.setBearing(data.getString("bearing"));
			device.setCo(MyStringUtil.object2String(data.getDouble("co")));
			device.setCommStatus(MyStringUtil.object2String(data
					.getInteger("commStatus")));
			device.setFrontArrowStatus(MyStringUtil.object2String(data
					.getInteger("frontArrowStatus")));
			device.setFrontXStatus(MyStringUtil.object2String(data
					.getInteger("frontXStatus")));
			device.setGreenStatus(MyStringUtil.object2String(data
					.getInteger("greenStatus")));
			device.setHumi(MyStringUtil.object2String(data.getDouble("humi")));
			device.setId(dvMap.get(deviceId));
			device.setStandardNumber(tmDevice.getStandardNumber());
			device.setLane1Flux(MyStringUtil.object2String(data
					.getDouble("lane1Flux")));
			device.setLane1Headway(MyStringUtil.object2String(data
					.getDouble("lane1Headway")));
			device.setLane1Occ(MyStringUtil.object2String(data
					.getDouble("lane1Occ")));
			device.setLane1Speed(MyStringUtil.object2String(data
					.getDouble("lane1Speed")));
			device.setLane2Flux(MyStringUtil.object2String(data
					.getDouble("lane2Flux")));
			device.setLane2Headway(MyStringUtil.object2String(data
					.getDouble("lane2Headway")));
			device.setLane2Occ(MyStringUtil.object2String(data
					.getDouble("lane2Occ")));
			device.setLane2Speed(MyStringUtil.object2String(data
					.getDouble("lane2Speed")));
			device.setLane3Flux(MyStringUtil.object2String(data
					.getDouble("lane3Flux")));
			device.setLane3Headway(MyStringUtil.object2String(data
					.getDouble("lane3Headway")));
			device.setLane3Occ(MyStringUtil.object2String(data
					.getDouble("lane3Occ")));
			device.setLane3Speed(MyStringUtil.object2String(data
					.getDouble("lane3Speed")));
			device.setLane4Flux(MyStringUtil.object2String(data
					.getDouble("lane4Flux")));
			device.setLane4Headway(MyStringUtil.object2String(data
					.getDouble("lane4Headway")));
			device.setLane4Occ(MyStringUtil.object2String(data
					.getDouble("lane4Occ")));
			device.setLane4Speed(MyStringUtil.object2String(data
					.getDouble("lane4Speed")));
			device.setLane5Flux(MyStringUtil.object2String(data
					.getDouble("lane5Flux")));
			device.setLane5Headway(MyStringUtil.object2String(data
					.getDouble("lane5Headway")));
			device.setLane5Occ(MyStringUtil.object2String(data
					.getDouble("lane5Occ")));
			device.setLane5Speed(MyStringUtil.object2String(data
					.getDouble("lane5Speed")));
			device.setLane6Flux(MyStringUtil.object2String(data
					.getDouble("lane6Flux")));
			device.setLane6Headway(MyStringUtil.object2String(data
					.getDouble("lane6Headway")));
			device.setLane6Occ(MyStringUtil.object2String(data
					.getDouble("lane6Occ")));
			device.setLane6Speed(MyStringUtil.object2String(data
					.getDouble("lane6Speed")));
			device.setLatitude(MyStringUtil.object2String(data
					.getDouble("latitude")));
			device.setLi(MyStringUtil.object2String(data.getDouble("li")));
			device.setLo(MyStringUtil.object2String(data.getDouble("lo")));
			device.setLongitude(data.getString("longitude"));
			device.setNo(MyStringUtil.object2String(data.getDouble("no")));
			device.setNo2(MyStringUtil.object2String(data.getDouble("no2")));
			device.setRainVol(MyStringUtil.object2String(data
					.getDouble("rainVol")));
			device.setRecTime(sdf.format(data.getDate(("recTime"))));
			device.setRedStatus(MyStringUtil.object2String(data
					.getInteger("redStatus")));
			device.setRoadSurface(MyStringUtil.object2String(data
					.getInteger("roadSurface")));
			device.setRoadTemp(MyStringUtil.object2String(data
					.getDouble("roadTemp")));
			device.setSnowVol(MyStringUtil.object2String(data
					.getDouble("snowVol")));
			device.setSpeed(MyStringUtil.object2String(data.getDouble("speed")));
			device.setStatus(MyStringUtil.object2String(data
					.getInteger("status")));
			device.setTurnStatus(MyStringUtil.object2String(data
					.getInteger("turnStatus")));
			device.setVi(MyStringUtil.object2String(data.getDouble("vi")));
			device.setWorkState(data.getString("workState"));
			device.setWs(MyStringUtil.object2String(data.getDouble("ws")));
			device.setYellowStatus(MyStringUtil.object2String(data
					.getInteger("yellowStatus")));
			devices.add(device);
		}

		return dto;
	}
}
