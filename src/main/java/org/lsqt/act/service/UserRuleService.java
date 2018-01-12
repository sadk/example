package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.UserRule;
import org.lsqt.act.model.UserRuleQuery;

public interface UserRuleService {
	
	Page<UserRule> queryForPage(UserRuleQuery query);

	UserRule saveOrUpdate(UserRule model);
	
	UserRule saveOrUpdate(UserRule model,String ...props) ;
	
	int deleteById(Long... ids);
	
	Collection<UserRule> getAll();
	
	// --------------------------------------------------------
	
	/**
	 * 获取规则解析后的用户 （重要！！！）
	 * @param loginUserId 登陆用户上下文 
	 * @param variables （流程或扩展）变量数据
	 * @param ruleIds 指定的规则
	 * @return 返回用户ID
	 */
	List<String> resolveUsers(Long loginUserId, Map<String,Object> variables, Long ... ruleIds) throws Exception;
}
