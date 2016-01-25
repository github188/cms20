package com.scsvision.cms.common.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.center.jms.util.amq.AmqUtil;
import com.scsvision.cms.common.dto.OrganDTO;
import com.scsvision.cms.common.dto.TreeRealOrganDTO;
import com.scsvision.cms.common.dto.TreeRealOrganDTO.TreeRealOrganVO;
import com.scsvision.cms.common.dto.TreeVirtualOrganDTO;
import com.scsvision.cms.constant.Header;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.util.file.Configuration;
import com.scsvision.cms.util.number.NumberUtil;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.SvDeviceManager;
import com.scsvision.database.manager.TmDeviceManager;
import com.scsvision.database.manager.UserManager;

/**
 * OrganController
 * 
 * @author sjt
 * 
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrganController {
	private final Logger logger = LoggerFactory
			.getLogger(OrganController.class);

	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;
	@EJB(beanName = "UserManagerImpl")
	private UserManager userManager;
	@EJB(beanName = "AmqUtil")
	private AmqUtil amqUtil;
	@EJB(beanName = "TmDeviceManagerImpl")
	private TmDeviceManager tmDeviceManager;
	@EJB(beanName = "SvDeviceManagerImpl")
	private SvDeviceManager svDeviceManager;

	public Object createOrganJson(HttpServletRequest request)
			throws UnsupportedEncodingException {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String standardNumber = reader.getString("standardNumber", true);
		String name = reader.getString("name", false);
		String type = reader.getString("type", false);
		String length = reader.getString("length", true);
		String parentId = reader.getString("parentId", true);
		String beginStake = reader.getString("beginStake", true);
		String endStake = reader.getString("endStake", true);
		String longitude = reader.getString("longitude", true);
		String latitude = reader.getString("latitude", true);
		String laneNumber = reader.getString("laneNumber", true);
		String entranceNumber = reader.getString("entranceNumber", true);
		String exitNumber = reader.getString("exitNumber", true);
		String roadNumber = reader.getString("roadNumber", true);
		Organ organ = new Organ();
		organ.setBeginStake(beginStake);
		organ.setEndStake(endStake);
		organ.setEntranceNumber(StringUtils.isNotBlank(entranceNumber) ? Integer
				.valueOf(entranceNumber) : null);
		organ.setExitNumber(StringUtils.isNotBlank(exitNumber) ? Integer
				.valueOf(exitNumber) : null);
		organ.setLaneNumber(StringUtils.isNotBlank(laneNumber) ? Integer
				.valueOf(laneNumber) : null);
		organ.setLatitude(latitude);
		organ.setLongitude(longitude);
		if (StringUtils.isNotBlank(length)) {
			organ.setLength(Float.valueOf(length));
		}
		organ.setName(name);
		organ.setParentId(StringUtils.isNotBlank(parentId) ? Long
				.valueOf(parentId) : null);
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = NumberUtil.randomStandardNumber();
		}
		organ.setStandardNumber(standardNumber);
		organ.setType(StringUtils.isNotBlank(type) ? Integer.valueOf(type)
				: null);
		organ.setRoadNumber(roadNumber);
		Long id = organManager.createOrgan(organ);
		String path = null;
		if (null != parentId) {
			Organ entity = organManager.getOrganById(Long.valueOf(parentId));
			path = entity.getPath() + "/" + id.toString();
		} else {
			path = "/" + id.toString();
		}
		organ.setPath(path);
		organManager.updateOrgan(organ);
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage(id + "::" + organ.getStandardNumber());
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object updateOrganJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		String standardNumber = reader.getString("standardNumber", true);
		String name = reader.getString("name", false);
		String type = reader.getString("type", false);
		String length = reader.getString("length", true);
		String parentId = reader.getString("parentId", true);
		String beginStake = reader.getString("beginStake", true);
		String endStake = reader.getString("endStake", true);
		String longitude = reader.getString("longitude", true);
		String latitude = reader.getString("latitude", true);
		String laneNumber = reader.getString("laneNumber", true);
		String entranceNumber = reader.getString("entranceNumber", true);
		String exitNumber = reader.getString("exitNumber", true);
		String path = reader.getString("path", true);
		String roadNumber = reader.getString("roadNumber", true);
		Organ organ = organManager.getOrganById(Long.valueOf(id));
		if (StringUtils.isNotBlank(name)) {
			organ.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			organ.setStandardNumber(standardNumber);
		}
		if (StringUtils.isNotBlank(length)) {
			organ.setLength(Float.valueOf(length));
		}
		if (StringUtils.isNotBlank(beginStake)) {
			organ.setBeginStake(beginStake);
		}
		if (StringUtils.isNotBlank(endStake)) {
			organ.setEndStake(endStake);
		}
		if (StringUtils.isNotBlank(longitude)) {
			organ.setLongitude(longitude);
		}
		if (StringUtils.isNotBlank(latitude)) {
			organ.setLatitude(latitude);
		}
		if (StringUtils.isNotBlank(path)) {
			organ.setPath(path);
		}
		if (StringUtils.isNotBlank(type)) {
			organ.setType(Integer.valueOf(type));
		}
		if (StringUtils.isNotBlank(parentId)) {
			organ.setParentId(Long.valueOf(parentId));
		}
		if (StringUtils.isNotBlank(laneNumber)) {
			organ.setLaneNumber(Integer.valueOf(laneNumber));
		}
		if (StringUtils.isNotBlank(entranceNumber)) {
			organ.setEntranceNumber(Integer.valueOf(entranceNumber));
		}
		if (StringUtils.isNotBlank(exitNumber)) {
			organ.setExitNumber(Integer.valueOf(exitNumber));
		}
		if (StringUtils.isNotBlank(roadNumber)) {
			organ.setRoadNumber(roadNumber);
		}
		organManager.updateOrgan(organ);
		BaseDTO dto = new BaseDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteOrganJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("id", false);
		List<Organ> list = organManager.listOrganlikePath(organId);
		if (list.size() > 1) {
			throw new BusinessException(ErrorCode.CHILDREN_EXIST, "Organ["
					+ organId + "] has children and can't be deleted !");
		}
		organManager.deleteOrgan(organId);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object listOrganJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long organId = reader.getLong("organId", true);
		String name = reader.getString("name", true);
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}
		List<Organ> list = organManager.listOrgan(organId, name, start, limit);
		Integer count = organManager.countOrgan(organId, name);
		OrganDTO dto = new OrganDTO();
		dto.setMessage(count + "");
		dto.setMethod(request.getHeader(Header.METHOD));
		List<OrganDTO.OrganDto> olist = new LinkedList<OrganDTO.OrganDto>();
		for (Organ o : list) {
			OrganDTO.OrganDto organDto = dto.new OrganDto();
			organDto.setBeginStake(o.getBeginStake());
			organDto.setEndStake(o.getEndStake());
			organDto.setEntranceNumber(o.getEntranceNumber());
			organDto.setExitNumber(o.getExitNumber());
			organDto.setId(o.getId());
			organDto.setLaneNumber(o.getLaneNumber());
			organDto.setLatitude(o.getLatitude());
			organDto.setLength(o.getLength());
			organDto.setName(o.getName());
			organDto.setParentId(o.getParentId());
			organDto.setPath(o.getPath());
			organDto.setStandardNumber(o.getStandardNumber());
			organDto.setType(o.getType());
			organDto.setParentId(o.getParentId());
			organDto.setRoadNumber(o.getRoadNumber());
			olist.add(organDto);
		}
		dto.setList(olist);
		return dto;
	}

	public Object addRealNodeJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer type = reader.getInteger("type", false);
		Long parentId = reader.getLong("parentId", true);
		Long userGroupId = reader.getLong("userGroupId", false);
		Long deviceId = reader.getLong("realId", false);
		String name = reader.getString("name", true);
		VirtualOrgan vorgan = new VirtualOrgan();
		vorgan.setType(type);
		vorgan.setUserGroupId(userGroupId);
		vorgan.setParentId(parentId);
		vorgan.setDeviceId(deviceId);
		vorgan.setName(name);
		Long id = organManager.createNode(vorgan);
		if (null == parentId) {
			vorgan.setPath("/" + id);
		} else {
			VirtualOrgan parentOrgan = organManager.getVOrgan(parentId);
			vorgan.setPath(parentOrgan.getPath() + "/" + id);
		}
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(vorgan.getId() + "");
		return dto;
	}

	public Object createNodeJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		Integer type = reader.getInteger("type", true);
		Long parentId = reader.getLong("parentId", true);
		Long userGroupId = reader.getLong("userGroupId", false);
		VirtualOrgan vorgan = new VirtualOrgan();
		vorgan.setName(name);
		if (null == type) {
			vorgan.setType(TypeDefinition.VIRTUAL_ORGAN);
		}
		vorgan.setUserGroupId(userGroupId);
		vorgan.setParentId(parentId);
		Long id = organManager.createNode(vorgan);
		if (null == parentId) {
			vorgan.setPath("/" + id);
		} else {
			VirtualOrgan parentOrgan = organManager.getVOrgan(parentId);
			vorgan.setPath(parentOrgan.getPath() + "/" + id);
		}
		organManager.updateNode(vorgan);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		dto.setMessage(vorgan.getId() + "");
		return dto;
	}

	public Object updateNodeJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", true);
		Long parentId = reader.getLong("parentId", true);
		Long id = reader.getLong("id", false);
		VirtualOrgan vorgan = organManager.getVOrgan(id);
		if (StringUtils.isNotBlank(name)) {
			vorgan.setName(name);
		}
		if (null != parentId) {
			vorgan.setParentId(parentId);
			VirtualOrgan parentOrgan = organManager.getVOrgan(parentId);
			vorgan.setPath(parentOrgan.getPath() + "/" + id);
		}
		organManager.updateNode(vorgan);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public Object deleteNodeJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String json = reader.getString("json", false);
		JSONObject obj = new JSONObject(json);
		JSONArray ids = obj.getJSONArray("ids");
		for (int i = 0; i < ids.length(); i++) {
			organManager.deleteNode(ids.getLong(i));
		}
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		return dto;
	}

	public String updateNode(String message) {
		RequestReader reader = new RequestReader(message);
		String name = reader.getString("Request/Name", true);
		Long parentId = reader.getLong("Request/ParentId", true);
		Long id = reader.getLong("Request/Id", false);
		VirtualOrgan vorgan = organManager.getVOrgan(id);
		if (StringUtils.isNotBlank(name)) {
			vorgan.setName(name);
		}
		if (null != parentId) {
			vorgan.setParentId(parentId);
			VirtualOrgan parentOrgan = organManager.getVOrgan(parentId);
			vorgan.setPath(parentOrgan.getPath() + "/" + id);
		}
		organManager.updateNode(vorgan);
		Document doc = XmlUtil.generateResponse("UpdateNode",
				ErrorCode.SUCCESS, "");
		return XmlUtil.xmlToString(doc);
	}

	public String deleteNode(String message) {
		RequestReader reader = new RequestReader(message);
		Long id = reader.getLong("Request/Id", false);
		organManager.deleteNode(id);
		Document doc = XmlUtil.generateResponse("DeleteNode",
				ErrorCode.SUCCESS, "");
		return XmlUtil.xmlToString(doc);
	}

	public String treeVirtualOrgan(String message) {
		RequestReader reader = new RequestReader(message);
		Long userGroupId = reader.getLong("Request/UserGroupId", false);
		List<VirtualOrgan> list = organManager.getvOrganList(userGroupId);
		VirtualOrgan rootOrgan = organManager.getUgRootOrgan(userGroupId);
		Element node = organManager.TreeVirtualOrgan(list, rootOrgan);
		Element root = DocumentHelper.createElement("Response");
		root.addAttribute("Method", "TreeVirtualOrgan");
		root.addAttribute("Code", ErrorCode.SUCCESS);
		root.addAttribute("Message", "");
		Document doc = DocumentHelper.createDocument(root);
		root.add(node);
		return XmlUtil.xmlToString(doc);
	}

	public Object treeVirtualOrganJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long userGroupId = reader.getLong("userGroupId", false);
		List<VirtualOrgan> list = organManager.getvOrganList(userGroupId);
		TreeVirtualOrganDTO dto = new TreeVirtualOrganDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		List<TreeVirtualOrganDTO.Children> cList = new LinkedList<TreeVirtualOrganDTO.Children>();
		for (VirtualOrgan vo : list) {
			TreeVirtualOrganDTO.Children child = dto.new Children();
			child.setId(vo.getId() + "");
			child.setName(vo.getName());
			if (null != vo.getParentId()) {
				child.setPid(vo.getParentId() + "");
			} else {
				child.setPid(0 + "");
			}
			child.setType(vo.getType() + "");
			cList.add(child);
		}
		dto.setChildren(cList);
		return dto;
	}

	public Object treeRealOrgan(HttpServletRequest request) {
		List<Organ> organs = organManager.listAllRealOrgan();
		TreeRealOrganDTO dto = new TreeRealOrganDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMessage("");
		dto.setMethod("ListSvOrganTree");
		List<TreeRealOrganVO> list = new ArrayList<TreeRealOrganVO>();
		for (Organ organ : organs) {
			TreeRealOrganDTO.TreeRealOrganVO vo = dto.new TreeRealOrganVO();
			vo.setId(organ.getId() + "");
			vo.setName(organ.getName());
			if (null == organ.getParentId()) {
				vo.setPid("0");
			} else {
				vo.setPid(organ.getParentId() + "");
			}
			vo.setType(organ.getType() + "");
			vo.setStandardNumber(organ.getStandardNumber());
			list.add(vo);
		}
		dto.setChildren(list);
		return dto;
	}
}
