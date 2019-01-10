package org.lsqt.components.mvc;

import org.lsqt.components.context.Order;
import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.context.factory.AnnotationBeanFactory;
import org.lsqt.components.db.Db;
import org.lsqt.components.mvc.impl.AnnotationUrlMappingRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbInit implements Order, Initialization {
	private static final Logger log = LoggerFactory.getLogger(DbInit.class);

	private int order = 900;
	
	private Db db;

	/**
	 * 
	 */
	private BeanFactory beanFactory;
	/**
	 * 
	 */
	public DbInit(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void init() {
		// DB 暂时配置在spring容器里
		db = beanFactory.getBean(Db.class);
	}

	public Db getDb() {
		return db;
	}

	public void setDb(Db db) {
		this.db = db;
	}
}
