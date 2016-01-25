/**
 * 
 */
package com.scsvision.cms.em.manager.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.em.dao.EventDAO;
import com.scsvision.cms.em.dao.EventRealDAO;
import com.scsvision.cms.em.manager.ResourceManager;
import com.scsvision.cms.em.service.vo.ResourceVO;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.interceptor.CacheInterceptor;
import com.scsvision.database.dao.EventResourceDAO;
import com.scsvision.database.dao.ResourceAudioDAO;
import com.scsvision.database.dao.ResourceDAO;
import com.scsvision.database.dao.ResourceImageDAO;
import com.scsvision.database.dao.ResourcePackageDAO;
import com.scsvision.database.dao.ResourceVideoDAO;
import com.scsvision.database.entity.Event;
import com.scsvision.database.entity.EventReal;
import com.scsvision.database.entity.EventResource;
import com.scsvision.database.entity.Resource;
import com.scsvision.database.entity.ResourceAudio;
import com.scsvision.database.entity.ResourceImage;
import com.scsvision.database.entity.ResourcePackage;
import com.scsvision.database.entity.ResourceVideo;

/**
 * @author wangbinyu
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Interceptors(CacheInterceptor.class)
public class ResourceManagerImpl implements ResourceManager {

	@EJB(beanName = "ResourceDAOImpl")
	private ResourceDAO resourceDAO;

	@EJB(beanName = "ResourceImageDAOImpl")
	private ResourceImageDAO resourceImageDAO;

	@EJB(beanName = "ResourceVideoDAOImpl")
	private ResourceVideoDAO resourceVideoDAO;

	@EJB(beanName = "ResourceAudioDAOImpl")
	private ResourceAudioDAO resourceAudioDAO;

	@EJB(beanName = "ResourcePackageDAOImpl")
	private ResourcePackageDAO resourcePackageDAO;

	@EJB(beanName = "EventResourceDAOImpl")
	private EventResourceDAO eventResourceDAO;

	@EJB(beanName = "EventDAOImpl")
	private EventDAO eventDAO;

	@EJB(beanName = "EventRealDAOImpl")
	private EventRealDAO eventRealDAO;

	@Override
	public void saveResource(String eventId, List<ResourceVO> vos) {
		Long eventIdl = Long.parseLong(eventId);
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("eventId", eventIdl);
		List<EventResource> ers = eventResourceDAO.findByPropertys(params);
		params.clear();

		// 删除操作
		for (EventResource er : ers) {
			Resource resource = resourceDAO.get(er.getResourceId());
			if (null != resource) {
				// 图片资源
				if (resource.getType() == TypeDefinition.RESOURCE_IMAGE) {
					resourceImageDAO.deleteById(resource.getId());
				}
				// 视频资源
				else if (resource.getType() == TypeDefinition.RESOURCE_VIDEO) {

				}
				// 语音资源
				else if (resource.getType() == TypeDefinition.RESOURCE_AUDIO) {

				}
				// 升级包资源
				else if (resource.getType() == TypeDefinition.RESOURCE_PACKAGE) {

				}
				// 其他
				else {

				}
				// 删除主表resource
				resourceDAO.delete(resource);
			}
			// 删除关联表
			eventResourceDAO.delete(er);
		}

		// 新增操作
		for (ResourceVO vo : vos) {
			// 资源主表
			Resource resource = new Resource();
			resource.setType(Integer.parseInt(vo.getType()));
			resourceDAO.save(resource);
			Long id = resource.getId();

			// 图片资源
			if (Integer.parseInt(vo.getType()) == TypeDefinition.RESOURCE_IMAGE) {
				ResourceImage resourceImage = new ResourceImage();
				resourceImage.setAddress(vo.getAddress());
				resourceImage.setCreateTime(System.currentTimeMillis());
				resourceImage.setId(id);
				String address[] = vo.getAddress().split("/");
				String name = address[address.length - 1];
				resourceImage.setName(name);
				resourceImageDAO.save(resourceImage);
			}
			// 视频资源
			else if (Integer.parseInt(vo.getType()) == TypeDefinition.RESOURCE_VIDEO) {

			}
			// 语音资源
			else if (Integer.parseInt(vo.getType()) == TypeDefinition.RESOURCE_AUDIO) {

			}
			// 升级包资源
			else if (Integer.parseInt(vo.getType()) == TypeDefinition.RESOURCE_PACKAGE) {

			}
			// 其他
			else {

			}

			// 事件资源关系表
			EventResource eResource = new EventResource();
			eResource.setEventId(Long.parseLong(eventId));
			eResource.setResourceId(id);
			eventResourceDAO.save(eResource);
		}

		// 更新事件资源标志
		Event event = eventDAO.get(eventIdl);
		if (null != event) {
			event.setResourceFlag(vos.size() > 0 ? TypeDefinition.EVENT_RESOURCE_FLAG_TRUE
					: TypeDefinition.EVENT_RESOURCE_FLAG_FALSE);
			eventDAO.update(event);
		}

		EventReal eventReal = eventRealDAO.get(eventIdl);
		if (null != eventReal) {
			eventReal
					.setResourceFlag(vos.size() > 0 ? TypeDefinition.EVENT_RESOURCE_FLAG_TRUE
							: TypeDefinition.EVENT_RESOURCE_FLAG_FALSE);
			eventRealDAO.update(eventReal);
		}
	}

	@Override
	public void deleteResource(Long id) {
		eventResourceDAO.deleteByEventId(id);
	}

	@Override
	public void saveResource1(String eventId, ResourceVO vo) {
		Long eventIdl = Long.parseLong(eventId);
		// 资源主表
		Resource resource = new Resource();
		resource.setType(Integer.parseInt(vo.getType()));
		resourceDAO.save(resource);
		Long id = resource.getId();

		// 图片资源
		if (Integer.parseInt(vo.getType()) == TypeDefinition.RESOURCE_IMAGE) {
			ResourceImage resourceImage = new ResourceImage();
			resourceImage.setAddress(vo.getAddress());
			resourceImage.setCreateTime(System.currentTimeMillis());
			resourceImage.setId(id);
			String address[] = vo.getAddress().split("/");
			String name = address[address.length - 1];
			resourceImage.setName(name);
			resourceImageDAO.save(resourceImage);
		}
		// 视频资源
		else if (Integer.parseInt(vo.getType()) == TypeDefinition.RESOURCE_VIDEO) {

		}
		// 语音资源
		else if (Integer.parseInt(vo.getType()) == TypeDefinition.RESOURCE_AUDIO) {

		}
		// 升级包资源
		else if (Integer.parseInt(vo.getType()) == TypeDefinition.RESOURCE_PACKAGE) {

		}
		// 其他
		else {

		}

		// 事件资源关系表
		EventResource eResource = new EventResource();
		eResource.setEventId(Long.parseLong(eventId));
		eResource.setResourceId(id);
		eventResourceDAO.save(eResource);

		// 更新事件资源标志
		Event event = eventDAO.get(eventIdl);
		if (null != event) {
			if (event.getResourceFlag().equals(
					TypeDefinition.EVENT_RESOURCE_FLAG_FALSE)) {
				event.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_TRUE);
				eventDAO.update(event);
			}
		}

		EventReal eventReal = eventRealDAO.get(eventIdl);
		if (null != eventReal) {
			if (eventReal.getResourceFlag().equals(
					TypeDefinition.EVENT_RESOURCE_FLAG_FALSE)) {
				eventReal
						.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_TRUE);
				eventRealDAO.update(eventReal);
			}
		}
	}

	@Override
	public void deleteResource(Long eventId, String address) {
		// 查询图片资源表 暂时只支持图片资源
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("address", address);
		List<ResourceImage> list = resourceImageDAO.findByPropertys(params);
		params.clear();
		if (list.size() < 0) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"ResourceImage Address:" + address + " not found");
		} else if (list.size() > 1) {
			throw new BusinessException(ErrorCode.IMAGE_ADDRESS_REPEAT,
					"ResourceImage Address:" + address + " is repeat");
		}
		Long id = list.get(0).getId();

		// 删除事件资源关联表
		eventResourceDAO.deleteResource(eventId, id);
		// 更新事件资源标志
		params.put("eventId", eventId);
		List<EventResource> ers = eventResourceDAO.findByPropertys(params);
		if (ers.size() <= 0) {
			// 更新事件资源标志
			Event event = eventDAO.get(eventId);
			if (null != event) {
				if (event.getResourceFlag().equals(
						TypeDefinition.EVENT_RESOURCE_FLAG_TRUE)) {
					event.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_FALSE);
					eventDAO.update(event);
				}
			}

			EventReal eventReal = eventRealDAO.get(eventId);
			if (null != eventReal) {
				if (eventReal.getResourceFlag().equals(
						TypeDefinition.EVENT_RESOURCE_FLAG_TRUE)) {
					eventReal
							.setResourceFlag(TypeDefinition.EVENT_RESOURCE_FLAG_FALSE);
					eventRealDAO.update(eventReal);
				}
			}
		}

		Resource resource = resourceDAO.get(id);
		Integer type = resource.getType();
		// 删除图片资源
		if (type.equals(TypeDefinition.RESOURCE_IMAGE)) {
			resourceImageDAO.deleteById(id);
		}
		// 删除视频资源
		else if (type.equals(TypeDefinition.RESOURCE_VIDEO)) {

		}
		// 删除音频资源
		else if (type.equals(TypeDefinition.RESOURCE_AUDIO)) {

		}
		// 删除升级包资源
		else if (type.equals(TypeDefinition.RESOURCE_PACKAGE)) {

		} else {

		}
		// 删除资源主表
		resourceDAO.delete(resource);

	}

	@Override
	public List<EventResource> listEventRealResource(Long eventRealId) {
		return eventResourceDAO.getResourceIdsByEventId(eventRealId);
	}

	@Override
	public Map<Long, ResourceVO> mapResourcedByIds(List<Long> ids) {
		Map<Long, ResourceVO> mapResourceVO = new HashMap<Long, ResourceVO>();
		Map<Long, Resource> map = new HashMap<Long, Resource>();
		map = resourceDAO.map(ids);
		List<Long> idList = new LinkedList<Long>();
		for (Entry<Long, Resource> entry : map.entrySet()) {
			// 图片资源
			if (entry.getValue().getType() == TypeDefinition.RESOURCE_IMAGE) {
				idList.add(entry.getKey());
				// 音频资源
			} else if (entry.getValue().getType() == TypeDefinition.RESOURCE_AUDIO) {
				idList.add(entry.getKey());
				// 升级包资源
			} else if (entry.getValue().getType() == TypeDefinition.RESOURCE_PACKAGE) {
				idList.add(entry.getKey());
				// 视频资源
			} else if (entry.getValue().getType() == TypeDefinition.RESOURCE_VIDEO) {
				idList.add(entry.getKey());
			}
		}
		Map<Long, ResourceImage> mapImage = resourceImageDAO.map(idList);
		Map<Long, ResourceVideo> mapVideo = resourceVideoDAO.map(idList);
		Map<Long, ResourceAudio> mapAudio = resourceAudioDAO.map(idList);
		Map<Long, ResourcePackage> mapPackage = resourcePackageDAO.map(idList);
		if (mapImage.size() > 0) {
			for (Entry<Long, ResourceImage> entry : mapImage.entrySet()) {
				ResourceVO vo = new ResourceVO();
				vo.setId(entry.getValue().getId());
				vo.setName(entry.getValue().getName());
				vo.setCreateTime(entry.getValue().getCreateTime());
				vo.setAddress(entry.getValue().getAddress());
				vo.setType(TypeDefinition.RESOURCE_IMAGE + "");
				mapResourceVO.put(entry.getKey(), vo);
			}
		}
		if (mapVideo.size() > 0) {
			for (Entry<Long, ResourceVideo> entry : mapVideo.entrySet()) {
				ResourceVO vo = new ResourceVO();
				vo.setId(entry.getValue().getId());
				vo.setName(entry.getValue().getName());
				vo.setCreateTime(entry.getValue().getBeginTime());
				vo.setAddress(entry.getValue().getAddress());
				vo.setType(TypeDefinition.RESOURCE_VIDEO + "");
				mapResourceVO.put(entry.getKey(), vo);
			}
		}
		if (mapAudio.size() > 0) {
			for (Entry<Long, ResourceAudio> entry : mapAudio.entrySet()) {
				ResourceVO vo = new ResourceVO();
				vo.setId(entry.getValue().getId());
				vo.setName(entry.getValue().getName());
				vo.setCreateTime(entry.getValue().getBeginTime());
				vo.setAddress(entry.getValue().getAddress());
				vo.setType(TypeDefinition.RESOURCE_AUDIO + "");
				mapResourceVO.put(entry.getKey(), vo);
			}
		}
		if (mapPackage.size() > 0) {
			for (Entry<Long, ResourcePackage> entry : mapPackage.entrySet()) {
				ResourceVO vo = new ResourceVO();
				vo.setId(entry.getValue().getId());
				vo.setName(entry.getValue().getName());
				vo.setCreateTime(entry.getValue().getCreateTime());
				vo.setAddress(entry.getValue().getAddress());
				vo.setType(TypeDefinition.RESOURCE_PACKAGE + "");
				mapResourceVO.put(entry.getKey(), vo);
			}
		}
		return mapResourceVO;
	}

	@Override
	public void saveResource(Long eventRealId, Long resourceId) {
		// 事件资源关系表
		EventResource eResource = new EventResource();
		eResource.setEventRealId(eventRealId);
		eResource.setResourceId(resourceId);
		eventResourceDAO.save(eResource);
	}

	public void updateResource(EventResource eResource) {
		eventResourceDAO.update(eResource);
	}

	@Override
	public void deleteResourceByEventRealId(Long eventRealId) {
		eventResourceDAO.deleteByEventRealId(eventRealId);

	}

}
