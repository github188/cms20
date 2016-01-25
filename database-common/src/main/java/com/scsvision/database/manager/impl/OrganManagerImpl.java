package com.scsvision.database.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.scsvision.cms.annotation.Cache;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.database.dao.OrganDAO;
import com.scsvision.database.dao.StandardNumberDAO;
import com.scsvision.database.dao.VOrganDAO;
import com.scsvision.database.entity.Organ;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.service.vo.OrganVO;

/**
 * @author sjt
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class OrganManagerImpl implements OrganManager {
	@EJB(beanName = "OrganDAOImpl")
	private OrganDAO organDAO;
	@EJB(beanName = "VOrganDAOImpl")
	private VOrganDAO vOrganDAO;
	@EJB(beanName = "StandardNumberDAOImpl")
	private StandardNumberDAO snDAO;

	@Override
	@Cache
	public Map<Long, Organ> getOrgans() {
		List<Organ> list = organDAO.list();
		Map<Long, Organ> map = new HashMap<Long, Organ>();
		for (Organ o : list) {
			map.put(o.getId(), o);
		}
		return map;
	}

	@Override
	@Cache
	public VirtualOrgan getUgRootOrgan(Long ugId) {
		return vOrganDAO.getUgRoot(ugId);
	}

	@Override
	@Cache
	public List<VirtualOrgan> listUgVirtualOrgan(Long id) {
		return vOrganDAO.listUgOrgan(id);
	}

	@Override
	@Cache
	public List<VirtualOrgan> listChildVirtualOrgan(Long id) {
		return vOrganDAO.listChildren(id);
	}

	@Override
	public List<VirtualOrgan> getvOrganList(Long userGroupId) {
		return vOrganDAO.getvOrganList(userGroupId);
	}

	@Override
	@Cache
	public VirtualOrgan getVOrgan(Long id) {
		return vOrganDAO.get(id);
	}

	@Override
	public List<Organ> listOrganlikePath(Long organId) {
		return organDAO.listOrganlikePath(organId);
	}

	@Override
	@Cache
	public List<VirtualOrgan> listUgRoad(Long ugId) {
		List<Integer> types = Arrays.asList(TypeDefinition.ORGAN_MOTORWAY,
				TypeDefinition.ORGAN_ROAD);
		return vOrganDAO.listUgOrganInTypes(ugId, types, null);
	}

	@Override
	public Element organTree(List<OrganVO> oDeviceList, OrganVO vorgan) {
		// 构建根节点
		Element node = DocumentHelper.createElement("Node");
		setNode(node, vorgan);
		if (oDeviceList.size() > 0) {
			genOrganTree(oDeviceList, vorgan, node);
		}
		return node;
	}

	public void genOrganTree(List<OrganVO> oDeviceList, OrganVO vorgan,
			Element node) {
		Element subNodes = DocumentHelper.createElement("SubNodes");
		Element tunnelList = DocumentHelper.createElement("TunnelList");
		Element bridgeList = DocumentHelper.createElement("BridgeList");
		for (OrganVO vo : oDeviceList) {
			if (vorgan.getId().equals(vo.getParentId())) {
				if (vo.getType().equals(TypeDefinition.ORGAN_TUNNEL)) {
					Element node1 = DocumentHelper.createElement("Tunnel");
					setNode(node1, vo);
					tunnelList.add(node1);
					genOrganTree(oDeviceList, vo, node1);
				} else if (vo.getType().equals(TypeDefinition.ORGAN_BRIDGE)) {
					Element node1 = DocumentHelper.createElement("Bridge");
					setNode(node1, vo);
					bridgeList.add(node1);
					genOrganTree(oDeviceList, vo, node1);
				} else {
					Element node1 = DocumentHelper.createElement("Node");
					setNode(node1, vo);
					subNodes.add(node1);
					genOrganTree(oDeviceList, vo, node1);
				}
			}
		}
		node.add(tunnelList);
		node.add(bridgeList);
		node.add(subNodes);
	}

	public void setNode(Element node, OrganVO vo) {
		node.addAttribute("Id", vo.getId().toString());
		node.addAttribute("Name", vo.getName() != null ? vo.getName() : "");
		node.addAttribute("Type", vo.getType() + "");
		node.addAttribute("Path", vo.getPath());
		node.addAttribute("BeginStake",
				vo.getBeginStake() != null ? vo.getBeginStake() : "");
		node.addAttribute("EndStake",
				vo.getEndStake() != null ? vo.getEndStake() : "");
		node.addAttribute("Length", vo.getLength() != null ? vo.getLength()
				.toString() : "");
		node.addAttribute("LaneNumber", vo.getLaneNumber() != null ? vo
				.getLaneNumber().toString() : "");
		node.addAttribute("EntranceNumber", vo.getEntranceNumber() != null ? vo
				.getEntranceNumber().toString() : "");
		node.addAttribute("ExitNumber", vo.getExitNumber() != null ? vo
				.getExitNumber().toString() : "");
		node.addAttribute("ParentName",
				vo.getParentName() != null ? vo.getParentName() : "");
	}

	@Override
	@Cache
	public List<VirtualOrgan> listUgRealOrgan(Long ugId) {
		List<Integer> types = Arrays.asList(TypeDefinition.ORGAN_BRIDGE,
				TypeDefinition.ORGAN_GENERAL, TypeDefinition.ORGAN_HIGHWAY,
				TypeDefinition.ORGAN_MOTORWAY, TypeDefinition.ORGAN_ROAD,
				TypeDefinition.ORGAN_TOLLGATE, TypeDefinition.ORGAN_TUNNEL);
		return vOrganDAO.listUgOrganInTypes(ugId, types, null);
	}

	@Override
	@Cache
	public List<VirtualOrgan> listRealChildOrgan(Long id) {
		List<Integer> types = Arrays.asList(TypeDefinition.ORGAN_BRIDGE,
				TypeDefinition.ORGAN_GENERAL, TypeDefinition.ORGAN_HIGHWAY,
				TypeDefinition.ORGAN_MOTORWAY, TypeDefinition.ORGAN_ROAD,
				TypeDefinition.ORGAN_TOLLGATE, TypeDefinition.ORGAN_TUNNEL);
		return vOrganDAO.listChildInTypes(id, types);
	}

	@Override
	@Cache
	public List<VirtualOrgan> listUgDevice(Long ugId) {
		return vOrganDAO.listUgDevice(ugId);
	}

	@Override
	public List<VirtualOrgan> listOrganChannel(Long id) {
		List<Integer> types = Arrays.asList(TypeDefinition.VIDEO_DVR,
				TypeDefinition.VIDEO_DVS, TypeDefinition.VIDEO_CAMERA_GUN,
				TypeDefinition.VIDEO_CAMERA_BALL,
				TypeDefinition.VIDEO_CAMERA_PLAT,
				TypeDefinition.VIDEO_CAMERA_HALFBALL);
		return vOrganDAO.listChildDeviceInTypes(id, types);
	}

	@Override
	@Cache
	public Map<Long, VirtualOrgan> mapVOrgan(List<Long> ids) {
		return vOrganDAO.map(ids);
	}

	@Override
	public List<VirtualOrgan> listUgAllOrgan(Long userGroupId) {
		List<Integer> types = Arrays.asList(TypeDefinition.ORGAN_BRIDGE,
				TypeDefinition.ORGAN_GENERAL, TypeDefinition.ORGAN_HIGHWAY,
				TypeDefinition.ORGAN_MOTORWAY, TypeDefinition.ORGAN_ROAD,
				TypeDefinition.ORGAN_TOLLGATE, TypeDefinition.ORGAN_TUNNEL,
				TypeDefinition.VIRTUAL_ORGAN);
		return vOrganDAO.listUgOrganInTypes(userGroupId, types,
				TypeDefinition.VORGAN_VISIBLE_TRUE);
	}

	@Override
	public List<VirtualOrgan> listUgAllVd(Long userGroupId) {
		List<Integer> types = Arrays.asList(
				TypeDefinition.VEHICLE_DETECTOR_MICRO,
				TypeDefinition.VEHICLE_DETECTOR_COIL,
				TypeDefinition.VEHICLE_DETECTOR_VIDEO);
		return vOrganDAO.listUgOrganInTypes(userGroupId, types,
				TypeDefinition.VORGAN_VISIBLE_TRUE);
	}

	@Override
	public List<VirtualOrgan> listUgAllVms(Long userGroupId) {
		List<Integer> types = Arrays.asList(TypeDefinition.VMS_DOOR,
				TypeDefinition.VMS_COLUMN, TypeDefinition.VMS_CANTILEVER);
		return vOrganDAO.listUgOrganInTypes(userGroupId, types,
				TypeDefinition.VORGAN_VISIBLE_TRUE);
	}

	@Override
	public List<VirtualOrgan> listVmsByOrgan(Long id) {
		List<Integer> types = Arrays.asList(TypeDefinition.VMS_DOOR,
				TypeDefinition.VMS_COLUMN, TypeDefinition.VMS_CANTILEVER);
		return vOrganDAO.listChildInTypes(id, types);
	}

	@Override
	public List<VirtualOrgan> listVOrgan(Long deviceId, Integer type) {
		return vOrganDAO.listVOrgan(deviceId, type);
	}

	@Override
	public Long createOrgan(Organ entity) {
		organDAO.save(entity);
		snDAO.sync(entity.getStandardNumber(), entity.getName(), Organ.class,
				entity.getType().toString(), false);
		return entity.getId();
	}

	@Override
	public Organ getOrganById(Long id) {
		return organDAO.get(id);
	}

	@Override
	public void updateOrgan(Organ entity) {
		organDAO.update(entity);
		snDAO.sync(entity.getStandardNumber(), entity.getName(), Organ.class,
				entity.getType().toString(), false);
	}

	@Override
	public void deleteOrgan(Long id) {
		Organ organ = organDAO.get(id);
		organDAO.delete(organ);
		snDAO.sync(organ.getStandardNumber(), organ.getName(), Organ.class,
				organ.getType().toString(), true);
	}

	@Override
	public List<Organ> listOrgan(Long organId, String name, int start, int limit) {
		return organDAO.listOrgan(organId, name, start, limit);
	}

	@Override
	public Integer countOrgan(Long organId, String name) {
		return organDAO.countOrgan(organId, name);
	}

	@Override
	public Long createNode(VirtualOrgan entity) {
		vOrganDAO.save(entity);
		return entity.getId();
	}

	@Override
	public void updateNode(VirtualOrgan entity) {
		vOrganDAO.update(entity);
	}

	@Override
	public void deleteNode(Long id) {
		VirtualOrgan entity = vOrganDAO.get(id);
		vOrganDAO.delete(entity);
	}

	@Override
	public Element TreeVirtualOrgan(List<VirtualOrgan> list,
			VirtualOrgan rootOrgan) {
		Map<Long, Element> parentNode = new HashMap<Long, Element>();
		Element parent = initNode(rootOrgan);
		parentNode.put(rootOrgan.getId(), parent);
		int deep = MyStringUtil.countCharInString(parent.getPath(), '/') + 1;
		Boolean flag = true;
		while (flag) {
			flag = false;
			for (VirtualOrgan vo : list) {
				if (deep == MyStringUtil.countCharInString(vo.getPath(), '/')) {
					Element child = initNode(vo);
					parentNode.get(vo.getParentId()).add(child);
					parentNode.put(vo.getId(), child);
					flag = true;
				}
			}
			deep++;
		}
		return parent;
	}

	public Element initNode(VirtualOrgan vo) {
		Element node = DocumentHelper.createElement("Node");
		node.addAttribute("Id", vo.getId().toString());
		node.addAttribute("Name",
				MyStringUtil.object2StringNotNull(vo.getName()));
		node.addAttribute("Type", vo.getType().toString());
		return node;
	}

	@Override
	@Cache
	public Map<Long, Organ> mapOrganById() {
		return organDAO.map(null);
	}

	@Override
	@Cache
	public List<VirtualOrgan> listAllOrgan(Long organId) {
		List<Integer> types = Arrays.asList(TypeDefinition.ORGAN_BRIDGE,
				TypeDefinition.ORGAN_GENERAL, TypeDefinition.ORGAN_HIGHWAY,
				TypeDefinition.ORGAN_MOTORWAY, TypeDefinition.ORGAN_ROAD,
				TypeDefinition.ORGAN_TOLLGATE, TypeDefinition.ORGAN_TUNNEL,
				TypeDefinition.VIRTUAL_ORGAN);
		return vOrganDAO.listChildInTypes(organId, types);
	}

	@Override
	public List<Long> listDeviceIds(Long id, List<Integer> list) {
		List<VirtualOrgan> vorgans = vOrganDAO.listChildDeviceInTypes(id, list);
		List<Long> ids = new ArrayList<Long>();
		for (VirtualOrgan vorgan : vorgans) {
			ids.add(vorgan.getDeviceId());
		}
		return ids;
	}

	@Override
	public Map<Long, Organ> mapOrgan(List<Long> ids) {
		return organDAO.map(ids);
	}

	@Override
	@Cache
	public Map<Long, VirtualOrgan> mapUgVirtualOrgan(Long id) {
		List<VirtualOrgan> list = vOrganDAO.listUgOrgan(id);
		Map<Long, VirtualOrgan> map = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan vo : list) {
			map.put(vo.getId(), vo);
		}
		return map;
	}

	@Override
	@Cache
	public Map<Long, VirtualOrgan> mapUgSvVirtualOrgan(Long id) {
		List<VirtualOrgan> list = vOrganDAO.listSvVirtualOrgan(id);
		Map<Long, VirtualOrgan> map = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan vo : list) {
			map.put(vo.getId(), vo);
		}
		return map;
	}

	@Override
	public List<Organ> listAllRealOrgan() {
		return organDAO.list();
	}

	@Override
	public List<VirtualOrgan> listUgAllCamera(Long userGroupId) {
		List<Integer> types = Arrays.asList(TypeDefinition.VIDEO_CAMERA_GUN,
				TypeDefinition.VIDEO_CAMERA_BALL,
				TypeDefinition.VIDEO_CAMERA_PLAT,
				TypeDefinition.VIDEO_CAMERA_HALFBALL);
		return vOrganDAO.listUgOrganInTypes(userGroupId, types,
				TypeDefinition.VORGAN_VISIBLE_TRUE);
	}
	
	@Override
	public List<VirtualOrgan> listRoad(Long userGroupId) {
		List<Integer> types = Arrays.asList(TypeDefinition.ORGAN_ROAD);
		return vOrganDAO.listUgOrganInTypes(userGroupId, types,
				TypeDefinition.VORGAN_VISIBLE_TRUE);
	}

	@Override
	public List<Organ> listOrganByIds(List<Long> ids) {
		return organDAO.listOrganByIds(ids);
	}

	@Override
	@Cache
	public Map<Long, VirtualOrgan> mapUgRealDevice(Long userGroupId) {
		List<VirtualOrgan> list = vOrganDAO.listUgDevice(userGroupId);
		Map<Long, VirtualOrgan> map = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan vo : list) {
			map.put(vo.getDeviceId(), vo);
		}
		return map;
	}
}
