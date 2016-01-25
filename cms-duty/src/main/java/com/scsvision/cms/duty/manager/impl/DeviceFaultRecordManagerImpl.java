package com.scsvision.cms.duty.manager.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.scsvision.cms.duty.dao.DeviceFaultRecordDAO;
import com.scsvision.cms.duty.dto.CountDeviceFaultDTO.CountDevice;
import com.scsvision.cms.duty.manager.DeviceFaultRecordManager;
import com.scsvision.database.entity.DeviceFaultRecord;

@Stateless
public class DeviceFaultRecordManagerImpl implements DeviceFaultRecordManager {

	@EJB(beanName = "DeviceFaultRecordDAOImpl")
	private DeviceFaultRecordDAO deviceFaultRecordDAO;

	@Override
	public Long saveDeviceFaultRecord(DeviceFaultRecord entity) {
		deviceFaultRecordDAO.save(entity);
		return entity.getId();
	}

	@Override
	public List<DeviceFaultRecord> listDeviceFaultRecord(int start, int limit,
			Long beginTime, Long endTime) {
		return deviceFaultRecordDAO.listDeviceFaultRecord(start, limit,
				beginTime, endTime);
	}

	@Override
	public Integer countDeviceFaultRecord(Long beginTime, Long endTime) {
		return deviceFaultRecordDAO.countDeviceFaultRecord(beginTime, endTime);
	}

	@Override
	public List<DeviceFaultRecord> listDeviceFaultRecord(Integer deviceType,
			String deviceName, int start, int limit, Long beginTime,
			Long endTime, List<Long> organIds, String confirmUserName) {
		return deviceFaultRecordDAO.listDeviceFaultRecord(deviceType,
				deviceName, start, limit, beginTime, endTime, organIds,
				confirmUserName);
	}

	@Override
	public Integer countDeviceFaultRecord(Integer deviceType,
			String deviceName, int start, int limit, Long beginTime,
			Long endTime, List<Long> organIds, String confirmUserName) {
		return deviceFaultRecordDAO.countDeviceFaultRecord(deviceType,
				deviceName, start, limit, beginTime, endTime, organIds,
				confirmUserName);
	}

	@Override
	public DeviceFaultRecord getDeviceFaultRecord(Long deviceFaultId) {
		return deviceFaultRecordDAO.get(deviceFaultId);
	}

	@Override
	public void updateDeviceFaultRecord(DeviceFaultRecord entity) {
		 deviceFaultRecordDAO.update(entity);
	}

	@Override
	public List<DeviceFaultRecord> countByTypeDeviceFaultRecord(Long beginTime,
			Long endTime, List<Long> organIds) {
		return deviceFaultRecordDAO.countByTypeDeviceFaultRecord(beginTime, endTime, organIds);
	}

}
