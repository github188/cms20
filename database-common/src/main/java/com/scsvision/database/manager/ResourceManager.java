package com.scsvision.database.manager;

import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;

import com.scsvision.database.entity.Resource;
import com.scsvision.database.entity.ResourceImage;

/**
 * ResourceManager
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月4日 上午10:13:19
 */
@Local
public interface ResourceManager {
	/**
	 * 上传资源到资源服务器
	 * 
	 * @param in
	 *            文件流
	 * @param resAddress
	 *            资源服务器接口地址
	 * @param fileName
	 *            上传原始文件名称
	 * @return 保存成功的路径
	 * @author MIKE
	 *         <p />
	 *         Create at 2015年12月31日 下午5:07:13
	 */
	public String uploadFile(InputStream in, String resAddress, String fileName);

	/**
	 * 创建资源主类型
	 * 
	 * @param resource
	 *            要创建的资源
	 * @return 创建成功的ID
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月4日 上午11:12:08
	 */
	public Long createResource(Resource resource);

	/**
	 * 删除资源
	 * 
	 * @param id
	 *            资源ID
	 * @param 资源url
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月5日 下午8:46:04
	 */
	public String deleteResource(Long id);

	/**
	 * 删除资源服务器上的资源
	 * 
	 * @param resAddress
	 *            资源服务器接口地址
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月5日 下午8:56:31
	 */
	public void deleteFile(String resAddress);

	/**
	 * 
	 * @param resource
	 *            保存主资源bao
	 */
	public Long saveResourcePackage();

	/**
	 * 查询未被关联的所有图片
	 * 
	 * @return 未被关联的图片资源
	 * @author MIKE
	 *         <p />
	 *         Create at 2016年1月19日 下午3:29:22
	 */
	public List<ResourceImage> listNoReferencedImages();
}
