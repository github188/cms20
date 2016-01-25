package com.scsvision.cms.maintain.test;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnectionFactory;

import junit.framework.TestCase;

public class BaseMaintainTest extends TestCase {

	protected Context context;

	@Override
	protected void setUp() throws Exception {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				"tcp://192.168.1.7:61616");
		p.put("java:/AmqConnectionFactory", factory);
		context = new InitialContext(p);
		context.bind("inject", this);
	}
}
