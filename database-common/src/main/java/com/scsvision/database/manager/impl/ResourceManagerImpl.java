package com.scsvision.database.manager.impl;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.util.http.HttpUtil;
import com.scsvision.database.dao.EventResourceDAO;
import com.scsvision.database.dao.ResourceAudioDAO;
import com.scsvision.database.dao.ResourceDAO;
import com.scsvision.database.dao.ResourceImageDAO;
import com.scsvision.database.dao.ResourcePackageDAO;
import com.scsvision.database.dao.ResourceVideoDAO;
import com.scsvision.database.dao.SvPresetDAO;
import com.scsvision.database.entity.Resource;
import com.scsvision.database.entity.ResourceAudio;
import com.scsvision.database.entity.ResourceImage;
import com.scsvision.database.entity.ResourcePackage;
import com.scsvision.database.entity.ResourceVideo;
import com.scsvision.database.manager.ResourceManager;

/**
 * ResourceManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月4日 上午10:20:18
 */
@Stateless
public class ResourceManagerImpl implements ResourceManager {

	@EJB(beanName = "ResourceDAOImpl")
	private ResourceDAO resourceDAO;
	@EJB(beanName = "ResourceImageDAOImpl")
	private ResourceImageDAO resourceImageDAO;
	@EJB(beanName = "ResourceAudioDAOImpl")
	private ResourceAudioDAO resourceAudioDAO;
	@EJB(beanName = "ResourceVideoDAOImpl")
	private ResourceVideoDAO resourceVideoDAO;
	@EJB(beanName = "ResourcePackageDAOImpl")
	private ResourcePackageDAO resourcePackageDAO;
	@EJB(beanName = "EventResourceDAOImpl")
	private EventResourceDAO eventResourceDAO;
	@EJB(beanName = "SvPresetDAOImpl")
	private SvPresetDAO svPresetDAO;

	private final Logger logger = LoggerFactory
			.getLogger(ResourceManagerImpl.class);

	@Override
	public String uploadFile(InputStream in, String resAddress, String fileName) {
		HttpURLConnection con = HttpUtil.multiPost(resAddress, in, fileName);
		try {
			InputStream resIn = con.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(resIn);
			Element root = doc.getRootElement();
			String code = doc.valueOf("Response/@Code");
			// 上传成功
			if (ErrorCode.SUCCESS.equals(code)) {
				String filePath = doc.valueOf("Response/Item[1]/@Url");
				return filePath;
			} else {
				throw new BusinessException(code,
						doc.valueOf("Response/@Message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					e.getMessage());
		}
	}

	@Override
	public Long createResource(Resource resource) {
		resourceDAO.save(resource);
		return resource.getId();
	}

	@Override
	public String deleteResource(Long id) {
		Resource resource = resourceDAO.get(id);
		if (null == resource) {
			return null;
		}
		String url = null;

		switch (resource.getType().intValue()) {
		case TypeDefinition.RESOURCE_AUDIO:
			ResourceAudio resourceAudio = resourceAudioDAO.get(id);
			url = resourceAudio.getAddress();
			resourceAudioDAO.delete(resourceAudio);
			break;
		case TypeDefinition.RESOURCE_VIDEO:
			ResourceVideo resourceVideo = resourceVideoDAO.get(id);
			url = resourceVideo.getAddress();
			resourceVideoDAO.delete(resourceVideo);
			break;
		case TypeDefinition.RESOURCE_IMAGE:
			ResourceImage resourceImage = resourceImageDAO.get(id);
			url = resourceImage.getAddress();
			resourceImageDAO.delete(resourceImage);
			break;
		case TypeDefinition.RESOURCE_PACKAGE:
			ResourcePackage resourcePackage = resourcePackageDAO.get(id);
			url = resourcePackage.getAddress();
			resourcePackageDAO.delete(resourcePackage);
			break;
		default:
			break;
		}
		resourceDAO.delete(resource);
		return url;
	}

	@Override
	public void deleteFile(String resAddress) {
		HttpURLConnection con = HttpUtil.delete(resAddress);
		try {
			if (con.getResponseCode() == 200) {
				return;
			} else {
				logger.error("Delete File["
						+ resAddress
						+ "] not success, please see RES log for more details !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					e.getMessage());
		}
	}

	@Override
	public Long saveResourcePackage() {
		Resource resource = new Resource();
		resource.setType(TypeDefinition.RESOURCE_PACKAGE);
		resourceDAO.save(resource);
		return resource.getId();
	}

	@Override
	public List<ResourceImage> listNoReferencedImages() {
		// 返回结果
		List<ResourceImage> list = new LinkedList<ResourceImage>();
		// 所有的图片资源
		List<ResourceImage> images = resourceImageDAO.list();
		// 所有关联中的图片ID集合
		List<Long> referencedIds = new LinkedList<Long>();
		// 事件关联中的图片ID集合
		List<Long> imageReferencedIds = eventResourceDAO
				.listReferencedResourceIds();
		// 预置点关联中的图片ID集合
		List<Long> presetReferencedIds = svPresetDAO
				.listReferencedResourceIds();
		referencedIds.addAll(imageReferencedIds);
		referencedIds.addAll(presetReferencedIds);

		for (ResourceImage image : images) {
			if (referencedIds.contains(image.getId())) {
				continue;
			} else {
				list.add(image);
			}
		}
		return list;
	}
}
