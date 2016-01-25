/**
 * 
 */
package com.scsvision.cms.sv.test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.openejb.api.LocalClient;

import com.scsvision.database.entity.SvDevice;
import com.scsvision.database.entity.VirtualOrgan;
import com.scsvision.database.manager.OrganManager;
import com.scsvision.database.manager.SvDeviceManager;

/**
 * @author wangbinyu
 *
 */

@LocalClient
@Stateless
public class SvDeviceTest extends BaseSvTest {
	@EJB(beanName = "OrganManagerImpl")
	private OrganManager organManager;

	@EJB(beanName = "SvDeviceManagerImpl")
	private SvDeviceManager svDeviceManager;
	
	public void testSendJms() throws JMSException{
		List<VirtualOrgan> vorgans = organManager.listRealChildOrgan(20002l);
		Map<Long, VirtualOrgan> map = new HashMap<Long, VirtualOrgan>();
		for (VirtualOrgan entity : vorgans) {
			map.put(entity.getDeviceId(), entity);
		}
		
		List<SvDevice> list = svDeviceManager.listCamera(null, null, null, null, 0, 9999);
		
		SvDeviceTest test = new SvDeviceTest();
		for (SvDevice sd : list) {
			StringBuffer sb = new StringBuffer();
			sb.append("<Request><Type>");
			sb.append(sd.getType() + sd.getSubType());
			sb.append("</Type><Name>");
			sb.append(sd.getName());
			sb.append("</Name><RealId>");
			sb.append(sd.getId());
			sb.append("</RealId><ParentId>");
			sb.append(map.get(sd.getOrganId()).getId());
			sb.append("</ParentId><UserGroupId>1</UserGroupId></Request>");
			test.setExpectedBody(sb.toString());
			test.setQUEUE_NAME("QueueCMS111");
			test.setUrl("tcp://192.168.1.7:61616");
			test.SendMessage();
		}
	}
	
	private String url;
	private String QUEUE_NAME;
	protected String expectedBody;

	public void SendMessage() throws JMSException {

		Connection connection = null;

		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					url);
			connection = connectionFactory.createConnection();

			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue(QUEUE_NAME);
			MessageProducer producer = session.createProducer(destination);
			Message message = session.createTextMessage(expectedBody);
			// message.setStringProperty("Method", "UserLogin");
			// message.setStringProperty("Method", "GetTmDeviceGather");
			message.setStringProperty("Method", "AddRealNode");
			// message.setStringProperty("Method", "ListSvOrganTree");
			// message.setStringProperty("Method", "ListEventManual");
			// message.setStringProperty("Method", "ListDeviceAlarm");
			// message.setStringProperty("Method", "ListGatherWst");
			// message.setStringProperty("Method", "ListEventManual");
			// message.setStringProperty("Method", "ListTmOrganDevice");
			message.setStringProperty("Type", "Text");
			message.setStringProperty("BusinessId", "111111111");
			message.setJMSReplyTo(session.createQueue("TestQueueCMS"));
			System.out.println(new Timestamp(System.currentTimeMillis()));
			producer.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}
	
	public String getExpectedBody() {
		return expectedBody;
	}

	public void setExpectedBody(String expectedBody) {
		this.expectedBody = expectedBody;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQUEUE_NAME() {
		return QUEUE_NAME;
	}

	public void setQUEUE_NAME(String qUEUE_NAME) {
		QUEUE_NAME = qUEUE_NAME;
	}
}
