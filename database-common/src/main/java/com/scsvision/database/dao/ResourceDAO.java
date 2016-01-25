/**
 * 
 */
package com.scsvision.database.dao;

import javax.ejb.Local;

import com.scsvision.database.entity.Resource;

/**
 * @author wangbinyu
 *
 */
@Local
public interface ResourceDAO extends BaseDAO<Resource, Long> {

}
