/**
 * 
 */
package com.scsvision.database.dao.impl;

import javax.ejb.Stateless;

import com.scsvision.database.dao.ResourceDAO;
import com.scsvision.database.entity.Resource;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class ResourceDAOImpl extends BaseDAOImpl<Resource, Long> implements
		ResourceDAO {

}
