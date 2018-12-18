package org.lsqt;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.lsqt.components.db.orm.ORMappingIdGenerator;

/**
 * 启动类
 * @author admin
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception {
	    long start = System.currentTimeMillis();
		Server server = new Server(80);
		WebAppContext webappHandler = new WebAppContext("src/main/webapp", "/");
		server.setHandler(webappHandler);
		server.start();
		long end = System.currentTimeMillis();
		System.out.println("jetty+webapp startup cost->"+(end-start)+"(ms)");
		server.join();
		server.stop();
	}
}
