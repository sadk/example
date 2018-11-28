package org.lsqt.uum.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.model.Pattern;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.Method;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.spi.uum.IOtherService;
import org.lsqt.spi.uum.UserService;
import org.lsqt.uum.model.UserTest;
import org.lsqt.uum.service.DepartmentServiceImpl;
import org.lsqt.uum.service.OtherService2Impl;
import org.lsqt.uum.service.UserService3AImpl;
import org.lsqt.uum.service.UserService3BImpl;
import org.lsqt.uum.service.UserService5Impl;

import com.alibaba.fastjson.JSON;

@Controller(mapping={"/user","/m_user"})
public class UserControllerTest {
	
	@Inject("userService")// 1.直接引用id; 
	private UserService userService; 
/*
	@Inject(name = "userService2", version = "2.0") // 2.直接name+version引用; 
	private UserService userService2; 

	@Inject(name = "userService3", impl = UserService3AImpl.class) // 3.按name不唯一，指定impl=xxxServiceImpl.Class引用
	private UserService userService3A; 

	@Inject(name = "userService3", impl = UserService3BImpl.class)  // 3.按name不唯一，指定impl=xxxServiceImpl.Class引用
	private UserService userService3B; 

	@Inject(name = "userService4Unique") // 4.直接引用name(唯一) 
	private UserService userService4;

	@Inject(impl = UserService5Impl.class) //  5.不写name,直接写实现类
	private UserService userService5;

	@Inject
	private IOtherService otherService; //  6.注入一个接口，啥也不写，这个接口只有一个实现类
	
	@Inject
	private OtherService2Impl otherService2Impl; // 第二类：直接引用实现类
	
	@Inject
	private DepartmentServiceImpl dept;
	*/
	@RequestMapping(mapping={"/list","/m_list"},view=View.FTL,path="test.ftl",method=Method.POST)
	public UserTest list(Integer pageIndex,Integer pageSize,UserTest user){
		UserTest t1= new UserTest();
		t1.setAge(34);
		t1.setId(234L);
		t1.setName("张三");
		
		UserTest t2= new UserTest();
		t2.setAge(34);
		t2.setId(234L);
		t2.setName("张三2");
		
		UserTest t3= new UserTest();
		t3.setAge(34);
		t3.setId(234L);
		t3.setName("张三3");
		
		ContextUtil.getContextMap().put("user", t1);
		ContextUtil.getContextMap().put("userList", Arrays.asList(t1,t2,t3));
		
		return t3;
		//return ;
	}

	@RequestMapping(view=View.JSON)
	public UserTest create(String name,Integer pageIndex,Long code,byte age,UserTest user){
		// 127.0.0.1:8080/create?name=zs&id=1000&province.name=湖南&province.user.name=李国&province.city.cname=深圳&province.city.no.noCode=1111111
		UserTest t3= new UserTest();
		t3.setAge(10);
		t3.setId(1000L);
		t3.setName("张三3xxxxxx");
		/*
		System.out.println(userService);
		System.out.println(userService2);
		System.out.println(userService3A);
		System.out.println(userService3B);
		System.out.println(userService4);
		System.out.println(userService5);
		*/
		System.out.println(name+"===="+pageIndex);
		System.out.println("创建了一个用户");
		
		System.out.println(JSON.toJSONString(user, true));
		return user;
	}
	
	@RequestMapping(view=View.JSON)
	public String create2(String name,Integer pageIndex,Long code,byte age,UserTest user){
		UserTest t3= new UserTest();
		t3.setAge(10);
		t3.setId(1000L);
		t3.setName("张三2222rrrrrrxxxxxx");
		
		
		System.out.println("创建了一个用户2");
		String json = JSON.toJSONString(t3,true);
		return json;
	}
	
	@RequestMapping(view=View.JSON)
	public String create3(String name,Integer pageIndex,Long code,byte age,UserTest user){
		UserTest t3= new UserTest();
		t3.setAge(10);
		t3.setId(1000L);
		t3.setName("张三3333rrrrrrxxxxxx");
		
		
		System.out.println("创建了一个用户3");
		String json = JSON.toJSONString(t3,true);
		return json;
	}
	
	@RequestMapping(view=View.JSON)
	public void create4(String name,
			File mytxt,
			@Pattern("yyyy/MM/dd") Date birthday2,
			Double balance, 
			UserTest user){
		
		DecimalFormat df = new DecimalFormat("");
		
		Db db = null;
		//db.cascade(user,"city");
	}
}
