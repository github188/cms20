package com.scsvision.cms.sv.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.response.BaseDTO;
import com.scsvision.cms.sv.dto.ListSvPlaySchemeDTO;
import com.scsvision.cms.sv.manager.PlaySchemeManager;
import com.scsvision.cms.util.request.JsonRequestReader;
import com.scsvision.cms.util.request.SimpleRequestReader;
import com.scsvision.database.entity.SvPlayScheme;

/**
 * PlaySchemeController
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月13日 上午10:19:57
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PlaySchemeController {

	private final Logger logger = LoggerFactory
			.getLogger(PlaySchemeController.class);

	@EJB(beanName = "PlaySchemeManagerImpl")
	private PlaySchemeManager playSchemeManager;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Object createSvPlaySchemeJson(HttpServletRequest request) {
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		JsonRequestReader reader = new JsonRequestReader(request);
		JSONObject jsonParam = reader.getJsonParam();

		String name = JsonRequestReader.getString(jsonParam, "name");
		Integer screenNo = JsonRequestReader.getInt(jsonParam, "screenNumber");

		Long id = playSchemeManager.createPlayScheme(name, screenNo,
				reader.getBody());
		dto.setMessage(id.toString());
		return dto;
	}

	public Object listSvPlaySchemeJson(HttpServletRequest request) {
		ListSvPlaySchemeDTO dto = new ListSvPlaySchemeDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		List<SvPlayScheme> list = playSchemeManager.listPlayScheme();
		for (SvPlayScheme playScheme : list) {
			try {
				JSONObject schemeConfig = new JSONObject(
						playScheme.getSchemeConfig());
				schemeConfig.put("id", playScheme.getId().longValue());
				dto.getPlaySchemes().put(schemeConfig);
			} catch (JSONException e) {
				logger.error("SvPlayScheme[" + playScheme.getId()
						+ "] scheme_config is not a valid JSON value !");
				continue;
			}
		}
		// 因为cms-sv的ClassLoader与sgc-interface的ClassLoader不同，
		// 又同时使用了JSONObject/JSONArray对象导致转换字符串时cms-sv传递过去的这些对象被当做Object对象处理，
		// 解决方式只能在cms-sv就完成字符串的转换
		return JSONObject.wrap(dto).toString();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Object updateSvPlaySchemeJson(HttpServletRequest request) {
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		JsonRequestReader reader = new JsonRequestReader(request);
		JSONObject jsonParam = reader.getJsonParam();

		String name = JsonRequestReader.getString(jsonParam, "name");
		Integer screenNo = JsonRequestReader.getInt(jsonParam, "screenNumber");
		Long id = JsonRequestReader.getLong(jsonParam, "id");

		playSchemeManager
				.updatePlayScheme(id, name, screenNo, reader.getBody());

		return dto;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Object deleteSvPlaySchemeJson(HttpServletRequest request) {
		BaseDTO dto = new BaseDTO();
		dto.setMethod(request.getHeader(Header.METHOD));

		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long id = reader.getLong("id", false);

		playSchemeManager.deletePlayScheme(id);
		return dto;
	}
}
