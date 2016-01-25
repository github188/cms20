/**
 * 
 */
package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author wangbinyu
 *
 */

@Entity
@Table(name = "tm_cms_publish_log")
@TableGenerator(name = "tmCmsPublishLogGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "tm_cms_publish_log_id", initialValue = 1000, allocationSize = 1)
public class TmCmsPublishLog implements Serializable {

	private static final long serialVersionUID = 3191503412431217797L;

	@Id
	@GeneratedValue(generator = "tmCmsPublishLogGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "user_name")
	private String userName;

	@Lob
	private String content;

	@Column(name = "cms_id")
	private Long cmsId;
	
	@Column(name = "cms_name")
	private String cmsName;

	private Short flag;

	@Column(name = "send_time")
	private Long sendTime;

	@Column(name = "business_id")
	private String businessId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCmsId() {
		return cmsId;
	}

	public void setCmsId(Long cmsId) {
		this.cmsId = cmsId;
	}

	public Short getFlag() {
		return flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getCmsName() {
		return cmsName;
	}

	public void setCmsName(String cmsName) {
		this.cmsName = cmsName;
	}

}
