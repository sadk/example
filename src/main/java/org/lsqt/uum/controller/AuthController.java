package org.lsqt.uum.controller;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;
import org.lsqt.uum.util.ResourceUtil;

/**
 * 授权管理
 * @author mingmin.yuan
 *
 */
@Controller(mapping={"/auth/grant"})
public class AuthController {
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/save_or_update_menu_function", "/m/save_or_update_menu_function" },text="给一个角色（或多个）分配菜单和功能按钮权限")
	public Integer saveOrUpdateMenuFunction(String roleIds,String resIds) {
		if (StringUtil.isNotBlank(roleIds)) {
			
			List<Long> roleIdList = StringUtil.split(Long.class, roleIds, ",");
			List<Long> resIdList = StringUtil.split(Long.class, roleIds, ",");
			
			
			if (ArrayUtil.isNotBlank(resIdList)) {
				String sql = String.format("delete from uum_role_res where role_id in (%s)", StringUtil.join(roleIdList));
				db.executeUpdate(sql);
			}
			
			if(StringUtil.isNotBlank(roleIds,resIds)) {
				String sql = "insert into uum_role_res(role_id,res_id) values (?,?)";
				List<Object> args = new ArrayList<>();
				for (Long roleId : roleIdList) {
					 for(Long resId: resIdList) {
						 args.add(roleId);
						 args.add(resId);
					 }
				}
				db.batchUpdate(sql, args);
			}
		}
		return 0;
	}
	
	 
}
