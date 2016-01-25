package com.scsvision.cms.em.dao.impl;

import javax.ejb.Stateless;

import com.scsvision.cms.em.dao.EventManualDAO;
import com.scsvision.database.entity.EventManual;

@Stateless
public class EventManualDAOImpl extends EmBaseDAOImpl<EventManual, Long>
		implements EventManualDAO {

}
