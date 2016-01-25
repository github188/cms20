package com.scsvision.cms.maintain.dao.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCursor;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.maintain.dao.OnlineRealDAO;
import com.scsvision.cms.util.json.JsonUtil;
import com.scsvision.database.entity.OnlineReal;

/**
 * OnlineRealDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:49:22
 */
@Stateless
public class OnlineRealDAOImpl extends OnlineBaseDAOImpl<OnlineReal> implements
		OnlineRealDAO {

	@PostConstruct
	public void initial() {
		// 创建基于StandardNumber的索引
		ListIndexesIterable<Document> indexs = collection.listIndexes();
		boolean createFlag = true;
		for (Document index : indexs) {
			Document key = (Document) index.get("key");
			if (null != key.get("standardNumber")) {
				createFlag = false;
				break;
			}
		}
		if (createFlag) {
			collection.createIndex(new BasicDBObject("standardNumber", 1));
		}
	}

	@Override
	public List<OnlineReal> listOnlineDevice() {
		FindIterable<Document> it = null;
		BasicDBObject filter = new BasicDBObject();
		filter.put(
				"type",
				new BasicDBObject("$gte", Integer
						.valueOf(TypeDefinition.VIDEO_DVR)));
		it = collection.find(filter);
		MongoCursor<Document> cursor = it.iterator();
		List<OnlineReal> list = new LinkedList<OnlineReal>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			OnlineReal t = JsonUtil.toBean(doc, OnlineReal.class);
			list.add(t);
		}
		return list;
	}

	@Override
	public List<OnlineReal> listOnlineReal(List<Integer> types,
			List<String> sns, Integer start, Integer limit) {
		FindIterable<Document> it = null;
		BasicDBObject filter = new BasicDBObject();
		if (null != types) {
			filter.put("type", new BasicDBObject("$in", types));
		}
		if (null != sns) {
			filter.put("standardNumber", new BasicDBObject("$in", sns));
		}
		if (null != start || null != limit) {
			it = collection.find(filter).skip(start).limit(limit);
		} else {
			it = collection.find(filter);
		}
		MongoCursor<Document> cursor = it.iterator();
		List<OnlineReal> list = new LinkedList<OnlineReal>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			OnlineReal t = JsonUtil.toBean(doc, OnlineReal.class);
			list.add(t);
		}
		return list;
	}

	@Override
	public OnlineReal getOnlineReal(String ticket) {
		BasicDBObject filter = new BasicDBObject("ticket", ticket);
		FindIterable<Document> it = collection.find(filter);
		Document doc = it.first();
		if (null != doc) {
			return JsonUtil.toBean(doc, OnlineReal.class);
		} else {
			return null;
		}
	}

	@Override
	public OnlineReal getOnlineRealById(String id) {
		ObjectId oid = new ObjectId(id);
		BasicDBObject filter = new BasicDBObject("_id", oid);
		FindIterable<Document> it = collection.find(filter);
		Document doc = it.first();
		if (null != doc) {
			return JsonUtil.toBean(doc, OnlineReal.class);
		} else {
			return null;
		}
	}

	@Override
	public Integer countOnlineRealList(List<Integer> types) {
		BasicDBObject filter = new BasicDBObject();
		filter.put("type", new BasicDBObject("$in", types));
		Long count = collection.count(filter);
		return count.intValue();
	}

	@Override
	public List<OnlineReal> mapOnlineCamera() {
		FindIterable<Document> it = null;
		BasicDBObject filter = new BasicDBObject();
		filter.put(
				"type",
				new BasicDBObject("$gte", Integer
						.valueOf(TypeDefinition.VIDEO_DVR)));
		filter.put(
				"type",
				new BasicDBObject("$lte", Integer
						.valueOf(TypeDefinition.VIDEO_CAMERA_HALFBALL)));
		it = collection.find(filter);
		MongoCursor<Document> cursor = it.iterator();
		List<OnlineReal> list = new LinkedList<OnlineReal>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			OnlineReal t = JsonUtil.toBean(doc, OnlineReal.class);
			list.add(t);
		}
		return list;
	}

	@Override
	public OnlineReal getOnlineRealByDeviceId(Long deviceId) {
		BasicDBObject filter = new BasicDBObject("resourceId", deviceId);
		FindIterable<Document> it = collection.find(filter);
		Document doc = it.first();
		if (null != doc) {
			return JsonUtil.toBean(doc, OnlineReal.class);
		} else {
			return null;
		}
	}

}
