package com.scsvision.database.dao;

import javax.ejb.Local;

import com.scsvision.database.entity.ErrorMessage;

/**
 * 
 * @author liangkun
 *
 */
@Local
public interface ErrorMsgDAO extends BaseDAO<ErrorMessage, Long> {

}
