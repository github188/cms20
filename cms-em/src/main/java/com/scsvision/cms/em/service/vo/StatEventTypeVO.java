package com.scsvision.cms.em.service.vo;

import java.io.Serializable;

/**
 * StatEventTypeVO
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:55:41
 */
public class StatEventTypeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5142339898721500945L;
	private String date;
	private int times;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
