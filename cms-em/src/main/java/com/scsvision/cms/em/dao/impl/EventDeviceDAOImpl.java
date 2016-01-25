package com.scsvision.cms.em.dao.impl;

import javax.ejb.Stateless;

import com.scsvision.cms.em.dao.EventDeviceDAO;
import com.scsvision.database.entity.EventDevice;

@Stateless
public class EventDeviceDAOImpl extends EmBaseDAOImpl<EventDevice, Long>
		implements EventDeviceDAO {

}
