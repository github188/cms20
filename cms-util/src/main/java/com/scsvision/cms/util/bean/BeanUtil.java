package com.scsvision.cms.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;

/**
 * BeanUtil
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:55:37
 */
public class BeanUtil {
	/**
	 * 合并多个相同Class的POJO对象的属性
	 * 
	 * @param results
	 *            相同Class的多个POJO对象
	 * @return 合并后的POJO对象
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:07:55
	 */
	public static Object merge(Object[] results) throws Exception {
		if (results.length == 1) {
			return results[0];
		}

		Class clazz = results[0].getClass();
		Field[] fields = clazz.getDeclaredFields();

		// 所有的结果合并到results[0]中
		for (int i = 1; i < results.length; i++) {
			if (!clazz.equals(results[i].getClass())) {
				throw new BusinessException(ErrorCode.ERROR,
						"could not merge results under different Class, one is "
								+ clazz.getSimpleName() + " other one is "
								+ results[i].getClass().getSimpleName());
			}
			for (Field field : fields) {
				Class fieldClass = field.getType();
				// List合并
				if (fieldClass.equals(List.class)) {
					String name = field.getName();
					String firstLetter = name.substring(0, 1).toUpperCase();
					String getterName = "get" + firstLetter + name.substring(1);

					Method getter = clazz.getMethod(getterName);
					List value = (List) getter.invoke(results[0]);
					List addValue = (List) getter.invoke(results[i]);
					if (null == value) {
						value = addValue;
					} else {
						if (null != addValue) {
							for (int j = 0; j < addValue.size(); j++) {
								Object o = addValue.get(j);
								if (!value.contains(o)) {
									value.add(o);
								}
							}
						}
					}
				}
			}

		}

		return results[0];
	}
}
