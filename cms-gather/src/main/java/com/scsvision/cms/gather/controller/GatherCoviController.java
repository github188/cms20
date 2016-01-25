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
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.gather.dto.CoviDTO;
import com.scsvision.cms.gather.dto.CoviDTO.Covi;
import com.scsvision.cms.gather.dto.FiveHourCoviDTO;
import com.scsvision.cms.gather.manager.GatherCoviManager;
import com.scsvision.cms.gather.service.vo.ListGatherCoviVO;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.GatherCovi;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.TmDeviceManager;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class GatherCoviController {
	@EJB(beanName = "GatherCoviManagerImpl")
	private GatherCoviManager gatherCoviManager;

	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;

	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;

	public Object listLatestCoviJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		List<Integer> list = new ArrayList<Integer>();
		list.add(TypeDefinition.DETECTOR_COVI);
		List<Long> ids = organManager.listDeviceIds(id, list);
		Map<Long, TmDevice> mapDevice = tmDeviceManager.mapTmDevice(ids);
		Map<Long, GatherCovi> mapCovi = new HashMap<Long, GatherCovi>();
		for (Long deviceId : ids) {
			mapCovi.put(deviceId,
					gatherCoviManager.getGatherEntity(deviceId, null, null));
		}
		Map<Long, GatherCovi> coviMap = new HashMap<Long, GatherCovi>();
		long now = System.currentTimeMillis();
		for (Entry<Long, GatherCovi> entity : mapCovi.entrySet()) {
			if (null != entity.getValue()) {
				if ((now - entity.getValue().getRecTime().getTime()) < mapDevice
						.get(entity.getKey()).getGatherInterval().longValue()) {
					coviMap.put(entity.getKey(), entity.getValue());
				} else {
					GatherCovi cv = entity.getValue();
					cv.setCo((double) 0);
					cv.setVi((double) 0);
					coviMap.put(entity.getKey(), cv);
				}
			}
		}
		CoviDTO dto = new CoviDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		List<Covi> cList = new LinkedList<Covi>();
		for (Entry<Long, GatherCovi> entry : coviMap.entrySet()) {
			Covi covi = dto.new Covi();
			covi.setId(entry.getValue().getId());
			covi.setCo(entry.getValue().getCo());
			covi.setVi(entry.getValue().getVi());
			covi.setDeviceId(entry.getValue().getDeviceId());
			covi.setRecTime(entry.getValue().getRecTime().getTime());
			cList.add(covi);
		}
		dto.setList(cList);
		return dto;
	}

	public Object listFiveHoursCoviJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		List<Integer> list = new ArrayList<Integer>();
		list.add(TypeDefinition.DETECTOR_COVI);
		List<Long> ids = organManager.listDeviceIds(id, list);
		Map<Long, TmDevice> mapDevice = tmDeviceManager.mapTmDevice(ids);

		Date beginTime = new Date(System.currentTimeMillis() - 5 * 60 * 60
				* 1000);
		Date endTime = new Date();
		List<GatherCovi> covis = gatherCoviManager.listCovi(ids, beginTime,
				endTime);

		Map<Long, List<GatherCovi>> mapGatherCovi = new HashMap<Long, List<GatherCovi>>();
		for (Long deviceId : ids) {
			List<GatherCovi> gcovis = new ArrayList<GatherCovi>();
			for (GatherCovi covi : covis) {
				if (deviceId.equals(covi.getDeviceId())) {
					gcovis.add(covi);
				}
			}
			mapGatherCovi.put(deviceId, gcovis);
		}
		Long beginTime1 = beginTime.getTime();

		boolean flag = true;
		List<ListGatherCoviVO> resultCo = new ArrayList<ListGatherCoviVO>();
		List<ListGatherCoviVO> resultVi = new ArrayList<ListGatherCoviVO>();
		for (Long deviceId : ids) {
			Integer gatherInterval = mapDevice.get(deviceId)
					.getGatherInterval();
			ListGatherCoviVO coVO = new ListGatherCoviVO();
			coVO.setId(deviceId);
			coVO.setName(mapDevice.get(deviceId).getName());
			ListGatherCoviVO viVO = new ListGatherCoviVO();
			viVO.setId(deviceId);
			viVO.setName(mapDevice.get(deviceId).getName());
			List<ListGatherCoviVO.CoviData> cos = new ArrayList<ListGatherCoviVO.CoviData>();
			List<ListGatherCoviVO.CoviData> vis = new ArrayList<ListGatherCoviVO.CoviData>();
			List<GatherCovi> listCovi = mapGatherCovi.get(deviceId);
			for (int i = 0; i < 10; i++) {
				flag = true;
				long recTime = beginTime1 + i * 30 * 60 * 1000;
				if (listCovi != null && listCovi.size() > 0) {
					for (GatherCovi covi : listCovi) {
						if (covi.getRecTime().getTime() > recTime
								&& covi.getRecTime().getTime() < recTime
										+ gatherInterval * 1000) {
							flag = false;
							ListGatherCoviVO.CoviData co = coVO.new CoviData();
							co.setX(recTime);
							co.setY(covi.getCo() != null ? Double
									.parseDouble(MyStringUtil.cutObject(
											covi.getCo(), 2)) : 0.0);
							cos.add(co);
							ListGatherCoviVO.CoviData vi = viVO.new CoviData();
							vi.setX(recTime);
							vi.setY(covi.getVi() != null ? Double
									.parseDouble(MyStringUtil.cutObject(
											covi.getVi(), 2)) : 0.0);
							vis.add(vi);
							break;
						}
					}
					if (flag) {
						ListGatherCoviVO.CoviData co = coVO.new CoviData();
						co.setX(recTime);
						co.setY(0.0);
						cos.add(co);
						ListGatherCoviVO.CoviData vi = viVO.new CoviData();
						vi.setX(recTime);
						vi.setY(0.0);
						vis.add(vi);
					}
				}
			}
			coVO.setData(cos);
			viVO.setData(vis);
			resultCo.add(coVO);
			resultVi.add(viVO);
		}

		FiveHourCoviDTO dto = new FiveHourCoviDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setCoList(resultCo);
		dto.setViList(resultVi);
		return dto;
	}

}
