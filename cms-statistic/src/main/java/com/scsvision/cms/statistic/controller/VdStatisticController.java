package com.scsvision.cms.statistic.controller;


import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.scsvision.cms.statistic.manager.VdStatisticManager;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.UserManager;

/**
 * VdStatisticController
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:09:48
 */
@Stateless
public class VdStatisticController {
	@EJB(beanName = "VdStatisticManagerImpl")
	private VdStatisticManager vdStatisticManager;
	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;
	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;

}
