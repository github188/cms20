/**
 * 
 */
package com.scsvision.cms.maintain.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.maintain.dto.ListCmsLogDTO;
import com.scsvision.cms.maintain.dto.ListCmsLogInfoDTO;
import com.scsvision.cms.maintain.manager.LogManager;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.TmCmsPublishLog;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class LogController {
	@EJB(beanName = "LogManagerImpl")
	private LogManager logManager;

	public String saveUserLog(String messageXml, String businessId)
			throws DocumentException {
		RequestReader reader = new RequestReader(messageXml);
		Element logElement = reader.getElement("Log");
		Element contentElement = reader.getElement("Content");
		String ticketId = reader.getAttribute(logElement, "Ticket", false);
		String method = reader.getAttribute(logElement, "Method", false);
		String code = reader.getAttribute(logElement, "Code", false);
		String message = reader.getAttribute(logElement, "Message", true);
		String targetId = reader.getAttribute(logElement, "TargetId", false);
		String targetName = reader
				.getAttribute(logElement, "TargetName", false);
		String targetType = reader
				.getAttribute(logElement, "TargetType", false);
		String resourceId = reader.getAttribute(logElement, "ResourceId", true);
		String conditions = reader.getAttribute(logElement, "Conditions", true);
		String reason = reader.getAttribute(logElement, "Reason", true);
		// 发布情报办时用到
		String flag = reader.getAttribute(logElement, "Flag", true);
		String content = contentElement.asXML();

		String id = logManager.saveUserLog(ticketId, method, code, message,
				targetId, targetName, targetType, businessId, resourceId,
				conditions, reason, content, flag);

		Element root = DocumentHelper.createElement("Response");
		root.addAttribute("Method", "SaveUserLog");
		root.addAttribute("Code", "200");
		root.addAttribute("Message", id);
		Document doc = DocumentHelper.createDocument(root);
		return XmlUtil.xmlToString(doc);
	}

	public Object updateUserLog(String messageXml, String businessId) {
		RequestReader reader = new RequestReader(messageXml);
		Short flag = reader.getShort("Request/Flag", false);
		logManager.updateUserLog(businessId, flag);
		Element root = DocumentHelper.createElement("Response");
		root.addAttribute("Method", "UpdateUserLog");
		root.addAttribute("Code", "200");
		root.addAttribute("Message", "");
		Document doc = DocumentHelper.createDocument(root);
		return XmlUtil.xmlToString(doc);
	}

	public Object listCmsLogJson(HttpServletRequest request)
			throws DocumentException {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		// 默认为1周
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}

		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		String cmsName = reader.getString("name", true);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Integer count = logManager.countCmsLog(cmsName, beginTime, endTime);
		List<TmCmsPublishLog> logs = logManager.listCmsLog(beginTime, endTime,
				cmsName, start, limit);

		ListCmsLogDTO dto = new ListCmsLogDTO();
		List<ListCmsLogDTO.CmsLogVO> list = new ArrayList<ListCmsLogDTO.CmsLogVO>();
		for (TmCmsPublishLog log : logs) {
			// 解析xml
			Document document = DocumentHelper.parseText(log.getContent());
			Element item = document.getRootElement();
			ListCmsLogDTO.CmsLogVO vo = dto.new CmsLogVO();
			vo.setSendTime(sdf.format(new Date(log.getSendTime())));
			vo.setName(log.getCmsName());
			vo.setText(item.attributeValue("Text"));
			vo.setFlag(log.getFlag() + "");
			vo.setStype(item.attributeValue("Width") + "*"
					+ item.attributeValue("Height"));
			list.add(vo);
		}
		dto.setList(list);
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(count + "");
		return dto;
	}

	public Object listCmsInfoJson(HttpServletRequest request) throws DocumentException {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject jsonObj = new JSONObject(json);
		JSONArray array = jsonObj.getJSONArray("businessIds");
		List<String> businessIds = new ArrayList<String>();
		for (int i = 0; i < array.length(); i++) {
			businessIds.add(array.get(i).toString());
		}
		List<TmCmsPublishLog> logs = logManager.listCmsInfo(businessIds);
		ListCmsLogInfoDTO dto = new ListCmsLogInfoDTO();
		List<ListCmsLogInfoDTO.CmsLogInfo> list = new ArrayList<ListCmsLogInfoDTO.CmsLogInfo>();
		for (TmCmsPublishLog log : logs) {
			// 解析xml
			Document document = DocumentHelper.parseText(log.getContent());
			Element item = document.getRootElement();
			ListCmsLogInfoDTO.CmsLogInfo vo = dto.new CmsLogInfo();
			vo.setSendTime(new Timestamp(log.getSendTime()).toString());
			vo.setName(log.getCmsName());
			vo.setText(item.attributeValue("Text"));
			vo.setFlag(log.getFlag() + "");
			vo.setPublishType(item.attributeValue("PublishType"));
			list.add(vo);
		}
		dto.setList(list);
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}
}
