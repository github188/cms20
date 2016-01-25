/**
 * 
 */
package com.scsvision.cms.tm.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.tm.dto.GetMapCenterDTO;
import com.scsvision.cms.tm.manager.TmMapCenterManager;
import com.scsvision.cms.tm.service.vo.TmMapCenterVO;
import com.scsvision.cms.util.annotation.Logon;
import com.scsvision.cms.util.interceptor.SessionCheckInterceptor;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.xml.XmlUtil;

/**
 * @author wangbinyu
 *
 */
@Stateless
@Interceptors(SessionCheckInterceptor.class)
public class TmMapController {

	@EJB(beanName = "TmMapCenterManagerImpl")
	private TmMapCenterManager tmMapCenterManager;

	@Logon
	public Object saveMapCenterJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String longitude = reader.getString("longitude", true);
		String latitude = reader.getString("latitude", true);
		Integer mapLevel = reader.getInteger("mapLevel", true);
		Long userId = reader.getUserId();
		tmMapCenterManager.saveMapCenter(longitude, userId, latitude, mapLevel);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	@Logon
	public Object getMapCenterJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userId = reader.getUserId();
		TmMapCenterVO vo = tmMapCenterManager.getMapCenter(userId);
		GetMapCenterDTO dto = new GetMapCenterDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod("GetMapCenter");
		dto.setMessage("");
		dto.setCenterDTO(vo);
		return dto;
	}
}
