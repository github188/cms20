package com.scsvision.cms.common.controller;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import com.scsvision.cms.common.dto.ServerDTO;
import com.scsvision.cms.constant.Header;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.util.number.NumberUtil;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.Server;
import com.scsvision.database.manager.ServerManager;

/**
 * @author sjt
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServerController {
	@EJB(beanName = "ServerManagerImpl")
	private ServerManager serverManager;

	public String serverConfig(String message) {
		RequestReader reader = new RequestReader(message);
		String sn = reader.getString("/Request/StandardNumber", false);
		String name = reader.getString("/Request/Name", true);
		Element network = reader.getElement("Network");
		String ip1 = reader.getAttribute(network, "IP1", true);
		String ip2 = reader.getAttribute(network, "IP2", true);
		String ip3 = reader.getAttribute(network, "IP3", true);
		String ip4 = reader.getAttribute(network, "IP4", true);
		String config = reader.getElement("Config").asXML();

		Server server = serverManager.getServerBySN(sn);
		if (StringUtils.isNotBlank(config)) {
			server.setConfigValue(config);
		}
		if (StringUtils.isNotBlank(ip1)) {
			server.setIp1(ip1);
		}
		if (StringUtils.isNotBlank(ip2)) {
			server.setIp2(ip2);
		}
		if (StringUtils.isNotBlank(ip3)) {
			server.setIp3(ip3);
		}
		if (StringUtils.isNotBlank(ip4)) {
			server.setIp4(ip4);
		}
		if (StringUtils.isNotBlank(name)) {
			server.setName(name);
		}
		serverManager.serverConfig(server);
		Element root = DocumentHelper.createElement("Response");
		root.addAttribute("Method", "ServerConfig");
		root.addAttribute("Code", "200");
		root.addAttribute("Message", "");
		Document doc = DocumentHelper.createDocument(root);
		return XmlUtil.xmlToString(doc);
	}

	public Object createServerJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		String name = reader.getString("name", false);
		Integer type = reader.getInteger("type", false);
		String ip1 = reader.getString("ip1", true);
		String ip2 = reader.getString("ip2", true);
		String ip3 = reader.getString("ip3", true);
		String ip4 = reader.getString("ip4", true);
		String config = reader.getString("configValue", true);
		Integer port = reader.getInteger("port", true);
		Server server = new Server();
		if (StringUtils.isBlank(sn)) {
			sn = NumberUtil.randomStandardNumber();
		}
		server.setStandardNumber(sn);
		server.setConfigValue(config);
		server.setIp1(ip1);
		server.setIp2(ip2);
		server.setIp3(ip3);
		server.setIp4(ip4);
		server.setName(name);
		server.setPort(port);
		server.setType(type);
		Long id = serverManager.createServer(server);
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(id + "::" + server.getStandardNumber());
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object updateServerJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);
		String sn = reader.getString("standardNumber", true);
		String name = reader.getString("name", true);
		Integer type = reader.getInteger("type", true);
		String ip1 = reader.getString("ip1", true);
		String ip2 = reader.getString("ip2", true);
		String ip3 = reader.getString("ip3", true);
		String ip4 = reader.getString("ip4", true);
		String config = reader.getString("configValue", true);
		Integer port = reader.getInteger("port", true);
		Server server = serverManager.getServerById(id);
		if (StringUtils.isNotBlank(sn)) {
			server.setStandardNumber(sn);
		}
		if (StringUtils.isNotBlank(config)) {
			server.setConfigValue(config);
		}
		if (StringUtils.isNotBlank(name)) {
			server.setName(name);
		}
		if (StringUtils.isNotBlank(ip1)) {
			server.setIp1(ip1);
		}
		if (StringUtils.isNotBlank(ip2)) {
			server.setIp2(ip2);
		}
		if (StringUtils.isNotBlank(ip3)) {
			server.setIp3(ip3);
		}
		if (StringUtils.isNotBlank(ip4)) {
			server.setIp4(ip4);
		}
		if (null != port) {
			server.setPort(port);
		}
		if (null != type) {
			server.setType(type);
		}
		serverManager.updateServer(server);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteServerJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONArray array = new JSONArray(json);
		List<Long> ids = new LinkedList<Long>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			ids.add(obj.getLong("id"));
		}
		for (Long id : ids) {
			serverManager.deleteServer(id);
		}
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object listServerJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", true);
		Integer type = reader.getInteger("type", true);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}
		List<Server> list = serverManager.listServer(name, type, limit, start);
		Integer count = serverManager.countServers(name, type);
		ServerDTO dto = new ServerDTO();
		dto.setMessage(count + "");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<Server> dtoList = new LinkedList<Server>();
		for (Server o : list) {
			Server server = new Server();
			server.setStandardNumber(o.getStandardNumber());
			server.setConfigValue(o.getConfigValue());
			server.setIp1(o.getIp1());
			server.setIp2(o.getIp2());
			server.setIp3(o.getIp3());
			server.setIp4(o.getIp4());
			server.setName(o.getName());
			server.setPort(o.getPort());
			server.setType(o.getType());
			server.setId(o.getId());
			dtoList.add(server);
		}
		dto.setList(dtoList);
		return dto;
	}

}
