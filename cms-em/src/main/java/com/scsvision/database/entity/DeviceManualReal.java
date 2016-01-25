package com.scsvision.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "em_device_manual_real")
@TableGenerator(name = "deviceRealGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", pkColumnValue = "event_real_id", initialValue = 1, allocationSize = 1)
public class DeviceManualReal {
	@Id
	@GeneratedValue(generator = "deviceRealGen", strategy = GenerationType.TABLE)
	private Long id;
	//设备类型
	private Integer type;

	@Column(name = "create_time")
	private Long createTime;
	
	//设备故障发生位置
	private String location;

	//设备名称
	@Column(name = "source_name")
	private String sourceName;
	
	//填报人
	@Column(name = "user_name")
	private String dutyUserName;

	//确认时间，暂定与创建时间一样与创建
	@Column(name = "confirm_time")
	private Long confirmTime;

	// 真实机构ID
	@Column(name = "organ_id")
	private Long organId;
	
	//确认人，暂定与填报人一样
	@Column(name = "confirm_user_name")
	private String confirmUserName;

	//填报报警信息
	@Column(name = "alarm_content")
	private String alarmContent;

	//确认票号
	@Column(name = "confirm_flag")
	private Short confirmFlag;


}
