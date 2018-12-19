package org.lsqt.uum.controller;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.After;
import org.lsqt.components.context.annotation.mvc.Before;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.uum.controller.handler.OneMethodClass;
import org.lsqt.uum.controller.handler.UserAfterHandlerDemo;
import org.lsqt.uum.controller.handler.UserBeforeHandlerDemo;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.User;
import org.lsqt.uum.model.UserQuery;
import org.lsqt.uum.service.OrgService;
import org.lsqt.uum.service.UserService;

@Controller(mapping={"/uum/demo"})
public class DemoController {

	@Inject private UserService userService; 
	@Inject private OrgService orgService;
	@Inject private Db db;
	
	@Before(clazz = OneMethodClass.class, text = "处理器类里只有一个方法")
	@RequestMapping(mapping = { "/save_or_update1", "/m/save_or_update1" },excludeTransaction=true)
	public User saveOrUpdate1(User form) throws Exception{
		if("admin".equals(form.getLoginName())) {
			throw new Exception("不能更改admin账号");
		}
		form = new User();
		form.setId(1L);
		form.setName("demo1");
		form.setEmail("demo1@demo.com");
		form.setLoginName("admin");
		return form;
	}
	
	@Before(clazz = UserBeforeHandlerDemo.class, text = "处理器里有几个方法，以当前方法名和参数匹配处理器方法")
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" },excludeTransaction = true)
	public User saveOrUpdate(User form) {
		form = new User();
		form.setId(1L);
		form.setName("demo");
		form.setEmail("demo@demo.com");
		form.setLoginName("admin");
		return form;
	}
	
/*	@Before(clazz = UserBeforeHandlerDemo.class, text = "处理器里有几个方法，以当前方法名和参数匹配处理器方法2")
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" },excludeTransaction = true)
	public User saveOrUpdate(User form,String nickName) {
		form = new User();
		form.setId(1L);
		form.setName("demo");
		form.setEmail("demo@demo.com");
		form.setLoginName("admin");
		form.setName(nickName);
		return form;
	}*/
	
	/**
	 * 指定方法
	 * 默认以当前方法参数匹配前置处理器参数,如果不写args的话
	 */
	@Before(clazz = UserBeforeHandlerDemo.class, method="update")
	@RequestMapping(mapping = { "/save_or_update2", "/m/save_or_update2" },excludeTransaction = true)
	public User saveOrUpdate2(User form, String loginPwdReboot) {
		form = new User();
		form.setId(1L);
		form.setName("demo2");
		form.setEmail("demo2@demo.com");
		form.setLoginName("admin");
		return form;
	}
	

	
	
	
	/**
	 * 指定方法、指定参数1
	 * 以定义参数个数类型，匹配前置处理器方法
	 */
	@Before(clazz = UserBeforeHandlerDemo.class, method="myUpdate", args={User.class,String.class,String.class})
	@RequestMapping(mapping = { "/save_or_update3", "/m/save_or_update3" },excludeTransaction = true)
	public User saveOrUpdate2(User form) {

		 System.out.println("源方法只有一个参数，处理器有三个参数，注解了args={User.class,String.class,String.class}三个参数");
		return form;
	}
	
	/**
	 *  指定方法、指定参数2
	 * 以定义参数个数类型，匹配前置处理器方法
	 */
	@Before(clazz = UserBeforeHandlerDemo.class, method="myUpdate", args={User.class,String.class,Long.class})
	@RequestMapping(mapping = { "/save_or_update3_1", "/m/save_or_update3_1" },excludeTransaction = true)
	public User saveOrUpdate31(User form) {

		 System.out.println("源方法只有一个参数，处理器有三个参数，注解了args={User.class,String.class,Long.class}三个参数");
		return form;
	}
	
	
	
	
	@Before(clazz = UserBeforeHandlerDemo.class, method="update")
	@RequestMapping(mapping = { "/save_or_update4", "/ipad/save_or_update4" },excludeTransaction = true)
	public User saveOrUpdate2(User form, String loginPwdReboot,Integer id,Long card) {
		form = new User();
		form.setId(1L);
		form.setName("demo2");
		form.setEmail("demo2@demo.com");
		form.setLoginName("admin_Integer");
		System.out.println("要执行 update, 三个入参的那个方法");
		return form;
	}
	
	
	
	
	
	
	@After(clazz = UserAfterHandlerDemo.class, method = "page4MobileTerminal")
	@RequestMapping(mapping = { "/page", "/m/page" },isTransaction = false)
	public Page<User> queryForPage(UserQuery query,Boolean isAllChild) {
		if (isAllChild != null && isAllChild) {
			Long root = query.getOrgId();
			List<Org> list = orgService.getAllChildNodes(root);
			
			List<Long> ids = new ArrayList<>();
			for(Org e: list) {
				ids.add(e.getId());
			}
			
			query.setOrgId(null);
			query.setOrgIds(StringUtil.join(ids, ","));
		}
		return userService.queryForPage(query); 
	}
	
}
