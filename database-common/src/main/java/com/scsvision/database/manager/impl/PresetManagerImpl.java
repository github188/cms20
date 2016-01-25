package com.scsvision.database.manager.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.scsvision.database.dao.SvPresetDAO;
import com.scsvision.database.entity.SvPreset;
import com.scsvision.database.manager.PresetManager;

/**
 * PresetManagerImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午9:16:03
 */
@Stateless
public class PresetManagerImpl implements PresetManager {
	@EJB(beanName = "SvPresetDAOImpl")
	private SvPresetDAO svPresetDAO;

	@Override
	public Long createPreset(Integer presetValue, Long deviceId, String name,
			Long resourceId) {
		SvPreset preset = null;
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("presetValue", presetValue);
		params.put("deviceId", deviceId);
		List<SvPreset> list = svPresetDAO.findByPropertys(params);
		if (list.size() > 0) {
			preset = list.get(0);
			preset.setName(name);
			svPresetDAO.update(preset);
		} else {
			preset = new SvPreset();
			preset.setDeviceId(deviceId);
			preset.setName(name);
			preset.setPresetValue(presetValue);
			svPresetDAO.save(preset);
		}
		return preset.getId();
	}

	@Override
	public void updatePreset(Long id, Integer presetValue, Long deviceId,
			String name) {
		SvPreset preset = svPresetDAO.get(id);
		if (null != presetValue) {
			preset.setPresetValue(presetValue);
		}
		if (null != deviceId) {
			preset.setDeviceId(deviceId);
		}
		if (null != name) {
			preset.setName(name);
		}
		svPresetDAO.update(preset);
	}

	@Override
	public List<SvPreset> listPreset(Long deviceId) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("deviceId", deviceId);
		List<SvPreset> list = svPresetDAO.findByPropertys(params);
		params.clear();
		return list;
	}

	@Override
	public void deletePreset(Long id) {
		svPresetDAO.deleteById(id);
	}
}
