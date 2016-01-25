package com.scsvision.cms.gather.manager.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

import com.scsvision.cms.gather.dao.DeviceRealDAO;
import com.scsvision.cms.gather.manager.DeviceRealManager;

/**
 * DeviceRealManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月7日 下午5:34:26
 */
@Stateless
public class DeviceRealManagerImpl implements DeviceRealManager {
	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	public List<Document> listDeviceReal(List<Long> deviceIds) {
		return deviceRealDAO.listDeviceReal(deviceIds);
	}

}
