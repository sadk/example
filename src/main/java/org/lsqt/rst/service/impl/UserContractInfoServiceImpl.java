package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.ContractVariableValue;
import org.lsqt.rst.model.UserContractInfo;
import org.lsqt.rst.model.UserContractInfoQuery;
import org.lsqt.rst.service.UserContractInfoService;

@Service
public class UserContractInfoServiceImpl implements UserContractInfoService{
	
	@Inject private Db db;
	
	public UserContractInfo getById(Long id) {
		return db.getById(UserContractInfo.class, id) ;
	}
	
	public List<UserContractInfo> queryForList(UserContractInfoQuery query) {
		return db.queryForList("queryForPage", UserContractInfo.class, query);
	}
	
	public Page<UserContractInfo> queryForPage(UserContractInfoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserContractInfo.class, query);
	}

	public List<UserContractInfo> getAll(){
		  return db.queryForList("getAll", UserContractInfo.class);
	}
	
	public UserContractInfo saveOrUpdate(UserContractInfo model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		if (ids != null && ids.length > 0) {
			String sql = "delete from %s where contract_id in (%s)";
			sql = String.format(sql, db.getFullTable(ContractVariableValue.class), StringUtil.join(Arrays.asList(ids)));
			db.executeUpdate(sql);

			return db.deleteById(UserContractInfo.class, Arrays.asList(ids).toArray());
		}
		return 0;
	}
}
