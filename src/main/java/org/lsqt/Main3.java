package org.lsqt;

import org.apache.tomcat.util.scan.StandardJarScanner;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.webapp.WebAppContext;
import org.lsqt.components.db.orm.ORMappingIdGenerator;

/**
 * 启动类,模拟分布式3
 * @author admin
 *
 */
public class Main3 {
	
	public static class JspStarter extends AbstractLifeCycle implements ServletContextHandler.ServletContainerInitializerCaller {
		JettyJasperInitializer sci;
		ServletContextHandler context;

		public JspStarter(ServletContextHandler context) {
			this.sci = new JettyJasperInitializer();
			this.context = context;
			//this.context.setAttribute("org.apache.tomcat.JarScanner", new StandardJarScanner());
		}

		@Override
		protected void doStart() throws Exception {
			ClassLoader old = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(context.getClassLoader());
			try {
				sci.onStartup(null, context.getServletContext());
				super.doStart();
			} finally {
				Thread.currentThread().setContextClassLoader(old);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
	    long start = System.currentTimeMillis();
		Server server = new Server(82);
		WebAppContext webappHandler = new WebAppContext("src/main/webapp", "/qdp3");
		
		webappHandler.addBean(new JspStarter(webappHandler)); 
		//webappHandler.addServlet(JettyJspServlet.class, "*.jsp"); 	
		
		server.setHandler(webappHandler);
		server.start();
		long end = System.currentTimeMillis();
		System.out.println("jetty+webapp startup cost->"+(end-start)+"(ms)");
		server.join();
		server.stop();
	}
}



