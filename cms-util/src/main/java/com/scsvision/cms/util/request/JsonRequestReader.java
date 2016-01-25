package com.scsvision.cms.util.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;

/**
 * JsonRequestReader
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月13日 上午10:52:22
 */
public class JsonRequestReader {
	private final Logger logger = LoggerFactory
			.getLogger(JsonRequestReader.class);

	private String body;

	private JSONObject jsonParam;

	public JsonRequestReader(HttpServletRequest request) {
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
			body = sb.toString();
			if (body.isEmpty()) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"Request body not found !");
			}
			jsonParam = new JSONObject(body);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Network io exception !");
		} catch (JSONException e) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Request body is not a JSON object: " + body);
		}
	}

	protected JsonRequestReader() {

	}

	public String getBody() {
		return body;
	}

	public JSONObject getJsonParam() {
		return jsonParam;
	}

	public static String getString(JSONObject param, String key) {
		try {
			String value = param.getString(key);
			return value;
		} catch (JSONException e) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [" + key + "]");
		}
	}

	public static int getInt(JSONObject param, String key) {
		try {
			int value = param.getInt(key);
			return value;
		} catch (JSONException e) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing or invalid number for [" + key + "]");
		}
	}

	public static long getLong(JSONObject param, String key) {
		try {
			long value = param.getLong(key);
			return value;
		} catch (JSONException e) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing or invalid number for [" + key + "]");
		}
	}
}
