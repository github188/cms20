package com.scsvision.sgc.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;

/**
 * HttpMethodMap
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午3:10:00
 */
public class HttpMethodMap {

	private static HttpMethodMap instance = new HttpMethodMap();

	private Map<String, String> map;

	// singleton
	private HttpMethodMap() {
		init();
	}

	public static HttpMethodMap getInstance() {
		return instance;
	}

	private void init() {
		map = new HashMap<String, String>();
		// maintain
		map.put("UserLogin", "cms-maintain-2.0/OnlineController#userLogin");
		map.put("ListOrganDevice",
				"cms-maintain-2.0/OnlineController#listOrganDeviceJson");
		map.put("ListSvOrganDevice",
				"cms-maintain-2.0/OnlineController#listSvOrganDeviceJson");
		map.put("LoginOut", "cms-maintain-2.0/OnlineController#loginOut");
		map.put("ListOnlineReal",
				"cms-maintain-2.0/OnlineController#listOnlineRealJson");
		map.put("ForceLogoff",
				"cms-maintain-2.0/OnlineController#forceLogoffJson");
		map.put("ListCmsLog", "cms-maintain-2.0/LogController#listCmsLogJson");
		map.put("ListCmsInfo", "cms-maintain-2.0/LogController#listCmsInfoJson");

		// common
		map.put("CreateOrgan", "cms-common-2.0/OrganController#createOrganJson");
		map.put("CreateUser", "cms-common-2.0/UserController#createUserJson");
		map.put("CreateUserGroup",
				"cms-common-2.0/UserController#createUserGroupJson");
		map.put("CreateServer",
				"cms-common-2.0/ServerController#createServerJson");
		map.put("DeleteUser", "cms-common-2.0/UserController#deleteUserJson");
		map.put("DeleteOrgan", "cms-common-2.0/OrganController#deleteOrganJson");
		map.put("DeleteUserGroup",
				"cms-common-2.0/UserController#deleteUserGroupJson");
		map.put("DeleteServer",
				"cms-common-2.0/ServerController#deleteServerJson");
		map.put("FileUpload", "cms-common-2.0/ResourceController#fileUpload");
		map.put("FileDelete", "cms-common-2.0/ResourceController#fileDelete");
		map.put("GetUser", "cms-common-2.0/UserController#getUserJson");
		map.put("GetUserName", "cms-common-2.0/UserController#getUserNameJson");
		map.put("GetPackage",
				"cms-common-2.0/ResourceController#getPackageJson");
		map.put("CheckUser", "cms-common-2.0/UserController#getUserTicketJson");
		map.put("ListUser", "cms-common-2.0/UserController#listUserJson");
		map.put("ListOrgan", "cms-common-2.0/OrganController#listOrganJson");
		map.put("ListUserGroup",
				"cms-common-2.0/UserController#listUserGroupJson");
		map.put("ListServer", "cms-common-2.0/ServerController#listServerJson");
		map.put("UpdateUser", "cms-common-2.0/UserController#updateUserJson");
		map.put("UpdateOrgan", "cms-common-2.0/OrganController#updateOrganJson");
		map.put("UpdateUserGroup",
				"cms-common-2.0/UserController#updateUserGroupJson");
		map.put("UpdateServer",
				"cms-common-2.0/ServerController#updateServerJson");
		map.put("CreateNode", "cms-common-2.0/OrganController#createNodeJson");
		map.put("AddRealNode", "cms-common-2.0/OrganController#addRealNodeJson");
		map.put("UpdateNode", "cms-common-2.0/OrganController#updateNodeJson");
		map.put("DeleteNode", "cms-common-2.0/OrganController#deleteNodeJson");
		map.put("TreeVirtualOrgan",
				"cms-common-2.0/OrganController#treeVirtualOrganJson");
		map.put("TreeRealOrgan",
				"cms-common-2.0/OrganController#treeRealOrganJson");

		// sv
		map.put("CreateDvr", "cms-sv-2.0/SvDeviceController#createDvrJson");
		map.put("CreatePreset",
				"cms-sv-2.0/SvDeviceController#createPresetJson");
		map.put("CreateSvPlayScheme",
				"cms-sv-2.0/PlaySchemeController#createSvPlaySchemeJson");
		map.put("DeletePreset",
				"cms-sv-2.0/SvDeviceController#deletePresetJson");
		map.put("DeleteSvPlayScheme",
				"cms-sv-2.0/PlaySchemeController#deleteSvPlaySchemeJson");
		map.put("ListPreset", "cms-sv-2.0/SvDeviceController#listPresetJson");
		map.put("ListSvOrganTree",
				"cms-sv-2.0/SvDeviceController#listSvOrganTreeJson");
		map.put("ListCameraByStakeNumber",
				"cms-sv-2.0/SvDeviceController#listCameraByStakeNumberJson");
		map.put("ListSvPlayScheme",
				"cms-sv-2.0/PlaySchemeController#listSvPlaySchemeJson");
		map.put("SaveSvMarkers",
				"cms-sv-2.0/SvDeviceController#saveSvMarkersJson");
		map.put("UpdatePreset",
				"cms-sv-2.0/SvDeviceController#updatePresetJson");
		map.put("UpdateDvr", "cms-sv-2.0/SvDeviceController#updateDvrJson");
		map.put("UpdateSvPlayScheme",
				"cms-sv-2.0/PlaySchemeController#updateSvPlaySchemeJson");
		map.put("CreateFavorite",
				"cms-sv-2.0/UserFavoriteController#createFavoriteJson");
		map.put("UpdateFavorite",
				"cms-sv-2.0/UserFavoriteController#updateFavoriteJson");
		map.put("DeleteFavorite",
				"cms-sv-2.0/UserFavoriteController#deleteFavoriteJson");
		map.put("ListFavorite",
				"cms-sv-2.0/UserFavoriteController#listFavoriteJson");
		map.put("CreateWall", "cms-sv-2.0/WallController#createWallJson");
		map.put("UpdateWall", "cms-sv-2.0/WallController#updateWallJson");
		map.put("DeleteWall", "cms-sv-2.0/WallController#deleteWallJson");
		map.put("ListWall", "cms-sv-2.0/WallController#listWallJson");
		map.put("CreateMonitor", "cms-sv-2.0/WallController#createMonitorJson");
		map.put("UpdateMonitor", "cms-sv-2.0/WallController#updateMonitorJson");
		map.put("DeleteMonitor", "cms-sv-2.0/WallController#deleteMonitorJson");
		map.put("ListMonitor", "cms-sv-2.0/WallController#listMonitorJson");
		map.put("CreateWallScheme",
				"cms-sv-2.0/WallController#createWallSchemeJson");
		map.put("UpdateWallScheme",
				"cms-sv-2.0/WallController#updateWallSchemeJson");
		map.put("DeleteWallScheme",
				"cms-sv-2.0/WallController#deleteWallSchemeJson");
		map.put("ListWallScheme",
				"cms-sv-2.0/WallController#listWallSchemeJson");
		map.put("ListWallMonitor",
				"cms-sv-2.0/WallController#listWallMonitorJson");
		map.put("EditWallLayout",
				"cms-sv-2.0/WallController#editWallLayoutJson");

		// gather
		map.put("GetTmDeviceGather",
				"cms-gather-2.0/GatherController#getGatherEntityJson");
		map.put("ListTodayVdRoad",
				"cms-gather-2.0/GatherVdController#listByOrganRoadJson");
		map.put("GetTmVdGather",
				"cms-gather-2.0/GatherVdController#listByDateJson");
		map.put("ListDayavgWstOfWeek",
				"cms-gather-2.0/GatherWstController#listDayavgWstOfWeekJson");
		map.put("ListDeviceReal",
				"cms-gather-2.0/GatherController#listDeviceRealJson");
		map.put("ListLatestCovi",
				"cms-gather-2.0/GatherCoviController#listLatestCoviJson");
		map.put("ListFiveHoursCovi",
				"cms-gather-2.0/GatherCoviController#listFiveHoursCoviJson");

		// tm
		map.put("DeleteTmCmsPublish",
				"cms-tm-2.0/TmVmsController#deleteTmCmsPublishJson");
		map.put("GetMapCenter", "cms-tm-2.0/TmMapController#getMapCenterJson");
		map.put("GetCmsPublishDetail",
				"cms-tm-2.0/TmVmsController#getCmsPublishDetailJson");
		map.put("ListDevice", "cms-tm-2.0/TmDeviceController#listDeviceJson");
		map.put("ListCmsPublish",
				"cms-tm-2.0/TmVmsController#listCmsPublishJson");
		map.put("SaveMapCenter", "cms-tm-2.0/TmMapController#saveMapCenterJson");
		map.put("SaveTmMarkers",
				"cms-tm-2.0/TmDeviceController#saveTmMarkersJson");
		map.put("SaveTmCmsPublish",
				"cms-tm-2.0/TmVmsController#saveTmCmsPublishJson");
		map.put("UpdateTmCmsPublish",
				"cms-tm-2.0/TmVmsController#updateTmCmsPublishJson");
		map.put("CreateTmDevice",
				"cms-tm-2.0/TmDeviceController#createTmDeviceJson");
		map.put("UpdateTmDevice",
				"cms-tm-2.0/TmDeviceController#updateTmDeviceJson");
		map.put("DeleteTmDeviceById",
				"cms-tm-2.0/TmDeviceController#deleteTmDeviceByIdJson");
		map.put("ListTmDevice",
				"cms-tm-2.0/TmDeviceController#listTmDeviceJson");
		map.put("CreatePlayList",
				"cms-tm-2.0/TmVmsController#createPlayListJson");
		map.put("UpdatePlayList",
				"cms-tm-2.0/TmVmsController#updatePlayListJson");
		map.put("DeletePlayList",
				"cms-tm-2.0/TmVmsController#deletePlayListJson");
		map.put("GetPlayList", "cms-tm-2.0/TmVmsController#getPlayListJson");
		map.put("ListCMSPubItem",
				"cms-tm-2.0/TmVmsController#listCMSPubItemJson");
		map.put("ListCMS", "cms-tm-2.0/TmVmsController#listCMSJson");
		map.put("ListStype", "cms-tm-2.0/TmVmsController#listStypeJson");
		map.put("SaveMarkers", "cms-tm-2.0/TmDeviceController#saveMarkersJson");
		map.put("UpdateLayoutOfDevices",
				"cms-tm-2.0/TmDeviceController#updateLayoutOfDevicesJson");

		// em
		map.put("CountEventAndAlarm",
				"cms-em-2.0/EventController#countEventAndAlarmJson");
		map.put("GetEventRealDetail",
				"cms-em-2.0/EventController#getEventRealDetailJson");
		map.put("ListHistoryAlarm",
				"cms-em-2.0/EventController#listHistoryAlarmJson");
		map.put("SaveSimpleEvent",
				"cms-em-2.0/EventController#saveSimpleEventJson");
		map.put("UpdateSimpleEvent",
				"cms-em-2.0/EventController#updateSimpleEventJson");
		map.put("ListSimpleEvent",
				"cms-em-2.0/EventController#listSimpleEventJson");
		map.put("ListEventManualReal",
				"cms-em-2.0/EventController#ListEventManualRealJson");
		map.put("ListMechanicalFault",
				"cms-em-2.0/EventController#ListMechanicalFaultRealJson");
		map.put("ListDeviceAlarmReal",
				"cms-em-2.0/EventController#listDeviceAlarmRealJson");
		map.put("DeviceAlarmRealConfirm",
				"cms-em-2.0/EventController#deviceAlarmRealConfirmJson");
		map.put("ListEventThresHoldAlarmReal",
				"cms-em-2.0/EventController#listThresholdAlarmRealJson");
		map.put("SaveEventReal", "cms-em-2.0/EventController#saveEventRealJson");
		map.put("UpdateEventReal",
				"cms-em-2.0/EventController#updateEventRealJson");
		map.put("DeleteEventResource",
				"cms-em-2.0/ResourceController#deleteResourceJson");
		map.put("SaveEvent", "cms-em-2.0/EventController#saveEventJson");

		map.put("DeleteResource",
				"cms-em-2.0/ResourceController#deleteResource");
		map.put("ListTunnelBright",
				"cms-em-2.0/EventController#listTunnelBrightJson");
		map.put("ListEventOrganInfo",
				"cms-em-2.0/EventController#listEventOrganInfoJson");
		map.put("GetDeviceAlarmDetail",
				"cms-em-2.0/EventController#getDeviceAlarmDetailJson");

		// duty
		map.put("CreateDutyUser",
				"cms-duty-2.0/DutyController#createDutyUserJson");
		map.put("DutyLogin", "cms-duty-2.0/DutyController#dutyLoginJson");
		map.put("DeleteDutyUser",
				"cms-duty-2.0/DutyController#deleteDutyUserJson");
		map.put("ListDutyUser", "cms-duty-2.0/DutyController#listDutyUserJson");
		map.put("UpdateDutyUser",
				"cms-duty-2.0/DutyController#updateDutyUserJson");
		map.put("SaveDeviceFaultRecord",
				"cms-duty-2.0/DeviceFaultRecordController#saveDeviceFaultRecordJson");
		map.put("UpdateDeviceFaultRecord",
				"cms-duty-2.0/DeviceFaultRecordController#updateDeviceFaultRecordJson");
		map.put("ListDeviceFaultRecord",
				"cms-duty-2.0/DeviceFaultRecordController#listDeviceFaultRecordJson");
		map.put("CountByTypeDeviceFaultRecord",
				"cms-duty-2.0/DeviceFaultRecordController#countByTypeDeviceFaultRecordJson");

		// statistic
		map.put("ListDeviceStatus",
				"cms-maintain-2.0/OnlineController#listDeviceStatusJson");
		map.put("ListCameraStatus",
				"cms-maintain-2.0/OnlineController#listCameraStatusJson");
		map.put("GetCameraStatus",
				"cms-maintain-2.0/OnlineController#getCameraStatusJson");

	}

	/**
	 * @param 接口方法
	 * 
	 * @return 将要调用的接口数组
	 */
	public String[] getBean(String method) {
		String value = map.get(method);
		if (StringUtils.isBlank(value)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"Method[" + method + "] not supported !");
		}
		String[] result = value.split(";");
		// result[0] = "java:global/cms/" + result[0];
		return result;
	}
}
