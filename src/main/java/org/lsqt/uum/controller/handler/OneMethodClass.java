package org.lsqt.uum.controller.handler;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.uum.model.User;

import com.alibaba.fastjson.JSON;

@Component
public class OneMethodClass {
	public void wrap(User form, String loginName) {
		System.out.println(loginName);
		System.out.println(JSON.toJSONString(form,true));
		
		if("admin".equals(loginName)) {
			//throw new RuntimeException("不能更改admin账号,before........");
		}
	}
}
