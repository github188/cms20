package com.scsvision.cms.em.test;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.openejb.api.LocalClient;

import com.scsvision.cms.em.manager.EventManualManager;
import com.scsvision.database.entity.EventManualReal;

/**
 * EventRealManagerTest
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午5:15:52
 */
@LocalClient
@Stateless
public class EventRealManagerTest extends BaseTest {
	@EJB(beanName = "EventManualManagerImpl")
	private EventManualManager eventManualManager;
	
	public void testCreateEventManualReal() {
		EventManualReal entity = new EventManualReal();
		entity.setId(System.currentTimeMillis());
		entity.setAdministration("test");
		Long id = eventManualManager.createEventManualReal(entity);
		System.out.println(id);
	}
}
