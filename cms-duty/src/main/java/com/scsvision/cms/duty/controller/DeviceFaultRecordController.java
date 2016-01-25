package com.scsvision.cms.duty.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.duty.dto.CountDeviceFaultDTO;
import com.scsvision.cms.duty.dto.CountDeviceFaultDTO.CountDevice;
import com.scsvision.cms.duty.dto.DeviceFaultDTO;
import com.scsvision.cms.duty.manager.DeviceFaultRecordManager;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.time.util.TimeUtil;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.date.DateUtil;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.database.entity.DeviceFaultRecord;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.User;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.SvDeviceManager;
import com.scsvision.database.manager.TmDeviceManager;
import com.scsvision.database.manager.UserManager;

@Stateless
public class DeviceFaultRecordController {
	@EJB(beanName = "DeviceFaultRecordManagerImpl")
	private DeviceFaultRecordManager deviceFaultRecordManager;

	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;

	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;

	@EJB(beanName = "SvDeviceManagerImpl")
	private SvDeviceManager svDeviceManager;

	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;

	public Object saveDeviceFaultRecordJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long deviceId = reader.getLong("deviceId", false);
		String note = reader.getString("note", false);
		String maintainer = reader.getString("maintainer", false);

		VirtualOrgan virtualOrgan = organManager.getVOrgan(deviceId);

		DeviceFaultRecord deviceFaultRecord = new DeviceFaultRecord();
		deviceFaultRecord.setDeviceId(deviceId);

		deviceFaultRecord.setConfirmFlag(TypeDefinition.CONFIRM_FLAG_YES);
		deviceFaultRecord.setRecordTime(System.currentTimeMillis());
		deviceFaultRecord.setConfirmTime(deviceFaultRecord.getRecordTime());
		deviceFaultRecord.setConfirmUserName(maintainer);
		deviceFaultRecord.setMaintainer(maintainer);
		deviceFaultRecord.setNote(note);
		if (virtualOrgan.getType() / 100 != TypeDefinition.VIDEO_CAMERA_GUN / 100) {
			TmDevice tmDevice = tmDeviceManager.getTmDevice(virtualOrgan
					.getDeviceId());
			deviceFaultRecord.setOrganId(tmDevice.getOrganId());
			deviceFaultRecord.setDeviceType(tmDevice.getType()
					+ tmDevice.getSubType());
			deviceFaultRecord.setStakeNumber(tmDevice.getStakeNumber());
			deviceFaultRecord.setDeviceName(tmDevice.getName());
		}
		if (virtualOrgan.getType() / 100 == TypeDefinition.VIDEO_CAMERA_GUN / 100) {
			SvDevice svDevice = svDeviceManager.getSvDevice(deviceId);
			deviceFaultRecord.setDeviceType(svDevice.getType()
					+ svDevice.getSubType());
			deviceFaultRecord.setOrganId(svDevice.getOrganId());
			deviceFaultRecord.setStakeNumber(svDevice.getStakeNumber());
			deviceFaultRecord.setDeviceName(svDevice.getName());
		}

		Long id = deviceFaultRecordManager
				.saveDeviceFaultRecord(deviceFaultRecord);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(String.valueOf(id));

