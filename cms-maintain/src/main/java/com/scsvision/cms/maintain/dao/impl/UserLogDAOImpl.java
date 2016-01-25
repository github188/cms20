/**
 * 
 */
package com.scsvision.cms.maintain.dao.impl;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.scsvision.cms.constant.TypeDefinition;
import com.scsvision.cms.maintain.dao.UserLogDAO;
import com.scsvision.cms.util.json.JsonUtil;
import com.scsvision.database.entity.UserLog;

/**
 * @author wangbinyu
 *
 */
@Stateless
public class UserLogDAOImpl extends OnlineBaseDAOImpl<UserLog> implements
		UserLogDAO {

	@Override
	public List<UserLog> listCmsInfo(List<Long> ids) {
		FindIterable<Document> it = null;
		if (ids.size() > 0) {
			BasicDBObject filter = new BasicDBObject();
			BasicDBList idList = new BasicDBList();
			for (Long id : ids) {
				idList.add(id);
			}
			filter.put("targetId", new BasicDBObject("$in", idList));
			filter.put("flag", TypeDefinition.USER_LOG_TRUE);
			filter.put("method", "PublishCms"); // 因为发布情报板时直接记日志随便写一个方法对应上
			it = collection.find(filter);
		} else {
			return null;
		}
		MongoCursor<Document> cursor = it.iterator();

		List<UserLog> list = new LinkedList<UserLog>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			UserLog userLog = JsonUtil.toBean(doc, UserLog.class);
			list.add(userLog);
		}
		return list;
	}

	@Override
	public void updateOldRerecord(Long targetId, String method) {
		// Document doc = new Document();
		BasicDBObject filter = new BasicDBObject();
		filter.put("targetId", targetId);
		filter.put("method", method);
		filter.put("flag", TypeDefinition.USER_LOG_TRUE);
		// BasicDBObject setValue = new BasicDBObject();
		// setValue.put("flag", TypeDefinition.USER_LOG_FALSE);
		// doc.append("flag", TypeDefinition.USER_LOG_FALSE);
		collection.updateOne(filter, new Document("$set", new BasicDBObject(
				"flag", TypeDefinition.USER_LOG_FALSE)));
	}

	@Override
	public void updateUserLog(String businessId, Short flag) {
		BasicDBObject filter = new BasicDBObject();
		filter.put("businessId", businessId);
		collection.updateOne(filter, new Document("$set", new BasicDBObject(
				"flag", flag)));
	}

}
