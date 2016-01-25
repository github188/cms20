package com.scsvision.cms.statistic.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.openejb.api.LocalClient;

import com.scsvision.cms.statistic.manager.VdStatisticManager;
import com.scsvision.cms.util.json.JsonUtil;
import com.scsvision.database.entity.HourVd;

/**
 * VdStatisticManagerTest
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:46:39
 */
@LocalClient
@Stateless
public class VdStatisticManagerTest extends BaseStatisticTest {
	@EJB(beanName = "VdStatisticManagerImpl")
	private VdStatisticManager vdStatisticManager;

	// public void testBaseInsertHourVd() {
	// Calendar cal = Calendar.getInstance();
	//
	// List<HourVd> list = new LinkedList<HourVd>();
	// for (int i = 0; i < 1000; i++) {
	// HourVd hvd = new HourVd();
	// hvd.setFlux(RandomUtils.nextDouble(100, 5000));
	// hvd.setHeadWayAvg(RandomUtils.nextDouble(1, 30));
	// hvd.setOccAvg(RandomUtils.nextDouble(0, 80));
	// hvd.setOrganId(4l);
	// cal.add(Calendar.HOUR_OF_DAY, -1 * i);
	// hvd.setRecTime(cal.getTime());
	// hvd.setSpeedAvg(RandomUtils.nextDouble(60, 120));
	// list.add(hvd);
	// }
	//
	// vdStatisticManager.batchInsertHourVd(list);
	// }

	public void testListHourVd() {
		List<HourVd> list = vdStatisticManager.listByHour(Arrays.asList(3l, 4l),
				new Date(1445731200000l), null, 0, 10);
		for (HourVd hvd : list) {
			System.out.println(JsonUtil.toDocument(hvd).toJson());
			System.out.println(hvd.getRecTime());
		}
	}
}