		return dto;
	}

	public Object updateDeviceFaultRecordJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		String note = reader.getString("note", true);
		String maintainer = reader.getString("maintainer", true);

		DeviceFaultRecord deviceFaultRecord = deviceFaultRecordManager
				.getDeviceFaultRecord(id);
		if (StringUtils.isNotBlank(note)) {
			deviceFaultRecord.setNote(note);
		}
		if (StringUtils.isNotBlank(maintainer)) {
			deviceFaultRecord.setMaintainer(maintainer);
		}

		deviceFaultRecordManager.updateDeviceFaultRecord(deviceFaultRecord);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;

	}

	@Logon
	public Object listDeviceFaultRecordJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("organId", true);
		Long beginTime = reader.getLong("beginTime", true);
		Integer deviceType = reader.getInteger("deviceType", true);
		String deviceName = reader.getString("deviceName", true);
		Long endTime = reader.getLong("endTime", true);
		String maintainer = reader.getString("maintainer", true);
		Integer start = reader.getInteger("start", true);
		Integer limit = reader.getInteger("limit", true);
		Long userId = reader.getUserId();

		if (null == start) {
			start = 0;
		}
		if (null == limit) {
			limit = 20;
		}
		// 默认查一个月
		if (null == beginTime) {
			beginTime = Long
					.valueOf(System.currentTimeMillis() - 30 * 24 * 3600000l);
		}
		if (null == endTime) {
			endTime = Long.valueOf(System.currentTimeMillis());
		}

		// 真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();

		if (null != organId) {
			organIds.add(organId);
		} else {
			// 查询虚拟子机构
			List<VirtualOrgan> organs = null;
			User user = userManager.getUser(userId);
			organs = organManager.listUgAllOrgan(user.getUserGroupId());
			if (organs.size() == 0) {
				throw new BusinessException(ErrorCode.DIRTY_DATA_FOUND,
						"Organ has not contain any real Organ !");
			}
			for (VirtualOrgan vo : organs) {
				if (vo.getDeviceId() != null) {
					organIds.add(vo.getDeviceId());
				}
			}
		}

		int count = deviceFaultRecordManager.countDeviceFaultRecord(deviceType,
				deviceName, start, limit, beginTime, endTime, organIds,
				maintainer);
		List<DeviceFaultRecord> list = deviceFaultRecordManager
				.listDeviceFaultRecord(deviceType, deviceName, start, limit,
						beginTime, endTime, organIds, maintainer);
		DeviceFaultDTO dto = new DeviceFaultDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		List<DeviceFaultDTO.DeviceFault> deviceFaults = new ArrayList<DeviceFaultDTO.DeviceFault>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DeviceFaultRecord deviceFaultRecord = (DeviceFaultRecord) iterator
					.next();
			DeviceFaultDTO.DeviceFault deviceFault = dto.new DeviceFault();
			deviceFault.setDeviceName(deviceFaultRecord.getDeviceName());
			deviceFault.setDeviceType(deviceFaultRecord.getDeviceType());
			deviceFault.setMaintainer(deviceFaultRecord.getMaintainer());
			deviceFault.setNote(deviceFaultRecord.getNote());
			deviceFault.setOrganId(deviceFaultRecord.getOrganId());
			Organ organ = organManager.getOrganById(deviceFaultRecord
					.getOrganId());
			deviceFault.setOrganName(organ.getName());
			deviceFault.setRecordTime(TimeUtil.getStringTime(
					deviceFaultRecord.getRecordTime(), 2));
			deviceFault.setStakeNumber(deviceFaultRecord.getStakeNumber());
			deviceFaults.add(deviceFault);
		}
		dto.setList(deviceFaults);
		dto.setMessage(String.valueOf(count));

		return dto;
	}

	@Logon
	public Object countByTypeDeviceFaultRecordJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		Long organId = reader.getLong("organId", true);
		Integer deviceType = reader.getInteger("deviceType", true);
		Long userId = reader.getUserId();
		// 默认查一个月
		if (null == beginTime) {
			beginTime = DateUtil.getLowerDayTime(new Date()) - 6 * 24 * 3600000l;
		}
		if (null == endTime) {
			endTime = Long.valueOf(System.currentTimeMillis());
		}
		// 真实机构ID列表
		List<Long> organIds = new LinkedList<Long>();
		Organ organ = null;
		if (null != organId) {
			organIds.add(organId);
			organ = organManager.getOrganById(organId);
		} else {
			// 查询虚拟子机构
			List<VirtualOrgan> organs = null;
			User user = userManager.getUser(userId);
			organs = organManager.listUgAllOrgan(user.getUserGroupId());
			if (organs.size() == 0) {
				throw new BusinessException(ErrorCode.DIRTY_DATA_FOUND,
						"Organ has not contain any real Organ !");
			}
			for (VirtualOrgan vo : organs) {
				if (vo.getDeviceId() != null) {
					organIds.add(vo.getDeviceId());
				}
			}
		}

		// 根据传入的参数确定，对应查询的天
		int day = (int) (1 + ((endTime - beginTime) / 86400000l));
		// 创建一个二维数组,维度分别代表时间（天），设备类型
		int dataCount[][] = new int[day][TypeDefinition.DEVICE_COUNT];
		// 初始化数组
		for (int i = 0; i < dataCount.length; i++) {
			for (int j = 0; j < dataCount[0].length; j++) {
				dataCount[i][j] = 0;
			}
		}
		// 查询按照起始时间和结束时间的手工保障表
		List<DeviceFaultRecord> list = deviceFaultRecordManager
				.countByTypeDeviceFaultRecord(beginTime, endTime, organIds);
		for (DeviceFaultRecord deviceFaultRecord : list) {
			int date = (int) ((deviceFaultRecord.getRecordTime() - beginTime) / 86400000l);
			Integer type = Integer.valueOf(deviceFaultRecord.getDeviceType());
			// 按照区间类型进行统计设备（3100<=x<3200:摄像头，4000<=x<4100:情报板，4100<=x<4200:车检器）
			// 摄像头
			if (type >= 3100 && type < 3200) {
				dataCount[date][0]++;
			}
			// 情报板
			if (type >= 4000 && type < 4100) {
				dataCount[date][1]++;
			}
			// 车检器
			if (type >= 4100 && type < 4200) {
				dataCount[date][2]++;
			}
			// 气象检测器
			if (type == TypeDefinition.WEATHER_DETECTOR) {
				dataCount[date][4]++;
			}
			// 风速风向
			if (type == TypeDefinition.WEATHER_DETECTOR_WIND) {
				dataCount[date][5]++;
			}
			// 路面检测
			if (type == TypeDefinition.WEATHER_DETECTOR_ROAD) {
				dataCount[date][6]++;
			}
			// 桥面检测
			if (type == TypeDefinition.WEATHER_DETECTOR_BRIDGE) {
				dataCount[date][7]++;
			}
			// 能见度
			if (type == TypeDefinition.WEATHER_DETECTOR_VI) {
				dataCount[date][8]++;
			}
			// COVI
			if (type == TypeDefinition.DETECTOR_COVI) {
				dataCount[date][9]++;
			}
			// 氮氧
			if (type == TypeDefinition.DETECTOR_NO) {
				dataCount[date][10]++;
			}
			// 光强
			if (type == TypeDefinition.DETECTOR_LOLI) {
				dataCount[date][11]++;
			}
			// 风机
			if (type == TypeDefinition.SWITCH_Fan) {
				dataCount[date][12]++;
			}
			// 卷帘门
			if (type == TypeDefinition.SWITCH_ROLLING_DOOR) {
				dataCount[date][13]++;
			}
			// 栏杆机
			if (type == TypeDefinition.SWITCH_RAIL) {
				dataCount[date][14]++;
			}
			// 光纤光栅
			if (type == TypeDefinition.SWITCH_FIBER) {
				dataCount[date][15]++;
			}
			// 火灾报警按钮
			if (type == TypeDefinition.SWITCH_BUTTON) {
				dataCount[date][16]++;
			}
			// 水池水泵
			if (type == TypeDefinition.SWITCH_WATER_PUMP) {
				dataCount[date][17]++;
			}
			// 车道指示灯
			if (type == TypeDefinition.VMS_LANE_INDICATOR) {
				dataCount[date][18]++;
			}
			// 交通信号灯
			if (type == TypeDefinition.VMS_TRAFFIC_SIGN) {
				dataCount[date][3]++;
			}
		}
		CountDeviceFaultDTO dto = new CountDeviceFaultDTO();
		// 确定时间轴
		List<String> names = new ArrayList<String>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < dataCount.length; i++) {
			names.add(simpleDateFormat.format((new Date(beginTime + i
					* 86400000))));
		}
		// 数据值
		List<CountDevice> data = new ArrayList<CountDeviceFaultDTO.CountDevice>();
		for (int i = 0; i < dataCount[0].length; i++) {
			CountDevice countDevice = dto.new CountDevice();
			List<Integer> integers = new ArrayList<Integer>();
			for (int j = 0; j < dataCount.length; j++) {
				integers.add(dataCount[j][i]);
			}
			countDevice.setDeviceCount(integers);
			// 摄像头
			if (i == 0) {
				countDevice.setDeviceName("摄像头");
			}
			// 情报板
			if (i == 1) {
				countDevice.setDeviceName("情报板");
			}
			// 车检器
			if (i == 2) {
				countDevice.setDeviceName("车检器");
			}
			// 气象检测器
			if (i == 4) {
				countDevice.setDeviceName("气象检测器");
			}
			// 风速风向
			if (i == 5) {
				countDevice.setDeviceName("风速风向");
			}
			// 路面检测
			if (i == 6) {
				countDevice.setDeviceName("路面检测");
			}
			// 桥面检测
			if (i == 7) {
				countDevice.setDeviceName("桥面检测");
			}
			// 能见度
			if (i == 8) {
				countDevice.setDeviceName("能见度");
			}
			// COVI
			if (i == 9) {
				countDevice.setDeviceName("COVI");
			}
			// 氮氧
			if (i == 10) {
				countDevice.setDeviceName("氮氧");
			}
			// 光强
			if (i == 11) {
				countDevice.setDeviceName("光强");
			}
			// 风机
			if (i == 12) {
				countDevice.setDeviceName("风机");
			}
			// 卷帘门
			if (i == 13) {
				countDevice.setDeviceName("卷帘门");
			}
			// 栏杆机
			if (i == 14) {
				countDevice.setDeviceName("栏杆机");
			}
			// 光纤光栅
			if (i == 15) {
				countDevice.setDeviceName("光纤光栅");
			}
			// 火灾报警按钮
			if (i == 16) {
				countDevice.setDeviceName("火灾报警按钮");
			}
			// 水池水泵
			if (i == 17) {
				countDevice.setDeviceName("水池水泵");
			}
			// 车道指示灯
			if (i == 18) {
				countDevice.setDeviceName("车道指示灯");
			}
			// 交通信号灯
			if (i == 3) {
				countDevice.setDeviceName("交通信号灯");
			}
			data.add(countDevice);
		}
		dto.setData(data);
		dto.setNames(names);
		dto.setOrganName(organ == null ? "" : organ.getName());
		dto.setMethod(request.getHeader(Header.METHOD));

		return dto;
	}

}
