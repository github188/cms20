/**
 * 
 */
package com.scsvision.cms.tm.service.vo;

import javax.persistence.Column;

/**
 * @author wangbinyu
 *
 */
public class VmsInfoVO {
	private Long id;

	private String content;

	private Integer font;

	private String color;

	private Integer duration;

	private Integer space;

	private Integer playSize;

	private Integer rowSize;

	private Integer marginLeft;

	private Integer marginTop;

	private String backgroundColor;

	private String StandardNumber;

	private String width;

	private String hight;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFont() {
		return font;
	}

	public void setFont(Integer font) {
		this.font = font;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getSpace() {
		return space;
	}

	public void setSpace(Integer space) {
		this.space = space;
	}

	public Integer getPlaySize() {
		return playSize;
	}

	public void setPlaySize(Integer playSize) {
		this.playSize = playSize;
	}

	public Integer getRowSize() {
		return rowSize;
	}

	public void setRowSize(Integer rowSize) {
		this.rowSize = rowSize;
	}

	public Integer getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(Integer marginLeft) {
		this.marginLeft = marginLeft;
	}

	public Integer getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(Integer marginTop) {
		this.marginTop = marginTop;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getStandardNumber() {
		return StandardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		StandardNumber = standardNumber;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHight() {
		return hight;
	}

	public void setHight(String hight) {
		this.hight = hight;
	}
}
