/**
 * 
 */
package com.scsvision.center.jms.mdb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.center.jms.MethodMap;
import com.scsvision.center.jms.util.amq.AmqUtil;
import com.scsvision.cms.constant.Header;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.util.xml.XmlUtil;

/**
 * @author MIKE
 *
 */
@TransactionManagement(TransactionManagementType.BEAN)
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "QueueCMS") })
public class QueueCmsListener implements MessageListener {

	private final Logger logger = LoggerFactory
			.getLogger(QueueCmsListener.class);

	@Resource
	private MessageDrivenContext context;

	@EJB(beanName = "AmqUtil")
	private AmqUtil amqUtil;

	public void onMessage(Message message) {
		String method = null;
		Destination responseQueue = null;
		Object result = null;
		String jmsCorrelationID = null;
		String code = ErrorCode.SUCCESS;
		String businessId = null;

		try {
			message.acknowledge();
			method = message.getStringProperty(Header.METHOD);
			String type = message.getJMSType();
			if (null == type || type.isEmpty()) {
				type = message.getStringProperty("Type");
			}
			responseQueue = message.getJMSReplyTo();
			if (null == type) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"JMS Type could not be empty !");
			}
			jmsCorrelationID = message.getJMSCorrelationID();
			if (null == responseQueue) {
				logger.info("message correlationID[" + jmsCorrelationID
						+ "] reply to is empty !");
			}
			businessId = message.getStringProperty(Header.BUSINESSID);
			if (null == businessId || businessId.isEmpty()) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"BusinessId could not be empty !");
			}

			Object body = null;

			switch (type) {
			case Header.TEXT:
				body = ((TextMessage) message).getText();
				if (logger.isDebugEnabled()) {
					logger.debug("JMSCorrelationID[" + jmsCorrelationID
							+ "] request body is: " + System.lineSeparator()
							+ (String) body);
				}
				break;
			case Header.BYTE:
				BytesMessage bytesmessage = (BytesMessage) message;
				long length = bytesmessage.getBodyLength();
				byte[] data = new byte[(int) length];
				int count = 0;
				int offset = 0;
				while ((count = bytesmessage.readBytes(data, offset)) > 0) {
					offset += count;
				}
				body = new ObjectInputStream(new ByteArrayInputStream(data));
				break;
			default:
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"JMS Header Type[" + type + "] is not supported !");
			}

			if (null == body) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"Request body is null !");
			}

			String[] bean = MethodMap.getInstance().getBean(method);
			Object controller = context.lookup(bean[0]);
			// context.getUserTransaction().begin();
			if (bean.length == 2) {
				result = controller.getClass()
						.getMethod(bean[1], body.getClass())
						.invoke(controller, body);
			} else if (bean.length == 3) {
				result = controller.getClass()
						.getMethod(bean[1], body.getClass(), String.class)
						.invoke(controller, body, businessId);
			}
			// context.getUserTransaction().commit();
		} catch (JMSException e) {
			e.printStackTrace();
			result = XmlUtil.xmlToString(XmlUtil.generateResponse(method,
					ErrorCode.AMQ_CONNECTION_ERROR, e.getMessage()));
			code = ErrorCode.AMQ_CONNECTION_ERROR;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result = XmlUtil.xmlToString(XmlUtil.generateResponse(method,
					ErrorCode.JNDI_FIND_ERROR, e.getMessage()));
			code = ErrorCode.JNDI_FIND_ERROR;
		} catch (InvocationTargetException e) {
			Throwable target = e.getTargetException();
			Throwable t = target.getCause();
			if (null == t) {
				t = target;
			}
			if (t instanceof BusinessException) {
				t.printStackTrace();
				BusinessException be = (BusinessException) t;
				result = XmlUtil.xmlToString(XmlUtil.generateResponse(method,
						be.getCode(), be.getMessage()));
				code = be.getCode();
			} else {
				result = XmlUtil.xmlToString(XmlUtil.generateResponse(method,
						ErrorCode.ERROR, e.getMessage()));
				code = ErrorCode.ERROR;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			result = XmlUtil.xmlToString(XmlUtil.generateResponse(method,
					e.getCode(), e.getMessage()));
			code = e.getCode();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			result = XmlUtil.xmlToString(XmlUtil.generateResponse(method,
					ErrorCode.ERROR, e.getMessage()));
			code = ErrorCode.ERROR;
		} catch (IOException e) {
			e.printStackTrace();
			result = XmlUtil.xmlToString(XmlUtil.generateResponse(method,
					ErrorCode.AMQ_CONNECTION_ERROR, e.getMessage()));
			code = ErrorCode.AMQ_CONNECTION_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			result = XmlUtil.xmlToString(XmlUtil.generateResponse(method,
					ErrorCode.ERROR, e.getMessage()));
			code = ErrorCode.ERROR;
		} finally {
			// send response
			if (null != responseQueue) {
				if (result instanceof Map) {
					Map<String, String> map = (Map<String, String>) result;
					// 返回
					amqUtil.sendTextResponse(message, responseQueue,
							map.get("response"), code);
					// 发送到新的模块处理
					if (amqUtil.validateSend(map)) {
						amqUtil.sendToQueue(map.get(Header.BODY),
								map.get(Header.ADDRESS),
								map.get(Header.METHOD),
								map.get(Header.BUSINESSID),
								map.get(Header.DEST), map.get(Header.SOURCE));
					}
				} else {
					amqUtil.sendTextResponse(message, responseQueue,
							(String) result, code);
				}
			}
		}
	}

}
