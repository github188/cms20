package com.scsvision.cms.duty.controller;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dom4j.Document;
import org.dom4j.Element;

import com.scsvision.cms.duty.manager.VisitRecordManager;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.VisitRecord;

/**
 * VisitRecordController
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:51:02
 */
@Stateless
public class VisitRecordController {
	@EJB(beanName = "VisitRecordManagerImpl")
	private VisitRecordManager visitRecordManager;

}
