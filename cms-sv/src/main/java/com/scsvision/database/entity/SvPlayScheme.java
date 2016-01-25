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
 * SvPlayScheme
 * 
 * @author MIKE
 *         <p />
 *         Create at 2016年1月12日 下午2:09:05
 */
@Entity
@Table(name = "sv_play_scheme")
@TableGenerator(name = "svPlaySchemeGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "sv_play_scheme_id", initialValue = 1000, allocationSize = 1)
public class SvPlayScheme implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7928343759029098055L;

	@Id
	@GeneratedValue(generator = "svPlaySchemeGen", strategy = GenerationType.TABLE)
	private Long id;
	private String name;
	@Column(name = "screen_no")
	private Integer screenNo;

	@Lob
	@Column(name = "scheme_config")
	private String schemeConfig;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScreenNo() {
		return screenNo;
	}

	public void setScreenNo(Integer screenNo) {
		this.screenNo = screenNo;
	}

	public String getSchemeConfig() {
		return schemeConfig;
	}

	public void setSchemeConfig(String schemeConfig) {
		this.schemeConfig = schemeConfig;
	}

}
