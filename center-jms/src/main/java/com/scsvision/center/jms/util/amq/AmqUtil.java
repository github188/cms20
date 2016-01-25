package com.scsvision.center.jms.util.amq;

import java.util.Enumeration;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.util.file.Configuration;

/**
 * AMQ发送消息工具
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午1:48:58
 */
@Startup
@Singleton
@LocalBean
public class AmqUtil {

	private final Logger logger = LoggerFactory.getLogger(AmqUtil.class);

	@Resource(mappedName = "java:/AmqConnectionFactory")
	private ConnectionFactory connectionFactory;

	private PooledConnectionFactory pool = null;

	/**
	 * 发送消息到指定的Queue
	 * 
	 * @param body
	 *            消息内容
	 * @param queue
	 *            Queue名称
	 * @param method
	 *            方法名称
	 * @param businessId
	 *            业务ID
	 * @param to
	 *            最终目标SN
	 * @param source
	 *            源发起者SN
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:03:27
	 */
	public void sendToQueue(String body, String queue, String method,
			String businessId, String dest, String source) {
		Session session = null;
		Connection connection = null;
		try {
			connection = pool.createConnection();
			connection.start();
			// Create a Session
			session = connection.createSession(false,
					Session.CLIENT_ACKNOWLEDGE);

			Destination destination = session.createQueue(queue);
			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);

			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			// Create a message
			TextMessage textMessage = session.createTextMessage(body);

			textMessage.setStringProperty(Header.METHOD, method);
			textMessage.setStringProperty(Header.BUSINESSID, businessId);
			textMessage.setStringProperty(Header.FROM, Configuration
					.getInstance().loadProperties("standardNumber"));
			textMessage.setStringProperty(Header.DEST, dest);
			textMessage.setStringProperty(Header.SOURCE, source);
			textMessage.setJMSType(Header.TEXT);
			textMessage.setJMSCorrelationID(Configuration.getInstance()
					.loadProperties("JMS.correlationID"));

			// Tell the producer to send the message
			producer.send(textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			// Clean up
			try {
				if (session != null)
					session.close();
				if (connection != null) {
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送消息到指定的Topic
	 * 
	 * @param body
	 *            消息内容
	 * @param topic
	 *            Topic名称
	 * @param method
	 *            方法名称
	 * @param businessId
	 *            业务ID
	 * @param to
	 *            最终目标SN
	 * @param source
	 *            源发起者SN
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午2:11:46
	 */
	public void sendToTopic(String body, String topic, String method,
			String businessId, String dest, String source) {
		Session session = null;
		Connection connection = null;
		try {
			connection = pool.createConnection();
			connection.start();
			// Create a Session
			session = connection.createSession(false,
					Session.CLIENT_ACKNOWLEDGE);

			Destination destination = session.createTopic(topic);
			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);

			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			// Create a message
			TextMessage textMessage = session.createTextMessage(body);

			textMessage.setStringProperty(Header.METHOD, method);
			textMessage.setStringProperty(Header.BUSINESSID, businessId);
			textMessage.setStringProperty(Header.FROM, Configuration
					.getInstance().loadProperties("standardNumber"));
			textMessage.setStringProperty(Header.DEST, dest);
			textMessage.setStringProperty(Header.SOURCE, source);
			textMessage.setJMSType(Header.TEXT);
			textMessage.setJMSCorrelationID(Configuration.getInstance()
					.loadProperties("JMS.correlationID"));

			// Tell the producer to send the message
			producer.send(textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			// Clean up
			try {
				if (session != null)
					session.close();
				if (connection != null) {
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendTextResponse(Message message, Destination responseQueue,
			String response, String code) {
		Session session = null;
		Connection connection = null;
		try {
			connection = pool.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false,
					Session.CLIENT_ACKNOWLEDGE);

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(responseQueue);

			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Create a message
			TextMessage resMessage = session.createTextMessage(response);
			Enumeration<String> headers = message.getPropertyNames();
			while (headers.hasMoreElements()) {
				String header = headers.nextElement();
				resMessage.setObjectProperty(header,
						message.getObjectProperty(header));
			}
			resMessage.setStringProperty("Type", Header.TEXT);
			resMessage.setJMSType(Header.TEXT);
			String jmsCorrelationID = message.getJMSCorrelationID();
			resMessage.setJMSCorrelationID(jmsCorrelationID);
			resMessage.setStringProperty(Header.CODE, code);

			// Tell the producer to send the message
			producer.send(resMessage);
			if (logger.isDebugEnabled()) {
				logger.debug("JMSCorrelationID["
						+ message.getJMSCorrelationID()
						+ "] response body is: " + System.lineSeparator()
						+ response);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			// Clean up
			try {
				if (session != null)
					session.close();
				if (connection != null)
					connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 验证发送参数
	 * 
	 * @param body
	 *            内容
	 * @param dest
	 *            目的队列
	 * @param method
	 *            方法名
	 * @param businessId
	 *            业务ID
	 * @param from
	 *            中转代理
	 * @param to
	 *            终点
	 * @param source
	 *            初始发起方
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午4:06:34
	 */
	public boolean validateSend(Map<String, String> map) {
		if (StringUtils.isBlank(map.get(Header.BODY))) {
			return false;
		}
		if (StringUtils.isBlank(map.get(Header.DEST))) {
			return false;
		}
		if (StringUtils.isBlank(map.get(Header.METHOD))) {
			return false;
		}
		return true;
	}

	@PostConstruct
	private void initPool() {
		pool = new PooledConnectionFactory();
		pool.setConnectionFactory(connectionFactory);
	}

	@PreDestroy
	private void destory() {
		if (logger.isDebugEnabled()) {
			logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!AmqUtil destoried !!");
		}
		if (null != pool) {
			pool.clear();
			pool.stop();
		}
	}
}
