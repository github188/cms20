package com.scsvision.cms.maintain.dao.impl;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;

import com.mongodb.client.ListIndexesIterable;
import com.scsvision.cms.maintain.dao.OnlineDAO;
import com.scsvision.cms.util.json.JsonUtil;
import com.scsvision.database.entity.Online;

@Stateless
public class OnlineDAOImpl extends OnlineBaseDAOImpl<Online> implements
		OnlineDAO {

	@Override
	public Online getOnline(String ticket) {
		BasicDBObject filter = new BasicDBObject("ticket", ticket);
		FindIterable<Document> it = collection.find(filter);
		Document doc = it.first();
		if (null != doc) {
			return JsonUtil.toBean(doc, Online.class);
		} else {
			return null;
		}
	}

	@PostConstruct
	public void initial() {
		// 创建基于StandardNumber的索引和Ticket的索引
		ListIndexesIterable<Document> indexs = collection.listIndexes();
		boolean snCreateFlag = true;
		boolean ticketCreateFlag = true;
		for (Document index : indexs) {
			Document key = (Document) index.get("key");
			if (null != key.get("standardNumber")) {
				snCreateFlag = false;
			}
			if (null != key.get("ticket")) {
				ticketCreateFlag = false;
			}
		}
		if (snCreateFlag) {
			collection.createIndex(new BasicDBObject("standardNumber", 1));
		}
		if (ticketCreateFlag) {
			collection.createIndex(new BasicDBObject("ticket", 1));
		}
	}

	@Override
	public Online getOnlineByDeviceId(Long deviceId) {
		BasicDBObject filter = new BasicDBObject("resourceId", deviceId);
		FindIterable<Document> it = collection.find(filter).sort(
				new BasicDBObject("recTime", -1));
		Document doc = it.first();
		if (null != doc) {
			return JsonUtil.toBean(doc, Online.class);
		} else {
			return null;
		}
	}
}
