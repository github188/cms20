package com.scsvision.center.jms.mdb;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.center.jms.MethodMap;
import com.scsvision.cms.constant.Header;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;

/**
 * TopicEventListener
 * 
 * @author MIKE
 *         <p />
 *         Create at 2015 下午2:09:59
 */
@TransactionManagement(TransactionManagementType.BEAN)
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "TopicEvent") })
public class TopicEventListener implements MessageListener {
	private final Logger logger = LoggerFactory
			.getLogger(TopicEventListener.class);
	@Resource
	private MessageDrivenContext context;

	@Override
	public void onMessage(Message message) {
		String method = null;
		String businessId = null;
		try {
			message.acknowledge();
			method = message.getStringProperty(Header.METHOD);
			String type = message.getJMSType();
			if (null == type || type.isEmpty()) {
				type = message.getStringProperty("Type");
			}
			if (null == type) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"JMS Type could not be empty !");
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
					logger.debug("BusinessId[" + businessId
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
				controller.getClass()
						.getDeclaredMethod(bean[1], body.getClass())
						.invoke(controller, body);
			} else if (bean.length == 3) {
				controller
						.getClass()
						.getDeclaredMethod(bean[1], body.getClass(),
								String.class)
						.invoke(controller, body, businessId);
			}
			// context.getUserTransaction().commit();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
