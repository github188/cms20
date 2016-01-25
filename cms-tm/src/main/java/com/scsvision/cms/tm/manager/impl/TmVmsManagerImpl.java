/**
 * 
 */
package com.scsvision.cms.tm.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.maintain.dao.UserLogDAO;
import com.scsvision.cms.tm.dao.TmCmsPublishDAO;
import com.scsvision.cms.tm.dao.TmPlayListDAO;
import com.scsvision.cms.tm.manager.TmVmsManager;
import com.scsvision.cms.tm.service.vo.VmsInfoVO;
import com.scsvision.cms.tm.service.vo.VmsPublishVO;
import com.scsvision.cms.util.string.MyStringUtil;
import com.scsvision.database.dao.TmDeviceDAO;
import com.scsvision.database.entity.TmCmsPublish;
import com.scsvision.database.entity.TmDevice;
import com.scsvision.database.entity.TmPlayList;
import com.scsvision.database.entity.UserLog;
import com.scsvision.database.entity.VirtualOrgan;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class TmVmsManagerImpl implements TmVmsManager {

	@EJB(beanName = "TmCmsPublishDAOImpl")
	private TmCmsPublishDAO tmCmsPublishDAO;

	@EJB(beanName = "TmDeviceDAOImpl")
	private TmDeviceDAO tmDeviceDAO;

	@EJB(beanName = "UserLogDAOImpl")
	private UserLogDAO userLogDAO;

	@EJB(beanName = "TmPlayListDAOImpl")
	private TmPlayListDAO tmPlayListDAO;

	@Override
	public Long saveCmsPublish(TmCmsPublish entity) {
		tmCmsPublishDAO.save(entity);
		return entity.getId();
	}

	@Override
	public TmCmsPublish getTmCmsPublish(Long id) {
		TmCmsPublish entity = tmCmsPublishDAO.get(id);
		return entity;
	}

	@Override
	public void update(TmCmsPublish entity) {
		tmCmsPublishDAO.update(entity);
	}

	@Override
	public void delete(List<Long> ids) {
		tmCmsPublishDAO.deleteCmsPublish(ids);
	}

	@Override
	public List<TmCmsPublish> listCmsPublish(Integer start, Integer limit,
			Long playlistId) {
		List<TmCmsPublish> list = tmCmsPublishDAO.listCmsPublish(start, limit,
				playlistId);
		return list;
	}

	@Override
	public Integer countCmsPublish(Long cmsId) {
		Integer count = tmCmsPublishDAO.countCmsPublish(cmsId);
		return count;
	}

	@Override
	public List<VmsPublishVO> listVmsPushbishVO(List<VirtualOrgan> vmses) {
		List<VmsPublishVO> list = new ArrayList<VmsPublishVO>();
		List<Long> ids = new ArrayList<Long>();
		Map<Long, VirtualOrgan> voMap = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan vms : vmses) {
			ids.add(vms.getDeviceId());
			voMap.put(vms.getDeviceId(), vms);
		}
		// 查询tmDevice
		Map<Long, TmDevice> deviceMap = tmDeviceDAO.map(ids);

		// 查询情报板发布日志
		List<UserLog> logList = userLogDAO.listCmsInfo(ids);
		for (UserLog log : logList) {
			// 作一个判断如果发布了情报板又删除了设备，可能会有异常，只返回存在的设备信息
			if (voMap.get(log.getTargetId()) != null
					&& deviceMap.get(log.getTargetId()) != null) {
				try {
					Document document = DocumentHelper.parseText(log
							.getContent());
					Element root = document.getRootElement();
					List<Element> items = root.elements();
					for (Element item : items) {
						VmsPublishVO vo = new VmsPublishVO();
						vo.setContent(item.attribute("Content").getValue());
						vo.setId(log.getTargetId() + "");
						vo.setName(deviceMap.get(log.getTargetId()).getName());
						vo.setSendTime(log.getCreateTime().toString());
						vo.setStakeNumber(deviceMap.get(log.getTargetId())
								.getStakeNumber());
						list.add(vo);
					}

				} catch (DocumentException e) {
					e.printStackTrace();
					throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
							e.getMessage());
				}
			}
		}
		return list;
	}

	@Override
	public Map<Long, TmDevice> mapTmDevice(List<VirtualOrgan> vmses) {
		List<Long> ids = new ArrayList<Long>();
		for (VirtualOrgan vms : vmses) {
			ids.add(vms.getDeviceId());
		}
		return tmDeviceDAO.map(ids);
	}

	@Override
	public TmCmsPublish getCmsPublishDetail(Long id) {
		return tmCmsPublishDAO.get(id);
	}

	@Override
	public List<VmsInfoVO> listVmsInfoByIds(List<Long> ids) {
		List<VmsInfoVO> vos = new ArrayList<VmsInfoVO>();
		// List<TmCmsPublish> list = tmCmsPublishDAO.list(ids);
		// if (list.get(0).getCmsId() == null) {
		// throw new BusinessException(ErrorCode.PARAMETER_INVALID,
		// "VmsInfo id " + list.get(0).getId() + " invalid");
		// }
		// TmDevice td = tmDeviceDAO.get(list.get(0).getCmsId());
		// String extend[] = td.getExtend().split("::");
		// String width = MyStringUtil.getAttributeValue(extend, "width");
		// String hight = MyStringUtil.getAttributeValue(extend, "hight");
		// String standardNumber = td.getStandardNumber();
		// for (TmCmsPublish tcp : list) {
		// VmsInfoVO vo = new VmsInfoVO();
		// vo.setBackgroundColor(tcp.getBackgroundColor());
		// vo.setColor(tcp.getColor());
		// vo.setContent(tcp.getContent());
		// vo.setDuration(tcp.getDuration());
		// vo.setFont(tcp.getFont());
		// vo.setHight(hight);
		// vo.setId(tcp.getId());
		// vo.setMarginLeft(tcp.getMarginLeft());
		// vo.setMarginTop(tcp.getMarginTop());
		// vo.setPlaySize(tcp.getPlaySize());
		// vo.setRowSize(tcp.getRowSize());
		// vo.setSpace(tcp.getSpace());
		// vo.setStandardNumber(standardNumber);
		// vo.setWidth(width);
		// vos.add(vo);
		// }
		return vos;
	}

	@Override
	public List<TmPlayList> listPlaylist(String sizeType) {
		return tmPlayListDAO.listPlaylist(sizeType);
	}

	@Override
	public Long createPlayList(String name, String sizeType) {
		TmPlayList playlist = new TmPlayList();
		playlist.setName(name);
		playlist.setSizeType(sizeType);
		tmPlayListDAO.save(playlist);
		return playlist.getId();
	}

	@Override
	public void updatePlayListJson(Long id, String name, String sizeType) {
		TmPlayList playlist = tmPlayListDAO.get(id);
		if (null != name) {
			playlist.setName(name);
		}
		if (null != sizeType) {
			playlist.setSizeType(sizeType);
		}
		tmPlayListDAO.update(playlist);

	}

	@Override
	public void deletePlayListJson(Long id) {
		TmPlayList playlist = tmPlayListDAO.get(id);
		// 删除节目单下的条目
		tmCmsPublishDAO.deleteByPlaylistId(playlist.getId());
		// 删除节目单
		tmPlayListDAO.delete(playlist);
	}

	@Override
	public TmPlayList getPlayListJson(Long id) {
		return tmPlayListDAO.get(id);
	}

	@Override
	public void batchTmCmsPublish(List<TmCmsPublish> list) {
		tmCmsPublishDAO.batchInsert(list);
	}

	@Override
	public void deleteCmsPublish(long playlistId) {
		tmCmsPublishDAO.deleteByPlaylistId(playlistId);
	}
}
