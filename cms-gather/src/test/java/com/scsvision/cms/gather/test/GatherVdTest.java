/**
 * 
 */
package com.scsvision.cms.gather.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.openejb.api.LocalClient;

import com.scsvision.cms.gather.dao.GatherCoviDAO;
import com.scsvision.cms.gather.dao.GatherVdDAO;
import com.scsvision.cms.gather.manager.GatherVdManager;
import com.scsvision.database.entity.GatherCovi;
import com.scsvision.database.entity.GatherVd;

/**
 * @author wangbinyu
 *
 */
@LocalClient
@Stateless
public class GatherVdTest extends BaseTest {
	@EJB(beanName = "GatherVdManagerImpl")
	private GatherVdManager gatherVdManager;

	@EJB(beanName = "GatherVdDAOImpl")
	private GatherVdDAO gatherVdDAO;
	
	@EJB(beanName = "GatherCoviDAOImpl")
	private GatherCoviDAO gatherCoviDAO;

//	public void testInsertVd() {
//		List<GatherCovi> list = new LinkedList<GatherCovi>();
//		for (int i = 0; i < 10; i++) {
//			// for (long n = 1; n < 5; n++) {
//			GatherCovi cv = new GatherCovi();
//			cv.setDeviceId(7l);
//			cv.setCo((double) 20);
//			cv.setVi((double) 20);
//			cv.setStatus(Integer.valueOf("0"));
//			cv.setRecTime(new Date(System.currentTimeMillis() + i * 1800000));
//			list.add(cv);
//			// }
//		}
//		gatherCoviDAO.batchInsert(list);
//	}
	
	public void testGetByTime() throws ParseException{

		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = sdf.parse("2015-11-17 15:03:56");
		Date endTime = new Date(beginTime.getTime()+540);
		List<GatherVd> list = gatherVdDAO.list(14l, beginTime, endTime);
		System.out.println();
		
	}
	
	public void testInsertVd() {
		List<GatherVd> list = new LinkedList<GatherVd>();
		for (int i = 0; i < 6040; i++) {
			GatherVd vd = new GatherVd();
			double lane1Fluxs = ((double) (20 + new Random().nextInt(20)));
			vd.setLane1Fluxs(lane1Fluxs);
			double lane1Fluxm = ((double) (10 + new Random().nextInt(10)));
			vd.setLane1Fluxm(lane1Fluxm);
			double lane1Fluxb = ((double) (5 + new Random().nextInt(5)));
			double lane1Fluxms = ((double) (5 + new Random().nextInt(5)));
			vd.setLane1Fluxb(lane1Fluxb);
			vd.setLane1Flux(lane1Fluxs+lane1Fluxm+lane1Fluxb);
			
			double lane2Fluxs = ((double) (20 + new Random().nextInt(20)));
			vd.setLane2Fluxs(lane2Fluxs);
			double lane2Fluxm = ((double) (10 + new Random().nextInt(10)));
			vd.setLane2Fluxm(lane2Fluxm);
			double lane2Fluxb = ((double) (5 + new Random().nextInt(5)));
			vd.setLane2Fluxb(lane2Fluxb);
			vd.setLane2Flux(lane2Fluxs+lane2Fluxm+lane2Fluxb);
			
			double lane3Fluxs = ((double) (20 + new Random().nextInt(20)));
			vd.setLane3Fluxs(lane3Fluxs);
			double lane3Fluxm = ((double) (10 + new Random().nextInt(10)));
			vd.setLane3Fluxm(lane3Fluxm);
			double lane3Fluxb = ((double) (5 + new Random().nextInt(5)));
			vd.setLane3Fluxb(lane3Fluxb);
			vd.setLane3Flux(lane3Fluxs+lane3Fluxm+lane3Fluxb);
			
			double lane4Fluxs = ((double) (20 + new Random().nextInt(20)));
			vd.setLane4Fluxs(lane4Fluxs);
			double lane4Fluxm = ((double) (10 + new Random().nextInt(10)));
			vd.setLane4Fluxm(lane4Fluxm);
			double lane4Fluxb = ((double) (5 + new Random().nextInt(5)));
			vd.setLane4Fluxb(lane4Fluxb);
			vd.setLane4Flux(lane4Fluxs+lane4Fluxm+lane4Fluxb);
			
			double lane5Fluxs = ((double) (20 + new Random().nextInt(20)));
			vd.setLane5Fluxs(lane5Fluxs);
			double lane5Fluxm = ((double) (10 + new Random().nextInt(10)));
			vd.setLane5Fluxm(lane5Fluxm);
			double lane5Fluxb = ((double) (5 + new Random().nextInt(5)));
			vd.setLane5Fluxb(lane5Fluxb);
			vd.setLane5Flux(lane5Fluxs+lane5Fluxm+lane5Fluxb);
			
			double lane6Fluxs = ((double) (20 + new Random().nextInt(20)));
			vd.setLane6Fluxs(lane6Fluxs);
			double lane6Fluxm = ((double) (10 + new Random().nextInt(10)));
			vd.setLane6Fluxm(lane6Fluxm);
			double lane6Fluxb = ((double) (5 + new Random().nextInt(5)));
			vd.setLane6Fluxb(lane6Fluxb);
			vd.setLane6Flux(lane6Fluxs+lane6Fluxm+lane6Fluxb);
			
			vd.setDeviceId(14l);
			vd.setStatus(0);
			vd.setCommStatus(0);
			vd.setRecTime(new Date(System.currentTimeMillis() - i * 120000));
			list.add(vd);
		}
		gatherVdDAO.batchInsert(list);
	}

}
