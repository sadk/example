package org.lsqt;

import javax.servlet.ServletException;

import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.lsqt.components.mvc.BootApplicationFilter;
/*
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.util.Headers;
*/
public class Undertown {
	public static void main(String[] args) throws ServletException {
		
/*		
		DeploymentInfo servletBuilder = Servlets.deployment().setClassLoader(Undertown.class.getClassLoader())
				.setContextPath("/").setDeploymentName("test.war")
				.addFilter(new FilterInfo("BootApplictionFilter", BootApplicationFilter.class));

		DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
		manager.deploy();
		PathHandler path = Handlers.path(Handlers.redirect("/")).addPrefixPath("/", manager.start());

		Undertow server = Undertow.builder().addHttpListener(8080, "localhost").setHandler(path).build();
		server.start();*/
	}

}
