package org.lsqt.uum.controller.handler;

import org.lsqt.components.context.Result;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.Match;
import org.lsqt.components.db.Db;
import org.lsqt.uum.model.User;

@Component
public class UserBeforeHandlerDemo {
	@Inject private Db db;
	
	@Match(mapping = { "/m/save_or_update" }, text = "更新用户（不能更新成admin账号）")
	public Result<User> saveOrUpdate(User form) {
		System.out.println("同名方法调用------------>,只匹配url:/m/save_or_update");
		if ("admin".equals(form.getLoginName())) {
			throw new RuntimeException("不能修改成admin账号");
			
			//return Result.fail("同名方法---------不能修改成admin账号");
		}
		return Result.ok(form);
	}
	
	@Match(mapping={"/m/save_or_update3"},excludeTransaction=true)
	public Result<User> update(User form, String loginPwdReboot,String other) {
		System.out.println("只匹配移动端，三个参数据处理器方法");
		return Result.ok(form);
	}
	
	@Match(mapping={"/m/save_or_update2"},text="只匹配移动端....")
	public Result<User> update(User form, String loginPwdReboot) {
		System.out.println("指定了更新方法为Update, 只匹配移动端");
		return Result.ok(form);
	}
	
	
	
	
	
	// ----------------------------- 参数类型不同 --------------------------------------
	@Match(mapping={"/m/save_or_update3_1"},excludeTransaction=true)
	public Result<User> myUpdate(User form, String loginPwdReboot,Long id) {
		System.out.println("执行三个参数的处理器方法，User form, String loginPwdReboot,Long id");
		return Result.ok(form);
	}
	
	@Match(mapping={"/m/save_or_update3"},excludeTransaction=true)
	public Result<User> myUpdate(User form, String loginPwdReboot,String id) {
		System.out.println("执行三个参数的处理器方法，User form, String loginPwdReboot,String id");
		return Result.ok(form);
	}
	
	@Match(mapping={"/m/save_or_update3"},excludeTransaction=true)
	public Result<User> myUpdate(User form, String loginPwdReboot,Integer id) {
		System.out.println("执行三个参数的处理器方法，User form, String loginPwdReboot,Integer id");
		return Result.ok(form);
	}
	// ----------------------------- 参数类型不同 --------------------------------------
	
	
	
	@Match(mapping={"/ipad/save_or_update4"})
	public Result<User> update(User form, String loginPwdReboot,Integer id,Long card) {
		System.out.println("执行三个参数的处理器方法，update(User form, String loginPwdReboot,Integer id,Long card)  ");
		return Result.ok(form);
	}
	
	
}
