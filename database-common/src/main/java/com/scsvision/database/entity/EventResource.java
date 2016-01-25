package com.scsvision.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * Resource
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 上午11:54:02
 */
@Entity
@Table(name = "r_event_resource")
@TableGenerator(name = "eventResourceGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "event_resource_id", initialValue = 100, allocationSize = 1)
public class EventResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5407553735356856656L;

	@Id
	@GeneratedValue(generator = "eventResourceGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "event_id")
	private Long eventId;

	@Column(name = "resource_id")
	private Long resourceId;
	
	@Column(name = "event_real_id")
	private Long eventRealId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getEventRealId() {
		return eventRealId;
	}

	public void setEventRealId(Long eventRealId) {
		this.eventRealId = eventRealId;
	}
	
	

}
