package com.scsvision.database.dao.impl;

import javax.ejb.Stateless;

import com.scsvision.database.dao.ErrorMsgDAO;
import com.scsvision.database.entity.ErrorMessage;

@Stateless
public class ErrorMsgDAOImpl extends BaseDAOImpl<ErrorMessage, Long> implements ErrorMsgDAO{

}
