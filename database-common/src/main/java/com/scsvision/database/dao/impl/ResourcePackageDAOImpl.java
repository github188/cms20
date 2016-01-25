package com.scsvision.database.dao.impl;

import javax.ejb.Stateless;

import com.scsvision.database.dao.ResourcePackageDAO;
import com.scsvision.database.entity.ResourcePackage;

@Stateless
public class ResourcePackageDAOImpl extends BaseDAOImpl<ResourcePackage, Long>
		implements ResourcePackageDAO {

}
