package com.scsvision.cms.maintain.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.scsvision.cms.maintain.dao.OnlineBaseDAO;
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
public class OnlineBaseDAOImpl<T> implements OnlineBaseDAO<T> {

	protected MongoCollection<Document> collection;

	protected Class<T> clazz;

	public OnlineBaseDAOImpl() {
		this.clazz = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@PostConstruct
	public void init() {
		MongoDatabase db = MongoDbUtil.getDatabase("maintain");
		collection = db.getCollection(MyStringUtil.classNameToTableName(clazz
				.getSimpleName()));
	}

	@Override
	public T insert(T entity) {
		Document doc = JsonUtil.toDocument(entity);
		collection.insertOne(doc);
		T t = JsonUtil.toBean(doc, clazz);
		return t;
	}

	@Override
	public void update(String id, T entity) {
		Document doc = JsonUtil.toDocument(entity);
		ObjectId oid = new ObjectId(id);
		BasicDBObject filter = new BasicDBObject("_id", oid);
		collection.updateOne(filter, new Document("$set", doc));
	}

	@Override
	public void delete(String id) {
		ObjectId oid = new ObjectId(id);
		BasicDBObject filter = new BasicDBObject("_id", oid);
		collection.deleteOne(filter);
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
	public List<T> list(int start, int limit) {
		FindIterable<Document> it = null;
		it = collection.find().skip(start).limit(limit);
		MongoCursor<Document> cursor = it.iterator();

		List<T> list = new LinkedList<T>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			T t = JsonUtil.toBean(doc, clazz);
			list.add(t);
		}
		return list;
	}

}
