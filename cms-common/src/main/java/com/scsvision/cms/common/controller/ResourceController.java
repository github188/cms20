package com.scsvision.cms.common.controller;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.common.dto.FileUploadDTO;
import com.scsvision.cms.common.dto.PackageVersionDTO;
import com.scsvision.cms.constant.Header;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.database.entity.Resource;
import com.scsvision.database.entity.ResourceImage;
import com.scsvision.database.entity.ResourcePackage;
import com.scsvision.database.entity.Server;
import com.scsvision.database.manager.ErrorMsgManager;
import com.scsvision.database.manager.PackageVersionManager;
import com.scsvision.database.manager.ResourceImageManager;
import com.scsvision.database.manager.ResourceManager;
import com.scsvision.database.manager.ResourcePackageManager;
import com.scsvision.database.manager.ServerManager;

/**
 * ResourceController
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015年12月31日 下午3:58:09
 */
@Stateless
public class ResourceController {

	private final Logger logger = LoggerFactory
			.getLogger(ResourceController.class);

	@EJB(beanName = "ErrorMsgManagerImpl")
	private ErrorMsgManager errorMsgManager;
	@EJB(beanName = "ResourceImageManagerImpl")
	private ResourceImageManager resourceImageManager;
	@EJB(beanName = "ResourcePackageManagerImpl")
	private ResourcePackageManager resourcePackageManager;
	@EJB(beanName = "ResourceManagerImpl")
	private ResourceManager resourceManager;
	@EJB(beanName = "ServerManagerImpl")
	private ServerManager serverManager;
	@EJB(beanName = "PackageVersionManagerImpl")
	private PackageVersionManager versionManager;

	public Object fileUpload(HttpServletRequest request) {
		FileUploadDTO dto = new FileUploadDTO();
		dto.setMethod(request.getHeader(Header.METHOD));
		// 检查是否文件上传请求
		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				InputStream in = null;
				String fileName = null;
				Long fileSize = null;
				String version = null;
				String svnVersion = null;
				String packageType = null;
				// 解析请求
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					String fieldName = item.getFieldName();
					fileName = item.getName();
					// 文件描述参数部分
					if ("version".equals(fieldName)) {
						version = item.getString();
					}
					if ("svnVersion".equals(fieldName)) {
						svnVersion = item.getString();
					}
					if ("packageType".equals(fieldName)) {
						packageType = item.getString();
					}

					// 文件流部分
					if ("fileData".equals(fieldName)) {
						in = item.getInputStream();
						fileSize = item.getSize();
					}
				}

