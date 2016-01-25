package com.scsvision.cms.util.request;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;

/**
 * RequestReader
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午4:08:25
 */
public class RequestReader {

	private Document document = null;
	private Element root = null;

	public RequestReader(String message) {
		try {
			this.document = DocumentHelper.parseText(message);
			this.root = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
					e.getMessage());
		}
	}

	public Integer getInteger(String path, boolean nullable) {
		Integer num = null;
		String value = document.valueOf(path);
		if (StringUtils.isBlank(value)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + path + "]");
			}
			return num;
		}
		try {
			num = Integer.valueOf(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + path + "[" + value + "] invalid !");
		}
		return num;
	}

	public Long getLong(String path, boolean nullable) {
		Long num = null;
		String value = document.valueOf(path);
		if (StringUtils.isBlank(value)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + path + "]");
			}
			return num;
		}
		try {
			num = Long.valueOf(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + path + "[" + value + "] invalid !");
		}
		return num;
	}

	public Short getShort(String path, boolean nullable) {
		Short num = null;
		String value = document.valueOf(path);
		if (StringUtils.isBlank(value)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + path + "]");
			}
			return num;
		}
		try {
			num = Short.valueOf(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + path + "[" + value + "] invalid !");
		}
		return num;
	}

	public Double getDouble(String path, boolean nullable) {
		Double num = null;
		String value = document.valueOf(path);
		if (StringUtils.isBlank(value)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + path + "]");
			}
			return num;
		}
		try {
			num = Double.valueOf(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + path + "[" + value + "] invalid !");
		}
		return num;
	}

	public String getString(String path, boolean nullable) {
		String value = document.valueOf(path);
		if (StringUtils.isBlank(value)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + path + "]");
			}
		}
		return value;
	}

	public Date getDate(String path, boolean nullable) {
		Date time = null;
		String value = document.valueOf(path);
		if (StringUtils.isBlank(value)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + path + "]");
			}
			return time;
		}
		try {
			time = new Date(Long.parseLong(value));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + path + "[" + value + "] invalid !");
		}
		return time;
	}

	/**
	 * 获取xml节点
	 * 
	 * @param path
	 *            节点参数
	 * @return Element
	 */
	public Element getElement(String path) {
		Element e = root.element(path);
		if (null == e) {
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
					document.asXML());
		}
		return e;
	}

	/**
	 * 获取xml节点中属性 <Item Id='' />
	 * 
	 * @param e
	 *            xml节点
	 * @param path
	 *            属性名称
	 * @param nullable
	 *            是否为null
	 * @return String
	 */
	public String getAttribute(Element e, String path, boolean nullable) {
		Attribute attribute = e.attribute(path);
		if (null == attribute) {
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR, "Paramter: "
					+ path + " invalid");
		}
		String value = attribute.getValue();
		if (StringUtils.isBlank(value)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + path + "]");
			}
		}
		return value;
	}
	
	/**
	 * 获取节点下集合<Items><Item ../><Item ../><Items>
	 * 
	 * @param e
	 *            节点
	 * @return List
	 */
	public List<Element> listElement(Element e) {
		List<Element> list = e.elements();
		return list;
	}

	public Element getRoot() {
		return root;
	}
}
