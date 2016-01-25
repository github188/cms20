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
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author wangbinyu
 *
 */
@Entity
@Table(name = "tm_playlist")
@TableGenerator(name = "tmPlayListGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "tm_playlist_id", initialValue = 1, allocationSize = 1)
public class TmPlayList implements Serializable {

	private static final long serialVersionUID = -6541590768541216998L;

	@Id
	@GeneratedValue(generator = "tmPlayListGen", strategy = GenerationType.TABLE)
	private Long id;

	private String name;

	@Column(name = "size_type")
	private String sizeType;

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

	public String getSizeType() {
		return sizeType;
	}

	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}

}
