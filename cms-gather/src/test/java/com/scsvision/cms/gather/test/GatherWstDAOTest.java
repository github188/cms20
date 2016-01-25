package com.scsvision.cms.gather.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang3.RandomUtils;
import org.apache.openejb.api.LocalClient;

import com.scsvision.cms.gather.dao.DeviceRealDAO;
import com.scsvision.cms.gather.dao.GatherVdDAO;
import com.scsvision.cms.gather.dao.GatherWstDAO;
import com.scsvision.cms.gather.manager.GatherVdManager;
import com.scsvision.database.entity.GatherWst;

/**
 * GatherWstDAOTest
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:09:31
 */
@LocalClient
@Stateless
public class GatherWstDAOTest extends BaseTest {
	@EJB(beanName = "GatherWstDAOImpl")
	private GatherWstDAO gatherWstDAO;

	@EJB(beanName = "GatherVdManagerImpl")
	private GatherVdManager gatherVdManager;

	@EJB(beanName = "GatherVdDAOImpl")
	private GatherVdDAO gatherVdDAO;

	@EJB(beanName = "DeviceRealDAOImpl")
	private DeviceRealDAO deviceRealDAO;

	// public void testBatchInsert() {
	// List<GatherWst> list = new LinkedList<GatherWst>();
	// for (int i = 0; i < 10000; i++) {
	// GatherWst wst = new GatherWst();
	// wst.setAirTemp(RandomUtils.nextDouble(0.0f, 40.0f));
	// wst.setCommStatus(Integer.valueOf("0"));
	// wst.setDeviceId(100001l);
	// wst.setDirection(RandomUtils.nextInt(1, 8) + "");
	// wst.setHumi(RandomUtils.nextDouble(0f, 100f));
	// wst.setRainVol(RandomUtils.nextDouble(0f, 50f));
	// wst.setRecTime(new Date(System.currentTimeMillis() - i * 120000));
	// wst.setRoadSurface(Integer.valueOf("2"));
	// wst.setRoadTemp(RandomUtils.nextDouble(0.0f, 50.0f));
	// wst.setSnowVol(RandomUtils.nextDouble(0f, 50f));
	// wst.setStatus(Integer.valueOf("0"));
	// wst.setVi(RandomUtils.nextDouble(50f, 1000f));
	// wst.setWs(RandomUtils.nextDouble(0f, 10f));
	// list.add(wst);
	// }
	// gatherWstDAO.batchInsert(list);
	// }

	// public void testBatchInsertDoc() {
	// List<Document> list = new LinkedList<Document>();
	// for (int i = 0; i < 10000; i++) {
	// Document wst = new Document();
	// wst.append("airTemp", RandomUtils.nextFloat(0.0f, 40.0f))
	// .append("commStatus", Short.valueOf("0"))
	// .append("deviceId", Long.valueOf(100001))
	// .append("direction", RandomUtils.nextInt(1, 8) + "")
	// .append("humi", RandomUtils.nextFloat(0f, 100f))
	// .append("rainVol", RandomUtils.nextFloat(0f, 50f))
	// .append("recTime",
	// new Date(System.currentTimeMillis() - i * 120000))
	// .append("roadSurface", Short.valueOf("2"))
	// .append("roadTemp", RandomUtils.nextFloat(0.0f, 50.0f))
	// .append("snowVol", RandomUtils.nextFloat(0f, 50f))
	// .append("status", Short.valueOf("0"))
	// .append("vi", RandomUtils.nextFloat(50f, 1000f))
	// .append("ws", RandomUtils.nextFloat(0f, 10f));
	// list.add(wst);
	// }
	// gatherWstDAO.batchInsertDoc(list);
	// }

	// public void testList() {
	// List<GatherWst> list = gatherWstDAO.list(100001l, 10, 10);
	// for (GatherWst wst : list) {
	// System.out.println(JsonUtil.toDocument(wst).toJson());
	// }
	// }
	//
	// public void testInsertVd() {
	// List<GatherVd> list = new LinkedList<GatherVd>();
	// for (int i = 0; i < 10000; i++) {
	// for (long n = 3; n < 8; n++) {
	// GatherVd vd = new GatherVd();
	// vd.setLane1Flux((double) (50 + new Random().nextInt(50)));
	// vd.setLane2Flux((double) (50 + new Random().nextInt(60)));
	// vd.setDeviceId(n);
	// vd.setLane3Flux((double) (50 + new Random().nextInt(70)));
	// vd.setLane4Flux((double) (50 + new Random().nextInt(80)));
	// vd.setLane5Flux((double) (50 + new Random().nextInt(90)));
	// vd.setLane6Flux((double) (50 + new Random().nextInt(100)));
	// vd.setStatus(Integer.valueOf("0"));
	// vd.setRecTime(new Date(System.currentTimeMillis() - i * 120000));
	// list.add(vd);
	// }
	// }
	// gatherVdDAO.batchInsert(list);
	// }

	public void testSaveMany() {
		List<GatherWst> list = new LinkedList<GatherWst>();
		for (int i = 0; i < 10000; i++) {
			GatherWst wst = new GatherWst();
			wst.setAirTemp(RandomUtils.nextDouble(0.0f, 40.0f));
			wst.setCommStatus(Integer.valueOf("0"));
			wst.setDeviceId(100001l);
			wst.setDirection(RandomUtils.nextInt(1, 8) + "");
			wst.setHumi(RandomUtils.nextDouble(0f, 100f));
			wst.setRainVol(RandomUtils.nextDouble(0f, 50f));
			wst.setRecTime(new Date(System.currentTimeMillis() - i * 120000));
			wst.setRoadSurface(Integer.valueOf("2"));
			wst.setRoadTemp(RandomUtils.nextDouble(0.0f, 50.0f));
			wst.setSnowVol(RandomUtils.nextDouble(0f, 50f));
			wst.setStatus(Integer.valueOf("0"));
			wst.setVi(RandomUtils.nextDouble(50f, 1000f));
			wst.setWs(RandomUtils.nextDouble(0f, 10f));
			list.add(wst);
		}
		deviceRealDAO.saveMany(list);
	}

	// public void testListVd() {
	// gatherVdManager.listByOrganRoad(new Date(
	// System.currentTimeMillis() - 86400000),
	// new Date(System.currentTimeMillis()), 1l);
	// }
}
