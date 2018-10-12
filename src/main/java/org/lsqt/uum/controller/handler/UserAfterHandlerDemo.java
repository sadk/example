package org.lsqt.uum.controller.handler;

import org.lsqt.components.context.Result;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.Match;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.uum.model.User;

@Component
public  class UserAfterHandlerDemo {
	@Inject private Db db;


	@Match(mapping = {"/m/page","/ipad/page"},text="手机和IPAD端只显示用户名、手机、邮箱")
	public Result<Page<User>> page4MobileTerminal(Page<User> page) {
		if(page!=null && ArrayUtil.isNotBlank(page.getData())) {
			
			db.executeQuery("select 1 from dual");
			
			
		}
		return Result.ok(page); //统一数据格式，用result包装
	}
}
