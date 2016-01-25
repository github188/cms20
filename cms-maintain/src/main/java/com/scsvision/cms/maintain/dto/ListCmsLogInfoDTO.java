/**
 * 
 */
package com.scsvision.cms.maintain.dto;

import java.util.List;

import com.scsvision.cms.response.BaseDTO;

/**
 * @author wangbinyu
 *
 */
public class ListCmsLogInfoDTO extends BaseDTO {
	private List<CmsLogInfo> list;

	public List<CmsLogInfo> getList() {
		return list;
	}

	public void setList(List<CmsLogInfo> list) {
		this.list = list;
	}

	public class CmsLogInfo {
		private String name;
		private String sendTime;
		private String text;
		private String publishType;
		private String flag;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSendTime() {
			return sendTime;
		}

		public void setSendTime(String sendTime) {
			this.sendTime = sendTime;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getPublishType() {
			return publishType;
		}

		public void setPublishType(String publishType) {
			this.publishType = publishType;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}
	}
}
