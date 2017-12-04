package org.lsqt.uum.dao;


import org.lsqt.components.context.annotation.Dao;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Scope;
import org.lsqt.components.db.Db;

@Dao(scope=Scope.PROTOTYPE)
public class UserDao {
	//@Inject
	//private User user;
	
	@Inject
	private Db db;

	public void hello(){
		Integer i = db.queryForObject("select 12345 from dual",Integer.class);
		System.out.println(i);
	}
	
}
