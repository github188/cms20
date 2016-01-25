package com.scsvision.cms.constant;

/**
 * 类型常量定义
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:37:24
 */
public class TypeDefinition {

	// ================机构==================//

	/**
	 * 普通机构
	 */
	public static final int ORGAN_GENERAL = 1000;
	/**
	 * 高速公路
	 */
	public static final int ORGAN_MOTORWAY = 1100;
	/**
	 * 干线公路
	 */
	public static final int ORGAN_HIGHWAY = 1101;
	/**
	 * 路段
	 */
	public static final int ORGAN_ROAD = 1102;
	/**
	 * 隧道
	 */
	public static final int ORGAN_TUNNEL = 1200;
	/**
	 * 桥梁
	 */
	public static final int ORGAN_BRIDGE = 1300;
	/**
	 * 收费站
	 */
	public static final int ORGAN_TOLLGATE = 1400;

	// ================虚拟机构==================//
	/**
	 * 虚拟机构
	 */
	public static final int VIRTUAL_ORGAN = 2000;

	// ================vorgan是否显示==================//
	/**
	 * 显示
	 */
	public static final int VORGAN_VISIBLE_TRUE = 1;
	/**
	 * 不显示
	 */
	public static final int VORGAN_VISIBLE_FALSE = 0;

	// ================视频设备==================//

	/**
	 * DVR
	 */
	public static final int VIDEO_DVR = 3000;
	/**
	 * DVS
	 */
	public static final int VIDEO_DVS = 3001;
	/**
	 * 摄像头-枪机
	 */
	public static final int VIDEO_CAMERA_GUN = 3100;
	/**
	 * 摄像头-球机
	 */
	public static final int VIDEO_CAMERA_BALL = 3101;
	/**
	 * 摄像头-云台枪机
	 */
	public static final int VIDEO_CAMERA_PLAT = 3102;
	/**
	 * 摄像头-半球
	 */
	public static final int VIDEO_CAMERA_HALFBALL = 3103;
	/**
	 * 视频输出通道
	 */
	public static final int VIDEO_MONITOR = 3200;

	/**
	 * 默认存储计划,168个1
	 */
	public static final String STORE_PLAN_DEFAULT = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
	/**
	 * 默认前端存储计划,168个0
	 */
	public static final String LOCAL_STORE_PLAN_DEFAULT = "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

	// ================数据设备==================//
	/**
	 * 设备总数(（3100<=x<3200:摄像头，4000<=x<4100:情报板，4100<=x<4200:车检器）其余的不是区间范围,照明回路在界面上不是设备
	 */
	public static final int DEVICE_COUNT = 19;

	/**
	 * 可变标志-门架式
	 */
	public static final int VMS_DOOR = 4000;
	/**
	 * 可变标志-立柱式
	 */
	public static final int VMS_COLUMN = 4001;
	/**
	 * 可变标志-悬臂式
	 */
	public static final int VMS_CANTILEVER = 4002;
	/**
	 * 可变标志-隧道内
	 */
	public static final int VMS_TUNNEL = 4003;
	/**
	 * 车检器-微波
	 */
	public static final int VEHICLE_DETECTOR_MICRO = 4100;
	/**
	 * 车检器-线圈
	 */
	public static final int VEHICLE_DETECTOR_COIL = 4101;
	/**
	 * 车检器-视频
	 */
	public static final int VEHICLE_DETECTOR_VIDEO = 4102;
	/**
	 * 气象检测器-全
	 */
	public static final int WEATHER_DETECTOR = 4200;
	/**
	 * 气象检测器-风速风向
	 */
	public static final int WEATHER_DETECTOR_WIND = 4201;
	/**
	 * 气象检测器-路面检测
	 */
	public static final int WEATHER_DETECTOR_ROAD = 4202;
	/**
	 * 气象检测器-桥面检测
	 */
	public static final int WEATHER_DETECTOR_BRIDGE = 4203;
	/**
	 * 气象检测器-能见度
	 */
	public static final int WEATHER_DETECTOR_VI = 4204;
	/**
	 * 环境检测-COVI
	 */
	public static final int DETECTOR_COVI = 4300;
	/**
	 * 环境检测-氮氧
	 */
	public static final int DETECTOR_NO = 4301;
	/**
	 * 环境检测-光强
	 */
	public static final int DETECTOR_LOLI = 4302;
	/**
	 * 开关量-照明回路
	 */
	public static final int SWITCH_LIGHT = 4400;
	/**
	 * 开关量-照明回路-基本照明
	 */
	public static final int SWITCH_LIGHT_BASE = 4401;
	/**
	 * 开关量-照明回路-过渡段照明
	 */
	public static final int SWITCH_LIGHT_TRANSITION = 4402;
	/**
	 * 开关量-照明回路-加强照明
	 */
	public static final int SWITCH_LIGHT_ENHANCE = 4403;
	/**
	 * 开关量-照明回路-应急照明
	 */
	public static final int SWITCH_LIGHT_EMERGENCY = 4404;
	/**
	 * 开关量-照明回路-路灯
	 */
	public static final int SWITCH_LIGHT_ROAD = 4405;
	/**
	 * 开关量-风机
	 */
	public static final int SWITCH_Fan = 4406;
	/**
	 * 开关量-卷帘门
	 */
	public static final int SWITCH_ROLLING_DOOR = 4407;
	/**
	 * 开关量-栏杆机
	 */
	public static final int SWITCH_RAIL = 4408;
	/**
	 * 开关量-光纤光栅
	 */
	public static final int SWITCH_FIBER = 4409;
	/**
	 * 开关量-火灾报警按钮
	 */
	public static final int SWITCH_BUTTON = 4410;
	/**
	 * 开关量-水池水泵
	 */
	public static final int SWITCH_WATER_PUMP = 4411;
	/**
	 * GPS
	 */
	public static final int DEVICE_GPS = 4500;
	/**
	 * 车道指示灯
	 */
	public static final int VMS_LANE_INDICATOR = 4600;
	/**
	 * 交通信号灯
	 */
	public static final int VMS_TRAFFIC_SIGN = 4700;
	/**
	 * PLC
	 */
	public static final int PLC = 5000;
	/**
	 * 火灾报警平台
	 */
	public static final int FACP = 5001;

