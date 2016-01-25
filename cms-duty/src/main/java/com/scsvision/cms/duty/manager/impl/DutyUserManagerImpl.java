package com.scsvision.cms.duty.manager.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.scsvision.cms.duty.dao.DutyRecordDAO;
import com.scsvision.cms.duty.dao.DutyUserDAO;
import com.scsvision.cms.duty.manager.DutyUserManager;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.database.dao.UserDAO;
import com.scsvision.database.entity.DutyRecord;
import com.scsvision.database.entity.DutyUser;
import com.scsvision.database.entity.User;

/**
 * DutyUserManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午1:51:27
 */
@Stateless
public class DutyUserManagerImpl implements DutyUserManager {
	@EJB(beanName = "DutyUserDAOImpl")
	private DutyUserDAO dutyUserDAO;
	@EJB(beanName = "DutyRecordDAOImpl")
	private DutyRecordDAO dutyRecordDAO;
	@EJB(beanName = "UserDAOImpl")
	private UserDAO userDAO;

	@Override
	public void dutyLogin(DutyUser dutyUser, Short action)
			throws BusinessException {
		// 设置之前的登录记录为下班
		DutyRecord latest = dutyRecordDAO.getLatest();
		if (null != latest) {
			latest.setEndTime(System.currentTimeMillis());
			dutyRecordDAO.update(latest);
		}

		// 新增一条登录记录
		DutyRecord dutyRecord = new DutyRecord();
		dutyRecord.setBeginTime(System.currentTimeMillis());
		dutyRecord.setName(dutyUser.getName());
		dutyRecord.setPhone(dutyUser.getPhone());
		dutyRecordDAO.save(dutyRecord);

		// 修改值班账户的状态
		dutyUser.setStatus(action);
		dutyUserDAO.update(dutyUser);
	}

	@Override
	public Long createDutyUser(DutyUser dutyUser) {
		dutyUserDAO.save(dutyUser);
		return dutyUser.getId();
	}

	@Override
	public DutyUser getDutyUser(Long id) {
		return dutyUserDAO.get(id);
	}

	@Override
	public void updateDutyUser(DutyUser entity) {
		dutyUserDAO.update(entity);
	}

	@Override
	public void deleteDutyUser(Long id) {
		DutyUser entity = dutyUserDAO.get(id);
		dutyUserDAO.delete(entity);
	}

	@Override
	public Integer countDutyUser(String name, String master) {
		return dutyUserDAO.countDutyUser(name, master);
	}

	@Override
	public DutyUser getByLoginName(String loginName) {
		return dutyUserDAO.getByLoginName(loginName);
	}

	@Override
	public List<DutyUser> listDutyUser(Long userId) {
		User user = userDAO.get(userId);
		List<DutyUser> list = dutyUserDAO.listDutyUser(user.getOrganId());
		return list;
	}

	@Override
	public List<DutyUser> dutyUserList(Long organId, int start, int limit) {
		List<DutyUser> list = dutyUserDAO.dutyUserList(organId, start, limit);
		return list;
	}

	@Override
	public Integer dutyUserCount(Long organId) {
		return dutyUserDAO.dutyUserCount(organId);
	}
}
