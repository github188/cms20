package com.scsvision.cms.statistic.dao.impl;

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
import com.scsvision.cms.statistic.dao.StatisticBaseDAO;
import com.scsvision.cms.util.json.JsonUtil;
import com.scsvision.cms.util.nosql.MongoDbUtil;
import com.scsvision.cms.util.string.MyStringUtil;

/**
 * StatisticBaseDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:10:23
 */
@Stateless
public class StatisticBaseDAOImpl<T> implements StatisticBaseDAO<T> {

	protected MongoCollection<Document> collection;

	protected Class<T> clazz;

	public StatisticBaseDAOImpl() {
		this.clazz = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@PostConstruct
	public void init() {
		MongoDatabase db = MongoDbUtil.getDatabase("statistic");
		collection = db.getCollection(MyStringUtil.classNameToTableName(clazz
				.getSimpleName()));
		// 创建基于时间的索引
		ListIndexesIterable<Document> indexs = collection.listIndexes();
		for (Document index : indexs) {
			if (null == index.get("recTime")) {
				collection.createIndex(new BasicDBObject("recTime", 1));
			}
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
	public List<T> list(List<Long> organIds, Date beginTime, Date endTime,
			int start, int limit) {
		FindIterable<Document> it = null;
		BasicDBObject filter = new BasicDBObject();
		if (organIds != null && organIds.size() > 0) {
			filter.put("organId", new BasicDBObject("$in", organIds));
		}

		BasicDBObject timeCondition = new BasicDBObject();
		if (null != beginTime) {
			timeCondition.put("$gte", beginTime);
		}
		if (null != endTime) {
			timeCondition.put("$lt", endTime);
		}
		if (null != beginTime || null != endTime) {
			filter.put("recTime", timeCondition);
		}

		it = collection.find(filter).skip(start).limit(limit)
				.sort(new BasicDBObject("recTime", 1));
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
	public T getLatest() {
		FindIterable<Document> it = collection.find().limit(1)
				.sort(new BasicDBObject("recTime", -1));
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
