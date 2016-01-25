package com.scsvision.cms.util.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;

/**
 * JsonUtil
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午4:53:39
 */
public class JsonUtil {

	/**
	 * POJO对象转Document(Mongodb中的记录行)，注意只能处理String, Integer, Long, Double,
	 * Date几种类型的属性
	 * 
	 * @param o
	 *            POJO对象
	 * @return Document(Mongodb中的记录行)
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午1:37:48
	 */
	public static Document toDocument(Object o) {
		Document doc = new Document();
		Class<?> clazz = o.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			// 移除ID属性
			if ("id".equals(name)) {
				continue;
			}
			String firstLetter = name.substring(0, 1).toUpperCase();
			String getterName = "get" + firstLetter + name.substring(1);
			try {
				Method getter = clazz.getMethod(getterName);
				Object value = getter.invoke(o);
				doc.append(name, value);
			} catch (Exception e) {
				continue;
			}
		}
		return doc;
	}

	/**
	 * Document(Mongodb中的记录行)转POJO对象
	 * 
	 * @param doc
	 *            Document(Mongodb中的记录行)
	 * @param clazz
	 *            POJO对象的Class
	 * @return POJO对象
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午1:42:18
	 */
	public static <T> T toBean(Document doc, Class<T> clazz) {
		T instance = null;
		try {
			instance = clazz.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e1.getMessage());
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			String firstLetter = name.substring(0, 1).toUpperCase();
			String setterName = "set" + firstLetter + name.substring(1);
			try {
				Method setter = clazz.getMethod(setterName, field.getType());
				Object value = doc.get(name);
				setter.invoke(instance, value);
			} catch (Exception e) {
				continue;
			}
		}
		// 单独setId()
		ObjectId id = (ObjectId) doc.get("_id");
		if (null != id) {
			try {
				clazz.getMethod("setId", String.class).invoke(instance,
						id.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
