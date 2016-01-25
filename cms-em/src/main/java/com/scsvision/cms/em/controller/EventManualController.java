package com.scsvision.cms.em.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.scsvision.cms.em.manager.EventManualManager;
import com.scsvision.cms.util.request.RequestReader;
import com.scsvision.cms.util.xml.XmlUtil;
import com.scsvision.database.entity.EventManualReal;

@Stateless
public class EventManualController {
	@EJB(beanName="EventManualManagerImpl")
	private EventManualManager eventManualManager;
	
	public String listEventManualReal(String message){
		RequestReader reader = new RequestReader(message);
		Integer start = reader.getInteger("Request/Start", true);
		if(null==start){
			start =0;
		}
		Integer limit = reader.getInteger("Request/Limit", true);
		if(null==limit){
			limit = 0;
		}
		List<EventManualReal> list = eventManualManager.emanualRealList(start, limit);
		Element root = DocumentHelper.createElement("Response");
		root.addAttribute("Method", "ListEventManualReal");
		root.addAttribute("code", "200");
		root.addAttribute("cmd", "1004");
		root.addAttribute("message", "");
		Document doc = DocumentHelper.createDocument(root);
		for(EventManualReal em:list){
			root.add(XmlUtil.createElement("Event", em));
		}
		return XmlUtil.xmlToString(doc);
		
	}
}
