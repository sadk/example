package org.lsqt.test.db;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.lsqt.components.db.Db;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InitDb {
	
	@Test
	public void initH2() {
		ApplicationContext app = new ClassPathXmlApplicationContext("application.xml");
		Assert.assertNotNull(app);
		
		Db db = app.getBean(Db.class);
		List<Map<String,Object>> list = db.executeQuery("select * from TEST where 1=?", 1);
		System.out.println(list);
	}
}
