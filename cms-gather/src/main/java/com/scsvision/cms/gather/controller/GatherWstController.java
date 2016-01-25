package com.scsvision.cms.gather.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.gather.dto.WstDTO;
import com.scsvision.cms.gather.dto.WstDTO.Datas;
import com.scsvision.cms.gather.manager.GatherWstManager;
import com.scsvision.cms.util.number.NumberUtil;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.database.entity.GatherWst;
import com.scsvision.database.manager.OrganManager;

/**
 * GatherWstController
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:28:38
 */
@Stateless
public class GatherWstController {
	private final Logger logger = LoggerFactory
			.getLogger(GatherWstController.class);

	@EJB(beanName = "GatherWstManagerImpl")
	private GatherWstManager gatherWstManager;
	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;

	public Object listDayavgWstOfWeekJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		Long deviceId = organManager.getVOrgan(id).getDeviceId();
		List<Long> ids = new LinkedList<Long>();
		ids.add(deviceId);
		Date beginTime = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60
				* 1000);
		Date endTime = new Date();
		List<GatherWst> list = gatherWstManager
				.listWst(ids, beginTime, endTime);
		Map<Date, List<GatherWst>> map = new LinkedHashMap<Date, List<GatherWst>>();
		for (int i = 6; i >= 0; i--) {
			List<GatherWst> dayList = new LinkedList<GatherWst>();
			for (GatherWst wst : list) {
				if (wst.getStatus().intValue() == 0
						&& wst.getRecTime().getTime() >= beginTime.getTime()
						&& wst.getRecTime().getTime() < (beginTime.getTime() + 24 * 60 * 60 * 1000)) {
					dayList.add(wst);
				}
			}
			map.put(beginTime, dayList);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				beginTime = new Date(sdf.parse(sdf.format(new Date()))
						.getTime() - i * 24 * 60 * 60 * 1000);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Map<Date, Integer[]> resultMap = new LinkedHashMap<Date, Integer[]>();
		for (Entry<Date, List<GatherWst>> entity : map.entrySet()) {
			Integer[] dList = new Integer[2];
			Double avgAirTemp = 0d;
			Double avgRoadTemp = 0d;
			for (GatherWst gw : entity.getValue()) {
				avgAirTemp = NumberUtil.avgDouble(gw.getAirTemp());
				avgRoadTemp = NumberUtil.avgDouble(gw.getRoadTemp());
			}
			dList[0] = NumberUtil.getInteger(avgAirTemp);
			dList[1] = NumberUtil.getInteger(avgRoadTemp);
			resultMap.put(entity.getKey(), dList);
		}
		WstDTO dto = new WstDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		String[] times = new String[7];
		List<Datas> dataList = new LinkedList<Datas>();
		Integer[] airdata = new Integer[7];
		Integer[] roaddata = new Integer[7];
		int i = 0;
		for (Entry<Date, Integer[]> entry : resultMap.entrySet()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			times[i] = sdf.format(entry.getKey());
			airdata[i] = entry.getValue()[0];
			roaddata[i] = entry.getValue()[1];
			i++;
		}
		Datas datas1 = dto.new Datas();
		datas1.setData(airdata);
		datas1.setName("气温");
		Datas datas2 = dto.new Datas();
		datas2.setData(roaddata);
		datas2.setName("路面温度");
		dataList.add(datas1);
		dataList.add(datas2);
		dto.setData(dataList);
		dto.setTimes(times);
		return dto;
	}
}
