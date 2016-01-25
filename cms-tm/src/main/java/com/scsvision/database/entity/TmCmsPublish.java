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
 * TmCmsPublish
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午2:36:01
 */
@Entity
@Table(name = "tm_cms_publish")
@TableGenerator(name = "tmCmsPublishGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "tm_cms_publish_id", initialValue = 100, allocationSize = 1)
public class TmCmsPublish implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2595614092564036129L;

	@Id
	@GeneratedValue(generator = "tmCmsPublishGen", strategy = GenerationType.TABLE)
	private Long id;

	private String content;

	private Integer font;

	private String color;

	private Integer duration;

	private Integer space;

	@Column(name = "play_size")
	private Integer playSize;

	@Column(name = "row_size")
	private Integer rowSize;

	@Column(name = "margin_left")
	private Integer marginLeft;

	@Column(name = "margin_top")
	private Integer marginTop;

	@Column(name = "background_color")
	private String backgroundColor;

	@Column(name = "playlist_id")
	private Long playlistId;

	@Column(name = "type")
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPlaySize() {
		return playSize;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setPlaySize(Integer playSize) {
		this.playSize = playSize;
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

	public Long getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(Long playlistId) {
		this.playlistId = playlistId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
