package com.scsvision.cms.statistic.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

public class BaseStatisticTest extends TestCase {

	protected Context context;

	@Override
	protected void setUp() throws Exception {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");
		context = new InitialContext(p);
		context.bind("inject", this);
	}
}
