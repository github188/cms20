package com.scsvision.cms.response;

/**
 * BaseDTO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午6:13:18
 */
public class BaseDTO {
	private String method;
	private String code = "200";
	private String message = "";

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
