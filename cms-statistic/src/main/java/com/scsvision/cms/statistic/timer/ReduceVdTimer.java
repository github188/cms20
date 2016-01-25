package com.scsvision.cms.statistic.timer;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.gather.manager.GatherVdManager;
import com.scsvision.cms.statistic.manager.VdStatisticManager;
import com.scsvision.cms.util.date.DateUtil;
import com.scsvision.cms.util.number.NumberUtil;
import com.scsvision.database.entity.DayVd;
import com.scsvision.database.entity.GatherVd;
import com.scsvision.database.entity.HourVd;
import com.scsvision.database.entity.MonthVd;
import com.scsvision.database.manager.TmDeviceManager;

/**
 * ReduceVdTimer
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:56:02
 */
@Singleton
public class ReduceVdTimer {

	private final Logger logger = LoggerFactory.getLogger(ReduceVdTimer.class);

	@EJB(beanName = "VdStatisticManagerImpl")
	private VdStatisticManager vdStatisticManager;
	@EJB(beanName = "GatherVdManagerImpl")
	private GatherVdManager gatherVdManager;
	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;

	@Schedule(second = "0", minute = "0", hour = "*", dayOfMonth = "*", month = "*", year = "*", persistent = false)
	public void reduceByHour() {
		try {
			Date beginTime = null;
			Date endTime = null;

			// 查询最后的归并时间
			HourVd latest = vdStatisticManager.getLastestHourVd();
			if (null != latest) {
				beginTime = new Date(latest.getRecTime().getTime() + 1);
			}
			// 没有归并过，获取原始数据的最早采集时间
			else {
				GatherVd first = gatherVdManager.getFirst();
				if (null == first) {
					return;
				}
				beginTime = first.getRecTime();
			}

			// 每次最多归并10天的采集数据
			endTime = new Date(beginTime.getTime() + 864000000l);
			// 原始数据
			List<GatherVd> list = gatherVdManager
					.list(null, beginTime, endTime);
			if (list.size() == 0) {
				return;
			}
			// 设备与路段真实机构的归属关系，隧道和桥梁上的设备归属到上级路段中
			Map<Long, Long> deviceMap = tmDeviceManager
					.mapDeviceByRoad(TypeDefinition.VEHICLE_DETECTOR_COIL / 100
							+ "");
			// 第一条记录的采集时间
			Date firstTime = list.get(0).getRecTime();
			// 计算得到第一个小时段的最大时间
			long upperLimit = DateUtil.getUpperHourTime(firstTime);
			long lowerLimit = DateUtil.getLowerHourTime(firstTime);
			// 要归并的数据集合,每个机构每小时一条数据, key-为机构ID, value-为该机构各小时的记录
			Map<Long, List<HourVd>> map = new HashMap<Long, List<HourVd>>();
			// 迭代采集数据，按小时归并
			for (GatherVd data : list) {
				long recTime = data.getRecTime().getTime();
				Long organId = deviceMap.get(data.getDeviceId());
				if (null == organId) {
					continue;
				}
				// 本小时的归并数据
				HourVd hvd = null;
				// 在本小时内计算统计值
				if (recTime < upperLimit) {
					List<HourVd> hvdList = map.get(organId);
					// 初始化map
					if (hvdList == null) {
						hvdList = new LinkedList<HourVd>();
						map.put(organId, hvdList);
					}
					// 初始化本小时数据
					if (hvdList.size() < 1) {
						hvd = initHvd(data, organId);
						hvdList.add(hvd);
					} else {
						// 最后一条记录
						hvd = hvdList.get(hvdList.size() - 1);
						// 归并数据, 注意达到下一个小时的初始化处理
						if (hvd.getRecTime().getTime() < lowerLimit) {
							HourVd nextHvd = initHvd(data, organId);
							hvdList.add(nextHvd);
						} else {
							mergeHvd(data, hvd);
						}
					}
				}
				// 初始化计算下1小时的数据
				else {
					upperLimit = DateUtil.getUpperHourTime(data.getRecTime());
					lowerLimit = DateUtil.getLowerHourTime(data.getRecTime());
					List<HourVd> hvdList = map.get(organId);
					// 因为可能有新机构加入，所以同样需要初始化map
					if (hvdList == null) {
						hvdList = new LinkedList<HourVd>();
						map.put(organId, hvdList);
					}
					// 初始化下1小时数据
					if (hvdList.size() < 1) {
						hvd = initHvd(data, organId);
						hvdList.add(hvd);
					} else {
						// 最后一条记录
						hvd = hvdList.get(hvdList.size() - 1);
						// 归并数据, 注意达到下一个小时的初始化处理
						if (hvd.getRecTime().getTime() < lowerLimit) {
							HourVd nextHvd = initHvd(data, organId);
							hvdList.add(nextHvd);
						} else {
							mergeHvd(data, hvd);
						}
					}
				}
			}
			// 要写入数据库的集合
			List<HourVd> insertList = new LinkedList<HourVd>();
			Collection<List<HourVd>> values = map.values();
			if (values == null || values.size() == 0) {
				logger.error("All records from " + beginTime + " to " + endTime
						+ " in gather_vd are dirty data, remove them first !");
				return;
			}
			for (List<HourVd> value : values) {
				insertList.addAll(value);
			}
			vdStatisticManager.batchInsertHourVd(insertList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化小时归并数据
	 * 
	 * @param data
	 *            采集数据
	 * @param organId
	 *            真实路段机构ID
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-17 下午3:11:22
	 */
	private HourVd initHvd(GatherVd data, Long organId) {
		HourVd hvd = new HourVd();
		hvd.setFlux(NumberUtil.plusDouble(data.getLane1Flux(),
				data.getLane2Flux(), data.getLane3Flux(), data.getLane4Flux(),
				data.getLane5Flux(), data.getLane6Flux()));
		hvd.setHeadWayAvg(NumberUtil.avgDouble(data.getLane1Headway(),
				data.getLane2Headway(), data.getLane3Headway(),
				data.getLane4Headway(), data.getLane5Headway(),
				data.getLane6Headway()));
		hvd.setOccAvg(NumberUtil.avgDouble(data.getLane1Occ(),
				data.getLane2Occ(), data.getLane3Occ(), data.getLane4Occ(),
				data.getLane5Occ(), data.getLane6Occ()));
		hvd.setRecTime(data.getRecTime());
		hvd.setSpeedAvg(NumberUtil.avgDouble(data.getLane1Speed(),
				data.getLane2Speed(), data.getLane3Speed(),
				data.getLane4Speed(), data.getLane5Speed(),
				data.getLane6Speed()));
		hvd.setOrganId(organId);
		return hvd;
	}

	/**
	 * 合并归并数据
	 * 
	 * @param data
	 *            采集数据
	 * @param hvd
	 *            已存在的归并数据
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:43:44
	 */
	private void mergeHvd(GatherVd data, HourVd hvd) {
		hvd.setFlux(NumberUtil.plusDouble(hvd.getFlux(), data.getLane1Flux(),
				data.getLane2Flux(), data.getLane3Flux(), data.getLane4Flux(),
				data.getLane5Flux(), data.getLane6Flux()));
		hvd.setHeadWayAvg(NumberUtil.avgDouble(hvd.getHeadWayAvg(),
				data.getLane1Headway(), data.getLane2Headway(),
				data.getLane3Headway(), data.getLane4Headway(),
				data.getLane5Headway(), data.getLane6Headway()));
		hvd.setOccAvg(NumberUtil.avgDouble(hvd.getOccAvg(), data.getLane1Occ(),
				data.getLane2Occ(), data.getLane3Occ(), data.getLane4Occ(),
				data.getLane5Occ(), data.getLane6Occ()));
		hvd.setRecTime(data.getRecTime());
		hvd.setSpeedAvg(NumberUtil.avgDouble(hvd.getSpeedAvg(),
				data.getLane1Speed(), data.getLane2Speed(),
				data.getLane3Speed(), data.getLane4Speed(),
				data.getLane5Speed(), data.getLane6Speed()));
	}

	@Schedule(second = "0", minute = "1", hour = "0", dayOfMonth = "*", month = "*", year = "*", persistent = false)
	public void reduceByDay() {
		try {
			Date beginTime = null;
			Date endTime = null;

			// 查询最后的归并时间
			DayVd latest = vdStatisticManager.getLastestDayVd();
			if (null != latest) {
				beginTime = new Date(latest.getRecTime().getTime() + 1);
			}
			// 没有归并过，获取小时归并数据的最早时间
			else {
				HourVd first = vdStatisticManager.getFirstHourVd();
				if (null == first) {
					return;
				}
				beginTime = first.getRecTime();
			}

			// 每次最多归并30天的采集数据
			endTime = new Date(beginTime.getTime() + 30 * 86400000l);
			// 原始数据
			List<HourVd> list = vdStatisticManager.listByHour(null, beginTime,
					endTime, 0, 999999);
			if (list.size() == 0) {
				return;
			}
			// 第一条记录的采集时间
			Date firstTime = list.get(0).getRecTime();
			// 计算得到第一个小时段的最大时间
			long upperLimit = DateUtil.getUpperDayTime(firstTime);
			long lowerLimit = DateUtil.getLowerDayTime(firstTime);
			// 要归并的数据集合,每个机构每天一条数据, key-为机构ID, value-为该机构每天的记录
			Map<Long, List<DayVd>> map = new HashMap<Long, List<DayVd>>();
			// 迭代小时归并数据，按天归并
			for (HourVd data : list) {
				long recTime = data.getRecTime().getTime();
				Long organId = data.getOrganId();
				if (null == organId) {
					continue;
				}
				// 本日的归并数据
				DayVd dvd = null;
				// 在本日内计算统计值
				if (recTime < upperLimit) {
					List<DayVd> dvdList = map.get(organId);
					// 初始化map
					if (dvdList == null) {
						dvdList = new LinkedList<DayVd>();
						map.put(organId, dvdList);
					}
					// 初始化本日数据
					if (dvdList.size() < 1) {
						dvd = initDvd(data);
						dvdList.add(dvd);
					} else {
						// 最后一条记录
						dvd = dvdList.get(dvdList.size() - 1);
						// 归并数据, 注意达到下一个小时的初始化处理
						if (dvd.getRecTime().getTime() < lowerLimit) {
							DayVd nextDvd = initDvd(data);
							dvdList.add(nextDvd);
						} else {
							mergeDvd(data, dvd);
						}
					}
				}
				// 初始化计算下1天的数据
				else {
					upperLimit = DateUtil.getUpperDayTime(data.getRecTime());
					lowerLimit = DateUtil.getLowerDayTime(data.getRecTime());
					List<DayVd> dvdList = map.get(organId);
					// 因为可能有新机构加入，所以同样需要初始化map
					if (dvdList == null) {
						dvdList = new LinkedList<DayVd>();
						map.put(organId, dvdList);
					}
					// 初始化下1天数据
					if (dvdList.size() < 1) {
						dvd = initDvd(data);
						dvdList.add(dvd);
					} else {
						// 最后一条记录
						dvd = dvdList.get(dvdList.size() - 1);
						// 归并数据, 注意达到下一个小时的初始化处理
						if (dvd.getRecTime().getTime() < lowerLimit) {
							DayVd nextDvd = initDvd(data);
							dvdList.add(nextDvd);
						} else {
							mergeDvd(data, dvd);
						}
					}
				}
			}
			// 要写入数据库的集合
			List<DayVd> insertList = new LinkedList<DayVd>();
			Collection<List<DayVd>> values = map.values();
			if (values == null || values.size() == 0) {
				logger.error("All records from " + beginTime + " to " + endTime
						+ " in hour_vd are dirty data, remove them first !");
				return;
			}
			for (List<DayVd> value : values) {
				insertList.addAll(value);
			}
			vdStatisticManager.batchInsertDayVd(insertList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化天归并数据
	 * 
	 * @param data
	 *            小时归并数据
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-11-11 下午3:11:22
	 */
	private DayVd initDvd(HourVd data) {
		DayVd dvd = new DayVd();
		dvd.setFlux(data.getFlux());
		dvd.setHeadWayAvg(data.getHeadWayAvg());
		dvd.setOccAvg(data.getOccAvg());
		dvd.setRecTime(data.getRecTime());
		dvd.setSpeedAvg(data.getSpeedAvg());
		dvd.setOrganId(data.getOrganId());
		return dvd;
	}

	/**
	 * 合并归并数据
	 * 
	 * @param data
	 *            小时归并数据
	 * @param dvd
	 *            已存在的天归并数据
	 * @author MIKE
	 *         <p />
	 *         Create at 2015-11-11 下午3:43:44
	 */
	private void mergeDvd(HourVd data, DayVd dvd) {
		dvd.setFlux(NumberUtil.plusDouble(dvd.getFlux(), data.getFlux()));
		dvd.setHeadWayAvg(NumberUtil.avgDouble(dvd.getHeadWayAvg(),
				data.getHeadWayAvg()));
		dvd.setOccAvg(NumberUtil.avgDouble(dvd.getOccAvg(), data.getOccAvg()));
		dvd.setRecTime(data.getRecTime());
		dvd.setSpeedAvg(NumberUtil.avgDouble(dvd.getSpeedAvg(),
				data.getSpeedAvg()));
	}

	@Schedule(second = "0", minute = "0", hour = "1", dayOfMonth = "1", month = "*", year = "*", persistent = false)
	public void reduceByMonth() {
		try {
			Date beginTime = null;
			Date endTime = null;

			// 获取最后归并时间
			MonthVd latest = vdStatisticManager.getLastestMonthVd();
			if (null != latest) {
				beginTime = new Date(latest.getRecTime().getTime() + 1);
			}
			// 没有归并过，查询按天归并的最早记录
			else {
				DayVd first = vdStatisticManager.getFirstDayVd();
				if (null == first) {
					return;
				}
				beginTime = first.getRecTime();
			}

			// 每次最多归并一年的采集数据
			endTime = new Date(beginTime.getTime() + 12 * 30 * 86400000l);
			// 获取原始数据
			List<DayVd> list = vdStatisticManager.listByDay(null, beginTime,
					endTime, 0, 999999);
			if (list.size() == 0) {
				return;
			}
			// 获取第一条数据的采集时间
			Date firstTime = list.get(0).getRecTime();
			// 计算第一个月的最大时间
			Long upperLimit = DateUtil.getUpperMonthTime(firstTime);
			Long lowerLimit = DateUtil.getLowerMonthTime(firstTime);
			// 要归并的数据集合，每个机构每月一条记录，key为机构Id，value为该机构每月记录
			Map<Long, List<MonthVd>> map = new HashMap<Long, List<MonthVd>>();
			// 迭代天归并数据，按月归并
			for (DayVd data : list) {
				Long recTime = data.getRecTime().getTime();
				Long organId = data.getOrganId();
				if (null == organId) {
					continue;
				}
				// 本月的归并数据
				MonthVd mvd = null;
				// 在本月内计算统计值
				if (recTime < upperLimit) {
					List<MonthVd> mvdList = map.get(organId);
					// 初始化map
					if (null == mvdList) {
						mvdList = new LinkedList<MonthVd>();
						map.put(organId, mvdList);
					}
					// 初始化本月数据
					if (mvdList.size() < 1) {
						mvd = initMvd(data);
						mvdList.add(mvd);
					} else {
						// 获取最后一条数据
						mvd = mvdList.get(mvdList.size() - 1);
						// 归并数据，如果到下一个月则要初始化值
						if (mvd.getRecTime().getTime() < lowerLimit) {
							MonthVd nextMvd = initMvd(data);
							mvdList.add(nextMvd);
						} else {
							mergeMvd(data, mvd);
						}
					}
				}
				// 初始化计算下一个月的数据
				else {
					upperLimit = DateUtil.getUpperMonthTime(data.getRecTime());
					lowerLimit = DateUtil.getLowerMonthTime(data.getRecTime());
					List<MonthVd> mvdList = map.get(organId);
					// 因为可能有新加入的机构，所以还是需要初始化map
					if (null == mvdList) {
						mvdList = new LinkedList<MonthVd>();
						map.put(organId, mvdList);
					}
					// 初始化本月数据
					if (mvdList.size() < 1) {
						mvd = initMvd(data);
						mvdList.add(mvd);
					} else {
						// 获取最后一条数据
						mvd = mvdList.get(mvdList.size() - 1);
						if (mvd.getRecTime().getTime() < lowerLimit) {
							MonthVd nextMvd = initMvd(data);
							mvdList.add(nextMvd);
						} else {
							mergeMvd(data, mvd);
						}
					}
				}
			}
			// 要写入数据库的集合
			List<MonthVd> insertList = new LinkedList<MonthVd>();
			Collection<List<MonthVd>> values = map.values();
			if (values == null || values.size() == 0) {
				logger.error("All records from " + beginTime + " to " + endTime
						+ " in Month_vd are dirty data, remove them first !");
				return;
			}
			for (List<MonthVd> value : values) {
				insertList.addAll(value);
			}
			vdStatisticManager.batchInsertMonthVd(insertList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MonthVd initMvd(DayVd data) {
		MonthVd mvd = new MonthVd();
		mvd.setFlux(data.getFlux());
		mvd.setHeadWayAvg(data.getHeadWayAvg());
		mvd.setOccAvg(data.getOccAvg());
		mvd.setOrganId(data.getOrganId());
		mvd.setRecTime(data.getRecTime());
		mvd.setSpeedAvg(data.getSpeedAvg());
		return mvd;
	}

	private void mergeMvd(DayVd data, MonthVd mvd) {
		mvd.setFlux(NumberUtil.plusDouble(data.getFlux(), mvd.getFlux()));
		mvd.setHeadWayAvg(NumberUtil.avgDouble(data.getHeadWayAvg(),
				mvd.getHeadWayAvg()));
		mvd.setOccAvg(NumberUtil.avgDouble(data.getOccAvg(), mvd.getOccAvg()));
		mvd.setSpeedAvg(NumberUtil.avgDouble(data.getSpeedAvg(),
				mvd.getSpeedAvg()));
		mvd.setRecTime(data.getRecTime());
	}
}
