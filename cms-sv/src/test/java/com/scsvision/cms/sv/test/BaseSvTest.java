package com.scsvision.cms.sv.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

/**
 * BaseTmTest
 * @author MIKE
 * <p />
 * Create at 2015 下午3:33:07
 */
public class BaseSvTest extends TestCase {
	protected Context context;

	@Override
	protected void setUp() throws Exception {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		p.put("mysqlDS", "new://Resource?type=DataSource");
		p.put("mysqlDS.JdbcDriver", "com.mysql.jdbc.Driver");
		p.put("mysqlDS.JdbcUrl", "jdbc:mysql://192.168.1.17:3306/cms20?useUnicode=true&characterEncoding=utf8");
		p.put("mysqlDS.JtaManaged", "true");
		p.put("mysqlDS.UserName", "cms20");
		p.put("mysqlDS.Password", "cms20");
		context = new InitialContext(p);
		context.bind("inject", this);
	}
}
