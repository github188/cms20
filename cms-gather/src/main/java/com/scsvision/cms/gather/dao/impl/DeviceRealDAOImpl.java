package com.scsvision.cms.gather.dao.impl;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.scsvision.cms.gather.dao.DeviceRealDAO;
import com.scsvision.cms.util.json.JsonUtil;
import com.scsvision.database.entity.DeviceReal;

/**
 * DeviceRealDAOImpl
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月7日 下午4:50:01
 */
@Stateless
public class DeviceRealDAOImpl extends GatherBaseDAOImpl<DeviceReal> implements
		DeviceRealDAO {

	public void saveMany(List list) {
		UpdateOptions updateOptions = new UpdateOptions();
		updateOptions.upsert(true);

		for (Object obj : list) {
			Document doc = JsonUtil.toDocument(obj);
			collection.updateOne(eq("deviceId", doc.get("deviceId")),
					new Document("$set", doc), updateOptions);
		}
	}

	public List<Document> listDeviceReal(List<Long> deviceIds) {
		BasicDBObject filter = new BasicDBObject();
		filter.append("deviceId", new BasicDBObject("$in", deviceIds));

		FindIterable<Document> it = collection.find(filter);
		MongoCursor<Document> cursor = it.iterator();

		List<Document> list = new LinkedList<Document>();
		while (cursor.hasNext()) {
			list.add(cursor.next());
		}

		return list;
	}
}