				if (null != in) {
					// 获取资源服务器信息
					List<Server> resList = serverManager
							.getServerByType(TypeDefinition.RESOURCE_RES);
					if (resList.size() == 0) {
						dto.setCode(ErrorCode.RESOURCE_NOT_FOUND);
						dto.setMessage(errorMsgManager.getErrorMsgMap().get(
								ErrorCode.RESOURCE_NOT_FOUND));
						logger.error("RES not found, please create 1 first !");
					} else {
						Server res = resList.get(0);
						StringBuilder resAddress = new StringBuilder();
						resAddress.append("http://");
						resAddress.append(res.getIp1());
						resAddress.append(":");
						resAddress.append(res.getPort().toString());
						resAddress.append("/res/Resource_Upload.xml");
						// 上传资源
						String filePath = resourceManager.uploadFile(in,
								resAddress.toString(), fileName);
						// 保存数据库
						// 判断资源类型，客户端不支持显示传递类型参数，目前只能通过文件后缀名来简单判断
						int type = getResourceType(fileName);
						Resource resource = new Resource();
						resource.setType(type);
						Long id = resourceManager.createResource(resource);
						switch (type) {
						case TypeDefinition.RESOURCE_IMAGE:
							ResourceImage image = new ResourceImage();
							image.setAddress(filePath);
							image.setCreateTime(System.currentTimeMillis());
							image.setId(id);
							image.setName(fileName);
							image.setServerId(res.getId());
							resourceImageManager.createImage(image);
							break;
						case TypeDefinition.RESOURCE_AUDIO:
							// TODO
							break;
						case TypeDefinition.RESOURCE_VIDEO:
							// TODO
							break;
						case TypeDefinition.RESOURCE_PACKAGE:
							if (null == version) {
								throw new BusinessException(
										ErrorCode.PARAMETER_NOT_FOUND,
										"missing [ version ]");
							}
							ResourcePackage resourcePackage = new ResourcePackage();
							resourcePackage.setId(id);
							resourcePackage.setName(fileName);
							resourcePackage.setAddress(filePath);
							resourcePackage.setCreateTime(System
									.currentTimeMillis());
							resourcePackage.setModuleName(packageType);
							resourcePackage.setPackageSize(fileSize);
							resourcePackage.setServerId(res.getId());
							resourcePackage.setSvnVersion(svnVersion);
							resourcePackage.setPackageVersion(version);
							Long resourceId = resourcePackageManager
									.saveResourcePackage(resourcePackage);
							versionManager.updateOrSavePackageVersion(
									resourceId, fileName, packageType, version);
							break;
						// 默认创建图片
						default:
							ResourceImage entity = new ResourceImage();
							entity.setAddress(filePath);
							entity.setCreateTime(System.currentTimeMillis());
							entity.setId(id);
							entity.setName(fileName);
							entity.setServerId(res.getId());
							resourceImageManager.createImage(entity);
							break;
						}
						dto.setAddress(filePath);
						dto.setCode(ErrorCode.SUCCESS);
						dto.setId(id.toString());
						dto.setMessage("");
					}
				} else {
					dto.setCode(ErrorCode.PARAMETER_NOT_FOUND);
					dto.setMessage(errorMsgManager.getErrorMsgMap().get(
							ErrorCode.PARAMETER_NOT_FOUND));
				}
			} else {
				dto.setCode(ErrorCode.NOT_MULTIPART_REQUEST);
				dto.setMessage(errorMsgManager.getErrorMsgMap().get(
						ErrorCode.NOT_MULTIPART_REQUEST));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(ErrorCode.NETWORK_IO_ERROR);
			dto.setMessage(errorMsgManager.getErrorMsgMap().get(
					ErrorCode.NETWORK_IO_ERROR));
		}

		return dto;
	}

	public Object fileDelete(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		Long id = reader.getLong("id", false);
		// 删除数据库
		String url = resourceManager.deleteResource(id);
		if (null == url) {
			dto.setCode(ErrorCode.RESOURCE_NOT_FOUND);
			dto.setMessage(errorMsgManager.getErrorMsgMap().get(
					ErrorCode.RESOURCE_NOT_FOUND));
			return dto;
		}

		// 删除资源服务器上的资源
		// 获取资源服务器信息
		List<Server> resList = serverManager
				.getServerByType(TypeDefinition.RESOURCE_RES);
		if (resList.size() == 0) {
			dto.setCode(ErrorCode.RESOURCE_NOT_FOUND);
			dto.setMessage(errorMsgManager.getErrorMsgMap().get(
					ErrorCode.RESOURCE_NOT_FOUND));
			logger.error("RES not found, please create 1 first !");
		} else {
			Server res = resList.get(0);
			StringBuilder resAddress = new StringBuilder();
			resAddress.append("http://");
			resAddress.append(res.getIp1());
			resAddress.append(":");
			resAddress.append(res.getPort().toString());
			resAddress.append(url);
			resourceManager.deleteFile(resAddress.toString());

			dto.setCode(ErrorCode.SUCCESS);
			dto.setMessage("");
		}

		return dto;
	}

	/**
	 * 根据文件后缀名简单判断资源类型
	 * 
	 * @param fileName
	 *            文件名称
	 * @return 资源类型
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月4日 上午11:10:40
	 */
	private int getResourceType(String fileName) {
		if (fileName.endsWith("jpg") || fileName.endsWith("bmp")
				|| fileName.endsWith("png") || fileName.endsWith("jpeg")
				|| fileName.endsWith("JPG") || fileName.endsWith("BMP")
				|| fileName.endsWith("PNG") || fileName.endsWith("JPEG")) {
			return TypeDefinition.RESOURCE_IMAGE;
		} else if (fileName.endsWith("mp4") || fileName.endsWith("h264")
				|| fileName.endsWith("avi") || fileName.endsWith("rvmb")
				|| fileName.endsWith("rmb") || fileName.endsWith("3gp")
				|| fileName.endsWith("MP4") || fileName.endsWith("H264")
				|| fileName.endsWith("AVI") || fileName.endsWith("RVMB")
				|| fileName.endsWith("RMB") || fileName.endsWith("3GP")) {
			return TypeDefinition.RESOURCE_VIDEO;
		} else if (fileName.endsWith("mp3") || fileName.endsWith("wmv")
				|| fileName.endsWith("MP3") || fileName.endsWith("WMV")) {
			return TypeDefinition.RESOURCE_AUDIO;
		} else if (fileName.endsWith("zip") || fileName.endsWith("apk")
				|| fileName.endsWith("rar") || fileName.endsWith("jar")
				|| fileName.endsWith("exe") || fileName.endsWith("ZIP")
				|| fileName.endsWith("APK") || fileName.endsWith("RAR")
				|| fileName.endsWith("JAR") || fileName.endsWith("EXE")) {
			return TypeDefinition.RESOURCE_PACKAGE;
		} else {
			return TypeDefinition.RESOURCE_IMAGE;
		}
	}

	public Object getPackageJson(HttpServletRequest request) {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String type = reader.getString("type", false);

		Long resourceId = versionManager.getResourceIdByPackageType(type);
		ResourcePackage resourcePackage = new ResourcePackage();
		if (null != resourceId) {
			resourcePackage = resourcePackageManager
					.gindResourceById(resourceId);
		}

		PackageVersionDTO dto = new PackageVersionDTO();
		dto.setCode(ErrorCode.SUCCESS);
		dto.setMethod("GetControlVersion");

		PackageVersionDTO.Package controlPackage = dto.new Package();
		controlPackage.setName(resourcePackage.getName());
		controlPackage.setUrl(resourcePackage.getAddress());
		controlPackage.setVersion(resourcePackage.getPackageVersion());

		dto.setControlPackage(controlPackage);
		return dto;
	}
}
