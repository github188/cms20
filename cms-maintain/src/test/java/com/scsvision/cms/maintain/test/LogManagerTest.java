package com.scsvision.cms.maintain.test;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.openejb.api.LocalClient;

import com.scsvision.cms.maintain.dao.UserLogDAO;

/**
 * LogManagerTest
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午11:51:02
 */
@Stateless
@LocalClient
public class LogManagerTest extends BaseMaintainTest {
	@EJB(beanName = "UserLogDAOImpl")
	private UserLogDAO userLogDAO;

	public void testUpdateOldRerecord() {
		userLogDAO.updateOldRerecord(2l, "PublishCms");
	}
}
