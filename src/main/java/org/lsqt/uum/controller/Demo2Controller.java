package org.lsqt.uum.controller;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.mvc.Before;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.uum.controller.handler.UserBeforeHandlerDemo;
import org.lsqt.uum.model.User;

@Controller(mapping={"/uum/demo2"})
public class Demo2Controller {
	/**
	 *  指定方法、指定参数3
	 * 以定义参数个数类型，匹配前置处理器方法
	 */
	@Before(clazz = UserBeforeHandlerDemo.class, method="myUpdate", args={User.class,String.class,Integer.class})
	@RequestMapping(mapping = { "/save_or_update3", "/m/save_or_update3" },excludeTransaction = true)
	public User saveOrUpdate2(User form) {
		System.out.println("源方法只有一个参数，处理器有三个参数，注解了args={User.class,String.class,Integer.class}三个参数");
		return form;
	}
}
