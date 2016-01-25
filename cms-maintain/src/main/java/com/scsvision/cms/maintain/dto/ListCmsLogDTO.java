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
public class ListCmsLogDTO extends BaseDTO {
	private List<CmsLogVO> list;

	public List<CmsLogVO> getList() {
		return list;
	}

	public void setList(List<CmsLogVO> list) {
		this.list = list;
	}

	public class CmsLogVO {
		private String name;
		private String sendTime;
		private String text;
		private String stype;
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

		public String getStype() {
			return stype;
		}

		public void setStype(String stype) {
			this.stype = stype;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

	}
}
