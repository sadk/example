package org.lsqt.uum.util;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.User;

/**
 * 用于控制页面元素的权限
 * @author mm
 *
 */
public class WebPermissionUtil {
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public static boolean hasPermission(String code) {
		User loginUser = ContextUtil.getLoginUser();
		if (loginUser == null) {
			return false;
		}

		List<Res> permissionList = loginUser.getMyResList();

		for (Res e : permissionList) {
			if (e.getType() == null)
				continue;

			if (Res.TYPE_页面元素 == e.getType() && code.equals(e.getCode())) {
				return true;
			}
		}

		return false;
	}
}

