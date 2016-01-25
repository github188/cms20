package com.scsvision.cms.tm.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * 
 * TmCmsPublishDTO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015年 下午3:25:16
 */

public class TmCmsPublishDTO extends BaseDTO {
	private TmCmsPublish tmCmsPublish;

	public TmCmsPublish getTmCmsPublish() {
		return tmCmsPublish;
	}

	public void setTmCmsPublish(TmCmsPublish tmCmsPublish) {
		this.tmCmsPublish = tmCmsPublish;
	}

	private List<TmCmsPublish> vms;

	public List<TmCmsPublish> getVms() {
		return vms;
	}

	public void setVms(List<TmCmsPublish> vms) {
		this.vms = vms;
	}

	public class TmCmsPublish {
		private String id;
		private String content;
		private String font;
		private String color;
		private String duration;
		private String space;
		private String playSize;
		private String rowSize;
		private String marginLeft;
		private String marginTop;
		private String backgroundColor;
		private String cmsId;
		private String type;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getFont() {
			return font;
		}

		public void setFont(String font) {
			this.font = font;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getDuration() {
			return duration;
		}

		public void setDuration(String duration) {
			this.duration = duration;
		}

		public String getSpace() {
			return space;
		}

		public void setSpace(String space) {
			this.space = space;
		}

		public String getPlaySize() {
			return playSize;
		}

		public void setPlaySize(String playSize) {
			this.playSize = playSize;
		}

		public String getRowSize() {
			return rowSize;
		}

		public void setRowSize(String rowSize) {
			this.rowSize = rowSize;
		}

		public String getMarginLeft() {
			return marginLeft;
		}

		public void setMarginLeft(String marginLeft) {
			this.marginLeft = marginLeft;
		}

		public String getMarginTop() {
			return marginTop;
		}

		public void setMarginTop(String marginTop) {
			this.marginTop = marginTop;
		}

		public String getBackgroundColor() {
			return backgroundColor;
		}

		public void setBackgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
		}

		public String getCmsId() {
			return cmsId;
		}

		public void setCmsId(String cmsId) {
			this.cmsId = cmsId;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
}
