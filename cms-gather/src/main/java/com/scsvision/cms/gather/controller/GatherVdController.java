/**
 * 
 */
package com.scsvision.cms.gather.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.gather.dto.VdDTO;
import com.scsvision.cms.gather.manager.GatherVdManager;
import com.scsvision.cms.gather.service.vo.GatherVdOrganVO;
import com.scsvision.cms.gather.service.vo.ListByOrganRoadDTO;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.interceptor.SessionCheckInterceptor;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.GatherVd;
import com.scsvision.database.entity.User;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.TmDeviceManager;
import com.scsvision.database.manager.UserManager;

/**
 * @author wangbinyu
 *
 */
@Stateless
@Interceptors(SessionCheckInterceptor.class)
public class GatherVdController {
	private final Logger logger = LoggerFactory
			.getLogger(GatherVdController.class);

	@EJB(beanName = "GatherVdManagerImpl")
	private GatherVdManager gatherVdManager;

	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;

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

	private int switchHour(Date recTime) {
		int n = 0;
		try {
			String hour = recTime.toString().substring(
					recTime.toString().indexOf(":") - 2,
					recTime.toString().indexOf(":"));
			n = Integer.parseInt(hour);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Gather Vd RecTime[" + recTime.toString() + "] Error! ");

		}
		return n;
	}

	@Logon
	public Object listByOrganRoadJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();
		Calendar cal = Calendar.getInstance();
		Date beginTime = new Date(System.currentTimeMillis() - 24 * 60 * 60
				* 1000);
		Date endTime = new Date();

		User user = userManager.getUser(userId);

		// 查询用户组所有机构
		List<VirtualOrgan> organs = organManager.listUgAllOrgan(user
				.getUserGroupId());

