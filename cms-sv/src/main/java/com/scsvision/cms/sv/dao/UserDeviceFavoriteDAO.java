/**
 * 
 */
package com.scsvision.cms.sv.dao;

import javax.ejb.Local;

import com.scsvision.database.entity.UserDeviceFavorite;

/**
 * UserDeviceFavoriteDAO
 * 
 * @author sjt
 *         <p />
 *         Create at 2016年 下午15:35:37
 */
@Local
public interface UserDeviceFavoriteDAO extends
		SvBaseDAO<UserDeviceFavorite, Long> {
}
