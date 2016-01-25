package com.scsvision.cms.gather.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.scsvision.cms.gather.dao.GatherBaseDAO;
import com.scsvision.cms.util.json.JsonUtil;
import com.scsvision.cms.util.nosql.MongoDbUtil;
import com.scsvision.cms.util.string.MyStringUtil;

/**
 * GatherBaseDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:09:14
 * @param <T>
 */
@Stateless
public class GatherBaseDAOImpl<T> implements GatherBaseDAO<T> {

	protected MongoCollection<Document> collection;

	protected Class<T> clazz;

	public GatherBaseDAOImpl() {
		this.clazz = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@PostConstruct
	public void init() {
		MongoDatabase db = MongoDbUtil.getDatabase("gather");
		collection = db.getCollection(MyStringUtil.classNameToTableName(clazz
				.getSimpleName()));
		// 创建基于时间的索引
		ListIndexesIterable<Document> indexs = collection.listIndexes();
		boolean createFlag = true;
		for (Document index : indexs) {
			Document key = (Document) index.get("key");
			if (null != key.get("recTime")) {
				createFlag = false;
				break;
			}

		}
		if (createFlag) {
			collection.createIndex(new BasicDBObject("recTime", 1));
		}
	}

	@Override
	public void batchInsert(List<T> list) {
		List<Document> documents = new LinkedList<Document>();
		for (T t : list) {
			Document doc = JsonUtil.toDocument(t);
			documents.add(doc);
		}
		collection.insertMany(documents);
	}

	@Override
	public List<T> list(Long deviceId, int start, int limit) {
		FindIterable<Document> it = null;
		if (null != deviceId) {
			BasicDBObject filter = new BasicDBObject();
			filter.put("deviceId", deviceId);
			it = collection.find(filter).skip(start).limit(limit);
		} else {
			it = collection.find().skip(start).limit(limit);
		}
		MongoCursor<Document> cursor = it.iterator();

		List<T> list = new LinkedList<T>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			T t = JsonUtil.toBean(doc, clazz);
			list.add(t);
		}
		return list;
	}

	@Override
	public List<T> list(List<Long> ids, Date beginTime, Date endTime) {
		FindIterable<Document> it = null;
		BasicDBObject filter = new BasicDBObject();
		if (null != beginTime && null != endTime) {
			filter.append("recTime", new BasicDBObject("$gte", beginTime)
					.append("$lte", endTime));
		}
		if (null != ids && ids.size() > 0) {
			filter.append("deviceId", new BasicDBObject("$in", ids));
		}
		it = collection.find(filter).sort(new BasicDBObject("recTime", 1));
		MongoCursor<Document> cursor = it.iterator();

		List<T> list = new LinkedList<T>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			T t = JsonUtil.toBean(doc, clazz);
			list.add(t);
		}
		return list;
	}
	
	@Override
	public List<T> list(Long deviceId, Date beginTime, Date endTime) {
		FindIterable<Document> it = null;
		BasicDBObject filter = new BasicDBObject();
		if(null != beginTime && null != endTime){
			filter.append("recTime", new BasicDBObject("$gte", beginTime).append("$lte", endTime));
		}
		if(null != deviceId){
			filter.append("deviceId", deviceId);
		}
		it = collection.find(filter).sort(new BasicDBObject("recTime", 1));
		MongoCursor<Document> cursor = it.iterator();
		List<T> list = new LinkedList<T>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			T t = JsonUtil.toBean(doc, clazz);
			list.add(t);
		}
		return list;
	}

	@Override
	public T getGatherEntity(Long deviceId) {
		FindIterable<Document> it = null;
		if (null != deviceId) {
			BasicDBObject filter = new BasicDBObject();
			filter.append("deviceId", deviceId);
			it = collection.find(filter).sort(new BasicDBObject("recTime", -1));
		} else {
			it = collection.find();
		}
		Document doc = it.first();
		if (null != doc) {
			return JsonUtil.toBean(doc, clazz);
		}
		return null;
	}

	@Override
	public T getFirst() {
		FindIterable<Document> it = collection.find().limit(1)
				.sort(new BasicDBObject("recTime", 1));
		Document doc = it.first();
		if (null != doc) {
			return JsonUtil.toBean(doc, clazz);
		}
		return null;
	}
}