		// 机构map
		Map<Long, VirtualOrgan> vorganMap = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan vorgan : organs) {
			vorganMap.put(vorgan.getId(), vorgan);
		}

		// 查询用户组车检器
		List<VirtualOrgan> vds = organManager
				.listUgAllVd(user.getUserGroupId());

		// 车检器deviceId
		List<Long> vdIds = new ArrayList<Long>();

		// 以及组成设备map key:设备id value机构id，如上级机构被屏蔽找再上一级机构
		Map<Long, Long> vdMap = new HashMap<Long, Long>();

		// 车检器path集合
		List<String> paths = new ArrayList<String>();

		// 构建设备map key:设备id value:机构id，如上级机构被屏蔽找再上一级机构
		for (VirtualOrgan vd : vds) {
			// 这个车检器id集合用于查询gather数据
			vdIds.add(vd.getDeviceId());

			// 构建设备map
			if (vorganMap.get(vd.getParentId()) == null) {
				paths.clear();
				String vdPath[] = vd.getPath().split("/");
				for (int i = 0; i < vdPath.length; i++) {
					paths.add(vdPath[i]);
				}
				vdMap.put(
						vd.getDeviceId(),
						queryParentOrgan(vd, vorganMap, paths, paths.size() - 1));
			} else {
				vdMap.put(vd.getDeviceId(), vd.getParentId());
			}

		}

		// 采集数据
		List<GatherVd> gatherVds = gatherVdManager.list(vdIds, beginTime,
				endTime);

		// 机构分组数据 y:机构路段分组 x:24小时值(开始时间和结束时间是一天24小时暂时写死,如要灵活必须按照采集时间分组)
		Long data[][] = new Long[organs.size()][24];
		for (int i = 0; i < organs.size(); i++) {
			for (int n = 0; n < 24; n++) {
				data[i][n] = 0l;
			}
		}

		// 机构设备MAP key:organId value:机构组
		Map<Long, Integer> map = new HashMap<Long, Integer>();
		for (int i = 0; i < organs.size(); i++) {
			map.put(organs.get(i).getId(), i);
		}

		// 数据统计累加
		for (GatherVd vd : gatherVds) {
			data[map.get(vdMap.get(vd.getDeviceId()))][switchHour(vd
					.getRecTime())] += (long) (vd.getLane1Flux()
					+ vd.getLane2Flux() + vd.getLane3Flux() + vd.getLane4Flux()
					+ vd.getLane5Flux() + vd.getLane6Flux());
		}

		List<GatherVdOrganVO> list = new ArrayList<GatherVdOrganVO>();

		// 返回数据24小时，需要根据当前小时为末尾
		int hour = new Date().getHours();
		String[] date = new String[24];
		// 返回数据
		for (int i = 0; i < organs.size(); i++) {
			GatherVdOrganVO vo = new GatherVdOrganVO();
			vo.setName(organs.get(i).getName());
			long[] result = new long[24];
			for (int n = hour; n < 24; n++) {
				result[n] = data[i][n];
			}
			for (int n = 0; n < hour; n++) {
				result[n] = data[i][n];
			}
			vo.setData(result);
			list.add(vo);
		}

		int m = 0;
		for (int i = 1; i <= 24; i++) {
			if (hour + i >= 24) {
				date[i - 1] = m + "";
				m++;
			} else {
				date[i - 1] = hour + i + "";
			}
		}

		ListByOrganRoadDTO dto = new ListByOrganRoadDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("ListByOrganRoadJson");
		dto.setList(list);
		dto.setDate(date);

		return dto;
	}

	public Object listByDateJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long virtualId = reader.getLong("id", false);
		Date beginTime = new Date(getStartTime() - 6 * 24 * 60 * 60 * 1000);
		Date endTime = new Date(System.currentTimeMillis());
		// 查询虚拟机构id
		VirtualOrgan vOrgan = organManager.getVOrgan(virtualId);
		// 获取设备id
		Long deviceId = vOrgan.getDeviceId();

		// 采集数据
		List<GatherVd> gatherVds = gatherVdManager.listById(deviceId,
				beginTime, endTime);

		// 5*7矩阵，行分别代表上行、下行、大车、中车、小车，列代表时间
		Long data[][] = new Long[7][5];

		// 初始化矩阵
		for (int i = 0; i < data.length; i++) {
			for (int t = 0; t < data[i].length; t++) {
				data[i][t] = 0l;
			}
		}

		for (GatherVd gatherVd : gatherVds) {
			Long day = null;
			Double lane1Flux = gatherVd.getLane1Flux() == null ? 0.0 : gatherVd
					.getLane1Flux();
			Double lane2Flux = gatherVd.getLane2Flux() == null ? 0.0 : gatherVd
					.getLane2Flux();
			Double lane3Flux = gatherVd.getLane3Flux() == null ? 0.0 : gatherVd
					.getLane3Flux();
			Double lane4Flux = gatherVd.getLane4Flux() == null ? 0.0 : gatherVd
					.getLane4Flux();
			Double lane5Flux = gatherVd.getLane5Flux() == null ? 0.0 : gatherVd
					.getLane5Flux();
			Double lane6Flux = gatherVd.getLane6Flux() == null ? 0.0 : gatherVd
					.getLane6Flux();

			Double lane1Fluxb = gatherVd.getLane1Fluxb() == null ? 0.0
					: gatherVd.getLane1Fluxb();
			Double lane2Fluxb = gatherVd.getLane2Fluxb() == null ? 0.0
					: gatherVd.getLane2Fluxb();
			Double lane3Fluxb = gatherVd.getLane3Fluxb() == null ? 0.0
					: gatherVd.getLane3Fluxb();
			Double lane4Fluxb = gatherVd.getLane4Fluxb() == null ? 0.0
					: gatherVd.getLane4Fluxb();
			Double lane5Fluxb = gatherVd.getLane5Fluxb() == null ? 0.0
					: gatherVd.getLane5Fluxb();
			Double lane6Fluxb = gatherVd.getLane6Fluxb() == null ? 0.0
					: gatherVd.getLane6Fluxb();

			Double lane1Fluxm = gatherVd.getLane1Fluxm() == null ? 0.0
					: gatherVd.getLane1Fluxm();
			Double lane2Fluxm = gatherVd.getLane2Fluxm() == null ? 0.0
					: gatherVd.getLane2Fluxm();
			Double lane3Fluxm = gatherVd.getLane3Fluxm() == null ? 0.0
					: gatherVd.getLane3Fluxm();
			Double lane4Fluxm = gatherVd.getLane4Fluxm() == null ? 0.0
					: gatherVd.getLane4Fluxm();
			Double lane5Fluxm = gatherVd.getLane5Fluxm() == null ? 0.0
					: gatherVd.getLane5Fluxm();
			Double lane6Fluxm = gatherVd.getLane6Fluxm() == null ? 0.0
					: gatherVd.getLane6Fluxm();

			Double lane1Fluxms = gatherVd.getLane1Fluxms() == null ? 0.0
					: gatherVd.getLane1Fluxms();
			Double lane2Fluxms = gatherVd.getLane2Fluxms() == null ? 0.0
					: gatherVd.getLane2Fluxms();
			Double lane3Fluxms = gatherVd.getLane3Fluxms() == null ? 0.0
					: gatherVd.getLane3Fluxms();
			Double lane4Fluxms = gatherVd.getLane4Fluxms() == null ? 0.0
					: gatherVd.getLane4Fluxms();
			Double lane5Fluxms = gatherVd.getLane5Fluxms() == null ? 0.0
					: gatherVd.getLane5Fluxms();
			Double lane6Fluxms = gatherVd.getLane6Fluxms() == null ? 0.0
					: gatherVd.getLane6Fluxms();

			Double lane1Fluxs = gatherVd.getLane1Fluxs() == null ? 0.0
					: gatherVd.getLane1Fluxs();
			Double lane2Fluxs = gatherVd.getLane2Fluxs() == null ? 0.0
					: gatherVd.getLane2Fluxs();
			Double lane3Fluxs = gatherVd.getLane3Fluxs() == null ? 0.0
					: gatherVd.getLane3Fluxs();
			Double lane4Fluxs = gatherVd.getLane4Fluxs() == null ? 0.0
					: gatherVd.getLane4Fluxs();
			Double lane5Fluxs = gatherVd.getLane5Fluxs() == null ? 0.0
					: gatherVd.getLane5Fluxs();
			Double lane6Fluxs = gatherVd.getLane6Fluxs() == null ? 0.0
					: gatherVd.getLane6Fluxs();

			day = (gatherVd.getRecTime().getTime() - beginTime.getTime()) / 86400000;
			switch (day + "") {
			case "0":
				data[0][0] += (long) (lane1Flux + lane2Flux + lane3Flux);
				data[0][1] += (long) (lane4Flux + lane5Flux + lane6Flux);
				data[0][2] += (long) (lane1Fluxb + lane2Fluxb + lane3Fluxb
						+ lane4Fluxb + lane5Fluxb + lane6Fluxb);
				data[0][3] += (long) (lane1Fluxm + lane2Fluxm + lane3Fluxm
						+ lane4Fluxm + lane5Fluxm + lane6Fluxm);
				data[0][4] += (long) (lane1Fluxms + lane2Fluxms + lane3Fluxms
						+ lane4Fluxms + lane5Fluxms + lane6Fluxms + lane1Fluxs
						+ lane2Fluxs + lane3Fluxs + lane4Fluxs + lane5Fluxs + lane6Fluxs);
				break;
			case "1":
				data[1][0] += (long) (lane1Flux + lane2Flux + lane3Flux);
				data[1][1] += (long) (lane4Flux + lane5Flux + lane6Flux);
				data[1][2] += (long) (lane1Fluxb + lane2Fluxb + lane3Fluxb
						+ lane4Fluxb + lane5Fluxb + lane6Fluxb);
				data[1][3] += (long) (lane1Fluxm + lane2Fluxm + lane3Fluxm
						+ lane4Fluxm + lane5Fluxm + lane6Fluxm);
				data[1][4] += (long) (lane1Fluxms + lane2Fluxms + lane3Fluxms
						+ lane4Fluxms + lane5Fluxms + lane6Fluxms + lane1Fluxs
						+ lane2Fluxs + lane3Fluxs + lane4Fluxs + lane5Fluxs + lane6Fluxs);
				break;
			case "2":
				data[2][0] += (long) (lane1Flux + lane2Flux + lane3Flux);
				data[2][1] += (long) (lane4Flux + lane5Flux + lane6Flux);
				data[2][2] += (long) (lane1Fluxb + lane2Fluxb + lane3Fluxb
						+ lane4Fluxb + lane5Fluxb + lane6Fluxb);
				data[2][3] += (long) (lane1Fluxm + lane2Fluxm + lane3Fluxm
						+ lane4Fluxm + lane5Fluxm + lane6Fluxm);
				data[2][4] += (long) (lane1Fluxms + lane2Fluxms + lane3Fluxms
						+ lane4Fluxms + lane5Fluxms + lane6Fluxms + lane1Fluxs
						+ lane2Fluxs + lane3Fluxs + lane4Fluxs + lane5Fluxs + lane6Fluxs);
				break;
			case "3":
				data[3][0] += (long) (lane1Flux + lane2Flux + lane3Flux);
				data[3][1] += (long) (lane4Flux + lane5Flux + lane6Flux);
				data[3][2] += (long) (lane1Fluxb + lane2Fluxb + lane3Fluxb
						+ lane4Fluxb + lane5Fluxb + lane6Fluxb);
				data[3][3] += (long) (lane1Fluxm + lane2Fluxm + lane3Fluxm
						+ lane4Fluxm + lane5Fluxm + lane6Fluxm);
				data[3][4] += (long) (lane1Fluxms + lane2Fluxms + lane3Fluxms
						+ lane4Fluxms + lane5Fluxms + lane6Fluxms + lane1Fluxs
						+ lane2Fluxs + lane3Fluxs + lane4Fluxs + lane5Fluxs + lane6Fluxs);
				break;
			case "4":
				data[4][0] += (long) (lane1Flux + lane2Flux + lane3Flux);
				data[4][1] += (long) (lane4Flux + lane5Flux + lane6Flux);
				data[4][2] += (long) (lane1Fluxb + lane2Fluxb + lane3Fluxb
						+ lane4Fluxb + lane5Fluxb + lane6Fluxb);
				data[4][3] += (long) (lane1Fluxm + lane2Fluxm + lane3Fluxm
						+ lane4Fluxm + lane5Fluxm + lane6Fluxm);
				data[4][4] += (long) (lane1Fluxms + lane2Fluxms + lane3Fluxms
						+ lane4Fluxms + lane5Fluxms + lane6Fluxms + lane1Fluxs
						+ lane2Fluxs + lane3Fluxs + lane4Fluxs + lane5Fluxs + lane6Fluxs);
				break;
			case "5":
				data[5][0] += (long) (lane1Flux + lane2Flux + lane3Flux);
				data[5][1] += (long) (lane4Flux + lane5Flux + lane6Flux);
				data[5][2] += (long) (lane1Fluxb + lane2Fluxb + lane3Fluxb
						+ lane4Fluxb + lane5Fluxb + lane6Fluxb);
				data[5][3] += (long) (lane1Fluxm + lane2Fluxm + lane3Fluxm
						+ lane4Fluxm + lane5Fluxm + lane6Fluxm);
				data[5][4] += (long) (lane1Fluxms + lane2Fluxms + lane3Fluxms
						+ lane4Fluxms + lane5Fluxms + lane6Fluxms + lane1Fluxs
						+ lane2Fluxs + lane3Fluxs + lane4Fluxs + lane5Fluxs + lane6Fluxs);
				break;
			case "6":
				data[6][0] += (long) (lane1Flux + lane2Flux + lane3Flux);
				data[6][1] += (long) (lane4Flux + lane5Flux + lane6Flux);
				data[6][2] += (long) (lane1Fluxb + lane2Fluxb + lane3Fluxb
						+ lane4Fluxb + lane5Fluxb + lane6Fluxb);
				data[6][3] += (long) (lane1Fluxm + lane2Fluxm + lane3Fluxm
						+ lane4Fluxm + lane5Fluxm + lane6Fluxm);
				data[6][4] += (long) (lane1Fluxms + lane2Fluxms + lane3Fluxms
						+ lane4Fluxms + lane5Fluxms + lane6Fluxms + lane1Fluxs
						+ lane2Fluxs + lane3Fluxs + lane4Fluxs + lane5Fluxs + lane6Fluxs);
				break;
			default:
				break;
			}
		}

		VdDTO dto = new VdDTO();
		// 实例化流量统计值
		VdDTO.FluxCount flux = dto.new FluxCount();
		// 实例化车类型统计值
		VdDTO.CarCount carCount = dto.new CarCount();
		List<VdDTO.Datas> dates = new ArrayList<VdDTO.Datas>();

		VdDTO.Datas date1 = dto.new Datas();
		VdDTO.Datas date2 = dto.new Datas();
		date1.setName("上行");
		date2.setName("下行");
		Integer[] data1 = new Integer[data.length];
		Integer[] data2 = new Integer[data.length];
		Object[] bigCar = new Object[] { "大车", 0 };
		Object[] midCar = new Object[] { "中车", 0 };
		Object[] smCar = new Object[] { "小车", 0 };
		String[] names = new String[data.length];
		Long bigCarCount = (long) 0;
		Long midCarCount = (long) 0;
		Long smCarCount = (long) 0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < data.length; i++) {		
			names[i] = simpleDateFormat.format((new Date(beginTime.getTime()
					+ i * 86400000)));
			data1[i] = Integer.valueOf(data[i][0].toString());
			data2[i] = Integer.valueOf(data[i][1].toString());
			bigCarCount += data[i][2];
			midCarCount += data[i][3];
			smCarCount += data[i][4];
		}
		date1.setData(data1);
		date2.setData(data2);
		dates.add(date1);
		dates.add(date2);
		bigCar[1] = bigCarCount;
		midCar[1] = midCarCount;
		smCar[1] = smCarCount;
		Object[] carCo = new Object[] { bigCar, midCar, smCar };
		carCount.setCarocxx(carCo);
		flux.setTimes(names);
		flux.setData(dates);

		dto.setCarCount(carCount);
		dto.setFluxCount(flux);
		dto.setMethod(request.getHeader(Header.METHOD));

		return dto;

	}

	// 获取当天0点开始时间
	private Long getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}

}
