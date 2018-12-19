package org.lsqt;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.lsqt.components.mvc.BootApplicationFilter;


public class JarServer {
public static void main(String[] args) throws Exception {
	 long start = System.currentTimeMillis();
		Server server = new Server(80);
		//WebAppContext webappHandler = new WebAppContext("src/main/webapp", "/");
		
		//webappHandler.addBean(new JspStarter(webappHandler)); 
		WebAppContext webappHandler = new WebAppContext();
		webappHandler.setContextPath("/");
		//webappHandler.setResourceBase("./");
		//webappHandler.addServlet(MyServlet.class, "*."); 	
		
		ServletContextHandler hd = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		EnumSet<DispatcherType>  es = EnumSet.allOf(DispatcherType.class);
		hd.addFilter(BootApplicationFilter.class, "/*",es);
		
		
		
	
	 
		//hd.addServlet(MyServlet.class, "/");
		
		server.setHandler(hd);
		server.start();
		long end = System.currentTimeMillis();
		System.out.println("jetty+webapp startup cost->"+(end-start)+"(ms)");
		server.join();
		server.stop();
}
}

