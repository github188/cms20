package com.scsvision.database.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * UserLog
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:03:42
 */
public class UserLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5745447716741228694L;

	private String id;

	private Long userId;

	private String userName;

	private Long userGroupId;

	private Date createTime;

	private String clientType;

	private String operationName;

	private String method;

	private Long code;

	private String reason;

	private Long targetId;

	private String targetName;

	private Long targetType;

	private String content;

	private String conditions;

	private String businessId;

	private Long resourceId;

	private Short flag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Long getTargetType() {
		return targetType;
	}

	public void setTargetType(Long targetType) {
		this.targetType = targetType;
	}

	public Short getFlag() {
		return flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}