	// ================资源类型==================//
	/**
	 * 用户
	 */
	public static final int RESOURCE_USER = 9000;
	/**
	 * 图片
	 */
	public static final int RESOURCE_IMAGE = 9100;
	/**
	 * 视频
	 */
	public static final int RESOURCE_VIDEO = 9101;
	/**
	 * 语音
	 */
	public static final int RESOURCE_AUDIO = 9102;
	/**
	 * 升级包
	 */
	public static final int RESOURCE_PACKAGE = 9103;
	/**
	 * PTS
	 */
	public static final int RESOURCE_PTS = 9200;
	/**
	 * MSS
	 */
	public static final int RESOURCE_MSS = 9201;
	/**
	 * CRS
	 */
	public static final int RESOURCE_CRS = 9202;
	/**
	 * DWS
	 */
	public static final int RESOURCE_DWS = 9203;
	/**
	 * DAS
	 */
	public static final int RESOURCE_DAS = 9204;
	/**
	 * RES
	 */
	public static final int RESOURCE_RES = 9205;
	// ================事件类型==================//
	/**
	 * 报警
	 */
	public static final int EVENT_ALARM = 1;
	/**
	 * 事件
	 */
	public static final int EVENT_MANUAL = 2;
	/**
	 * 交通事故
	 */
	public static final int EVENT_TYPE_TA = 1;
	/**
	 * 自然灾害
	 */
	public static final int EVENT_TYPE_NW = 2;
	// /**
	// * 地质灾害
	// */
	// public static final int EVENT_TYPE_ED = 3;
	/**
	 * 交通控制
	 */
	public static final int EVENT_TYPE_TC = 4;
	// /**
	// * 视频检测
	// */
	// public static final int EVENT_TYPE_VD = 5;
	// /**
	// * 其他
	// */
	// public static final int EVENT_TYPE_OTHER = 6;
	/**
	 * 设备离线-全
	 */
	public static final int EVENT_TYPE_DOL = 7;
	/**
	 * 设备离线-网络故障
	 */
	public static final int EVENT_TYPE_WEB_FAULT = 0;
	/**
	 * 设备离线-设备故障
	 */
	public static final int EVENT_TYPE_DEVICE_FAULT = 1;
	/**
	 * 阀值报警
	 */
	public static final int EVENT_TYPE_VA = 8;
	/**
	 * 火灾报警
	 */
	public static final int EVENT_TYPE_FIRE = 9;

	// ================设备状态==================//
	/**
	 * 在线
	 */
	public static final String STATUS_ONLINE = "1";
	/**
	 * 离线
	 */
	public static final String STATUS_OFFLINE = "0";
	// ================离线类型==================//
	/**
	 * 正常离线
	 */
	public static final int OFFLINE_NORMAL = 0;
	/**
	 * 心跳过期
	 */
	public static final int OFFLINE_EXPIRE = 1;
	/**
	 * 管理员踢出
	 */
	public static final int OFFLINE_KICKOFF = 2;

	// ================CMS日志最新发布标志==================//

	/**
	 * 最新发布标志
	 */
	public static final long USER_LOG_TRUE = 1;

	/**
	 * 已发布标志
	 */
	public static final long USER_LOG_FALSE = 0;

	// ================事件资源标志==================//

	/**
	 * 事件关联资源
	 */
	public static final short EVENT_RESOURCE_FLAG_TRUE = 1;

	/**
	 * 事件未关联资源
	 */
	public static final short EVENT_RESOURCE_FLAG_FALSE = 0;

	// ================平台互联=====================//
	/**
	 * 本级平台资源
	 */
	public static final short LOCAL_PLATFORM_RESOURCE = 0;

	/**
	 * 下级平台资源
	 */
	public static final short SUB_PLATFORM_RESOURCE = 1;

	// ================客户端类型=====================//
	/**
	 * SGC
	 */
	public static final String CLIENT_TYPE_SGC = "SGC";
	/**
	 * OMC
	 */
	public static final String CLIENT_TYPE_OMC = "OMC";

	// ================包类型类型=====================//
	/**
	 * ios
	 */
	public static final String PACKAGE_TYPE_IOS = "ios";
	/**
	 * ocx
	 */
	public static final String PACKAGE_TYPE_OCX = "ocx";
	/**
	 * exe
	 */
	public static final String PACKAGE_TYPE_EXE = "exe";

	// ==================事件等级======================//
	/**
	 * 简单事件
	 */
	public static final int SIMPLE_EVENT = 0;
	/**
	 * 一般事件
	 */
	public static final int COMMON_EVENT = 1;
	/**
	 * 糟糕事件
	 */
	public static final int TERRIBLE_EVENT = 2;
	/**
	 * 灾难事件
	 */
	public static final int DISASTER_EVENT = 3;

	// =================确认票号====================//
	/**
	 * 未确认
	 */
	public static final short CONFIRM_FLAG_NOT = 0;
	/**
	 * 已确认
	 */
	public static final short CONFIRM_FLAG_YES = 1;
}
