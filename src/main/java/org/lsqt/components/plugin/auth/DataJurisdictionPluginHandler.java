package org.lsqt.components.plugin.auth;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.context.permission.AuthenticationNode;
import org.lsqt.components.context.permission.JurisdictionHandler;
import org.lsqt.components.db.Db;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据查询权限处理器
 * @author mm
 *
 */
@Component
public class DataJurisdictionPluginHandler implements JurisdictionHandler{
	@Inject private Db db;
	
	/**超级管理员**/
	private String ADMIN = "admin";
	
	private static final Logger log = LoggerFactory.getLogger(DataJurisdictionPluginHandler.class);
		
	public Object handle(Object context) throws Exception {
		
		if (ADMIN.equals(ContextUtil.getLoginAccount())) {
			return null;
		}
		if (ContextUtil.getLoginId() == null) {
			return null;
		}
		if(!isEnable()) {
			return null;
		}
		
		ResQuery query = new ResQuery();
		query.setUserId(Long.valueOf(ContextUtil.getLoginId()));
		query.setType(Res.TYPE_数据查询);
		List<Res> list = db.queryForList("queryForPage", Res.class, query);

		List<AuthenticationNode> nodeList = new ArrayList<>();
		for (Res e : list) {
			AuthenticationNode node = new AuthenticationNode();
			node.id = e.getId();
			node.name = e.getName();
			node.value = e.getValue();
			nodeList.add(node);
		}

		String dataJurisdictKey = JurisdictionHandler.class.getName() +"."+ContextUtil.getLoginAccount()+"."+ Thread.currentThread().getId();
		log.info("数据查询权限处理器，上下文key：{}",dataJurisdictKey);
		
		ContextUtil.getContextMap().put(dataJurisdictKey, nodeList);

		return null;
	}

	public boolean isEnable() {
		log.debug("数据查询权限处理器是否启用: {}",false);
		return false;
	}
	
}

