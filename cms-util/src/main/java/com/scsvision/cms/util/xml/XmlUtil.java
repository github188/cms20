package com.scsvision.cms.util.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;

/**
 * XmlUtil
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 上午10:49:34
 */
public class XmlUtil {

	private static final String format = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 生成通用的接口响应xml内容
	 * 
	 * @param method
	 *            请求Method
	 * @param code
	 *            成功失败编码
	 * @param message
	 *            创建成功生成的Id或者失败时的错误信息
	 * @return <Response Method="UpdateUser" Code="200" Message="" />
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 上午10:53:17
	 */
	public static Document generateResponse(String method, String code,
			String message) {
		Element root = DocumentHelper.createElement("Response");
		Document document = DocumentHelper.createDocument(root);
		root.addAttribute("Method", method);
		root.addAttribute("Code", code);
		root.addAttribute("Message", message);
		document.setXMLEncoding("UTF-8");
		return document;
	}

	/**
	 * 创建一个Element，所有的属性值按照原始obj对象包括父类对象的public get方法生成
	 * 
	 * @param name
	 *            要生成的Element名称
	 * @param obj
	 *            原始对象
	 * @param childProperties
	 *            需要生成为child element的属性
	 * @param excludeProperties
	 *            例外的属性名称
	 * @return
	 */
	public static Element createElement(String name, Object obj,
			List<String> childProperties, List<String> excludeProperties) {
		Element element = DocumentHelper.createElement(name);
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			// 只关注get方法和is开头的属性值获取相关方法
			if (methodName.startsWith("get") || methodName.startsWith("is")) {
				String key = getPropertyName(methodName);
				if (null != excludeProperties
						&& excludeProperties.contains(key.toLowerCase())) {
					continue;
				}
				if (method.getReturnType().getName().equals("java.util.List")) {
					continue;
				}
				if (method.getReturnType().getName().equals("java.util.Set")) {
					continue;
				}
				if (method.getReturnType().getName().equals("java.lang.Class")) {
					continue;
				}
				if (method.getReturnType().getName()
						.startsWith("com.scsvision")) {
					continue;
				}
				String value = "";
				try {
					Object o = method.invoke(obj);
					if (null != o) {
						if (o instanceof Date) {
							value = new SimpleDateFormat(format).format(o);
						} else {
							value = o.toString();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				if (null != childProperties
						&& childProperties.contains(key.toLowerCase())) {
					Element child = DocumentHelper.createElement(key);
					child.setText(value);
					element.add(child);
				} else {
					element.addAttribute(key, value);
				}
			}
		}

		return element;
	}

	/**
	 * 创建一个Element，所有的属性值按照原始obj对象包括父类对象的public get方法生成
	 * 
	 * @param name
	 *            要生成的Element名称
	 * @param obj
	 *            原始对象
	 * @return
	 * @throws Exception
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 下午3:23:09
	 */
	public static Element createElement(String name, Object obj) {
		return createElement(name, obj, null, null);
	}

	/**
	 * 根据get或is开头的方法，获取属性的名称，第一个字母大写
	 * 
	 * @param methodName
	 *            get或is开头的属性获取方法
	 * @return 第一个字母大写的属性名称
	 */
	public static String getPropertyName(String methodName) {
		if (methodName.startsWith("get")) {
			return methodName.substring(3);
		} else if (methodName.startsWith("is")) {
			return methodName.substring(2);
		} else {
			return methodName;
		}
	}

	public static String xmlToString(Object xml) {
		OutputFormat format = new OutputFormat();
		format.setEncoding("UTF-8");
		format.setIndent(true);
		format.setNewlines(true);
		try {
			StringWriter out = new StringWriter();
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(xml);
			writer.flush();
			return out.toString();
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.ERROR,
					"IOException while generating textual "
							+ "representation: " + e.getMessage());
		}
	}

	public static Long getLong(Element e, String attributeName) {
		String value = e.attributeValue(attributeName);
		if (null == value || value.isEmpty()) {
			return null;
		} else {
			try {
				return Long.valueOf(value);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Value[" + value + "] for " + e.getPath()
								+ " is invalid !");
			}
		}
	}

	public static Integer getInteger(Element e, String attributeName) {
		String value = e.attributeValue(attributeName);
		if (null == value || value.isEmpty()) {
			return null;
		} else {
			try {
				return Integer.valueOf(value);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Value[" + value + "] for " + e.getPath()
								+ " is invalid !");
			}
		}
	}

	public static Date getDate(Element e, String attributeName) {
		String value = e.attributeValue(attributeName);
		if (null == value || value.isEmpty()) {
			return null;
		} else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				return sdf.parse(value);
			} catch (ParseException e1) {
				e1.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Value[" + value + "] for " + e.getPath()
								+ " is invalid !");
			}
		}
	}

	public static Double getDouble(Element e, String attributeName) {
		String value = e.attributeValue(attributeName);
		if (null == value || value.isEmpty()) {
			return null;
		} else {
			try {
				return Double.valueOf(value);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Value[" + value + "] for " + e.getPath()
								+ " is invalid !");
			}
		}
	}

	public static Node stringToDetachNode(String text) {
		try {
			Document doc = DocumentHelper.parseText(text);
			Element root = doc.getRootElement();
			return root.detach();
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR, text
					+ " is not a valid xml content !");
		}
	}
}
