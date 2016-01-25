package com.scsvision.database.dao.impl;

import javax.ejb.Stateless;

import com.scsvision.database.dao.ResourceImageDAO;
import com.scsvision.database.entity.ResourceImage;

@Stateless
public class ResourceImageDAOImpl extends BaseDAOImpl<ResourceImage, Long>
		implements ResourceImageDAO {
}
