package com.scsvision.cms.util.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.util.string.MyStringUtil;

/**
 * 简单HTTP请求解析类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-16 下午3:29:29
 */
public class SimpleRequestReader {

	private final Logger logger = LoggerFactory
			.getLogger(SimpleRequestReader.class);

	private HttpServletRequest request;

	private String[] params;

	public SimpleRequestReader(HttpServletRequest request)
			throws BusinessException {
		this.request = request;
		String encode = request.getCharacterEncoding();
		if (null == encode) {
			encode = "utf8";
		}

		try {
			InputStream in = request.getInputStream();
			InputStreamReader inReader = new InputStreamReader(in, encode);
			BufferedReader buffer = new BufferedReader(inReader);
			StringBuilder sb = new StringBuilder();
			String s = null;
			while ((s = buffer.readLine()) != null) {
				sb.append(s);
			}
			String body = sb.toString();
			if (StringUtils.isNotBlank(body)) {
				this.params = body.split("&");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		}
	}

	protected SimpleRequestReader() {

	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取字符串参数
	 * 
	 * @param param
	 *            参数名称
	 * @param nullable
	 *            是否可以为空
	 * @return String
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午3:51:34
	 */
	public String getString(String param, boolean nullable) {
		String s = MyStringUtil.getAttributeValue(params, param);
		if (!nullable) {
			if (StringUtils.isBlank(s)) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
		}
		if (null != s) {
			try {
				return URLDecoder.decode(s, "utf8");
			} catch (UnsupportedEncodingException e) {

			}
		}
		return s;
	}

	/**
	 * 获取Short参数
	 * 
	 * @param param
	 *            参数名称
	 * @param nullable
	 *            是否可以为空
	 * @return Short
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午3:52:40
	 */
	public Short getShort(String param, boolean nullable) {
		Short num = null;
		String s = MyStringUtil.getAttributeValue(params, param);
		if (StringUtils.isBlank(s)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
			return num;
		}
		try {
			num = Short.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + param + "[" + s + "] invalid !");
		}
		return num;
	}

	/**
	 * 获取Integer参数
	 * 
	 * @param param
	 *            参数名称
	 * @param nullable
	 *            是否可以为空
	 * @return Integer
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午3:53:51
	 */
	public Integer getInteger(String param, boolean nullable) {
		Integer num = null;
		String s = MyStringUtil.getAttributeValue(params, param);
		if (StringUtils.isBlank(s)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
			return num;
		}
		try {
			num = Integer.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + param + "[" + s + "] invalid !");
		}
		return num;
	}

	/**
	 * 获取Integer参数
	 * 
	 * @param param
	 *            参数名称
	 * @param nullable
	 *            是否可以为空
	 * @return Long
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午3:54:45
	 */
	public Long getLong(String param, boolean nullable) {
		Long num = null;
		String s = MyStringUtil.getAttributeValue(params, param);
		if (StringUtils.isBlank(s)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
			return num;
		}
		try {
			num = Long.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + param + "[" + s + "] invalid !");
		}
		return num;
	}

	public Double getDouble(String param, boolean nullable) {
		Double num = null;
		String s = MyStringUtil.getAttributeValue(params, param);
		if (StringUtils.isBlank(s)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
			return num;
		}
		try {
			num = Double.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + param + "[" + s + "] invalid !");
		}
		return num;
	}

	public Long getUserId() {
		Long userId = (Long) request.getSession().getAttribute("userId");
		if (null == userId) {
			logger.error("Request session is null, please logon first !");
		}
		return userId;
	}

	public Long getUserGroupId() {
		return (Long) request.getSession().getAttribute("userGroupId");
	}

	public String getClientIp() {
		String forwarded = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(forwarded)
				|| "unKnown".equalsIgnoreCase(forwarded)) {
			return request.getRemoteAddr();
		} else {
			return forwarded.split(",")[0];
		}
	}

	public Long getTimeByString(String param, boolean nullable) {
		Long num = null;
		String s = MyStringUtil.getAttributeValue(params, param);
		if (StringUtils.isBlank(s)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
			return num;
		}
		try {
			SimpleDateFormat mytime = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			num = mytime.parse(s).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + param + "[" + s + "] invalid !");
		}
		return num;
	}

	public List<String> getArray(String param, boolean nullable) {
		List<String> s = MyStringUtil.getAttributeValues(params, param);
		if (null == s) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
		}

		return s;
	}
}
