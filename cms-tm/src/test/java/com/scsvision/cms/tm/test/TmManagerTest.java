package com.scsvision.cms.tm.test;

import java.util.Arrays;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.openejb.api.LocalClient;

import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.manager.TmDeviceManager;

/**
 * TmManagerTest
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:34:25
 */
@LocalClient
@Stateless
public class TmManagerTest extends BaseTmTest {
	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;

	public void testMapTmDevice() {
		Map<Long, TmDevice> map = tmDeviceManager.mapTmDevice(Arrays.asList(
				Long.valueOf(1), Long.valueOf(2), Long.valueOf(3)));
		Map<Long, TmDevice> map1 = tmDeviceManager.mapTmDevice(null);
		Map<Long, TmDevice> map2 = tmDeviceManager.mapTmDevice(Arrays
				.asList(Long.valueOf(9999)));
	}
}
