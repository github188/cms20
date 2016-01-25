/**
 * 
 */
package com.scsvision.cms.gather.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

/**
 * @author MIKE
 *
 */
public class BaseTest extends TestCase {

	protected Context context;

	@Override
	protected void setUp() throws Exception {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
//		p.put("mysqlDS", "new://Resource?type=DataSource");
//		p.put("mysqlDS.JdbcDriver", "com.mysql.jdbc.Driver");
//		p.put("mysqlDS.JdbcUrl", "jdbc:mysql://192.168.1.104:3306/cms?useUnicode=true&characterEncoding=utf8");
//		p.put("mysqlDS.JtaManaged", "true");
//		p.put("mysqlDS.UserName", "cms");
//		p.put("mysqlDS.Password", "cms");
		context = new InitialContext(p);
		context.bind("inject", this);
	}
}
