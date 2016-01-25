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
@Table(name = "sv_wall_scheme_item")
@TableGenerator(name = "svWallSchemeItemGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "sv_wall_scheme_item_id", initialValue = 1, allocationSize = 1)
public class SvWallSchemeItem implements Serializable{
	
	private static final long serialVersionUID = -7646797789031966653L;

	@Id
	@GeneratedValue(generator = "svWallSchemeItemGen", strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "wall_scheme_id")
	private Long wallSchemeId;

	@Column(name = "monitor_id")
	private Long monitorId;

	@Lob
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWallSchemeId() {
		return wallSchemeId;
	}

	public void setWallSchemeId(Long wallSchemeId) {
		this.wallSchemeId = wallSchemeId;
	}

	public Long getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(Long monitorId) {
		this.monitorId = monitorId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
